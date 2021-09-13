package foundationgames.enhancedblockentities.mixin;

import com.google.common.collect.ImmutableList;
import foundationgames.enhancedblockentities.util.ResourceUtil;
import foundationgames.enhancedblockentities.util.hacks.ExperimentalSetup;
import net.minecraft.resource.*;
import net.minecraft.util.Unit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(ReloadableResourceManagerImpl.class)
public abstract class ReloadableResourceManagerImplMixin {
    @Shadow public abstract void addPack(ResourcePack resourcePack);

    @ModifyVariable(method = "reload", at = @At("HEAD"), index = 4)
    private List<ResourcePack> enhanced_bes$injectRRP(List<ResourcePack> old) {
        ImmutableList.Builder<ResourcePack> builder = ImmutableList.builder();
        for (ResourcePack pack : old) {
            builder.add(pack);
            if (pack instanceof DefaultResourcePack) {
                builder.add(ResourceUtil.getPack());
            }
        }

        return builder.build();
    }

    @Inject(method = "reload", at = @At(value = "RETURN", shift = At.Shift.BEFORE))
    private void enhanced_bes$injectAndSetupExperimentalPack(Executor prepareExecutor, Executor applyExecutor, CompletableFuture<Unit> initialStage, List<ResourcePack> packs, CallbackInfoReturnable<ResourceReload> cir) {
        ExperimentalSetup.cacheResources((ResourceManager) this);
        ExperimentalSetup.setup();
        this.addPack(ResourceUtil.getExperimentalPack());
    }
}
