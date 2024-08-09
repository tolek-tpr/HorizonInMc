package pl.epsi.gui.modules;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;
import pl.epsi.player.inventory.ResourceItem;
import pl.epsi.util.HorizonUtil;

import java.util.ArrayList;
import java.util.List;

public class ResourceItemDescriptionModule extends ContainerWidget {

    public ArrayList<ClickableWidget> children = new ArrayList<>();

    private int x;
    private int y;
    private TextRenderer tx;
    private ResourceItem item;
    private final Identifier ITEM_NAME_BACKGROUND_COMMON = new Identifier("horizoninmc", "inventory/item_name_background_common");
    private final Identifier ITEM_NAME_BACKGROUND_UNCOMMON = new Identifier("horizoninmc", "inventory/item_name_background_uncommon");
    private final Identifier ITEM_NAME_BACKGROUND_RARE = new Identifier("horizoninmc", "inventory/item_name_background_rare");
    private final Identifier ITEM_NAME_BACKGROUND_VERY_RARE = new Identifier("horizoninmc", "inventory/item_name_background_very_rare");
    private final Identifier ITEM_NAME_BACKGROUND_LEGENDARY = new Identifier("horizoninmc", "inventory/item_name_background_legendary");

    public ResourceItemDescriptionModule(int x, int y, int height, ResourceItem item) {
        super(x, y, InventorySlotModule.scale * 5 + 50, height, Text.literal("ResourceItemDescriptionModule"));
        this.x = x;
        this.y = y;
        this.tx = MinecraftClient.getInstance().textRenderer;
        this.item = item;
    }

    @Override
    public List<? extends ClickableWidget> children() {
        return children;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        MatrixStack matrixStack = context.getMatrices();
        int rarity = item.getSettings().getRarity();
        int rarityColor = HorizonUtil.getColorByRarity(rarity);
        String categoryName = HorizonUtil.getResourceSubCategoryName(item.getSettings().getResourcesSubCategory());

        // Item name rendering
        RenderSystem.enableBlend();
        context.drawGuiTexture(getTextBackgroundForRarity(item.getSettings().getRarity()), x - 5, y - 6, 256, 32);
        RenderSystem.disableBlend();

        matrixStack.push();
        matrixStack.translate(x, y, 0);
        matrixStack.scale(2.2F, 2.2F, 0);
        context.drawTextWithShadow(tx, item.getItemName().getString().toUpperCase(), 0, 0, 0xdedad1);
        matrixStack.pop();


        // Sub category + rarity rendering
        context.drawTextWithShadow(tx, categoryName, x, y + 23, 0xdedad1);
        context.drawTextWithShadow(tx, "|", x + tx.getWidth(categoryName) + 5, y + 23, 0x5e5d5c);
        int rarityOffset = tx.getWidth(categoryName) + 10 + tx.getWidth("|");
        context.drawTextWithShadow(tx, HorizonUtil.getRarityName(rarity), x + rarityOffset, y + 23, rarityColor);

        // Description rendering
        int yDescOffset = 33 + tx.fontHeight;
        List<OrderedText> descriptionWrapped = tx.wrapLines(item.getDescription(), getWidth());
        for (OrderedText t : descriptionWrapped) {
            context.drawTextWithShadow(tx, t, x, y + yDescOffset, 0xa29f9a);
            yDescOffset += tx.fontHeight + 2;
        }

        // Sources rendering
        int ySourceOffset = yDescOffset + tx.fontHeight + 20;

        matrixStack.push();
        matrixStack.translate(x, y + ySourceOffset, 0);
        matrixStack.scale(1.5F, 1.5F, 0);
        context.drawTextWithShadow(tx, "SOURCES", 0, 0, 0xdedad1);
        matrixStack.pop();

        context.drawHorizontalLine(x, x + this.getWidth(), y + ySourceOffset + 15, 0xffa29f9a);

        ySourceOffset += 30;

        List<OrderedText> sourcesWrapped = tx.wrapLines(item.getSources(), getWidth());
        for (OrderedText t : sourcesWrapped) {
            context.drawTextWithShadow(tx, t, x, y + ySourceOffset, 0xa29f9a);
            ySourceOffset += tx.fontHeight + 2;
        }

        // Uses rendering
        int yUseOffset = ySourceOffset + tx.fontHeight + 20;

        matrixStack.push();
        matrixStack.translate(x, y + yUseOffset, 0);
        matrixStack.scale(1.5F, 1.5F, 0);
        context.drawTextWithShadow(tx, "USED FOR", 0, 0, 0xdedad1);
        matrixStack.pop();

        context.drawHorizontalLine(x, x + this.getWidth(), y + yUseOffset + 15, 0xffa29f9a);

        yUseOffset += 30;

        List<OrderedText> usesWrapped = tx.wrapLines(item.getUses(), getWidth());
        for (OrderedText t : usesWrapped) {
            context.drawTextWithShadow(tx, t, x, y + yUseOffset, 0xa29f9a);
            yUseOffset += tx.fontHeight + 2;
        }
    }

    public Identifier getTextBackgroundForRarity(int rarity) {
        switch(rarity) {
            case 0 -> { return ITEM_NAME_BACKGROUND_COMMON; }
            case 1 -> { return ITEM_NAME_BACKGROUND_UNCOMMON; }
            case 2 -> { return ITEM_NAME_BACKGROUND_RARE; }
            case 3 -> { return ITEM_NAME_BACKGROUND_VERY_RARE; }
            case 4 -> { return ITEM_NAME_BACKGROUND_LEGENDARY; }
        }
        return ITEM_NAME_BACKGROUND_COMMON;
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

}
