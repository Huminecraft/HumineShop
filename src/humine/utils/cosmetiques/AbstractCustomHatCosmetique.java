package humine.utils.cosmetiques;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import humine.utils.Prestige;

public abstract class AbstractCustomHatCosmetique extends Cosmetique
{

	private static final long serialVersionUID = -1140418409061961374L;
	private String libelle;
	private int amount;
	private CustomHatData customHatData;

	public AbstractCustomHatCosmetique(String name, ItemStack customItem, int humisPrice, int pixelPrice, Prestige prestige, String libelle)
	{
		super(name, customItem, humisPrice, pixelPrice, prestige);
		this.libelle = libelle;
		this.amount = 1;
		this.customHatData = new CustomHatData();
	}
	
	public String getLibelle() {
		return libelle;
	}
	
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public CustomHatData getCustomHatData()
	{
		return customHatData;
	}
	
	public void setCustomHatData(CustomHatData customHatData)
	{
		this.customHatData = customHatData;
	}
	
	@Override
	public void addLore(ChatColor color, List<String> lores)
	{
		lores.add(color + "Custom head: " + getLibelle());
	}
	
	@Override
	public TypeCosmetique getType()
	{
		return TypeCosmetique.CUSTOM_HAT;
	}
}
