package foundationgames.enhancedblockentities.client.model;

import foundationgames.enhancedblockentities.util.DateUtil;
import foundationgames.enhancedblockentities.util.duck.ModelStateHolder;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.function.Supplier;

public interface ModelSelector {
    ModelSelector STATE_HOLDER_SELECTOR = (view, state, pos, rand, ctx) -> {
        if(view.getBlockEntity(pos) instanceof ModelStateHolder stateHolder) {
            return stateHolder.getModelState();
        }
        return 0;
    };

    ModelSelector CHEST = STATE_HOLDER_SELECTOR;

    ModelSelector CHEST_WITH_CHRISTMAS = (view, state, pos, rand, ctx) -> {
        int os = DateUtil.isChristmas() ? 2 : 0;
        if(view.getBlockEntity(pos) instanceof ModelStateHolder stateHolder) {
            return stateHolder.getModelState() + os;
        }
        return os;
    };

    ModelSelector BELL = STATE_HOLDER_SELECTOR;

    ModelSelector SHULKER_BOX = STATE_HOLDER_SELECTOR;

    int getModelIndex(BlockRenderView view, BlockState state, BlockPos pos, Supplier<Random> rand, @Nullable RenderContext ctx);
}
