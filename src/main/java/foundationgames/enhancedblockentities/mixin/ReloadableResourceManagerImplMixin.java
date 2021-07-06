package foundationgames.enhancedblockentities.mixin;

import com.google.common.collect.ImmutableList;
import foundationgames.enhancedblockentities.util.ResourceUtil;
import foundationgames.enhancedblockentities.util.hacks.ExperimentalSetup;
import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceReloadMonitor;
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

    @ModifyVariable(method = "beginMonitoredReload", at = @At("HEAD"), index = 4)
    private List<ResourcePack> enhanced_bes$injectRRP(List<ResourcePack> old) {
        ImmutableList.Builder<ResourcePack> builder = ImmutableList.builder();
        builder.add(old.get(0));
        builder.add(ResourceUtil.getPack());
        for (int i = 1; i < old.size(); i++) {
            builder.add(old.get(i));
        }

        return builder.build();
    }

    @Inject(method = "beginMonitoredReload", at = @At(value = "RETURN", shift = At.Shift.BEFORE))
    private void enhanced_bes$injectAndSetupExperimentalPack(Executor prepareExecutor, Executor applyExecutor, CompletableFuture<Unit> initialStage, List<ResourcePack> packs, CallbackInfoReturnable<ResourceReloadMonitor> cir) {
        ExperimentalSetup.cacheResources((ResourceManager) this);
        ExperimentalSetup.setup();
        this.addPack(ResourceUtil.getExperimentalPack());
    }
}
