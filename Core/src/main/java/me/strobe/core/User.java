package me.strobe.core;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.xml.stream.Location;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter
public class User {

    private Player player;
    private UUID uuid;
    private String name;
    private HashMap<Location, Long> chestLocations;
    private HashMap<String, List<ItemStack>> chestLoot;
    private double balance;
    private int oddFragments;
    private int boardType;
    private float chestVolume;
    private int pvpKills;
    private int pvpDeaths;
    private int playerCopKills;
    private double killStreak;
    private double wantedLevel;
    private boolean isCop;
    private boolean didAttackCop;
    private List<ItemStack> savedPlayerInventory;

    private User() {}

    public User(String uuid){
        this.uuid = UUID.fromString(uuid);
        OfflinePlayer p = Bukkit.getOfflinePlayer(this.uuid);
        this.name = p.getName();
        Core.getCore().getUserManager().attemptToCreateUser(uuid);
        if(p.isOnline())
            this.player = p.getPlayer();
    }
}
