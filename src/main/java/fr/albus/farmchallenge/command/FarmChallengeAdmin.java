package fr.albus.farmchallenge.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import fr.albus.farmchallenge.service.FarmChallengeService;
import org.bukkit.entity.Player;

import java.io.IOException;

@CommandAlias("fm|farmchallenge")
public class FarmChallengeAdmin extends BaseCommand {

    private final FarmChallengeService service;

    public FarmChallengeAdmin(FarmChallengeService service) {
        this.service = service;
    }

    @Subcommand("admin setFarmBlock")
    @CommandPermission("farmchallenge.admin.set")
    @Description("Set block material for the farm challenge")
    public void onSetFarmBlockCommand(Player player, String material) {
        this.service.setFarmBlock(player, material);
    }

    @Subcommand("admin getFarmBlock")
    @CommandPermission("farmchallenge.admin.get")
    @Description("Get block material for the farm challenge")
    public void onGetFarmBlockCommand(Player player) {
        this.service.getFarmBlock(player);
    }

    @Subcommand("admin getGlobalProgress")
    @CommandPermission("farmchallenge.admin.get")
    @Description("Get challenge global progress")
    public void onGetGlobalProgressCommand(Player player) {
        this.service.getGlobalProgress(player);
    }

    @Subcommand("admin getPlayerProgress")
    @CommandPermission("farmchallenge.admin.get")
    @Description("Get challenge player progress")
    public void onGetGlobalProgressCommand(Player player, String targetPlayer) throws IOException {
        this.service.getPlayerProgress(player, targetPlayer);
    }
}
