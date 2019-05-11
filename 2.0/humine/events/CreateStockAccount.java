package humine.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import humine.main.MainShop;
import humine.utils.shop.Stock;

/**
 * Package regroupant les evenements "principals" du plugin HumineShop
 * Classe d'evenement permettant de creer un stock au joueur
 * se connectant pour la 1er fois sur le serveur
 * 
 * @author miza
 */
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
