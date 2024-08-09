package pl.epsi.gui;

import net.minecraft.text.Text;
import pl.epsi.gui.modules.InventoryTypeSelectorModule;
import pl.epsi.gui.modules.MenuSelectorModule;
import pl.epsi.gui.modules.QuestTypeSelectorModule;

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
    public void close() {
        client.setScreen(null);
    }

    public int getMenuSelected() { return this.menuSelected; }

}
