package foundationgames.enhancedblockentities.config.gui.option;

import java.util.List;

public final class EBEOption {
    public final String key;
    private final List<String> values;
    private final int defaultValue;
    private int selected;
    public final boolean hasValueComments;

    public EBEOption(String key, List<String> values, int defaultValue, boolean hasValueComments) {
        this.key = key;
        this.values = values;
        this.defaultValue = defaultValue;
        this.selected = defaultValue;
        this.hasValueComments = hasValueComments;
    }

    public String getValue() {
        return values.get(selected);
    }

    public String getValueKey() {
        return String.format("option.ebe.%s.value.%s", key, getValue());
    }

    public String getCommentKey() {
        return String.format("option.ebe.%s.comment", key);
    }

    public String getValueCommentKey() {
        return String.format("option.ebe.%s.valueComment.%s", key, getValue());
    }

    public void next() {
        selected++;
        if (selected >= values.size()) selected = 0;
    }

    public boolean isDefault() {
        return selected == defaultValue;
    }
}
