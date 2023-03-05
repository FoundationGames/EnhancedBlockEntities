package foundationgames.enhancedblockentities.client.model;

import foundationgames.enhancedblockentities.util.DateUtil;
import foundationgames.enhancedblockentities.util.duck.ModelStateHolder;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class ModelSelector {
    private static final List<ModelSelector> REGISTRY = new ArrayList<>();

    public static final ModelSelector STATE_HOLDER_SELECTOR = new ModelSelector() {
        @Override
        public int getModelIndex(BlockRenderView view, BlockState state, BlockPos pos, Supplier<Random> rand, @Nullable RenderContext ctx) {
            if(view.getBlockEntity(pos) instanceof ModelStateHolder stateHolder) {
                return stateHolder.getModelState();
            }
            return 0;
        }
    };

    public static final ModelSelector CHEST = STATE_HOLDER_SELECTOR;

    public static final ModelSelector CHEST_WITH_CHRISTMAS = new ModelSelector() {
        @Override
        public int getParticleModelIndex() {
            return DateUtil.isChristmas() ? 2 : 0;
        }

        @Override
        public int getModelIndex(BlockRenderView view, BlockState state, BlockPos pos, Supplier<Random> rand, @Nullable RenderContext ctx) {
            if(view.getBlockEntity(pos) instanceof ModelStateHolder stateHolder) {
                return stateHolder.getModelState() + this.getParticleModelIndex();
            }
            return this.getParticleModelIndex();
        }
    };

    public static final ModelSelector BELL = STATE_HOLDER_SELECTOR;

    public static final ModelSelector SHULKER_BOX = STATE_HOLDER_SELECTOR;

    public int getParticleModelIndex() {
        return 0;
    }

    public abstract int getModelIndex(BlockRenderView view, BlockState state, BlockPos pos, Supplier<Random> rand, @Nullable RenderContext ctx);

    public final int id;

    public ModelSelector() {
        this.id = REGISTRY.size();
        REGISTRY.add(this);
    }

    public static ModelSelector fromId(int id) {
        return REGISTRY.get(id);
    }
}
