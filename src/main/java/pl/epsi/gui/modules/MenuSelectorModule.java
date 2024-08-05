package pl.epsi.gui.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import pl.epsi.util.InstancedValues;

import java.util.ArrayList;
import java.util.List;

public class MenuSelectorModule extends ContainerWidget {

    public List<ClickableWidget> children = new ArrayList<>();
    private final Identifier SELECT_LEFT = new Identifier("horizoninmc", "select_left");
    private final Identifier SELECT_RIGHT = new Identifier("horizoninmc", "select_right");
    private final Identifier INVENTORY = new Identifier("horizoninmc", "inventory_text");
    private final Identifier INVENTORY_SEL = new Identifier("horizoninmc", "inventory_text_selected");
    private final Identifier MAP = new Identifier("horizoninmc", "map_text");
    private final Identifier MAP_SEL = new Identifier("horizoninmc", "map_text_selected");
    private final Identifier SKILLS = new Identifier("horizoninmc", "skills_text");
    private final Identifier SKILLS_SEL = new Identifier("horizoninmc", "skills_text_selected");
    private final Identifier QUESTS = new Identifier("horizoninmc", "quests_text");
    private final Identifier QUESTS_SEL = new Identifier("horizoninmc", "quests_text_selected");
    private final Identifier NOTEBOOK = new Identifier("horizoninmc", "notebook_text");
    private final Identifier NOTEBOOK_SEL = new Identifier("horizoninmc", "notebook_text_selected");
    private final Identifier EXCLAMATION_POINT = new Identifier("horizoninmc", "exclamation_point");
    private int width;
    private int menuSelected;

    public MenuSelectorModule(int width, int y, int menuSelected) {
        super(width / 2, y, (width / 4) * 2, 40, Text.literal("MenuSelectorModule"));
        this.width = width;
        this.menuSelected = menuSelected;
        InstancedValues.getInstance().screenExclamationUpdate.add(0);
        InstancedValues.getInstance().screenExclamationUpdate.add(1);
        InstancedValues.getInstance().screenExclamationUpdate.add(3);
        InstancedValues.getInstance().screenExclamationUpdate.add(4);
    }

    @Override
    public List<? extends ClickableWidget> children() {
        return children;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        int j = this.getY();
        context.drawGuiTexture(SELECT_LEFT, this.width / 4, j, 24, 24);
        context.drawGuiTexture(SELECT_RIGHT, this.width - this.width / 4 - 25, j, 24, 24);

        if (this.menuSelected == 0) {
            context.drawGuiTexture(INVENTORY_SEL, this.width / 2 - 206, j + 2, 76, 24);
        } else {
            context.drawGuiTexture(INVENTORY, this.width / 2 - 206, j + 6, 76, 12);
        }
        if (this.menuSelected == 1) {
            context.drawGuiTexture(SKILLS_SEL, this.width / 2 - 97, j + 2, 50, 24);
        } else {
            context.drawGuiTexture(SKILLS, this.width / 2 - 97, j + 6, 50, 12);
        }
        if (this.menuSelected == 2) {
            context.drawGuiTexture(MAP_SEL, this.width / 2 - 27 / 2, j + 2, 27, 24);
        } else {
            context.drawGuiTexture(MAP, this.width / 2 - 27 / 2, j + 6, 27, 12);
        }
        if (this.menuSelected == 3) {
            context.drawGuiTexture(QUESTS_SEL, this.width / 2 + 47, j + 2, 52, 24);
        } else {
            context.drawGuiTexture(QUESTS, this.width / 2 + 47, j + 6, 52, 12);
        }
        if (this.menuSelected == 4) {
            context.drawGuiTexture(NOTEBOOK_SEL, this.width / 2 + 130, j + 2, 69, 24);
        } else {
            context.drawGuiTexture(NOTEBOOK, this.width / 2 + 130, j + 6, 69, 12);
        }

        InstancedValues.getInstance().screenExclamationUpdate.forEach((i) -> {
            if (i == 0) { context.drawGuiTexture(EXCLAMATION_POINT, this.width / 2 - 168 - 4, j + 23, 8, 8); }
            if (i == 1) { context.drawGuiTexture(EXCLAMATION_POINT, this.width / 2 - 72 - 4, j + 23, 8, 8); }
            if (i == 2) { context.drawGuiTexture(EXCLAMATION_POINT, this.width / 2 - 4, j + 23, 8, 8); }
            if (i == 3) { context.drawGuiTexture(EXCLAMATION_POINT, this.width / 2 + 73 - 4, j + 23, 8, 8); }
            if (i == 4) { context.drawGuiTexture(EXCLAMATION_POINT, this.width / 2 + 164 - 4, j + 23, 8, 8); }
        });

    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

}
