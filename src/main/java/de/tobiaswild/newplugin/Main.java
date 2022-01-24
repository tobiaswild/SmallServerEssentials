package de.tobiaswild.newplugin;

import de.tobiaswild.newplugin.commands.*;
import de.tobiaswild.newplugin.listeners.*;
import de.tobiaswild.newplugin.utils.timer.Timer;
import de.tobiaswild.newplugin.utils.Config;
import de.tobiaswild.newplugin.utils.backpack.BackpackManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Main extends JavaPlugin {
    private static Main instance;
    private Timer timer;
    private Config config;
    private BackpackManager backpackManager;
    public static final String
            PERMISSION  = "newplugin.",
            PREFIX      = ChatColor.GRAY + "[" + ChatColor.BLUE + "NP" + ChatColor.GRAY + "]" + ChatColor.RESET + " ",
            ERROR       = PREFIX + ChatColor.RED,
            INFO        = PREFIX + ChatColor.YELLOW,
            SUCCESS     = PREFIX + ChatColor.GREEN;
    private final HashMap<Player, Location> frozenPlayers = new HashMap<>();

    @Override
    public void onLoad() {
        instance = this;
        config = new Config();
    }

    @Override
    public void onEnable() {
        backpackManager = new BackpackManager();
        timer = new Timer();
        init(Bukkit.getPluginManager());
    }

    @Override
    public void onDisable() {
        backpackManager.save();
        timer.save();
        config.save();
    }

    public void init(PluginManager pluginManager) {
        // Commands
        this.getCommand("backpack").setExecutor(new BackpackCommand());
        this.getCommand("clearchat").setExecutor(new ClearChatCommand());
        this.getCommand("enderchest").setExecutor(new EnderchestCommand());
        this.getCommand("fly").setExecutor(new FlyCommand(this));
        this.getCommand("freeze").setExecutor(new FreezeCommand());
        this.getCommand("gamemode").setExecutor(new GamemodeCommand());
        this.getCommand("heal").setExecutor(new HealCommand());
        this.getCommand("inventory").setExecutor(new InventoryCommand());
        this.getCommand("ip").setExecutor(new IpCommand());
        this.getCommand("kick").setExecutor(new KickCommand());
        this.getCommand("kickall").setExecutor(new KickallCommand());
        this.getCommand("ping").setExecutor(new PingCommand());
        this.getCommand("shutdown").setExecutor(new ShutdownCommand());
        this.getCommand("top").setExecutor(new TopCommand());
        this.getCommand("vanish").setExecutor(new VanishCommand(this));
        this.getCommand("weather").setExecutor(new WeatherCommand());
        this.getCommand("timer").setExecutor(new TimerCommand());
        // Listeners
        pluginManager.registerEvents(new ChatListener(), this);
        pluginManager.registerEvents(new ConnectionListener(), this);
        pluginManager.registerEvents(new DeathListener(), this);
        pluginManager.registerEvents(new FreezeListener(), this);
        pluginManager.registerEvents(new VanishListener(this), this);
    }

    public static void noPlayer(CommandSender sender) {
        sender.sendMessage(ERROR + "You have to be a player.");
    }
    public static void noPermission(CommandSender sender) {
        sender.sendMessage(ERROR + "You are not allowed to do this.");
    }
    public static void notPossible(CommandSender sender) {
        sender.sendMessage(ERROR + "this is currently not possible.");
    }
    public static void wrongGamemode(CommandSender sender, GameMode gameMode) {
        sender.sendMessage(ERROR + "You have to be in " + gameMode + " mode");
    }
    public static void wrongTargetGamemode(CommandSender sender, String target, GameMode gameMode) {
        sender.sendMessage(ERROR + target + " has to be in " + gameMode + " mode");
    }

    public static Main getInstance() {return instance;}
    public Config getConfiguration() {return config;}
    public BackpackManager getBackpackManager() {return backpackManager;}
    public Timer getTimer() {return timer;}
    public HashMap<Player, Location> getFrozenPlayers() {return frozenPlayers;}
}
