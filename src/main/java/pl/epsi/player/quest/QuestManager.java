package pl.epsi.player.quest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestManager {

    private static QuestManager instance;
    private QuestManager() {}

    public static QuestManager getInstance() {
        if (instance == null) instance = new QuestManager();
        return instance;
    }

    private HashMap<QuestCategory, ArrayList<Quest>> availableQuests = new HashMap<>();

    public HashMap<QuestCategory, ArrayList<Quest>> getAvailableQuests() {
        return availableQuests;
    }

    public void loadQuests(HashMap<QuestCategory, ArrayList<Quest>> quests) {
        for (QuestCategory c : quests.keySet()) {
            for (Quest q : quests.get(c)) {
                this.addQuestToCategory(c, q);
            }
        }
    }

    public ArrayList<Quest> getQuestsForCategory(QuestCategory category) {
        return availableQuests.getOrDefault(category, new ArrayList<>());
    }

    public void addQuestToCategory(QuestCategory category, Quest quest) {
        ArrayList<Quest> tempList = availableQuests.getOrDefault(category, new ArrayList<>());
        tempList.add(quest);
        availableQuests.put(category, tempList);
    }

}
