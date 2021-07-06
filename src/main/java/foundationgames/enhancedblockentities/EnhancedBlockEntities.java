package foundationgames.enhancedblockentities;

import foundationgames.enhancedblockentities.client.model.ModelIdentifiers;
import foundationgames.enhancedblockentities.config.EBEConfig;
import foundationgames.enhancedblockentities.util.DateUtil;
import foundationgames.enhancedblockentities.util.ResourceUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class EnhancedBlockEntities implements ClientModInitializer {
    public static final Logger LOG = LogManager.getLogger("Enhanced Block Entities");

    public static final EBEConfig CONFIG = new EBEConfig();

    @Override
    public void onInitializeClient() {
        ModelIdentifiers.init();
        EBESetup.setupResourceProviders();
        FabricModelPredicateProviderRegistry.register(Items.CHEST, new Identifier("is_christmas"), (stack, world, entity, seed) -> DateUtil.isChristmas() ? 1 : 0);

        load();
    }

    public static void reload(ReloadType type) {
        load();
        if (type == ReloadType.WORLD) {
            MinecraftClient.getInstance().worldRenderer.reload();
        } else if (type == ReloadType.RESOURCES) {
            MinecraftClient.getInstance().reloadResources();
        }
    }

    public static void load() {
        CONFIG.load();

        EnhancedBlockEntityRegistry.clear();
        ResourceUtil.resetPack();

        if(CONFIG.renderEnhancedChests) {
            EBESetup.setupChests();
            EBESetup.setupRRPChests();
        }

        if(CONFIG.renderEnhancedSigns) {
            EBESetup.setupSigns();
            EBESetup.setupRRPSigns();
        }

        if(CONFIG.renderEnhancedBells) {
            EBESetup.setupBells();
            EBESetup.setupRRPBells();
        }

        if(CONFIG.renderEnhancedBeds) {
            EBESetup.setupBeds();
            EBESetup.setupRRPBeds();
        }
    }
}
