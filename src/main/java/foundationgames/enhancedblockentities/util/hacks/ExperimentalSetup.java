package foundationgames.enhancedblockentities.util.hacks;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.client.resource.EBEPack;
import foundationgames.enhancedblockentities.config.EBEConfig;
import foundationgames.enhancedblockentities.util.ResourceUtil;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.DyeColor;

import java.io.IOException;

public enum ExperimentalSetup {;
    private static ResourceManager RESOURCES;

    public static void setup() {
        EBEConfig config = EnhancedBlockEntities.CONFIG;

        if (config.renderEnhancedChests && config.experimentalChests) {
            try {
                if (RESOURCES != null) setupChests(RESOURCES);
            } catch (IOException e) {
                EnhancedBlockEntities.LOG.error("Error loading experimental chests!", e);
                config.experimentalChests = false;
                config.save();
            }
        }
        if (config.renderEnhancedBeds && config.experimentalBeds) {
            try {
                if (RESOURCES != null) setupBeds(RESOURCES);
            } catch (IOException e) {
                EnhancedBlockEntities.LOG.error("Error loading experimental beds!", e);
                config.experimentalBeds = false;
                config.save();
            }
        }
        if (config.renderEnhancedSigns && config.experimentalSigns) {
            try {
                if (RESOURCES != null) setupSigns(RESOURCES);
            } catch (IOException e) {
                EnhancedBlockEntities.LOG.error("Error loading experimental signs!", e);
                config.experimentalSigns = false;
                config.save();
            }
        }
    }

    public static void setupChests(ResourceManager manager) throws IOException {
        EBEPack p = ResourceUtil.getTopLevelPack();

        ResourceHacks.addChestParticleTexture("chest", "entity/chest/normal", manager, p);
        ResourceHacks.addChestParticleTexture("trapped_chest", "entity/chest/trapped", manager, p);
        ResourceHacks.addChestParticleTexture("ender_chest", "entity/chest/ender", manager, p);
        ResourceHacks.addChestParticleTexture("christmas_chest", "entity/chest/christmas", manager, p);
    }

    public static void setupBeds(ResourceManager manager) throws IOException {
        EBEPack p = ResourceUtil.getTopLevelPack();

        for (var color : DyeColor.values()) {
            ResourceHacks.addBedParticleTexture(color.getName(), "entity/bed/"+color.getName(), manager, p);
        }
    }

    public static void setupSigns(ResourceManager manager) throws IOException {
        EBEPack p = ResourceUtil.getTopLevelPack();

        ResourceHacks.addSignParticleTexture("oak", "entity/signs/oak", manager, p);
        ResourceHacks.addSignParticleTexture("birch", "entity/signs/birch", manager, p);
        ResourceHacks.addSignParticleTexture("spruce", "entity/signs/spruce", manager, p);
        ResourceHacks.addSignParticleTexture("jungle", "entity/signs/jungle", manager, p);
        ResourceHacks.addSignParticleTexture("acacia", "entity/signs/acacia", manager, p);
        ResourceHacks.addSignParticleTexture("dark_oak", "entity/signs/dark_oak", manager, p);
        ResourceHacks.addSignParticleTexture("mangrove", "entity/signs/mangrove", manager, p);
        ResourceHacks.addSignParticleTexture("cherry", "entity/signs/cherry", manager, p);
        ResourceHacks.addSignParticleTexture("crimson", "entity/signs/crimson", manager, p);
        ResourceHacks.addSignParticleTexture("warped", "entity/signs/warped", manager, p);
        ResourceHacks.addSignParticleTexture("bamboo", "entity/signs/bamboo", manager, p);
    }

    public static void cacheResources(ResourceManager resources) {
        RESOURCES = resources;
    }
}
