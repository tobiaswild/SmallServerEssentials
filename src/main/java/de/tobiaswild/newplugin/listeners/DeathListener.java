package de.tobiaswild.newplugin.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.tobiaswild.newplugin.Main;

public class DeathListener implements Listener {

    private Main plugin;

    public DeathListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(ChatColor.RED + "☠ " + ChatColor.RESET + event.getDeathMessage());
    }

    @EventHandler
    public void onPlayerDeath(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (plugin.getFrozenPlayers().containsKey(player)) {
                event.setCancelled(true);
            }
        }
    }
}
