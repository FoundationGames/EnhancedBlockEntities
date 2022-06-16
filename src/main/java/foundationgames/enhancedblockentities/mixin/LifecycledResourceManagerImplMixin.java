package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.util.ResourceUtil;
import foundationgames.enhancedblockentities.util.hacks.ExperimentalSetup;
import net.fabricmc.fabric.api.resource.ModResourcePack;
import net.minecraft.resource.DefaultResourcePack;
import net.minecraft.resource.LifecycledResourceManagerImpl;
import net.minecraft.resource.NamespaceResourceManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(LifecycledResourceManagerImpl.class)
public abstract class LifecycledResourceManagerImplMixin {
    @Shadow @Final          private Map<String, NamespaceResourceManager> subManagers;

    @ModifyVariable(method = "<init>", at = @At(value = "INVOKE_ASSIGN", target = "Ljava/util/List;copyOf(Ljava/util/Collection;)Ljava/util/List;", shift = At.Shift.BEFORE), ordinal = 0)
    private List<ResourcePack> enhanced_bes$injectRRP(List<ResourcePack> old) {
        var packs = new ArrayList<>(old);

        int idx = 0;
        if (packs.size() > 0) do {
            idx++;
        } while (idx < packs.size() && !(packs.get(idx - 1) instanceof DefaultResourcePack));
        packs.add(idx, ResourceUtil.getPack());

        return packs;
    }

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void enhanced_bes$injectExperimentalPack(ResourceType type, List<ResourcePack> packs, CallbackInfo ci) {
        ExperimentalSetup.cacheResources((ResourceManager) this);
        ExperimentalSetup.setup();

        addPack(type, ResourceUtil.getExperimentalPack());
    }

    private void addPack(ResourceType type, ResourcePack pack) {
        for (var namespace : pack.getNamespaces(type)) {
            this.subManagers.computeIfAbsent(namespace, n -> new NamespaceResourceManager(type, n)).addPack(pack);
        }
    }
}
