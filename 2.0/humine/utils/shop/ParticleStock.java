package humine.utils.shop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import humine.utils.ItemShop;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.cosmetiques.ParticleCosmetique;
import humine.utils.cosmetiques.temporary.TemporaryParticleCosmetique;

public class ParticleStock extends Shop
{

	private static String stockName = "Particle Stock";
	
	public ParticleStock(String name) {
		super(stockName + " " + name);
	}

	public void filter(Stock stock) {
		this.resetShop();
		List<Cosmetique> cosmetiques = new ArrayList<Cosmetique>();

		for(Page page : stock.getPages()) {
			for(int i = 0; i < page.getCosmetiques().length; i++) {
				if(page.getCosmetiques()[i] != null) {
					if(page.getCosmetiques()[i] instanceof ParticleCosmetique || page.getCosmetiques()[i] instanceof TemporaryParticleCosmetique) {
						cosmetiques.add(page.getCosmetiques()[i]);
					}
				}
			}
		}
		
		for(Cosmetique c : cosmetiques) {
			this.addCosmetique(c);
		}
	}
	
	public static String getParticleStockName()
	{
		return stockName;
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
		
		if(this.isMultiPage()) {
			inv.setItem(inv.getSize() - 6, ItemShop.itemPreviousArrow());
			inv.setItem(inv.getSize() - 4, ItemShop.itemNextArrow());
		}
		
		inv.setItem(inv.getSize() - 5, ItemShop.itemDisable());
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
		
		if(this.isMultiPage()) {
			inv.setItem(inv.getSize() - 6, ItemShop.itemPreviousArrow());
			inv.setItem(inv.getSize() - 4, ItemShop.itemNextArrow());
		}
		
		inv.setItem(inv.getSize() - 5, ItemShop.itemDisable());
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
		
		if(this.isMultiPage()) {
			inv.setItem(inv.getSize() - 6, ItemShop.itemPreviousArrow());
			inv.setItem(inv.getSize() - 4, ItemShop.itemNextArrow());
		}
		
		inv.setItem(inv.getSize() - 5, ItemShop.itemDisable());
		inv.setItem(inv.getSize() - 9, ItemShop.itemQuit());
		inv.setItem(inv.getSize() - 1, ItemShop.itemQuit());
		
		this.getPlayersOnShop().replace(player, page);
		player.openInventory(inv);
	}
}
