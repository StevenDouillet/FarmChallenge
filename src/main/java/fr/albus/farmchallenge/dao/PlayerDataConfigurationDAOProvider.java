package fr.albus.farmchallenge.dao;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PlayerDataConfigurationDAOProvider implements PlayerDataConfigurationDAO {

    private static final String PLAYER_DATA_SECTION = "player-counter";

    private final Plugin plugin;

    public PlayerDataConfigurationDAOProvider(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void incrementPlayerProgress(Player player, int amount) throws IOException {
        FileConfiguration config = this.getConfig(player);
        config.set(PLAYER_DATA_SECTION, amount + getPlayerProgress(player));

        this.saveConfig(config, player);
    }

    @Override
    public int getPlayerProgress(Player player) throws IOException {
        FileConfiguration config = this.getConfig(player);

        if (config.get(PLAYER_DATA_SECTION) == null) return 0;

        return (int) config.get(PLAYER_DATA_SECTION);
    }

    private void saveConfig(FileConfiguration config, Player player) {
        Path path = this.getConfigPath(player);

        try { config.save(path.toFile());
        } catch (IOException e) { e.printStackTrace(); }
    }

    private FileConfiguration getConfig(Player player) throws IOException {
        Path path = this.getConfigPath(player);
        File file = new File(this.plugin.getDataFolder() + "/data/playerdata/", player.getUniqueId() + ".yml");

        if (!file.exists()) file.createNewFile();

        return YamlConfiguration.loadConfiguration(path.toFile());
    }

    private Path getConfigPath(Player player) {
        return Paths.get(this.plugin.getDataFolder() + "/data/playerdata/"
                + player.getUniqueId() + ".yml");
    }
}
