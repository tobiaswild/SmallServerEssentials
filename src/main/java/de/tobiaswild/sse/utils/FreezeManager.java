package de.tobiaswild.sse.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.tobiaswild.sse.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class FreezeManager {

    private final YamlConfiguration config = Main.getInstance().getConfiguration().getConfig();
    private final HashMap<UUID, Location> frozenPlayers;

    public FreezeManager() {
        frozenPlayers = new HashMap<>();
        load();
    }

    public Location getFreezePosition(UUID uuid) {
        if (frozenPlayers.containsKey(uuid)) {
            return frozenPlayers.get(uuid);
        }
        return null;
    }

    public void setFreezePosition(UUID uuid, Location location) {
        frozenPlayers.put(uuid, location);
    }

    public void deleteFreezePosition(UUID uuid) {
        frozenPlayers.remove(uuid);
    }

    private void load() {
        List<String> uuids = config.getStringList("frozen");
        uuids.forEach(s -> {
            UUID uuid = UUID.fromString(s);
            Location location = config.getLocation("freeze." + s);
            try {
                frozenPlayers.put(uuid, location);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        run();
    }

    public void save() {
        List<String> uuids = new ArrayList<>();
        for (UUID uuid : frozenPlayers.keySet()) {
            uuids.add(uuid.toString());
        }
        config.set("frozen", uuids);
        frozenPlayers.forEach((uuid, location) -> config.set("freeze." + uuid.toString(), location));
    }

    public Set<UUID> getFrozenPlayers() {
        return frozenPlayers.keySet();
    }

    private void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                sendActionbar();
            }
        }.runTaskTimer(Main.getInstance(), 20, 20);
    }

    public void sendActionbar() {
        for (UUID uuid : frozenPlayers.keySet()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null)
                return;
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                    new TextComponent(ChatColor.BLUE + "❄ " + ChatColor.RESET + "you are frozen"));
        }
    }
}
