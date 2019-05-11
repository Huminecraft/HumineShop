package humine.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import humine.utils.ParticleScheduler;

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
		ParticleScheduler.disableParticleCosmetique(event.getPlayer());
	}
}
