package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.client.MinecraftClient;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestBlockEntity.class)
public abstract class ChestBlockEntityMixin extends LootableContainerBlockEntity {
    @Shadow protected float animationAngle;
    private int rebuildScheduler = 0;

    protected ChestBlockEntityMixin(BlockEntityType<?> blockEntityType) {
        super(blockEntityType);
    }

    @Inject(method = "tick", at = @At(
            value = "INVOKE", target = "Lnet/minecraft/block/entity/ChestBlockEntity;playSound(Lnet/minecraft/sound/SoundEvent;)V", shift = At.Shift.AFTER, ordinal = 0
    ))
    public void enhanced_bes$listenForOpen(CallbackInfo ci) {
        if(this.world.isClient()) {
            rebuildChunk();
        }
    }


    @Inject(method = "tick", at = @At(
            value = "JUMP", opcode = Opcodes.IFGE, ordinal = 0, shift = At.Shift.BEFORE
    ), slice = @Slice(
            from = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/ChestBlockEntity;playSound(Lnet/minecraft/sound/SoundEvent;)V", shift = At.Shift.AFTER, ordinal = 1)
    ))
    public void enhanced_bes$listenForClose(CallbackInfo ci) {
        if(this.world.isClient()) {
            if(this.animationAngle <= 0) {
                rebuildScheduler = 1;
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void enhanced_bes$rebuildIfNeeded(CallbackInfo ci) {
        if(rebuildScheduler > 0) {
            rebuildScheduler--;
            if(rebuildScheduler <= 0) rebuildChunk();
        }
    }

    private void rebuildChunk() {
        if(EnhancedBlockEntities.CONFIG.renderEnhancedChests) {
            MinecraftClient.getInstance().worldRenderer.updateBlock(world, pos, getCachedState(), getCachedState(), 1);
        }
    }
}
