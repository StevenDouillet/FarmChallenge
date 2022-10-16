package fr.albus.farmchallenge.dao;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GlobalDataConfigurationDAOProvider implements GlobalDataConfigurationDAO {

    private static final String GLOBAL_DATA_CONFIG_FILE = "global.yml";
    private static final String GLOBAL_DATA_SECTION = "global-counter";

    private final Plugin plugin;

    public GlobalDataConfigurationDAOProvider(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void incrementGlobalProgress(int amount) {
        FileConfiguration config = this.getConfig();
        config.set(GLOBAL_DATA_SECTION, amount + getGlobalProgress());

        this.saveConfig(config);
    }

    @Override
    public int getGlobalProgress() {
        FileConfiguration config = this.getConfig();

        if (config.get(GLOBAL_DATA_SECTION) == null) return 0;

        return (int) config.get(GLOBAL_DATA_SECTION);
    }

    private void saveConfig(FileConfiguration config) {
        Path path = this.getConfigPath();

        try { config.save(path.toFile());
        } catch (IOException e) { e.printStackTrace(); }
    }

    private FileConfiguration getConfig() {
        Path path = this.getConfigPath();

        if(!Files.exists(path)) this.plugin.saveResource(GLOBAL_DATA_CONFIG_FILE, false);

        return YamlConfiguration.loadConfiguration(path.toFile());
    }

    private Path getConfigPath() {
        return Paths.get(this.plugin.getDataFolder() + "/data/" + GLOBAL_DATA_CONFIG_FILE);
    }
}
