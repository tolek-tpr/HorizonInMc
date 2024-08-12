package pl.epsi.gui.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import pl.epsi.render.CustomTextRenderer;
import pl.epsi.util.InstancedValues;

import java.util.ArrayList;
import java.util.List;

public class QuestTypeSelectorModule extends ContainerWidget {

    public ArrayList<ClickableWidget> children = new ArrayList<>();
    private int height;
    private boolean drawNames;
    private InstancedValues iv = InstancedValues.getInstance();
    private int y;
    private int x;
    private TextRenderer tx = MinecraftClient.getInstance().textRenderer;

    private final Identifier SELECT_UP = new Identifier("horizoninmc", "selection/select_up");
    private final Identifier SELECT_DOWN = new Identifier("horizoninmc", "selection/select_down");
    private final Identifier WEAPONS_TEXT = new Identifier("horizoninmc", "inventory/weapons_text");

    public ArrayList<ClickableWidget> getChildren() {
        return children;
    }

    public QuestTypeSelectorModule(int x, int y, int height, boolean drawNames) {
        super(x, y, 100, height, Text.literal("QuestTypeSelectorModule"));
        this.height = height;
        this.drawNames = drawNames;
        this.x = x;
        this.y = y;
    }

    @Override
    public List<? extends ClickableWidget> children() {
        return children;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        int j = this.getY();

        context.drawGuiTexture(SELECT_UP, this.width / 2 - 10, j, 20, 20);
        context.drawGuiTexture(SELECT_DOWN, this.width / 2 - 10, j + 407, 20, 20);

        renderEntries(context);
    }

    private void renderEntries(DrawContext context) {
        CustomTextRenderer renderer = new CustomTextRenderer(context);
        int j = this.getY() + 25;

        if (this.drawNames) {
            if (iv.questEntrySelected == 0) {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_gold.png"));
                renderer.renderText("ACTIVE", x + 40, j, 1, 10);
            } else {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_1.png"));
                renderer.renderText("ACTIVE", x + 30, j, 1, 10);
            }
            if (iv.questEntrySelected == 1) {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_gold.png"));
                renderer.renderText("MAIN", x + 40, j + 30, 1, 10);
            } else {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_1.png"));
                renderer.renderText("MAIN", x + 30, j + 30, 1, 10);
            }
            if (iv.questEntrySelected == 2) {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_gold.png"));
                renderer.renderText("SIDE", x + 40, j + 55, 1, 10);
            } else {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_1.png"));
                renderer.renderText("SIDE", x + 30, j + 55, 1, 10);
            }
            if (iv.questEntrySelected == 3) {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_gold.png"));
                renderer.renderText("ERRANDS", x + 40, j + 80, 1, 10);
            } else {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_1.png"));
                renderer.renderText("ERRANDS", x + 30, j + 80, 1, 10);
            }
            if (iv.questEntrySelected == 4) {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_gold.png"));
                renderer.renderText("JOBS", x + 40, j + 105, 1, 10);
            } else {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_1.png"));
                renderer.renderText("JOBS", x + 30, j + 105, 1, 10);
            }
            if (iv.questEntrySelected == 5) {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_gold.png"));
                renderer.renderText("SALVAGE CONTRACTS", x + 40, j + 130, 1, 10);
            } else {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_1.png"));
                renderer.renderText("SALVAGE CONTRACTS", x + 30, j + 130, 1, 10);
            }
            if (iv.questEntrySelected == 6) {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_gold.png"));
                renderer.renderText("HUNTING GROUNDS", x + 40, j + 155, 1, 10);
            } else {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_1.png"));
                renderer.renderText("HUNTING GROUNDS", x + 30, j + 155, 1, 10);
            }
            if (iv.questEntrySelected == 7) {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_gold.png"));
                renderer.renderText("MELEE PIT", x + 40, j + 180, 1, 10);
            } else {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_1.png"));
                renderer.renderText("MELEE PIT", x + 30, j + 180, 1, 10);
            }
            if (iv.questEntrySelected == 8) {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_gold.png"));
                renderer.renderText("THE ARENA", x + 40, j + 205, 1, 10);
            } else {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_1.png"));
                renderer.renderText("THE ARENA", x + 30, j + 205, 1, 10);
            }
            if (iv.questEntrySelected == 9) {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_gold.png"));
                renderer.renderText("REBEL CAMPS", x + 40, j + 230, 1, 10);
            } else {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_1.png"));
                renderer.renderText("REBEL CAMPS", x + 30, j + 230, 1, 10);
            }
            if (iv.questEntrySelected == 10) {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_gold.png"));
                renderer.renderText("REBEL OUTPOSTS", x + 40, j + 255, 1, 10);
            } else {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_1.png"));
                renderer.renderText("REBEL OUTPOSTS", x + 30, j + 255, 1, 10);
            }
            if (iv.questEntrySelected == 11) {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_gold.png"));
                renderer.renderText("RELIC RUINS", x + 40, j + 280, 1, 10);
            } else {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_1.png"));
                renderer.renderText("RELIC RUINS", x + 30, j + 280, 1, 10);
            }
            if (iv.questEntrySelected == 12) {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_gold.png"));
                renderer.renderText("TALLNECKS", x + 40, j + 305, 1, 10);
            } else {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_1.png"));
                renderer.renderText("TALLNECKS", x + 30, j + 305, 1, 10);
            }
            if (iv.questEntrySelected == 13) {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_gold.png"));
                renderer.renderText("CAULDRONS", x + 40, j + 330, 1, 10);
            } else {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_1.png"));
                renderer.renderText("CAULDRONS", x + 30, j + 330, 1, 10);
            }
            if (iv.questEntrySelected == 14) {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_gold.png"));
                renderer.renderText("COLLECTABLES", x + 40, j + 355, 1, 10);
            } else {
                renderer.setTextMap(new Identifier("horizoninmc", "font/custom_font_1.png"));
                renderer.renderText("COLLECTABLES", x + 30, j + 355, 1, 10);
            }
        }
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

}
