package pl.epsi.player.quest;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestManager {

    public HashMap<QuestCategory, ArrayList<Quest>> availableQuests = new HashMap<>();
    public HashMap<QuestStep, TravelToObjective> TRAVEL_TO = new HashMap<>();
    private static QuestManager instance;

    private QuestManager() {}

    public static QuestManager getInstance() {
        if (instance == null) instance = new QuestManager();
        return instance;
    }

    public ArrayList<Quest> getQuestsForCategory(QuestCategory category) {
        return availableQuests.getOrDefault(category, new ArrayList<Quest>());
    }

}
