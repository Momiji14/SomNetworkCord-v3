package SomNetworkCord;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static SomNetworkCord.SomCore.Log;

public class SncReload implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        long startTime = System.currentTimeMillis();
        Updater.UpdatePlugin();
        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "plugman reload SomNetworkCord");
        Log("§a[SomCore]§bSNCReload - " + (System.currentTimeMillis() - startTime) + "ms");
        return true;
    }
}

