package humine.utils;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import humine.utils.cosmetiques.AbstractCustomHatCosmetique;
import humine.utils.cosmetiques.Cosmetique;

public abstract class Presentation {

	public static final String PRESENTATION_NAME = "Presentation";
	private static HashMap<Player, Cosmetique> cosmetiques = new HashMap<Player, Cosmetique>();
	
	/**
	 * Permet d'ouvrir la version presentation d'un cosmetique
	 * @param player
	 * @param cosmetique
	 */
	public static void openPresentation(Player player, Cosmetique cosmetique) {
		Inventory inv = Bukkit.createInventory(player, (9 * 5), PRESENTATION_NAME + " #" + cosmetique.getId());

		
		inv.setItem(10, ItemShop.blockHumisBuy(cosmetique, player));
		inv.setItem(11, ItemShop.blockPixelBuy(cosmetique, player));
		
		inv.setItem(13, itemApercu());
		
		inv.setItem(15, ItemShop.itemDemo());
		
		inv.setItem(inv.getSize() - 9, ItemShop.itemQuit());
		inv.setItem(inv.getSize() - 5, ItemShop.itemCosmetiqueInfo(cosmetique));
		inv.setItem(inv.getSize() - 1, ItemShop.itemQuit());

		player.openInventory(inv);
		cosmetiques.put(player, cosmetique);
	}
	
	public static void openCustomHeadWindow(Player player, AbstractCustomHatCosmetique cosmetique) {
		Inventory inv = Bukkit.createInventory(player, (9 * 5), PRESENTATION_NAME + " #" + cosmetique.getId());
		
		inv.setItem(11, ItemShop.itemPlus());
		
		inv.setItem(14, ItemShop.itemMinus());
		inv.setItem(15, itemTakeAllBlocks());
		inv.setItem(16, itemTakeAllInventory());
		
		inv.setItem(inv.getSize() - 9, ItemShop.itemQuit());
		inv.setItem(inv.getSize() - 5, ItemShop.itemCosmetiqueInfo(cosmetique, cosmetique.getAmount()));
		inv.setItem(inv.getSize() - 1, ItemShop.itemQuit());
		
		player.openInventory(inv);
		cosmetiques.put(player, cosmetique);
	}
	
	public static ItemStack itemApercu() {
		ItemStack item = ItemShop.itemLink();
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Apercu");
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack itemTakeAllInventory() {
		ItemStack item = ItemShop.itemMinus();
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Tout recuperer dans l'inventaire");
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack itemTakeAllBlocks() {
		ItemStack item = ItemShop.itemMinus();
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Recuperer tout les blocs exterieurs");
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static void closePresentation(Player player) {
		player.closeInventory();
		cosmetiques.remove(player);
	}
	
	public static HashMap<Player, Cosmetique> getCosmetiques() {
		return cosmetiques;
	}
}
