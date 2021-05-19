package foundationgames.enhancedblockentities.util.hacks;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.config.EBEConfig;
import foundationgames.enhancedblockentities.util.ResourceUtil;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.minecraft.resource.ResourcePack;

import java.io.IOException;
import java.util.List;

public enum ExperimentalSetup {;
    public static void setup(List<ResourcePack> packs) {
        EBEConfig config = EnhancedBlockEntities.CONFIG;
        ResourceUtil.resetExperimentalPack();

        if (config.renderEnhancedChests && config.experimentalChests) {
            try {
                setupChests(packs);
            } catch (IOException e) {
                EnhancedBlockEntities.LOG.error("Error loading experimental chests!", e);
                config.experimentalChests = false;
                config.save();
            }
        }
    }

    public static void setupChests(List<ResourcePack> packs) throws IOException {
        RuntimeResourcePack p = ResourceUtil.getExperimentalPack();

        ResourceHacks.addChestParticleTexture("chest", "entity/chest/normal", packs, p);
        ResourceHacks.addChestParticleTexture("trapped_chest", "entity/chest/trapped", packs, p);
        ResourceHacks.addChestParticleTexture("ender_chest", "entity/chest/ender", packs, p);
        ResourceHacks.addChestParticleTexture("christmas_chest", "entity/chest/christmas", packs, p);
    }
}
