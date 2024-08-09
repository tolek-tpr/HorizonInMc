package pl.epsi.player.quest;

public abstract class QuestObjective {

    private boolean isCompleted;
    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
