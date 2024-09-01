package pl.epsi.gui.inGame;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import pl.epsi.event.EventImpl;
import pl.epsi.event.EventManager;
import pl.epsi.event.HudRenderCallbackListener;
import pl.epsi.event.UpdateListener;

public class WeaponsOverlay extends EventImpl implements HudRenderCallbackListener, UpdateListener {

    private static WeaponsOverlay instance;

    private WeaponsOverlay() {}

    public static WeaponsOverlay getInstance() {
        if (instance == null) instance = new WeaponsOverlay();
        return instance;
    }

    public boolean shouldRender = false;

    @Override
    public void onEnable() {
        EventManager.getInstance().add(HudRenderCallbackListener.class, this);
        EventManager.getInstance().add(UpdateListener.class, this);
    }

    @Override
    public void onDisable() {
        EventManager.getInstance().remove(HudRenderCallbackListener.class, this);
        EventManager.getInstance().remove(UpdateListener.class, this);
    }

    @Override
    public void onRender(DrawContext context, float tickDelta) {
        if (!this.shouldRender) return;
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, "TEST", 500, 500, 0xffffff);
    }

    @Override
    public void onUpdate() {
        if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(),
                GLFW.GLFW_KEY_C)) {
            this.shouldRender = true;
        } else { this.shouldRender = false; }
    }
}
