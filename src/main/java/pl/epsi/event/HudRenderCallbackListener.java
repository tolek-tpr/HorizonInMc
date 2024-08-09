package pl.epsi.event;

import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;

public interface HudRenderCallbackListener extends Listener {

    void onRender(DrawContext context, float tickDelta);

    public static class HudRenderCallbackEvent extends Event<HudRenderCallbackListener> {
        private final DrawContext context;
        private final float tickDelta;

        public HudRenderCallbackEvent(DrawContext context, float tickDelta) {
            this.context = context;
            this.tickDelta = tickDelta;
        }

        @Override
        public void fire(ArrayList<HudRenderCallbackListener> listeners) {
            for(HudRenderCallbackListener listener : listeners)
                listener.onRender(context, tickDelta);
        }

        @Override
        public Class<HudRenderCallbackListener> getListenerType()
        {
            return HudRenderCallbackListener.class;
        }
    }




}
