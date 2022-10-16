package fr.albus.farmchallenge.service;

import org.bukkit.entity.Player;

import java.io.IOException;

public interface FarmChallengeService {

    void setFarmBlock(Player player, String material);

    void getFarmBlock(Player player);

    void depositFarmBlock(Player player) throws IOException;

    void getGlobalProgress(Player player);

    void getPlayerProgress(Player player, String targetPlayer) throws IOException;
}
