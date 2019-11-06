package humine.utils.shop;

import java.util.List;

import org.bukkit.inventory.Inventory;

import humine.utils.Shopper;
import humine.utils.cosmetiques.Cosmetique;

/**
 * Package regroupant les outils de HumineShop <br />
 * Classe stockant des {@link Cosmetique} <br />
 * Chaque joueur a son propre stock de {@link Cosmetique}
 * 
 * @author miza
 */
public class Stock extends Shop {

	public static final String STOCKNAME = "Stock";
	
	/**
	 * Constructeur
	 * @param owner le nom du proprietaire de ce stock
	 * par defaut: multiPage est a <b>true</b>
	 */
	public Stock(Shopper owner) {
		this(owner, true);
	}

	/**
	 * Constructeur
	 * @param owner le nom du proprietaire de ce stock
	 * @param multiPage le rendre multiPage ou non
	 */
	public Stock(Shopper owner, boolean multiPage) {
		super(owner, multiPage);
	}
	
	@Override
	public void pageTo(int index) {
		if(this.isEmpty() || index < 0 || index >= getPages().size())
			return;
		
		Page page = this.getPage(index);
		currentPage = index;
		
		Inventory inv = createInventory(STOCKNAME, page);
		
		shopper.getPlayer().openInventory(inv);
	}
	
	public void setCosmetique(List<Cosmetique> cosmetiques) {
		resetShop();
		for(Cosmetique c : cosmetiques)
			addCosmetique(c);
	}
	
}
