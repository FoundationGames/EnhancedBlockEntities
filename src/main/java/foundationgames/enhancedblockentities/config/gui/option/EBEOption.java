package foundationgames.enhancedblockentities.config.gui.option;

import foundationgames.enhancedblockentities.ReloadType;
import foundationgames.enhancedblockentities.util.GuiUtil;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public final class EBEOption {
    private static final Text NEWLINE = Text.of("\n");

    public final String key;
    private final List<String> values;
    private final int defaultValue;
    private int selected;
    public final boolean hasValueComments;
    public final Text comment;
    private MutableText tooltip = null;
    private Text text = null;
    public final ReloadType reloadType;

    public EBEOption(String key, List<String> values, int defaultValue, boolean hasValueComments, ReloadType reloadType) {
        this.key = key;
        this.values = values;
        this.defaultValue = MathHelper.clamp(defaultValue, 0, values.size());
        this.selected = this.defaultValue;
        this.hasValueComments = hasValueComments;
        this.reloadType = reloadType;

        String commentKey = I18n.translate(String.format("option.ebe.%s.comment", key));
        comment = GuiUtil.shorten(commentKey, 20);
    }

    public String getValue() {
        return values.get(selected);
    }

    public String getValueKey() {
        return String.format("option.ebe.%s.value.%s", key, getValue());
    }

    public Text getText() {
        if (text == null) text = new TranslatableText(getValueKey()).styled(style -> style.withColor(isDefault() ? 0xFFFFFF : 0xFFDA5E));
        return text;
    }

    public MutableText getTooltip() {
        if (tooltip == null) {
            if (hasValueComments) tooltip = new TranslatableText(String.format("option.ebe.%s.valueComment.%s", key, getValue())).append(NEWLINE).append(comment.copy());
            else tooltip = comment.copy();
        }
        return tooltip;
    }

    public void next() {
        selected++;
        if (selected >= values.size()) selected = 0;
        tooltip = null;
        text = null;
    }

    public boolean isDefault() {
        return selected == defaultValue;
    }
}
