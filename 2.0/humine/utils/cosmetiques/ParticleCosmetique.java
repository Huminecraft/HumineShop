package humine.utils.cosmetiques;

import java.io.File;
import java.io.IOException;

import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import humine.utils.Prestige;

/**
 * Package regroupant les outils de HumineShop
 * Classe representant un cosmetique particle qui permet d'activer
 * des effects de particules autour de son personnage
 * 
 * @author miza
 */
public class ParticleCosmetique extends Cosmetique{

	

	private Particle particleEffect;
	
	/**
	 * Classe permettant de creer des cosmetiques sur les effects de particule
	 * @param name le nom du cosmetique
	 * @param itemShop Le material servant a representer le cosmetique dans le shop
	 * @param price le prix du cosmetique
	 * @param particle la particule qui sera utilisee
	 */
	public ParticleCosmetique(String name, ItemStack itemShop, int humisPrice, int pixelPrice, Particle particle, Prestige prestige) {
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
	
	/**
	 * sauvegarder le cosmetique dans un fichier
	 * @param file le fichier dans lequel le cosmetique doit etre enregistre
	 */
	@Override
	public void save(File file) {
		super.save(file);
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("particle", this.particleEffect.toString());
		try {
			config.save(file);
		} catch (IOException e) {
			System.err.println("Erreur enregistrement fichier");
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * charge le cosmetique dans un fichier
	 * @param file le fichier dans lequel le cosmetique doit etre charge
	 */
	@Override
	public void load(File file) {
		super.load(file);
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		if(!config.contains("particle")) {
			System.err.println("Erreur parametre manquant dans le fichier " + file.getName());
			return;
		}
		
		this.particleEffect = Particle.valueOf(config.getString("particle"));
	}


}
