package foundationgames.enhancedblockentities.compat.dashloader;

import dev.quantumfusion.dashloader.core.api.DashDependencies;
import dev.quantumfusion.dashloader.core.api.DashObject;
import dev.quantumfusion.dashloader.core.registry.RegistryReader;
import dev.quantumfusion.dashloader.core.registry.RegistryWriter;
import dev.quantumfusion.dashloader.def.data.model.DashBasicBakedModel;
import dev.quantumfusion.dashloader.def.data.model.DashBuiltinBakedModel;
import dev.quantumfusion.dashloader.def.data.model.DashModel;
import dev.quantumfusion.dashloader.def.data.model.DashMultipartBakedModel;
import dev.quantumfusion.hyphen.scan.annotations.Data;
import foundationgames.enhancedblockentities.client.model.DynamicBakedModel;
import foundationgames.enhancedblockentities.client.model.DynamicModelEffects;
import foundationgames.enhancedblockentities.client.model.ModelSelector;
import net.minecraft.client.render.model.BakedModel;

@Data
@DashObject(DynamicBakedModel.class)
@DashDependencies({DashBasicBakedModel.class, DashBuiltinBakedModel.class, DashMultipartBakedModel.class})
public class DashDynamicBakedModel implements DashModel {
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
    public BakedModel export(RegistryReader reader) {
        BakedModel[] out = new BakedModel[models.length];
        for (int i = 0; i < models.length; i++) {
            out[i] = reader.get(models[i]);
        }

        return new DynamicBakedModel(out, ModelSelector.fromId(selectorId), DynamicModelEffects.fromId(effectsId));
    }
}
