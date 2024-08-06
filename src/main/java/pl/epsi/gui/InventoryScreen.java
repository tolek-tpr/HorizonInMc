package pl.epsi.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import pl.epsi.gui.modules.InventorySlotModule;
import pl.epsi.gui.modules.InventoryTypeSelectorModule;
import pl.epsi.gui.modules.MenuSelectorModule;
import pl.epsi.gui.modules.ScrollableListModule;
import pl.epsi.player.inventory.CustomInventory;
import pl.epsi.player.inventory.CustomItem;
import pl.epsi.util.HorizonUtil;
import pl.epsi.util.InstancedValues;

import java.util.ArrayList;
import java.util.HashMap;

public class InventoryScreen extends MainMenuScreenE {

    private int menuSelected;
    public boolean subCategoryEntered = false;
    private ArrayList<ClickableWidget> toRemove = new ArrayList<>();
    private HashMap<InventorySlotModule, int[]> slotPositions = new HashMap<>();
    private InventorySlotModule selectedSlot;
    private ScrollableListModule slm;
    private InventoryTypeSelectorModule itsm;
    private final Identifier ITEM_SELECTED = new Identifier("horizoninmc", "inventory/item_selected");
    private final Identifier ENTER_ICON = new Identifier("horizoninmc", "selection/enter");
    private final Identifier ESCAPE_ICON = new Identifier("horizoninmc", "selection/escape");

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

        itsm = new InventoryTypeSelectorModule(30, 80, height, true);
        addDrawableChild(itsm);
        itsm.children().forEach(this::addDrawableChild);

        slm = new ScrollableListModule(MinecraftClient.getInstance(), InventorySlotModule.scale * 4 + 40, InventorySlotModule.scale * 6, 80, 56);
        slm.setRenderBackground(false);
        slm.setX(200);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        if (!this.subCategoryEntered) {
            context.drawGuiTexture(ENTER_ICON, 20, height - 26, 26, 16);
            context.drawTextWithShadow(textRenderer, "Select", 51, height - 19 - textRenderer.fontHeight / 2, 0xd2e2e9);
        } else {
            context.drawGuiTexture(ESCAPE_ICON, 20, height - 26, 16, 16);
            context.drawTextWithShadow(textRenderer, "Exit", 41, height - 19 - textRenderer.fontHeight / 2, 0xd2e2e9);
        }

        if (!this.subCategoryEntered) return;
        if (this.selectedSlot == null) {
            slotPositions.keySet().forEach((slot) -> {
                slotPositions.replace(slot, new int[]{slot.getX(), slot.getY(), slot.getX() + InventorySlotModule.scale,
                        slot.getY() + InventorySlotModule.scale});
                if (HorizonUtil.isCoordinateInsideSquare(slotPositions.get(slot), mouseX, mouseY)) {
                    context.drawGuiTexture(ITEM_SELECTED, slot.getX() - 12, slot.getY() - 12,
                            InventorySlotModule.scale + 24, InventorySlotModule.scale + 24);
                }
            });
        } else {
            context.drawGuiTexture(ITEM_SELECTED, this.selectedSlot.getX() - 12, this.selectedSlot.getY() - 12,
                    InventorySlotModule.scale + 24, InventorySlotModule.scale + 24);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.subCategoryEntered) return super.mouseClicked(mouseX, mouseY, button);
        slotPositions.keySet().forEach((slot) -> {
            slotPositions.replace(slot, new int[]{ slot.getX(), slot.getY(), slot.getX() + InventorySlotModule.scale,
                    slot.getY() + InventorySlotModule.scale });
            if (HorizonUtil.isCoordinateInsideSquare(slotPositions.get(slot), (int) mouseX, (int) mouseY)) {
                if (this.selectedSlot == slot) {
                    this.selectedSlot = null;
                } else {
                    this.selectedSlot = slot;
                }
            }
        });
        return super.mouseClicked(mouseX, mouseY, button);
    }



    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ENTER && !this.subCategoryEntered) {
            redrawItsm(new InventoryTypeSelectorModule(30, 80, height, false));
            slm.setX(100);
            handleInventoryRender(InstancedValues.getInstance().inventoryEntrySelected);
            this.subCategoryEntered = true;
        }
        if (keyCode == GLFW.GLFW_KEY_ESCAPE && this.subCategoryEntered) {
            redrawItsm(new InventoryTypeSelectorModule(30, 80, height, true));
            slm.setX(200);
            handleInventoryRender(InstancedValues.getInstance().inventoryEntrySelected);
            this.subCategoryEntered = false;
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_W || keyCode == GLFW.GLFW_KEY_S) {
            if (this.subCategoryEntered) return super.keyPressed(keyCode, scanCode, modifiers);
            handleInventoryRender(InstancedValues.getInstance().inventoryEntrySelected);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void redrawItsm(InventoryTypeSelectorModule itsm) {
        remove(this.itsm);
        this.itsm.children().forEach(this::remove);
        this.itsm = itsm;
        addDrawableChild(itsm);
        itsm.children().forEach(this::addDrawableChild);
    }

    private void handleInventoryRender(int inventoryEntrySelected) {
        this.selectedSlot = null;
        toRemove.forEach(this::remove);
        toRemove.clear();
        slotPositions.clear();
        remove(slm);
        int slmXPrev = slm == null ? 200 : slm.getX();

        slm = new ScrollableListModule(MinecraftClient.getInstance(), InventorySlotModule.scale * 4 + 40, InventorySlotModule.scale * 6, 80, 56);
        slm.setRenderBackground(false);
        slm.setX(slmXPrev);

        ScrollableListModule.ListEntry lastRow = null;
        int j = 0;
        HashMap<CustomItem, Integer> map = getMapForEntry(inventoryEntrySelected);

        for (int i = 0; i < map.size(); ++i) {
            if (i % 4 == 0)  {
                lastRow = slm.addRow();
                j = 0;
            } else { j++; }
            CustomItem item = (CustomItem) map.keySet().toArray()[i];
            InventorySlotModule ism = new InventorySlotModule(slm.getX() + j * InventorySlotModule.scale + j * 10, 0, item, map.get(item));

            toRemove.add(slm);
            toRemove.add(ism);
            if (lastRow != null) {
                lastRow.add(ism);
                slotPositions.put(ism, new int[]{ ism.getX(), ism.getY(), ism.getX() + InventorySlotModule.scale, ism.getY() + InventorySlotModule.scale});
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
