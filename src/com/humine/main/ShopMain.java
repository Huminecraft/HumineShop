package com.humine.main;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.humine.commands.GetGemmeCommand;
import com.humine.commands.OpenShopCommand;
import com.humine.utils.money.GemmeManager;
import com.humine.utils.shop.Shop;

public class ShopMain extends JavaPlugin{

	private static ShopMain instance;
	private GemmeManager gemmeManager;
	private Shop shop;
	
	
	@Override
	public void onEnable() {
		instance = this;
		this.gemmeManager = new GemmeManager();
		this.shop = new Shop();
		
		initializeCommands();
	}
	
	void initializeCommands() {
		this.getCommand("gemme").setExecutor(new GetGemmeCommand());
		this.getCommand("shop").setExecutor(new OpenShopCommand());
	}
	

	public static ShopMain getInstance() {
		return instance;
	}

	public GemmeManager getGemmeManager() {
		return gemmeManager;
	}

	public void setGemmeManager(GemmeManager gemmeManager) {
		this.gemmeManager = gemmeManager;
	}
	
	public static void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.AQUA + "[HumineShop] " + ChatColor.RESET + message);
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
}
