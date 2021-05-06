package foundationgames.enhancedblockentities.util;

import com.google.common.collect.ImmutableList;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public enum GuiUtil {;
    public static List<Text> shorten(String text, int maxLength, Formatting ... formats) {
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        int lineLength = 0;
        List<Text> lines = new ArrayList<>();
        for (String word : words) {
            line.append(word).append(" ");
            lineLength += (word.length() + 1);
            if (lineLength > maxLength) {
                lines.add(new LiteralText(line.toString()).formatted(formats));
                line = new StringBuilder();
                lineLength = 0;
            }
        }
        if (lineLength > 0) {
            lines.add(new LiteralText(line.toString()).formatted(formats));
        }
        return ImmutableList.copyOf(lines);
    }
}
