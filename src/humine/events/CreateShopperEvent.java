package humine.events;

import java.io.IOException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import humine.main.MainShop;
import humine.utils.Shopper;
import humine.utils.files.ShopLoader;

public class CreateShopperEvent implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		Shopper shopper = null;
		try {
			shopper = ShopLoader.loadShopper(player);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		if(shopper == null) {
			shopper = new Shopper(player);
			MainShop.getShopperManager().addShopper(shopper);
		}
	}
}
