package foundationgames.enhancedblockentities.client.model;

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelResolver;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class DynamicModelProvidingPlugin implements ModelLoadingPlugin, ModelResolver {
    private final Supplier<DynamicUnbakedModel> model;
    private final Identifier id;

    public DynamicModelProvidingPlugin(Identifier id, Supplier<DynamicUnbakedModel> model) {
        this.model = model;
        this.id = id;
    }

    @Override
    public void onInitializeModelLoader(ModelLoadingPlugin.Context ctx) {
        ctx.resolveModel().register(this);
    }

    @Override
    public @Nullable UnbakedModel resolveModel(ModelResolver.Context ctx) {
        if (ctx.id().equals(this.id)) return this.model.get();
        return null;
    }
}
