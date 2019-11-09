package humine.utils.cosmetiques;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import humine.utils.Prestige;

public abstract class AbstractMaterialHatCosmetique extends Cosmetique
{

	private static final long serialVersionUID = -690362258839694181L;
	private Material materialHat;
	
	public AbstractMaterialHatCosmetique(String name, ItemStack itemShop, int humisPrice, int pixelPrice, Material materialHat, Prestige prestige)
	{
		super(name, itemShop, humisPrice, pixelPrice, prestige);
		this.materialHat = materialHat;
	}
	
	public Material getMaterialHat()
	{
		return materialHat;
	}
	
	public void setMaterialHat(Material materialHat)
	{
		this.materialHat = materialHat;
	}

	@Override
	public void addLore(ChatColor color, List<String> lores)
	{
		lores.add(color + "Chapeau: " + materialHat.name().toLowerCase());
	}
	
	@Override
	public TypeCosmetique getType()
	{
		return TypeCosmetique.MATERIAL_HAT;
	}
}
