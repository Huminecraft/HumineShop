package humine.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import humine.main.MainShop;

/**
 * Package regroupant les evenements "principals" du plugin HumineShop
 * Classe d'evenement permettant de creer un compte bancaire au joueur
 * se connectant pour la 1er fois sur le serveur
 * 
 * @author miza
 */
public class CreateBankAccount implements Listener{

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		if(!MainShop.getInstance().getBankHumis().containsPlayer(player)) {
			MainShop.getInstance().getBankHumis().addPlayer(player);
		}
		
		if(!MainShop.getInstance().getBankPixel().containsPlayer(player)) {
			MainShop.getInstance().getBankPixel().addPlayer(player);
		}
	}
}
