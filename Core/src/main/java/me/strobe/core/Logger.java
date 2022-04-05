package me.strobe.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Logger {

    /**
     * log a message to the console with color
     * @param msg message to send
     */
    public static void log(Class<?> clazz, String msg) {
        msg = ChatColor.translateAlternateColorCodes('&', "&a[&b" + clazz.getName() + "&a]&r " + msg);
        Bukkit.getConsoleSender().sendMessage(msg);
    }

    /**
     * log a debug message in the console with [DEBUG] prefix
     * @param msg msg to send
     */
    public static void debug(Class<?> clazz, String msg) {
        log(clazz, "&7[&eDEBUG&7]&r" + msg);
    }

    /**
     * return a message that starts with ERROR ->
     * @param msg error message to print
     */
    public static void errorMsg(Class<?> clazz, String msg) {
        log(clazz, ChatColor.translateAlternateColorCodes('&', "&c&lERROR -> &7" + msg));
    }
}