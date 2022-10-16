package fr.albus.farmchallenge.service;

import fr.albus.farmchallenge.dao.GeneralConfigurationDAO;
import fr.albus.farmchallenge.dao.GlobalDataConfigurationDAO;
import fr.albus.farmchallenge.dao.PlayerDataConfigurationDAO;
import fr.albus.farmchallenge.lang.MessagesFR;
import fr.albus.farmchallenge.util.MessageProvider;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FarmChallengeServiceProvider implements FarmChallengeService {

    private final Plugin plugin;
    private final GeneralConfigurationDAO generalConfigurationDAO;
    private final GlobalDataConfigurationDAO globalDataConfigurationDAO;
    private final PlayerDataConfigurationDAO playerDataConfigurationDAO;


    public FarmChallengeServiceProvider(Plugin plugin, GeneralConfigurationDAO generalConfigurationDAO,
                                        GlobalDataConfigurationDAO globalDataConfigurationDAO,
                                        PlayerDataConfigurationDAO playerDataConfigurationDAO) {
        this.plugin = plugin;
        this.generalConfigurationDAO = generalConfigurationDAO;
        this.globalDataConfigurationDAO = globalDataConfigurationDAO;
        this.playerDataConfigurationDAO = playerDataConfigurationDAO;
    }

    @Override
    public void setFarmBlock(Player player, String material) {
        if (Material.getMaterial(material) != null) {
            this.generalConfigurationDAO.setFarmBlock(Material.getMaterial(material));
            MessageProvider.sendPlayerMessage(player, MessagesFR.AdminMaterialUpdated);
            return;
        };
        MessageProvider.sendPlayerMessage(player, MessagesFR.AdminWrongMaterial);
    }

    @Override
    public void getFarmBlock(Player player) {
        MessageProvider.sendPlayerMessage(player, MessagesFR.AdminGetFarmBlockMaterial
                .replace("{farmBlock}", this.generalConfigurationDAO.getFarmBlock().toString()));
    }

    @Override
    public void depositFarmBlock(Player player) throws IOException {
        List<ItemStack> playerBlocks = Arrays.stream(player.getInventory().getContents()).filter(Objects::nonNull)
                .filter(item -> item.getType() == generalConfigurationDAO.getFarmBlock()).collect(Collectors.toList());

        int playerBlockAmount = playerBlocks.stream().mapToInt(ItemStack::getAmount).sum();

        player.getInventory().removeItem(new ItemStack(generalConfigurationDAO.getFarmBlock(), playerBlockAmount));

        globalDataConfigurationDAO.incrementGlobalProgress(playerBlockAmount);
        playerDataConfigurationDAO.incrementPlayerProgress(player, playerBlockAmount);

        MessageProvider.sendPlayerMessage(player,
                MessagesFR.DepositSuccessful.replace("{depositAmount}", Integer.toString(playerBlockAmount)));
    }

    @Override
    public void getGlobalProgress(Player player) {
        MessageProvider.sendPlayerMessage(player, MessagesFR.AdminGetGlobalProgress.replace("{globalProgress}",
                Integer.toString(globalDataConfigurationDAO.getGlobalProgress())));
    }

    @Override
    public void getPlayerProgress(Player player, String targetPlayer) throws IOException {
        MessageProvider.sendPlayerMessage(player,
                MessagesFR.AdminGetPlayerProgress.replace("{player}", targetPlayer).replace("{playerProgress}",
                        Integer.toString(playerDataConfigurationDAO.getPlayerProgress(
                                Bukkit.getOfflinePlayer(targetPlayer).getPlayer()))));
    }
}
