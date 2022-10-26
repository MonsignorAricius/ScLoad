package me.aricius.scload;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TabCompletions implements TabCompleter{
    static TabCompletions instance;
    public TabCompletions() {
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<String>();
        if (args.length < 2 || args[0].equalsIgnoreCase("")) {
            completions.add("load");
            completions.add("reload");
            completions.add("help");
            completions.add("cfg");
            completions.add("list");

            Set<String> oldVals = new HashSet<>();//Hard to explain, basicly if the user types "/scload lo", then the only tab complete option will be "load" opposed to all the options
            oldVals.addAll(completions);          

            completions.clear();

            for(String s:oldVals) {
                if(s.trim().startsWith(args[0].trim().toLowerCase())) {
                    completions.add(s);
                }
            }
            return completions;
        }
        if (args[0].equalsIgnoreCase("load")) {
            Plugin fawe = Bukkit.getServer().getPluginManager().getPlugin("FastAsyncWorldEdit");
            if (fawe != null) {
                File schmFolder = new File(fawe.getDataFolder() + File.separator + "schematics");
                File[] listOfFiles = schmFolder.listFiles();
                for (File file: listOfFiles)
                    if (file.getName().lastIndexOf('.') != -1)
                    completions.add(file.getName().substring(0, file.getName().lastIndexOf('.')));
            }
            Plugin we = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
            if (we != null) {
                File schmFolder = new File(we.getDataFolder() + File.separator + "schematics");
                File[] listOfFiles = schmFolder.listFiles();
                for (File file: listOfFiles)
                    if (file.getName().lastIndexOf('.') != -1)
                    completions.add(file.getName().substring(0, file.getName().lastIndexOf('.')));
            }
            
            Set<String> oldVals = new HashSet<>();
            oldVals.addAll(completions); 

            completions.clear();

            for(String s:oldVals) {
                if(s.trim().startsWith(args[1].trim().toLowerCase())) {
                    completions.add(s);
                }
            }
            return completions;
        }
         

        return Collections.emptyList();
    }
}
