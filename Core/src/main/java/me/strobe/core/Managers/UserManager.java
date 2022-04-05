package me.strobe.core.Managers;

import com.mongodb.client.MongoCollection;
import lombok.NonNull;
import me.strobe.core.Core;
import me.strobe.core.Logger;
import me.strobe.core.User;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class UserManager {

    private final List<User> users;

    public UserManager(){
        this.users = new ArrayList<>();
    }

    /**
     * Get User from player UUID
     *
     * @param UUID | The UUID that should be fetched
     */
    public User getUserByUUID(String UUID) {
        for(User user : users) {
            if(UUID.equals(user.getUuid().toString())) return user;
        }
        return null;
    }

    /**
     * Get User from player name
     *
     * @param playerName | The player name that should be fetched
     */
    public User getUserByName(String playerName) {
        for(User user : users) {
            if(playerName.equals(user.getName())) return user;
        }
        return null;
    }

    /**
     * Get User from OfflinePlayer
     *
     * @param offlinePlayer | The offline player that should be fetched
     * @see OfflinePlayer
     */
    public User getUserByOfflinePlayer(@NonNull OfflinePlayer offlinePlayer) {
        UUID offlinePlayerUUID = offlinePlayer.getUniqueId();
        for(User user : users) {
            if(offlinePlayer.getUniqueId() == offlinePlayerUUID) return user;
        }
        return null;
    }

    /**
     * Get User from Player
     *
     * @param player The player that should be fetched
     * @see Player
     */
    public User getUserByPlayer(@NonNull Player player) {
        UUID playerUUID = player.getUniqueId();
        for(User user : users) {
            if(player.getUniqueId() == playerUUID)
                return user;
        }
        return null;
    }

    public void attemptToCreateUser(String uuid) {
        DatabaseManager dm = Core.getCore().getDatabaseManager();
        Document playerDoc = new Document("UUID", uuid);
        Document found = dm.getPlayers().find(playerDoc).first();

        if(found == null) {
            OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
            setValue(playerDoc, "UUID", uuid);
            setValue(playerDoc, "name", p.getName());
            setValue(playerDoc, "chestLocations", new HashMap<String, Long>());
            setValue(playerDoc, "chestLoot", new HashMap<String, List<String>>());
            setValue(playerDoc, "balance", 0.0);
            setValue(playerDoc, "oddFragments", 0);
            setValue(playerDoc, "boardType", 0);
            setValue(playerDoc, "chestOpenVolume", 100F);
            setValue(playerDoc, "pvpKills", 0);
            setValue(playerDoc, "pvpDeaths", 0);
            setValue(playerDoc, "playerCopKills", 0);
            setValue(playerDoc, "killStreak", 0);
            setValue(playerDoc, "wantedLevel", 0);
            setValue(playerDoc, "isCop", false);
            setValue(playerDoc, "didAttackCop", false);
            setValue(playerDoc, "savedInventory", new ArrayList<String>());

            //insert into database
            dm.getPlayers().insertOne(playerDoc);
            users.add(new User(uuid));

            // send log message to the console
            Logger.log(Core.class, "&aAdded new User to database (Name: " + Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName() + ", UUID: " + uuid + ")");
        }

    }

    // load all player information from the database
    public void loadAllUsers() {
        // mongo collection players are stored in
        MongoCollection<Document> players = Core.getCore().getDatabaseManager().getPlayers();

        // no users were found in the database to load
        if(players.countDocuments() == 0) {
            Logger.log(Core.class, "&eNo users were found in the database to load. No action needed.");
            return;
        }

        players.find().forEach((Consumer<Document>) doc -> {
            users.add(new User(doc.getString("UUID"))); // create new user from database UUID
            User user = getUserByUUID(doc.getString("UUID"));

            assert user != null;

            // load users balance into vault
            Core.getCore().getPlayerAccount().put(user.getUuid()), doc.getDouble("balance"));
        });
        // completed message
        Logger.log(Core.class, "&aSuccessfully loaded all users from the database.");
    }


    private void setValue(Document doc, final String key, final Object value) {doc.append(key, value);}
    private Object getValue(Document doc, final String key, final Object value) {Object temp; return (temp = doc.get(key)) != null? temp : value;}


}