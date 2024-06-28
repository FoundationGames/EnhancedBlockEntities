package foundationgames.enhancedblockentities.util;

public enum ConvUtil {;
    public static boolean bool(String bool) {
        return bool != null && bool.equalsIgnoreCase("true");
    }

    public static boolean defaultedBool(String bool, boolean defaultVal) {
        return bool == null ? defaultVal : bool(bool);
    }
}
