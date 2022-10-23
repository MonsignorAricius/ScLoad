package me.aricius.scload;

import com.sk89q.worldedit.math.BlockVector3;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class SLUtil extends FGUtilCore implements CommandExecutor, Listener {
    ScLoad plg;

    public SLUtil(ScLoad plugin, boolean savelng, String language) {
        super(plugin, savelng, language, "scload", "schematic");
        this.plg = plugin;
        fillMSG();
        if (savelng) this.SaveMSG();
        this.initCommands();
    }
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if (args.length > 0 && this.checkCmdPerm(sender, args[0])) {
            //1st command.
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("cfg")) {
                    printConfig(sender, 1, 100, false, true);
                } else if (args[0].equalsIgnoreCase("reload")) {
                    plg.reloadCfg();
                    printMSG(sender, "msg_reloaded");
                } else if (args[0].equalsIgnoreCase("list")) {
                    File f = new File(plg.schem_dir);
                    List<String> files = new ArrayList<String>();
                    for (String fn : f.list())
                        if (fn.endsWith(".schematic") || fn.endsWith(".schem")) files.add(fn.substring(0, fn.length() - 6));
                    printPage(sender, files, 1, "msg_filelist", "", false, 20);
                } else if (args[0].equalsIgnoreCase("help")) {
                    PrintHlpList(sender, 1, 100);
                } else return false;
                return true;
            }
            //End of 1st command.

            //2nd command.
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("load")) {
                    if (!this.checkPerFilePermission(sender instanceof Player ? (Player)sender : null, args[1])) {
                        return this.returnMSG(true, sender, "msg_nopermforfile", args[1], 'c', '4');
                    }

                    if (!(sender instanceof Player)) {
                        printMSG(sender, "msg_senderisnotplayer", 'c');
                        return true;
                    }

                    Player p = (Player)sender;
                    QueueManager.addQueue(p, args[1]);
                    printMSG(p, "msg_loadstarted", p.getLocation(), args[1]);
                } else return false;
                return true;
            }
            //End of 2nd command.

            //3rd command.
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("load")) {
                    if (!this.checkPerFilePermission(sender instanceof Player ? (Player)sender : null, args[1])) {
                        return this.returnMSG(true, sender, "msg_nopermforfile", args[1], 'c', '4');
                    }

                    if (!(sender instanceof Player)) {
                        printMSG(sender, "msg_senderisnotplayer", 'c');
                        return true;
                    }
                    boolean ignoreAir;
                    if (args[2].equalsIgnoreCase("-a") || args[2].equalsIgnoreCase("-air")) {
                        ignoreAir = true;
                    } else {
                        sender.sendMessage(ChatColor.RED+"Last argument must be -a or -air");
                        return true;
                    }
                    Player p = (Player)sender;
                    QueueManager.addQueue(p, args[1], ignoreAir);
                    printMSG(p, "msg_loadstartednoair", p.getLocation(), args[1]);
                } else return false;
                return true;
            }
            //End of 3rd command.

            //4th command.
            if (args.length == 6) {
                if (args[0].equalsIgnoreCase("load")) {
                    if (!this.checkPerFilePermission(sender instanceof Player ? (Player)sender : null, args[1])) {
                        return this.returnMSG(true, sender, "msg_nopermforfile", args[1], 'c', '4');
                    } else {
                        World w = Bukkit.getWorld(args[2]);
                        if (w == null) {
                            printMSG(sender, "msg_unknownworld", 'c', '4', args[2]);
                            return true;
                        } else if (!this.isIntegerSigned(args[3], args[4], args[5])) {
                            this.printMSG(sender, "msg_wrongcoordinate", 'c', '4', args[3], args[4], args[5]);
                            return true;
                        } else {
                            int x = Integer.parseInt(args[3]);
                            int y = Integer.parseInt(args[4]);
                            int z = Integer.parseInt(args[5]);
                            BlockVector3 v = BlockVector3.at(x, y, z);
                            QueueManager.addQueue(sender, w, v, args[1]);
                            printMSG(sender, "msg_loadstarted", new Location(w, x, y, z), args[1]);
                            return true;
                        }
                    }
                } else {
                    return false;
                }
            }
            //End of 4th command.

            //5th command.
            if (args.length == 7) {
                if (args[0].equalsIgnoreCase("load")) {
                    if (!this.checkPerFilePermission(sender instanceof Player ? (Player)sender : null, args[1])) {
                        return this.returnMSG(true, sender, "msg_nopermforfile", args[1], 'c', '4');
                    } else {
                        World w = Bukkit.getWorld(args[2]);
                        if (w == null) {
                            printMSG(sender, "msg_unknownworld", 'c', '4', args[2]);
                            return true;
                        } else if (!this.isIntegerSigned(args[3], args[4], args[5])) {
                            this.printMSG(sender, "msg_wrongcoordinate", 'c', '4', args[3], args[4], args[5]);
                            return true;
                        } else {
                            int x = Integer.parseInt(args[3]);
                            int y = Integer.parseInt(args[4]);
                            int z = Integer.parseInt(args[5]);
                            boolean ignoreAir;
                            if (args[6].equalsIgnoreCase("-a") || args[6].equalsIgnoreCase("-air")) {
                                ignoreAir = true;
                            } else {
                                sender.sendMessage(ChatColor.RED+"Last argument must be -a or -air");
                                return true;
                            }
                            BlockVector3 v = BlockVector3.at(x, y, z);
                            QueueManager.addQueue(sender, w, v, args[1], ignoreAir);
                            printMSG(sender, "msg_loadstartednoair", new Location(w, x, y, z), args[1]);
                            return true;
                        }
                    }
                } else {
                    return false;
                }
            }
            //End of 5th commad.

            if (args.length == 4) {
                printMsg(sender, ChatColor.RED+"Invalid arguments. /scload help.");
            }
            if (args.length == 5) {
                printMsg(sender, ChatColor.RED+"Invalid arguments. /scload help.");
            }
            if (args.length >= 8) {
                printMsg(sender, ChatColor.RED+"Invalid arguments. /scload help.");
            }
        } else {
            printMSG(sender, "cmd_cmdpermerr", 'c');
        }

        return true;
    }

    public void initCommands() {
        this.addCmd("help", "config", "hlp_thishelp", "/scl help", true);
        this.addCmd("reload", "config", "hlp_reload", "/scl reload", true);
        this.addCmd("cfg", "config", "hlp_cfg", "/scl cfg", true);
        this.addCmd("list", "load", "hlp_list", "/scl list", true);
        this.addCmd("load", "load", "hlp_load", "/scl load <filename> [<world> <x> <y> <z>]", true);
    }

    public void fillMSG() {
        this.addMSG("hlp_cfg", "%1% - display current configuration");
        this.addMSG("hlp_reload", "%1% - reload plugin configuration and reload gadgets");
        this.addMSG("hlp_list", "%1% - list all availiable schematic files");
        this.addMSG("hlp_load", "%1% - load structure from the schematic file and build it at the world");
        this.addMSG("msg_unknownworld", "World %1% was not found");
        this.addMSG("msg_wrongcoordinate", "Failed to determine location from coordinates:  %1% (%2%,%3%,%4%)");
        this.addMSG("msg_filenotloaded", "Failed to load file: %1%");
        this.addMSG("msg_loadstarted", "File %2% was loaded. Starting to build at %1%");
        this.addMSG("msg_loadstartednoair", "File %2% was loaded. Starting to build without air at %1%");
        this.addMSG("msg_senderisnotplayer", "You need to specify the world and coordinates or run this command as a logged player");
        this.addMSG("msg_reloaded", "Configuration reloaded");
        this.addMSG("msg_filelist", "File list (extension not shown)");
        this.addMSG("cfgmsg_schematic-loader_use-worldedit-folder", "Use WorldEdit folder: %1%");
        this.addMSG("cfgmsg_schematic-loader_use-fastasyncworldedit-folder", "Use FastAsyncWorldEdit folder: %1%");
        this.addMSG("cfgmsg_schematic-loader_blocks-per-tick", "Number of blocks to place during the single tick: %1%");
        this.addMSG("cfgmsg_schematic-loader_delay-between-ticks", "Delay between ticks (min = 1): %1%");
        this.addMSG("cfgmsg_schematic-loader_fast-place", "Place blocks fast (without physics): %1%");
        this.addMSG("cfgmsg_schematic-loader_use-permission-per-file", "Use permission per file: %1%");
        this.addMSG("msg_nopermforfile", "You have not enough permissions to load file %1%");
    }

    public List<Entity> getEntities(Location l1, Location l2) {
        List<Entity> entities = new ArrayList<Entity>();
        if (!l1.getWorld().equals(l2.getWorld())) return entities;
        int x1 = Math.min(l1.getBlockX(), l2.getBlockX());
        int x2 = Math.max(l1.getBlockX(), l2.getBlockX());
        int y1 = Math.min(l1.getBlockY(), l2.getBlockY());
        int y2 = Math.max(l1.getBlockY(), l2.getBlockY());
        int z1 = Math.min(l1.getBlockZ(), l2.getBlockZ());
        int z2 = Math.max(l1.getBlockZ(), l2.getBlockZ());
        int chX1 = x1 >> 4;
        int chX2 = x2 >> 4;
        int chZ1 = z1 >> 4;
        int chZ2 = z2 >> 4;
        for (int x = chX1; x <= chX2; x++)
            for (int z = chZ1; z <= chZ2; z++) {
                for (Entity e : l1.getWorld().getChunkAt(x, z).getEntities()) {
                    double ex = e.getLocation().getX();
                    double ey = e.getLocation().getY();
                    double ez = e.getLocation().getZ();
                    if ((x1 <= ex) && (ex <= x2) && (y1 <= ey) && (ey <= y2) && (z1 <= ez) && (ez <= z2))
                        entities.add(e);
                }
            }
        return entities;
    }

    private boolean checkPerFilePermission(Player player, String fileName) {
        if (!plg.usePermPerFile) return true;
        if (player == null) return true;
        if (player.hasPermission("schematic.file") || player.hasPermission("schematic.file.*")) return true;
        return (player.hasPermission("schematic.file." + fileName.toLowerCase()));
    }

}

