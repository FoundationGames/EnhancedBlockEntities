package foundationgames.enhancedblockentities.config.gui.option;

import foundationgames.enhancedblockentities.ReloadType;
import foundationgames.enhancedblockentities.util.GuiUtil;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public final class EBEOption {
    public final String key;
    private final List<String> values;
    private final int defaultValue;
    private int selected;
    public final boolean hasValueComments;
    public final List<Text> commentLines;
    private List<Text> valueCommentLines = null;
    public final ReloadType reloadType;

    public EBEOption(String key, List<String> values, int defaultValue, boolean hasValueComments, ReloadType reloadType) {
        this.key = key;
        this.values = values;
        this.defaultValue = MathHelper.clamp(defaultValue, 0, values.size());
        this.selected = this.defaultValue;
        this.hasValueComments = hasValueComments;
        this.reloadType = reloadType;

        String comment = I18n.translate(String.format("option.ebe.%s.comment", key));
        commentLines = GuiUtil.shorten(comment, 20);
    }

    public String getValue() {
        return values.get(selected);
    }

    public String getValueKey() {
        var definition = I18n.translate(String.format("option.ebe.%s.value", key));

        if(this.values.get(0).equals("true") && this.values.get(1).equals("false")){
            if(getValue().equals("true")) {
                return I18n.translate("options.on.composed", definition);
            }
            else {
                return I18n.translate("options.off.composed", definition);
            }
        }
        else {
            var value = I18n.translate(String.format("option.ebe.%s.value.%s", key, getValue()));
            return I18n.translate("options.generic_value", definition, value);
        }

    }

    public List<Text> getValueCommentLines() {
        if (valueCommentLines == null) {
            valueCommentLines = GuiUtil.shorten(I18n.translate(String.format("option.ebe.%s.valueComment.%s", key, getValue())), 20, Formatting.YELLOW);
        }
        return valueCommentLines;
    }

    public void next() {
        selected++;
        if (selected >= values.size()) selected = 0;
        valueCommentLines = null;
    }

    public boolean isDefault() {
        return selected == defaultValue;
    }
}
