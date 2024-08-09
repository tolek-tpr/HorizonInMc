package pl.epsi.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import pl.epsi.event.EventImpl;
import pl.epsi.event.EventManager;
import pl.epsi.event.HudRenderCallbackListener;

public class SubRenderer extends EventImpl implements HudRenderCallbackListener {

    private DrawContext context;
    private float tickDelta;
    private MinecraftClient client = MinecraftClient.getInstance();
    private TextRenderer tx;
    private float width;
    private float height;

    @Override
    public void onEnable() {
        EventManager.getInstance().add(HudRenderCallbackListener.class, this);
    }

    @Override
    public void onDisable() {
        EventManager.getInstance().remove(HudRenderCallbackListener.class, this);
    }

    @Override
    public void onRender(DrawContext context, float tickDelta) {
        this.context = context;
        this.tickDelta = tickDelta;
        this.tx = client.textRenderer;
        this.width = client.getWindow().getScaledWidth();
        this.height = client.getWindow().getScaledHeight();
    }

    private boolean drawSubtitle(Text text, int x, int y, int color) {
        if (this.context == null || this.tx == null) return false;
        context.drawText(tx, text, x, y, color, false);
        return true;
    }

    public boolean drawCenteredSubtitle(Text text, int y, int color) {
        return this.drawSubtitle(text, (int) (width / 2 - tx.getWidth(text)), y, color);
    }

}
