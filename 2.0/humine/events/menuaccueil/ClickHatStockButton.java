package humine.events.menuaccueil;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;
import humine.utils.ItemShop;
import humine.utils.shop.HatStock;
import humine.utils.shop.Stock;

/**
 * Package regroupant les evenements du menu d'accueil du plugin HumineShop
 * Classe d'evenement permettant d'ouvrir le stock des cosmetiques material hat
 * 
 * @author miza
 */
public class ClickHatStockButton implements Listener{

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getInventory().getName().equals(MainShop.getInstance().getMenuAccueil().getName())) {
			if(event.getCurrentItem() != null) {
				if(event.getCurrentItem().isSimilar(ItemShop.itemHatStock())) {
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
			HatStock pStock = new HatStock(player.getName());
			pStock.filter(stock);
			
			MainShop.getInstance().getHatStockList().put(player.getName(), pStock);
			
			if(pStock.isEmpty())
				MainShop.sendMessage(player, "Hat Stock vide");
			else
				pStock.openShop(player);
		}
		
	}
}
