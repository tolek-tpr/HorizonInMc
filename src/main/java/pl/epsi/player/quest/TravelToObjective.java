package pl.epsi.player.quest;

public class TravelToObjective extends QuestObjective {

    public int x;
    public int y;
    public int z;
    public int detectRadius;
    private QuestStep parentStep;
    private boolean isCompleted = false;

    public TravelToObjective(int x, int y, int z, int detectRadius, QuestStep parentStep) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.detectRadius = detectRadius;
        this.parentStep = parentStep;
        register();
    }

    @Override
    public void setCompleted(boolean completed) {
        if (completed) QuestManager.getInstance().TRAVEL_TO.remove(parentStep.getQuest());
        this.isCompleted = completed;
        this.parentStep.setCompleted(completed);
    }

    private void register() {
        QuestManager.getInstance().TRAVEL_TO.put(parentStep, this);
    }

}
