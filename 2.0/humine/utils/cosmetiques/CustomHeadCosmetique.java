package humine.utils.cosmetiques;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import humine.utils.Prestige;

/**
 * Package regroupant les outils de HumineShop
 * Classe representant un cosmetique custom head
 * qui sont des cosmetiques de tete personnalisee
 * pouvant etre place sur la tete ou sur le sol en tant que block
 * 
 * @author miza
 */
public class CustomHeadCosmetique extends Cosmetique{

	private String libelle;
	private int amount;
	
	public CustomHeadCosmetique(String name, ItemStack customItem, int humisPrice, int pixelPrice, Prestige prestige, String libelle) {
		super(name, customItem, humisPrice, pixelPrice, prestige);
		this.libelle = libelle;
		this.amount = 1;
	}
	
	public String getLibelle() {
		return libelle;
	}
	
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	@Override
	public void save(File file) {
		super.save(file);

		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("libelle", this.libelle);
		config.set("amount", this.amount);
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

		if(!config.contains("libelle") || !config.contains("amount")) {
			System.err.println("Erreur parametre manquant dans le fichier " + file.getName());
			return;
		}
		
		this.libelle = config.getString("libelle");
		this.amount = config.getInt("amount");
	}
}
