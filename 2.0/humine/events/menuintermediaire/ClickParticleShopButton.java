package humine.events.menuintermediaire;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import humine.main.MainShop;
import humine.utils.Shopper;
import humine.utils.cosmetiques.TypeCosmetique;
import humine.utils.events.ClickItemMenuIntermediaireEvent;

/**
 * Package regroupant les evenements du menu intermediaire du plugin HumineShop
 * Classe d'evenement permettant d'ouvrir le sous-shop cosmetique particle
 * 
 * @author miza
 */
public class ClickParticleShopButton implements Listener{

	
	@EventHandler
	public void onClick(ClickItemMenuIntermediaireEvent event) {
		if(event.getItem().isSimilar(MainShop.getInstance().getMenuIntermediaire().itemParticleShop())) {
			openParticleShop(event.getShopper());
		}
	}

	private void openParticleShop(Shopper shopper) {
		if(!MainShop.getInstance().getShop().isEmpty()) {
			shopper.getShop().update(MainShop.getInstance().getShop(), TypeCosmetique.PARTICLE);
			shopper.getShop().openShop();
		}
		else
			MainShop.sendMessage(shopper.getPlayer(), "Boutique vide :3");
	}
}
