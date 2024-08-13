package pl.epsi.gui.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ContainerWidget;
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
        CustomTextRenderer renderer = CustomTextRenderer.of("horizoninmc", "font/tpr_chunky_16");
        int j = this.getY() + 25;

        if (this.drawNames) {
            if (iv.questEntrySelected == 0) {
                renderer.renderLine(context, "ACTIVE", x + 40, j);
            } else {
                renderer.renderLine(context, "ACTIVE", x + 30, j);
            }
            if (iv.questEntrySelected == 1) {
                renderer.renderLine(context, "MAIN", x + 40, j + 30);
            } else {
                renderer.renderLine(context, "MAIN", x + 30, j + 30);
            }
            if (iv.questEntrySelected == 2) {
                renderer.renderLine(context, "SIDE", x + 40, j + 55);
            } else {
                renderer.renderLine(context, "SIDE", x + 30, j + 55);
            }
            if (iv.questEntrySelected == 3) {
                renderer.renderLine(context, "ERRANDS", x + 40, j + 80);
            } else {
                renderer.renderLine(context, "ERRANDS", x + 30, j + 80);
            }
            if (iv.questEntrySelected == 4) {
                renderer.renderLine(context, "JOBS", x + 40, j + 105);
            } else {
                renderer.renderLine(context, "JOBS", x + 30, j + 105);
            }
            if (iv.questEntrySelected == 5) {
                renderer.renderLine(context, "SALVAGE CONTRACTS", x + 40, j + 130);
            } else {
                renderer.renderLine(context, "SALVAGE CONTRACTS", x + 30, j + 130);
            }
            if (iv.questEntrySelected == 6) {
                renderer.renderLine(context, "HUNTING GROUNDS", x + 40, j + 155);
            } else {
                renderer.renderLine(context, "HUNTING GROUNDS", x + 30, j + 155);
            }
            if (iv.questEntrySelected == 7) {
                renderer.renderLine(context, "MELEE PIT", x + 40, j + 180);
            } else {
                renderer.renderLine(context, "MELEE PIT", x + 30, j + 180);
            }
            if (iv.questEntrySelected == 8) {
                renderer.renderLine(context, "THE ARENA", x + 40, j + 205);
            } else {
                renderer.renderLine(context, "THE ARENA", x + 30, j + 205);
            }
            if (iv.questEntrySelected == 9) {
                renderer.renderLine(context, "REBEL CAMPS", x + 40, j + 230);
            } else {
                renderer.renderLine(context, "REBEL CAMPS", x + 30, j + 230);
            }
            if (iv.questEntrySelected == 10) {
                renderer.renderLine(context, "REBEL OUTPOSTS", x + 40, j + 255);
            } else {
                renderer.renderLine(context, "REBEL OUTPOSTS", x + 30, j + 255);
            }
            if (iv.questEntrySelected == 11) {
                renderer.renderLine(context, "RELIC RUINS", x + 40, j + 280);
            } else {
                renderer.renderLine(context, "RELIC RUINS", x + 30, j + 280);
            }
            if (iv.questEntrySelected == 12) {
                renderer.renderLine(context, "TALLNECKS", x + 40, j + 305);
            } else {
                renderer.renderLine(context, "TALLNECKS", x + 30, j + 305);
            }
            if (iv.questEntrySelected == 13) {
                renderer.renderLine(context, "CAULDRONS", x + 40, j + 330);
            } else {
                renderer.renderLine(context, "CAULDRONS", x + 30, j + 330);
            }
            if (iv.questEntrySelected == 14) {
                renderer.renderLine(context, "COLLECTABLES", x + 40, j + 355);
            } else {
                renderer.renderLine(context, "COLLECTABLES", x + 30, j + 355);
            }
        }
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

}
