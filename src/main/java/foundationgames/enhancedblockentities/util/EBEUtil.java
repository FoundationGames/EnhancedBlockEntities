package foundationgames.enhancedblockentities.util;

import net.minecraft.util.DyeColor;

public enum EBEUtil {;
    // Contains all dye colors, and null
    public static final DyeColor[] DEFAULTED_DYE_COLORS;

    static {
        var dColors = DyeColor.values();
        DEFAULTED_DYE_COLORS = new DyeColor[dColors.length + 1];
        System.arraycopy(dColors, 0, DEFAULTED_DYE_COLORS, 0, dColors.length);
    }


}
