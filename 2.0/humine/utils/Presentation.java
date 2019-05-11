package humine.utils;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import humine.utils.cosmetiques.Cosmetique;
import humine.utils.cosmetiques.CustomHeadCosmetique;
import humine.utils.cosmetiques.temporary.TemporaryCustomHeadCosmetique;

public abstract class Presentation {

	private static String name = "Presentation";
	private static HashMap<Player, Cosmetique> cosmetiques = new HashMap<Player, Cosmetique>();
	
	/**
	 * Permet d'ouvrir la version presentation d'un cosmetique
	 * @param player
	 * @param cosmetique
	 */
	public static void openPresentation(Player player, Cosmetique cosmetique) {
		Inventory inv = Bukkit.createInventory(player, (9 * 5), name + " #" + cosmetique.getId());

		
		inv.setItem(11, ItemShop.blockHumisBuy(cosmetique, player));
		inv.setItem(13, itemApercu());
		inv.setItem(15, ItemShop.blockPixelBuy(cosmetique, player));
		
		inv.setItem(inv.getSize() - 9, ItemShop.itemQuit());
		inv.setItem(inv.getSize() - 5, ItemShop.itemCosmetiqueInfo(cosmetique));
		inv.setItem(inv.getSize() - 1, ItemShop.itemQuit());

		player.openInventory(inv);
		cosmetiques.put(player, cosmetique);
	}
	
	public static void openCustomHeadWindow(Player player, Cosmetique cosmetique) {
		Inventory inv = Bukkit.createInventory(player, (9 * 5), name + " #" + cosmetique.getId());
		
		if(!(cosmetique instanceof CustomHeadCosmetique) && !(cosmetique instanceof TemporaryCustomHeadCosmetique)) {
			player.openInventory(inv);
			return;
		}
		
		
		inv.setItem(11, ItemShop.itemPlus());
		inv.setItem(13, ItemShop.itemDamiStick());
		
		inv.setItem(15, ItemShop.itemMinus());
		inv.setItem(16, itemTakeAllBlocks());
		inv.setItem(17, itemTakeAllInventory());
		
		inv.setItem(inv.getSize() - 9, ItemShop.itemQuit());
		
		if(cosmetique instanceof CustomHeadCosmetique)
			inv.setItem(inv.getSize() - 5, ItemShop.itemCosmetiqueInfo(cosmetique, ((CustomHeadCosmetique) cosmetique).getAmount()));
		else
			inv.setItem(inv.getSize() - 5, ItemShop.itemCosmetiqueInfo(cosmetique, ((TemporaryCustomHeadCosmetique) cosmetique).getAmount()));
		
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
	
	public static String getName() {
		return name;
	}
	
	public static HashMap<Player, Cosmetique> getCosmetiques() {
		return cosmetiques;
	}
}
