package humine.utils.shop;

import java.util.ArrayList;
import java.util.List;


import humine.utils.cosmetiques.Cosmetique;
import humine.utils.cosmetiques.ParticleCosmetique;
import humine.utils.cosmetiques.temporary.TemporaryParticleCosmetique;

public class ParticleShop extends Shop{

	public ParticleShop(String name) {
		super(name);
	}
	
	public void filter(Shop shop) {
		this.resetShop();
		List<Cosmetique> cosmetiques = new ArrayList<Cosmetique>();

		for(Page page : shop.getPages()) {
			for(int i = 0; i < page.getCosmetiques().length; i++) {
				if(page.getCosmetiques()[i] != null) {
					if(page.getCosmetiques()[i] instanceof ParticleCosmetique || page.getCosmetiques()[i] instanceof TemporaryParticleCosmetique) {
						cosmetiques.add(page.getCosmetiques()[i]);
					}
				}
			}
		}
		
		for(Cosmetique c : cosmetiques) {
			this.addCosmetique(c);
		}
	}

}
