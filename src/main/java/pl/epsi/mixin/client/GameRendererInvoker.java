package pl.epsi.mixin.client;


import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRenderer.class)
public interface GameRendererInvoker {

    @Invoker("getFov")
    double horizon_getFov(Camera camera, float tickDelta, boolean changingFov);

}
