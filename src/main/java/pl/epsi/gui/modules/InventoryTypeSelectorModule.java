package pl.epsi.gui.modules;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import pl.epsi.util.InstancedValues;

import java.util.ArrayList;
import java.util.List;

public class InventoryTypeSelectorModule extends ContainerWidget {

    public ArrayList<ClickableWidget> children = new ArrayList<>();
    private int x;
    private int y;
    private int height;
    private InstancedValues iv = InstancedValues.getInstance();

    private final Identifier SELECT_UP = new Identifier("horizoninmc", "selection/select_up");
    private final Identifier SELECT_DOWN = new Identifier("horizoninmc", "selection/select_down");
    private final Identifier WEAPONS_TEXT = new Identifier("horizoninmc", "inventory/weapons_text");
    private final Identifier WEAPONS_TEXT_SELECTED = new Identifier("horizoninmc", "inventory/weapons_text_selected");
    private final Identifier OUTFITS_TEXT = new Identifier("horizoninmc", "inventory/outfits_text");
    private final Identifier OUTFITS_TEXT_SELECTED = new Identifier("horizoninmc", "inventory/outfits_text_selected");
    private final Identifier TOOLS_TEXT = new Identifier("horizoninmc", "inventory/tools_text");
    private final Identifier TOOLS_TEXT_SELECTED = new Identifier("horizoninmc", "inventory/tools_text_selected");
    private final Identifier AMMO_TEXT = new Identifier("horizoninmc", "inventory/ammo_text");
    private final Identifier AMMO_TEXT_SELECTED = new Identifier("horizoninmc", "inventory/ammo_text_selected");
    private final Identifier RESOURCES_TEXT = new Identifier("horizoninmc", "inventory/resources_text");
    private final Identifier RESOURCES_TEXT_SELECTED = new Identifier("horizoninmc", "inventory/resources_text_selected");
    private final Identifier STRIKE_PIECES_TEXT = new Identifier("horizoninmc", "inventory/strike_pieces_text");
    private final Identifier STRIKE_PIECES_TEXT_SELECTED = new Identifier("horizoninmc", "inventory/strike_pieces_text_selected");
    private final Identifier SPECIAL_GEAR_TEXT = new Identifier("horizoninmc", "inventory/special_gear_text");
    private final Identifier SPECIAL_GEAR_TEXT_SELECTED = new Identifier("horizoninmc", "inventory/special_gear_text_selected");

    public InventoryTypeSelectorModule(int x, int y, int height) {
        super(x, y, 100, 200, Text.literal("InventoryTypeSelectorModule"));
        this.x = x;
        this.y = y;
        this.height = height;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        int j = this.getY();
        context.drawGuiTexture(SELECT_UP, this.width / 2 - 10, j, 20, 20);
        context.drawGuiTexture(SELECT_DOWN, this.width / 2 - 10, j + 210, 20, 20);

        if (iv.inventoryEntrySelected == 0) {
            context.drawGuiTexture(WEAPONS_TEXT_SELECTED, x + 40, j + 30, 64, 10);
        } else {
            context.drawGuiTexture(WEAPONS_TEXT, x + 30, j + 30, 64, 10);
        }
        if (iv.inventoryEntrySelected == 1) {
            context.drawGuiTexture(OUTFITS_TEXT_SELECTED, x + 40, j + 55, 60, 10);
        } else {
            context.drawGuiTexture(OUTFITS_TEXT, x + 30, j + 55, 60, 10);
        }
        if (iv.inventoryEntrySelected == 2) {
            context.drawGuiTexture(TOOLS_TEXT_SELECTED, x + 40, j + 80, 44, 10);
        } else {
            context.drawGuiTexture(TOOLS_TEXT, x + 30, j + 80, 44, 10);
        }
        if (iv.inventoryEntrySelected == 3) {
            context.drawGuiTexture(AMMO_TEXT_SELECTED, x + 40, j + 105, 38, 10);
        } else {
            context.drawGuiTexture(AMMO_TEXT, x + 30, j + 105, 38, 10);
        }
        if (iv.inventoryEntrySelected == 4) {
            context.drawGuiTexture(RESOURCES_TEXT_SELECTED, x + 40, j + 130, 76, 10);
        } else {
            context.drawGuiTexture(RESOURCES_TEXT, x + 30, j + 130, 76, 10);
        }
        if (iv.inventoryEntrySelected == 5) {
            context.drawGuiTexture(STRIKE_PIECES_TEXT_SELECTED, x + 40, j + 155, 102, 10);
        } else {
            context.drawGuiTexture(STRIKE_PIECES_TEXT, x + 30, j + 155, 102, 10);
        }
        if (iv.inventoryEntrySelected == 6) {
            context.drawGuiTexture(SPECIAL_GEAR_TEXT_SELECTED, x + 40, j + 180, 98, 10);
        } else {
            context.drawGuiTexture(SPECIAL_GEAR_TEXT, x + 30, j + 180, 98, 10);
        }
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    @Override
    public List<? extends ClickableWidget> children() {
        return children;
    }


}
