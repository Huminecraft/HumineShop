package humine.events.menuintermediaire;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import humine.main.MainShop;
import humine.utils.Shopper;
import humine.utils.cosmetiques.TypeCosmetique;
import humine.utils.events.ClickItemMenuIntermediaireEvent;
import humine.utils.menus.MenuIntermediaire;

/**
 * Package regroupant les evenements du menu intermediaire du plugin HumineShop
 * Classe d'evenement permettant d'ouvrir le sous-shop cosmetique material hat
 * 
 * @author miza
 */
public class ClickHatShopButton implements Listener {

	@EventHandler
	public void onClick(ClickItemMenuIntermediaireEvent event) {
		if(event.getItem().isSimilar(MenuIntermediaire.itemHatShop())) {
			openHatShop(event.getShopper());
		}
	}
	
	private void openHatShop(Shopper shopper) {
		if(!MainShop.getInstance().getShop().isEmpty()) {
			shopper.getShop().update(MainShop.getInstance().getShop(), TypeCosmetique.MATERIAL_HAT);
			shopper.getShop().openShop();
		}
		else
			MainShop.sendMessage(shopper.getPlayer(), "Boutique vide :3");
	}
}
