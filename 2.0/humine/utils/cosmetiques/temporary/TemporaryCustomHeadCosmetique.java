package humine.utils.cosmetiques.temporary;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import humine.utils.Prestige;

public class TemporaryCustomHeadCosmetique extends TemporaryCosmetique{

	private String libelle;
	
	public TemporaryCustomHeadCosmetique(String name, ItemStack customHead, int humisPrice, int pixelPrice,
			LocalDate date, Prestige prestige, String libelle) {
		super(name, customHead, humisPrice, pixelPrice, date, prestige);
		this.libelle = libelle;
	}

	@Override
	public void save(File file) {
		super.save(file);

		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("libelle", this.libelle);
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

		if(!config.contains("libelle")) {
			System.err.println("Erreur parametre manquant dans le fichier " + file.getName());
			return;
		}
		
		this.libelle = config.getString("libelle");
	}
	
	public String getLibelle() {
		return libelle;
	}
	
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
}
