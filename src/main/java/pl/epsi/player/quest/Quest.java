package pl.epsi.player.quest;

import net.minecraft.text.Text;
import pl.epsi.player.CustomPlayer;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Quest {

    ArrayList<QuestStep> steps = new ArrayList<>();
    private Text name;
    private QuestCategory category;
    private Text description;
    private int level;
    private Consumer<Integer> completionLambda = (e) -> {};
    public int currentStep = 0;

    public Quest(Text name, Text description, int level, QuestCategory category) {
        this.name = name;
        this.description = description;
        this.level = level;
        this.category = category;
    }

    public void update() {
        boolean e = true;

        for (QuestStep step : steps) {
            if (!step.isCompleted()) e = false;
        }

        if (e) complete();
    }

    public void complete() {
        completionLambda.accept(0);
        //if (CustomPlayer.getInstance().getQuest() == this) CustomPlayer.getInstance().setQuest(null);
    }

    public Text getName() {
        return name;
    }

    public Quest addStep(QuestStep e) {
        this.steps.add(e);
        return this;
    }

    public ArrayList<QuestStep> getSteps() {
        return steps;
    }

    public Text getDescription() {
        return description;
    }

    public int getLevel() {
        return level;
    }

    public void setConsumer(Consumer<Integer> completionLambda) {
        this.completionLambda = completionLambda;
    }

}
