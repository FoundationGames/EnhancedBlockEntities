package foundationgames.enhancedblockentities.util;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import net.minecraft.client.renderer.blockentity.ChestRenderer;

public enum DateUtil {;
    public static boolean isChristmas() {
        String config = EnhancedBlockEntities.CONFIG.christmasChests;
        if (config.equals("disabled")) return false;
        if (config.equals("forced")) return true;
        return ChestRenderer.isChristmas();
    }
}
