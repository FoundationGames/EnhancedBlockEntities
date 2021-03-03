package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.client.render.SignRenderManager;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "render", at = @At("TAIL"))
    public void onEndRender(boolean tick, CallbackInfo ci) {
        SignRenderManager.endFrame();
    }
}
