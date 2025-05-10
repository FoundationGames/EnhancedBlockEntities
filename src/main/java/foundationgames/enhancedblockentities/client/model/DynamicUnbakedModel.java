package foundationgames.enhancedblockentities.client.model;

import net.fabricmc.fabric.api.client.model.loading.v1.ExtraModelKey;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.*;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class DynamicUnbakedModel implements BlockStateModel.Unbaked, UnbakedModel {
    private final List<BlockStateModel.Unbaked> models;
    private final ModelSelector selector;
    private final DynamicModelEffects effects;


    public DynamicUnbakedModel(Identifier[] identifiers, ModelSelector selector, DynamicModelEffects effects) {
        this.selector = selector;
        this.effects = effects;
        BakedModelManager manager = MinecraftClient.getInstance().getBakedModelManager();
        models = new ArrayList<>();
        for (Identifier identifier : identifiers) {
            models.add(manager.getModel(ExtraModelKey.create(identifier::toString)));
        }
    }


    @Override
    public void resolve(Resolver resolver) {
        for (BlockStateModel.Unbaked modelId : models) {
            if(modelId == null) continue;
            modelId.resolve(resolver);
        }
    }

    @Override
    public BlockStateModel bake(Baker baker) {
        BlockStateModel[] bakedModels = new BlockStateModel[models.size()];

        for (int i = 0; i < models.size(); i++) {
            bakedModels[i] = models.get(i).bake(baker);
        }

        return new DynamicBakedModel(bakedModels, selector, effects);
    }

    @Override
    public BlockStateModel.UnbakedGrouped cached() {
        return new BlockStateModel.CachedUnbaked(this);
    }
}
