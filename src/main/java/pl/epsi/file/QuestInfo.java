package pl.epsi.file;

import pl.epsi.player.quest.Quest;

public class QuestInfo extends FileInfo {

    public Quest quest;

    public QuestInfo(Quest quest) {
        this.quest = quest;
    }

}
