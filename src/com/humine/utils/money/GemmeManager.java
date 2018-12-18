package com.humine.utils.money;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class GemmeManager {

	HashMap<String, Gemme> gemmes;
	
	public GemmeManager() {
		this.gemmes = new HashMap<String, Gemme>();
	}

	public void addPlayer(Player player) {
		this.gemmes.put(player.getName(), new Gemme());
	}
	
	public void removePlayer(Player player) {
		this.gemmes.remove(player.getName());
	}
	
	public Gemme getGemme(Player player) {
		return this.gemmes.get(player.getName());
	}
	
	public boolean containsPlayer(Player player) {
		return this.gemmes.containsKey(player.getName());
	}
	
	public HashMap<String, Gemme> getGemmes() {
		return gemmes;
	}

	public void setGemmes(HashMap<String, Gemme> gemmes) {
		this.gemmes = gemmes;
	}
}
