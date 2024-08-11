package pl.epsi.util;

import net.minecraft.client.gui.widget.ContainerWidget;
import org.joml.Vector3d;
import pl.epsi.gui.modules.ResourceItemDescriptionModule;
import pl.epsi.gui.modules.SpearItemDescriptionModule;
import pl.epsi.player.inventory.*;

import java.util.*;
import java.util.stream.Collectors;

public class HorizonUtil {

    /**
     *
     * @param squareVertices Vertices of the square, in the order of x1, y1, x2, y2
     * @param posX The X position to check
     * @param posY The Y position to check
     * @return Boolean, are the position coordinates inside the square
     */
    public static boolean isCoordinateInsideSquare(int[] squareVertices, int posX, int posY) {
        return squareVertices.length >= 4 ?
                posX >= squareVertices[0] && posX <= squareVertices[2] && posY >= squareVertices[1] && posY <= squareVertices[3] : false;
    }

    /**
     *
     * @param center The center of the sphere, in the order of x, y, z
     * @param radius The radius of the sphere
     * @param posX The X position to check
     * @param posY The Y position to check
     * @param posZ The Z position to check
     * @return Boolean, are the position coordinates inside the sphere
     */
    public static boolean isCoordinateInSphere(int[] center, int radius, int posX, int posY, int posZ) {
        double distanceSquared = Math.pow(posX - center[0], 2) +
                Math.pow(posY - center[1], 2) +
                Math.pow(posZ - center[2], 2);

        double radiusSquared = Math.pow(radius, 2);

        return distanceSquared <= radiusSquared;
    }

    /**
     *
     * @param center A Vector3d containing the coordinates of the middle of the sphere
     * @param pos A Vector3d of the position to check
     * @param radius the radius of the sphere
     * @return Boolean, is the position inside the sphere
     */
    public static boolean isCoordinateInSphere(Vector3d center, Vector3d pos, int radius) {
        double distanceSquared = Math.pow(pos.x - center.x, 2) +
                Math.pow(pos.y - center.y, 2) +
                Math.pow(pos.z - center.z, 2);

        double radiusSquared = Math.pow(radius, 2);

        return distanceSquared <= radiusSquared;
    }

    /**
     *
     * @param rarity The rarity of an item, ranging from 0-4
     * @return Int, the color of the rarity
     */
    public static int getColorByRarity(int rarity) {
        switch (rarity) {
            case 0 -> { return 0x807f7b; }
            case 1 -> { return 0x86ab89; }
            case 2 -> { return 0x4f7797; }
            case 3 -> { return 0x6f568f; }
            case 4 -> { return 0x915e2d; }
        }
        return 0xffffff;
    }

    /**
     *
     * @param rarity The rarity of an item, ranging from 0-4
     * @return String, the name of the rarity
     */
    public static String getRarityName(int rarity) {
        switch (rarity) {
            case 0 -> { return "Common"; }
            case 1 -> { return "Uncommon"; }
            case 2 -> { return "Rare"; }
            case 3 -> { return "Very Rare"; }
            case 4 -> { return "Legendary"; }
        }
        return "";
    }

    /**
     *
     * @param category The sub-category of an item
     * @return String, the name of the subcategory
     */
    public static String getResourceSubCategoryName(ResourcesSubCategory category) {
        switch (category) {
            case VALUABLES_TO_SELL -> { return "Valuables to Sell"; }
            case AMMUNITION_RESOURCES -> { return "Ammunition Resources"; }
            case GEAR_UPGRADE_RESOURCES -> { return "Gear Upgrade Resources"; }
        }
        return "";
    }

    public static String getWeaponSubCategoryName(WeaponSubCategory category) {
        switch (category) {
            case SPEAR -> { return "Spear"; }
            case BOW -> { return "Bow"; }
            case SLINGSHOT -> { return "Slingshot"; }
            case TRIPCASTER -> { return "Tripcaster"; }
            case WIRECASTER -> { return "Wirecaster"; }
        }
        return "";
    }

    /**
     *
     * @param inventoryEntry The currently selected inventory entry
     * @param x The x coordinate of the widget
     * @param y The y coordinate of the widget
     * @param height The height of the widget
     * @param item The item, that the widget will use to generate info about the item
     * @return ContainerWidget, the specific description widget for the inventoryEntry, or null
     */
    public static ContainerWidget getDescriptionWidgetForCategory(int inventoryEntry, int x, int y, int height, CustomItem item) {
        switch (inventoryEntry) {
            case 0 -> { return manageWidgetForWeapon(inventoryEntry, x, y, height, item); }
            case 4 -> { return new ResourceItemDescriptionModule(x, y, height, (ResourceItem) item); }
        }
        return null;
    }

    private static ContainerWidget manageWidgetForWeapon(int inventoryEntry, int x, int y, int height, CustomItem item) {
        WeaponItem weaponItem = (WeaponItem) item;
        switch (weaponItem.getWeaponCategory()) {
            case SPEAR -> { return new SpearItemDescriptionModule(x, y, height, (SpearItem) item);
            }
        }
        return null;
    }

    /**
     *
     * @param item1 The first item to compare
     * @param item2 The second item to compare
     * @return int, -1 if the first item has a higher rarity, 1 if the second item has a lower rarity, and 0 if the rarities are equal
     */
    public static int compareItemsByRarity(CustomItem item1, CustomItem item2) {
        if (item1.getSettings().getRarity() > item2.getSettings().getRarity()) return -1;
        if (item1.getSettings().getRarity() < item2.getSettings().getRarity()) return 1;
        return 0;
    }

    /**
     * INTERNAL FUNCTION
     * @param sortMap The map to get sorted
     * @return List, a list of the items, sorted by rarity
     */
    public static List<CustomItem> sortItemsByRarity(HashMap<CustomItem, Integer> sortMap) {
        return sortMap.keySet().stream().sorted(HorizonUtil::compareItemsByRarity).collect(Collectors.toList());
    }

    @SafeVarargs
    public static <T> ArrayList<T> asArray(T... toAdd) {
        ArrayList<T> list = new ArrayList<>();
        Collections.addAll(list, toAdd);
        return list;
    }

}
