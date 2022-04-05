package me.strobe.aggregate;

import lombok.Getter;
import me.strobe.aggregate.Database.DatabaseManager;
import me.strobe.aggregate.econ.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;


@Getter
public class Aggregation extends JavaPlugin {

    @Getter private static Aggregation plugin;
    private ServicesManager sm;
    private DatabaseManager dm;
    private CustomFile configFile;
    private HashMap<String, Economy> economiesLoaded;

    public void onEnable(){
        plugin = this;
        this.sm = Bukkit.getServicesManager();
        configFile = new CustomFile("config");
        economiesLoaded = new HashMap<>();
        dm = new DatabaseManager(economiesLoaded);
    }
}
