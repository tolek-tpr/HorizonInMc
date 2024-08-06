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
        if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), keyCode)) {
            if (keyCode == GLFW.GLFW_KEY_TAB && !(MinecraftClient.getInstance().currentScreen instanceof MainMenuScreenE)) {
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
                        if (isInventorySubGroupOpen()) return;
                        if (iv.inventoryEntrySelected == 0) {
                            iv.inventoryEntrySelected = 6;
                        } else {
                            iv.inventoryEntrySelected--;
                        }
                    }
                    case GLFW.GLFW_KEY_S -> {
                        if (isInventorySubGroupOpen()) return;
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

    private boolean isInventorySubGroupOpen() {
        if (MinecraftClient.getInstance().currentScreen instanceof InventoryScreen) {
            InventoryScreen screen = (InventoryScreen) MinecraftClient.getInstance().currentScreen;
            return screen.subCategoryEntered;
        } else {
            return false;
        }
    }

    private void handleScreenChangeLeft() {
        if (isInventorySubGroupOpen()) return;
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
        if (isInventorySubGroupOpen()) return;
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
