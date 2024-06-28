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
    public static final String RENDER_ENHANCED_BELLS_KEY = "render_enhanced_bells";
    public static final String RENDER_ENHANCED_BEDS_KEY = "render_enhanced_beds";
    public static final String RENDER_ENHANCED_SHULKER_BOXES_KEY = "render_enhanced_shulker_boxes";
    public static final String RENDER_ENHANCED_DECORATED_POTS_KEY = "render_enhanced_decorated_pots";
    public static final String CHEST_AO_KEY = "chest_ao";
    public static final String SIGN_AO_KEY = "sign_ao";
    public static final String BELL_AO_KEY = "bell_ao";
    public static final String BED_AO_KEY = "bed_ao";
    public static final String SHULKER_BOX_AO_KEY = "shulker_box_ao";
    public static final String DECORATED_POT_AO_KEY = "decorated_pot_ao";
    public static final String CHRISTMAS_CHESTS_KEY = "christmas_chests";
    public static final String SIGN_TEXT_RENDERING_KEY = "sign_text_rendering";
    public static final String EXPERIMENTAL_CHESTS_KEY = "experimental_chests";
    public static final String EXPERIMENTAL_BEDS_KEY = "experimental_beds";
    public static final String EXPERIMENTAL_SIGNS_KEY = "experimental_signs";
    public static final String FORCE_RESOURCE_PACK_COMPAT_KEY = "force_resource_pack_compat";

    public boolean renderEnhancedChests = true;
    public boolean renderEnhancedSigns = true;
    public boolean renderEnhancedBells = true;
    public boolean renderEnhancedBeds = true;
    public boolean renderEnhancedShulkerBoxes = true;
    public boolean renderEnhancedDecoratedPots = true;
    public boolean chestAO = false;
    public boolean signAO = false;
    public boolean bellAO = true;
    public boolean bedAO = false;
    public boolean shulkerBoxAO = false;
    public boolean decoratedPotAO = false;
    public String christmasChests = "allowed";
    public String signTextRendering = "smart";
    public boolean experimentalChests = true;
    public boolean experimentalBeds = true;
    public boolean experimentalSigns = true;
    public boolean forceResourcePackCompat = false;

    public void writeTo(Properties properties) {
        properties.setProperty(RENDER_ENHANCED_CHESTS_KEY, Boolean.toString(renderEnhancedChests));
        properties.setProperty(RENDER_ENHANCED_SIGNS_KEY, Boolean.toString(renderEnhancedSigns));
        properties.setProperty(RENDER_ENHANCED_BELLS_KEY, Boolean.toString(renderEnhancedBells));
        properties.setProperty(RENDER_ENHANCED_BEDS_KEY, Boolean.toString(renderEnhancedBeds));
        properties.setProperty(RENDER_ENHANCED_SHULKER_BOXES_KEY, Boolean.toString(renderEnhancedShulkerBoxes));
        properties.setProperty(RENDER_ENHANCED_DECORATED_POTS_KEY, Boolean.toString(renderEnhancedDecoratedPots));
        properties.setProperty(CHEST_AO_KEY, Boolean.toString(chestAO));
        properties.setProperty(SIGN_AO_KEY, Boolean.toString(signAO));
        properties.setProperty(BELL_AO_KEY, Boolean.toString(bellAO));
        properties.setProperty(BED_AO_KEY, Boolean.toString(bedAO));
        properties.setProperty(SHULKER_BOX_AO_KEY, Boolean.toString(shulkerBoxAO));
        properties.setProperty(DECORATED_POT_AO_KEY, Boolean.toString(decoratedPotAO));
        properties.setProperty(CHRISTMAS_CHESTS_KEY, christmasChests);
        properties.setProperty(SIGN_TEXT_RENDERING_KEY, signTextRendering);
        properties.setProperty(EXPERIMENTAL_CHESTS_KEY, Boolean.toString(experimentalChests));
        properties.setProperty(EXPERIMENTAL_BEDS_KEY, Boolean.toString(experimentalBeds));
        properties.setProperty(EXPERIMENTAL_SIGNS_KEY, Boolean.toString(experimentalSigns));
        properties.setProperty(FORCE_RESOURCE_PACK_COMPAT_KEY, Boolean.toString(forceResourcePackCompat));
    }

    public void readFrom(Properties properties) {
        this.renderEnhancedChests = ConvUtil.defaultedBool(properties.getProperty(RENDER_ENHANCED_CHESTS_KEY), true);
        this.renderEnhancedSigns = ConvUtil.defaultedBool(properties.getProperty(RENDER_ENHANCED_SIGNS_KEY), true);
        this.renderEnhancedBells = ConvUtil.defaultedBool(properties.getProperty(RENDER_ENHANCED_BELLS_KEY), true);
        this.renderEnhancedBeds = ConvUtil.defaultedBool(properties.getProperty(RENDER_ENHANCED_BEDS_KEY), true);
        this.renderEnhancedShulkerBoxes = ConvUtil.defaultedBool(properties.getProperty(RENDER_ENHANCED_SHULKER_BOXES_KEY), true);
        this.renderEnhancedDecoratedPots = ConvUtil.defaultedBool(properties.getProperty(RENDER_ENHANCED_DECORATED_POTS_KEY), true);
        String pCC = properties.getProperty(CHRISTMAS_CHESTS_KEY);
        if (pCC != null && (pCC.equals("allowed") || pCC.equals("forced") || pCC.equals("disabled"))) {
            this.christmasChests = pCC;
        } else {
            EnhancedBlockEntities.LOG.warn("Configuration option 'christmas_chests' must be one of: 'allowed', 'forced', 'disabled'");
            this.christmasChests = "allowed";
        }
        String sST = properties.getProperty(SIGN_TEXT_RENDERING_KEY);
        if (sST != null && (sST.equals("smart") || sST.equals("all") || sST.equals("most") || sST.equals("some") || sST.equals("few"))) {
            this.signTextRendering = sST;
        } else {
            EnhancedBlockEntities.LOG.warn("Configuration option 'sign_text_rendering' must be one of: 'smart', 'all', 'most', 'some', 'few'");
            this.signTextRendering = "smart";
        }
        this.chestAO = ConvUtil.defaultedBool(properties.getProperty(CHEST_AO_KEY), false);
        this.signAO = ConvUtil.defaultedBool(properties.getProperty(SIGN_AO_KEY), false);
        this.bellAO = ConvUtil.defaultedBool(properties.getProperty(BELL_AO_KEY), true);
        this.bedAO = ConvUtil.defaultedBool(properties.getProperty(BED_AO_KEY), false);
        this.shulkerBoxAO = ConvUtil.defaultedBool(properties.getProperty(SHULKER_BOX_AO_KEY), false);
        this.decoratedPotAO = ConvUtil.defaultedBool(properties.getProperty(DECORATED_POT_AO_KEY), false);
        this.experimentalChests = ConvUtil.defaultedBool(properties.getProperty(EXPERIMENTAL_CHESTS_KEY), true);
        this.experimentalBeds = ConvUtil.defaultedBool(properties.getProperty(EXPERIMENTAL_BEDS_KEY), true);
        this.experimentalSigns = ConvUtil.defaultedBool(properties.getProperty(EXPERIMENTAL_SIGNS_KEY), true);
        this.forceResourcePackCompat = ConvUtil.defaultedBool(properties.getProperty(FORCE_RESOURCE_PACK_COMPAT_KEY), false);
    }

    public void save() {
        Properties properties = new Properties();
        writeTo(properties);
        Path configPath = FabricLoader.getInstance().getConfigDir().resolve("enhanced_bes.properties");
        if (!Files.exists(configPath)) {
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
        if (!Files.exists(configPath)) {
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
