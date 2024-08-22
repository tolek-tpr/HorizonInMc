package pl.epsi.gui.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class SeparatorModule extends ContainerWidget {

    public ArrayList<ClickableWidget> children = new ArrayList<>();
    private final int x;
    private final int width;
    private final int height;
    private final Text text;
    private final int color;
    private final TextRenderer tx = MinecraftClient.getInstance().textRenderer;

    public SeparatorModule(int x, int y, int width, int height, Text text, int color) {
        super(x, y, width, height, Text.literal("SeparatorModule"));
        this.x = x;
        this.width = width;
        this.height = height;
        this.text = text;
        this.color = color;
    }


    @Override
    public List<? extends Element> children() {
        return children;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        int y = getY();
        context.drawTextWithShadow(tx, text, x, y + 40, color);
        context.drawHorizontalLine(x, x + width, y + tx.fontHeight + 42, color);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

}
