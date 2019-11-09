package humine.utils.cosmetiques;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import humine.main.MainShop;
import humine.utils.Prestige;

/**
 * Package regroupant les outils de HumineShop
 * Classe representant un cosmetique
 * 
 * @author miza
 */
public abstract class Cosmetique implements Serializable, Cloneable{

	private static final long serialVersionUID = -5983883403291933943L;
	private String id;
	private String name;
	private Prestige prestige;
	
	private int humisPrice;
	private int PixelPrice;
	
	/**
	 * Classe abstraite permettant la création des cosmetiques
	 * de HumineShop
	 * @param name le nom du cosmetique
	 * @param itemShop Le material servant a representer le cosmetique dans le shop
	 */
	public Cosmetique(String name, ItemStack itemShop, int humisPrice, int pixelPrice, Prestige prestige) {
		this.name = name;
		this.humisPrice = humisPrice;
		this.PixelPrice = pixelPrice;
		this.prestige = prestige;
		
		this.id = "" + LocalDate.now().getYear();
		this.id += LocalDate.now().getDayOfYear();
		this.id += "-";
		this.id += MainShop.CosmetiqueID;
		
		MainShop.CosmetiqueID++;
		
		MainShop.getInstance().getItemShopBank().put(this, itemShop);
	}
	
	/**
	 * @return id l'id du cosmetique
	 */
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return name le nom du cosmetique
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return itemShop le material representant le cosmetique
	 */
	public ItemStack getItemShop() {
		return MainShop.getInstance().getItemShopBank().getItemShop(this);
	}
	
	/**
	 * @return le prix en humis du cosmetique
	 */
	public int getHumisPrice() {
		return humisPrice;
	}

	/**
	 * @param humisPrice le nouveau prix en humis du cosmetique
	 */
	public void setHumisPrice(int humisPrice) {
		this.humisPrice = humisPrice;
	}

	/**
	 * @return le prix en pixel du cosmetique
	 */
	public int getPixelPrice() {
		return PixelPrice;
	}

	/**
	 * @param humisPrice le nouveau prix en pixel du cosmetique
	 */
	public void setPixelPrice(int pixelPrice) {
		PixelPrice = pixelPrice;
	}

	/**
	 * @param name le nouveau nom du cosmetique
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param itemShop le nouvelle item de presentation dans le shop/stock
	 * du cosmetique
	 */
	public void setItemShop(ItemStack itemShop) {
		MainShop.getInstance().getItemShopBank().put(this, itemShop);
	}
	
	/**
	 * @return le prestige du cosmetique
	 */
	public Prestige getPrestige() {
		return prestige;
	}

	/**
	 * Renvoie de quel type est le cosmetique
	 * @return le type du cosmetique
	 */
	public abstract TypeCosmetique getType();
	
	/**
	 * Convertir un cosmetique en itemStack
	 * @param c le cosmetique
	 */
	public ItemStack convertToItem() {

		ItemStack item = new ItemStack(getItemShop());
		ChatColor color = ChatColor.valueOf(getPrestige().getColor());
		
		ArrayList<String> lores = new ArrayList<String>();
		lores.add(" ");
		lores.add(color + "" + ChatColor.BOLD + getPrestige().name().toUpperCase());
		lores.add(" ");
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(color + "" + ChatColor.UNDERLINE + getName() + " #" + getId());
		
		addLore(color, lores);

		meta.setLore(lores);
		item.setItemMeta(meta);
		return item;
	}

	public abstract void addLore(ChatColor color, List<String> lores);

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + PixelPrice;
		result = prime * result + humisPrice;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((prestige == null) ? 0 : prestige.hashCode());
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
		Cosmetique other = (Cosmetique) obj;
		if (PixelPrice != other.PixelPrice)
			return false;
		if (humisPrice != other.humisPrice)
			return false;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		if (prestige != other.prestige)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "Cosmetique [id=" + id + ", name=" + name + ", prestige=" + prestige + ", humisPrice=" + humisPrice
				+ ", PixelPrice=" + PixelPrice + "]";
	}

}
