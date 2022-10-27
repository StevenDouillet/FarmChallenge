package fr.albus.farmchallenge.models;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

public class ChallengeStep {

    @Getter
    @Setter
    private int level;
    @Getter
    @Setter
    private int farmBlockRequired;
    @Getter
    @Setter
    private Material materialDisplayed;
    @Getter
    @Setter
    private int customModelData;

    public ChallengeStep(int level, int farmBlockRequired, Material materialDisplayed, int customModelData) {
        this.level = level;
        this.farmBlockRequired = farmBlockRequired;
        this.materialDisplayed = materialDisplayed;
        this.customModelData = customModelData;
    }
}
