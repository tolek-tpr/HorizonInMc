package pl.epsi.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.lwjgl.opengl.GL11;
import pl.epsi.gui.modules.InventoryTypeSelectorModule;
import pl.epsi.gui.modules.MenuSelectorModule;
import pl.epsi.util.InstancedValues;

public class InventoryScreen extends MainMenuScreenE {

    private int menuSelected;

    public InventoryScreen(int menuSelected) {
        super(Text.translatable("screen.horizoninmc.inventory_screen"));
        this.menuSelected = menuSelected;
    }

    @Override
    public void init() {
        InstancedValues.getInstance().inventoryEntrySelected = 0;

        MenuSelectorModule msm = new MenuSelectorModule(width, 20, menuSelected);
        addDrawableChild(msm);
        msm.children().forEach(this::addDrawableChild);

        InventoryTypeSelectorModule itsm = new InventoryTypeSelectorModule(30, 80, height);
        addDrawableChild(itsm);
        itsm.children().forEach(this::addDrawableChild);
    }

    /*@Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, int delta) {
        GL11.glEnable(GL11.GL_BLEND);
        context.drawGuiTexture(background, 0, 0, this.width, this.height);
        GL11.glDisable(GL11.GL_BLEND);
    }*/

    @Override
    public void close() {
        client.setScreen(null);
    }

    public int getMenuSelected() { return this.menuSelected; }

}
