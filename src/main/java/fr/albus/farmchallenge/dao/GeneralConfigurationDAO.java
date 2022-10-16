package fr.albus.farmchallenge.dao;

import org.bukkit.Material;

public interface GeneralConfigurationDAO {

    void setFarmBlock(Material material);

    Material getFarmBlock();
}
