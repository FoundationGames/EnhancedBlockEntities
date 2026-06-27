package foundationgames.enhancedblockentities.config.gui.option;

import foundationgames.enhancedblockentities.ReloadType;
import foundationgames.enhancedblockentities.config.EBEConfig;
import foundationgames.enhancedblockentities.util.GuiUtil;
import net.minecraft.ChatChatFormatting;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public final class EBEOption {
    private static final Component NEWLINE = Component.of("\n");
    private static final String OPTION_VALUE = "options.generic_value";
    private static final String DIVIDER = "text.ebe.option_value_division";
    private static final String OVERRIDDEN = "warning.ebe.overridden";

    public final String key;
    public final boolean hasValueComments;
    public final Component comment;
    public final ReloadType reloadType;
    public final ComponentPalette palette;
    public final @Nullable EBEConfig.Override override;

    private final List<String> values;
    private final int defaultValue;

    private int selected;
    private Tooltip tooltip = null;
    private Component text = null;

    public EBEOption(String key, List<String> values, ConfigView config, boolean hasValueComments, ComponentPalette palette, ReloadType reloadType) {
        this.key = key;
        this.values = values;
        this.defaultValue = Mth.clamp(values.indexOf(config.configValues.getProperty(key)), 0, values.size());
        this.override = config.overrides.get(key);
        this.selected = this.defaultValue;
        this.hasValueComments = hasValueComments;
        this.palette = palette;
        this.reloadType = reloadType;

        String commentKey = I18n.translate(String.format("option.ebe.%s.comment", key));
        comment = GuiUtil.shorten(commentKey, 20);
    }

    public String getValue() {
        return values.get(selected);
    }

    public String getOptionKey() {
        return String.format("option.ebe.%s", key);
    }

    public String getValueKey() {
        return String.format("value.ebe.%s", getValue());
    }

    public Component getComponent() {
        var option = Component.translatable(this.getOptionKey()).styled(style -> style.withColor(isDefault() ? 0xFFFFFF : 0xFFDA5E));
        var value = Component.translatable(this.getValueKey()).styled(style -> style.withColor(this.palette.getColor((float)this.selected / this.values.size())));

        if (text == null) text = option.append(Component.translatable(DIVIDER).append(value));
        return text;
    }

    public Tooltip getTooltip() {
        if (tooltip == null) {
            if (override != null) {
                var text = Component.translatable(OVERRIDDEN, override.modResponsible().getMetadata().getId())
                        .formatted(ChatFormatting.RED, ChatFormatting.UNDERLINE);
                if (override.reason() != null) {
                    text.append(NEWLINE).append(override.reason());
                }

                tooltip = Tooltip.of(text);
            }
            else if (hasValueComments) tooltip = Tooltip.of(Component.translatable(String.format("option.ebe.%s.valueComment.%s", key, getValue())).append(NEWLINE).append(comment.copyContentOnly()));
            else tooltip = Tooltip.of(comment.copyContentOnly());
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

    public record ConfigView(Properties configValues, Map<String, EBEConfig.Override> overrides) {}
}
