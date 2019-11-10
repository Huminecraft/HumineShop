package humine.events.presentation;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import humine.utils.Presentation;
import humine.utils.events.ClickItemPresentationEvent;

/**
 * Package regroupant les evenements du menu de presentation du plugin HumineShop
 * Classe d'evenement permettant d'acceder au site de HumineCraft (avec une ancre)
 * 
 * @author miza
 */
public class ClickLinkButton implements Listener{

	@EventHandler
	public void onClick(ClickItemPresentationEvent event) {
		if(event.getItem().isSimilar(Presentation.itemApercu())) {
			sendLink(event.getShopper().getPlayer());
			event.getShopper().getPlayer().closeInventory();
		}
	}
	
	private void sendLink(Player player) {
		//TODO envoyer lien cliquable au joueur
	}
}
