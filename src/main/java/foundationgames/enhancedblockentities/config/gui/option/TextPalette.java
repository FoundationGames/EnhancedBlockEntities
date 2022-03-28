package foundationgames.enhancedblockentities.config.gui.option;

public interface TextPalette {
    TextPalette ON_OFF = opt -> (opt < 0.5f) ? 0x74ff45 : 0xff6745;

    /**
     * Return a color based on the amount the option has been cycled through
     *
     * @param cycle a value 0-1 representing the progress cycling this palette
     * @return a RGB color integer
     */
    int getColor(float cycle);

    static TextPalette rainbow(float start) {
        return opt -> {
            opt += start;

            float r = 0.5f * (float)Math.cos(2 * Math.PI * opt) + 0.5f;
            float g = 0.5f * (float)Math.cos(2 * Math.PI * (opt - 0.333333333f)) + 0.5f;
            float b = 0.5f * (float)Math.cos(2 * Math.PI * (opt - 0.666666666f)) + 0.5f;

            return ((int)(r * 255) << 16) | ((int)(g * 255) << 8) | (int)(b * 255);
        };
    }
}
