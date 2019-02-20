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

public abstract class Presentation {

	private static Inventory inv;

	public static void openPresentation(Cosmetique cosmetique, Player player) {
		inv = Bukkit.createInventory(player, (9 * 3), "Présentation");

		inv.setItem(14, blockBuy(cosmetique, player));

		inv.setItem(12, blockTest(cosmetique));

		inv.setItem(18, addArrow("Retour"));

		player.openInventory(inv);
	}

	private static ItemStack blockBuy(Cosmetique cosmetique, Player player) {
		ItemStack item = new ItemStack(Material.GREEN_WOOL);
		ItemMeta meta = item.getItemMeta();

		List<String> lores = new ArrayList<String>();

		if (ShopMain.getInstance().getGemmeManager().getGemme(player).getGemme() >= cosmetique.getPrice())
			lores.add(ChatColor.BOLD + "" + ChatColor.GREEN + "Acheter !");
		else
			lores.add(ChatColor.BOLD + "" + ChatColor.RED + "Vous n'avez pas assez de gemme(s) !");

		lores.add("Prix: " + ChatColor.GREEN + cosmetique.getPrice());
		lores.add("Vous avez " + ShopMain.getInstance().getGemmeManager().getGemme(player).getGemme() + " gemme(s)");
		lores.add("Buy");

		meta.setDisplayName(cosmetique.getName());

		meta.setLore(lores);

		item.setItemMeta(meta);
		return item;

	}

	private static ItemStack blockTest(Cosmetique cosmetique) {
		ItemStack item = new ItemStack(Material.PURPLE_WOOL);
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lores = new ArrayList<String>();

		meta.setDisplayName(cosmetique.getName());

		lores.add(ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + "Essayer le !");
		lores.add("Try");
		meta.setLore(lores);

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
