package humine.utils.menus;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Package regroupant les outils de HumineShop
 * Interface pour la creation de menu
 * 
 * @author miza
 */
public interface Menu {

	public void openMenu(Player player);
	
	public Inventory initInventory(Player player);
}
