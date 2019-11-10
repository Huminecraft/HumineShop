package humine.events.menuintermediaire;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import humine.utils.ItemShop;
import humine.utils.events.ClickItemMenuIntermediaireEvent;
import humine.utils.menus.MenuAccueil;

/**
 * Package regroupant les evenements du menu intermediaire du plugin HumineShop
 * Classe d'evenement permettant de retourner au menu d'accueil
 * 
 * @author miza
 */
public class ClickQuitButton implements Listener{

	@EventHandler
	public void onClick(ClickItemMenuIntermediaireEvent event) {
		if(event.getItem().isSimilar(ItemShop.itemQuit())) {
			MenuAccueil.openMenu(event.getShopper().getPlayer());
		}
	}
}
