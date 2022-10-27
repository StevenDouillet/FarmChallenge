package fr.albus.farmchallenge.dao;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface GeneralConfigurationDAO {

    void setFarmBlock(Material material);

    Material getFarmBlock();

    ItemStack getFullProgressItemMenu();

    ItemStack getStepItemMenu();
}
