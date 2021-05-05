package foundationgames.enhancedblockentities.mixin;

import com.mojang.datafixers.util.Unit;
import foundationgames.enhancedblockentities.util.ResourceUtil;
import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceReloadMonitor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(ReloadableResourceManagerImpl.class)
public abstract class ReloadableResourceManagerImplMixin {
    @Shadow public abstract void addPack(ResourcePack resourcePack);

    /**
     * Why does this exist, you ask?
     *
     * Because ARRP's callback for some ungodly reason adds RRPs BEFORE VANILLA RESOURCES
     * Meaning that all the blockstate files get OVERWRITTEN BY VANILLA
     */

    @Inject(method = "beginMonitoredReload", at = @At(value = "RETURN", shift = At.Shift.BEFORE))
    private void registerARRPs(Executor prepareExecutor, Executor applyExecutor, CompletableFuture<Unit> initialStage, List<ResourcePack> packs, CallbackInfoReturnable<ResourceReloadMonitor> cir) {
        this.addPack(ResourceUtil.getPack());
    }
}
