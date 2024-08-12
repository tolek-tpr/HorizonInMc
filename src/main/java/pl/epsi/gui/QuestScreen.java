package pl.epsi.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import pl.epsi.gui.modules.*;
import pl.epsi.util.HorizonUtil;
import pl.epsi.util.InstancedValues;

import java.util.HashMap;

public class QuestScreen extends MainMenuScreenE {

    private int menuSelected;
    private QuestTypeSelectorModule qtsm;
    private HashMap<QuestSlotModule, int[]> slotPositions = new HashMap<>();
    private QuestSlotModule selectedQuest;
    private ScrollableListModule slm;
    private boolean subCategoryEntered = false;
    private final Identifier ENTER_ICON = new Identifier("horizoninmc", "selection/enter");
    private final Identifier ESCAPE_ICON = new Identifier("horizoninmc", "selection/escape");
    private final Identifier QUEST_SELECTED = new Identifier("horizoninmc", "quests/quest_selected");

    public QuestScreen(int menuSelected) {
        super(Text.translatable("screen.horizoninmc.quest_screen"));
        this.menuSelected = menuSelected;
    }

    @Override
    public void init() {
        InstancedValues.getInstance().questEntrySelected = 0;
        this.subCategoryEntered = false;

        MenuSelectorModule msm = new MenuSelectorModule(width, 20, menuSelected);
        addDrawableChild(msm);
        msm.children().forEach(this::addDrawableChild);

        qtsm = new QuestTypeSelectorModule(30, 57, height, true);
        addDrawableChild(qtsm);
        qtsm.children().forEach(this::addDrawableChild);

        slm = new ScrollableListModule(MinecraftClient.getInstance(), InventorySlotModule.scale * 4 + 40, InventorySlotModule.scale * 6, 80, 56);
        slm.setRenderBackground(false);
        slm.setX(200);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        if (!this.subCategoryEntered) {
            context.drawGuiTexture(ENTER_ICON, 70, height - 26, 26, 16);
            context.drawTextWithShadow(textRenderer, "Select", 101, height - 19 - textRenderer.fontHeight / 2, 0xd2e2e9);
        } else {
            context.drawGuiTexture(ESCAPE_ICON, 70, height - 26, 16, 16);
            context.drawTextWithShadow(textRenderer, "Exit", 101, height - 19 - textRenderer.fontHeight / 2, 0xd2e2e9);
        }

        if (!this.subCategoryEntered) return;
        if (selectedQuest == null) {
            slotPositions.keySet().forEach((slot) -> {
                slotPositions.replace(slot, new int[]{slot.getX(), slot.getY(), slot.getX() + QuestSlotModule.scale,
                        slot.getY() + QuestSlotModule.scale});
                if (HorizonUtil.isCoordinateInsideSquare(slotPositions.get(slot), mouseX, mouseY)) {
                    context.drawGuiTexture(QUEST_SELECTED, slot.getX() - 12, slot.getY() - 12,
                            QuestSlotModule.scale + 24, QuestSlotModule.scale + 24);
                }
            });
        } else {
            context.drawGuiTexture(QUEST_SELECTED, this.selectedQuest.getX() - 12, this.selectedQuest.getY() - 12,
                    QuestSlotModule.scale + 24, QuestSlotModule.scale + 24);
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ENTER && !this.subCategoryEntered) {
            slm.setX(100);
            redrawSelector(new QuestTypeSelectorModule(30, 57, height, false));
            this.subCategoryEntered = true;
        }
        if (keyCode == GLFW.GLFW_KEY_ESCAPE && this.subCategoryEntered) {
            slm.setX(200);
            redrawSelector(new QuestTypeSelectorModule(30, 57, height, true));
            this.subCategoryEntered = false;
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_W || keyCode == GLFW.GLFW_KEY_S) {
            //if (this.subCategoryEntered) return super.keyPressed(keyCode, scanCode, modifiers);
            //handleInventoryRender(InstancedValues.getInstance().inventoryEntrySelected);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void redrawSelector(QuestTypeSelectorModule qtsm) {
        remove(this.qtsm);
        this.qtsm.children().forEach(this::remove);
        this.qtsm = qtsm;
        addDrawableChild(qtsm);
        qtsm.children().forEach(this::addDrawableChild);
    }

    @Override
    public void close() {
        client.setScreen(null);
    }

    public int getMenuSelected() { return this.menuSelected; }

}
