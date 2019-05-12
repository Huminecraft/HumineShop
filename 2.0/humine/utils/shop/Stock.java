package humine.utils.shop;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import humine.utils.cosmetiques.Cosmetique;

/**
 * Package regroupant les outils de HumineShop <br />
 * Classe stockant des {@link Cosmetique} <br />
 * Chaque joueur a son propre stock de {@link Cosmetique}
 * 
 * @author miza
 */
public class Stock extends Shop {

	private static String stockName = "Stock";
	
	private String owner;

	/**
	 * Constructeur
	 * @param owner le nom du proprietaire de ce stock
	 * par defaut: multiPage est a <b>true</b>
	 */
	public Stock(String owner) {
		this(owner, true);
	}

	/**
	 * Constructeur
	 * @param owner le nom du proprietaire de ce stock
	 * @param multiPage le rendre multiPage ou non
	 */
	public Stock(String owner, boolean multiPage) {
		super(stockName, multiPage);
		this.owner = owner;

	}

	/**
	 * Sauvegarder le stock dans un dossier
	 * 
	 * @param folder le dossier dans lequel on veut sauvegarder la boutique
	 */
	public void save(File folder) {
		super.save(folder);

		if (!folder.exists())
			folder.mkdirs();

		File index = new File(folder, "index.yml");

		FileConfiguration config = YamlConfiguration.loadConfiguration(index);
		config.set("owner", this.owner);
		try {
			config.save(index);
		} catch (IOException e) {
			System.err.println("Erreur enregistrement index.yml");
			e.printStackTrace();
			return;
		}

	}

	/*
	 * Charger le stock dans un dossier
	 * 
	 * @param folder le dossier dans lequel on veut charger le stock
	 */
	public void load(File folder) {
		if (!folder.exists())
			return;

		File index = new File(folder, "index.yml");
		if (!index.exists()) {
			System.err.println("Erreur fichier introuvable index.yml");
			return;
		}

		FileConfiguration config = YamlConfiguration.loadConfiguration(index);
		this.owner = config.getString("owner");
		index.delete();

		for (File file : folder.listFiles()) {
			Page page = new Page("", 0);
			page.load(file);
			this.pages.add(page);
		}
	}

	/**
	 * @return le proprietaire
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @return le nom du stock
	 */
	public static String getStockName() {
		return stockName;
	}
}
