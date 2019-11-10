package humine.utils.menus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import humine.utils.ItemShop;

/**
 * Package regroupant les outils de HumineShop
 * Classe representant le menu d'accueil de HumineShop
 * 
 * @author miza
 */
public abstract class MenuAccueil{

	public static final String ACCUEIL_NAME = "Menu accueil";
	
	public static void openMenu(Player player) {
		player.openInventory(initInventory(player));
	}

	public static Inventory initInventory(Player player) {
		Inventory inv = Bukkit.createInventory(null, (9 * 4), ACCUEIL_NAME);

		inv.setItem(11, ItemShop.itemPermanentShop());
		inv.setItem(13, ItemShop.itemLink());
		inv.setItem(15, ItemShop.itemTemporaryShop());
		
		inv.setItem(inv.getSize() - 9, ItemShop.itemQuit());
		inv.setItem(inv.getSize() - 1, ItemShop.itemQuit());
		
		inv.setItem(inv.getSize() - 6, ItemShop.itemParticleStock());
		inv.setItem(inv.getSize() - 5, ItemShop.itemHatStock());
		inv.setItem(inv.getSize() - 4, ItemShop.itemCustomHeadStock());
		
		return inv;
	}
}
