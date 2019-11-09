package humine.utils.cosmetiques;

import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;

import humine.utils.Prestige;

/**
 * Package regroupant les outils de HumineShop
 * Classe representant un cosmetique particle qui permet d'activer
 * des effects de particules autour de son personnage
 * 
 * @author miza
 */
public class ParticleCosmetique extends AbstractParticleCosmetique{

	
	private static final long serialVersionUID = -3611073053542804446L;

	/**
	 * Classe permettant de creer des cosmetiques sur les effects de particule
	 * @param name le nom du cosmetique
	 * @param itemShop Le material servant a representer le cosmetique dans le shop
	 * @param price le prix du cosmetique
	 * @param particle la particule qui sera utilisee
	 */
	public ParticleCosmetique(String name, ItemStack itemShop, int humisPrice, int pixelPrice, Particle particle, Prestige prestige)
	{
		super(name, itemShop, humisPrice, pixelPrice, prestige, particle);
	}

}
