package de.tobiaswild.sse.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.tobiaswild.sse.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Timer {
    private boolean running;
    private boolean hidden;
    private int time;
    private int seconds;
    private int minutes;
    private int hours;
    private int safe;

    private YamlConfiguration config = Main.getInstance().getConfiguration().getConfig();

    public Timer() {
        this.running = false;
        if (config.contains("timer.time")) {
            this.time = config.getInt("timer.time");
        } else {
            this.time = 0;
        }
        if (config.contains("timer.hidden")) {
            this.hidden = config.getBoolean("timer.hidden");
        } else {
            this.hidden = false;
        }
        run();
    }

    public void sendActionbar() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!isRunning()) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                        new TextComponent(ChatColor.RED + "timer is paused"));
            } else {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                        new TextComponent(ChatColor.GOLD + getHours() + ":" + getMinutes() + ":" + getSeconds()));
            }
        }
    }

    public void save() {
        config.set("timer.time", time);
        config.set("timer.hidden", hidden);
    }

    private void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!isHidden()) {
                    sendActionbar();
                    if (isRunning()) {
                        setTime(getTime() + 1);
                        setHours(getTime() / 3600);
                        setSafe(getTime() - hours * 3600);
                        setMinutes(getSafe() / 60);
                        setSafe(getSafe() - minutes * 60);
                        setSeconds(getSafe());
                    }
                } else {
                    setRunning(false);
                }
            }
        }.runTaskTimer(Main.getInstance(), 20, 20);
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getSeconds() {
        if (seconds <= 9) {
            return "0" + seconds;
        }
        return Integer.toString(seconds);
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public String getMinutes() {
        if (minutes <= 9) {
            return "0" + minutes;
        }
        return Integer.toString(minutes);
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getHours() {
        if (hours <= 9) {
            return "0" + hours;
        }
        return Integer.toString(hours);
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getSafe() {
        return safe;
    }

    public void setSafe(int safe) {
        this.safe = safe;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
