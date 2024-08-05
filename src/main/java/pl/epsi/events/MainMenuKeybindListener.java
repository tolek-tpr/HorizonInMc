package pl.epsi.events;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import pl.epsi.HorizonInMc;
import pl.epsi.event.EventImpl;
import pl.epsi.event.EventManager;
import pl.epsi.event.KeyboardListener;
import pl.epsi.gui.*;
import pl.epsi.render.SubtitleRenderer;
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
        if (keyCode == GLFW.GLFW_KEY_0 && InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), keyCode)) {
            DrawContext context = new DrawContext(MinecraftClient.getInstance(),
                    MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers());
            SubtitleRenderer sr = new SubtitleRenderer(100, 100);
            sr.drawCenteredSubtitle(Text.of("HELLO"), 50, 0xffffff);
        }
        if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), keyCode)) {
            if (keyCode == GLFW.GLFW_KEY_TAB) {
                iv.screen = new QuestScreen(2);
                MinecraftClient.getInstance().setScreen(iv.screen);
            }
            if (MinecraftClient.getInstance().currentScreen instanceof MainMenuScreenE) {
                switch (keyCode) {
                    case GLFW.GLFW_KEY_Q ->
                        handleScreenChangeLeft();
                    case GLFW.GLFW_KEY_E ->
                        handleScreenChangeRight();
                }
            }
            if (MinecraftClient.getInstance().currentScreen instanceof InventoryScreen) {
                switch (keyCode) {
                    case GLFW.GLFW_KEY_W ->  {
                        if (iv.inventoryEntrySelected == 0) {
                            iv.inventoryEntrySelected = 6;
                        } else {
                            iv.inventoryEntrySelected--;
                        }
                    }
                    case GLFW.GLFW_KEY_S -> {
                        if (iv.inventoryEntrySelected == 6) {
                            iv.inventoryEntrySelected = 0;
                        } else {
                            iv.inventoryEntrySelected++;
                        }
                    }
                }
            }
        }
    }

    private void handleScreenChangeLeft() {
        switch (iv.screen.getMenuSelected()) {
            case 0 -> iv.screen = new NotebookScreen(4);
            case 1 -> iv.screen = new InventoryScreen(0);
            case 2 -> iv.screen = new SkillsScreen(1);
            case 3 -> iv.screen = new MapScreen(2);
            case 4 -> iv.screen = new QuestScreen(3);
        }
        this.updateScreen();
    }

    private void handleScreenChangeRight() {
        switch (iv.screen.getMenuSelected()) {
            case 0 -> iv.screen = new SkillsScreen(1);
            case 1 -> iv.screen = new MapScreen(2);
            case 2 -> iv.screen = new QuestScreen(3);
            case 3 -> iv.screen = new NotebookScreen(4);
            case 4 -> iv.screen = new InventoryScreen(0);
        }
        this.updateScreen();
    }

    private void updateScreen() {
        MinecraftClient.getInstance().setScreen(iv.screen);
    }

}
