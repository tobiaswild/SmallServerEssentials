package de.tobiaswild.sse.settings;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class SettingsInventory {

    private static int SIZE = 54;
    private Inventory settingsInventory;
    private ItemStack[] settingsItems;

    public SettingsInventory() {
        init();
    }

    public void init() {
        settingsInventory = Bukkit.createInventory(null, SIZE, "Settings");
        settingsItems = generateItems();
        settingsInventory.setContents(settingsItems);
    }

    public ItemStack[] generateItems() {
        ItemStack space = getSpaceholder();

        ItemStack back = getBackItem();

        return new ItemStack[]{
                back, null, space, back, null, space, back, null, space,
                back, null, space, back, null, space, back, null, space,
                back, null, space, back, null, space, back, null, space,
                back, null, space, back, null, space, back, null, space,
                back, null, space, back, null, space, back, null, space,
                back, null, space, back, null, space, back, null, space,
        };
    }

    public ItemStack getSpaceholder() {
        ItemStack spaceholderItem = new ItemStack(Material.GRAY_STAINED_GLASS);
        ItemMeta spaceholderMeta = spaceholderItem.getItemMeta();
        spaceholderMeta.setDisplayName(" ");
        spaceholderMeta.setLore(null);
        spaceholderItem.setItemMeta(spaceholderMeta);
        return spaceholderItem;
    }

    public ItemStack getBackItem() {
        ItemStack backItem = new ItemStack(Material.RESPAWN_ANCHOR);
        ItemMeta backMeta = backItem.getItemMeta();
        backMeta.setDisplayName("/back Command");
        ArrayList<String> backLore = new ArrayList<>();
        backLore.add("kill yourself");
        backMeta.setLore(backLore);
        backItem.setItemMeta(backMeta);
        return backItem;
    }

    public Inventory getSettingsInventory() {
        return settingsInventory;
    }

    public void setSettingsInventory(Inventory settingsInventory) {
        this.settingsInventory = settingsInventory;
    }
}
