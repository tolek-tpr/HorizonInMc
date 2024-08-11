package pl.epsi.player.quest;

import pl.epsi.event.EventImpl;
import pl.epsi.event.EventManager;
import pl.epsi.event.UpdateListener;
import pl.epsi.player.CustomPlayer;

public class QuestListener extends EventImpl implements UpdateListener {

    private CustomPlayer player = CustomPlayer.getInstance();

    private QuestListener() {}

    private static QuestListener instance;

    public static QuestListener getInstance() {
        if (instance == null) instance = new QuestListener();
        return instance;
    }

    @Override
    public void onEnable() {
        EventManager.getInstance().add(UpdateListener.class, this);
    }

    @Override
    public void onDisable() {
        EventManager.getInstance().remove(UpdateListener.class, this);
    }

    @Override
    public void onUpdate() {
        QuestStep step = player.getCurrentQuest().getCurrentStep();
        if (!(step instanceof TravelStep)) return;
        ((TravelStep) step).onUpdate();
        player.getCurrentQuest().check();
    }

}
