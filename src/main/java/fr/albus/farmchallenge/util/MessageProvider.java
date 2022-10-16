package fr.albus.farmchallenge.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageProvider {

    private static final char COLOR_CHAR = '&';

    public static void sendPlayerMessage(Player player, String message) {
        player.sendMessage(MessageProvider.parseColors(message));
    }

    public static String parseColors(String text) {
        return ChatColor.translateAlternateColorCodes(COLOR_CHAR, text);
    }
}
