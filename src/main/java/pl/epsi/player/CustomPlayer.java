package pl.epsi.player;

import pl.epsi.player.inventory.CustomInventory;

public class CustomPlayer {

    private boolean visible;
    private final CustomInventory inventory = CustomInventory.getInstance();

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public CustomInventory getInventory() {
        return inventory;
    }
}
