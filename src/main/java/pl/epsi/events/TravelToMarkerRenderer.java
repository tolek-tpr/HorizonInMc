package pl.epsi.events;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.Camera;
import net.minecraft.util.Identifier;
import org.joml.Matrix4d;
import org.joml.Vector3d;
import pl.epsi.event.EventImpl;
import pl.epsi.event.EventManager;
import pl.epsi.event.HudRenderCallbackListener;
import pl.epsi.mixin.client.GameRendererInvoker;
import pl.epsi.player.CustomPlayer;
import pl.epsi.player.quest.*;

public class TravelToMarkerRenderer extends EventImpl implements HudRenderCallbackListener {

    private final MinecraftClient client = MinecraftClient.getInstance();
    private final CustomPlayer player = CustomPlayer.getInstance();
    private Quest selectedQuest = player.getQuest();
    private QuestStep step;
    private TravelToObjective objective;

    @Override
    public void onEnable() {
        EventManager.getInstance().add(HudRenderCallbackListener.class, this);
    }

    @Override
    public void onDisable() {
        EventManager.getInstance().remove(HudRenderCallbackListener.class, this);
    }

    @Override
    public void onRender(DrawContext context, float tickDelta) {
        this.selectedQuest = player.getQuest();
        if (selectedQuest == null) return;
        this.step = selectedQuest.getSteps().get(selectedQuest.currentStep);
        if (step == null) return;
        if (step.getObjective() instanceof TravelToObjective) {
            this.objective = (TravelToObjective) step.getObjective();
        }
        if (objective == null) return;

        this.draw(context, tickDelta, objective);
    }

    public double getFarPlaceViewDistance() {
        return client.gameRenderer.getViewDistance() * 4D;
    }

    public void draw(DrawContext context, float tickDelta, TravelToObjective objective) {
        Camera camera = client.gameRenderer.getCamera();
        Vector3d position = new Vector3d(objective.x, objective.y, objective.z);
        Matrix4d worldToScreenSpace = new Matrix4d();
        double fov = ((GameRendererInvoker) client.gameRenderer)
                .horizon_getFov(camera, tickDelta, true);
        double aspect = (double) client.getWindow().getWidth() / client.getWindow().getHeight();
        double width = client.getWindow().getScaledWidth();
        double height = client.getWindow().getScaledHeight();
        Identifier i = new Identifier("horizoninmc", "exclamation_point");

        worldToScreenSpace.setPerspective(Math.toRadians(fov), aspect,
                0.05, getFarPlaceViewDistance());

        worldToScreenSpace.translateLocal(1, -1, 0);
        worldToScreenSpace.scaleLocal(width / 2, -height / 2, 1.0);

        worldToScreenSpace.rotateX(Math.toRadians(camera.getPitch()));
        worldToScreenSpace.rotateY(Math.toRadians(camera.getYaw() + 180.0F));
        worldToScreenSpace.translate(-camera.getPos().x, -camera.getPos().y, -camera.getPos().z);

        worldToScreenSpace.transformProject(position);

        if (position.x > width || position.y > height || position.z < 0 ||
            position.x < 0 || position.y < 0 || position.z > 1) return;
        context.drawGuiTexture(i, (int) position.x, (int) position.y, 16, 16);
    }

}
