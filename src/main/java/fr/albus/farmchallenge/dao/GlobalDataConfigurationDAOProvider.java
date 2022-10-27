package fr.albus.farmchallenge.dao;

import fr.albus.farmchallenge.models.ChallengeStep;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GlobalDataConfigurationDAOProvider implements GlobalDataConfigurationDAO {

    private static final String GLOBAL_DATA_CONFIG_FILE = "global.yml";
    private static final String GLOBAL_DATA_SECTION = "global-counter";
    private static final String PERSONAL_STEPS_SECTION = "steps.personal";
    private static final String COMMUNITY_STEPS_SECTION = "steps.community";

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

    @Override
    public List<ChallengeStep> getPersonalChallengeSteps() {
        ConfigurationSection config = this.getConfig().getConfigurationSection(PERSONAL_STEPS_SECTION);

        if (config.getKeys(false).size() <= 0)
            return new ArrayList<ChallengeStep>();

        List<ChallengeStep> steps = new ArrayList<ChallengeStep>();

        config.getKeys(false).forEach(step -> {
            int farmBlockRequired = config.getInt(step + ".farm-block-required");
            Material materialDisplayed = Material.getMaterial(config.getString(step + ".material-displayed"));
            int customModelData = config.getInt(step + ".custom-model-data");
            steps.add(new ChallengeStep(Integer.parseInt(step), farmBlockRequired, materialDisplayed, customModelData));
        });

        return steps;
    }

    @Override
    public List<ChallengeStep> getCommunityChallengeSteps() {
        ConfigurationSection config = this.getConfig().getConfigurationSection(COMMUNITY_STEPS_SECTION);

        if (config.getKeys(false).size() <= 0)
            return new ArrayList<ChallengeStep>();

        List<ChallengeStep> steps = new ArrayList<ChallengeStep>();

        config.getKeys(false).forEach(step -> {
            int farmBlockRequired = config.getInt(step + ".farm-block-required");
            Material materialDisplayed = Material.getMaterial(config.getString(step + ".material-displayed"));
            int customModelData = config.getInt(step + ".custom-model-data");
            steps.add(new ChallengeStep(Integer.parseInt(step), farmBlockRequired, materialDisplayed, customModelData));
        });

        return steps;
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
