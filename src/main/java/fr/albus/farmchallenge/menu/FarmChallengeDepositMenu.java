package fr.albus.farmchallenge.menu;

import fr.albus.farmchallenge.FarmChallenge;
import fr.albus.farmchallenge.dao.GeneralConfigurationDAO;
import fr.albus.farmchallenge.dao.GlobalDataConfigurationDAO;
import fr.albus.farmchallenge.dao.PlayerDataConfigurationDAO;
import fr.albus.farmchallenge.lang.MessagesFR;
import fr.albus.farmchallenge.models.ChallengeStep;
import fr.albus.farmchallenge.util.InventoryUtils;
import fr.albus.farmchallenge.util.MessageProvider;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import lombok.SneakyThrows;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class FarmChallengeDepositMenu implements InventoryProvider {

    private final GlobalDataConfigurationDAO globalDataConfigurationDAO;
    private final GeneralConfigurationDAO generalConfigurationDAO;
    private final PlayerDataConfigurationDAO playerDataConfigurationDAO;
    private final int itemPerPage = 4;

    public FarmChallengeDepositMenu(GlobalDataConfigurationDAO globalDataConfigurationDAO, GeneralConfigurationDAO generalConfigurationDAO, PlayerDataConfigurationDAO playerDataConfigurationDAO) {
        this.globalDataConfigurationDAO = globalDataConfigurationDAO;
        this.generalConfigurationDAO = generalConfigurationDAO;
        this.playerDataConfigurationDAO = playerDataConfigurationDAO;
    }

    public SmartInventory getInventory() {
        return SmartInventory.builder()
                .provider(new FarmChallengeDepositMenu(globalDataConfigurationDAO, generalConfigurationDAO, playerDataConfigurationDAO))
                .size(6, 9)
                .title(MessageProvider.parseColors(MessagesFR.DepositMenuTitle))
                .manager(FarmChallenge.getInventoryManager())
                .build();
    }

    @SneakyThrows
    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();
        pagination.setItems(new ClickableItem[globalDataConfigurationDAO.getCommunityChallengeSteps().size()]);
        pagination.setItemsPerPage(itemPerPage);
        this.setContents(contents, pagination.getPage(), player);
        this.setStaticButtons(contents, player);
    }

    @Override
    public void update(Player player, InventoryContents contents) {
    }

    private void setContents(InventoryContents contents, int page, Player player) throws IOException {
        List<ChallengeStep> communitySteps = this.globalDataConfigurationDAO.getCommunityChallengeSteps();
        List<ChallengeStep> personalSteps = this.globalDataConfigurationDAO.getPersonalChallengeSteps();
        int numberLoop = getNumberOfLoop(communitySteps.size());
        int stepLevelId = getInventoryStartedStep(communitySteps.size(), page + 1) - 1;

        for (int i = 0; i < numberLoop; i++) {

            boolean isAStep = false;

            if (i % 2 != 0) {
                isAStep = true;
                stepLevelId+= 1;
            }

            System.out.println("numberLoop:" + numberLoop);
            System.out.println("stepleft:" + stepsLeft(communitySteps.size(), page + 1, itemPerPage));
            System.out.println("page:" + page);
            System.out.println("communitySteps.size():" + communitySteps.size());
            if (stepsLeft(communitySteps.size(), page + 1, itemPerPage) || stepLevelId <= communitySteps.size()) {
                if (isAStep) {
                    setStepsItems(i, communitySteps.get(stepLevelId - 1), personalSteps.get(stepLevelId - 1), contents);
                } else {
                    setSProgressItems(i, contents,
                            this.globalDataConfigurationDAO.getGlobalProgress(),
                            getNextTopStep(communitySteps, stepLevelId).getFarmBlockRequired(),
                            this.playerDataConfigurationDAO.getPlayerProgress(player),
                            getNextTopStep(personalSteps, stepLevelId).getFarmBlockRequired());
                }
            }
        }
    }

    private ChallengeStep getNextTopStep(List<ChallengeStep> steps, int currentStepLevel) {
        if (steps.size() <= currentStepLevel) return steps.get(steps.size() - 1);
        return steps.get(currentStepLevel);
    }

    private void setStepsItems(int invPlacement, ChallengeStep communityStep, ChallengeStep personalStep, InventoryContents contents) {
        this.setItem(invPlacement, getStepItemStack(communityStep), getRewardsConsumer(), contents);
        this.setItem(invPlacement + 27, getStepItemStack(personalStep), getRewardsConsumer(), contents);
    }

    private void setSProgressItems(int invPlacement, InventoryContents contents, int globalProgression, int globalProgressionRequired,
                                   int playerProgression, int playerProgressionRequired) {
        boolean globalProgressionValidated = isValidated(globalProgression, globalProgressionRequired);
        boolean playerProgressionIsValidated = isValidated(playerProgression, playerProgressionRequired);

        this.setItem(invPlacement, getProgressItemStack(globalProgressionValidated), null, contents);
        this.setItem(invPlacement + 27, getProgressItemStack(playerProgressionIsValidated), null, contents);
    }

    public static int getInventoryStartedStep(int stepsSize, int page) {
        int itemPerPage = 4;
        if (stepsSize <= itemPerPage) return 1;
        return (itemPerPage * (page - 1)) + 1;
    }

    private int getNumberOfLoop(int steps) {
        if (steps >= 4) return 9;
        if (steps == 0) return 0;
        return steps * 2;
    }

    public static boolean stepsLeft(int steps, int page, int itemPerPage) {
        int stepsLeft = steps - (page * itemPerPage);
        return Math.abs(stepsLeft) > 0;
    }

    private boolean isValidated(int currentProgression, int stepRequirement) {
        return currentProgression >= stepRequirement;
    }

    private void setStaticButtons(InventoryContents contents, Player player) {
        SmartInventory inventory = contents.inventory();
        Pagination pagination = contents.pagination();

        contents.set(5, 1, ClickableItem.of(InventoryUtils.createItemStack(
                        Material.PAPER, 1, ChatColor.GRAY + "Page précédente", 42),
                e -> inventory.open(player, pagination.previous().getPage())));

        contents.set(5, 7, ClickableItem.of(InventoryUtils.createItemStack(
                        Material.PAPER, 1, ChatColor.GRAY + "Page suivante", 41),
                e -> inventory.open(player, pagination.next().getPage())));

        contents.set(5, 4, ClickableItem.of(InventoryUtils.createItemStack(
                        Material.PAPER, 1, ChatColor.RED + "Fermer le menu", 44),
                e -> inventory.close(player)));
    }

    private void setItem(int slot, ItemStack item, Consumer<InventoryClickEvent> consumer, InventoryContents contents) {

        int[] positions = this.getRowColumnArray(contents, slot);

        ClickableItem clickable = consumer != null ? ClickableItem.of(item, consumer) : ClickableItem.empty(item);

        contents.set(positions[0], positions[1], clickable);
    }

    private int[] getRowColumnArray(InventoryContents contents, int slot) {

        int row = slot / contents.inventory().getColumns();
        int column = slot % contents.inventory().getColumns();

        return new int[]{row, column};
    }

    private Consumer<InventoryClickEvent> getRewardsConsumer() {
        return event -> event.getWhoClicked().sendMessage("CC"); // TODO
    }

    private ItemStack getStepItemStack(ChallengeStep step) {
        ItemStack itemStack = generalConfigurationDAO.getStepItemMenu();
        itemStack.setType(step.getMaterialDisplayed());

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(itemMeta.getDisplayName().replace("{stepLevel}", Integer.toString(step.getLevel() + 1)));

        itemMeta.setLore(itemMeta.getLore().stream().map(lore -> lore.replace("{currentProgression}",
                String.format("%,d", globalDataConfigurationDAO.getGlobalProgress())).replace("{requirement}",
                String.format("%,d", step.getFarmBlockRequired()))).collect(Collectors.toList()));
        itemMeta.setCustomModelData(step.getCustomModelData());

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    private ItemStack getProgressItemStack(boolean isValidated) {
        ItemStack itemStack = generalConfigurationDAO.getFullProgressItemMenu();

        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setLore(itemMeta.getLore().stream().map(lore -> lore.replace("{currentProgression}",
                String.format("%,d", globalDataConfigurationDAO.getGlobalProgress()))).collect(Collectors.toList()));

        if (!isValidated) {
            itemMeta.setCustomModelData(25);
        }

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
