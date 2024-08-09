package pl.epsi.events;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import pl.epsi.event.EventManager;

import static pl.epsi.event.HudRenderCallbackListener.HudRenderCallbackEvent;

public class FabricEvents implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            HudRenderCallbackEvent event = new HudRenderCallbackEvent(drawContext, tickDelta);
            EventManager.getInstance().fire(event);
        });

    }

}
