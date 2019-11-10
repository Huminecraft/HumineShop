package humine.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import humine.utils.events.ClickItemEvent;
import humine.utils.menus.MenuAccueil;
import humine.utils.menus.MenuIntermediaire;

/**
 * Package regroupant les evenements "principals" du plugin HumineShop
 * Classe d'evenement permettant d'annuler les deplacements des items
 * dans tout les shop de HumineShop
 * 
 * @author miza
 */
public class BlockMoveCosmetique implements Listener{

	@EventHandler
	public void onMove(ClickItemEvent event) {
		
		if(event.getView().getTitle().equals(MenuAccueil.ACCUEIL_NAME)) {
			event.setCancelled(true);
		}
		
		else if(event.getView().getTitle().equals(MenuIntermediaire.NAME_INTERMEDIAIRE)) {
			event.setCancelled(true);
		}
		
	}
}
