package humine.utils.shop;

import java.util.ArrayList;
import java.util.List;

import humine.utils.cosmetiques.Cosmetique;
import humine.utils.cosmetiques.MaterialHatCosmetique;
import humine.utils.cosmetiques.temporary.TemporaryMaterialHatCosmetique;

public class HatStock extends Shop
{

	private static String stockName = "Hat Stock";
	
	public HatStock(String name) {
		super(stockName + " " + name);
	}

	public void filter(Stock stock) {
		this.resetShop();
		List<Cosmetique> cosmetiques = new ArrayList<Cosmetique>();

		for(Page page : stock.getPages()) {
			for(int i = 0; i < page.getCosmetiques().length; i++) {
				if(page.getCosmetiques()[i] != null) {
					if(page.getCosmetiques()[i] instanceof MaterialHatCosmetique || page.getCosmetiques()[i] instanceof TemporaryMaterialHatCosmetique) {
						cosmetiques.add(page.getCosmetiques()[i]);
					}
				}
			}
		}
		
		for(Cosmetique c : cosmetiques) {
			this.addCosmetique(c);
		}
	}
	
	public static String getHatStockName()
	{
		return stockName;
	}
}
