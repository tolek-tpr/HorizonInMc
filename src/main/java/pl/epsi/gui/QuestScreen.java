package pl.epsi.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import pl.epsi.gui.modules.*;
import pl.epsi.interfaces.IScheduler;
import pl.epsi.player.CustomPlayer;
import pl.epsi.player.quest.Quest;
import pl.epsi.player.quest.QuestCategory;
import pl.epsi.player.quest.QuestManager;
import pl.epsi.player.quest.QuestStatus;
import pl.epsi.util.HorizonUtil;
import pl.epsi.util.InstancedValues;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestScreen extends MainMenuScreenE {

    private final int menuSelected;
    private QuestTypeSelectorModule qtsm;
    private final HashMap<QuestSlotModule, int[]> slotPositions = new HashMap<>();
    private final InstancedValues iv = InstancedValues.getInstance();
    private boolean internalChangeSelectText = false;
    private QuestSlotModule selectedQuest;
    private final ArrayList<ClickableWidget> toRemove = new ArrayList<>();
    private QuestDescriptionModule questDescriptionModule;
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
        iv.questEntrySelected = 0;
        this.subCategoryEntered = false;

        MenuSelectorModule msm = new MenuSelectorModule(width, 20, menuSelected);
        addDrawableChild(msm);
        msm.children().forEach(this::addDrawableChild);

        qtsm = new QuestTypeSelectorModule(30, 57, height, true);
        addDrawableChild(qtsm);
        qtsm.children().forEach(this::addDrawableChild);

        slm = new ScrollableListModule(MinecraftClient.getInstance(), QuestSlotModule.scaleX + 2, this.height - 100, 80, 56);
        slm.setRenderBackground(false);
        slm.setX(250);
        slm.setScrollbarPosition(slm.getX() + QuestSlotModule.scaleX + 10);

        renderQuest(iv.questEntrySelected);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        if (!this.subCategoryEntered) {
            context.drawGuiTexture(ENTER_ICON, 30, height - 26, 26, 16);
            context.drawTextWithShadow(textRenderer, "Select", 61, height - 19 - textRenderer.fontHeight / 2, 0xd2e2e9);
        } else {
            context.drawGuiTexture(ESCAPE_ICON, 30, height - 26, 16, 16);
            context.drawTextWithShadow(textRenderer, "Exit", 51, height - 19 - textRenderer.fontHeight / 2, 0xd2e2e9);

            if (iv.questEntrySelected != 0 && this.selectedQuest != null) {
                int i = 51 + textRenderer.getWidth("Exit");
                context.drawGuiTexture(ENTER_ICON, i + 10, height - 26, 26, 16);
                if (this.internalChangeSelectText) {
                    context.drawTextWithShadow(textRenderer, "Quest Selected!", i + 41, height - 19 - textRenderer.fontHeight / 2, 0xc7b37b);
                } else {
                    context.drawTextWithShadow(textRenderer, "Select Quest", i + 41, height - 19 - textRenderer.fontHeight / 2, 0xd2e2e9);
                }
            }
        }

        if (!this.subCategoryEntered) return;
        if (selectedQuest == null) {
            boolean isAnyHovered = false;
            for (QuestSlotModule module : slotPositions.keySet()) {
                slotPositions.replace(module, new int[]{module.getX(), module.getY(), module.getX() + QuestSlotModule.scaleX,
                        module.getY() + QuestSlotModule.scaleY});
                if (HorizonUtil.isCoordinateInsideSquare(slotPositions.get(module), mouseX, mouseY)) {
                    context.drawGuiTexture(QUEST_SELECTED, module.getX() - 2, module.getY() - 2,
                            QuestSlotModule.scaleX + 4, QuestSlotModule.scaleY + 4);
                    drawDescriptionWidget(module.getQuest());
                    isAnyHovered = true;
                }
            }
            if (!isAnyHovered) {
                remove(this.questDescriptionModule);
                this.questDescriptionModule = null;
            }
        } else {
            context.drawGuiTexture(QUEST_SELECTED, this.selectedQuest.getX() - 2, this.selectedQuest.getY() - 2,
                    QuestSlotModule.scaleX + 4, QuestSlotModule.scaleY + 4);
            drawDescriptionWidget(this.selectedQuest.getQuest());
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ENTER && !this.subCategoryEntered) {
            slm.setX(150);
            slm.setScrollbarPosition(slm.getX() + QuestSlotModule.scaleX + 10);
            redrawSelector(new QuestTypeSelectorModule(30, 57, height, false));
            renderQuest(iv.questEntrySelected);
            this.subCategoryEntered = true;
        }
        if (keyCode == GLFW.GLFW_KEY_ENTER && this.subCategoryEntered && this.selectedQuest != null) {
            CustomPlayer.getInstance().setCurrentQuest(selectedQuest.getQuest());
            this.internalChangeSelectText = true;
            if (this.client == null) { this.client = MinecraftClient.getInstance(); }
            ((IScheduler) client).scheduleNonRepeating(20, (b) -> { this.internalChangeSelectText = false; });
        }
        if (keyCode == GLFW.GLFW_KEY_ESCAPE && this.subCategoryEntered) {
            slm.setX(250);
            slm.setScrollbarPosition(slm.getX() + QuestSlotModule.scaleX + 10);
            redrawSelector(new QuestTypeSelectorModule(30, 57, height, true));
            renderQuest(iv.questEntrySelected);
            this.subCategoryEntered = false;
            remove(questDescriptionModule);
            this.questDescriptionModule = null;
            this.selectedQuest = null;
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_W || keyCode == GLFW.GLFW_KEY_S) {
            if (this.subCategoryEntered) return super.keyPressed(keyCode, scanCode, modifiers);
            renderQuest(iv.questEntrySelected);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.subCategoryEntered) return super.mouseClicked(mouseX, mouseY, button);
        slotPositions.keySet().forEach((module) -> {
            slotPositions.replace(module, new int[]{module.getX(), module.getY(), module.getX() + QuestSlotModule.scaleX,
                    module.getY() + QuestSlotModule.scaleY});
            if (HorizonUtil.isCoordinateInsideSquare(slotPositions.get(module), (int) mouseX, (int) mouseY)) {
                if (this.selectedQuest == module) {
                    remove(questDescriptionModule);
                    this.questDescriptionModule = null;
                    this.selectedQuest = null;
                } else {
                    this.selectedQuest = module;
                    drawDescriptionWidget(module.getQuest());
                }
            }
        });

        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void drawDescriptionWidget(Quest quest) {
        int x = slm.getX() + slm.getWidth() + 20;

        if (questDescriptionModule != null) {
            if (questDescriptionModule.getQuest() == quest) return;
            remove(questDescriptionModule);
        }
        questDescriptionModule = new QuestDescriptionModule(x, width, height, quest);
        addDrawableChild(questDescriptionModule);
    }

    private void redrawSelector(QuestTypeSelectorModule qtsm) {
        remove(this.qtsm);
        this.qtsm.children().forEach(this::remove);
        this.qtsm = qtsm;
        addDrawableChild(qtsm);
        qtsm.children().forEach(this::addDrawableChild);
    }

    public ClickableWidget addChildAndRemove(ClickableWidget widget) {
        toRemove.add(widget);
        return addDrawableChild(widget);
    }

    private void renderQuest(int selectedEntry) {
        toRemove.forEach(this::remove);
        toRemove.clear();
        remove(slm);
        this.selectedQuest = null;
        this.slotPositions.clear();

        int prevX = slm.getX();
        int prevScrollbarPos = slm.getScrollbarPosition();
        slm = new ScrollableListModule(MinecraftClient.getInstance(), QuestSlotModule.scaleX + 2, this.height - 100, 80, 56);
        slm.setX(prevX);
        slm.setRenderBackground(false);
        slm.setScrollbarPosition(prevScrollbarPos);

        if (selectedEntry == 0) {
            // 70
            ActiveQuestModule module = new ActiveQuestModule(slm.getX(), this.width, this.height);
            addChildAndRemove(module);
        } else {
            QuestCategory category = HorizonUtil.getQuestCategoryById(selectedEntry);
            ArrayList<Quest> notCompleted = new ArrayList<Quest>();
            ArrayList<Quest> completed = new ArrayList<Quest>();

            for (Quest quest : QuestManager.getInstance().getQuestsForCategory(category)) {
                if (quest.getStatus() == QuestStatus.STATUS_COMPLETED) {
                    completed.add(quest);
                } else { notCompleted.add(quest); }
            }

            if (!notCompleted.isEmpty()) {
                slm.addRow(new SeparatorModule(slm.getX(), 0, QuestSlotModule.scaleX, 48,
                        Text.literal(HorizonUtil.getQuestCategoryName(category) + "s"), 0xffe3d7d7));
                for (Quest q : notCompleted) {
                    QuestSlotModule slot = new QuestSlotModule(slm.getX(), 0, q);
                    slm.addRow(slot);
                    slotPositions.put(slot, new int[]{ slot.getX(), slot.getY(), slot.getX() + QuestSlotModule.scaleX,
                            slot.getY() + QuestSlotModule.scaleY });
                }
            }

            slm.addRow(new SeparatorModule(slm.getX(), 100, QuestSlotModule.scaleX, 48,
                    Text.literal("COMPLETED QUESTS"), 0xff5e5d5c));
            for (Quest q : completed) {
                QuestSlotModule slot = new QuestSlotModule(slm.getX(), 0, q);
                slm.addRow(slot);
                slotPositions.put(slot, new int[]{ slot.getX(), slot.getY(), slot.getX() + QuestSlotModule.scaleX,
                        slot.getY() + QuestSlotModule.scaleY });
            }
            addChildAndRemove(slm);
        }
    }

    @Override
    public void close() {
        client.setScreen(null);
    }
    
    @Override
    public void handleSubGroupChange(int keyCode) {
        switch (keyCode) {
            case GLFW.GLFW_KEY_W ->  {
                if (this.subCategoryEntered) return;
                if (iv.questEntrySelected == 0) {
                    iv.questEntrySelected = 14;
                } else {
                    iv.questEntrySelected--;
                }
            }
            case GLFW.GLFW_KEY_S -> {
                if (this.subCategoryEntered) return;
                if (iv.questEntrySelected == 14) {
                    iv.questEntrySelected = 0;
                } else {
                    iv.questEntrySelected++;
                }
            }
        }
    }

    @Override
    public boolean isSubGroupEntered() {
        return this.subCategoryEntered;
    }

    public int getMenuSelected() { return this.menuSelected; }

}
