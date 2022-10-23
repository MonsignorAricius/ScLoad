package me.aricius.scload;

import com.sk89q.worldedit.math.BlockVector3;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QueueManager {
    private static Map<String, SLQueue> qman = new HashMap<String, SLQueue>();

    public QueueManager() {
    }

    public static boolean addQueue(Player player, String fileName) {
        return qman.containsKey(player.getName()) && ((SLQueue)qman.get(player.getName())).isActive() ? false : addQueue(player, player.getWorld(), BlockVector3.at(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ()), fileName);
    }
    public static boolean addQueue(Player player, String fileName, boolean ignoreAir) {
        return qman.containsKey(player.getName()) && ((SLQueue)qman.get(player.getName())).isActive() ? false : addQueue(player, player.getWorld(), BlockVector3.at(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ()), fileName, ignoreAir);
    }

    public static boolean addQueue(CommandSender sender, World world, BlockVector3 v, String fileName) {
        if (qman.containsKey(sender.getName()) && ((SLQueue)qman.get(sender.getName())).isActive()) {
            return false;
        } else {
            SLQueue slQueue = null;

            try {
                slQueue = new SLQueue(world, v, fileName);
            } catch (FileNotFoundException var6) {
                sender.sendMessage(ChatColor.RED + "Schematic file not found.");
                return false;
            }

            qman.put(sender.getName(), slQueue);
            startNext();
            return true;
        }
    }

    public static boolean addQueue(CommandSender sender, World world, BlockVector3 v, String fileName, boolean ignoreAir) {
        if (qman.containsKey(sender.getName()) && ((SLQueue)qman.get(sender.getName())).isActive()) {
            return false;
        } else {
            SLQueue slQueue = null;

            try {
                slQueue = new SLQueue(world, v, fileName, ignoreAir);
            } catch (FileNotFoundException var6) {
                sender.sendMessage(ChatColor.RED + "Schematic file not found.");
                return false;
            }

            qman.put(sender.getName(), slQueue);
            startNext();
            return true;
        }
    }

    public static void startNext() {
        SLQueue q = getNext();
        if (q != null) {
            q.start();
        }

    }

    public static SLQueue getNext() {
        if (isActive()) {
            return null;
        } else {
            Iterator<SLQueue> var0 = qman.values().iterator();

            while(var0.hasNext()) {
                SLQueue q = (SLQueue)var0.next();
                if (!q.isFinished()) {
                    return q;
                }
            }

            return null;
        }
    }

    public static boolean isActive() {
        Iterator<SLQueue> var0 = qman.values().iterator();

        while(var0.hasNext()) {
            SLQueue q = (SLQueue)var0.next();
            if (q.isActive()) {
                return true;
            }
        }

        return false;
    }
}
