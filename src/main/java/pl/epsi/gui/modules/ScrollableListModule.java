package pl.epsi.gui.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import org.apache.commons.compress.utils.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScrollableListModule extends ElementListWidget<ScrollableListModule.ListEntry> {

    public ScrollableListModule(MinecraftClient minecraftClient, int width, int height, int y, int itemHeight) {
        super(minecraftClient, width, height, y, itemHeight);
    }

    private int scrollbarXPos = width - 40;

    public ListEntry addRow(ClickableWidget... widget) {
        ListEntry e = new ListEntry(widget);
        addEntry(e);
        return e;
    }

    public void setScrollbarPosition(int scrollbarXPos) { this.scrollbarXPos = scrollbarXPos; }
    public int getScrollbarPosition() { return this.scrollbarXPos; }

    protected int getScrollbarPositionX() {
        return this.scrollbarXPos;
    }

    public class ListEntry extends Entry<ListEntry> {

        private ArrayList<Selectable> selectables = new ArrayList<>();
        private ArrayList<ClickableWidget> elements = new ArrayList<>();

        public ListEntry(ClickableWidget... e) {
            elements.addAll(Arrays.asList(e));
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return selectables;
        }

        @Override
        public List<? extends Element> children() {
            return elements;
        }

        public ListEntry add(ClickableWidget... e) {
            elements.addAll(Arrays.asList(e));
            return this;
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            elements.forEach(e -> {
                e.setY(y + Math.max(0, (entryHeight - e.getHeight()) / 2));
                e.render(context, mouseX, mouseY, tickDelta);
            });
        }
    }

}
