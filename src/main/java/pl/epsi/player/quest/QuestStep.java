package pl.epsi.player.quest;

import net.minecraft.text.Text;

public abstract class QuestStep {

    private QuestStatus status = QuestStatus.STATUS_PENDING;
    private boolean isMajor = false;
    private Text name;
    private Text description;

    public QuestStep(Text name, Text description) {
        this.name = name;
        this.description = description;
    }

    public Text getName() {
        return name;
    }

    public Text getDescription() {
        return description;
    }

    public QuestStatus getStatus() {
        return this.status;
    }

    public void setStatus(QuestStatus status) {
        this.status = status;
    }

    public boolean isMajor() {
        return isMajor;
    }

    public void announceCompleted() {
        System.out.println("STEP COMPLETED");
    }

    public void announceFailed() {
        System.out.println("STEP FAILED");
    }

}
