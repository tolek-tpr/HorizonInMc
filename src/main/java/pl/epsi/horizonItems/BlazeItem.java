package pl.epsi.horizonItems;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import pl.epsi.player.inventory.*;

public class BlazeItem extends ResourceItem {

    public BlazeItem() {
        super(CustomItemSettings.create()
                .setRarity(0)
                .setCategory(InventoryCategory.RESOURCES)
                .setResourcesSubCategory(ResourcesSubCategory.AMMUNITION_RESOURCES)
                .setTexture(new Identifier("horizoninmc", "inventory/items/blaze_canister")),
                //.setStackSize()
                Text.translatable("customItem.horizoninmc.blaze"),
                Text.translatable("customItem.horizoninmc.blaze.description"),
                Text.translatable("customItem.horizoninmc.blaze.sources"),
                Text.translatable("customItem.horizoninmc.blaze.uses"));
    }



}
