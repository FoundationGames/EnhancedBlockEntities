package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.event.EBEEvents;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "reloadResources()Ljava/util/concurrent/CompletableFuture;",
            at = @At("RETURN"))
    private void enhanced_bes$fireReloadEvent(CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        cir.getReturnValue().thenAccept(v -> EBEEvents.RESOURCE_RELOAD.invoker().run());
    }
}
