package pl.epsi.player.inventory;

import net.minecraft.text.Text;

public abstract class ResourceItem extends CustomItem {

    private Text sources;
    private Text uses;

    public ResourceItem(CustomItemSettings settings, Text itemName, Text description, Text sources, Text uses) {
        super(settings, itemName, description);
        this.sources = sources;
        this.uses = uses;
    }

    public Text getSources() { return sources; }
    public Text getUses() { return uses; }

}
