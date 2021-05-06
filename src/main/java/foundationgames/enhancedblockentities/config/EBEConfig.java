package foundationgames.enhancedblockentities.config;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.util.ConvUtil;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class EBEConfig {
    public static final String RENDER_ENHANCED_CHESTS_KEY = "render_enhanced_chests";
    public static final String RENDER_ENHANCED_SIGNS_KEY = "render_enhanced_signs";
    public static final String CHRISTMAS_CHESTS_KEY = "christmas_chests";
    public static final String CHEST_AO_KEY = "chest_ao";
    public static final String SIGN_AO_KEY = "sign_ao";

    public boolean renderEnhancedChests = true;
    public boolean chestAO = false;
    public boolean signAO = false;
    public String christmasChests = "allowed";
    public boolean renderEnhancedSigns = true;

    public void writeTo(Properties properties) {
        properties.setProperty(RENDER_ENHANCED_CHESTS_KEY, Boolean.toString(renderEnhancedChests));
        properties.setProperty(CHEST_AO_KEY, Boolean.toString(chestAO));
        properties.setProperty(SIGN_AO_KEY, Boolean.toString(signAO));
        properties.setProperty(CHRISTMAS_CHESTS_KEY, christmasChests);
        properties.setProperty(RENDER_ENHANCED_SIGNS_KEY, Boolean.toString(renderEnhancedSigns));
    }

    public void readFrom(Properties properties) {
        this.renderEnhancedChests = ConvUtil.bool(properties.getProperty(RENDER_ENHANCED_CHESTS_KEY));
        String pCC = properties.getProperty(CHRISTMAS_CHESTS_KEY);
        if(pCC != null && (pCC.equals("allowed") || pCC.equals("forced") || pCC.equals("disabled"))) {
            this.christmasChests = pCC;
        } else {
            EnhancedBlockEntities.LOG.warn("Configuration option 'christmas_chests' must be one of: 'allowed', 'forced', 'disabled'");
            this.christmasChests = "allowed";
        }
        this.chestAO = ConvUtil.bool(properties.getProperty(CHEST_AO_KEY));
        this.signAO = ConvUtil.bool(properties.getProperty(SIGN_AO_KEY));
        this.renderEnhancedSigns = ConvUtil.bool(properties.getProperty(RENDER_ENHANCED_SIGNS_KEY));
    }

    public void save() {
        Properties properties = new Properties();
        writeTo(properties);
        Path configPath = FabricLoader.getInstance().getConfigDir().resolve("enhanced_bes.properties");
        if(!Files.exists(configPath)) {
            try {
                Files.createFile(configPath);
            } catch (IOException e) {
                EnhancedBlockEntities.LOG.error("Failed to create configuration file!");
                e.printStackTrace();
                return;
            }
        }
        try {
            properties.store(Files.newOutputStream(configPath), "Configuration file for Enhanced Block Entities");
        } catch (IOException e) {
            EnhancedBlockEntities.LOG.error("Failed to write to configuration file!");
            e.printStackTrace();
        }
    }

    public void load() {
        Properties properties = new Properties();
        Path configPath = FabricLoader.getInstance().getConfigDir().resolve("enhanced_bes.properties");
        if(!Files.exists(configPath)) {
            try {
                Files.createFile(configPath);
                save();
            } catch (IOException e) {
                EnhancedBlockEntities.LOG.error("Failed to create configuration file!");
                e.printStackTrace();
                return;
            }
        }
        try {
            properties.load(Files.newInputStream(configPath));
        } catch (IOException e) {
            EnhancedBlockEntities.LOG.error("Failed to read configuration file!");
            e.printStackTrace();
            return;
        }
        readFrom(properties);
    }
}
