package pl.epsi.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class SubtitleRenderer {

    private DrawContext context;
    private int height;
    private int width;
    private TextRenderer tx = MinecraftClient.getInstance().textRenderer;

    public SubtitleRenderer(DrawContext context, int width, int height) {
        this.context = context;
        this.width = width;
        this.height = height;
    }

    private void drawSubtitle(Text text, int x, int y, int color) {
        context.drawText(MinecraftClient.getInstance().textRenderer, text, x, y, color, false);
    }

    public void drawCenteredSubtitle(Text text, int y, int color) {
        this.drawSubtitle(text, width / 2 - tx.getWidth(text), y, color);
    }

}
