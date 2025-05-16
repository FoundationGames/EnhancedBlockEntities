package foundationgames.enhancedblockentities.client.model;

import net.fabricmc.fabric.api.client.model.loading.v1.ExtraModelKey;
import net.fabricmc.fabric.api.client.model.loading.v1.UnbakedExtraModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.*;
import net.minecraft.util.Identifier;

import java.util.*;

public class DynamicUnbakedModel implements UnbakedExtraModel<BlockStateModel>, UnbakedModel {
    private final ExtraModelKey<BlockStateModel>[] models;
    private final ModelSelector selector;
    private final DynamicModelEffects effects;


    public DynamicUnbakedModel(ExtraModelKey<BlockStateModel>[] modelKeys, ModelSelector selector, DynamicModelEffects effects) {
        this.selector = selector;
        this.effects = effects;
        models = modelKeys;
        for (ExtraModelKey<BlockStateModel> key : modelKeys) {
            System.out.println("Loading model: " + key);
        }
    }


    @Override
    public void resolve(Resolver resolver) {
        for (ExtraModelKey<BlockStateModel> modelId : models) {
            if(modelId == null) continue;

        }
    }

    private static final ThreadLocal<net.minecraft.util.math.random.Random> RANDOM = ThreadLocal.withInitial(() -> net.minecraft.util.math.random.Random.create(42L));

    @Override
    public BlockStateModel bake(Baker baker) {
        BlockStateModel[] bakedModels = new BlockStateModel[models.length];
        BakedModelManager manager = MinecraftClient.getInstance().getBakedModelManager();
        for (int i = 0; i < models.length; i++) {
            BlockStateModel modl = manager.getModel(models[i]);
            System.out.println("Baking model: " + models[i] + " -> " + modl);
            bakedModels[i] = modl;
        }
        
        return new DynamicBakedModel(bakedModels, selector, effects);
    }
}