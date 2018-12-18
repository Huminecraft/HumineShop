package com.humine.utils.shop;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.humine.main.ShopMain;

public class Shop {

	private ArrayList<Page> pages;
	private String name;
	
	public Shop() {
		this.pages = new ArrayList<Page>();
		this.name = "Shop";
		
		this.pages.add(new Page(this.name));
	}
	
	public Shop(String name) {
		this.pages = new ArrayList<Page>();
		this.name = name;
		
		this.pages.add(new Page(this.name));
	}
	
	public void openShop(Player player) {
		if(!this.pages.isEmpty())
			player.openInventory(this.pages.get(0).getInventory());
		else
			ShopMain.sendMessage(player, "Désolé, le shop est totalement vide");
	}
	
	public void openShop(Player player, int numeroOfPage) {
		if(!this.pages.isEmpty()) {
			if(this.pages.size() >= numeroOfPage && 0 < numeroOfPage)
				player.openInventory(this.pages.get((numeroOfPage-1)).getInventory());
			else
				ShopMain.sendMessage(player, "Désolé, impossible d'accéder à cette page");
		}
		else
			ShopMain.sendMessage(player, "Désolé, le shop est totalement vide");
	}
	
	public void addCosmetique(Cosmetique cosmetique) {
		if(this.pages.get(getNumberOfPage()-1).hasReachLimit()) {
			Page page = new Page(this.name);
			page.addCosmetique(cosmetique);
			this.pages.add(page);
		}
		else
			this.pages.get(getNumberOfPage()-1).addCosmetique(cosmetique);
	}
	
	public void removeCosmetique(Cosmetique cosmetique) {
		for(Page page : this.pages) {
			if(page.containsCosmetique(cosmetique)) {
				page.removeCosmetique(cosmetique);
			}
		}
	}
	
	public int getNumberOfPage() {
		return this.pages.size();
	}

	public ArrayList<Page> getPages() {
		return pages;
	}

	public void setPages(ArrayList<Page> pages) {
		this.pages = pages;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
