package pl.epsi.horizonItems;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import pl.epsi.player.inventory.*;

public class NoraLegacySpear extends SpearItem {

    public NoraLegacySpear() {
        super(CustomItemSettings.create()
                        .setStackSize(1)
                        .setCategory(InventoryCategory.WEAPONS)
                        .setRarity(2)
                        .setTexture(new Identifier("horizoninmc", "inventory/items/nora_legacy_spear")),
                Text.translatable("customItem.horizoninmc.noraLegacySpear"),
                Text.translatable("customItem.horizoninmc.noraLegacySpear.description"),
                SpearSettings.create()
                        .setDamage(100)
                        .setChargeSpeed(100)
                        .setDischargeDamage(100)
                        .setEnergizedDuration(100)
                        .setResonatorBlastDamage(100)
                        .setCriticalStrikeDamage(100)
                        .setSilentStrikeDamage(100)
                        .setPowerAttackDamage(100),
                WeaponSubCategory.SPEAR);
    }

}
