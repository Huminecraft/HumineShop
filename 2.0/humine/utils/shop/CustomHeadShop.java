package humine.utils.shop;

import humine.utils.cosmetiques.CustomHeadCosmetique;
import humine.utils.cosmetiques.temporary.TemporaryCustomHeadCosmetique;

/**
 * Package regroupant les outils de HumineShop <br />
 * Classe de sous-shop filtrant la classe mere {@link Shop} <br />
 * en ne montrant que les {@link CustomHeadCosmetique}
 * 
 * @author miza
 */
public class CustomHeadShop extends Shop{

	public CustomHeadShop(String name) {
		super(name);
	}
	
	public void filter(Shop shop) {
		this.resetShop();

		for(Page page : shop.getPages()) {
			for(int i = 0; i < page.getCosmetiques().length; i++) {
				if(page.getCosmetiques()[i] != null) {
					if(page.getCosmetiques()[i] instanceof CustomHeadCosmetique || page.getCosmetiques()[i] instanceof TemporaryCustomHeadCosmetique) {
						this.addCosmetique(page.getCosmetiques()[i]);
					}
				}
			}
		}
	}

}
