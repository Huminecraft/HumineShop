package humine.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import humine.main.MainShop;
import humine.utils.shop.Stock;

public class CreateStockAccount implements Listener{

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		if(!MainShop.getInstance().getInventories().containsStockOfPlayer(player.getName())) {
			Stock stock = new Stock(player.getName());
			MainShop.getInstance().getInventories().addStock(stock);
		}
	}
}
