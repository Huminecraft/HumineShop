package humine.utils.shop;

import humine.utils.cosmetiques.CustomHeadCosmetique;
import humine.utils.cosmetiques.temporary.TemporaryCustomHeadCosmetique;

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
