package foundationgames.enhancedblockentities.dashloader;

import foundationgames.enhancedblockentities.client.model.DynamicBakedModel;
import foundationgames.enhancedblockentities.client.model.DynamicModelEffects;
import foundationgames.enhancedblockentities.client.model.ModelSelector;
import io.activej.serializer.annotations.Deserialize;
import io.activej.serializer.annotations.Serialize;
import io.activej.serializer.annotations.SerializeSubclasses;
import net.minecraft.client.render.model.BakedModel;
import net.quantumfusion.dashloader.DashLoader;
import net.quantumfusion.dashloader.DashRegistry;
import net.quantumfusion.dashloader.models.DashModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashDynamicBakedModel implements DashModel {
    @Serialize(order = 0)
    public final Long[] models;

    @Serialize(order = 1)
    public final int selector;

    @Serialize(order = 2)
    public final int effects;

    public DashDynamicBakedModel(@Deserialize("models") Long[] models,
                                 @Deserialize("selector") int selector,
                                 @Deserialize("effects") int effects) {
        this.models = models;
        this.selector = selector;
        this.effects = effects;
    }


    public DashDynamicBakedModel(DynamicBakedModel dynamicBakedModel,DashRegistry registry) {
        final BakedModel[] models = dynamicBakedModel.getModels();
        this.models = new Long[models.length];
        for (int i = 0, modelsLength = models.length; i < modelsLength; i++) {
            final BakedModel model = models[i];
            this.models[i] = (registry.createModelPointer(model, DashLoader.getInstance().multipartData.get(model)));
        }
        final ModelSelector selector = dynamicBakedModel.getSelector();
        if(selector == ModelSelector.BELL) {
            this.selector = 0;
        } else if(selector == ModelSelector.CHEST) {
            this.selector = 1;
        } else {
            this.selector = 2;
        }
        final DynamicModelEffects effects = dynamicBakedModel.getEffects();
        if (effects == DynamicModelEffects.BELL) {
            this.effects = 0;
        } else if (effects == DynamicModelEffects.CHEST) {
            this.effects = 1;
        } else {
            this.effects = 2;
        }
    }

    @Override
    public BakedModel toUndash(DashRegistry registry) {
        BakedModel[] baked = new BakedModel[models.length];
        for (int i = 0; i < models.length; i++) {
            baked[i] = registry.getModel(models[i]);
        }
        ModelSelector outSelector;
        switch (selector) {
            case 0:
                outSelector = ModelSelector.BELL;
                break;
            case 1:
                outSelector = ModelSelector.CHEST;
                break;
            default:
                outSelector = ModelSelector.CHEST_WITH_CHRISTMAS;
        }
        DynamicModelEffects outEffect;
        switch (selector) {
            case 0:
                outEffect = DynamicModelEffects.BELL;
                break;
            case 1:
                outEffect = DynamicModelEffects.CHEST;
                break;
            default:
                outEffect = DynamicModelEffects.DEFAULT;
                break;
        }
        return new DynamicBakedModel(baked,outSelector,outEffect);
    }

    @Override
    public void apply(DashRegistry registry) {
        DashModel.super.apply(registry);
    }

    @Override
    public int getStage() {
        return 3;
    }
}
