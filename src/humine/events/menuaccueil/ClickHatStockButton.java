package humine.events.menuaccueil;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import humine.main.MainShop;
import humine.utils.ItemShop;
import humine.utils.Shopper;
import humine.utils.cosmetiques.TypeCosmetique;
import humine.utils.events.ClickItemMenuAccueilEvent;

/**
 * Package regroupant les evenements du menu d'accueil du plugin HumineShop
 * Classe d'evenement permettant d'ouvrir le stock des cosmetiques material hat
 * 
 * @author miza
 */
public class ClickHatStockButton implements Listener{

	@EventHandler
	public void onClick(ClickItemMenuAccueilEvent event) {
		if(event.getItem().isSimilar(ItemShop.itemHatStock())) {
			openStock(event.getShopper());
		}
	}
	
	private void openStock(Shopper shopper)
	{
		shopper.getStock().setCosmetique(shopper.getCosmetiques(TypeCosmetique.MATERIAL_HAT));
		if(shopper.getStock().isEmpty())
			MainShop.sendMessage(shopper.getPlayer(), "Hat Material Stock vide");
		else
			shopper.getStock().openShop();
	}
}
