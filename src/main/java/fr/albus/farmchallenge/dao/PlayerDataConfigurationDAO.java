package fr.albus.farmchallenge.dao;

import org.bukkit.entity.Player;

import java.io.IOException;

public interface PlayerDataConfigurationDAO {

    void incrementPlayerProgress(Player player, int amount) throws IOException;

    int getPlayerProgress(Player player) throws IOException;
}
