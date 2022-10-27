package fr.albus.farmchallenge.manager;

import co.aikar.commands.PaperCommandManager;
import fr.albus.farmchallenge.FarmChallenge;
import fr.albus.farmchallenge.command.FarmChallengeAdmin;
import fr.albus.farmchallenge.command.FarmChallengeCommand;

import java.util.Locale;

public class CommandManager {

    public CommandManager(FarmChallenge plugin) {
        PaperCommandManager commandManager = new PaperCommandManager(plugin);
        commandManager.enableUnstableAPI("help");
        commandManager.addSupportedLanguage(Locale.FRANCE);
        commandManager.getLocales().setDefaultLocale(Locale.FRANCE);
        commandManager.registerCommand(new FarmChallengeCommand(plugin.getFarmChallengeService(),
                plugin.getGlobalDataConfigurationDAO(), plugin.getGeneralConfigurationDAO(), plugin.getPlayerDataConfigurationDAO()));
        commandManager.registerCommand(new FarmChallengeAdmin(plugin.getFarmChallengeService()));
    }
}
