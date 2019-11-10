package humine.utils.cosmetiques;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;

import humine.utils.Prestige;

public abstract class AbstractParticleCosmetique extends Cosmetique
{

	private static final long serialVersionUID = 1660141763213141484L;
	private Particle particleEffect;
	
	/**
	 * Classe permettant de creer des cosmetiques sur les effects de particule
	 * @param name le nom du cosmetique
	 * @param itemShop Le material servant a representer le cosmetique dans le shop
	 * @param price le prix du cosmetique
	 * @param particle la particule qui sera utilisee
	 */
	public AbstractParticleCosmetique(String name, ItemStack itemShop, int humisPrice, int pixelPrice, Prestige prestige, Particle particle)
	{
		super(name, itemShop, humisPrice, pixelPrice, prestige);
		this.particleEffect = particle;
	}

	
	
	/**
	 * @return particleEffect la particule qui sera utilisee
	 */
	public Particle getParticleEffect() {
		return particleEffect;
	}

	/**
	 * @param particleEffect change la particule qui sera utilisee
	 */
	public void setParticleEffect(Particle particleEffect) {
		this.particleEffect = particleEffect;
	}
	
	@Override
	public void addLore(ChatColor color, List<String> lores)
	{
		lores.add(color + "Particules: " + particleEffect.name().toLowerCase());
	}
	
	@Override
	public TypeCosmetique getType()
	{
		return TypeCosmetique.PARTICLE;
	}

}
