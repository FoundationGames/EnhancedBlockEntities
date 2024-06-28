package foundationgames.enhancedblockentities.util;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;

import java.util.Calendar;

public enum DateUtil {;
    public static boolean isChristmas() {
        String config = EnhancedBlockEntities.CONFIG.christmasChests;
        if (config.equals("disabled")) return false;
        if (config.equals("forced")) return true;
        Calendar calendar = Calendar.getInstance();
        return (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26);
    }
}
