package humine.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Stock extends Shop{

	
	private String owner;
	
	public Stock(String owner) {
		super(owner);
		this.owner = owner;
	}
	
	/**
	 * Ajouter une page dans le stock
	 * @param page la page en question
	 */
	public void addPage(Page page) {
		this.pages.add(page);
	}
	
	/**
	 * permet de supprimer une page
	 * @param page la page en question
	 * @return boolean retourne vrai si la page a etait supprimee, sinon false
	 */
	public boolean removePage(Page page) {
		for(int i = 0; i < this.pages.size(); i++) {
			if(this.pages.get(i).getId().equals(page.getId())) {
				this.pages.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * permet de supprimer une page
	 * @param id l'id de la page en question
	 * @return boolean retourne vrai si la page a etait supprimee, sinon false
	 */
	public boolean removePage(long id) {
		for(int i = 0; i < this.pages.size(); i++) {
			if(this.pages.get(i).getId().equals(""+id)) {
				this.pages.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * permet de supprimer une page
	 * @param id l'id de la page en question
	 * @return boolean retourne vrai si la page a etait supprimee, sinon false
	 */
	public boolean removePage(String id) {
		for(int i = 0; i < this.pages.size(); i++) {
			if(this.pages.get(i).getId().equals(id)) {
				this.pages.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * permet de verifier si le stock contient une page
	 * @param page la page a verifier
	 * @return boolean renvoie vrai si la boutique contient la page, sinon false
	 */
	public boolean containsPage(Page page) {
		for(int i = 0; i < this.pages.size(); i++) {
			if(this.pages.get(i).getId().equals(page.getId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * permet de verifier si le stock contient une page
	 * @param id l'id de la page a verifier
	 * @return boolean renvoie vrai si la boutique contient la page, sinon false
	 */
	public boolean containsPage(long id) {
		for(int i = 0; i < this.pages.size(); i++) {
			if(this.pages.get(i).getId().equals(""+id)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * permet de verifier si le stock contient une page
	 * @param id l'id de la page a verifier
	 * @return boolean renvoie vrai si la boutique contient la page, sinon false
	 */
	public boolean containsPage(String id) {
		for(int i = 0; i < this.pages.size(); i++) {
			if(this.pages.get(i).getId().equals(id)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Permet de recuperer la premiere page du stock
	 * @return Page renvoie la premiere page du stock, renvoie NULL si il n'y a aucune page
	 */
	public Page getFirstPage() {
		if(this.pages.isEmpty())
			return null;
		else
			return this.pages.get(0);
	}
	
	/**
	 * Permet de recuperer la derniere page du stock
	 * @return Page renvoie la derniere page de la boutique, renvoie NULL si il n'y a aucune page
	 */
	public Page getLastPage() {
		if(this.pages.isEmpty())
			return null;
		else
			return this.pages.get(this.pages.size()-1);
	}
	
	/**
	 * Permet de recuperer la derniere page du stock
	 * @param n la page souhaitee
	 * @return Page renvoie la derniere page de la boutique, renvoie NULL si il n'y a aucune page OU est inexistant
	 */
	public Page getPage(int n) {
		if(!this.pages.isEmpty() && n >= 0 && n < this.pages.size())
			return this.pages.get(n);
		else
			return null;
	}
	
	/**
	 * Verifie si le stock contient des pages
	 * @return boolean renvoie true si la boutique ne contient aucune page, sinon false
	 */
	public boolean isEmpty() {
		return this.pages.isEmpty();
	}
	
	/**
	 * Sauvegarder le stock dans un dossier
	 * @param folder le dossier dans lequel on veut sauvegarder la boutique
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
		config.set("id", this.getId());
		config.set("owner", this.owner);
		try {
			config.save(index);
		} catch (IOException e) {
			System.err.println("Erreur enregistrement index.yml");
			e.printStackTrace();
			return;
		}
		
		File pageFile;
		for(Page page : this.pages) {
			pageFile = new File(folder, page.getId());
			page.save(pageFile);
		}
		
	}
	
	/*
	 * Charger le stock dans un dossier
	 * @param folder le dossier dans lequel on veut charger le stock
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
		this.owner = config.getString("owner");
		index.delete();
		
		for(File file : folder.listFiles()) {
			Page page = new Page("", 0);
			page.load(file);
			this.pages.add(page);
		}
	}


	public String getOwner() {
		return owner;
	}

	public ArrayList<Page> getPages() {
		return pages;
	}

	public static int getNumId() {
		return getNumId();
	}
	
	public static void openStock(Stock stock, Player player) {
		if(stock.isEmpty()) {
			return;
		}
		
		Inventory inv = Bukkit.createInventory(player, stock.getFirstPage().getSize()+9, "Inventaire");
		for(int i = 0; i < stock.getFirstPage().getSize(); i++) {
			if(stock.getFirstPage().getCosmetiques()[i] != null) {
				ItemStack item = Utils.CosmetiqueToItem(stock.getFirstPage().getCosmetiques()[i]);
				inv.addItem(item);
			}
		}
		
		inv.setItem(inv.getSize() - 9, Utils.addArrow("Retour"));
		inv.setItem(inv.getSize() - 5, Utils.itemQuit());
		inv.setItem(inv.getSize() - 1, Utils.addArrow("Suivant"));
		
		player.openInventory(inv);
	}
	
	/**
	 * Permet d'aller a la page suivante
	 * @param stock la boutique en question
	 * @param player le joueur en question
	 */
	public static void nextPage(Stock stock, Player player) {
		if(stock.isEmpty() || !stock.containsPlayer(player))
			return;
		
		if((stock.getPlayersOnShop().get(player) + 1) > stock.getPages().size())
			return;
		
		int page = stock.getPlayersOnShop().get(player) + 1;
		
		Inventory inv = Bukkit.createInventory(player, stock.getPage(page-1).getSize(), stock.getName());
		for(int i = 0; i < stock.getPage(page-1).getSize(); i++) {
			if(stock.getPage(page-1).getCosmetiques()[i] != null) {
				ItemStack item = Utils.CosmetiqueToItem(stock.getPage(page-1).getCosmetiques()[i]);
				inv.addItem(item);
			}
		}
		
		inv.setItem(inv.getSize() - 9, Utils.addArrow("Retour"));
		inv.setItem(inv.getSize() - 5, Utils.itemStock());
		inv.setItem(inv.getSize() - 4, Utils.itemRandomShop());
		inv.setItem(inv.getSize() - 1, Utils.addArrow("Suivant"));
		
		stock.getPlayersOnShop().replace(player, page);
		player.openInventory(inv);
	}
	
	/**
	 * Permet d'aller a la page precedente
	 * @param stock la boutique en question
	 * @param player le joueur en question
	 */
	public static void previousPage(Stock stock, Player player) {
		if(stock.isEmpty() || !stock.containsPlayer(player))
			return;
		
		if((stock.getPlayersOnShop().get(player) - 1) < 1)
			return;
		
		int page = stock.getPlayersOnShop().get(player) - 1;
		
		Inventory inv = Bukkit.createInventory(player, stock.getPage(page-1).getSize(), stock.getName());
		for(int i = 0; i < stock.getPage(page-1).getSize(); i++) {
			if(stock.getPage(page-1).getCosmetiques()[i] != null) {
				ItemStack item = Utils.CosmetiqueToItem(stock.getPage(page-1).getCosmetiques()[i]);
				inv.addItem(item);
			}
		}
		
		inv.setItem(inv.getSize() - 9, Utils.addArrow("Retour"));
		inv.setItem(inv.getSize() - 5, Utils.itemStock());
		inv.setItem(inv.getSize() - 4, Utils.itemRandomShop());
		inv.setItem(inv.getSize() - 1, Utils.addArrow("Suivant"));
		
		stock.getPlayersOnShop().replace(player, page);
		player.openInventory(inv);
	}

}
