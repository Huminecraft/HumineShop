package humine.events;

import java.io.IOException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import humine.main.MainShop;
import humine.utils.ParticleScheduler;
import humine.utils.Shopper;
import humine.utils.files.ShopSaver;

/**
 * Package regroupant les evenements "principals" du plugin HumineShop
 * Classe d'evenement permettant de desactiver un cosmetique particule
 * si le joueur se deconnecte avec celui-ci
 * 
 * @author miza
 */
public class PlayerQuit implements Listener
{

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		ParticleScheduler.disableParticleCosmetique(player);
		Shopper shopper = MainShop.getShopperManager().getShopper(player);
		if(shopper != null) {
			try {
				ShopSaver.saveShopper(shopper);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
