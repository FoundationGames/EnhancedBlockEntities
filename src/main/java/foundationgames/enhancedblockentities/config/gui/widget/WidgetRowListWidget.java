package foundationgames.enhancedblockentities.config.gui.widget;

import foundationgames.enhancedblockentities.mixin.ClickableWidgetAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.GridWidget;

import java.util.ArrayList;
import java.util.List;

public class WidgetRowListWidget extends ElementListWidget<WidgetRowListWidget.Entry> {
    public static final int SPACING = 3;

    public final int rowWidth;
    public final int rowHeight;

    public WidgetRowListWidget(MinecraftClient mc, int w, int h, int top, int bottom, int rowWidth, int rowHeight) {
        super(mc, w, h, top, bottom, rowHeight + SPACING);
        this.rowWidth = rowWidth;
        this.rowHeight = rowHeight;
    }

    public void add(ClickableWidget ... widgets) {
        if (widgets.length == 0) return;

        var grid = new GridWidget();
        grid.setColumnSpacing(SPACING);
        var adder = grid.createAdder(widgets.length);

        int width = (this.rowWidth - ((widgets.length - 1) * SPACING)) / widgets.length;

        for (var widget : widgets) {
            widget.setWidth(width);
            ((ClickableWidgetAccessor) widget).setHeight(this.rowHeight);
            adder.add(widget);
        }

        grid.refreshPositions();

        this.addEntry(new Entry(grid));
    }

    @Override
    public int getRowWidth() {
        return rowWidth;
    }

    @Override
    protected int getScrollbarPositionX() {
        return this.width - 6;
    }

//    @Override
//    protected void drawMenuListBackground(DrawContext context) {
//    }

    public static class Entry extends ElementListWidget.Entry<Entry> {
        private final GridWidget widget;
        private final List<ClickableWidget> children = new ArrayList<>();

        public Entry(GridWidget widget) {
            this.widget = widget;
            widget.forEachChild(children::add);
        }

        @Override
        public List<? extends Element> children() {
            return this.children;
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return this.children;
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.widget.setPosition(x - 3, y);
            this.widget.refreshPositions();

            this.widget.forEachChild(c -> c.render(context, mouseX, mouseY, tickDelta));
        }
    }
}
