package humine.events.menuaccueil;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import humine.utils.ItemShop;
import humine.utils.events.ClickItemMenuAccueilEvent;

/**
 * Package regroupant les evenements du menu d'accueil du plugin HumineShop
 * Classe d'evenement permettant d'envoyer le lien vers le site de humineCraft
 * 
 * @author miza
 */
public class ClickLinkButton implements Listener{

	@EventHandler
	public void onClick(ClickItemMenuAccueilEvent event) {
		if(event.getItem().isSimilar(ItemShop.itemLink())) {
			sendLink(event.getShopper().getPlayer());
			event.getShopper().getPlayer().closeInventory();
		}
	}
	
	private void sendLink(Player player) {
		//TODO envoyer lien cliquable au joueur
	}
}
