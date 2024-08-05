package pl.epsi.gui;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import pl.epsi.gui.modules.InventorySlotModule;
import pl.epsi.gui.modules.InventoryTypeSelectorModule;
import pl.epsi.gui.modules.MenuSelectorModule;
import pl.epsi.gui.modules.ScrollableListModule;
import pl.epsi.player.inventory.CustomInventory;
import pl.epsi.player.inventory.CustomItem;
import pl.epsi.util.InstancedValues;

import java.util.ArrayList;
import java.util.HashMap;

public class InventoryScreen extends MainMenuScreenE {

    private int menuSelected;
    private ArrayList<ClickableWidget> toRemove = new ArrayList<>();
    private ScrollableListModule slm;

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

        //slm = new ScrollableListModule(MinecraftClient.getInstance(), 158, 192, 80, 40);
        //slm.setRenderBackground(false);
        //slm.setX(200);
        //addDrawableChild(slm);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_W || keyCode == GLFW.GLFW_KEY_S)
            handleInventoryRender(InstancedValues.getInstance().inventoryEntrySelected);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void handleInventoryRender(int inventoryEntrySelected) {
        toRemove.forEach(this::remove);
        toRemove.clear();
        remove(slm);

        slm = new ScrollableListModule(MinecraftClient.getInstance(), InventorySlotModule.scale * 4 + 40, InventorySlotModule.scale * 6, 80, 56);
        slm.setRenderBackground(false);
        slm.setX(200);

        ScrollableListModule.ListEntry lastRow = null;
        int j = 0;
        HashMap<CustomItem, Integer> map = getMapForEntry(inventoryEntrySelected);

        for (int i = 0; i < map.size(); ++i) {
            if (i % 4 == 0)  {
                lastRow = slm.addRow();
                j = 0;
            } else { j++; }
            CustomItem item = (CustomItem) map.keySet().toArray()[i];
            InventorySlotModule ism = new InventorySlotModule(200 + j * InventorySlotModule.scale + j * 10, 0, item, map.get(item));
            toRemove.add(slm);
            toRemove.add(ism);
            if (lastRow != null) {
                lastRow.add(ism);
                addDrawableChild(slm);
                toRemove.add(slm);
            }
        };
    }

    private HashMap<CustomItem, Integer> getMapForEntry(int entry) {
        CustomInventory inv = CustomInventory.getInstance();
        switch (entry) {
            case 0 -> { return inv.WEAPONS; }
            case 1 -> { return inv.OUTFITS; }
            case 2 -> { return inv.TOOLS; }
            case 3 -> { return inv.AMMO; }
            case 4 -> { return inv.RESOURCES; }
            case 5 -> { return inv.STRIKE_PIECES; }
            case 6 -> { return inv.SPECIAL_GEAR; }
        }
        return null;
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
