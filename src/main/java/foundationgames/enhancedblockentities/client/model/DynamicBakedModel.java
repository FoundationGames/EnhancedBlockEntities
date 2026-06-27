package foundationgames.enhancedblockentities.client.model;

import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class DynamicBakedModel implements BakedModel, FabricBakedModel {
    private final BakedModel[] models;
    private final ModelSelector selector;
    private final DynamicModelEffects effects;

    private final ThreadLocal<int[]> activeModelIndices;
    private final ThreadLocal<BakedModel[]> displayedModels;

    public DynamicBakedModel(BakedModel[] models, ModelSelector selector, DynamicModelEffects effects) {
        this.models = models;
        this.selector = selector;
        this.effects = effects;

        this.activeModelIndices = ThreadLocal.withInitial(() -> new int[selector.displayedModelCount]);
        this.displayedModels = ThreadLocal.withInitial(() -> new BakedModel[selector.displayedModelCount]);
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(QuadEmitter emitter, BlockAndTintGetter view, BlockState state, BlockPos pos, Supplier<RandomSource> rng, Predicate<@Nullable Direction> cullTest) {
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
            for (BakedModel model : models) if (model != null) {
                for (BakedQuad quad : model.getQuads(state, dir, rng.get())) {
                    emitter.fromVanilla(quad, mat, dir);
                    emitter.emit();
                }
            }
        }
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, RandomSource random) {
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
    public TextureAtlasSprite getParticleIcon() {
        return models[getSelector().getParticleModelIndex()].getParticleIcon();
    }

    @Override
    public ItemTransforms getTransforms() {
        return ItemTransforms.NO_TRANSFORMS;
    }

    public BakedModel[] getModels() {
        return models;
    }

    public ModelSelector getSelector() {
        return selector;
    }

    public DynamicModelEffects getEffects() {
        return effects;
    }
}
