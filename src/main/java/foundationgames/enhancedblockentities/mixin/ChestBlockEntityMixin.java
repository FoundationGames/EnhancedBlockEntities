package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.util.WorldUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestBlockEntity.class)
public abstract class ChestBlockEntityMixin {
    @Inject(method = "clientTick", at = @At(value = "TAIL"))
    private static void enhanced_bes$listenForClose(World world, BlockPos pos, BlockState state, ChestBlockEntity blockEntity, CallbackInfo ci) {
        boolean rebuild = blockEntity.getAnimationProgress(0) != 0 && blockEntity.getAnimationProgress(1) == 0;
        if (rebuild && EnhancedBlockEntities.CONFIG.renderEnhancedChests) {
            WorldUtil.scheduleRebuild(pos, state, 1);
        }
    }
}
