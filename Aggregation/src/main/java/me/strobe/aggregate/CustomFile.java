package me.strobe.aggregate;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class CustomFile {

   //local reference for readability
   private final Aggregation main = Aggregation.getPlugin();
   //File's temporary name
   private final String fileName;
   //Current configFile rep
   private File customConfigFile;
   //Current Bukkit representation
   private FileConfiguration customConfig;

   /**
    * @param fileName The file name for this file.
    */
   public CustomFile(String fileName) {
      this.fileName = fileName;
      createConfigsFile();
   }

   private void createConfigsFile() {
      this.customConfigFile = new File(this.main.getDataFolder(), this.fileName + ".yml");
      this.main.saveResource(this.fileName + ".yml", false);
      this.customConfig = YamlConfiguration.loadConfiguration(this.customConfigFile);
   }

   public void saveCustomConfig() {
      if(this.customConfig == null || this.customConfigFile == null || !this.customConfigFile.exists())
         createConfigsFile();
      else
         try { this.getCustomConfig().save(this.customConfigFile); }
         catch(IOException ex) {
            Logger.log(Aggregation.class, "Could not save config to " + this.customConfigFile);
         }
   }

   public FileConfiguration getCustomConfig() {
      if(this.customConfig == null) {
         this.reloadCustomConfig();
      }
      return this.customConfig;
   }

   public void reloadCustomConfig() {
      if(this.customConfigFile == null) {
         this.customConfigFile = new File(this.main.getDataFolder(), this.fileName + ".yml");
      }
      this.customConfig = YamlConfiguration.loadConfiguration(this.customConfigFile);
      InputStreamReader defConfigStream = new InputStreamReader(this.main.getResource(this.fileName + ".yml"));
      YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
      this.customConfig.setDefaults(defConfig);
   }

   String getFileName() {
      return this.fileName;
   }
}

