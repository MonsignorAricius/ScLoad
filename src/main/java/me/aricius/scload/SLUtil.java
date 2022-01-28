package me.aricius.scload;

import com.sk89q.worldedit.math.BlockVector3;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class SLUtil extends FGUtilCore implements CommandExecutor, Listener {
    ScLoad plg;

    public SLUtil(ScLoad plugin, boolean vcheck, boolean savelng, String language) {
        super(plugin, savelng, language, "scload", "schematic");
        this.plg = plugin;
        this.fillMSG();
        if (savelng) {
            this.SaveMSG();
        }

        this.initCommands();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if (args.length > 0 && this.checkCmdPerm(sender, args[0])) {
            if (args.length == 1) {
                return this.executeCmd(sender, args[0]);
            }

            if (args.length == 2) {
                return this.executeCmd(sender, args[0], args[1]);
            }

            if (args.length == 6) {
                return this.executeCmd(sender, args[0], args[1], args[2], args[3], args[4], args[5]);
            }
        } else {
            this.printMSG(sender, new Object[]{"cmd_cmdpermerr", 'c'});
        }

        return true;
    }

    private boolean executeCmd(CommandSender sender, String cmd, String fn, String wn, String xs, String ys, String zs) {
        if (cmd.equalsIgnoreCase("load")) {
            if (!this.checkPerFilePermission(sender instanceof Player ? (Player)sender : null, fn)) {
                return this.returnMSG(true, sender, new Object[]{"msg_nopermforfile", fn, 'c', '4'});
            } else {
                World w = Bukkit.getWorld(wn);
                if (w == null) {
                    this.printMSG(sender, new Object[]{"msg_unknownworld", 'c', '4', wn});
                    return true;
                } else if (!this.isIntegerSigned(new String[]{xs, ys, zs})) {
                    this.printMSG(sender, new Object[]{"msg_wrongcoordinate", 'c', '4', xs, ys, zs});
                    return true;
                } else {
                    int x = Integer.parseInt(xs);
                    int y = Integer.parseInt(ys);
                    int z = Integer.parseInt(zs);
                    BlockVector3 v = BlockVector3.at(x, y, z);
                    QueueManager.addQueue(sender, w, v, fn);
                    this.printMSG(sender, new Object[]{"msg_loadstarted", new Location(w, (double)x, (double)y, (double)z), fn});
                    return true;
                }
            }
        } else {
            return false;
        }
    }

    private boolean executeCmd(CommandSender sender, String cmd, String arg) {
        if (cmd.equalsIgnoreCase("load")) {
            if (!this.checkPerFilePermission(sender instanceof Player ? (Player)sender : null, arg)) {
                return this.returnMSG(true, sender, new Object[]{"msg_nopermforfile", arg, 'c', '4'});
            }

            if (!(sender instanceof Player)) {
                this.printMSG(sender, new Object[]{"msg_senderisnotplayer", 'c'});
                return true;
            }

            Player p = (Player)sender;
            QueueManager.addQueue(p, arg);
            this.printMSG(p, new Object[]{"msg_loadstarted", p.getLocation(), arg});
        } else {
            if (!cmd.equalsIgnoreCase("list")) {
                return false;
            }

            int page = 1;
            if (this.isIntegerGZ(arg)) {
                page = Integer.parseInt(arg);
            }

            File f = new File(this.plg.schem_dir);
            List<String> files = new ArrayList();
            String[] var7 = f.list();
            int var8 = var7.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                String fn = var7[var9];
                if (fn.endsWith(".schematic")) {
                    files.add(fn.substring(0, fn.length() - 10));
                }
            }

            this.printPage(sender, files, page, "msg_filelist", "", false, 20);
        }

        return true;
    }

    private boolean executeCmd(CommandSender sender, String cmd) {
        if (cmd.equalsIgnoreCase("cfg")) {
            this.printConfig(sender, 1, 100, false, true);
        } else if (cmd.equalsIgnoreCase("reload")) {
            this.plg.reloadCfg();
            this.printMSG(sender, new Object[]{"msg_reloaded"});
        } else if (cmd.equalsIgnoreCase("list")) {
            File f = new File(this.plg.schem_dir);
            List<String> files = new ArrayList();
            String[] var5 = f.list();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                String fn = var5[var7];
                if (fn.endsWith(".schematic")) {
                    files.add(fn.substring(0, fn.length() - 10));
                }
            }

            this.printPage(sender, files, 1, "msg_filelist", "", false, 20);
        } else {
            if (!cmd.equalsIgnoreCase("help")) {
                return false;
            }

            this.PrintHlpList(sender, 1, 100);
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
        this.addMSG("msg_senderisnotplayer", "You need to specify the world and coordinates or run this command as a logged player");
        this.addMSG("msg_reloaded", "Configuration reloaded");
        this.addMSG("msg_filelist", "File list (extension not shown)");
        this.addMSG("cfgmsg_schematic-loader_use-worldedit-folder", "Use WorldEdit folder: %1%");
        this.addMSG("cfgmsg_schematic-loader_blocks-per-tick", "Number of blocks to place during the single tick: %1%");
        this.addMSG("cfgmsg_schematic-loader_delay-between-ticks", "Delay between ticks (min = 1): %1%");
        this.addMSG("cfgmsg_schematic-loader_fast-place", "Place blocks fast (without physics): %1%");
        this.addMSG("msg_nopermforfile", "You have not enough permissions to load file %1%");
    }

    public List<Entity> getEntities(Location l1, Location l2) {
        List<Entity> entities = new ArrayList();
        if (!l1.getWorld().equals(l2.getWorld())) {
            return entities;
        } else {
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

            for(int x = chX1; x <= chX2; ++x) {
                for(int z = chZ1; z <= chZ2; ++z) {
                    Entity[] var16 = l1.getWorld().getChunkAt(x, z).getEntities();
                    int var17 = var16.length;

                    for(int var18 = 0; var18 < var17; ++var18) {
                        Entity e = var16[var18];
                        double ex = e.getLocation().getX();
                        double ey = e.getLocation().getY();
                        double ez = e.getLocation().getZ();
                        if ((double)x1 <= ex && ex <= (double)x2 && (double)y1 <= ey && ey <= (double)y2 && (double)z1 <= ez && ez <= (double)z2) {
                            entities.add(e);
                        }
                    }
                }
            }

            return entities;
        }
    }

    private boolean checkPerFilePermission(Player player, String fileName) {
        if (!this.plg.usePermPerFile) {
            return true;
        } else if (player == null) {
            return true;
        } else {
            return !player.hasPermission("schematic.file") && !player.hasPermission("schematic.file.*") ? player.hasPermission("schematic.file." + fileName.toLowerCase()) : true;
        }
    }
}

