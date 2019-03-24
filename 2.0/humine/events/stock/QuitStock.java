package humine.events.stock;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import humine.main.MainShop;
import humine.utils.Stock;

public class QuitStock implements Listener
{

	@EventHandler
	public void onQuit(InventoryCloseEvent event) {
		
		if (event.getInventory().getName().startsWith("Inventaire"))
		{
			
			Player player = (Player) event.getPlayer();
			Stock stock = MainShop.getInstance().getInventories().getStockOfPlayer(player.getName());
			
			if(stock != null)
				stock.getPlayersOnShop().remove(player);
		}
	}
	
	
}
