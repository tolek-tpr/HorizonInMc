package pl.epsi.player.inventory;

import pl.epsi.event.EventManager;
import pl.epsi.horizonItems.*;

import java.util.HashMap;

import static pl.epsi.event.InventoryModifyListener.ItemAddEvent;
import static pl.epsi.event.InventoryModifyListener.ItemRemoveEvent;

public class CustomInventory {

    public HashMap<CustomItem, Integer> WEAPONS = new HashMap<>();
    public HashMap<CustomItem, Integer> OUTFITS = new HashMap<>();
    public HashMap<CustomItem, Integer> TOOLS = new HashMap<>();
    public HashMap<CustomItem, Integer> AMMO = new HashMap<>();
    public HashMap<CustomItem, Integer> RESOURCES = new HashMap<>();
    public HashMap<CustomItem, Integer> STRIKE_PIECES = new HashMap<>();
    public HashMap<CustomItem, Integer> SPECIAL_GEAR = new HashMap<>();
    private static CustomInventory instance;

    private CustomInventory() {
        this.addItem(new BlazeItem(), 10);
        this.addItem(new TestItem1(), 10);
        this.addItem(new TestItem2(), 10);
        TestItem3 s = new TestItem3();
        s.getSettings()//.setCategory(InventoryCategory.AMMO);
                .setStackSize(15);
        this.addItem(s, 10);
        this.addItem(new TestItem4(), 10);
    }

    public static CustomInventory getInstance() {
        if (instance == null) instance = new CustomInventory();
        return instance;
    }

    private HashMap<CustomItem, Integer> getMapForItem(CustomItem item) {
        if (item.getSettings().getCategory().equals(InventoryCategory.WEAPONS)) {
            return WEAPONS;
        } else if (item.getSettings().getCategory().equals(InventoryCategory.OUTFITS)) {
            return OUTFITS;
        } else if (item.getSettings().getCategory().equals(InventoryCategory.TOOLS)) {
            return TOOLS;
        } else if (item.getSettings().getCategory().equals(InventoryCategory.AMMO)) {
            return AMMO;
        } else if (item.getSettings().getCategory().equals(InventoryCategory.RESOURCES)) {
            return RESOURCES;
        } else if (item.getSettings().getCategory().equals(InventoryCategory.STRIKE_PIECES)) {
            return STRIKE_PIECES;
        } else if (item.getSettings().getCategory().equals(InventoryCategory.SPECIAL_GEAR)) {
            return SPECIAL_GEAR;
        }
        return null;
    }

    public boolean addItem(CustomItem item, int amount) {
        HashMap<CustomItem, Integer> map = getMapForItem(item);
        map.put(item, map.getOrDefault(item, 0) + amount);
        ItemAddEvent event = new ItemAddEvent(item, amount);
        EventManager.getInstance().fire(event);
        return true; // TRUE = Successfully added item;
    }

    public boolean removeItem(CustomItem item, int amount) {
        HashMap<CustomItem, Integer> map = getMapForItem(item);
        int itemAmount = map.getOrDefault(item, 0);

        if (itemAmount < amount) return false; // FALSE = Failed to remove item;
        map.put(item, map.getOrDefault(item, 0) - amount);

        ItemRemoveEvent event = new ItemRemoveEvent(item, amount);
        EventManager.getInstance().fire(event);
        return true;
    }

}
