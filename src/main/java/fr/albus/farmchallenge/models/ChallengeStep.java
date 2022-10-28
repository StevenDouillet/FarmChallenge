package fr.albus.farmchallenge.models;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ChallengeStep {

    @Getter
    @Setter
    private int level;
    @Getter
    @Setter
    private int farmBlockRequired;
    @Getter
    @Setter
    private ItemStack displayedItem;
    @Getter
    @Setter
    private List<String> actions;

    public ChallengeStep(int level, int farmBlockRequired, ItemStack displayedItem, List<String> actions) {
        this.level = level;
        this.farmBlockRequired = farmBlockRequired;
        this.displayedItem = displayedItem;
        this.actions = actions;
    }
}
