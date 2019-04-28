package humine.utils.shop;

import java.io.File;
import java.time.LocalDate;


import humine.main.MainShop;

public class RandomShop extends Shop {

	private LocalDate currentDate;

	public RandomShop(String name, boolean multiPage) {
		super(name, multiPage);
		this.currentDate = LocalDate.now();
	}

	public RandomShop(String name) {
		super(name);
		this.currentDate = LocalDate.now();
	}

	public void update() {
		this.resetShop();
		File folder = new File(MainShop.getInstance().getRandomShopFolder(), LocalDate.now().toString());
		if (!folder.exists())
			return;

		super.load(folder);
		this.currentDate = LocalDate.now();
	}

	public LocalDate getCurrentDate() {
		return currentDate;
	}

}
