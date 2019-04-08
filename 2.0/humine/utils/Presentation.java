package humine.utils;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import humine.utils.cosmetiques.Cosmetique;

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
	
	public static ItemStack itemApercu() {
		ItemStack item = ItemShop.itemLink();
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Apercu");
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
