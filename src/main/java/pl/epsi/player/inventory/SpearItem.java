package pl.epsi.player.inventory;

import net.minecraft.text.Text;

public abstract class SpearItem extends WeaponItem {

    private SpearSettings spearSettings;

    public SpearItem(CustomItemSettings settings, Text itemName, Text description, SpearSettings spearSettings, WeaponSubCategory category) {
        super(settings, itemName, description, category);
        this.spearSettings = spearSettings;
    }

    public SpearSettings getSpearSettings() {
        return this.spearSettings;
    }

}
