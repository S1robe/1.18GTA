package me.strobe.files;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class FileAPI extends JavaPlugin {
    private static FileAPI main;
    @Getter private static FileManager fMan;

    public FileAPI(){
        main = this;
        fMan = new FileManager();
    }

    @Override
    public void onEnable(){}

    @Override
    public void onDisable(){
        fMan.saveAllFiles();
    }

}
