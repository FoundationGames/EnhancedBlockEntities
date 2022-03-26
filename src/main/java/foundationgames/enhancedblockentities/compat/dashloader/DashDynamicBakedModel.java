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
    public final Selector selector;
    public final DynamicModelEffect effects;

    public DashDynamicBakedModel(int[] models, Selector selector, DynamicModelEffect effects) {
        this.models = models;
        this.selector = selector;
        this.effects = effects;
    }

    public DashDynamicBakedModel(DynamicBakedModel model, RegistryWriter writer) {
        BakedModel[] models = model.getModels();
        int[] dModels = new int[models.length];
        for (int i = 0; i < models.length; i++) {
            dModels[i] = writer.add(models[i]);
        }

        this.models = dModels;
        this.selector = Selector.fromSelector(model.getSelector());
        this.effects = DynamicModelEffect.fromEffect(model.getEffects());
    }

    @Override
    public BakedModel export(RegistryReader reader) {
        BakedModel[] out = new BakedModel[models.length];
        for (int i = 0; i < models.length; i++) {
            out[i] = reader.get(models[i]);
        }

        return new DynamicBakedModel(out, selector.getSelector(), effects.getModelEffect());
    }


    @Data
    public enum Selector {
        STATE_HOLDER_SELECTOR,
        CHEST,
        CHEST_WITH_CHRISTMAS,
        BELL,
        SHULKER_BOX;

        public static Selector fromSelector(ModelSelector selector) {
            if (ModelSelector.STATE_HOLDER_SELECTOR.equals(selector)) {
                return STATE_HOLDER_SELECTOR;
            } else if (ModelSelector.CHEST.equals(selector)) {
                return CHEST;
            } else if (ModelSelector.CHEST_WITH_CHRISTMAS.equals(selector)) {
                return CHEST_WITH_CHRISTMAS;
            } else if (ModelSelector.BELL.equals(selector)) {
                return BELL;
            } else if (ModelSelector.SHULKER_BOX.equals(selector)) {
                return SHULKER_BOX;
            }
            throw new IllegalArgumentException();
        }

        public ModelSelector getSelector() {
            return switch (this) {
                case STATE_HOLDER_SELECTOR -> ModelSelector.STATE_HOLDER_SELECTOR;
                case CHEST -> ModelSelector.CHEST;
                case CHEST_WITH_CHRISTMAS -> ModelSelector.CHEST_WITH_CHRISTMAS;
                case BELL -> ModelSelector.BELL;
                case SHULKER_BOX -> ModelSelector.SHULKER_BOX;
            };
        }
    }

    @Data
    public enum DynamicModelEffect {
        DEFAULT,
        CHEST,
        BELL,
        SHULKER_BOX;

        public static DynamicModelEffect fromEffect(DynamicModelEffects selector) {
            if (DynamicModelEffects.DEFAULT.equals(selector)) {
                return DEFAULT;
            } else if (DynamicModelEffects.CHEST.equals(selector)) {
                return CHEST;
            } else if (DynamicModelEffects.BELL.equals(selector)) {
                return BELL;
            } else if (DynamicModelEffects.SHULKER_BOX.equals(selector)) {
                return SHULKER_BOX;
            }
            throw new IllegalArgumentException();
        }

        public DynamicModelEffects getModelEffect() {
            return switch (this) {
                case DEFAULT -> DynamicModelEffects.DEFAULT;
                case CHEST -> DynamicModelEffects.CHEST;
                case BELL -> DynamicModelEffects.BELL;
                case SHULKER_BOX -> DynamicModelEffects.SHULKER_BOX;
            };
        }
    }
}
