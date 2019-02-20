package com.humine.utils.player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.humine.utils.shop.Cosmetique;
import com.humine.utils.shop.Shop;

public class CosmetiquePlayerManager {

	private HashMap<String, ArrayList<Cosmetique>> players;

	public CosmetiquePlayerManager() {
		this.players = new HashMap<String, ArrayList<Cosmetique>>();
	}

	public void addCosmetique(Player player, Cosmetique c) {
		ArrayList<Cosmetique> list;
		if (this.players.containsKey(player.getName())) {
			list = this.players.get(player.getName());
			list.add(c);
			this.players.replace(player.getName(), list);
		} else {
			list = new ArrayList<Cosmetique>();
			list.add(c);
			this.players.put(player.getName(), list);
		}
	}

	public void removeCosmetique(Player player, Cosmetique c) {
		if (this.players.containsKey(player.getName())) {
			ArrayList<Cosmetique> list = this.players.get(player.getName());
			list.remove(c);
			this.players.replace(player.getName(), list);
		}
	}

	public ArrayList<Cosmetique> getCosmetiquesOfPlayer(Player player) {
		if (this.players.containsKey(player.getName()))
			return this.players.get(player.getName());
		else
			return null;
	}

	public void setCosmetiquesOfPlayer(Player player, ArrayList<Cosmetique> list) {
		if (this.players.containsKey(player.getName()))
			this.players.replace(player.getName(), list);
		else
			this.players.put(player.getName(), list);
	}

	public HashMap<String, ArrayList<Cosmetique>> getCosmetiquesOfAllPlayer() {
		return this.players;
	}

	public void registerInFile(File file) {
		if (file.exists()) {
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			config.createSection("Cosmetiques");
			HashMap<String, ArrayList<String>> list = new HashMap<>();

			for (Entry<String, ArrayList<Cosmetique>> entry : this.players.entrySet()) {
				ArrayList<String> cosmetiques = new ArrayList<>();
				for (Cosmetique c : entry.getValue())
					cosmetiques.add(c.getName());

				list.put(entry.getKey(), cosmetiques);
			}

			for (Entry<String, ArrayList<String>> entry : list.entrySet()) {
				config.set("Cosmetiques." + entry.getKey(), entry.getValue());
			}

			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void getOnFile(File file, Shop shop) {
		if (file.exists()) {
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			HashMap<String, ArrayList<Cosmetique>> cosmetiques = new HashMap<>();

			for (String player : config.getConfigurationSection("Cosmetiques").getKeys(false)) {
				ArrayList<String> list = (ArrayList<String>) config.getStringList("Cosmetiques." + player);
				ArrayList<Cosmetique> cosmetiquesList = new ArrayList<>();

				for (String str : list) {
					Cosmetique c = shop.getCosmetique(str);
					if (c != null)
						cosmetiquesList.add(c);
				}

				cosmetiques.put(player, cosmetiquesList);
			}

			if (!cosmetiques.isEmpty())
				this.players = cosmetiques;
		}
	}
}
