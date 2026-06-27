package foundationgames.enhancedblockentities.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public enum GuiUtil {;
    public static Component shorten(String text, final int maxLength, ChatFormatting ... formats) {
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            var word = words[i];
            line.append(word).append(" ");
            if (line.length() > maxLength) {
                if (i < words.length - 1) {
                    line.append("\n");
                }
                result.append(line);
                line = new StringBuilder();
            }
        }
        if (line.length() > 0) result.append(line);
        return Component.literal(result.toString()).formatted(formats);
    }
}
