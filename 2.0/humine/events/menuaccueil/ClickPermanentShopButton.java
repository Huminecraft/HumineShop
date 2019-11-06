package humine.events.menuaccueil;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import humine.main.MainShop;
import humine.utils.ItemShop;
import humine.utils.events.ClickItemMenuAccueilEvent;

/**
 * Package regroupant les evenements du menu d'accueil du plugin HumineShop
 * Classe d'evenement permettant d'ouvrir le menu intermediaire du shop permanent
 * 
 * @author miza
 */
public class ClickPermanentShopButton implements Listener{

	@EventHandler
	public void onClick(ClickItemMenuAccueilEvent event) {
		if(event.getItem().isSimilar(ItemShop.itemPermanentShop())) {
			MainShop.getInstance().getMenuIntermediaire().openMenu(event.getShopper().getPlayer());
		}
	}
}
