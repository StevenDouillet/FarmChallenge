package fr.albus.farmchallenge.menu;

import fr.albus.farmchallenge.FarmChallenge;
import fr.albus.farmchallenge.lang.MessagesFR;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.entity.Player;

public class FarmChallengeDepositMenu implements InventoryProvider {

    public FarmChallengeDepositMenu(Player player) {
    }

    public static SmartInventory getInventory(Player player) {
        return SmartInventory.builder()
                .provider(new FarmChallengeDepositMenu(player))
                .size(6, 9)
                .title(MessagesFR.DepositMenuTitle)
                .manager(FarmChallenge.getInventoryManager())
                .build();
    }

    @Override
    public void init(Player player, InventoryContents contents) {
    }

    @Override
    public void update(Player player, InventoryContents contents) {
        player.sendMessage("test"); // to remove
    }
}
