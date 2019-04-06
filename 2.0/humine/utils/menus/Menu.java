package humine.utils.menus;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface Menu {

	public void openMenu(Player player);
	
	public Inventory initInventory(Player player);
	
	public String getName();
}
