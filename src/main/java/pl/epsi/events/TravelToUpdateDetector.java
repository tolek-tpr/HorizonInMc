package pl.epsi.events;

import net.minecraft.client.MinecraftClient;
import pl.epsi.event.EventImpl;
import pl.epsi.event.EventManager;
import pl.epsi.event.UpdateListener;
import pl.epsi.player.CustomPlayer;
import pl.epsi.player.quest.QuestManager;
import pl.epsi.player.quest.TravelToObjective;
import pl.epsi.util.HorizonUtil;

public class TravelToUpdateDetector extends EventImpl implements UpdateListener {

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
        if (MinecraftClient.getInstance() == null || MinecraftClient.getInstance().player == null) return;
        double x = MinecraftClient.getInstance().player.getX();
        double y = MinecraftClient.getInstance().player.getY();
        double z = MinecraftClient.getInstance().player.getZ();

        QuestManager.getInstance().TRAVEL_TO.keySet().forEach(step -> {
            TravelToObjective objective = QuestManager.getInstance().TRAVEL_TO.get(step);
            if (CustomPlayer.getInstance().getQuest() != step.getQuest()) return;
            if (HorizonUtil.isCoordinateInSphere(new int[]{ objective.x, objective.y, objective.z }, objective.detectRadius, (int) x, (int) y, (int) z)) {
                objective.setCompleted(true);
            }
        });
    }

}
