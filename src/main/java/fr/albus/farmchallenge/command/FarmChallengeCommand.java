package fr.albus.farmchallenge.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import fr.albus.farmchallenge.service.FarmChallengeService;
import org.bukkit.entity.Player;

import java.io.IOException;

@CommandAlias("fm|farmchallenge")
public class FarmChallengeCommand extends BaseCommand {

    private final FarmChallengeService service;

    public FarmChallengeCommand(FarmChallengeService service) {
        this.service = service;
    }

    @Default
    @CommandPermission("farmchallenge.use")
    @Description("Open the farm challenge main menu")
    public void onDefaultCommand(Player player) {
        // todo
    }

    @Subcommand("deposit")
    @CommandPermission("farmchallenge.deposit")
    @Description("Deposit farmable block menu")
    public void onDepositCommand(Player player) throws IOException {
        service.depositFarmBlock(player);
    }
}
