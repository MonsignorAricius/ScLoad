package me.aricius.scload;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinEventListener implements Listener {
    ScLoad plg;

    public PlayerJoinEventListener(ScLoad plugin) {
        this.plg = plugin;
    }
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (plg.vcheck) {
            if (p.isOp()) {
                new UpdateChecker(plg, 88674).getVersion(version -> {
                    if (!plg.getDescription().getVersion().equals(version)) {
                        p.sendMessage(ChatColor.GREEN+"[ScLoad] There is a new version "+ChatColor.YELLOW+version+ChatColor.GREEN+" avalible. Currently "+ChatColor.YELLOW+plg.getDescription().getVersion());
                        p.sendMessage(ChatColor.GREEN+"https://www.spigotmc.org/resources/scload-reloaded.88674/");
                    }
                });
            }
        }
    }
}
