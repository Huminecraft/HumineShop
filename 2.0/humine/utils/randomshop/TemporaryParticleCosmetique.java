package humine.utils.randomshop;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import humine.utils.ParticleCosmetique;
import humine.utils.Prestige;

public class TemporaryParticleCosmetique extends ParticleCosmetique{

	
	private LocalDate date;
	private Prestige prestige;
	
	public TemporaryParticleCosmetique(String name, Material itemShop, int price, Particle particle, LocalDate date, Prestige prestige) {
		super(name, itemShop, price, particle);
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
			e.printStackTrace();
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
}
