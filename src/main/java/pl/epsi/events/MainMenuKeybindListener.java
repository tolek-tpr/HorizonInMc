package pl.epsi.events;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import pl.epsi.HorizonInMc;
import pl.epsi.event.EventImpl;
import pl.epsi.event.EventManager;
import pl.epsi.event.KeyboardListener;
import pl.epsi.gui.MainMenuScreenE;
import pl.epsi.gui.QuestScreen;
import pl.epsi.util.InstancedValues;

public class MainMenuKeybindListener extends EventImpl implements KeyboardListener {

    private InstancedValues iv = InstancedValues.getInstance();

    @Override
    public void onEnable() {
        EventManager.getInstance().add(KeyboardListener.class, this);
    }

    @Override
    public void onDisable() {
        EventManager.getInstance().remove(KeyboardListener.class, this);
    }

    @Override
    public void onKey(int keyCode, int scanCode, int modifiers) {
        if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), keyCode)) {
            if (keyCode == GLFW.GLFW_KEY_TAB) {
                iv.screen = new QuestScreen(2);
                MinecraftClient.getInstance().setScreen(iv.screen);
            }
            if (MinecraftClient.getInstance().currentScreen instanceof MainMenuScreenE) {

                switch (keyCode) {
                    case GLFW.GLFW_KEY_Q -> {
                        if (iv.screen.getMenuSelected() == 0) {
                            iv.screen = new QuestScreen(4);
                        } else {
                            iv.screen = new QuestScreen(iv.screen.getMenuSelected() - 1);
                        }
                        this.updateScreen();
                    }
                    case GLFW.GLFW_KEY_E -> {
                        if (iv.screen.getMenuSelected() == 4) {
                            iv.screen = new QuestScreen(0);
                        } else {
                            iv.screen = new QuestScreen(iv.screen.getMenuSelected() + 1);
                        }
                        this.updateScreen();
                    }
                }
            }
        }
    }

    private void updateScreen() {
        MinecraftClient.getInstance().setScreen(iv.screen);
    }

}
