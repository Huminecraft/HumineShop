package humine.events.presentation;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import humine.utils.ItemShop;
import humine.utils.ParticleScheduler;
import humine.utils.Presentation;
import humine.utils.cosmetiques.AbstractParticleCosmetique;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.events.ClickItemPresentationEvent;

public class ClickDemoButton implements Listener{

	@EventHandler
	public void onClick(ClickItemPresentationEvent event) {
		if(event.getItem().isSimilar(ItemShop.itemDemo())) {
			Cosmetique c = Presentation.getCosmetiques().get(event.getShopper().getPlayer());
			
			if(c instanceof AbstractParticleCosmetique)
				ParticleScheduler.playDemoParticleCosmetique(event.getShopper().getPlayer(), (AbstractParticleCosmetique) c);
			
			event.getShopper().getPlayer().closeInventory();
		}
	}
}
