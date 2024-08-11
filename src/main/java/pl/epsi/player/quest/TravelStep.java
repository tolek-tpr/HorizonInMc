package pl.epsi.player.quest;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.joml.Vector3d;
import pl.epsi.util.HorizonUtil;

import java.util.LinkedList;
import java.util.List;

public class TravelStep extends QuestStep {

    private List<Vector3d> points = new LinkedList<>();
    private MinecraftClient client = MinecraftClient.getInstance();
    private Integer currentPoint;
    private int detectRadius;

    public TravelStep(Text name, Text description, int detectRadius) {
        super(name, description);

        currentPoint = 0;
        this.detectRadius = detectRadius;
    }

    public void addPoint(Vector3d point) {
        points.add(point);
    }

    public Vector3d getCurrentPoint() {
        return points.get(currentPoint);
    }

    public void onUpdate() {
        if (client.player == null) return;
        Vector3d position = new Vector3d(client.player.getPos().x, client.player.getPos().y,
                client.player.getPos().z);

        if (HorizonUtil.isCoordinateInSphere(getCurrentPoint(), position, detectRadius)) {
            if (points.size() == currentPoint + 1) {
                this.setStatus(QuestStatus.STATUS_COMPLETED);
            } else {
                currentPoint += 1;
            }
        }
    }

}
