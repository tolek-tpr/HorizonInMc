package pl.epsi.player.inventory;

import net.minecraft.text.Text;

public abstract class WeaponItem extends CustomItem {

    private WeaponSubCategory weaponCategory;

    public WeaponItem(CustomItemSettings settings, Text itemName, Text description, WeaponSubCategory weaponCategory) {
        super(settings, itemName, description);
        this.weaponCategory = weaponCategory;
    }

    public WeaponSubCategory getWeaponCategory() {
        return this.weaponCategory;
    }

}
