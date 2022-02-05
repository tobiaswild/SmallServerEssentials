package de.tobiaswild.newplugin.listeners;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.tobiaswild.newplugin.Main;
import de.tobiaswild.newplugin.utils.FreezeManager;

public class DeathListener implements Listener {

    private YamlConfiguration config = Main.getInstance().getConfiguration().getConfig();
    private FreezeManager freezeManager = Main.getInstance().getFreezeManager();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(ChatColor.RED + "☠ " + ChatColor.RESET + event.getDeathMessage());
        config.set("death." + event.getEntity().getUniqueId(), event.getEntity().getLocation());
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (freezeManager.getFrozenPlayers().contains(player)) {
                event.setCancelled(true);
            }
        }
    }
}
