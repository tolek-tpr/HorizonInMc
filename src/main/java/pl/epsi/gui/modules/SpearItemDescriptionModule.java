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
import pl.epsi.player.inventory.ResourceItem;
import pl.epsi.player.inventory.SpearItem;
import pl.epsi.util.HorizonUtil;

import java.util.ArrayList;
import java.util.List;

public class SpearItemDescriptionModule extends ContainerWidget {

    public ArrayList<ClickableWidget> children = new ArrayList<>();
    private int x;
    private int y;
    private TextRenderer tx;
    private SpearItem item;
    private final Identifier ITEM_NAME_BACKGROUND_COMMON = new Identifier("horizoninmc", "inventory/item_name_background_common");
    private final Identifier ITEM_NAME_BACKGROUND_UNCOMMON = new Identifier("horizoninmc", "inventory/item_name_background_uncommon");
    private final Identifier ITEM_NAME_BACKGROUND_RARE = new Identifier("horizoninmc", "inventory/item_name_background_rare");
    private final Identifier ITEM_NAME_BACKGROUND_VERY_RARE = new Identifier("horizoninmc", "inventory/item_name_background_very_rare");
    private final Identifier ITEM_NAME_BACKGROUND_LEGENDARY = new Identifier("horizoninmc", "inventory/item_name_background_legendary");

    public SpearItemDescriptionModule(int x, int y, int height, SpearItem item) {
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
        String categoryName = HorizonUtil.getWeaponSubCategoryName(item.getWeaponCategory());

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
        int yStatsOffset = yDescOffset + tx.fontHeight + 20;

        matrixStack.push();
        matrixStack.translate(x, y + yStatsOffset, 0);
        matrixStack.scale(1.5F, 1.5F, 0);
        context.drawTextWithShadow(tx, "STATS", 0, 0, 0xdedad1);
        matrixStack.pop();

        context.drawHorizontalLine(x, x + this.getWidth(), y + yStatsOffset + 15, 0xffa29f9a);

        yStatsOffset += 30;

        drawStats(context, yStatsOffset);
    }

    private int getWidthOffset(Text text) {
        return tx.getWidth(text);
    }

    private int getWidthOffset(String text) {
        return tx.getWidth(text);
    }

    private void drawStats(DrawContext ctx, int yOffset) {
        int offset1 = getWidthOffset(Text.literal(item.getSpearSettings().getDamageMultiplier() + "%"));
        ctx.drawTextWithShadow(tx, Text.literal(item.getSpearSettings().getDamageMultiplier() + "%"), x, y + yOffset, 0xa29f9a);
        ctx.drawTextWithShadow(tx, Text.literal("|"), x + offset1 + 5, y + yOffset, 0x5e5d5c);
        ctx.drawTextWithShadow(tx, Text.literal("Damage"), x + offset1 + getWidthOffset("|") + 10, y + yOffset, 0xa29f9a);
        yOffset += tx.fontHeight + 2;

        int offset2 = getWidthOffset(Text.literal(item.getSpearSettings().getChargeSpeedMultiplier() + "%"));
        ctx.drawTextWithShadow(tx, Text.literal(item.getSpearSettings().getChargeSpeedMultiplier() + "%"), x, y + yOffset, 0xa29f9a);
        ctx.drawTextWithShadow(tx, Text.literal("|"), x + offset2 + 5, y + yOffset, 0x5e5d5c);
        ctx.drawTextWithShadow(tx, Text.literal("Charge Speed"), x + offset2 + getWidthOffset("|") + 10, y + yOffset, 0xa29f9a);
        yOffset += tx.fontHeight + 2;

        int offset3 = getWidthOffset(Text.literal(item.getSpearSettings().getDischargeDamageMultiplier() + "%"));
        ctx.drawTextWithShadow(tx, Text.literal(item.getSpearSettings().getDischargeDamageMultiplier() + "%"), x, y + yOffset, 0xa29f9a);
        ctx.drawTextWithShadow(tx, Text.literal("|"), x + offset3 + 5, y + yOffset, 0x5e5d5c);
        ctx.drawTextWithShadow(tx, Text.literal("Discharge Damage"), x + offset3 + getWidthOffset("|") + 10, y + yOffset, 0xa29f9a);
        yOffset += tx.fontHeight + 2;

        int offset4 = getWidthOffset(Text.literal(item.getSpearSettings().getEnergizedDurationMultiplier() + "%"));
        ctx.drawTextWithShadow(tx, Text.literal(item.getSpearSettings().getEnergizedDurationMultiplier() + "%"), x, y + yOffset, 0xa29f9a);
        ctx.drawTextWithShadow(tx, Text.literal("|"), x + offset4 + 5, y + yOffset, 0x5e5d5c);
        ctx.drawTextWithShadow(tx, Text.literal("Energized Duration"), x + offset4 + getWidthOffset("|") + 10, y + yOffset, 0xa29f9a);
        yOffset += tx.fontHeight + 2;

        int offset5 = getWidthOffset(Text.literal(item.getSpearSettings().getResonatorBlastDamageMultiplier() + "%"));
        ctx.drawTextWithShadow(tx, Text.literal(item.getSpearSettings().getResonatorBlastDamageMultiplier() + "%"), x, y + yOffset, 0xa29f9a);
        ctx.drawTextWithShadow(tx, Text.literal("|"), x + offset5 + 5, y + yOffset, 0x5e5d5c);
        ctx.drawTextWithShadow(tx, Text.literal("Resonator Blast Damage"), x + offset5 + getWidthOffset("|") + 10, y + yOffset, 0xa29f9a);
        yOffset += tx.fontHeight + 2;

        int offset6 = getWidthOffset(Text.literal(item.getSpearSettings().getCriticalStrikeDamageMultiplier() + "%"));
        ctx.drawTextWithShadow(tx, Text.literal(item.getSpearSettings().getCriticalStrikeDamageMultiplier() + "%"), x, y + yOffset, 0xa29f9a);
        ctx.drawTextWithShadow(tx, Text.literal("|"), x + offset6 + 5, y + yOffset, 0x5e5d5c);
        ctx.drawTextWithShadow(tx, Text.literal("Critical Strike Damage"), x + offset6 + getWidthOffset("|") + 10, y + yOffset, 0xa29f9a);
        yOffset += tx.fontHeight + 2;

        int offset7 = getWidthOffset(Text.literal(item.getSpearSettings().getSilentStrikeDamageMultiplier() + "%"));
        ctx.drawTextWithShadow(tx, Text.literal(item.getSpearSettings().getSilentStrikeDamageMultiplier() + "%"), x, y + yOffset, 0xa29f9a);
        ctx.drawTextWithShadow(tx, Text.literal("|"), x + offset7 + 5, y + yOffset, 0x5e5d5c);
        ctx.drawTextWithShadow(tx, Text.literal("Silent Strike Damage"), x + offset7 + getWidthOffset("|") + 10, y + yOffset, 0xa29f9a);
        yOffset += tx.fontHeight + 2;

        int offset8 = getWidthOffset(Text.literal(item.getSpearSettings().getPowerAttackDamageMultiplier() + "%"));
        ctx.drawTextWithShadow(tx, Text.literal(item.getSpearSettings().getPowerAttackDamageMultiplier() + "%"), x, y + yOffset, 0xa29f9a);
        ctx.drawTextWithShadow(tx, Text.literal("|"), x + offset8 + 5, y + yOffset, 0x5e5d5c);
        ctx.drawTextWithShadow(tx, Text.literal("Power Attack Damage"), x + offset8 + getWidthOffset("|") + 10, y + yOffset, 0xa29f9a);
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
