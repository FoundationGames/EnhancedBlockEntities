package foundationgames.enhancedblockentities.client.model;

import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBlockStateModel;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    @Override
    public void emitQuads(QuadEmitter emitter, BlockRenderView view, BlockPos pos, BlockState state, Random random, Predicate<@Nullable Direction> cullTest) {
        RenderMaterial mat = null;

        var indices = this.activeModelIndices.get();
        var models = this.displayedModels.get();


        getSelector().writeModelIndices(view, state, pos, () -> random, indices);
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
                for (BlockModelPart part : model.getParts(random)) {
                    for (BakedQuad quad : part.getQuads(dir)) {
                        emitter.fromVanilla(quad, mat, dir);
                        emitter.emit();
                    }
                }
            }
        }
    }

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

    public ModelSelector getSelector() {
        return selector;
    }

    public DynamicModelEffects getEffects() {
        return effects;
    }

    @Override
    public void addParts(Random random, List<BlockModelPart> parts) {
        // add parts to models
        return;
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
