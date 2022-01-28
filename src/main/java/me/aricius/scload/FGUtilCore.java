package me.aricius.scload;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public abstract class FGUtilCore {
    JavaPlugin plg;
    private String px = "";
    private String permprefix = "fgutilcore.";
    private String language = "english";
    private String plgcmd = "<command>";
    YamlConfiguration lng;
    private boolean savelng = false;
    protected HashMap<String, String> msg = new HashMap();
    private char c1 = 'a';
    private char c2 = '2';
    protected String msglist = "";
    private boolean colorconsole = false;
    private Set<String> log_once = new HashSet();
    protected HashMap<String, FGUtilCore.Cmd> cmds = new HashMap();
    protected String cmdlist = "";
    PluginDescriptionFile des;
    private Logger log = Logger.getLogger("Minecraft");
    Random random = new Random();
    BukkitTask chId;
    private boolean project_check_version = true;
    private String project_id = "";
    private String project_name = "";
    private String project_current_version = "";
    private String project_last_version = "";
    private String project_curse_url = "";
    private String version_info_perm;
    private String project_bukkitdev;

    public FGUtilCore(JavaPlugin plg, boolean savelng, String lng, String plgcmd, String permissionPrefix) {
        this.version_info_perm = this.permprefix + "config";
        this.project_bukkitdev = "";
        this.permprefix = permissionPrefix.endsWith(".") ? permissionPrefix : permissionPrefix + ".";
        this.plg = plg;
        this.des = plg.getDescription();
        this.language = lng;
        this.InitMsgFile();
        this.initStdMsg();
        this.fillLoadedMessages();
        this.savelng = savelng;
        this.plgcmd = plgcmd;
        this.px = ChatColor.translateAlternateColorCodes('&', "&3[" + this.des.getName() + "]&f ");
    }

    private void initStdMsg() {
        this.addMSG("msg_outdated", "%1% is outdated!");
        this.addMSG("msg_pleasedownload", "Please download new version (%1%) from ");
        this.addMSG("hlp_help", "Help");
        this.addMSG("hlp_thishelp", "%1% - this help");
        this.addMSG("hlp_execcmd", "%1% - execute command");
        this.addMSG("hlp_typecmd", "Type %1% - to get additional help");
        this.addMSG("hlp_typecmdpage", "Type %1% - to see another page of this help");
        this.addMSG("hlp_commands", "Command list:");
        this.addMSG("hlp_cmdparam_command", "command");
        this.addMSG("hlp_cmdparam_page", "page");
        this.addMSG("hlp_cmdparam_parameter", "parameter");
        this.addMSG("cmd_unknown", "Unknown command: %1%");
        this.addMSG("cmd_cmdpermerr", "Something wrong (check command, permissions)");
        this.addMSG("enabled", "enabled");
        this.msg.put("enabled", ChatColor.DARK_GREEN + (String)this.msg.get("enabled"));
        this.addMSG("disabled", "disabled");
        this.msg.put("disabled", ChatColor.RED + (String)this.msg.get("disabled"));
        this.addMSG("lst_title", "String list:");
        this.addMSG("lst_footer", "Page: [%1% / %2%]");
        this.addMSG("lst_listisempty", "List is empty");
        this.addMSG("msg_config", "Configuration");
        this.addMSG("cfgmsg_general_check-updates", "Check updates: %1%");
        this.addMSG("cfgmsg_general_language", "Language: %1%");
        this.addMSG("cfgmsg_general_language-save", "Save translation file: %1%");
    }

    public void setConsoleColored(boolean colorconsole) {
        this.colorconsole = colorconsole;
    }

    public boolean isConsoleColored() {
        return this.colorconsole;
    }

    public void addCmd(String cmd, String perm, String desc_id, String desc_key) {
        this.addCmd(cmd, perm, desc_id, desc_key, this.c1, this.c2, false);
    }

    public void addCmd(String cmd, String perm, String desc_id, String desc_key, char color) {
        this.addCmd(cmd, perm, desc_id, desc_key, this.c1, color, false);
    }

    public void addCmd(String cmd, String perm, String desc_id, String desc_key, boolean console) {
        this.addCmd(cmd, perm, desc_id, desc_key, this.c1, this.c2, console);
    }

    public void addCmd(String cmd, String perm, String desc_id, String desc_key, char color, boolean console) {
        this.addCmd(cmd, perm, desc_id, desc_key, this.c1, color, console);
    }

    public void addCmd(String cmd, String perm, String desc_id, String desc_key, char color1, char color2) {
        this.addCmd(cmd, perm, desc_id, desc_key, color1, color2, false);
    }

    public void addCmd(String cmd, String perm, String desc_id, String desc_key, char color1, char color2, boolean console) {
        String desc = this.getMSG(desc_id, desc_key, color1, color2);
        this.cmds.put(cmd, new FGUtilCore.Cmd(this.permprefix + perm, desc, console));
        if (this.cmdlist.isEmpty()) {
            this.cmdlist = cmd;
        } else {
            this.cmdlist = this.cmdlist + ", " + cmd;
        }

    }

    public boolean checkCmdPerm(CommandSender sender, String cmd) {
        if (!this.cmds.containsKey(cmd.toLowerCase())) {
            return false;
        } else {
            FGUtilCore.Cmd cm = (FGUtilCore.Cmd)this.cmds.get(cmd.toLowerCase());
            if (!(sender instanceof Player)) {
                return cm.console;
            } else {
                return cm.perm.isEmpty() || sender.hasPermission(cm.perm);
            }
        }
    }

    public void printPage(CommandSender p, List<String> ln, int pnum, String title, String footer, boolean shownum) {
        FGUtilCore.PageList pl = new FGUtilCore.PageList(ln, title, footer, shownum);
        pl.printPage(p, pnum);
    }

    public void printPage(CommandSender p, List<String> ln, int pnum, String title, String footer, boolean shownum, int lineperpage) {
        FGUtilCore.PageList pl = new FGUtilCore.PageList(ln, title, footer, shownum);
        pl.printPage(p, pnum, lineperpage);
    }

    public boolean isIdInList(int id, String str) {
        if (!str.isEmpty()) {
            String[] ln = str.split(",");
            if (ln.length > 0) {
                for(int i = 0; i < ln.length; ++i) {
                    if (!ln[i].isEmpty() && ln[i].matches("[0-9]*") && Integer.parseInt(ln[i]) == id) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean isAllIdInList(int[] ids, String str) {
        int[] var3 = ids;
        int var4 = ids.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            int id = var3[var5];
            if (!this.isIdInList(id, str)) {
                return false;
            }
        }

        return true;
    }

    public boolean isWordInList(String word, String str) {
        String[] ln = str.split(",");
        if (ln.length > 0) {
            for(int i = 0; i < ln.length; ++i) {
                if (ln[i].equalsIgnoreCase(word)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isItemInList(String id, int data, String str) {
        String[] ln = str.split(",");
        if (ln.length > 0) {
            for(int i = 0; i < ln.length; ++i) {
                if (this.compareItemStr(id, data, ln[i])) {
                    return true;
                }
            }
        }

        return false;
    }

    /** @deprecated */
    @Deprecated
    public boolean compareItemStr(String item_id, int item_data, String itemstr) {
        return this.compareItemStrIgnoreName(item_id, item_data, 1, itemstr);
    }

    public boolean compareItemStr(ItemStack item, String str) {
        String itemstr = str;
        String name = "";
        if (str.contains("$")) {
            name = str.substring(0, str.indexOf("$"));
            name = ChatColor.translateAlternateColorCodes('&', name.replace("_", " "));
            itemstr = str.substring(name.length() + 1);
        }

        if (itemstr.isEmpty()) {
            return false;
        } else {
            if (!name.isEmpty()) {
                String iname = item.hasItemMeta() ? item.getItemMeta().getDisplayName() : "";
                if (!name.equals(iname)) {
                    return false;
                }
            }

            return this.compareItemStrIgnoreName(item.getType().name(), item.getDurability(), item.getAmount(), itemstr);
        }
    }

    public boolean compareItemStrIgnoreName(String item_id, int item_data, int item_amount, String itemstr) {
        if (!itemstr.isEmpty()) {
            String id = null;
            int amount = 1;
            int data = -1;
            String[] si = itemstr.split("\\*");
            if (si.length > 0) {
                if (si.length == 2 && si[1].matches("[1-9]+[0-9]*")) {
                    amount = Integer.parseInt(si[1]);
                }

                String[] ti = si[0].split(":");
                if (ti.length > 0) {
                    try {
                        id = Material.getMaterial(ti[0]).name();
                    } catch (Exception var11) {
                        this.logOnce("unknownitem" + ti[0], "Unknown item: " + ti[0]);
                    }

                    if (ti.length == 2 && ti[1].matches("[0-9]*")) {
                        data = Integer.parseInt(ti[1]);
                    }

                    return item_id.equals(id) && (data < 0 || item_data == data) && item_amount >= amount;
                }
            }
        }

        return false;
    }

    public void giveItemOrDrop(Player p, ItemStack item) {
        Iterator var3 = p.getInventory().addItem(new ItemStack[]{item}).values().iterator();

        while(var3.hasNext()) {
            ItemStack i = (ItemStack)var3.next();
            p.getWorld().dropItemNaturally(p.getLocation(), i);
        }

    }

    public void printMsg(CommandSender p, String msg) {
        String message = ChatColor.translateAlternateColorCodes('&', msg);
        if (!(p instanceof Player) && !this.colorconsole) {
            message = ChatColor.stripColor(message);
        }

        p.sendMessage(message);
    }

    public void printPxMsg(CommandSender p, String msg) {
        this.printMsg(p, this.px + msg);
    }

    public void BC(String msg) {
        this.plg.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', this.px + msg));
    }

    public void broadcastMSG(String perm, Object... s) {
        Iterator var3 = Bukkit.getOnlinePlayers().iterator();

        while(var3.hasNext()) {
            Player p = (Player)var3.next();
            if (p.hasPermission(this.permprefix + perm)) {
                this.printMSG(p, s);
            }
        }

    }

    public void broadcastMsg(String perm, String msg) {
        Iterator var3 = Bukkit.getOnlinePlayers().iterator();

        while(var3.hasNext()) {
            Player p = (Player)var3.next();
            if (p.hasPermission(this.permprefix + perm)) {
                this.printMsg(p, msg);
            }
        }

    }

    public void log(String msg) {
        this.log.info(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', this.px + msg)));
    }

    public void logOnce(String error_id, String msg) {
        if (!this.log_once.contains(error_id)) {
            this.log_once.add(error_id);
            this.log.info(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', this.px + msg)));
        }

    }

    public void SC(String msg) {
        this.plg.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', this.px + msg));
    }

    public void InitMsgFile() {
        try {
            this.lng = new YamlConfiguration();
            File f = new File(this.plg.getDataFolder() + File.separator + "language" + File.separator + this.language + ".lng");
            if (!f.exists()) {
                this.plg.saveResource("language/" + this.language + ".lng", false);
            }

            this.lng.load(f);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public void fillLoadedMessages() {
        if (this.lng != null) {
            Iterator var1 = this.lng.getKeys(true).iterator();

            while(var1.hasNext()) {
                String key = (String)var1.next();
                this.addMSG(key, this.lng.getString(key));
            }
        }

    }

    public void addMSG(String key, String txt) {
        this.msg.put(key, ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', this.lng.getString(key, txt))));
        if (this.msglist.isEmpty()) {
            this.msglist = key;
        } else {
            this.msglist = this.msglist + "," + key;
        }

    }

    public void SaveMSG() {
        String[] keys = this.msglist.split(",");

        try {
            File f = new File(this.plg.getDataFolder() + File.separator + this.language + ".lng");
            if (!f.exists()) {
                f.createNewFile();
            }

            YamlConfiguration cfg = new YamlConfiguration();

            for(int i = 0; i < keys.length; ++i) {
                cfg.set(keys[i], this.msg.get(keys[i]));
            }

            cfg.save(f);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public String getMSG(Object... s) {
        String str = "&4Unknown message";
        String color1 = "&" + this.c1;
        String color2 = "&" + this.c2;
        if (s.length > 0) {
            String id = s[0].toString();
            str = "&4Unknown message (" + id + ")";
            if (this.msg.containsKey(id)) {
                int px = 1;
                if (s.length > 1 && s[1] instanceof Character) {
                    px = 2;
                    color1 = "&" + (Character)s[1];
                    if (s.length > 2 && s[2] instanceof Character) {
                        px = 3;
                        color2 = "&" + (Character)s[2];
                    }
                }

                str = color1 + (String)this.msg.get(id);
                if (px < s.length) {
                    for(int i = px; i < s.length; ++i) {
                        String f = s[i].toString();
                        if (s[i] instanceof Location) {
                            Location loc = (Location)s[i];
                            f = loc.getWorld().getName() + "[" + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + "]";
                        }

                        str = str.replace("%" + Integer.toString(i - px + 1) + "%", color2 + f + color1);
                    }
                }
            } else if (this.savelng) {
                this.addMSG(id, str);
                this.SaveMSG();
            }
        }

        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public void printMSG(CommandSender p, Object... s) {
        String message = this.getMSG(s);
        if (!(p instanceof Player) && !this.colorconsole) {
            message = ChatColor.stripColor(message);
        }

        p.sendMessage(message);
    }

    public void PrintHLP(Player p) {
        this.printMsg(p, "&6&l" + this.project_name + " v" + this.des.getVersion() + " &r&6| " + this.getMSG("hlp_help", '6'));
        this.printMSG(p, "hlp_thishelp", "/" + this.plgcmd + " help");
        this.printMSG(p, "hlp_execcmd", "/" + this.plgcmd + " <" + this.getMSG("hlp_cmdparam_command", '2') + "> [" + this.getMSG("hlp_cmdparam_parameter", '2') + "]");
        this.printMSG(p, "hlp_typecmd", "/" + this.plgcmd + " help <" + this.getMSG("hlp_cmdparam_command", '2') + ">");
        this.printMsg(p, this.getMSG("hlp_commands") + " &2" + this.cmdlist);
    }

    public void printHLP(Player p, String cmd) {
        if (this.cmds.containsKey(cmd)) {
            this.printMsg(p, "&6&l" + this.project_name + " v" + this.des.getVersion() + " &r&6| " + this.getMSG("hlp_help", '6'));
            this.printMsg(p, ((FGUtilCore.Cmd)this.cmds.get(cmd)).desc);
        } else {
            this.printMSG(p, "cmd_unknown", 'c', 'e', cmd);
        }

    }

    public void PrintHlpList(CommandSender p, int page, int lpp) {
        String title = "&6&l" + this.project_name + " v" + this.des.getVersion() + " &r&6| " + this.getMSG("hlp_help", '6');
        List<String> hlp = new ArrayList();
        hlp.add(this.getMSG("hlp_thishelp", "/" + this.plgcmd + " help"));
        hlp.add(this.getMSG("hlp_execcmd", "/" + this.plgcmd + " <" + this.getMSG("hlp_cmdparam_command", '2') + "> [" + this.getMSG("hlp_cmdparam_parameter", '2') + "]"));
        if (p instanceof Player) {
            hlp.add(this.getMSG("hlp_typecmdpage", "/" + this.plgcmd + " help <" + this.getMSG("hlp_cmdparam_page", '2') + ">"));
        }

        String[] ks = this.cmdlist.replace(" ", "").split(",");
        if (ks.length > 0) {
            String[] var7 = ks;
            int var8 = ks.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                String cmd = var7[var9];
                hlp.add(((FGUtilCore.Cmd)this.cmds.get(cmd)).desc);
            }
        }

        this.printPage(p, hlp, page, title, "", false, lpp);
    }

    public String EnDis(boolean b) {
        return b ? this.getMSG("enabled", '2') : this.getMSG("disabled", 'c');
    }

    public String EnDis(String str, boolean b) {
        String str2 = ChatColor.stripColor(str);
        return b ? ChatColor.DARK_GREEN + str2 : ChatColor.RED + str2;
    }

    public void printEnDis(CommandSender p, String msg_id, boolean b) {
        p.sendMessage(this.getMSG(msg_id) + ": " + this.EnDis(b));
    }

    public void setPermPrefix(String ppfx) {
        this.permprefix = ppfx + ".";
        this.version_info_perm = this.permprefix + "config";
    }

    public boolean equalCmdPerm(String cmd, String perm) {
        return this.cmds.containsKey(cmd.toLowerCase()) && ((FGUtilCore.Cmd)this.cmds.get(cmd.toLowerCase())).perm.equalsIgnoreCase(this.permprefix + perm);
    }

    public Color colorByName(String colorname) {
        Color[] clr = new Color[]{Color.WHITE, Color.SILVER, Color.GRAY, Color.BLACK, Color.RED, Color.MAROON, Color.YELLOW, Color.OLIVE, Color.LIME, Color.GREEN, Color.AQUA, Color.TEAL, Color.BLUE, Color.NAVY, Color.FUCHSIA, Color.PURPLE};
        String[] clrs = new String[]{"WHITE", "SILVER", "GRAY", "BLACK", "RED", "MAROON", "YELLOW", "OLIVE", "LIME", "GREEN", "AQUA", "TEAL", "BLUE", "NAVY", "FUCHSIA", "PURPLE"};

        for(int i = 0; i < clrs.length; ++i) {
            if (colorname.equalsIgnoreCase(clrs[i])) {
                return clr[i];
            }
        }

        return null;
    }

    public boolean isPlayerAround(Location loc, int radius) {
        Iterator var3 = loc.getWorld().getPlayers().iterator();

        while(var3.hasNext()) {
            Player p = (Player)var3.next();
            if (p.getLocation().distance(loc) <= (double)radius) {
                return true;
            }
        }

        return false;
    }

    public String getMSGnc(Object... s) {
        return ChatColor.stripColor(this.getMSG(s));
    }

    public boolean rollDiceChance(int chance) {
        return this.random.nextInt(100) < chance;
    }

    public int tryChance(int chance) {
        return this.random.nextInt(chance);
    }

    public int getRandomInt(int maxvalue) {
        return this.random.nextInt(maxvalue);
    }

    public boolean isIntegerSigned(String str) {
        return str.matches("-?[0-9]+[0-9]*");
    }

    public boolean isIntegerSigned(String... str) {
        if (str.length == 0) {
            return false;
        } else {
            String[] var2 = str;
            int var3 = str.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String s = var2[var4];
                if (!s.matches("-?[0-9]+[0-9]*")) {
                    return false;
                }
            }

            return true;
        }
    }

    public boolean isIntegerGZ(String str) {
        return str.matches("[1-9]+[0-9]*");
    }

    public void printConfig(CommandSender p, int page, int lpp, boolean section, boolean usetranslation) {
        List<String> cfgprn = new ArrayList();
        if (!this.plg.getConfig().getKeys(true).isEmpty()) {
            Iterator var7 = this.plg.getConfig().getKeys(true).iterator();

            label33:
            while(true) {
                String k;
                String value;
                String str;
                while(true) {
                    if (!var7.hasNext()) {
                        break label33;
                    }

                    k = (String)var7.next();
                    Object objvalue = this.plg.getConfig().get(k);
                    value = objvalue.toString();
                    str = k;
                    if (objvalue instanceof Boolean && usetranslation) {
                        value = this.EnDis((Boolean)objvalue);
                    }

                    if (objvalue instanceof MemorySection) {
                        if (!section) {
                            continue;
                        }
                        break;
                    }

                    str = k + " : " + value;
                    break;
                }

                if (usetranslation) {
                    str = this.getMSG("cfgmsg_" + k.replaceAll("\\.", "_"), value);
                }

                cfgprn.add(str);
            }
        }

        String title = "&6&l" + this.project_current_version + " v" + this.des.getVersion() + " &r&6| " + this.getMSG("msg_config", '6');
        this.printPage(p, cfgprn, page, title, "", false);
    }

    public boolean returnMSG(boolean result, CommandSender p, Object... s) {
        if (p != null) {
            this.printMSG(p, s);
        }

        return result;
    }

    public class Cmd {
        String perm;
        String desc;
        boolean console;

        public Cmd(String perm, String desc) {
            this.perm = perm;
            this.desc = desc;
            this.console = false;
        }

        public Cmd(String perm, String desc, boolean console) {
            this.perm = perm;
            this.desc = desc;
            this.console = console;
        }
    }

    public class PageList {
        private List<String> ln;
        private int lpp = 15;
        private String title_msgid = "lst_title";
        private String footer_msgid = "lst_footer";
        private boolean shownum = false;

        public void setLinePerPage(int lpp) {
            this.lpp = lpp;
        }

        public PageList(List<String> ln, String title_msgid, String footer_msgid, boolean shownum) {
            this.ln = ln;
            if (!title_msgid.isEmpty()) {
                this.title_msgid = title_msgid;
            }

            if (!footer_msgid.isEmpty()) {
                this.footer_msgid = footer_msgid;
            }

            this.shownum = shownum;
        }

        public void addLine(String str) {
            this.ln.add(str);
        }

        public boolean isEmpty() {
            return this.ln.isEmpty();
        }

        public void setTitle(String title_msgid) {
            this.title_msgid = title_msgid;
        }

        public void setShowNum(boolean shownum) {
        }

        public void setFooter(String footer_msgid) {
            this.footer_msgid = footer_msgid;
        }

        public void printPage(CommandSender p, int pnum) {
            this.printPage(p, pnum, this.lpp);
        }

        public void printPage(CommandSender p, int pnum, int linesperpage) {
            if (this.ln.size() > 0) {
                int maxp = this.ln.size() / linesperpage;
                if (this.ln.size() % linesperpage > 0) {
                    ++maxp;
                }

                if (pnum > maxp) {
                    pnum = maxp;
                }

                int maxl = linesperpage;
                if (pnum == maxp) {
                    maxl = this.ln.size() % linesperpage;
                    if (maxp == 1) {
                        maxl = this.ln.size();
                    }
                }

                if (maxl == 0) {
                    maxl = linesperpage;
                }

                if (FGUtilCore.this.msg.containsKey(this.title_msgid)) {
                    FGUtilCore.this.printMsg(p, "&6&l" + FGUtilCore.this.getMSGnc(this.title_msgid));
                } else {
                    FGUtilCore.this.printMsg(p, this.title_msgid);
                }

                String numpx = "";

                for(int i = 0; i < maxl; ++i) {
                    if (this.shownum) {
                        numpx = "&3" + Integer.toString(1 + i + (pnum - 1) * linesperpage) + ". ";
                    }

                    FGUtilCore.this.printMsg(p, numpx + "&a" + (String)this.ln.get(i + (pnum - 1) * linesperpage));
                }

                if (maxp > 1) {
                    FGUtilCore.this.printMSG(p, this.footer_msgid, 'e', '6', pnum, maxp);
                }
            } else {
                FGUtilCore.this.printMSG(p, "lst_listisempty", 'c');
            }

        }
    }
}
