package humine.events.presentation;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import humine.utils.Presentation;
import humine.utils.Shopper;
import humine.utils.cosmetiques.AbstractCustomHatCosmetique;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.events.ClickItemPresentationEvent;

/**
 * Package regroupant les evenements du menu de presentation du plugin HumineShop
 * Classe d'evenement de recuperer tout les cosmetiques custom heads du joueur se
 * trouvant dans l'inventaire du joueur
 * 
 * @author miza
 */
public class ClickTakeAllInventoryButton implements Listener{

	@EventHandler
	public void onClick(ClickItemPresentationEvent event) {
		if(event.getItem().getItemMeta().getDisplayName().equals(Presentation.itemTakeAllInventory().getItemMeta().getDisplayName())) {
			takeAllInventory(event.getShopper());
		}
	}
	
	public void takeAllInventory(Shopper shopper) {
		Cosmetique c = Presentation.getCosmetiques().get(shopper.getPlayer());
		if(c == null && !(c instanceof AbstractCustomHatCosmetique))
			return;
		
		AbstractCustomHatCosmetique cosmetique = (AbstractCustomHatCosmetique) c;
		
		ClickTakeAllButton.takeItemInInventory(shopper, cosmetique);
	}
}
