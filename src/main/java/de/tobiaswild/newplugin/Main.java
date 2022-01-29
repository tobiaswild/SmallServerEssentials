package de.tobiaswild.newplugin;

import java.util.HashMap;

import de.tobiaswild.newplugin.commands.*;
import de.tobiaswild.newplugin.utils.PositionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.tobiaswild.newplugin.listeners.ChatListener;
import de.tobiaswild.newplugin.listeners.ConnectionListener;
import de.tobiaswild.newplugin.listeners.DeathListener;
import de.tobiaswild.newplugin.listeners.FreezeListener;
import de.tobiaswild.newplugin.utils.BackpackManager;
import de.tobiaswild.newplugin.utils.Config;
import de.tobiaswild.newplugin.utils.Timer;

public final class Main extends JavaPlugin {

    private static Main instance;
    private Timer timer;
    private Config config;
    private BackpackManager backpackManager;
    private PositionManager positionManager;
    private final HashMap<Player, Location> frozenPlayers = new HashMap<>();

    public static final String PERMISSION = "newplugin.",
            PREFIX = "",
            ERROR = PREFIX + ChatColor.RED,
            INFO = PREFIX + ChatColor.YELLOW,
            SUCCESS = PREFIX + ChatColor.GREEN,
            NO_PLAYER = ERROR + "You have to be a player.",
            NO_PERMISSION = ERROR + "You are not allowed to do this.",
            NOT_POSSIBLE = ERROR + "This is currently not possible.";


    @Override
    public void onLoad() {
        instance = this;
        config = new Config();
    }

    @Override
    public void onEnable() {
        positionManager = new PositionManager();
        backpackManager = new BackpackManager();
        timer = new Timer();
        init(Bukkit.getPluginManager());
    }

    @Override
    public void onDisable() {
        positionManager.save();
        backpackManager.save();
        timer.save();
        config.save();
    }

    public void init(PluginManager pluginManager) {
        // Commands
        this.getCommand("backpack").setExecutor(new BackpackCommand(this));
        this.getCommand("clearchat").setExecutor(new ClearChatCommand());
        this.getCommand("enderchest").setExecutor(new EnderchestCommand());
        this.getCommand("fly").setExecutor(new FlyCommand(this));
        this.getCommand("freeze").setExecutor(new FreezeCommand(this));
        this.getCommand("gamemode").setExecutor(new GamemodeCommand());
        this.getCommand("heal").setExecutor(new HealCommand(this));
        this.getCommand("ip").setExecutor(new IpCommand());
        this.getCommand("ping").setExecutor(new PingCommand());
        this.getCommand("position").setExecutor(new PositionCommand(this));
        this.getCommand("see").setExecutor(new SeeCommand(this));
        this.getCommand("timer").setExecutor(new TimerCommand());
        this.getCommand("top").setExecutor(new TopCommand());
        this.getCommand("vanish").setExecutor(new VanishCommand(this));
        // Listeners
        pluginManager.registerEvents(new ChatListener(this), this);
        pluginManager.registerEvents(new ConnectionListener(this), this);
        pluginManager.registerEvents(new DeathListener(this), this);
        pluginManager.registerEvents(new FreezeListener(this), this);
    }

    public String wrongGamemode(GameMode gameMode) {
        return ERROR + "You have to be in " + gameMode + " mode";
    }

    public String wrongTargetGamemode(String target, GameMode gameMode) {
        return ERROR + target + " has to be in " + gameMode + " mode";
    }

    public static Main getInstance() {
        return instance;
    }

    public Config getConfiguration() {
        return config;
    }

    public BackpackManager getBackpackManager() {
        return backpackManager;
    }

    public PositionManager getPositionManager() {
        return positionManager;
    }

    public Timer getTimer() {
        return timer;
    }

    public HashMap<Player, Location> getFrozenPlayers() {
        return frozenPlayers;
    }
}
