package pl.epsi.player.quest;

import net.minecraft.text.Text;

public class QuestStep {

    private QuestObjective objective;
    private boolean isCompleted;
    private Quest parent;
    private Text name;
    private Text description;

    public QuestStep(Quest parent, Text name, Text description) {
        this.name = name;
        this.description = description;
        this.parent = parent;
    }

    public Quest getQuest() {
        return this.parent;
    }

    public void setObjective(QuestObjective objective) {
        this.objective = objective;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
        this.parent.update();
    }

    public QuestObjective getObjective() { return this.objective; }

}
