package humine.events.randomshop;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import humine.main.MainShop;

public class QuitRandomShop implements Listener
{

	@EventHandler
	public void onQuit(InventoryCloseEvent event) {
		
		if (event.getInventory().getName().equals(MainShop.getInstance().getRandomShop().getName()))
		{
			Player player = (Player) event.getPlayer();
			MainShop.getInstance().getShop().getPlayersOnShop().remove(player);
		}
	}
}
