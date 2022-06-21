package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.client.render.entity.ChestBlockEntityRendererOverride;
import foundationgames.enhancedblockentities.util.duck.ModelStateHolder;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestBlockEntity.class)
public abstract class ChestBlockEntityMixin extends BlockEntity implements ModelStateHolder {
    @Unique private int enhanced_bes$modelState = 0;

    private ChestBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "clientTick", at = @At(value = "TAIL"))
    private static void enhanced_bes$listenForOpenClose(World world, BlockPos pos, BlockState state, ChestBlockEntity blockEntity, CallbackInfo ci) {
        int mState = ChestBlockEntityRendererOverride.getAnimationProgress(blockEntity, 0)
                .getAnimationProgress(0) > 0 ? 1 : 0;
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
