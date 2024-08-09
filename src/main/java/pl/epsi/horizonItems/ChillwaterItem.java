package pl.epsi.horizonItems;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import pl.epsi.player.inventory.*;

public class ChillwaterItem extends ResourceItem {

    public ChillwaterItem() {
        super(CustomItemSettings.create()
                .setRarity(0)
                .setCategory(InventoryCategory.RESOURCES)
                .setResourcesSubCategory(ResourcesSubCategory.AMMUNITION_RESOURCES)
                .setStackSize(50)
                .setTexture(new Identifier("horizoninmc", "inventory/items/chillwater")),
                Text.translatable("customItem.horizoninmc.chillwater"),
                Text.translatable("customItem.horizoninmc.chillwater.description"),
                Text.translatable("customItem.horizoninmc.chillwater.sources"),
                Text.translatable("customItem.horizoninmc.chillwater.uses"));
    }

}
