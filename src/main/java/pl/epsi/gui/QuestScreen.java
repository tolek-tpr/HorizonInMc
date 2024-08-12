package pl.epsi.gui;

import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import pl.epsi.gui.modules.InventoryTypeSelectorModule;
import pl.epsi.gui.modules.MenuSelectorModule;
import pl.epsi.gui.modules.QuestTypeSelectorModule;
import pl.epsi.util.InstancedValues;

public class QuestScreen extends MainMenuScreenE {

    private int menuSelected;
    private QuestTypeSelectorModule qtsm;

    public QuestScreen(int menuSelected) {
        super(Text.translatable("screen.horizoninmc.quest_screen"));
        this.menuSelected = menuSelected;
    }

    @Override
    public void init() {
        MenuSelectorModule msm = new MenuSelectorModule(width, 20, menuSelected);
        addDrawableChild(msm);
        msm.children().forEach(this::addDrawableChild);

        qtsm = new QuestTypeSelectorModule(30, 57, height, true);
        addDrawableChild(qtsm);
        qtsm.children().forEach(this::addDrawableChild);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_W || keyCode == GLFW.GLFW_KEY_S) {
            //if (this.subCategoryEntered) return super.keyPressed(keyCode, scanCode, modifiers);
            handleInventoryRender(InstancedValues.getInstance().inventoryEntrySelected);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void close() {
        client.setScreen(null);
    }

    public int getMenuSelected() { return this.menuSelected; }

}
