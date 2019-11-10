package humine.utils.cosmetiques;

import org.bukkit.inventory.ItemStack;

import humine.utils.Prestige;

/**
 * Package regroupant les outils de HumineShop
 * Classe representant un cosmetique custom head
 * qui sont des cosmetiques de tete personnalisee
 * pouvant etre place sur la tete ou sur le sol en tant que block
 * 
 * @author miza
 */
public class CustomHeadCosmetique extends AbstractCustomHatCosmetique{

	
	private static final long serialVersionUID = 7368381076768950373L;

	public CustomHeadCosmetique(String name, ItemStack customItem, int humisPrice, int pixelPrice, Prestige prestige, String libelle)
	{
		super(name, customItem, humisPrice, pixelPrice, prestige, libelle);
	}
}
