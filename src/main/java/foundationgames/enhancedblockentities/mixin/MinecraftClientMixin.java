package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.event.EBEEvents;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {
    @Inject(method = "reloadResourcePacks()Ljava/util/concurrent/CompletableFuture;",
            at = @At("RETURN"))
    private void enhanced_bes$fireReloadEvent(CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        cir.getReturnValue().thenAccept(v -> EBEEvents.RESOURCE_RELOAD.invoker().run());
    }
}
