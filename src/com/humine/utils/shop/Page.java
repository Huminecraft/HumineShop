package com.humine.utils.shop;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Page {

	private ArrayList<Cosmetique> cosmetiques;
	private int limit;
	private Inventory inventory;
	private String title;
	
	public Page() {
		this.cosmetiques = new ArrayList<Cosmetique>();
		this.limit = (9 * 4);
		this.title = "";
		this.inventory = Bukkit.createInventory(null, (this.limit + 9), this.title);
	
		this.inventory.setItem(this.limit, addArrow("Precedent"));
		this.inventory.setItem((this.limit + 9)-1, addArrow("Suivant"));
	}
	
	public Page(String title) {
		this.cosmetiques = new ArrayList<Cosmetique>();
		this.limit = (9 * 4);
		this.title = title;
		this.inventory = Bukkit.createInventory(null, (this.limit + 9), this.title);
	
		this.inventory.setItem(this.limit, addArrow("Precedent"));
		this.inventory.setItem((this.limit + 9)-1, addArrow("Suivant"));
	}
	
	public Page(String title, int numberOfLine) {
		this.cosmetiques = new ArrayList<Cosmetique>();
		this.limit = (9 * numberOfLine);
		this.title = title;
		this.inventory = Bukkit.createInventory(null, (this.limit + 9), this.title);
	
		this.inventory.setItem(this.limit, addArrow("Precedent"));
		this.inventory.setItem((this.limit + 9)-1, addArrow("Suivant"));
	}
	
	private ItemStack addArrow(String name) {
		ItemStack item = new ItemStack(Material.ARROW);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.ITALIC + name);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public void addCosmetique(Cosmetique cosmetique) {
		if(this.limit > this.cosmetiques.size()) {
			this.cosmetiques.add(cosmetique);
			this.inventory.setItem(this.cosmetiques.size()-1, cosmetique.getBlockRepresentation());
		}
	}
	
	public void removeCosmetique(Cosmetique cosmetique) {
		this.cosmetiques.remove(cosmetique);
		this.inventory.remove(cosmetique.getBlockRepresentation());
	}
	
	public boolean containsCosmetique(Cosmetique cosmetique) {
		return this.cosmetiques.contains(cosmetique);
	}
	
	public boolean hasReachLimit() {
		return this.limit <= this.cosmetiques.size();
	}
	
	public boolean isEmpty() {
		return this.cosmetiques.isEmpty();
	}

	public ArrayList<Cosmetique> getCosmetiques() {
		return cosmetiques;
	}

	public void setCosmetiques(ArrayList<Cosmetique> cosmetiques) {
		this.cosmetiques = cosmetiques;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		
		Inventory inv = Bukkit.createInventory(null, (this.limit + 9), this.title);
		inv.setContents(this.inventory.getContents());
		this.inventory = inv;
	}
}
