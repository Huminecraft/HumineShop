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
		return player.getName();
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
}
