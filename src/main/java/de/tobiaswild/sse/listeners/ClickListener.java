package de.tobiaswild.sse.listeners;

import de.tobiaswild.sse.Main;
import de.tobiaswild.sse.utils.VanishManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class ClickListener implements Listener {

    private final Main plugin;


    public ClickListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        ItemStack suicide = new ItemStack(Material.RED_DYE);
        ItemMeta suicideMeta = suicide.getItemMeta();
        suicideMeta.setDisplayName("Suicide");
        ArrayList<String> suicideLore = new ArrayList<>();
        suicideLore.add("kill yourself");
        suicideMeta.setLore(suicideLore);
        suicide.setItemMeta(suicideMeta);

        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);

        Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory().contains(suicide)) {
            switch (event.getCurrentItem().getType()) {
                case RED_DYE -> {
                    player.closeInventory();
                    player.setHealth(0);
                }
                case GREEN_DYE -> {
                    player.closeInventory();
                    player.setHealth(20);
                    player.setFoodLevel(20);
                    player.setSaturation(20);
                }
                case DIAMOND_SWORD -> {
                    player.closeInventory();
                    player.getInventory().addItem(sword);
                }
                case RED_WOOL -> {
                    event.getCurrentItem().setType(Material.GREEN_WOOL);
                }
                case GREEN_WOOL -> {
                    event.getCurrentItem().setType(Material.RED_WOOL);
                }
            }
            event.setCancelled(true);
        }
    }
}
