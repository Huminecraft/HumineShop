package humine.utils.shop;

import java.io.File;
import java.util.ArrayList;

public class Inventories {

	private ArrayList<Stock> stocks;
	
	/**
	 * Permet de creer un inventaire permettant de contenir des Class Stock
	 */
	public Inventories() {
		this.stocks = new ArrayList<Stock>();
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
	 * @param stock le stock a verifier
	 * @return true si trouve, sinon false
	 */
	public boolean containsStock(Stock stock) {
		return this.stocks.contains(stock);
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
		
		File pageFile;
		for(Stock stock : this.stocks) {
			pageFile = new File(folder, stock.getOwner());
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
		
		for(File file : folder.listFiles()) {
			Stock stock = new Stock("");
			stock.load(file);
			this.stocks.add(stock);
		}
	}
	
}
