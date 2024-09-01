package pl.epsi.gui;

import net.minecraft.text.Text;
import pl.epsi.gui.modules.MenuSelectorModule;

public class MapScreen extends MainMenuScreenE {

    private int menuSelected;

    public MapScreen(int menuSelected) {
        super(Text.translatable("screen.horizoninmc.map_screen"));
        this.menuSelected = menuSelected;
    }

    @Override
    public void init() {
        MenuSelectorModule msm = new MenuSelectorModule(width, 20, menuSelected);
        addDrawableChild(msm);
        msm.children().forEach(this::addDrawableChild);
    }

    @Override
    public void handleSubGroupChange(int keyCode) {

    }

    @Override
    public boolean isSubGroupEntered() { return false; }

    public int getMenuSelected() { return this.menuSelected; }

}
