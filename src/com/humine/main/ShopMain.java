package com.humine.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.humine.commands.GetGemmeCommand;
import com.humine.commands.OpenShopCommand;
import com.humine.events.GemmeOnJoinEvent;
import com.humine.events.ShopClickItemEvent;
import com.humine.events.ShopMoveItemEvent;
import com.humine.utils.money.GemmeManager;
import com.humine.utils.shop.Cosmetique;
import com.humine.utils.shop.Shop;

public class ShopMain extends JavaPlugin{

	private static ShopMain instance;
	private GemmeManager gemmeManager;
	private Shop shop;
	
	private File gemmeFile;
	@Override
	public void onEnable() {
		instance = this;
		this.gemmeManager = new GemmeManager();
		this.shop = new Shop();
		
		for(Player player : Bukkit.getOnlinePlayers())
			this.gemmeManager.addPlayer(player);
		
		ArrayList<Cosmetique> c = new ArrayList<>();
		for(int i = 0; i < 100; i++) {
			c.add(new Cosmetique("itemDebug #" + i, i*10));
			this.shop.addCosmetique(c.get(i));
		}
		
		this.saveDefaultConfig();
		
		initializeGemmes();
		initializeCommands();
		initializeEvents();
	}
	
	@Override
	public void onDisable()
	{
		if(this.gemmeFile.exists()) {
			try
			{
				FileManager.saveGemmes(this.gemmeFile, this.gemmeManager);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void initializeGemmes() {
		this.gemmeFile = new File(this.getDataFolder(), "gemmes.yml");
		if(!this.gemmeFile.exists())
		{
			try{
				this.gemmeFile.createNewFile();
			}
			catch (IOException e)
			{e.printStackTrace();}
		}
		else {
			this.gemmeManager.setGemmes(FileManager.getGemmes(this.gemmeFile));
		}
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(!this.gemmeManager.containsPlayer(player))
				this.gemmeManager.addPlayer(player);
		}
	}
	
	private void initializeCommands() {
		this.getCommand("gemme").setExecutor(new GetGemmeCommand());
		this.getCommand("shop").setExecutor(new OpenShopCommand());
	}
	
	private void initializeEvents() {
		this.getServer().getPluginManager().registerEvents(new ShopClickItemEvent(), this);
		this.getServer().getPluginManager().registerEvents(new ShopMoveItemEvent(), this);
		this.getServer().getPluginManager().registerEvents(new GemmeOnJoinEvent(), this);
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
