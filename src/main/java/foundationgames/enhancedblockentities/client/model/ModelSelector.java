package foundationgames.enhancedblockentities.client.model;

import foundationgames.enhancedblockentities.util.DateUtil;
import foundationgames.enhancedblockentities.util.duck.AppearanceStateHolder;
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
        public void writeModelIndices(BlockRenderView view, BlockState state, BlockPos pos, Supplier<Random> rand, @Nullable RenderContext ctx, int[] indices) {
            if (view.getBlockEntity(pos) instanceof AppearanceStateHolder stateHolder) {
                indices[0] = stateHolder.getModelState();
                return;
            }
            indices[0] = 0;
        }
    };

    public static final ModelSelector CHEST = STATE_HOLDER_SELECTOR;

    public static final ModelSelector CHEST_WITH_CHRISTMAS = new ModelSelector() {
        @Override
        public int getParticleModelIndex() {
            return DateUtil.isChristmas() ? 2 : 0;
        }

        @Override
        public void writeModelIndices(BlockRenderView view, BlockState state, BlockPos pos, Supplier<Random> rand, @Nullable RenderContext ctx, int[] indices) {
            if (view.getBlockEntity(pos) instanceof AppearanceStateHolder stateHolder) {
                indices[0] = stateHolder.getModelState() + this.getParticleModelIndex();
                return;
            }
            indices[0] = this.getParticleModelIndex();
        }
    };

    public static final ModelSelector BELL = STATE_HOLDER_SELECTOR;

    public static final ModelSelector SHULKER_BOX = STATE_HOLDER_SELECTOR;

    public int getParticleModelIndex() {
        return 0;
    }

    public abstract void writeModelIndices(BlockRenderView view, BlockState state, BlockPos pos, Supplier<Random> rand, @Nullable RenderContext ctx, int[] indices);

    public final int id;
    public final int displayedModelCount;

    public ModelSelector(int displayedModelCount) {
        this.id = REGISTRY.size();
        this.displayedModelCount = displayedModelCount;
        REGISTRY.add(this);
    }

    public ModelSelector() {
        this(1);
    }

    public static ModelSelector fromId(int id) {
        return REGISTRY.get(id);
    }
}
