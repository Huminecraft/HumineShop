package humine.events.menuaccueil;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import humine.main.MainShop;
import humine.utils.ItemShop;
import humine.utils.events.ClickItemMenuAccueilEvent;

/**
 * Package regroupant les evenements du menu d'accueil du plugin HumineShop
 * Classe d'evenement permettant d'ouvrir le Random Shop (cosmetiques temporaires)
 * 
 * @author miza
 */
public class ClickTemporaryShopButton implements Listener{

	@EventHandler
	public void onClick(ClickItemMenuAccueilEvent event) {
		if(event.getItem().isSimilar(ItemShop.itemTemporaryShop())) {
			if(!MainShop.getInstance().getRandomShop().isEmpty()) {
				event.getShopper().getRandomShop().update(MainShop.getInstance().getRandomShop());
				event.getShopper().getRandomShop().openShop();
			}
			else
				MainShop.sendMessage(event.getShopper().getPlayer(), "Boutique aleatoire vide :3");
		}
	}
}
