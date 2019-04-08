package humine.utils.cosmetiques;

import java.io.File;
import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import humine.utils.Prestige;

public class MaterialHatCosmetique extends Cosmetique
{
	
	private Material materialHat;
	
	public MaterialHatCosmetique(String name, ItemStack itemShop, int humisPrice, int pixelPrice, Material materialHat, Prestige prestige)
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
	public void save(File file)
	{
		super.save(file);
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("materialHat", this.materialHat.toString());
		try {
			config.save(file);
		} catch (IOException e) {
			System.err.println("Erreur enregistrement fichier");
			e.printStackTrace();
			return;
		}
		
	}
	
	@Override
	public void load(File file)
	{
		super.load(file);

		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		if(!config.contains("materialHat")) {
			System.err.println("Erreur parametre manquant dans le fichier " + file.getName());
			return;
		}
		
		this.materialHat = Material.valueOf(config.getString("materialHat"));
	}

}
