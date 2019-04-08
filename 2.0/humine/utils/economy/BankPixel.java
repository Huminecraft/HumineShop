package humine.utils.economy;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class BankPixel {

	private String name;
	private HashMap<String, Integer> players;
	
	/**
	 * constructeur de la banque 
	 * @param name nom de la valeur monetaire
	 */
	public BankPixel(String nameValue) {
		this.name = nameValue;
		this.players = new HashMap<String, Integer>();
	}
	
	/**
	 * ajouter un joueur dans la banque
	 * son argent est initialise a 0
	 * @param player le joueur en question
	 */
	public void addPlayer(Player player) {
		this.players.put(player.getName(), 0);
	}
	
	/**
	 * supprime un joueur dans la banque
	 * @param player le joueur en question
	 */
	public void removePlayer(Player player) {
		this.players.remove(player.getName());
	}
	
	/**
	 * supprime un joueur dans la banque
	 * @param player le joueur en question
	 */
	public void removePlayer(String player) {
		this.players.remove(player);
	}
	
	/**
	 * verifie si la banque contient le joueur
	 * @param player le joueur en question
	 * @return vrai si la banque contient le joueur, sinon false
	 */
	public boolean containsPlayer(Player player) {
		return this.players.containsKey(player.getName());
	}
	
	/**
	 * verifie si la banque contient le joueur
	 * @param player le joueur en question
	 * @return vrai si la banque contient le joueur, sinon false
	 */
	public boolean containsPlayer(String player) {
		return this.players.containsKey(player);
	}
	
	/**
	 * ajouter de l'argent a un joueur
	 * @param player le joueur en question
	 * @param amount le montant a ajouter
	 */
	public void addMoney(Player player, int amount) {
		if(this.players.containsKey(player.getName())) {
			this.players.replace(player.getName(), (this.players.get(player.getName()) + amount));
		}
	}
	
	/**
	 * ajouter de l'argent a un joueur
	 * @param player le joueur en question
	 * @param amount le montant a ajouter
	 */
	public void addMoney(String player, int amount) {
		if(this.players.containsKey(player)) {
			this.players.replace(player, (this.players.get(player) + amount));
		}
	}
	
	/**
	 * retirer de l'argent a un joueur
	 * @param player le joueur en question
	 * @param amount le montant a retirer
	 */
	public void removeMoney(Player player , int amount) {
		if(this.players.containsKey(player.getName())) {
			this.players.replace(player.getName(), (this.players.get(player.getName()) - amount));
		}
	}
	
	/**
	 * retirer de l'argent a un joueur
	 * @param player le joueur en question
	 * @param amount le montant a retirer
	 */
	public void removeMoney(String player , int amount) {
		if(this.players.containsKey(player)) {
			this.players.replace(player, (this.players.get(player) - amount));
		}
	}
	
	/**
	 * recuperer l'argent d'un joueur
	 * @param player le joueur en question
	 * @return l'argent
	 */
	public int getMoney(Player player) {
		if(this.players.containsKey(player.getName()))
			return this.players.get(player.getName());
		else
			return 0;
	}
	
	/**
	 * recuperer l'argent d'un joueur
	 * @param player le joueur en question
	 * @return l'argent
	 */
	public int getMoney(String player) {
		if(this.players.containsKey(player))
			return this.players.get(player);
		else
			return 0;
	}

	/**
	 * @return recupere le nom de la valeur monetaire
	 */
	public String getNameValue() {
		return name;
	}

	/**
	 * @param name change le nom de la banque
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return les clés (nom joueur) et valeur (argent)
	 */
	public HashMap<String, Integer> getPlayers() {
		return players;
	}

	/**
	 * @param players nouvelle liste
	 */
	public void setPlayers(HashMap<String, Integer> players) {
		this.players = players;
	}
	
	/**
	 * sauvegarder la banque dans un fichier
	 * @param file le fichier dans lequel sauvegarder
	 */
	public void save(File file) {
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("name", this.name);
		config.createSection("Players");
		for(Entry<String, Integer> entry : this.players.entrySet()) {
			config.set("Players." + entry.getKey(), entry.getValue());
		}
		
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * charger la banque dans un fichier
	 * @param file le fichier dans lequel charger
	 */
	public void load(File file) {
		if(!file.exists()) {
			return;
		}
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		if(config.contains("name")) {
			this.name = config.getString("name");
		}
		
		if(config.contains("Players")) {
			for(String key : config.getConfigurationSection("Players").getKeys(false)) {
				this.players.put(key, config.getInt("Players."+key));
			}
		}
		
	}
	
}
