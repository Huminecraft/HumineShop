package humine.utils.cosmetiques;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import humine.utils.Prestige;

/**
 * Package regroupant les outils de HumineShop
 * Classe representant un cosmetique material hat qui sont des 
 * material de minecraft pouvant etre pose sur la tete
 * 
 * @author miza
 */
public class MaterialHatCosmetique extends AbstractMaterialHatCosmetique
{
	
	private static final long serialVersionUID = 1930637979898184038L;

	public MaterialHatCosmetique(String name, ItemStack itemShop, int humisPrice, int pixelPrice, Material materialHat, Prestige prestige)
	{
		super(name, itemShop, humisPrice, pixelPrice, materialHat, prestige);
	}
}
