package pl.epsi.player.inventory;

import net.minecraft.util.Identifier;

public class CustomItemSettings {

    private int stackSize = -1;
    private ResourcesSubCategory resourcesSubCategory;
    private int rarity = 0; /* 0 = common, 1 = uncommon, 2 = rare, 3 = very rare, 4 = legendary */
    private Identifier texture;
    private InventoryCategory category;

    public static CustomItemSettings create() {
        return new CustomItemSettings();
    }

    public CustomItemSettings setStackSize(int size) {
        this.stackSize = size;
        return this;
    }

    public CustomItemSettings setCategory(InventoryCategory category) {
        this.category = category;
        return this;
    }

    public CustomItemSettings setTexture(Identifier texture) {
        this.texture = texture;
        return this;
    }

    public CustomItemSettings setRarity(int rarity) {
        this.rarity = rarity;
        return this;
    }

    public CustomItemSettings setResourcesSubCategory(ResourcesSubCategory s) {
        this.resourcesSubCategory = s;
        return this;
    }

    public int getStackSize() {
        return this.stackSize;
    }

    public ResourcesSubCategory getResourcesSubCategory() {
        return resourcesSubCategory;
    }

    public int getRarity() {
        return rarity;
    }

    public Identifier getTexture() {
        return texture;
    }

    public InventoryCategory getCategory() {
        return category;
    }

}
