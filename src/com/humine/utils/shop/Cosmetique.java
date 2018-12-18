package com.humine.utils.shop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Cosmetique {

	private Particle particle;
	private int price;
	private String name;
	
	private ItemStack blockRepresentation;
	
	public Cosmetique() {
		this.price = 0;
		this.name = "";
		this.particle = Particle.CLOUD;
		this.blockRepresentation = generateBlock("Default", this.price);
	}
	
	public Cosmetique(String name) {
		this.price = 0;
		this.name = name;
		this.particle = Particle.CLOUD;
		this.blockRepresentation = generateBlock(this.name, this.price);
	}
	
	public Cosmetique(String name, int price) {
		this.price = price;
		this.name = name;
		this.particle = Particle.CLOUD;
		this.blockRepresentation = generateBlock(this.name, this.price);
	}
	
	private ItemStack generateBlock(String name, int price) {
		ItemStack item = new ItemStack(Material.RED_WOOL);
		List<String> lores = new ArrayList<String>();
		
		lores.add("Prix: " + ChatColor.GREEN + price);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lores);
		
		item.setItemMeta(meta);
		
		return item;
	}

	public Particle getParticle() {
		return particle;
	}

	public void setParticle(Particle particle) {
		this.particle = particle;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ItemStack getBlockRepresentation() {
		return blockRepresentation;
	}

	public void setBlockRepresentation(ItemStack blockRepresentation) {
		this.blockRepresentation = blockRepresentation;
	}

	@Override
	public boolean equals(Object cosmetique) {
		if(cosmetique instanceof Cosmetique) {
			Cosmetique c = (Cosmetique) cosmetique;
			return (c.getPrice() == this.getPrice() && c.getName() == this.getName() && c.getName() == this.getName());
		}
		
		return false;
	}
}
