package me.aricius.scload;

import java.io.File;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ScLoad extends JavaPlugin {
    boolean use_we_folder = true;
    int blockpertick = 5000;
    int delay = 2;
    boolean vcheck = false;
    boolean language_save = false;
    String language = "english";
    boolean fastplace = true;
    boolean usePermPerFile = false;
    boolean undo_enabled = true;
    String schem_dir;
    SLUtil u;
    static ScLoad instance;

    public ScLoad() {
    }

    public void onEnable() {
        this.reloadCfg();
        this.u = new SLUtil(this, this.vcheck, this.language_save, this.language);
        this.getCommand("scload").setExecutor(this.u);
        this.schem_dir = this.getSchematicDirectory();
        instance = this;
    }

    public void reloadCfg() {
        this.reloadConfig();
        this.language = this.getConfig().getString("general.language", "english");
        this.getConfig().set("general.language", this.language);
        this.language_save = this.getConfig().getBoolean("general.language-save", false);
        this.getConfig().set("general.language-save", this.language_save);
        this.vcheck = this.getConfig().getBoolean("general.check-updates", true);
        this.getConfig().set("general.check-updates", this.vcheck);
        this.use_we_folder = this.getConfig().getBoolean("schematic-loader.use-worldedit-folder", true);
        this.getConfig().set("schematic-loader.use-worldedit-folder", this.use_we_folder);
        this.blockpertick = this.getConfig().getInt("schematic-loader.blocks-per-tick", 5000);
        this.getConfig().set("schematic-loader.blocks-per-tick", this.blockpertick);
        this.delay = this.getConfig().getInt("schematic-loader.delay-between-ticks", 2);
        this.getConfig().set("schematic-loader.delay-between-ticks", this.delay);
        this.fastplace = this.getConfig().getBoolean("schematic-loader.fast-place", false);
        this.getConfig().set("schematic-loader.fast-place", this.fastplace);
        this.usePermPerFile = this.getConfig().getBoolean("schematic-loader.use-permission-per-file", false);
        this.getConfig().set("schematic-loader.use-permission-per-file", this.usePermPerFile);
        this.saveConfig();
    }

    private String getSchematicDirectory() {
        String dir = this.getDataFolder() + File.separator + "schematics";
        if (this.use_we_folder) {
            Plugin we = this.getServer().getPluginManager().getPlugin("WorldEdit");
            if (we != null) {
                dir = we.getDataFolder() + File.separator + "schematics";
            }
        }

        File sd = new File(dir);
        if (!sd.exists()) {
            sd.mkdirs();
        }

        return dir;
    }
}
