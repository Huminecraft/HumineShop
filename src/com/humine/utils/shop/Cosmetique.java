package com.humine.utils.shop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.humine.utils.shop.utils.CosmetiqueEffect;

public class Cosmetique {

	private Particle particle;
	private int price;
	private String name;
	private CosmetiqueEffect type;
	
	private ItemStack blockRepresentation;
	private ItemStack block;
	
	public Cosmetique() {
		this.price = 0;
		this.name = "";
		this.particle = Particle.CLOUD;
		this.type = CosmetiqueEffect.NOTHING;
		this.blockRepresentation = generateBlock("Default", this.price, Material.RED_WOOL);
	}
	
	public Cosmetique(String name) {
		this.price = 0;
		this.name = name;
		this.particle = Particle.CLOUD;
		this.type = CosmetiqueEffect.NOTHING;
		this.blockRepresentation = generateBlock(this.name, this.price, Material.RED_WOOL);
	}
	
	public Cosmetique(String name, int price) {
		this.price = price;
		this.name = name;
		this.particle = Particle.CLOUD;
		this.type = CosmetiqueEffect.NOTHING;
		this.blockRepresentation = generateBlock(this.name, this.price, Material.RED_WOOL);
	}
	
	private ItemStack generateBlock(String name, int price, Material material) {
		ItemStack item = new ItemStack(material);
		List<String> lores = new ArrayList<String>();
		
		lores.add("Prix: " + ChatColor.GREEN + price);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + name);
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

	public CosmetiqueEffect getType() {
		return type;
	}

	public void setType(CosmetiqueEffect type) {
		this.type = type;
	}

	public ItemStack getBlock() {
		return block;
	}

	public void setBlock(ItemStack block) {
		this.block = block;
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
