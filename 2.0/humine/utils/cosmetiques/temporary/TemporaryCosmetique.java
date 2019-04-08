package humine.utils.cosmetiques.temporary;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import humine.utils.Prestige;
import humine.utils.cosmetiques.Cosmetique;

public class TemporaryCosmetique extends Cosmetique{

	private LocalDate date;
	
	public TemporaryCosmetique(String name, ItemStack itemShop, int humisPrice, int pixelPrice, LocalDate date, Prestige prestige) {
		super(name, itemShop, humisPrice, pixelPrice, prestige);
		this.date = date;
	}
	
	public LocalDate getDate() {
		return date;
	}

	
	@Override
	public void save(File file) {
		super.save(file);

		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("date", this.date.toString());
		try {
			config.save(file);
		} catch (IOException e) {
			System.err.println("Erreur enregistrement fichier");
			e.printStackTrace();
			return;
		}
	}
	
	@Override
	public void load(File file) {
		super.load(file);

		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		if(!config.contains("date")) {
			System.err.println("Erreur parametre manquant dans le fichier " + file.getName());
			return;
		}
		
		int year = Integer.parseInt(config.getString("date").split("-")[0]);
		int month = Integer.parseInt(config.getString("date").split("-")[1]);
		int day = Integer.parseInt(config.getString("date").split("-")[2]);
		
		this.date = LocalDate.of(year, month, day);
	}

	@Override
	public String toString() {
		return "TemporaryCosmetique [getId()=" + getId() + ", getName()=" + getName() + ", getItemShop()="
				+ getItemShop() + ", getHumisPrice()=" + getHumisPrice() + ", getPixelPrice()=" + getPixelPrice()
				+ ", date=" + date + "]";
	}
	
	
	
}
