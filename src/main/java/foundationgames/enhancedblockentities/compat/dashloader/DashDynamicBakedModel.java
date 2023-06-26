package foundationgames.enhancedblockentities.compat.dashloader;

import dev.notalpha.dashloader.api.DashObject;
import dev.notalpha.dashloader.api.registry.RegistryReader;
import dev.notalpha.dashloader.api.registry.RegistryWriter;
import foundationgames.enhancedblockentities.client.model.DynamicBakedModel;
import foundationgames.enhancedblockentities.client.model.DynamicModelEffects;
import foundationgames.enhancedblockentities.client.model.ModelSelector;
import net.minecraft.client.render.model.BakedModel;

public class DashDynamicBakedModel implements DashObject<DynamicBakedModel> {
    public final int[] models;
    public final int selectorId;
    public final int effectsId;

    public DashDynamicBakedModel(int[] models, int selectorId, int effectsId) {
        this.models = models;
        this.selectorId = selectorId;
        this.effectsId = effectsId;
    }

    public DashDynamicBakedModel(DynamicBakedModel model, RegistryWriter writer) {
        BakedModel[] models = model.getModels();
        int[] dModels = new int[models.length];
        for (int i = 0; i < models.length; i++) {
            dModels[i] = writer.add(models[i]);
        }

        this.models = dModels;
        this.selectorId = model.getSelector().id;
        this.effectsId = model.getEffects().id;
    }

    @Override
    public DynamicBakedModel export(RegistryReader reader) {
        BakedModel[] out = new BakedModel[models.length];
        for (int i = 0; i < models.length; i++) {
            out[i] = reader.get(models[i]);
        }

        return new DynamicBakedModel(out, ModelSelector.fromId(selectorId), DynamicModelEffects.fromId(effectsId));
    }
}
