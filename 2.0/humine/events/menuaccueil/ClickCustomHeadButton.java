package humine.events.menuaccueil;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;
import humine.utils.ItemShop;
import humine.utils.shop.CustomHeadStock;
import humine.utils.shop.Stock;

/**
 * Package regroupant les evenements du menu d'accueil du plugin HumineShop
 * Classe d'evenement permettant d'ouvrir le stock des cosmetiques custom head
 * 
 * @author miza
 */
public class ClickCustomHeadButton implements Listener{

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getInventory().getName().equals(MainShop.getInstance().getMenuAccueil().getName())) {
			if(event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
				if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ItemShop.itemCustomHeadStock().getItemMeta().getDisplayName())) {
					Player player = (Player) event.getWhoClicked();
					openStock(player);
				}
			}
		}
	}
	
	private void openStock(Player player)
	{
		Stock stock = MainShop.getInstance().getInventories().getStockOfPlayer(player.getName());
		
		if(stock != null) {
			CustomHeadStock pStock = new CustomHeadStock(player.getName());
			pStock.filter(stock);
			
			MainShop.getInstance().getCustomHeadStockList().put(player.getName(), pStock);
			
			if(pStock.isEmpty())
				MainShop.sendMessage(player, "Custom Head Stock vide");
			else
				pStock.openShop(player);
		}
		
	}
}
