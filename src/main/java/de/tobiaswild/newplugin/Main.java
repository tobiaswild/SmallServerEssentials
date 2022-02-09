package de.tobiaswild.newplugin;

import de.tobiaswild.newplugin.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.tobiaswild.newplugin.commands.BackCommand;
import de.tobiaswild.newplugin.commands.BackpackCommand;
import de.tobiaswild.newplugin.commands.BuildCommand;
import de.tobiaswild.newplugin.commands.ClearChatCommand;
import de.tobiaswild.newplugin.commands.EnderchestCommand;
import de.tobiaswild.newplugin.commands.FlyCommand;
import de.tobiaswild.newplugin.commands.FreezeCommand;
import de.tobiaswild.newplugin.commands.GamemodeCommand;
import de.tobiaswild.newplugin.commands.HealCommand;
import de.tobiaswild.newplugin.commands.IpCommand;
import de.tobiaswild.newplugin.commands.PingCommand;
import de.tobiaswild.newplugin.commands.PositionCommand;
import de.tobiaswild.newplugin.commands.SeeCommand;
import de.tobiaswild.newplugin.commands.TimerCommand;
import de.tobiaswild.newplugin.commands.TopCommand;
import de.tobiaswild.newplugin.commands.VanishCommand;
import de.tobiaswild.newplugin.listeners.ChatListener;
import de.tobiaswild.newplugin.listeners.ConnectionListener;
import de.tobiaswild.newplugin.listeners.DeathListener;
import de.tobiaswild.newplugin.listeners.FreezeListener;

public final class Main extends JavaPlugin {

    private static Main instance;
    private Config config;
    private BackpackManager backpackManager;
    private DeathManager deathManager;
    private FreezeManager freezeManager;
    private PositionManager positionManager;
    private VanishManager vanishManager;
    private Timer timer;

    public static final String PERMISSION = "newplugin.",
            ERROR = ChatColor.RED.toString() + ChatColor.BOLD + "! " + ChatColor.RESET,
            INFO = ChatColor.YELLOW.toString() + "ℹ " + ChatColor.RESET,
            SUCCESS = ChatColor.GREEN.toString() + "✌ " + ChatColor.RESET,
            NO_PLAYER = ERROR + "You have to be a player to do this",
            NO_PERMISSION = ERROR + "You do not have the permission to do this",
            NOT_POSSIBLE = ERROR + "You are currently not able to do this";

    @Override
    public void onLoad() {
        instance = this;
        config = new Config();
    }

    @Override
    public void onEnable() {
        backpackManager = new BackpackManager();
        deathManager = new DeathManager();
        freezeManager = new FreezeManager();
        positionManager = new PositionManager();
        vanishManager = new VanishManager();
        timer = new Timer();
        init(Bukkit.getPluginManager());
    }

    @Override
    public void onDisable() {
        backpackManager.save();
        deathManager.save();
        freezeManager.save();
        positionManager.save();
        vanishManager.save();
        timer.save();
        config.save();
    }

    public void init(PluginManager pluginManager) {
        // Commands
        this.getCommand("back").setExecutor(new BackCommand());
        this.getCommand("backpack").setExecutor(new BackpackCommand());
        this.getCommand("build").setExecutor(new BuildCommand());
        this.getCommand("clearchat").setExecutor(new ClearChatCommand());
        this.getCommand("enderchest").setExecutor(new EnderchestCommand());
        this.getCommand("fly").setExecutor(new FlyCommand(this));
        this.getCommand("freeze").setExecutor(new FreezeCommand(this));
        this.getCommand("gamemode").setExecutor(new GamemodeCommand(this));
        this.getCommand("heal").setExecutor(new HealCommand(this));
        this.getCommand("ip").setExecutor(new IpCommand());
        this.getCommand("ping").setExecutor(new PingCommand());
        this.getCommand("position").setExecutor(new PositionCommand());
        this.getCommand("see").setExecutor(new SeeCommand(this));
        this.getCommand("timer").setExecutor(new TimerCommand());
        this.getCommand("top").setExecutor(new TopCommand());
        this.getCommand("vanish").setExecutor(new VanishCommand(this));
        // Listeners
        pluginManager.registerEvents(new ChatListener(), this);
        pluginManager.registerEvents(new ConnectionListener(this), this);
        pluginManager.registerEvents(new DeathListener(), this);
        pluginManager.registerEvents(new FreezeListener(), this);
    }

    public String wrongGamemode(GameMode gameMode) {
        return ERROR + "You have to be in " + gameMode + " mode";
    }

    public String wrongTargetGamemode(String target, GameMode gameMode) {
        return ERROR + target + " has to be in " + gameMode + " mode";
    }

    public String playerNotAvailable(String target) {
        return ERROR + "The Player " + target + " is not available";
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

    public DeathManager getDeathManager() {
        return deathManager;
    }

    public FreezeManager getFreezeManager() {
        return freezeManager;
    }

    public PositionManager getPositionManager() {
        return positionManager;
    }

    public VanishManager getVanishManager() {
        return vanishManager;
    }

    public Timer getTimer() {
        return timer;
    }

}
