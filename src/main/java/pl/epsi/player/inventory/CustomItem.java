package pl.epsi.player.inventory;

import net.minecraft.text.Text;

public abstract class CustomItem {

    private CustomItemSettings settings;
    private Text itemName;
    private Text description;

    public CustomItem(CustomItemSettings settings, Text itemName, Text description) {
        this.settings = settings;
        this.itemName = itemName;
        this.description = description;
    }

    public CustomItemSettings getSettings() {
        return settings;
    }

    public Text getItemName() {
        return itemName;
    }
}
