package foundationgames.enhancedblockentities.config;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.util.ConvUtil;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class EBEConfig {
    public boolean renderEnhancedChests = true;
    public boolean useAO = false;
    public String christmasChests = "allowed";
    public boolean renderEnhancedSigns = true;

    public void writeTo(Properties properties) {
        properties.setProperty("render_enhanced_chests", Boolean.toString(renderEnhancedChests));
        properties.setProperty("use_ao", Boolean.toString(useAO));
        properties.setProperty("christmas_chests", christmasChests);
        properties.setProperty("render_enhanced_signs", Boolean.toString(renderEnhancedSigns));
    }

    public void readFrom(Properties properties) {
        this.renderEnhancedChests = ConvUtil.bool(properties.getProperty("render_enhanced_chests"));
        String pCC = properties.getProperty("christmas_chests");
        if(pCC != null && (pCC.equals("allowed") || pCC.equals("forced") || pCC.equals("disabled"))) {
            this.christmasChests = pCC;
        } else {
            EnhancedBlockEntities.LOG.warn("Configuration option 'christmas_chests' must be one of: 'allowed', 'forced', 'disabled'");
            this.christmasChests = "allowed";
        }
        this.useAO = ConvUtil.bool(properties.getProperty("use_ao"));
        this.renderEnhancedSigns = ConvUtil.bool(properties.getProperty("render_enhanced_signs"));
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
