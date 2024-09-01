package pl.epsi.player.quest;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public abstract class QuestStep {

    private QuestStatus status = QuestStatus.STATUS_PENDING;
    private boolean isMajor = false;
    private String name;
    private String description;
    private String type;

    public QuestStep(Text name, Text description, Class type) {
        this.name = name.getString();
        this.description = description.getString();
        this.type = type.getName();
    }

    public Text getName() {
        return Text.literal(name);
    }

    public Text getDescription() {
        return Text.literal(description);
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
    public void setMajor(boolean major) { this.isMajor = major; }

    public void announceCompleted() {
        System.out.println("STEP COMPLETED");
    }

    public void announceFailed() {
        System.out.println("STEP FAILED");
    }

}
