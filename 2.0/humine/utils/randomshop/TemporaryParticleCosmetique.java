package humine.utils.randomshop;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import humine.utils.Prestige;

public class TemporaryParticleCosmetique extends TemporaryCosmetique{

	private Particle particle;
	
	public TemporaryParticleCosmetique(String name, Material itemShop, int price, LocalDate date, Prestige prestige, Particle particle) {
		super(name, itemShop, price, date, prestige);
		this.particle = particle;
	}
	
	public Particle getParticle() {
		return particle;
	}
	
	@Override
	public void save(File file) {
		super.save(file);
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("particle", this.particle.toString());
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

		if(!config.contains("particle")) {
			System.err.println("Erreur parametre manquant dans le fichier " + file.getName());
			return;
		}
		
		this.particle = Particle.valueOf(config.getString("particle"));
	}
}
