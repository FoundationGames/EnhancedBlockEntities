package foundationgames.enhancedblockentities.mixin;

import com.google.common.collect.ImmutableList;
import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class EBEMixinPlugin implements IMixinConfigPlugin {
    public static final List<String> OPTIFINE_INCOMPATIBLE_MIXINS = ImmutableList.of(
            "foundationgames.enhancedblockentities.mixin.VideoOptionsScreenMixin"
    );

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (FabricLoader.getInstance().isModLoaded("optifabric")) {
            EnhancedBlockEntities.LOG.warn("OptiFabric is loaded, some features will be disabled");
            return !OPTIFINE_INCOMPATIBLE_MIXINS.contains(mixinClassName);
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
