package SomNetworkCord;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static SomNetworkCord.SomCore.*;

public class Events implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.joinMessage(Component.empty());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.quitMessage(Component.empty());
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        if (!event.isCancelled()) {
            try {
                Player player = event.getPlayer();
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);
                TextComponent message = (TextComponent) event.message();
                out.writeUTF("Chat");
                out.writeUTF(player.getName());
                out.writeUTF(message.content());
                player.sendPluginMessage(SomCore.plugin(), SNCChannel, b.toByteArray());
                b.close();
                out.close();
                event.setCancelled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private final Set<String> ignoreCommand = new HashSet<String>() {{
        add("minecraft:tell");
        add("minecraft:w");
    }};

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        for (String ignore : ignoreCommand) {
            if (event.getMessage().contains(ignore)) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
