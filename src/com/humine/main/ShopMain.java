package com.humine.main;

import org.bukkit.plugin.java.JavaPlugin;

public class ShopMain extends JavaPlugin{

	private static ShopMain instance;
	
	@Override
	public void onEnable() {
		instance = this;
	}

	public static ShopMain getInstance() {
		return instance;
	}
}
