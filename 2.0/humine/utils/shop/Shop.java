package humine.utils.shop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import humine.utils.ItemShop;
import humine.utils.cosmetiques.Cosmetique;


public class Shop {

	private String name;
	protected ArrayList<Page> pages;
	protected HashMap<Player, Integer> playersOnShop;
	private boolean multiPage;
	
	/**
	 * Permet de creer une boutique de cosmetique
	 * @param name le nom de la boutique
	 */
	public Shop(String name) {
		this(name, true);
	}
	
	/**
	 * Permet de creer une boutique de cosmetique
	 * @param name le nom de la boutique
	 * @param multiPage si la boutique peut contenir plusieurs page
	 */
	public Shop(String name, boolean multiPage) {
		this.name = name;
		this.pages = new ArrayList<Page>();
		this.playersOnShop = new HashMap<Player, Integer>();
		this.multiPage = multiPage;
	}
	
	/**
	 * Ajouter une page dans la boutique
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
			if(this.pages.get(i).equals(page)) {
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
	public boolean removePage(int idx) {
		if(idx >= 0 && idx < this.pages.size()) {
			this.pages.remove(idx);
		}
		
		return false;
	}
	
	/**
	 * permet de verifier si le shop contient une page
	 * @param page la page a verifier
	 * @return boolean renvoie vrai si la boutique contient la page, sinon false
	 */
	public boolean containsPage(Page page) {
		for(int i = 0; i < this.pages.size(); i++) {
			if(this.pages.get(i).equals(page)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * ajoute un cosmetique dans un shop
	 * @param shop le shop dans lequel ajouter
	 * @param cosmetique le cosmetique a ajouter
	 */
	public void addCosmetique(Cosmetique cosmetique) {
		if(!this.isEmpty()) {
			if(!this.getLastPage().isFull()) {
				this.getLastPage().addCosmetique(cosmetique);
			}
			else {
				Page page = new Page("Page "+ this.pages.size() + 1, this.getLastPage().getSize());
				page.addCosmetique(cosmetique);
				this.addPage(page);
			}
		}
		else {
			Page page = new Page("Page 1", (9 * 4));
			page.addCosmetique(cosmetique);
			this.addPage(page);
		}
		
	}
	
	/**
	 * Permet de recuperer la premiere page de la boutique
	 * @return Page renvoie la premiere page de la boutique, renvoie NULL si il n'y a aucune page
	 */
	public Page getFirstPage() {
		if(this.pages.isEmpty())
			return null;
		else
			return this.pages.get(0);
	}
	
	/**
	 * Permet de recuperer la derniere page de la boutique
	 * @return Page renvoie la derniere page de la boutique, renvoie NULL si il n'y a aucune page
	 */
	public Page getLastPage() {
		if(this.pages.isEmpty())
			return null;
		else
			return this.pages.get(this.pages.size()-1);
	}
	
	/**
	 * Permet de recuperer la derniere page de la boutique
	 * @param n la page souhaitee
	 * @return Page renvoie la derniere page de la boutique, renvoie NULL si il n'y a aucune page OU est inexistant
	 */
	public Page getPage(int n) {
		if(!this.pages.isEmpty() && n >= 0 && n < this.pages.size())
			return this.pages.get(n);
		else
			return null;
	}
	
	public void resetShop() {
		this.getPages().clear();
	}
	
	/**
	 * Permet de voir si le joueur regarde le shop
	 * @param player le joueur a verifier
	 * @return true si contient le joueur, sinon false
	 */
	public boolean containsPlayer(Player player) {
		for(Player p : this.playersOnShop.keySet()) {
			if(p.getName().equals(player.getName()))
				return true;
		}
		return false;
	}
	/**
	 * Verifie si la boutique contient des pages
	 * @return boolean renvoie true si la boutique ne contient aucune page, sinon false
	 */
	public boolean isEmpty() {
		return this.pages.isEmpty();
	}
	
	public Cosmetique getCosmetique(String id) {
		for(Page page : this.pages) {
			Cosmetique c = page.getCosmetique(id);
			if(c != null)
				return c;
		}
		
		return null;
	}
	
	/**
	 * Sauvegarder la boutique dans un dossier
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
		config.set("name", this.name);
		config.set("multiPage", this.multiPage);
		
		try {
			config.save(index);
		} catch (IOException e) {
			System.err.println("Erreur enregistrement index.yml");
			e.printStackTrace();
			return;
		}
		
		File pageFile;
		for(Page page : this.pages) {
			pageFile = new File(folder, page.getName());
			page.save(pageFile);
		}
		
	}
	
	/*
	 * Charger la boutique dans un dossier
	 * @param folder le dossier dans lequel on veut charger la boutique
	 */
	public void load(File folder) {
		if(!folder.exists())
			return;
		
		File index = new File(folder, "index.yml");
		if(!index.exists()) {
			System.err.println("Erreur fichier introuvable index.yml: " + this.getClass().getName());
			return;
		}
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(index);
		this.name = config.getString("name");
		this.multiPage = config.getBoolean("multiPage");
		index.delete();
		
		for(File file : folder.listFiles()) {
			Page page = new Page("", 0);
			page.load(file);
			this.pages.add(page);
		}
		
	}
	
	/**
	 * @return String le nom de la boutique
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return ArrayList&lt;Page&gt; renvoie la liste des pages
	 */
	public ArrayList<Page> getPages() {
		return pages;
	}

	/**
	 * @return ArrayList&lt;Player&gt; renvoie la liste des joueurs regardant la boutique
	 */
	public HashMap<Player, Integer> getPlayersOnShop() {
		return playersOnShop;
	}
	
	public boolean isMultiPage() {
		return multiPage;
	}
	
	public void setMultiPage(boolean multiPage) {
		this.multiPage = multiPage;
	}
	
	/**
	 * Ouvrir la premiere page de la boutique au joueur, ne fait rien
	 * si la boutique est vide
	 * @param shop la boutique a ouvrir
	 * @param player a qui ouvrir la boutique
	 */
	public void openShop(Player player) {
		if(this.isEmpty()) {
			return;
		}
		
		Inventory inv = Bukkit.createInventory(player, this.getFirstPage().getSize()+9, this.getName());
		for(int i = 0; i < this.getFirstPage().getSize(); i++) {
			if(this.getFirstPage().getCosmetiques()[i] != null) {
				ItemStack item = Cosmetique.cosmetiqueToItem(this.getFirstPage().getCosmetiques()[i]);
				inv.addItem(item);
			}
		}
		
		if(multiPage) {
			inv.setItem(inv.getSize() - 6, ItemShop.itemPreviousArrow());
			inv.setItem(inv.getSize() - 4, ItemShop.itemNextArrow());
		}
		
		inv.setItem(inv.getSize() - 9, ItemShop.itemQuit());
		inv.setItem(inv.getSize() - 1, ItemShop.itemQuit());
		
		
		
		this.getPlayersOnShop().put(player, 1);
		player.openInventory(inv);
	}
	
	/**
	 * Permet d'aller a la page suivante
	 * @param shop la boutique en question
	 * @param player le joueur en question
	 */
	public void nextPage(Player player) {
		if(this.isEmpty() || !this.containsPlayer(player))
			return;
		
		if((this.getPlayersOnShop().get(player) + 1) > this.getPages().size())
			return;
		
		int page = this.getPlayersOnShop().get(player) + 1;
		
		Inventory inv = Bukkit.createInventory(player, this.getPage(page-1).getSize(), this.getName());
		for(int i = 0; i < this.getPage(page-1).getSize(); i++) {
			if(this.getPage(page-1).getCosmetiques()[i] != null) {
				ItemStack item = Cosmetique.cosmetiqueToItem(this.getPage(page-1).getCosmetiques()[i]);
				inv.addItem(item);
			}
		}
		
		if(multiPage) {
			inv.setItem(inv.getSize() - 6, ItemShop.itemPreviousArrow());
			inv.setItem(inv.getSize() - 4, ItemShop.itemNextArrow());
		}
		
		inv.setItem(inv.getSize() - 9, ItemShop.itemQuit());
		inv.setItem(inv.getSize() - 1, ItemShop.itemQuit());
		
		this.getPlayersOnShop().replace(player, page);
		player.openInventory(inv);
	}
	
	/**
	 * Permet d'aller a la page precedente
	 * @param shop la boutique en question
	 * @param player le joueur en question
	 */
	public void previousPage(Player player) {
		if(this.isEmpty() || !this.containsPlayer(player))
			return;
		
		if((this.getPlayersOnShop().get(player) - 1) < 1)
			return;
		
		int page = this.getPlayersOnShop().get(player) - 1;
		
		Inventory inv = Bukkit.createInventory(player, this.getPage(page-1).getSize(), this.getName());
		for(int i = 0; i < this.getPage(page-1).getSize(); i++) {
			if(this.getPage(page-1).getCosmetiques()[i] != null) {
				ItemStack item = Cosmetique.cosmetiqueToItem(this.getPage(page-1).getCosmetiques()[i]);
				inv.addItem(item);
			}
		}
		
		if(multiPage) {
			inv.setItem(inv.getSize() - 6, ItemShop.itemPreviousArrow());
			inv.setItem(inv.getSize() - 4, ItemShop.itemNextArrow());
		}
		
		inv.setItem(inv.getSize() - 9, ItemShop.itemQuit());
		inv.setItem(inv.getSize() - 1, ItemShop.itemQuit());
		
		this.getPlayersOnShop().replace(player, page);
		player.openInventory(inv);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (multiPage ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pages == null) ? 0 : pages.hashCode());
		result = prime * result + ((playersOnShop == null) ? 0 : playersOnShop.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Shop))
			return false;
		Shop other = (Shop) obj;
		if (multiPage != other.multiPage)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pages == null) {
			if (other.pages != null)
				return false;
		} else if (!pages.equals(other.pages))
			return false;
		if (playersOnShop == null) {
			if (other.playersOnShop != null)
				return false;
		} else if (!playersOnShop.equals(other.playersOnShop))
			return false;
		return true;
	}
	
	
	
}
