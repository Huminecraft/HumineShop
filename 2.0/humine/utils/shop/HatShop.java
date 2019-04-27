package humine.utils.shop;

import humine.utils.cosmetiques.MaterialHatCosmetique;
import humine.utils.cosmetiques.temporary.TemporaryMaterialHatCosmetique;

public class HatShop extends Shop{

	public HatShop(String name) {
		super(name);
	}

	public void filter(Shop shop) {
		this.resetShop();

		for(Page page : shop.getPages()) {
			for(int i = 0; i < page.getCosmetiques().length; i++) {
				if(page.getCosmetiques()[i] != null) {
					if(page.getCosmetiques()[i] instanceof MaterialHatCosmetique || page.getCosmetiques()[i] instanceof TemporaryMaterialHatCosmetique) {
						this.addCosmetique(page.getCosmetiques()[i]);
					}
				}
			}
		}
	}
}
