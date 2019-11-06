package humine.utils.shop;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import humine.utils.ItemShop;
import humine.utils.Shopper;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.cosmetiques.TypeCosmetique;

/**
 * Package regroupant les outils de HumineShop <br />
 * Classe stockant des {@link Cosmetique}
 * 
 * @author miza
 */
public class Shop {

	public static final String SHOPNAME = "SHOP";
	protected ArrayList<Page> pages;
	protected boolean multiPage;
	protected Shopper shopper;
	protected int currentPage;
	
	/**
	 * Permet de creer une boutique de cosmetique
	 * @param name le nom de la boutique
	 * par defaut: multiPage est a <b>true</b>
	 */
	public Shop(Shopper shopper) {
		this(shopper, true);
	}
	
	/**
	 * Permet de creer une boutique de cosmetique
	 * @param name le nom de la boutique
	 * @param multiPage si la boutique peut contenir plusieurs page
	 */
	public Shop(Shopper shopper, boolean multiPage) {
		this.shopper = shopper;
		this.currentPage = 0;
		this.pages = new ArrayList<Page>();
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
				Page page = new Page(this.getLastPage().getSize());
				page.addCosmetique(cosmetique);
				this.addPage(page);
			}
		}
		else {
			Page page = new Page(9 * 4);
			page.addCosmetique(cosmetique);
			this.addPage(page);
		}
		
	}
	
	
	public void update(Shop shop) {
		resetShop();
		for(Page page : shop.getPages()) {
			for(Cosmetique c : page.getCosmetiques()) {
				if(c != null)
					addCosmetique(c);
			}
		}
	}
	
	public void update(Shop shop, TypeCosmetique type) {
		resetShop();
		for(Page page : shop.getPages()) {
			for(Cosmetique c : page.getCosmetiques()) {
				if(c != null && c.getType() == type)
					addCosmetique(c);
			}
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
	
	/**
	 * Supprime tout le contenu du Shop: {@link Page} et {@link Cosmetique}
	 */
	public void resetShop() {
		this.getPages().clear();
	}
	

	/**
	 * Verifie si la boutique contient des pages
	 * @return boolean renvoie true si la boutique ne contient aucune page, sinon false
	 */
	public boolean isEmpty() {
		return this.pages.isEmpty();
	}
	
	/**
	 * Recupere le cosmetique contenu dans le shop
	 * En regardant dans toute les {@link Page}
	 * @param id du cosmetique a rechercher
	 * @return le cosmetique trouve, sinon null si introuvable
	 */
	public Cosmetique getCosmetique(String id) {
		for(Page page : this.pages) {
			Cosmetique c = page.getCosmetique(id);
			if(c != null)
				return c;
		}
		
		return null;
	}
	
	public boolean containsCosmetique(String id) {
		return getCosmetique(id) != null;
	}
	
	/**
	 * @return ArrayList&lt;Page&gt; renvoie la liste des pages
	 */
	public ArrayList<Page> getPages() {
		return pages;
	}
	
	/**
	 * @return true si il est multiPage, sinon false
	 */
	public boolean isMultiPage() {
		return multiPage;
	}
	
	public void setMultiPage(boolean multiPage) {
		this.multiPage = multiPage;
	}
	
	public void openShop() {
		pageTo(0);
	}
	
	public void nextPage() {
		pageTo((currentPage + 1));
	}
	
	public void previousPage() {
		pageTo((currentPage - 1));
	}
	
	public void closeShop() {
		this.shopper.getPlayer().closeInventory();
		this.currentPage = 0;
	}

	public void pageTo(int index) {
		if(this.isEmpty() || index < 0 || index >= getPages().size())
			return;
		
		Page page = this.getPage(index);
		this.currentPage = index;
		
		Inventory inv = createInventory(SHOPNAME, page);
		
		this.shopper.getPlayer().openInventory(inv);
	}
	
	
	public Inventory createInventory(String name, Page page) {
		Inventory inv = Bukkit.createInventory(null, page.getSize(), name);
		for(int i = 0; i < page.getSize(); i++) {
			if(page.getCosmetiques()[i] != null) {
				ItemStack item = page.getCosmetiques()[i].convertToItem();
				inv.addItem(item);
			}
		}
		
		if(multiPage) {
			inv.setItem(inv.getSize() - 6, ItemShop.itemPreviousArrow());
			inv.setItem(inv.getSize() - 4, ItemShop.itemNextArrow());
		}
		
		inv.setItem(inv.getSize() - 9, ItemShop.itemQuit());
		inv.setItem(inv.getSize() - 1, ItemShop.itemQuit());
		
		return inv;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + currentPage;
		result = prime * result + (multiPage ? 1231 : 1237);
		result = prime * result + ((pages == null) ? 0 : pages.hashCode());
		result = prime * result + ((shopper == null) ? 0 : shopper.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shop other = (Shop) obj;
		if (currentPage != other.currentPage)
			return false;
		if (multiPage != other.multiPage)
			return false;
		if (pages == null)
		{
			if (other.pages != null)
				return false;
		}
		else if (!pages.equals(other.pages))
			return false;
		if (shopper == null)
		{
			if (other.shopper != null)
				return false;
		}
		else if (!shopper.equals(other.shopper))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "Shop [pages=" + pages + ", multiPage=" + multiPage + ", shopper=" + shopper + ", currentPage="
				+ currentPage + "]";
	}

	
}
