package pl.epsi.events;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4d;
import org.joml.Vector3d;
import org.lwjgl.opengl.GL20;
import pl.epsi.HorizonInMc;
import pl.epsi.event.EventImpl;
import pl.epsi.event.EventManager;
import pl.epsi.event.HudRenderCallbackListener;
import pl.epsi.mixin.client.GameRendererInvoker;
import pl.epsi.player.CustomPlayer;
import pl.epsi.player.quest.*;
import pl.epsi.render.HorizonShader;
import pl.epsi.render.RenderUtils;
import pl.epsi.util.HorizonUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Path;
import java.util.Optional;

public class TravelToMarkerRenderer extends EventImpl implements HudRenderCallbackListener {

    private final MinecraftClient client = MinecraftClient.getInstance();
    private final CustomPlayer player = CustomPlayer.getInstance();
    private Quest selectedQuest = player.getCurrentQuest();
    private QuestStep step;

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
        this.selectedQuest = player.getCurrentQuest();
        if (selectedQuest == null) return;
        this.step = selectedQuest.getCurrentStep();
        if (step == null) return;
        if (step instanceof TravelStep)
            this.draw(context, tickDelta, (TravelStep) step);
    }

    public double getFarPlaceViewDistance() {
        return client.gameRenderer.getViewDistance() * 4D;
    }

    public void draw(DrawContext context, float tickDelta, TravelStep objective) {
        TextRenderer tx = MinecraftClient.getInstance().textRenderer;
        Camera camera = client.gameRenderer.getCamera();
        Vector3d point = objective.getCurrentPoint();
        Vector3d position = new Vector3d(point.x, point.y, point.z);
        double width = client.getWindow().getScaledWidth();
        double height = client.getWindow().getScaledHeight();
        Identifier i = new Identifier("horizoninmc", "exclamation_point");
        Vec3d posInternal = client.player.getPos();
        Vector3d playerPos = new Vector3d(posInternal.x, posInternal.y, posInternal.z);
        double distance = playerPos.distance(position);
        MatrixStack matrixStack = context.getMatrices();

        matrixStack.push();
        Matrix4d matrix = new Matrix4d();
        matrixStack.peek().getPositionMatrix().get(matrix);

        Matrix4d worldToScreenSpace = RenderUtils.getBasicProjectionMatrix(client, tickDelta, matrix);

        worldToScreenSpace.translateLocal(1, -1, 0);
        worldToScreenSpace.scaleLocal(width / 2, -height / 2, 1.0);

        worldToScreenSpace.rotateX(Math.toRadians(camera.getPitch()));
        worldToScreenSpace.rotateY(Math.toRadians(camera.getYaw() + 180.0F));
        worldToScreenSpace.translate(-camera.getPos().x, -camera.getPos().y, -camera.getPos().z);

        worldToScreenSpace.transformProject(position);

        if (position.x > width || position.y > height || position.z < 0 ||
            position.x < 0 || position.y < 0 || position.z > 1) return;
        context.drawGuiTexture(i, (int) position.x, (int) position.y, 16, 16);

        position.add(new Vector3d(-tx.getWidth(Math.round(distance) + " Steps") / 2 + 8, 0.5, 0));

        context.drawTextWithShadow(tx, Math.round(distance) + " Steps", (int) position.x, (int) position.y, 0xe3d7d7);
        matrixStack.pop();
    }

}
