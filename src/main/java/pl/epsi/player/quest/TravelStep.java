package pl.epsi.player.quest;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.joml.Vector3d;
import pl.epsi.util.HorizonUtil;

import java.util.LinkedList;
import java.util.List;

public class TravelStep extends QuestStep {

    private LinkedList<Vector3d> points = new LinkedList<>();
    private Integer currentPoint;
    private int detectRadius;

    public TravelStep(Text name, Text description, int detectRadius) {
        super(name, description, TravelStep.class);
        currentPoint = 0;
        this.detectRadius = detectRadius;
    }

    public void setPoints(LinkedList<Vector3d> points) { this.points = points; }
    public void setCurrentPoint(int point) { this.currentPoint = point; }
    public void addPoint(Vector3d point) {
        points.add(point);
    }

    public Vector3d getCurrentPoint() {
        return points.get(currentPoint);
    }

    public void onUpdate() {
        MinecraftClient client = MinecraftClient.getInstance();

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
