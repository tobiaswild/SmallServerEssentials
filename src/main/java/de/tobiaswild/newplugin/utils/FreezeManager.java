package de.tobiaswild.newplugin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.tobiaswild.newplugin.Main;

public class FreezeManager {

    private final YamlConfiguration config = Main.getInstance().getConfiguration().getConfig();
    private final HashMap<Player, Location> frozenPlayers;

    public FreezeManager() {
        frozenPlayers = new HashMap<>();
        load();
    }

    public Location getFreezePosition(Player player) {
        if (frozenPlayers.containsKey(player)) {
            return frozenPlayers.get(player);
        }
        return null;
    }

    public void setFreezePosition(Player player, Location location) {
        frozenPlayers.put(player, location);
        save();
    }

    public void deleteFreezePosition(Player player) {
        frozenPlayers.remove(player);
        config.set("freeze." + player, null);
        save();
    }

    private void load() {
        List<String> stringPlayers = config.getStringList("frozen");
        List<Player> players = new ArrayList<>();
        for (String s: stringPlayers) {
            try {
                players.add(Bukkit.getPlayer(s));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        players.forEach(s -> {
            Location location = config.getLocation("freeze." + s);
            frozenPlayers.put(s, location);
        });
    }

    public void save() {
        List<Player> players = new ArrayList<>(frozenPlayers.keySet());
        config.set("positions", players);
        frozenPlayers.forEach((player, position) -> config.set("freeze." + player, position));
    }

    public Set<Player> getFrozenPlayers() {
        return frozenPlayers.keySet();
    }
}
