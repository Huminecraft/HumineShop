package humine.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ParticleCosmetique extends Cosmetique{

	

	private Particle particleEffect;
	
	/**
	 * Classe permettant de creer des cosmetiques sur les effects de particule
	 * @param name le nom du cosmetique
	 * @param itemShop Le material servant a representer le cosmetique dans le shop
	 * @param price le prix du cosmetique
	 * @param particle la particule qui sera utilisee
	 */
	public ParticleCosmetique(String name, Material itemShop, int price, Particle particle) {
		super(name, itemShop, price);
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
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.err.println("Erreur dans la creation du fichier");
				e.printStackTrace();
				return;
			}
		}
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("id", this.getId());
		config.set("name", this.getName());
		config.set("prix", this.getPrice());
		config.set("itemshop", this.getItemShop().toString());
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
		if(!file.exists())
			return;
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		if(!config.contains("id") || !config.contains("name") || !config.contains("itemshop") || !config.contains("particle")) {
			System.err.println("Erreur parametre manquant dans le fichier " + file.getName());
			return;
		}
		
		this.setId(config.getString("id"));
		this.setName(config.getString("name"));
		this.setPrice(config.getInt("prix"));
		this.setItemShop(Material.matchMaterial(config.getString("itemshop")));
		this.particleEffect = Particle.valueOf(config.getString("particle"));
	}


}
