package foundationgames.enhancedblockentities.util;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public enum GuiUtil {;
    public static Text shorten(String text, final int maxLength, Formatting ... formats) {
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
        return Text.literal(result.toString()).formatted(formats);
    }
}
