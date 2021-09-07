package foundationgames.enhancedblockentities.util.hacks;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.client.resource.ExperimentalResourcePack;
import foundationgames.enhancedblockentities.config.EBEConfig;
import foundationgames.enhancedblockentities.util.ResourceUtil;
import net.minecraft.resource.ResourceManager;

import java.io.IOException;

public enum ExperimentalSetup {;
    private static ResourceManager RESOURCES;

    public static void setup() {
        EBEConfig config = EnhancedBlockEntities.CONFIG;
        ResourceUtil.resetExperimentalPack();

        if (config.renderEnhancedChests && config.experimentalChests) {
            try {
                if (RESOURCES != null) setupChests(RESOURCES);
            } catch (IOException e) {
                EnhancedBlockEntities.LOG.error("Error loading experimental chests!", e);
                config.experimentalChests = false;
                config.save();
            }
        }
    }

    public static void setupChests(ResourceManager manager) throws IOException {
        ExperimentalResourcePack p = ResourceUtil.getExperimentalPack();

        ResourceHacks.addChestParticleTexture("chest", "entity/chest/normal", manager, p);
        ResourceHacks.addChestParticleTexture("trapped_chest", "entity/chest/trapped", manager, p);
        ResourceHacks.addChestParticleTexture("ender_chest", "entity/chest/ender", manager, p);
        ResourceHacks.addChestParticleTexture("christmas_chest", "entity/chest/christmas", manager, p);
    }

    public static void cacheResources(ResourceManager resources) {
        RESOURCES = resources;
    }
}
