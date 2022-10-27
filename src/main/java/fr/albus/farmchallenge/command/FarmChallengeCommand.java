package fr.albus.farmchallenge.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import fr.albus.farmchallenge.dao.GeneralConfigurationDAO;
import fr.albus.farmchallenge.dao.GlobalDataConfigurationDAO;
import fr.albus.farmchallenge.dao.PlayerDataConfigurationDAO;
import fr.albus.farmchallenge.menu.FarmChallengeDepositMenu;
import fr.albus.farmchallenge.service.FarmChallengeService;
import org.bukkit.entity.Player;

import java.io.IOException;

@CommandAlias("fm|farmchallenge")
public class FarmChallengeCommand extends BaseCommand {

    private final FarmChallengeService service;
    private final GlobalDataConfigurationDAO globalDataConfigurationDAO;
    private final GeneralConfigurationDAO generalConfigurationDAO;
    private final PlayerDataConfigurationDAO playerDataConfigurationDAO;

    public FarmChallengeCommand(FarmChallengeService service, GlobalDataConfigurationDAO globalDataConfigurationDAO, GeneralConfigurationDAO generalConfigurationDAO, PlayerDataConfigurationDAO playerDataConfigurationDAO) {
        this.service = service;
        this.globalDataConfigurationDAO = globalDataConfigurationDAO;
        this.generalConfigurationDAO = generalConfigurationDAO;
        this.playerDataConfigurationDAO = playerDataConfigurationDAO;
    }

    @Default
    @CommandPermission("farmchallenge.use")
    @Description("Open the farm challenge main menu")
    public void onDefaultCommand(Player player) {
        FarmChallengeDepositMenu menu = new FarmChallengeDepositMenu(globalDataConfigurationDAO, generalConfigurationDAO,
                playerDataConfigurationDAO);
        menu.getInventory().open(player);
    }

    @Subcommand("deposit")
    @CommandPermission("farmchallenge.deposit")
    @Description("Deposit farmable block menu")
    public void onDepositCommand(Player player) throws IOException {
        service.depositFarmBlock(player);
    }
}
