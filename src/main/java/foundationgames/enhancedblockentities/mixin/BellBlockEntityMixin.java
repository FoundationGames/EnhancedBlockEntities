package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BellBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BellBlockEntity.class)
public class BellBlockEntityMixin extends BlockEntity {
    @Shadow public boolean ringing;

    public BellBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    // Can't inject because one of the args' type is private (BellBlockEntity$Effect)
    // So now you get to suffer through this
    @ModifyVariable(method = "tick", at = @At(
            value = "JUMP", opcode = Opcodes.IF_ICMPLT, ordinal = 0, shift = At.Shift.BEFORE
    ), index = 3)
    private static BellBlockEntity enhanced_bes$listenForStopRinging(BellBlockEntity blockEntity) {
        if (blockEntity.ringTicks >= 50) {
            rebuildChunk(blockEntity.getWorld(), blockEntity.getPos(), blockEntity.getCachedState());
        }
        return blockEntity;
    }

    @Inject(method = "onSyncedBlockEvent", at = @At("HEAD"))
    public void enhanced_bes$listenForRing(int type, int data, CallbackInfoReturnable<Boolean> cir) {
        // the !ringing prevents people from unnecessarily destroying your fps by spamming a bell in a complex, hard-to-rebuild chunk
        if (type == 1 && !ringing) {
            rebuildChunk(world, pos, getCachedState());
        }
    }

    private static void rebuildChunk(World world, BlockPos pos, BlockState state) {
        if(EnhancedBlockEntities.CONFIG.renderEnhancedBells) {
            MinecraftClient.getInstance().worldRenderer.updateBlock(world, pos, state, state, 1);
        }
    }
}
