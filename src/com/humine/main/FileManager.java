package com.humine.main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.humine.utils.money.Gemme;
import com.humine.utils.money.GemmeManager;

public abstract class FileManager
{

	private static String GemmeSection = "Gemmes";
	
	public static void saveGemmes(File file, GemmeManager manager) throws IOException {
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		if(!manager.getGemmes().isEmpty()) {
			for(Entry<String, Gemme> gem : manager.getGemmes().entrySet()) {
				config.set(GemmeSection + "." + gem.getKey() + ".Gemme", gem.getValue().getGemme());
			}
			
			config.save(file);
		}
	}
	
	public static HashMap<String, Gemme> getGemmes(File file) {
		HashMap<String, Gemme> gems = new HashMap<String, Gemme>();
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		for(String key : config.getConfigurationSection(GemmeSection).getKeys(false)) {
			Gemme gemme = new Gemme(config.getInt(GemmeSection + "." + key + ".Gemme"));
			gems.put(key, gemme);
		}
		
		return gems;
	}
}
