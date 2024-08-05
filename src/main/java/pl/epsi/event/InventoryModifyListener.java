package pl.epsi.event;

import pl.epsi.player.inventory.CustomItem;

import java.util.ArrayList;

public interface InventoryModifyListener extends Listener {

    void onItemAdd(CustomItem item, int amount);
    void onItemRemove(CustomItem item, int amount);

    public static class ItemAddEvent extends Event<InventoryModifyListener> {
        private final CustomItem item;
        private final int amount;

        public ItemAddEvent(CustomItem item, int amount) {
            this.item = item;
            this.amount = amount;
        }

        @Override
        public void fire(ArrayList<InventoryModifyListener> listeners) {
            for(InventoryModifyListener listener : listeners)
                listener.onItemAdd(item, amount);
        }

        @Override
        public Class<InventoryModifyListener> getListenerType()
        {
            return InventoryModifyListener.class;
        }
    }

    public static class ItemRemoveEvent extends Event<InventoryModifyListener> {
        private final CustomItem item;
        private final int amount;

        public ItemRemoveEvent(CustomItem item, int amount) {
            this.item = item;
            this.amount = amount;
        }

        @Override
        public void fire(ArrayList<InventoryModifyListener> listeners) {
            for(InventoryModifyListener listener : listeners)
                listener.onItemRemove(item, amount);
        }

        @Override
        public Class<InventoryModifyListener> getListenerType()
        {
            return InventoryModifyListener.class;
        }
    }

}
