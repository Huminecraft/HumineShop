package humine.utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import humine.main.MainShop;

public abstract class Cosmetique {

	private String id;
	private String name;
	private Material itemShop;
	private int price;
	
	public static int NumId = 0;
	
	static {
		File file = MainShop.getInstance().getIDFile();
		if(file.exists()) {
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			if(config.contains("cosmetique")) {
				NumId = config.getInt("cosmetique");
			}
			else {
				config.set("cosmetique", NumId);
				try {
					config.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * Classe abstraite permettant la création des cosmetiques
	 * de HumineShop
	 * @param name le nom du cosmetique
	 * @param itemShop Le material servant a representer le cosmetique dans le shop
	 */
	public Cosmetique(String name, Material itemShop, int price) {
		this.name = name;
		this.itemShop = itemShop;
		this.price = price;
		
		this.id = "" + LocalDate.now().getYear();
		this.id = this.id.substring(2);
		this.id += LocalDate.now().getDayOfYear();
		this.id += NumId;
		
		NumId++;
	}
	
	/**
	 * @return id l'id du cosmetique
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @return name le nom du cosmetique
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return itemShop le material representant le cosmetique
	 */
	public Material getItemShop() {
		return itemShop;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setItemShop(Material itemShop) {
		this.itemShop = itemShop;
	}

	/**
	 * sauvegarder le cosmetique dans un fichier
	 * @param file le fichier dans lequel le cosmetique doit etre enregistre
	 */
	public void save(File file) {
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.err.println("Erreur dans la creation du fichier");
				e.printStackTrace();
				return;
			}
		}
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("id", this.id);
		config.set("name", this.name);
		config.set("itemshop", this.itemShop.toString());
		try {
			config.save(file);
		} catch (IOException e) {
			System.err.println("Erreur enregistrement fichier");
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * charge le cosmetique dans un fichier
	 * @param file le fichier dans lequel le cosmetique doit etre charge
	 */
	public void load(File file) {
		if(!file.exists())
			return;
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		if(!config.contains("id") || !config.contains("name") || !config.contains("itemshop")) {
			System.err.println("Erreur parametre manquant dans le fichier " + file.getName());
			return;
		}
		
		this.id = config.getString("id");
		this.name = config.getString("name");
		this.itemShop = Material.matchMaterial(config.getString("itemshop"));
	}
	
	public static int getNumId() {
		return NumId;
	}
}
