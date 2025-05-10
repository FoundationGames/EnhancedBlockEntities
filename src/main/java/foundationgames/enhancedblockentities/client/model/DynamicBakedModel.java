package foundationgames.enhancedblockentities.client.model;

import net.fabricmc.fabric.api.renderer.v1.model.FabricBlockStateModel;
import net.minecraft.client.render.model.BlockModelPart;
import net.minecraft.client.render.model.BlockStateModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.random.Random;

import java.util.List;

public class DynamicBakedModel implements BlockStateModel, FabricBlockStateModel {
    private final BlockStateModel[] models;
    private final ModelSelector selector;
    private final DynamicModelEffects effects;

    private final ThreadLocal<int[]> activeModelIndices;
    private final ThreadLocal<BlockStateModel[]> displayedModels;

    public DynamicBakedModel(BlockStateModel[] models, ModelSelector selector, DynamicModelEffects effects) {
        this.models = models;
        this.selector = selector;
        this.effects = effects;

        this.activeModelIndices = ThreadLocal.withInitial(() -> new int[selector.displayedModelCount]);
        this.displayedModels = ThreadLocal.withInitial(() -> new BlockStateModel[selector.displayedModelCount]);
    }

/*    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(QuadEmitter emitter, BlockRenderView view, BlockState state, BlockPos pos, Supplier<Random> rng, Predicate<@Nullable Direction> cullTest) {
        RenderMaterial mat = null;

        var indices = this.activeModelIndices.get();
        var models = this.displayedModels.get();

        getSelector().writeModelIndices(view, state, pos, rng, indices);
        for (int i = 0; i < indices.length; i++) {
            int modelIndex = indices[i];

            if (modelIndex >= 0) {
                models[i] = this.models[modelIndex];
            } else {
                models[i] = null;
            }
        }

        var renderer = Renderer.get();
        if (renderer != null) {
            mat = renderer.materialById(RenderMaterial.STANDARD_ID);
        }

        for (int i = 0; i <= 6; i++) {
            Direction dir = ModelHelper.faceFromIndex(i);
            for (BlockStateModel model : models) if (model != null) {
                for (BakedQuad quad : model.getQuads(state, dir, rng.get())) {
                    emitter.fromVanilla(quad, mat, dir);
                    emitter.emit();
                }
            }
        }
    }*/

/*    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return models[0].getQuads(state, face, random);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return getEffects().ambientOcclusion();
    }

    @Override
    public boolean hasDepth() {
        return false;
    }

    @Override
    public boolean isSideLit() {
        return false;
    }



    @Override
    public ModelTransformation getTransformation() {
        return null;
    }*/

    public BlockStateModel[] getModels() {
        return models;
    }

    public ModelSelector getSelector() {
        return selector;
    }

    public DynamicModelEffects getEffects() {
        return effects;
    }

    @Override
    public void addParts(Random random, List<BlockModelPart> parts) {

    }

    /**
     * {@return a texture that represents the model}
     *
     * <p>This is primarily used in particles. For example, block break particles use this sprite.
     */
    @Override
    public Sprite particleSprite() {
        return models[getSelector().getParticleModelIndex()].particleSprite();
    }
}
