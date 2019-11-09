package humine.events.presentation;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import humine.main.MainShop;
import humine.utils.ItemShop;
import humine.utils.Presentation;
import humine.utils.Shopper;
import humine.utils.cosmetiques.AbstractCustomHatCosmetique;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.events.ClickItemPresentationEvent;

/**
 * Package regroupant les evenements du menu de presentation du plugin HumineShop
 * Classe d'evenement permettant ajouter une cosmetique custom head dans
 * l'inventaire du joueur si il lui reste des custom heads en stock
 * 
 * @author miza
 */
public class ClickPlusButton implements Listener{

	
	@EventHandler
	public void onClick(ClickItemPresentationEvent event) {
		if(event.getItem().getItemMeta().getDisplayName().equals(ItemShop.itemPlus().getItemMeta().getDisplayName())) {
			giveToPlayer(event.getShopper());
		}
	}

	private void giveToPlayer(Shopper shopper) {
		Cosmetique c = Presentation.getCosmetiques().get(shopper.getPlayer());
		if(c == null || !(c instanceof AbstractCustomHatCosmetique))
			return;
		
		AbstractCustomHatCosmetique cosmetique = (AbstractCustomHatCosmetique) c;
		
		if(cosmetique.getCustomHatData().getInStock() > 0) {
			shopper.getPlayer().getInventory().addItem(cosmetique.convertToItem());
			cosmetique.getCustomHatData().setInStock((cosmetique.getCustomHatData().getInStock() - 1));
			cosmetique.getCustomHatData().setInInventory((cosmetique.getCustomHatData().getInInventory() + 1));
		}
		else
			MainShop.sendMessage(shopper.getPlayer(), "Vous ne pouvez pas prendre plus de tete");
	}
}
