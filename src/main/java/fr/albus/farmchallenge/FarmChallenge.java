package fr.albus.farmchallenge;

import fr.albus.farmchallenge.dao.*;
import fr.albus.farmchallenge.manager.CommandManager;
import fr.albus.farmchallenge.menu.FarmChallengeDepositMenu;
import fr.albus.farmchallenge.service.FarmChallengeService;
import fr.albus.farmchallenge.service.FarmChallengeServiceProvider;
import fr.minuskube.inv.InventoryManager;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public final class FarmChallenge extends JavaPlugin {

    @Getter
    private FarmChallengeService farmChallengeService;
    @Getter
    private static InventoryManager inventoryManager;
    @Getter
    private GeneralConfigurationDAO generalConfigurationDAO;
    @Getter
    private GlobalDataConfigurationDAO globalDataConfigurationDAO;
    @Getter
    private PlayerDataConfigurationDAO playerDataConfigurationDAO;

    @SneakyThrows
    @Override
    public void onEnable() {
        this.setupConfig();
        this.setupService();

        inventoryManager = new InventoryManager(this);
        inventoryManager.init();

        new CommandManager(this);

        Bukkit.getLogger().log(Level.INFO, "[FarmChallenge]: Plugin enabled");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().log(Level.INFO, "[FarmChallenge]: Plugin disabled");
    }


    private void setupService() {
        generalConfigurationDAO = new GeneralConfigurationDAOProvider(this);
        globalDataConfigurationDAO = new GlobalDataConfigurationDAOProvider(this);
        playerDataConfigurationDAO = new PlayerDataConfigurationDAOProvider(this);

        this.farmChallengeService = new FarmChallengeServiceProvider(this, generalConfigurationDAO,
                globalDataConfigurationDAO, playerDataConfigurationDAO);
    }

    private void setupConfig() throws IOException {
        super.saveDefaultConfig();

        File dataFolder = new File(this.getDataFolder(), "data/");
        File globalData = new File(this.getDataFolder(), "data/global.yml");
        File playerDataFolder = new File(this.getDataFolder(), "data/playerdata/");

        if (!dataFolder.exists()) dataFolder.mkdirs();
        if (!globalData.exists()) globalData.createNewFile();
        if (!playerDataFolder.exists()) playerDataFolder.mkdirs();
    }
}
