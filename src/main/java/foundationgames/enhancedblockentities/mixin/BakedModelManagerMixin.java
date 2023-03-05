package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.event.EBEEvents;
import foundationgames.enhancedblockentities.util.duck.BakedModelManagerAccess;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(BakedModelManager.class)
public class BakedModelManagerMixin implements BakedModelManagerAccess {
    @Shadow private Map<Identifier, BakedModel> models;

    @Override
    public BakedModel getModel(Identifier id) {
        return this.models.get(id);
    }

    // Should invoke the EBE event after the model manager's block model cache is filled during a resource reload
    @Inject(method = "reload", at = @At("TAIL"))
    public void enhanced_bes$invokeReloadEvent(ResourceReloader.Synchronizer synchronizer, ResourceManager manager,
                                               Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor,
                                               Executor applyExecutor, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        cir.getReturnValue().thenAccept(obj -> {
            var result = (BakedModelManager.BakingResult)(Object)obj;
            EBEEvents.RELOAD_MODELS.invoker().onReload(result.modelLoader(), manager, applyProfiler);
        });
    }
}
