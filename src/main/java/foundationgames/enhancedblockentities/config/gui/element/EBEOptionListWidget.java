package foundationgames.enhancedblockentities.config.gui.element;

import com.mojang.blaze3d.systems.RenderSystem;
import foundationgames.enhancedblockentities.config.gui.option.EBEOption;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class EBEOptionListWidget extends ElementListWidget<EBEOptionListWidget.EBEOptionEntry> {
    private static final int WIDTH = 270;

    public EBEOptionListWidget(MinecraftClient minecraftClient, int width, int height, int top, int bottom, int itemHeight) {
        super(minecraftClient, width, height, top, bottom, itemHeight);
    }

    @Override
    public int getRowWidth() {
        return WIDTH;
    }

    public void add(EBEOption ... options) {
        for (EBEOption o : options) {
            this.addEntry(new EBEOptionEntry(o));
        }
    }

    public void forEach(Consumer<EBEOption> action) {
        for (int i = 0; i < getEntryCount(); i++) {
            action.accept(getEntry(i).option);
        }
    }

    public static class EBEOptionEntry extends ElementListWidget.Entry<EBEOptionListWidget.EBEOptionEntry> {
        private final EBEOption option;

        public EBEOptionEntry(EBEOption option) {
            this.option = option;
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            TextRenderer textRenderer = minecraftClient.textRenderer;
            minecraftClient.getTextureManager().bindTexture(AbstractButtonWidget.WIDGETS_LOCATION);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            int vo = 1;
            if (hovered) {
                vo = 2;
            }
            vo *= 20;
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            DrawableHelper draw = new DrawableHelper() {
                @Override protected void drawHorizontalLine(MatrixStack matrices, int x1, int x2, int y, int color) {}
            };
            draw.drawTexture(matrices, x, y, 0, 46 + vo, entryWidth / 2, 20);
            draw.drawTexture(matrices, x + entryWidth / 2, y, 200 - entryWidth / 2, 46 + vo, entryWidth / 2, 20);
            drawCenteredText(matrices, textRenderer, Text.of(I18n.translate(option.getValueKey())), x + entryWidth / 2, y + 12 / 2, option.isDefault() ? 0xFFFFFF : 0xffda5e);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (button == 0) {
                option.next();
                return true;
            }
            return false;
        }

        @Override
        public List<? extends Element> children() {
            return Collections.emptyList();
        }
    }
}
