package pl.epsi.player;

import pl.epsi.player.inventory.CustomInventory;
import pl.epsi.player.quest.Quest;

public class CustomPlayer {

    private boolean visible;
    private final CustomInventory inventory = CustomInventory.getInstance();
    private Quest quest;
    private static CustomPlayer instance;
    private int dustAmount = 0;

    private CustomPlayer() {}

    public static CustomPlayer getInstance() {
        if (instance == null) instance = new CustomPlayer();
        return instance;
    }

    public void addDust(int amount) { this.dustAmount += amount; }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public CustomInventory getInventory() {
        return inventory;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public Quest getQuest() {
        return quest;
    }
}
