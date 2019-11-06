package humine.events.menuintermediaire;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import humine.main.MainShop;
import humine.utils.ItemShop;
import humine.utils.events.ClickItemMenuIntermediaireEvent;

/**
 * Package regroupant les evenements du menu intermediaire du plugin HumineShop
 * Classe d'evenement permettant d'ouvrir le shop empereur
 * 
 * @author miza
 */
public class ClickEmpereurButton implements Listener{

	@EventHandler
	public void onClick(ClickItemMenuIntermediaireEvent event) {
		if(event.getItem().isSimilar(ItemShop.itemEmpereur())) {
			if(MainShop.getInstance().getEmperorShop().isEmpty())
				MainShop.sendMessage(event.getShopper().getPlayer(), "Emperor Shop vide :3");
			else {
				event.getShopper().getShop().update(MainShop.getInstance().getEmperorShop());
				event.getShopper().getShop().openShop();
			}
		}
	}
	
}
