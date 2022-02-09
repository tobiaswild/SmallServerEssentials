package de.tobiaswild.newplugin.utils;

import de.tobiaswild.newplugin.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.util.*;

public class VanishManager {

    private final YamlConfiguration config = Main.getInstance().getConfiguration().getConfig();
    private final Set<UUID> vanishedPlayers;

    public VanishManager() {
        vanishedPlayers = new HashSet<>();
        load();
    }

    public void addPlayer(UUID uuid) {
       vanishedPlayers.add(uuid);
    }

    public void removePlayer(UUID uuid) {
        vanishedPlayers.remove(uuid);
    }

    private void load() {
        List<String> uuids = config.getStringList("vanished");
        uuids.forEach(s -> {
            UUID uuid = UUID.fromString(s);
            try {
                vanishedPlayers.add(uuid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void save() {
        List<String> uuids = new ArrayList<>();
        for (UUID uuid : vanishedPlayers) {
            uuids.add(uuid.toString());
        }
        config.set("vanished", uuids);
    }

    public Set<UUID> getVanishedPlayers() {
        return vanishedPlayers;
    }
}
