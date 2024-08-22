package pl.epsi.player.quest;

import net.minecraft.text.Text;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestManagerTest {

    @Test
    void addQuestToCategory() {
        System.out.println(QuestManager.getInstance().getQuestsForCategory(QuestCategory.MAIN));
        new Quest(Text.literal("name"),
                Text.literal("desc"), 4, QuestCategory.MAIN);
        new Quest(Text.literal("nam3"),
                Text.literal("desc"), 4, QuestCategory.MAIN);
        System.out.println(QuestManager.getInstance().getQuestsForCategory(QuestCategory.MAIN));
    }
}