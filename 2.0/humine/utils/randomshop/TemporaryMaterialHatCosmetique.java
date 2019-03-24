package humine.utils.randomshop;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import humine.utils.Prestige;

public class TemporaryMaterialHatCosmetique extends TemporaryCosmetique
{

	private Material materialHat;
	
	public TemporaryMaterialHatCosmetique(String name, Material itemShop, int humisPrice, int pixelPrice, LocalDate date, Prestige prestige, Material materialHat)
	{
		super(name, itemShop, humisPrice, pixelPrice, date, prestige);
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
			e.printStackTrace();
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
