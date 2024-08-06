package pl.epsi.horizonItems;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import pl.epsi.player.inventory.CustomItem;
import pl.epsi.player.inventory.CustomItemSettings;
import pl.epsi.player.inventory.InventoryCategory;
import pl.epsi.player.inventory.ResourcesSubCategory;

public class ChillwaterItem extends CustomItem {

    public ChillwaterItem() {
        super(CustomItemSettings.create()
                .setRarity(1)
                .setCategory(InventoryCategory.RESOURCES)
                .setResourcesSubCategory(ResourcesSubCategory.AMMUNITION_RESOURCES)
                .setStackSize(50)
                .setTexture(new Identifier("horizoninmc", "inventory/items/chillwater")),
                Text.translatable("customItem.horizoninmc.chillwater"),
                Text.translatable("customItem.horizoninmc.chillwater.description"));
    }

}
