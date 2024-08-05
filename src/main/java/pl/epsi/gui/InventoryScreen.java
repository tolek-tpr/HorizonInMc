package pl.epsi.gui;

import net.minecraft.text.Text;
import pl.epsi.gui.modules.MenuSelectorModule;

public class InventoryScreen extends MainMenuScreenE {

    private int menuSelected;

    public InventoryScreen(int menuSelected) {
        super(Text.translatable("screen.horizoninmc.inventory_screen"));
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
