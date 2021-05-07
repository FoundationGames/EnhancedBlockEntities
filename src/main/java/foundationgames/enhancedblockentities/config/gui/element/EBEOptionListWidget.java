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
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class EBEOptionListWidget extends ElementListWidget<EBEOptionListWidget.BaseEntry> {
    private static final int WIDTH = 312;

    public EBEOptionListWidget(MinecraftClient minecraftClient, int width, int height, int top, int bottom, int itemHeight) {
        super(minecraftClient, width, height, top, bottom, itemHeight);
        this.left = 0;
        this.right = width;
    }

    @Override
    public int getRowWidth() {
        return WIDTH;
    }

    @Override
    protected int getScrollbarPositionX() {
        return width - 6;
    }

    @Nullable
    public EBEOptionEntry getHovered(double x, double y) {
        int halfRowWidth = this.getRowWidth() / 2;
        int centerX = this.left + this.width / 2;
        int leftBound = centerX - halfRowWidth;
        int rightBound = centerX + halfRowWidth;
        int boundedY = MathHelper.floor(y - (double)this.top) - this.headerHeight + (int)this.getScrollAmount() - 4;
        int index = boundedY / this.itemHeight;
        BaseEntry entry =
                x < (double)this.getScrollbarPositionX() &&
                x >= (double)leftBound &&
                x <= (double)rightBound &&
                index >= 0 && boundedY >= 0 &&
                index < this.getEntryCount()
        ? this.children().get(index) : null;
        if (entry instanceof EBEOptionEntry) {
            return (EBEOptionEntry)entry;
        }
        if (entry instanceof PairEntry) {
            return ((PairEntry)entry).getHovered((int) x);
        }
        return null;
    }

    public void add(EBEOption ... options) {
        for (EBEOption o : options) {
            this.addEntry(new EBEOptionEntry(o));
        }
    }

    public void addTitle(Text title) {
        this.addEntry(new TitleEntry(title));
    }

    public void addPair(EBEOption left, EBEOption right) {
        this.addEntry(new PairEntry(new EBEOptionEntry(left), new EBEOptionEntry(right), this));
    }

    public void forEach(Consumer<EBEOption> action) {
        for (int i = 0; i < getEntryCount(); i++) {
            if (getEntry(i) instanceof EBEOptionEntry) {
                action.accept(((EBEOptionEntry)getEntry(i)).option);
            }
            if (getEntry(i) instanceof PairEntry) {
                action.accept(((PairEntry)getEntry(i)).left.option);
                action.accept(((PairEntry)getEntry(i)).right.option);
            }
        }
    }

    public static abstract class BaseEntry extends ElementListWidget.Entry<EBEOptionListWidget.BaseEntry> {}

    public static class TitleEntry extends BaseEntry {
        private final Text title;

        public TitleEntry(Text title) {
            this.title = title;
        }

        @Override
        public List<? extends Element> children() {
            return Collections.emptyList();
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            TextRenderer tr = MinecraftClient.getInstance().textRenderer;
            int tw = tr.getWidth(title);
            drawCenteredText(matrices, tr, title, x + (entryWidth / 2), y + (entryHeight / 2) - 4, 0xFFFFFF);
            RenderSystem.disableTexture();
            int lw = (entryWidth / 2) - (6 + (tw / 2));
            int side = 3;
            drawTexture(matrices, x + side, y + 9, -100, 0, 0, lw - side, 1, 1, 1);
            drawTexture(matrices, x + (entryWidth - lw), y + 9, -100, 0, 0, lw - side, 1, 1, 1);
            RenderSystem.enableTexture();
        }
    }

    public static class PairEntry extends BaseEntry {
        public final EBEOptionEntry left;
        public final EBEOptionEntry right;
        private final EBEOptionListWidget list;

        public PairEntry(EBEOptionEntry left, EBEOptionEntry right, EBEOptionListWidget list) {
            this.left = left;
            this.right = right;
            this.list = list;
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            left.render(matrices, index, y, x, (entryWidth / 2) - 2, entryHeight, mouseX, mouseY, hovered && isMouseLeft(mouseX), tickDelta);
            right.render(matrices, index, y, x + 2 + (entryWidth / 2), (entryWidth / 2) - 2, entryHeight, mouseX, mouseY, hovered && !isMouseLeft(mouseX), tickDelta);
        }

        private boolean isMouseLeft(int mouseX) {
            return mouseX < (list.width / 2);
        }

        public EBEOptionEntry getHovered(int mouseX) {
            return isMouseLeft(mouseX) ? left : right;
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (isMouseLeft((int)mouseX)) return left.mouseClicked(mouseX, mouseY, button);
            else return right.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        public List<? extends Element> children() { return Collections.emptyList(); }
    }

    public static class EBEOptionEntry extends BaseEntry {
        public final EBEOption option;

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
