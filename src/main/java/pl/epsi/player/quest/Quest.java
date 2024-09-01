package pl.epsi.player.quest;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.LinkedList;

public class Quest {
    public LinkedList<QuestStep> steps = new LinkedList<>();
    public ArrayList<Reward> rewards = new ArrayList<>();
    private QuestStatus status;
    private Integer currentStep = 0;
    private int minLevel;
    private String name;
    private String description;
    private QuestCategory category;

    public Quest(Text name, Text description, int minLevel, QuestCategory category) {
        this.name = name.getString();
        this.description = description.getString();
        this.minLevel = minLevel;
        this.category = category;

        QuestManager.getInstance().addQuestToCategory(category, this);
    }

    public QuestStep getCurrentStep() {
        if (steps == null || steps.isEmpty()) return null;
        return steps.get(currentStep);
    }

    public void advanceStep() {
        if (steps.size() == currentStep + 1) {
            this.status = QuestStatus.STATUS_COMPLETED;
        } else {
            currentStep++;
        }
    }

    public void failQuest() {
        this.status = QuestStatus.STATUS_FAILED;
    }

    public void announceCompleted() {
        System.out.println("QUEST COMPLETED");
    }

    public void announceFailed() {
        System.out.println("QUEST FAILED");
    }

    public QuestStatus getStatus() { return this.status; }

    public void check() {
        if (this.getStatus() != QuestStatus.STATUS_PENDING) return;
        QuestStep s = steps.get(currentStep);
        switch (s.getStatus()) {
            case STATUS_COMPLETED :
                if (s.isMajor()) s.announceCompleted();
                this.advanceStep();
                break;
            case STATUS_FAILED :
                if (s.isMajor()) s.announceFailed();
                this.failQuest();
                break;
            default:
                break;
        }
        if (this.getStatus() == QuestStatus.STATUS_COMPLETED) this.announceCompleted();
        if (this.getStatus() == QuestStatus.STATUS_FAILED) this.announceFailed();
    }

    public void setStatus(QuestStatus status) {
        this.status = status;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public Text getName() {
        return Text.literal(name);
    }

    public void setName(Text name) {
        this.name = name.getString();
    }

    public Text getDescription() {
        return Text.literal(description);
    }

    public void setDescription(Text description) {
        this.description = description.getString();
    }

    public QuestCategory getCategory() {
        return category;
    }

    public void setCategory(QuestCategory category) {
        this.category = category;
    }

}
