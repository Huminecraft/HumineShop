package humine.utils.shop;

import humine.utils.cosmetiques.CustomHeadCosmetique;
import humine.utils.cosmetiques.temporary.TemporaryCustomHeadCosmetique;

public class CustomHeadStock extends Shop{

	private static String stockName = "Custom Head Stock";

	public CustomHeadStock(String name) {
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
	
	public static String getCustomHeadStockName() {
		return stockName;
	}
}