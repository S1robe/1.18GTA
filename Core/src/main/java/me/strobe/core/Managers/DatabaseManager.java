package me.strobe.core.Managers;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.strobe.core.Core;
import me.strobe.core.Logger;
import org.bson.Document;


public class DatabaseManager {

    // configuration values
    private final String host = Core.getCore().getConfigFile().getCustomConfig().getString("database.host");
    private final int port = Core.getCore().getConfigFile().getCustomConfig().getInt("database.port");
    private final String database = Core.getCore().getConfigFile().getCustomConfig().getString("database.database") == null ?
            "default_database" : Core.getCore().getConfigFile().getCustomConfig().getString("database.database");
    private final String username = Core.getCore().getConfigFile().getCustomConfig().getString("database.username");
    private final String password = Core.getCore().getConfigFile().getCustomConfig().getString("database.password");

    private final boolean useCredentials = Core.getCore().getConfigFile().getCustomConfig().getBoolean("database.credentials");

    // mongo client
    private MongoClient client;

    @Getter
    private MongoCollection<Document> players;

    public void connect() {
        if(host == null) {
            Logger.errorMsg(Core.class, "&cNo database host is defined in the config.yml.");
            return;
        }
        if(!useCredentials) {
            Logger.log(Core.class, "&6Connecting to database without credentials...");
            client = new com.mongodb.MongoClient(host, port);
        }
        else {
            Logger.log(Core.class, "&6Connecting to database with credentials...");
            MongoClientURI clientURI = new MongoClientURI("mongodb://" + username + ":" + password + "@" + host + "/?authSource=db1&ssl=false");
            client = new MongoClient(clientURI);
        }

        // get database defined in config.yml
        MongoDatabase db = client.getDatabase(database);

        // players collection in database
        // this is where player information is stored
        players = db.getCollection("players");

        Logger.log(Core.class, "&aSuccessfully connected to database [" + db.getName() + "]");
    }

    // close mongo database connection
    public void disconnect() {
        if(client == null)
            Logger.log(Core.class, "&cCannot disconnect from database, no connection.");
        else{
            client.close();
            Logger.log(Core.class, "&aSuccessfully closed connection to database.");
        }
    }
}
