package pl.epsi.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilderStorage;
import org.spongepowered.asm.mixin.Shadow;
import pl.epsi.event.EventManager;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.epsi.interfaces.ISubtitleRenderer;

import static pl.epsi.event.UpdateListener.UpdateEvent;
import static pl.epsi.event.MinecraftQuitListener.MinecraftQuitEvent;
import static pl.epsi.event.MinecraftStartListener.MinecraftStartEvent;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin implements ISubtitleRenderer {

    @Shadow
    private BufferBuilderStorage bufferBuilders;
    private DrawContext context;

    @Inject(at = @At("HEAD"), method = "tick")
    private void onTick(CallbackInfo ci) {
        EventManager.getInstance().fire(UpdateEvent.INSTANCE);
    }

    @Inject(at = @At("HEAD"), method = "scheduleStop")
    private void scheduleStop(CallbackInfo ci) {
        MinecraftQuitEvent event = new MinecraftQuitEvent();
        EventManager.getInstance().fire(event);
    }

    @Inject(at = @At("HEAD"), method = "run")
    private void run(CallbackInfo ci) {
        MinecraftStartEvent event = new MinecraftStartEvent();
        EventManager.getInstance().fire(event);
    }

    @Inject(at = @At(value = "CONSTANT", args = { "stringValue=blit" }, ordinal = 0), method = "render")
    private void render(CallbackInfo ci) {
        DrawContext drawContext = new DrawContext(MinecraftClient.getInstance(), this.bufferBuilders.getEntityVertexConsumers());
        this.context = drawContext;
    }

    @Override
    public DrawContext getDrawContext() {
        return this.context;
    }
}
