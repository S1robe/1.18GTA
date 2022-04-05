package me.strobe.core;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import lombok.Getter;
import me.strobe.core.Managers.DatabaseManager;
import me.strobe.core.Managers.UserManager;
import me.strobe.files.CustomFile;
import me.strobe.files.FileAPI;
import me.strobe.files.FileManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class Core extends JavaPlugin {

    @Getter private static Core core;
    @Getter private static FileManager fileManager;
    @Getter private final CustomFile configFile;
    @Getter private final CustomFile lootFile;
    @Getter private final WorldGuardPlugin worldGuardPlugin;
    @Getter private final WorldGuard worldGuard;
    @Getter private final PluginManager plMan;
    @Getter private final DatabaseManager databaseManager;
    @Getter private final UserManager userManager;


    protected Core(){
        core = this;
        fileManager = FileAPI.getFMan();
        configFile = new CustomFile.Builder(this,"config").build();
        lootFile = new CustomFile.Builder(this,"loot").build();
        worldGuardPlugin = WorldGuardPlugin.inst();
        worldGuard = WorldGuard.getInstance();
        plMan = getServer().getPluginManager();
        databaseManager = new DatabaseManager();
        userManager = new UserManager();
    }

    /**
     * Needed for CommandAPI to work
     */
    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIConfig().verboseOutput(true)); // Load with verbose output
    }

    @Override
    public void onDisable(){

    }

    @Override
    public synchronized void onEnable(){
        fileManager.registerFile(this, configFile);
        fileManager.registerFile(this, lootFile);
        databaseManager.connect();
        // Needed in order to use CommandAPI
        CommandAPI.onEnable(this);
    }
}
