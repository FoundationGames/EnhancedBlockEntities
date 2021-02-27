package foundationgames.enhancedblockentities.client.model;

import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class BasicModelProvider implements ModelResourceProvider {
    private final Identifier id;
    private final ResourceManager manager;

    public BasicModelProvider(Identifier id, ResourceManager manager) {
        this.id = id;
        this.manager = manager;
    }

    @Override
    public @Nullable UnbakedModel loadModelResource(Identifier identifier, ModelProviderContext context) {
        return identifier.equals(id) ? context.loadModel(id) : null;
    }
}
