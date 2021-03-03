package foundationgames.enhancedblockentities.client.model;

import foundationgames.enhancedblockentities.util.DateUtil;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.function.Supplier;

public interface ModelSelector {
    ModelSelector CHEST = (view, state, pos, rand, ctx) -> {
        if(view.getBlockEntity(pos) instanceof ChestAnimationProgress) {
            ChestAnimationProgress be = (ChestAnimationProgress)view.getBlockEntity(pos);
            if(be.getAnimationProgress(1) > 0) {
                return 1;
            }
        }
        return 0;
    };

    ModelSelector CHEST_WITH_CHRISTMAS = (view, state, pos, rand, ctx) -> {
        int os = DateUtil.isChristmas() ? 2 : 0;
        if(view.getBlockEntity(pos) instanceof ChestAnimationProgress) {
            ChestAnimationProgress be = (ChestAnimationProgress)view.getBlockEntity(pos);
            if(be.getAnimationProgress(1) > 0) {
                return 1 + os;
            }
        }
        return os;
    };

    int getModelIndex(BlockRenderView view, BlockState state, BlockPos pos, Supplier<Random> rand, @Nullable RenderContext ctx);
}
