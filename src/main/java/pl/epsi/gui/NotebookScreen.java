package pl.epsi.gui;

import net.minecraft.text.Text;
import pl.epsi.gui.modules.MenuSelectorModule;

public class NotebookScreen extends MainMenuScreenE {

    private int menuSelected;

    public NotebookScreen(int menuSelected) {
        super(Text.translatable("screen.horizoninmc.notebook_screen"));
        this.menuSelected = menuSelected;
    }

    @Override
    public void init() {
        MenuSelectorModule msm = new MenuSelectorModule(width, 20, menuSelected);
        addDrawableChild(msm);
        msm.children().forEach(this::addDrawableChild);
    }

    @Override
    public void close() {
        client.setScreen(null);
    }

    public int getMenuSelected() { return this.menuSelected; }

}
