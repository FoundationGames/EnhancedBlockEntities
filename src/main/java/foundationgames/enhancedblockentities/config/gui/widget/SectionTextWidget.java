package foundationgames.enhancedblockentities.config.gui.widget;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.AbstractTextWidget;
import net.minecraft.text.Text;

public class SectionTextWidget extends AbstractTextWidget {
    public SectionTextWidget(Text message, TextRenderer textRenderer) {
        this(0, 0, 200, 20, message, textRenderer);
    }

    public SectionTextWidget(int x, int y, int width, int height, Text message, TextRenderer textRenderer) {
        super(x, y, width, height, message, textRenderer);
        this.active = false;
    }

    @Override
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        final int white = 0xFFFFFFFF;
        var font = this.getTextRenderer();
        var msg = this.getMessage();

        int l = this.getX();
        int w = this.getWidth();
        int r = l + w;
        int y = (this.getY() + this.getHeight()) - 6;

        int tx = l + (w / 2);
        int ty = y - (font.fontHeight / 2);
        int tw = font.getWidth(msg);

        int ml = l + ((w - tw) / 2) - 5;
        int mr = ml + tw + 10;

        l += 1;
        r -= 1;

        context.fill(l, y, ml, y + 2, white);
        context.fill(mr, y, r, y + 2, white);

        context.drawCenteredTextWithShadow(font, msg, tx, ty, 0xFFFFFF);
    }
}
