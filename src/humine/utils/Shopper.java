package humine.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import humine.main.MainShop;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.cosmetiques.TypeCosmetique;
import humine.utils.shop.RandomShop;
import humine.utils.shop.Shop;
import humine.utils.shop.Stock;

public class Shopper
{

	private Money pixel;
	private Money humis;
	private Player player;
	
	private List<Cosmetique> cosmetiques;
	
	private Shop shop;
	private RandomShop randomShop;
	private Stock stock;
	
	public Shopper(Player player)
	{
		this.pixel = new Money("Pixel");
		this.humis = new Money("Humis");
		this.player = player;
		this.cosmetiques = new ArrayList<Cosmetique>();
		this.shop = new Shop(this);
		this.stock = new Stock(this);
		this.randomShop = new RandomShop(this, false);
	}
	
	public Money getPixel()
	{
		return pixel;
	}
	
	public void setPixel(Money pixel)
	{
		this.pixel = pixel;
	}
	
	public Money getHumis()
	{
		return humis;
	}
	
	public void setHumis(Money humis)
	{
		this.humis = humis;
	}
	
	public Player getPlayer()
	{
		return player;
	}

	public String getName() {
		return (player != null) ? player.getName() : null;
	}
	
	public List<Cosmetique> getCosmetiques()
	{
		return cosmetiques;
	}
	
	public List<Cosmetique> getCosmetiques(TypeCosmetique type) {
		List<Cosmetique> cosmetiques = new ArrayList<>();
		for(Cosmetique c : this.cosmetiques) {
			if(c.getType() == type)
				cosmetiques.add(c);
		}
		return cosmetiques;
	}
	
	public void setCosmetiques(List<Cosmetique> cosmetiques)
	{
		this.cosmetiques = cosmetiques;
	}
	
	public Shop getShop()
	{
		return shop;
	}
	
	public void setShop(Shop shop)
	{
		this.shop = shop;
	}
	
	public Stock getStock()
	{
		return stock;
	}
	
	public void setStock(Stock stock)
	{
		this.stock = stock;
	}
	
	public RandomShop getRandomShop()
	{
		return randomShop;
	}
	
	public void setRandomShop(RandomShop randomShop)
	{
		this.randomShop = randomShop;
	}
	
	public File getShopperFolder() {
		return new File(MainShop.getInstance().getShopperFolder(), this.player.getUniqueId().toString());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cosmetiques == null) ? 0 : cosmetiques.hashCode());
		result = prime * result + ((humis == null) ? 0 : humis.hashCode());
		result = prime * result + ((pixel == null) ? 0 : pixel.hashCode());
		result = prime * result + ((randomShop == null) ? 0 : randomShop.hashCode());
		result = prime * result + ((shop == null) ? 0 : shop.hashCode());
		result = prime * result + ((stock == null) ? 0 : stock.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shopper other = (Shopper) obj;
		if (cosmetiques == null) {
			if (other.cosmetiques != null)
				return false;
		} else if (!cosmetiques.equals(other.cosmetiques))
			return false;
		if (humis == null) {
			if (other.humis != null)
				return false;
		} else if (!humis.equals(other.humis))
			return false;
		if (pixel == null) {
			if (other.pixel != null)
				return false;
		} else if (!pixel.equals(other.pixel))
			return false;
		if (randomShop == null) {
			if (other.randomShop != null)
				return false;
		} else if (!randomShop.equals(other.randomShop))
			return false;
		if (shop == null) {
			if (other.shop != null)
				return false;
		} else if (!shop.equals(other.shop))
			return false;
		if (stock == null) {
			if (other.stock != null)
				return false;
		} else if (!stock.equals(other.stock))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Shopper [name=" + getName() + ", pixel=" + pixel + ", humis=" + humis + ", cosmetiques="
				+ cosmetiques + "]";
	}

	
}
