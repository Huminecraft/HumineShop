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
	private Prestige prestige;
	
	public TemporaryCosmetique(String name, ItemStack itemShop, int humisPrice, int pixelPrice, LocalDate date, Prestige prestige) {
		super(name, itemShop, humisPrice, pixelPrice);
		this.date = date;
		this.prestige = prestige;
	}
	
	public LocalDate getDate() {
		return date;
	}

	public Prestige getPrestige() {
		return prestige;
	}
	
	@Override
	public void save(File file) {
		super.save(file);

		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("date", this.date.toString());
		config.set("prestige", this.prestige.toString());
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
		
		if(!config.contains("date") || !config.contains("prestige")) {
			System.err.println("Erreur parametre manquant dans le fichier " + file.getName());
			return;
		}
		
		int year = Integer.parseInt(config.getString("date").split("-")[0]);
		int month = Integer.parseInt(config.getString("date").split("-")[1]);
		int day = Integer.parseInt(config.getString("date").split("-")[2]);
		
		this.date = LocalDate.of(year, month, day);
		this.prestige = Prestige.valueOf(config.getString("prestige"));
	}

	@Override
	public String toString() {
		return "TemporaryCosmetique [getId()=" + getId() + ", getName()=" + getName() + ", getItemShop()="
				+ getItemShop() + ", getHumisPrice()=" + getHumisPrice() + ", getPixelPrice()=" + getPixelPrice()
				+ ", date=" + date + ", prestige=" + prestige + "]";
	}
	
	
	
}
