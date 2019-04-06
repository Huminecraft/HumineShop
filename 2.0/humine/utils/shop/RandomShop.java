package humine.utils.shop;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

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
		File folder = new File(MainShop.getInstance().getRandomShopFolder(), LocalDate.now().toString());
		if (!folder.exists())
			return;

		this.load(folder);
		this.currentDate = LocalDate.now();
	}

	@Override
	public void save(File folder) {
		super.save(folder);

		File index = new File(folder, "date.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(index);
		config.set("currentDate", this.currentDate.toString());

		try {
			config.save(index);
		} catch (IOException e) {
			System.err.println("Erreur enregistrement index.yml");
			e.printStackTrace();
			return;
		}
	}

	@Override
	public void load(File folder) {
		super.load(folder);

		File index = new File(folder, "date.yml");

		if (!index.exists()) {
			System.err.println("Erreur fichier introuvable index.yml");
			return;
		}

		FileConfiguration config = YamlConfiguration.loadConfiguration(index);

		String date = config.getString("currentDate");

		this.currentDate = LocalDate.of(Integer.parseInt(date.split("-")[0]), Integer.parseInt(date.split("-")[1]), Integer.parseInt(date.split("-")[2]));
		index.delete();
	}

	public LocalDate getCurrentDate() {
		return currentDate;
	}

}
