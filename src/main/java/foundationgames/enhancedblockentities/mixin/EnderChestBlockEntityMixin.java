package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.util.duck.ModelStateHolder;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderChestBlockEntity.class)
public abstract class EnderChestBlockEntityMixin extends BlockEntity implements ModelStateHolder {
    @Unique private int enhanced_bes$modelState = 0;

    private EnderChestBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "clientTick", at = @At(value = "TAIL"))
    private static void enhanced_bes$listenForOpenClose(World world, BlockPos pos, BlockState state, EnderChestBlockEntity blockEntity, CallbackInfo ci) {
        int mState = blockEntity.getAnimationProgress(0) > 0 ? 1 : 0;
        if (EnhancedBlockEntities.CONFIG.renderEnhancedChests && ((ModelStateHolder)blockEntity).getModelState() != mState) {
            ((ModelStateHolder)blockEntity).setModelState(mState, world, pos);
        }
    }

    @Override
    public int getModelState() {
        return enhanced_bes$modelState;
    }

    @Override
    public void applyModelState(int state) {
        this.enhanced_bes$modelState = state;
    }
}
