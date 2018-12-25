package com.humine.utils.shop;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.humine.main.ShopMain;

public class Shop {

	private ArrayList<Page> pages;
	private String name;
	private HashMap<String, Integer> players;
	
	public Shop() {
		this.pages = new ArrayList<Page>();
		this.name = "Humine Shop";
		this.players = new HashMap<String, Integer>();

		this.pages.add(new Page(this.name + " 1/1"));
	}

	public Shop(String name)
	{
		this.pages = new ArrayList<Page>();
		this.name = name;
		this.players = new HashMap<String, Integer>();

		this.pages.add(new Page(this.name + " 1/1"));
	}

	public void openShop(Player player)
	{
		if (!this.pages.isEmpty())
		{
			player.openInventory(this.pages.get(0).getInventory());
			this.players.put(player.getName(), 1);
		}
		else
			ShopMain.sendMessage(player, "Desole, le shop est totalement vide");
	}

	public void openShop(Player player, int numeroOfPage)
	{
		if (!this.pages.isEmpty())
		{
			if (this.pages.size() >= numeroOfPage && 0 < numeroOfPage)
			{
				player.openInventory(this.pages.get((numeroOfPage - 1)).getInventory());
				this.players.put(player.getName(), numeroOfPage);
			}
			else
				ShopMain.sendMessage(player, "Desole, impossible d'acceder a cette page");
		}
		else
			ShopMain.sendMessage(player, "Desole, le shop est totalement vide");
	}

	public void nextPage(Player player)
	{
		if (this.players.containsKey(player.getName()))
		{
			if ((this.players.get(player.getName()) + 1) < this.pages.size())
			{
				player.openInventory(this.pages.get(this.players.get(player.getName()) + 1).getInventory());
				this.players.replace(player.getName(), (this.players.get(player.getName()) + 1));
			}
		}
	}

	public void previousPage(Player player)
	{
		if (this.players.containsKey(player.getName()))
		{
			if ((this.players.get(player.getName()) - 1) >= 0)
			{
				player.openInventory(this.pages.get(this.players.get(player.getName()) - 1).getInventory());
				this.players.replace(player.getName(), (this.players.get(player.getName()) - 1));
			}
		}
	}

	public void addCosmetique(Cosmetique cosmetique)
	{
		if (getLastPage().hasReachLimit())
		{
			Page page = new Page(this.name);
			page.addCosmetique(cosmetique);
			this.pages.add(page);
			refreshNamePage();
		}
		else
		{
			getLastPage().addCosmetique(cosmetique);
		}
	}

	public void removeCosmetique(Cosmetique cosmetique)
	{
		for (Page page : this.pages)
		{
			if (page.containsCosmetique(cosmetique))
			{
				page.removeCosmetique(cosmetique);
			}
		}
	}

	public boolean isTheFirstPage(Page page)
	{
		return (this.pages.get(0).getTitle().equals(page.getTitle()));
	}

	public boolean isTheLastPage(Page page)
	{
		return (this.pages.get(this.pages.size() - 1).getTitle().equals(page.getTitle()));
	}

	public Page getFirstPage()
	{
		return this.pages.get(0);
	}

	public Page getLastPage()
	{
		return this.pages.get(this.pages.size() - 1);
	}

	public Cosmetique getCosmetique(ItemStack item)
	{
		for (Page page : this.pages)
		{
			for (Cosmetique cosmetique : page.getCosmetiques())
			{
				if (cosmetique.getBlockRepresentation().isSimilar(item))
					return cosmetique;
			}
		}

		return null;
	}

	private void refreshNamePage()
	{
		for (int i = 0; i < this.pages.size(); i++)
		{
			this.pages.get(i).setTitle(this.name + " " + (i + 1) + "/" + this.pages.size());
		}
	}

	public int getNumberOfPage()
	{
		return this.pages.size();
	}

	public ArrayList<Page> getPages()
	{
		return pages;
	}

	public void setPages(ArrayList<Page> pages)
	{
		this.pages = pages;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public HashMap<String, Integer> getPlayersLookingShop()
	{
		return players;
	}
}
