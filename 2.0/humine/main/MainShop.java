package humine.main;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import humine.commands.CreateParticleCosmetique;
import humine.commands.CreateTemporaryParticleCosmetique;
import humine.commands.HelpList;
import humine.commands.Money;
import humine.commands.OpenShop;
import humine.commands.RemoveCosmetique;
import humine.events.BlockMoveCosmetique;
import humine.events.ClickPresentationCosmetique;
import humine.events.CreateBankAccount;
import humine.events.CreateStockAccount;
import humine.events.shop.ClickCosmetique;
import humine.events.shop.ClickRandomShop;
import humine.events.shop.ClickStock;
import humine.utils.Cosmetique;
import humine.utils.Inventories;
import humine.utils.Page;
import humine.utils.Shop;
import humine.utils.Utils;
import humine.utils.economy.Bank;
import humine.utils.randomshop.RandomShop;

public class MainShop extends JavaPlugin {

	private static MainShop instance;
	private Shop shop;
	private RandomShop randomShop;
	private Bank bank;
	private Inventories inventories;

	private final File shopFolder = new File(getDataFolder(), "Shop");
	private final File randomShopFolder = new File(getDataFolder(), "RandomShop");
	private final File bankFile = new File(getDataFolder(), "Bank.yml");
	private final File inventoriesFolder = new File(getDataFolder(), "Inventories");
	private final File IDFile = new File(getDataFolder(), "ID.yml");
	
	@Override
	public void onEnable() {
		instance = this;
		saveDefaultConfig();

		this.shop = new Shop("Shop");
		this.randomShop = new RandomShop("RandomShop");
		this.bank = new Bank("Humins");
		this.inventories = new Inventories();

		this.shop.load(this.shopFolder);
		this.randomShop.load(this.randomShopFolder);
		this.bank.load(this.bankFile);
		this.inventories.load(this.inventoriesFolder);

		Utils.schedulerBuyCosmetique(this);
		Utils.schedulerTryCosmetique(this);
		
		initializeCommands();
		initializeEvents();
		
	}
	
	@Override
	public void onDisable()
	{
		this.shop.save(this.shopFolder);
		this.randomShop.save(this.randomShopFolder);
		this.bank.save(this.bankFile);
		this.inventories.save(this.inventoriesFolder);
		
		if(!this.IDFile.exists()) {
			try
			{
				this.IDFile.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		FileConfiguration config = YamlConfiguration.loadConfiguration(this.IDFile);
		config.set("shop", Shop.getNumId());
		config.set("cosmetique", Cosmetique.getNumId());
		config.set("inventories", Inventories.getNumId());
		config.set("page", Page.getNumId());
		
		try
		{
			config.save(this.IDFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void initializeCommands() {
		this.getCommand("money").setExecutor(new Money());
		this.getCommand("shop").setExecutor(new OpenShop());
		this.getCommand("ccp").setExecutor(new CreateParticleCosmetique());
		this.getCommand("chelp").setExecutor(new HelpList());
		this.getCommand("tccp").setExecutor(new CreateTemporaryParticleCosmetique());
		this.getCommand("rc").setExecutor(new RemoveCosmetique());
	}
	
	private void initializeEvents() {
		this.getServer().getPluginManager().registerEvents(new BlockMoveCosmetique(), this);
		this.getServer().getPluginManager().registerEvents(new ClickCosmetique(), this);
		this.getServer().getPluginManager().registerEvents(new ClickPresentationCosmetique(), this);
		this.getServer().getPluginManager().registerEvents(new CreateBankAccount(), this);
		this.getServer().getPluginManager().registerEvents(new CreateStockAccount(), this);
		this.getServer().getPluginManager().registerEvents(new ClickStock(), this);
		this.getServer().getPluginManager().registerEvents(new ClickRandomShop(), this);
	}

	public static MainShop getInstance() {
		return instance;
	}

	public static void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.AQUA + "[Shop] " + ChatColor.RESET + message);
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Inventories getInventories() {
		return inventories;
	}

	public void setInventories(Inventories inventories) {
		this.inventories = inventories;
	}

	public File getShopFolder()
	{
		return shopFolder;
	}

	public File getBankFile()
	{
		return bankFile;
	}

	public File getInventoriesFolder()
	{
		return inventoriesFolder;
	}

	public File getIDFile()
	{
		return IDFile;
	}

	public RandomShop getRandomShop() {
		return randomShop;
	}

	public void setRandomShop(RandomShop randomShop) {
		this.randomShop = randomShop;
	}

	public File getRandomShopFolder() {
		return randomShopFolder;
	}
}
