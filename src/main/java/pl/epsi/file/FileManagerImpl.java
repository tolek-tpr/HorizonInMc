package pl.epsi.file;

import net.minecraft.text.Text;
import pl.epsi.event.EventImpl;
import pl.epsi.event.EventManager;
import pl.epsi.event.MinecraftQuitListener;
import pl.epsi.event.MinecraftStartListener;
import pl.epsi.player.CustomPlayer;
import pl.epsi.player.quest.Quest;
import pl.epsi.player.quest.QuestCategory;
import pl.epsi.player.quest.QuestManager;

import java.util.ArrayList;
import java.util.HashMap;

public class FileManagerImpl extends EventImpl implements MinecraftStartListener, MinecraftQuitListener {

    @Override
    public void onEnable() {
        EventManager.getInstance().add(MinecraftStartListener.class, this);
        EventManager.getInstance().add(MinecraftQuitListener.class, this);
    }

    @Override
    public void onDisable() {
        EventManager.getInstance().remove(MinecraftStartListener.class, this);
        EventManager.getInstance().remove(MinecraftQuitListener.class, this);
    }

    @Override
    public void onQuit() {
        QuestInfo info = new QuestInfo(CustomPlayer.getInstance().getCurrentQuest());
        FileManager fm = new FileManager();

        fm.save(info, QuestManager.getInstance().getAvailableQuests(), new SettingsInfo());
    }

    private void setupFirstStart() {
        Quest mainQuest1 = new Quest(Text.literal("Reach for the Stars"),
                Text.literal("A long search has led Aloy to a mysterious ancient facility. " +
                        "Together with her friend Varl, she must comb through the ruins for a backup of GAIA — " +
                        "her last hope for repairing the terraforming system that can heal the world."),
                3, QuestCategory.MAIN);
        CustomPlayer.getInstance().setCurrentQuest(mainQuest1);
    }

    @Override
    public void onStart() {
        FileManager fm = new FileManager();
        SettingsInfo settingsInfo = fm.loadSettings();
        QuestInfo questInfo = fm.loadCurrentQuest();
        HashMap<QuestCategory, ArrayList<Quest>> allQuests = fm.loadAvailableQuests();

        if (allQuests != null) {
            QuestManager.getInstance().loadQuests(allQuests);
        }
        if (questInfo != null) {
            CustomPlayer.getInstance().setCurrentQuest(questInfo.quest);
        } else {
            this.setupFirstStart();
        }

    }
}
