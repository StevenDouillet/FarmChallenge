package fr.albus.farmchallenge.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class InventoryUtils {

    public static ItemStack createItemStack(Material material, int amount, String displayName, ArrayList<String> lores) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lores);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createItemStack(Material material, int amount, String displayName,
                                            ArrayList<String> lores, int customModelData) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lores);
        itemMeta.setCustomModelData(customModelData);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createItemStack(Material material, int amount, String displayName, int customModelData) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setCustomModelData(customModelData);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createItemStack(Material material, int amount, String displayName) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
