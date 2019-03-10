package humine.utils.randomshop;

import java.io.File;
import java.time.LocalDate;

import humine.utils.Page;
import humine.utils.Shop;

public class RandomShop extends Shop{

	private LocalDate currentDate;
	
	public RandomShop(String name) {
		super(name);
		this.currentDate = LocalDate.now();
	}
	
	public void update(File folder) {
		if(!folder.exists())
			return;
		
		File file = new File(folder, LocalDate.now().toString());
		if(!file.exists())
			return;

		resetShop();
		for(File f : file.listFiles()) {
			Page page = new Page("", 0);
			page.load(f);
			this.addPage(page);
		}
		
		this.currentDate = LocalDate.now();
	}
	
	public void resetShop() {
		this.getPages().clear();
	}
	
	public LocalDate getCurrentDate() {
		return currentDate;
	}
}
