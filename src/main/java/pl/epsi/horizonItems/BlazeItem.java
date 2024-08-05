package pl.epsi.horizonItems;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import pl.epsi.player.inventory.CustomItem;
import pl.epsi.player.inventory.CustomItemSettings;
import pl.epsi.player.inventory.InventoryCategory;
import pl.epsi.player.inventory.ResourcesSubCategory;

public class BlazeItem extends CustomItem {

    public BlazeItem() {
        super(CustomItemSettings.create()
                .setRarity(0)
                .setCategory(InventoryCategory.RESOURCES)
                .setResourcesSubCategory(ResourcesSubCategory.AMMUNITION_RESOURCES)
                .setTexture(new Identifier("horizoninmc", "inventory/items/blaze_canister")),
                //.setStackSize()
                Text.translatable("customItem.horizoninmc.blaze"),
                Text.translatable("customItem.horizoninmc.blaze.description"));
    }



}
