package humine.utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import humine.main.MainShop;

public class Inventories {

	private String id;
	private ArrayList<Stock> stocks;
	
	private static int NumId = 0;
	
	static {
		File file = new File(MainShop.getInstance().getDataFolder(), "ID.yml");
		if(file.exists()) {
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			if(config.contains("inventories")) {
				NumId = config.getInt("inventories");
			}
			else {
				config.set("inventories", NumId);
				try {
					config.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Permet de creer un inventaire permettant de contenir des Class Stock
	 */
	public Inventories() {
		this.stocks = new ArrayList<Stock>();
		
		this.id = "" + LocalDate.now().getYear();
		this.id = this.id.substring(2);
		this.id += LocalDate.now().getDayOfYear();
		this.id += NumId;
		
		NumId++;
	}

	/**
	 * @return Permet de recuperer tout les stocks
	 */
	public ArrayList<Stock> getStocks() {
		return stocks;
	}
	
	/**
	 * @param name le nom du joueur
	 * @return le stock du joueur, null si il n'a pas de stock
	 */
	public Stock getStockOfPlayer(String name) {
		for(Stock stock : this.stocks) {
			if(stock.getOwner().equals(name))
				return stock;
		}
		
		return null;
	}
	
	/**
	 * @param stock le stock a ajouter
	 */
	public void addStock(Stock stock) {
		this.stocks.add(stock);
	}
	
	/**
	 * @param stock le stock a supprimer
	 * @return true si le stock a etait supprime, sinon false
	 */
	public boolean removeStock(Stock stock) {
		if(this.stocks.contains(stock)) {
			this.stocks.remove(stock);
			return true;
		}
		
		return false;
	}
	
	/**
	 * @param id l'id du stock a supprimer
	 * @return true si le stock a etait supprime, sinon false
	 */
	public boolean removeStock(long id) {
		for(Stock stock : this.stocks) {
			if(stock.getId().equals(""+id)) {
				this.stocks.remove(stock);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param id l'id du stock a supprimer
	 * @return true si le stock a etait supprime, sinon false
	 */
	public boolean removeStock(String id) {
		for(Stock stock : this.stocks) {
			if(stock.getId().equals(id)) {
				this.stocks.remove(stock);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param stock le stock a verifier
	 * @return true si trouve, sinon false
	 */
	public boolean containsStock(Stock stock) {
		return this.stocks.contains(stock);
	}
	
	/**
	 * @param id l'id du stock a verifier
	 * @return true si trouve, sinon false
	 */
	public boolean containsStock(long id) {
		for(Stock stock : this.stocks) {
			if(stock.getId().equals(""+id)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param id l'id du stock a verifier
	 * @return true si trouve, sinon false
	 */
	public boolean containsStock(String id) {
		for(Stock stock : this.stocks) {
			if(stock.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param name le nom du joueur
	 * @return le Stock du joueur
	 */
	public boolean containsStockOfPlayer(String name) {
		for(Stock stock : this.stocks) {
			if(stock.getOwner().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return true si les stocks sont vides, sinon false
	 */
	public boolean isEmpty() {
		return this.stocks.isEmpty();
	}
	
	/**
	 * Permet de sauvegarder l'inventories
	 * @param folder le dossier dans lequel enregistrer
	 */
	public void save(File folder) {
		if(!folder.exists())
			folder.mkdirs();
		
		File index = new File(folder, "index.yml");
		if(!index.exists()) {
			try {
				index.createNewFile();
			} catch (IOException e) {
				System.err.println("Erreur creation index.yml");
				e.printStackTrace();
				return;
			}
		}
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(index);
		config.set("id", this.id);
		try {
			config.save(index);
		} catch (IOException e) {
			System.err.println("Erreur enregistrement index.yml");
			e.printStackTrace();
			return;
		}
		
		File pageFile;
		for(Stock stock : this.stocks) {
			pageFile = new File(folder, stock.getId()+".yml");
			stock.save(pageFile);
		}
	}
	
	/**
	 * Permet de charger l'inventories
	 * @param folder le dossier dans lequel charger
	 */
	public void load(File folder) {
		if(!folder.exists())
			return;
		
		File index = new File(folder, "index.yml");
		if(!index.exists()) {
			System.err.println("Erreur fichier introuvable index.yml");
			return;
		}
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(index);
		this.id = config.getString("id");
		index.delete();
		
		for(File file : folder.listFiles()) {
			Stock stock = new Stock("", "");
			stock.load(file);
			this.stocks.add(stock);
		}
	}
	
	/**
	 * return l'id de Inventories
	 */
	public String getId() {
		return id;
	}
	
	public static int getNumId() {
		return NumId;
	}
}
