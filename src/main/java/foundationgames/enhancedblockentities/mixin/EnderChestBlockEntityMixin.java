package foundationgames.enhancedblockentities.mixin;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.client.MinecraftClient;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderChestBlockEntity.class)
public abstract class EnderChestBlockEntityMixin extends BlockEntity {
    @Shadow
    public float animationProgress;
    private int rebuildScheduler = 0;

    protected EnderChestBlockEntityMixin(BlockEntityType<?> blockEntityType) {
        super(blockEntityType);
    }

    @Inject(method = "tick", at = @At(
            value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V", shift = At.Shift.AFTER, ordinal = 0
    ))
    public void listenForOpen(CallbackInfo ci) {
        if(this.world.isClient()) {
            rebuildChunk();
        }
    }


    @Inject(method = "tick", at = @At(
            value = "JUMP", opcode = Opcodes.IFGE, ordinal = 0, shift = At.Shift.BEFORE
    ), slice = @Slice(
            from = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V", shift = At.Shift.AFTER, ordinal = 1)
    ))
    public void listenForClose(CallbackInfo ci) {
        if(this.world.isClient()) {
            if(this.animationProgress <= 0) {
                rebuildScheduler = 1;
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void rebuildIfNeeded(CallbackInfo ci) {
        if(rebuildScheduler > 0) {
            rebuildScheduler--;
            if(rebuildScheduler <= 0) rebuildChunk();
        }
    }

    private void rebuildChunk() {
        MinecraftClient.getInstance().worldRenderer.updateBlock(world, pos, getCachedState(), getCachedState(), 1);
    }
}
