package humine.events.stocks;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import humine.main.MainShop;
import humine.main.ShopUtils;
import humine.utils.ParticleScheduler;
import humine.utils.Presentation;
import humine.utils.Shopper;
import humine.utils.cosmetiques.AbstractCustomHatCosmetique;
import humine.utils.cosmetiques.AbstractMaterialHatCosmetique;
import humine.utils.cosmetiques.AbstractParticleCosmetique;
import humine.utils.events.ClickCosmetiqueStockEvent;

/**
 * Package regroupant les evenements des stocks du plugin HumineShop
 * Classe d'evenement permettant de faire une action une fois cliquer
 * sur un cosmetique dans le stock (les actions sont differentes selon les
 * sous-stock particle, material hat ou custom head)
 * 
 * @author miza
 */
public class ClickCosmetiqueButton implements Listener
{

	@EventHandler
	public void onClick(ClickCosmetiqueStockEvent event) {
		if(event.getCosmetique() instanceof AbstractParticleCosmetique) {
			clickParticleCosmetique(event.getShopper(), (AbstractParticleCosmetique) event.getCosmetique());
		}
		else if(event.getCosmetique() instanceof AbstractMaterialHatCosmetique) {
			clickHatCosmetique(event.getShopper(), (AbstractMaterialHatCosmetique) event.getCosmetique());
		}
		else if(event.getCosmetique() instanceof AbstractCustomHatCosmetique) {
			clickCustomHatCosmetique(event.getShopper(), (AbstractCustomHatCosmetique) event.getCosmetique());
		}
	}
	
	public void clickCustomHatCosmetique(Shopper shopper, AbstractCustomHatCosmetique cosmetique) {
		Presentation.openCustomHeadWindow(shopper.getPlayer(), cosmetique);
	}

	public void clickParticleCosmetique(Shopper shopper, AbstractParticleCosmetique cosmetique) {
		ParticleScheduler.playParticleCosmetique(shopper.getPlayer(), cosmetique);
		shopper.getPlayer().closeInventory();
	}
	
	public void clickHatCosmetique(Shopper shopper, AbstractMaterialHatCosmetique cosmetique) {
		if(shopper.getPlayer().getInventory().getHelmet() != null && shopper.getPlayer().getInventory().getHelmet().getType() != Material.AIR) {
			if(!ShopUtils.isCosmetique(shopper, shopper.getPlayer().getInventory().getHelmet()))
				MainShop.sendMessage(shopper.getPlayer(), "Veuillez retirer votre casque d'abord");
			else
				shopper.getPlayer().getInventory().setHelmet(cosmetiqueToItem(cosmetique));
		}
		else
			shopper.getPlayer().getInventory().setHelmet(cosmetiqueToItem(cosmetique));
		
		shopper.getPlayer().closeInventory();
	}
	
	private ItemStack cosmetiqueToItem(AbstractMaterialHatCosmetique cosmetique) {
		ItemStack item = cosmetique.convertToItem();
		item.setType(cosmetique.getMaterialHat());
		return item;
	}
}
