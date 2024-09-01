package pl.epsi.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import org.joml.Matrix4d;
import pl.epsi.mixin.client.GameRendererInvoker;

public class RenderUtils {

    public static Matrix4d getBasicProjectionMatrix(MinecraftClient client, float tickDelta) {
        Camera camera = client.gameRenderer.getCamera();
        double fov = ((GameRendererInvoker) client.gameRenderer)
                .horizon_getFov(camera, tickDelta, true);
        double aspect = (double) client.getWindow().getWidth() / client.getWindow().getHeight();
        Matrix4d projectionMatrix = new Matrix4d();

        projectionMatrix.setPerspective(Math.toRadians(fov), aspect,
                0.05, getFarPlaceViewDistance(client));

        return projectionMatrix;
    }

    public static Matrix4d getBasicProjectionMatrix(MinecraftClient client, float tickDelta, Matrix4d matrix) {
        Camera camera = client.gameRenderer.getCamera();
        double fov = ((GameRendererInvoker) client.gameRenderer)
                .horizon_getFov(camera, tickDelta, true);
        double aspect = (double) client.getWindow().getWidth() / client.getWindow().getHeight();

        matrix.setPerspective(Math.toRadians(fov), aspect,
                0.05, getFarPlaceViewDistance(client));

        return matrix;
    }

    private static double getFarPlaceViewDistance(MinecraftClient client) {
        return client.gameRenderer.getViewDistance() * 4D;
    }

}
