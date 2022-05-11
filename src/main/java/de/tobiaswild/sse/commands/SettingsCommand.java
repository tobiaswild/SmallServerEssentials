package de.tobiaswild.sse.commands;

import de.tobiaswild.sse.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SettingsCommand implements CommandExecutor, TabCompleter {

    private final Main plugin;

    public SettingsCommand(Main plugin) {
        this.plugin = plugin;
    }

    private static int SIZE = 9;


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Main.NO_PLAYER);
            return false;
        }
        if (!player.hasPermission(Main.PERMISSION + "settings")) {
            player.sendMessage(Main.NO_PERMISSION);
            return false;
        }
        Inventory settingsInventory = Bukkit.createInventory(player, SIZE, "Settings");
        ItemStack suicide = new ItemStack(Material.RED_DYE);
        ItemStack heal = new ItemStack(Material.GREEN_DYE);
        ItemStack weapon = new ItemStack(Material.DIAMOND_SWORD);
        ItemStack itemBoolean = new ItemStack(Material.RED_WOOL);

        ItemMeta suicideMeta = suicide.getItemMeta();
        suicideMeta.setDisplayName("Suicide");
        ArrayList<String> suicideLore = new ArrayList<>();
        suicideLore.add("kill yourself");
        suicideMeta.setLore(suicideLore);
        suicide.setItemMeta(suicideMeta);

        ItemMeta healMeta = heal.getItemMeta();
        healMeta.setDisplayName("Heal");
        ArrayList<String> healLore = new ArrayList<>();
        healLore.add("heal yourself");
        healMeta.setLore(healLore);
        heal.setItemMeta(healMeta);

        ItemMeta weaponMeta = weapon.getItemMeta();
        weaponMeta.setDisplayName("Weapon");
        ArrayList<String> weaponLore = new ArrayList<>();
        weaponLore.add("Get a weapon");
        weaponMeta.setLore(weaponLore);
        weapon.setItemMeta(weaponMeta);

        ItemMeta booleanMeta = itemBoolean.getItemMeta();
        booleanMeta.setDisplayName("Boolean");
        itemBoolean.setItemMeta(booleanMeta);

        ItemStack[] menuItems = {suicide, heal, weapon, itemBoolean};
        settingsInventory.setContents(menuItems);
        player.openInventory(plugin.getSettingsInventory());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }
}
