package foundationgames.enhancedblockentities.mixin;

import com.google.common.collect.ImmutableList;
import foundationgames.enhancedblockentities.util.ResourceUtil;
import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.resource.ResourcePack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(ReloadableResourceManagerImpl.class)
public abstract class ReloadableResourceManagerImplMixin {
    @Shadow public abstract void addPack(ResourcePack resourcePack);

    /**
     * Why does this exist, you ask?
     *
     * Because ARRP's callback for some ungodly reason adds RRPs BEFORE VANILLA RESOURCES
     * Meaning that all the blockstate files get OVERWRITTEN BY VANILLA
     */

    @ModifyVariable(method = "beginMonitoredReload", at = @At("HEAD"), index = 4)
    private List<ResourcePack> enhanced_bes$enhanced_bes$injectRRP(List<ResourcePack> old) {
        ImmutableList.Builder<ResourcePack> builder = new ImmutableList.Builder<>();
        builder.add(old.get(0));
        builder.add(ResourceUtil.getPack());
        for (int i = 1; i < old.size(); i++) {
            builder.add(old.get(i));
        }
        return builder.build();
    }
}
