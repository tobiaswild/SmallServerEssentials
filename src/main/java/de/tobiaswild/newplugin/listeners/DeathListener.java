package de.tobiaswild.newplugin.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import de.tobiaswild.newplugin.Main;
import de.tobiaswild.newplugin.utils.DeathManager;
import de.tobiaswild.newplugin.utils.FreezeManager;

public class DeathListener implements Listener {

    private final DeathManager deathManager = Main.getInstance().getDeathManager();
    private final FreezeManager freezeManager = Main.getInstance().getFreezeManager();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(ChatColor.RED + "☠ " + ChatColor.RESET + event.getDeathMessage());
        deathManager.setDeathPlayer(event.getEntity().getUniqueId(), event.getEntity().getLocation());
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        event.getPlayer().sendMessage(Main.INFO + "Use /back to come back to your death position");
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (freezeManager.getFrozenPlayers().contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }
}
