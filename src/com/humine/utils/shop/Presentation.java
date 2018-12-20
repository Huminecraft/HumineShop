package com.humine.utils.shop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.humine.main.ShopMain;

public abstract class Presentation
{

	private static Inventory inv;

	public static void openPresentation(Cosmetique cosmetique, Player player)
	{
		inv = Bukkit.createInventory(player, (9 * 3), ShopMain.getInstance().getShop().getName() + " " + cosmetique.getName());
		
		Cosmetique c = cosmetique;
		c.setBlockRepresentation(blockBuy(c.getPrice(), player));
		inv.setItem(14, c.getBlockRepresentation());
		
		c.setBlockRepresentation(blockTest());
		inv.setItem(12, c.getBlockRepresentation());
		
		inv.setItem(18, addArrow("Retour"));
		
		player.openInventory(inv);
	}

	private static ItemStack blockBuy(int price, Player player)
	{
		ItemStack item = new ItemStack(Material.GREEN_WOOL);
		ItemMeta meta = item.getItemMeta();

		List<String> lores = new ArrayList<String>();
		lores.add("Prix: " + ChatColor.GREEN + price);
		lores.add("Vous avez " + ShopMain.getInstance().getGemmeManager().getGemme(player).getGemme() + " gemme(s)");

		if (ShopMain.getInstance().getGemmeManager().getGemme(player).getGemme() >= price)
			meta.setDisplayName(ChatColor.BOLD + "" + ChatColor.GREEN + "Acheter !");
		else
			meta.setDisplayName(ChatColor.BOLD + "" + ChatColor.RED + "Vous n'avez pas assez de gemme(s) !");

		meta.setLore(lores);

		item.setItemMeta(meta);
		return item;

	}
	
	private static ItemStack blockTest()
	{
		ItemStack item = new ItemStack(Material.PURPLE_WOOL);
		ItemMeta meta = item.getItemMeta();


		meta.setDisplayName(ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + "Essayer le !");

		item.setItemMeta(meta);
		return item;

	}
	
	private static ItemStack addArrow(String name) {
		ItemStack item = new ItemStack(Material.ARROW);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.ITALIC + name);
		item.setItemMeta(meta);
		
		return item;
	}
}
