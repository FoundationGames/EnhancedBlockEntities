package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.event.EBEEvents;
import foundationgames.enhancedblockentities.util.duck.BakedModelManagerAccess;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(BakedModelManager.class)
public class BakedModelManagerMixin implements BakedModelManagerAccess {

    @Shadow private Map<Identifier, BakedModel> models;

    @Override
    public BakedModel getModel(Identifier id) {
        return this.models.get(id);
    }

    @Inject(method = "apply", at = @At("TAIL"))
    public void invokeReloadEvent(ModelLoader modelLoader, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci) {
        EBEEvents.RELOAD_MODELS.invoker().onReload(modelLoader, resourceManager, profiler);
    }
}
