package fr.albus.farmchallenge.dao;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GeneralConfigurationDAOProvider implements GeneralConfigurationDAO {

    private static final String MAIN_CONFIG_FILE = "config.yml";
    private static final String GENERAL_SECTION = "general";
    private static final String MENU_SECTION = "menus";

    private final Plugin plugin;

    public GeneralConfigurationDAOProvider(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setFarmBlock(Material material) {
        FileConfiguration config = this.getConfig();
        ConfigurationSection section = config.getConfigurationSection(GENERAL_SECTION);

        if(section == null) {
            ConfigurationSection generalSection = config.createSection(GENERAL_SECTION);
            generalSection.set("farm-block", material.toString());
        } else section.set("farm-block", material.toString());

        this.saveConfig(config);
    }

    @Override
    public Material getFarmBlock() {
        FileConfiguration config = this.getConfig();
        ConfigurationSection section = config.getConfigurationSection(GENERAL_SECTION);
        return Material.getMaterial(section.get("farm-block").toString());
    }

    @Override
    public ItemStack getFullProgressItemMenu() {
        FileConfiguration config = this.getConfig();
        ConfigurationSection section = config.getConfigurationSection(MENU_SECTION);
        return section.getConfigurationSection("progress").getItemStack("item");
    }

    @Override
    public ItemStack getStepItemMenu() {
        FileConfiguration config = this.getConfig();
        ConfigurationSection section = config.getConfigurationSection(MENU_SECTION);
        return section.getConfigurationSection("step").getItemStack("item");
    }

    private void saveConfig(FileConfiguration config) {
        Path path = this.getConfigPath();

        try { config.save(path.toFile());
        } catch (IOException e) { e.printStackTrace(); }
    }

    private FileConfiguration getConfig() {
        Path path = this.getConfigPath();

        if(!Files.exists(path)) this.plugin.saveResource(MAIN_CONFIG_FILE, false);

        return YamlConfiguration.loadConfiguration(path.toFile());
    }

    private Path getConfigPath() {
        return Paths.get(this.plugin.getDataFolder() + "/" + MAIN_CONFIG_FILE);
    }
}
