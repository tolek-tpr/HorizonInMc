package pl.epsi.gui.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import pl.epsi.player.quest.Quest;
import pl.epsi.player.quest.QuestStatus;

import java.util.ArrayList;
import java.util.List;

public class QuestSlotModule extends ContainerWidget {

    public ArrayList<ClickableWidget> children = new ArrayList<>();
    private Quest quest;
    private Identifier ITEM_BACKDROP = new Identifier("horizoninmc", "quests/quest_background");
    private Identifier ITEM_BACKDROP_COMPLETED = new Identifier("horizoninmc", "quests/quest_background_completed");
    private final TextRenderer tx = MinecraftClient.getInstance().textRenderer;
    public static final int scaleX = 192;
    public static final int scaleY = 48;

    public QuestSlotModule(int x, int y, Quest quest) {
        super(x, y, 48, 48, Text.literal("QuestSlotModule"));
        this.quest = quest;
    }

    @Override
    public List<? extends ClickableWidget> children() {
        return children;
    }

    public Quest getQuest() {
        return quest;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        int y = this.getY();
        int x = this.getX();

        if (quest.getStatus() == QuestStatus.STATUS_COMPLETED) {
            context.drawGuiTexture(ITEM_BACKDROP_COMPLETED, x, y, scaleX, scaleY);
            context.drawTextWithShadow(tx, quest.getName(), x + 40, y + 10, 0x5e5d5c);
            context.drawTextWithShadow(tx, "LEVEL " + quest.getMinLevel(), x + 40, y + tx.fontHeight + 12, 0x5e5d5c);
        } else {
            context.drawGuiTexture(ITEM_BACKDROP, x, y, scaleX, scaleY);
            context.drawTextWithShadow(tx, quest.getName(), x + 40, y + 10, 0xe3d7d7);
            context.drawTextWithShadow(tx, "LEVEL " + quest.getMinLevel(), x + 40, y + tx.fontHeight + 12, 0xe3d7d7);
        }

        // the logo thing drawing thing idfk
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

}
