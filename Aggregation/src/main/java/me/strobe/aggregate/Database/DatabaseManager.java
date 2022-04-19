package me.strobe.aggregate.Database;

import me.strobe.aggregate.Aggregation;
import me.strobe.aggregate.Logger;
import me.strobe.aggregate.econ.Economy;
import org.bukkit.OfflinePlayer;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class DatabaseManager {

    private final Aggregation pl = Aggregation.getPlugin();
    // configuration values
    private final String host = pl.getConfigFile().getCustomConfig().getString("database.host");
    private final int port = pl.getConfigFile().getCustomConfig().getInt("database.port");
    private final String database = pl.getConfigFile().getCustomConfig().getString("database.database") == null ?
            "aggregation_database" : pl.getConfigFile().getCustomConfig().getString("database.database");
    private final String username = pl.getConfigFile().getCustomConfig().getString("database.username");
    private final String password = pl.getConfigFile().getCustomConfig().getString("database.password");

    private final boolean useCredentials = pl.getConfigFile().getCustomConfig().getBoolean("database.credentials");

    private final List<String> tableNames = pl.getConfigFile().getCustomConfig().getStringList("database.economies");

    private Connection conn;
    private String currentTableName;
    //                      Tables : currency names
    //databse layout :       fields : UUID, NAME, BALANCE



    public DatabaseManager(HashMap<String, Economy> econStorage){
        String connectionLine = "jdbc:mysql://"+host+ ((port != 0)? ":"+port : "/" ) +database;
        try {
            if(useCredentials)
                this.conn = DriverManager.getConnection(connectionLine, username, password);
            else
                this.conn = DriverManager.getConnection(connectionLine);
        }
        catch(SQLException e){
            Logger.errorMsg(Aggregation.class, "Connection to database failed: " + e.getMessage());
        }
    }

    public void initialzeTables() {
        try {
            DatabaseMetaData meta = conn.getMetaData();
            tableNames.forEach(name -> {
                try {
                    ResultSet results = meta.getTables(null, null, name.toUpperCase(), new String[]{"TABLE"});
                    if(!results.next()){
                        String sql = "CREATE TABLE '"+ name.toUpperCase() +"'"+
                                "('ID' CHAR(255) not NULL, " +
                                " 'BALANCE' INT" +
                                " PRIMARY KEY ( 'ID' ))";
                        //Creates the table now that we hav ran the SQL code thru JDBC
                        //This also sets the sorting by the UUID of the players
                        conn.createStatement().execute(sql);
                    }
                    else {

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void disconnect(){
        try {
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            Logger.errorMsg(Aggregation.class, "Failed to safely disconnect from database: " + e.getMessage());
        }
    }

    public boolean hasAccount(final OfflinePlayer p1) {
//        conn.createStatement().execute("");
    }

    public boolean hasAccount(final OfflinePlayer p0, final String p1) {

    }

    public double getBalance(final OfflinePlayer p0) {

    }

    public double getBalance(final OfflinePlayer p0, final String p1) {

    }

    public boolean has(final OfflinePlayer p0, final double p1) {

    }

    public boolean has(final OfflinePlayer p0, final String p1, final double p2) {

    }

    public boolean createPlayerAccount(final OfflinePlayer p0) {

    }

    public boolean createPlayerAccount(final OfflinePlayer p0, final String p1) {

    }
}
