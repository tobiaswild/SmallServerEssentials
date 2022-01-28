package de.tobiaswild.newplugin.utils;

import de.tobiaswild.newplugin.Main;

import java.io.IOException;
import java.util.*;

import org.bukkit.configuration.file.YamlConfiguration;

public class BackpackManager {

    private final YamlConfiguration config = Main.getInstance().getConfiguration().getConfig();
    private final Map<UUID, Backpack> map;

    public BackpackManager() {
        map = new HashMap<>();
        load();
    }

    public Backpack getBackpack(UUID uuid) {
        if (map.containsKey(uuid)) {
            return map.get(uuid);
        }
        Backpack backpack = new Backpack(uuid);
        map.put(uuid, backpack);
        return backpack;
    }

    public void setBackpack(UUID uuid, Backpack backpack) {
        map.put(uuid, backpack);
    }

    private void load() {
        List<String> uuids = config.getStringList("backpacks");
        uuids.forEach(s -> {
            UUID uuid = UUID.fromString(s);
            String base64 = config.getString("backpack." + s);
            try {
                map.put(uuid, new Backpack(uuid, base64));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void save() {
        List<String> uuids = new ArrayList<>();
        for (UUID uuid : map.keySet()) {
            uuids.add(uuid.toString());
        }
        config.set("backpacks", uuids);
        map.forEach((uuid, backpack) -> config.set("backpack." + uuid.toString(), backpack.toBase64()));
    }
}
