package humine.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import humine.utils.cosmetiques.Cosmetique;

public abstract class Presentation {

	private static String name = "Presentation";
	
	/**
	 * Permet d'ouvrir la version presentation d'un cosmetique
	 * @param player
	 * @param cosmetique
	 */
	public static void openPresentation(Player player, Cosmetique cosmetique) {
		Inventory inv = Bukkit.createInventory(player, (9 * 5), name + " #" + cosmetique.getId());

		
		inv.setItem(11, ItemShop.blockHumisBuy(cosmetique, player));
		inv.setItem(13, ItemShop.itemLink());
		inv.setItem(15, ItemShop.blockPixelBuy(cosmetique, player));
		
		inv.setItem(inv.getSize() - 9, ItemShop.itemQuit());
		inv.setItem(inv.getSize() - 5, ItemShop.itemCosmetiqueInfo(cosmetique));
		inv.setItem(inv.getSize() - 1, ItemShop.itemQuit());

		player.openInventory(inv);
	}
	
	public static String getName() {
		return name;
	}
}
