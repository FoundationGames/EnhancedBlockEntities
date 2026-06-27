package foundationgames.enhancedblockentities.client.model;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class DynamicUnbakedModel implements UnbakedModel {
    private final ResourceLocation[] models;
    private final ModelSelector selector;
    private final DynamicModelEffects effects;

    public DynamicUnbakedModel(ResourceLocation[] models, ModelSelector selector, DynamicModelEffects effects) {
        this.models = models;
        this.selector = selector;
        this.effects = effects;
    }

    @Override
    public void resolve(Resolver resolver) {
        for (ResourceLocation modelId : models) {
            if(modelId == null) continue;
            resolver.resolve(modelId);
        }
    }

    @Override
    public @Nullable BakedModel bake(ModelBaker baker, java.util.function.Function<Material, net.minecraft.client.renderer.texture.TextureAtlasSprite> spriteGetter, ModelState settings) {
        BakedModel[] baked = new BakedModel[models.length];
        for (int i = 0; i < models.length; i++) {
            baked[i] = baker.bake(models[i], settings);
        }
        return new DynamicBakedModel(baked, selector, effects);
    }
}
