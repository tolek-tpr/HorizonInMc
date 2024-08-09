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

    public ArrayList<ClickableWidget> getChildren() {
        return children;
    }

    public QuestTypeSelectorModule(int x, int y, int height, boolean drawNames) {
        super(x, y, 100, height - 100, Text.literal("QuestTypeSelectorModule"));
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
        MatrixStack matrixStack = context.getMatrices();

        context.drawGuiTexture(SELECT_UP, this.width / 2 - 10, j, 20, 20);
        context.drawGuiTexture(SELECT_DOWN, this.width / 2 - 10, this.getHeight() - 10, 20, 20);

        renderEntries(matrixStack, context);
    }

    private void renderEntries(MatrixStack stack, DrawContext context) {
        stack.push();
        if (iv.questEntrySelected == 0) {
            stack.translate(x + 40, y + 30, 0);
        } else {
            stack.translate(x + 30, y + 30, 0);
        }
        stack.scale(1.5F, 1.5F, 0);
        context.drawTextWithShadow(tx, "ACTIVE", 0, 0, 0x3d423e);
        stack.pop();
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

}
