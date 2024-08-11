import net.minecraft.text.Text;
import org.junit.jupiter.api.Test;
import pl.epsi.player.CustomPlayer;
import pl.epsi.player.quest.Quest;
import pl.epsi.player.quest.QuestCategory;
import pl.epsi.player.quest.QuestStatus;
import pl.epsi.player.quest.TravelStep;

public class QuestTest {

    @Test
    public void createQuestTest() {
        Quest quest = new Quest(Text.literal("a"), Text.literal("b"), 3, QuestCategory.MAIN);
        TravelStep step = new TravelStep(Text.literal("c"), Text.literal("d"), 2);

        quest.steps.add(step);
        CustomPlayer.getInstance().setCurrentQuest(quest);
        step.setStatus(QuestStatus.STATUS_COMPLETED);
        quest.check();
    }

}
