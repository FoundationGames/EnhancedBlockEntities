package foundationgames.enhancedblockentities.client.model;

import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class DynamicBakedModel implements BakedModel, FabricBakedModel {
    private final BakedModel[] models;
    private final ModelSelector selector;
    private final DynamicModelEffects effects;

    private final int[] activeModelIndices;
    private final BakedModel[] displayedModels;

    public DynamicBakedModel(BakedModel[] models, ModelSelector selector, DynamicModelEffects effects) {
        this.models = models;
        this.selector = selector;
        this.effects = effects;

        this.activeModelIndices = new int[selector.displayedModelCount];
        this.displayedModels = new BakedModel[selector.displayedModelCount];
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockRenderView view, BlockState state, BlockPos blockPos, Supplier<Random> rng, RenderContext context) {
        QuadEmitter emitter = context.getEmitter();
        RenderMaterial mat = null;

        getSelector().writeModelIndices(view, state, blockPos, rng, context, this.activeModelIndices);
        for (int i = 0; i < this.activeModelIndices.length; i++) {
            int modelIndex = this.activeModelIndices[i];

            if (modelIndex >= 0) {
                this.displayedModels[i] = this.models[modelIndex];
            } else {
                this.displayedModels[i] = null;
            }
        }

        var renderer = RendererAccess.INSTANCE.getRenderer();
        if (renderer != null) {
            mat = renderer.materialById(RenderMaterial.MATERIAL_STANDARD);
        }

        for (int i = 0; i <= 6; i++) {
            Direction dir = ModelHelper.faceFromIndex(i);
            for (BakedModel model : this.displayedModels) if (model != null) {
                for (BakedQuad quad : model.getQuads(state, dir, rng.get())) {
                    emitter.fromVanilla(quad, mat, dir);
                    emitter.emit();
                }
            }
        }
    }

    @Override
    public void emitItemQuads(ItemStack itemStack, Supplier<Random> supplier, RenderContext renderContext) {
        // no
    }

    @Override
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
    public boolean isBuiltin() {
        return false;
    }

    @Override
    public Sprite getParticleSprite() {
        return models[getSelector().getParticleModelIndex()].getParticleSprite();
    }

    @Override
    public ModelTransformation getTransformation() {
        return null;
    }

    @Override
    public ModelOverrideList getOverrides() {
        return null;
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
