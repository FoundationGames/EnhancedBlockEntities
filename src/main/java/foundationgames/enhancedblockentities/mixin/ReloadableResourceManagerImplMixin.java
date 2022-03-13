package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.util.ResourceUtil;
import foundationgames.enhancedblockentities.util.hacks.ExperimentalSetup;
import net.minecraft.resource.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.ArrayList;
import java.util.List;

@Mixin(ReloadableResourceManagerImpl.class)
public abstract class ReloadableResourceManagerImplMixin {

    @ModifyArg(
            method = "reload",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/LifecycledResourceManagerImpl;<init>(Lnet/minecraft/resource/ResourceType;Ljava/util/List;)V"),
            index = 1
    )
    private List<ResourcePack> enhanced_bes$injectRRP(List<ResourcePack> packs) {
        List<ResourcePack> list = new ArrayList<>(packs);

        ExperimentalSetup.cacheResources((ResourceManager) this);
        ExperimentalSetup.setup();

        list.addAll(packs);

        list.add(ResourceUtil.getPack());
        list.add(ResourceUtil.getExperimentalPack());

        return list;
    }
}
