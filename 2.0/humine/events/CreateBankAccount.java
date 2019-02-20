package humine.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import humine.main.MainShop;

public class CreateBankAccount implements Listener{

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		if(!MainShop.getInstance().getBank().containsPlayer(player)) {
			MainShop.getInstance().getBank().addPlayer(player);
		}
	}
}
