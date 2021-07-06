package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.util.duck.RebuildScheduler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderChestBlockEntity.class)
public abstract class EnderChestBlockEntityMixin implements RebuildScheduler {
    private int rebuildScheduler = 0;

    @Inject(method = "clientTick", at = @At(
            value = "TAIL", shift = At.Shift.AFTER, ordinal = 0
    ))
    private static void enhanced_bes$listenForOpenClose(World world, BlockPos pos, BlockState state, EnderChestBlockEntity blockEntity, CallbackInfo ci) {
        if(world.isClient()) {
            var entityAccess = (ChestBlockEntityAccessor)blockEntity;
            var schedulable = (RebuildScheduler)blockEntity;
            rebuildChunk(world, pos, state);
            if(schedulable.getScheduler() > 0) {
                schedulable.setScheduler(schedulable.getScheduler() - 1);
                if(schedulable.getScheduler() <= 0) rebuildChunk(world, pos, state);
            }
            if(entityAccess.getAnimator().getProgress(0) <= 0) {
                schedulable.setScheduler(1);
            }
        }
    }

    @Override
    public void setScheduler(int value) {
        this.rebuildScheduler = value;
    }

    @Override
    public int getScheduler() {
        return rebuildScheduler;
    }

    private static void rebuildChunk(World world, BlockPos pos, BlockState state) {
        if(EnhancedBlockEntities.CONFIG.renderEnhancedBells) {
            MinecraftClient.getInstance().worldRenderer.updateBlock(world, pos, state, state, 1);
        }
    }
}
