package SomNetworkCord;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SomCore extends JavaPlugin implements PluginMessageListener {

    private static Plugin plugin;
    public static String SNCChannel = "snc:main";

    @Override
    public void onEnable() {
        plugin = this;
        this.getServer().getPluginManager().registerEvents(new Events(), this);
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, SNCChannel, this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, SNCChannel);
        this.getCommand("SncReload").setExecutor(new SncReload());

        Bukkit.getScheduler().runTaskTimerAsynchronously(SomCore.plugin(), () -> {
            try {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getGameMode() != GameMode.SPECTATOR) {
                        ByteArrayOutputStream b = new ByteArrayOutputStream();
                        DataOutputStream out = new DataOutputStream(b);
                        out.writeUTF("TabListUpdate");
                        out.writeUTF(player.getUniqueId().toString());
                        out.writeUTF(player.getPlayerListName());
                        player.sendPluginMessage(SomCore.plugin(), SNCChannel, b.toByteArray());
                        b.close();
                        out.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 50, 50);
    }

    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }

    public static Plugin plugin() {
        return plugin;
    }

    public static void Log(Exception e) {
        Log(e.toString());
        for (int i = 0; i < e.getStackTrace().length; i++) {
            Log(e.getStackTrace()[i].toString());
        }
    }

    public static void Log(String log) {
        Log(log, false);
    }
    public static void Log(String log, boolean stacktrace) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("som10.reload")) {
                player.sendMessage(log);
            }
        }
        Bukkit.getLogger().info(log);
        if (stacktrace) throw new RuntimeException("StackTrace Log");
    }

    @Override
    public void onPluginMessageReceived(@NotNull String s, @NotNull Player player, @NotNull byte[] bytes) {

    }
}
