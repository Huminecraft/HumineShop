package humine.utils.shop;

import humine.utils.cosmetiques.ParticleCosmetique;
import humine.utils.cosmetiques.temporary.TemporaryParticleCosmetique;

/**
 * Package regroupant les outils de HumineShop <br />
 * Classe de sous-shop filtrant la classe mere {@link Shop} <br />
 * en ne montrant que les {@link ParticleCosmetique}
 * 
 * @author miza
 */
public class ParticleShop extends Shop{

	public ParticleShop(String name) {
		super(name);
	}
	
	public void filter(Shop shop) {
		this.resetShop();

		for(Page page : shop.getPages()) {
			for(int i = 0; i < page.getCosmetiques().length; i++) {
				if(page.getCosmetiques()[i] != null) {
					if(page.getCosmetiques()[i] instanceof ParticleCosmetique || page.getCosmetiques()[i] instanceof TemporaryParticleCosmetique) {
						this.addCosmetique(page.getCosmetiques()[i]);
					}
				}
			}
		}
	}

}
