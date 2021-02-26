package foundationgames.enhancedblockentities.client.model;

import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

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

    int getModelIndex(BlockRenderView view, BlockState state, BlockPos pos, Supplier<Random> rand, RenderContext ctx);
}
