package pl.epsi.gui.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import pl.epsi.player.inventory.CustomItem;

import java.util.ArrayList;
import java.util.List;

public class QuestSlotModule extends ContainerWidget {

    public ArrayList<ClickableWidget> children = new ArrayList<>();
    private CustomItem item;
    private int stackAmount;
    private Identifier ITEM_BACKDROP = new Identifier("horizoninmc", "inventory/item_backdrop");
    private Identifier COMMON_ITEM_BACKDROP = new Identifier("horizoninmc", "inventory/common_item_backdrop");
    private Identifier UNCOMMON_ITEM_BACKDROP = new Identifier("horizoninmc", "inventory/uncommon_item_backdrop");
    private Identifier RARE_ITEM_BACKDROP = new Identifier("horizoninmc", "inventory/rare_item_backdrop");
    private Identifier VERY_RARE_ITEM_BACKDROP = new Identifier("horizoninmc", "inventory/very_rare_item_backdrop");
    private Identifier LEGENDARY_ITEM_BACKDROP = new Identifier("horizoninmc", "inventory/legendary_item_backdrop");
    private final TextRenderer tx = MinecraftClient.getInstance().textRenderer;
    public static final int scale = 48;

    public QuestSlotModule(int x, int y, CustomItem item, int stackAmount) {
        super(x, y, 48, 48, Text.literal("InventorySlotModule"));
        this.item = item; // if stack size == -1 then dont render max size;
        this.stackAmount = stackAmount;
    }

    @Override
    public List<? extends ClickableWidget> children() {
        return children;
    }

    public CustomItem getItem() {
        return item;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        int y = this.getY();
        int x = this.getX();
        context.drawGuiTexture(ITEM_BACKDROP, x, y, scale, scale);
        switch (item.getSettings().getRarity()) {
            case 0 ->
                context.drawGuiTexture(COMMON_ITEM_BACKDROP, x, y, scale, scale);
            case 1 ->
                context.drawGuiTexture(UNCOMMON_ITEM_BACKDROP, x, y, scale, scale);
            case 2 ->
                    context.drawGuiTexture(RARE_ITEM_BACKDROP, x, y, scale, scale);
            case 3 ->
                    context.drawGuiTexture(VERY_RARE_ITEM_BACKDROP, x, y, scale, scale);
            case 4 ->
                    context.drawGuiTexture(LEGENDARY_ITEM_BACKDROP, x, y, scale, scale);
        }
        context.drawGuiTexture(item.getSettings().getTexture(), x + 8, y + 8, scale - 16, scale - 16);
        if (this.item.getSettings().getStackSize() == -1) {
            context.drawTextWithShadow(tx, String.valueOf(stackAmount), x, y + scale - tx.fontHeight, 0xffffff);
        } else {
            context.drawTextWithShadow(tx, stackAmount + "/" + item.getSettings().getStackSize(), x, y + scale - tx.fontHeight, 0xffffff);
        }
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

}
