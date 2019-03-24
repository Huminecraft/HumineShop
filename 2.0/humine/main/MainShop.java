package humine.main;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import humine.commands.CreateMaterialHatCosmetique;
import humine.commands.CreateParticleCosmetique;
import humine.commands.CreateTemporaryMaterialHatCosmetique;
import humine.commands.CreateTemporaryParticleCosmetique;
import humine.commands.HelpList;
import humine.commands.Money;
import humine.commands.OpenShop;
import humine.commands.RemoveCosmetique;
import humine.events.BlockMoveCosmetique;
import humine.events.ClickPresentationCosmetique;
import humine.events.CreateBankAccount;
import humine.events.CreateStockAccount;
import humine.events.PlayerQuit;
import humine.events.randomshop.ClickArrowButton;
import humine.events.randomshop.ClickQuitButton;
import humine.events.randomshop.QuitRandomShop;
import humine.events.shop.ClickCosmetique;
import humine.events.shop.ClickRandomShop;
import humine.events.shop.ClickStock;
import humine.events.shop.QuitShop;
import humine.events.stock.ClickDisableButton;
import humine.events.stock.QuitStock;
import humine.utils.Cosmetique;
import humine.utils.Inventories;
import humine.utils.Page;
import humine.utils.Shop;
import humine.utils.Utils;
import humine.utils.economy.BankHumis;
import humine.utils.economy.BankPixel;
import humine.utils.randomshop.RandomShop;

public class MainShop extends JavaPlugin {

	private static MainShop instance;
	private Shop shop;
	private RandomShop randomShop;
	private BankHumis bankHumis;
	private BankPixel bankPixel;
	private Inventories inventories;

	private final File shopFolder = new File(getDataFolder(), "Shop");
	private final File randomShopFolder = new File(getDataFolder(), "RandomShop");
	private final File bankHumisFile = new File(getDataFolder(), "BankHumis.yml");
	private final File bankPixelFile = new File(getDataFolder(), "BankPixel.yml");
	private final File inventoriesFolder = new File(getDataFolder(), "Inventories");
	private final File IDFile = new File(getDataFolder(), "ID.yml");
	
	@Override
	public void onEnable() {
		instance = this;
		saveDefaultConfig();

		this.shop = new Shop("Shop");
		this.randomShop = new RandomShop("RandomShop");
		this.bankHumis = new BankHumis("Humis");
		this.bankPixel = new BankPixel("Pixel");
		this.inventories = new Inventories();

		this.shop.load(this.shopFolder);
		
		File dateFolder = new File(this.randomShopFolder, LocalDate.now().toString());
		this.randomShop.load(dateFolder);
		this.bankHumis.load(this.bankHumisFile);
		this.bankPixel.load(this.bankPixelFile);
		this.inventories.load(this.inventoriesFolder);

		Utils.schedulerBuyCosmetique(this);
		Utils.schedulerTryCosmetique(this);
		
		initializeCommands();
		initializeEvents();
		
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				if(LocalDate.now().isAfter(randomShop.getCurrentDate())) {
					randomShop.update();
				}
			}
		}, 0L, (60 * 20));
		
	}
	
	@Override
	public void onDisable()
	{
		this.shop.save(this.shopFolder);
		
		File dateFolder = new File(this.randomShopFolder, LocalDate.now().toString());
		this.randomShop.save(dateFolder);
		this.bankHumis.save(this.bankHumisFile);
		this.bankPixel.save(this.bankPixelFile);
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
		this.getCommand("ccmh").setExecutor(new CreateMaterialHatCosmetique());
		this.getCommand("chelp").setExecutor(new HelpList());
		this.getCommand("tccp").setExecutor(new CreateTemporaryParticleCosmetique());
		this.getCommand("tccmh").setExecutor(new CreateTemporaryMaterialHatCosmetique());
		this.getCommand("rc").setExecutor(new RemoveCosmetique());
	}
	
	private void initializeEvents() {
		this.getServer().getPluginManager().registerEvents(new ClickArrowButton(), this);
		this.getServer().getPluginManager().registerEvents(new humine.events.randomshop.ClickCosmetique(), this);
		this.getServer().getPluginManager().registerEvents(new ClickQuitButton(), this);
		this.getServer().getPluginManager().registerEvents(new QuitRandomShop(), this);
		
		this.getServer().getPluginManager().registerEvents(new humine.events.shop.ClickArrowButton(), this);
		this.getServer().getPluginManager().registerEvents(new ClickCosmetique(), this);
		this.getServer().getPluginManager().registerEvents(new ClickRandomShop(), this);
		this.getServer().getPluginManager().registerEvents(new ClickStock(), this);
		this.getServer().getPluginManager().registerEvents(new QuitShop(), this);
		
		this.getServer().getPluginManager().registerEvents(new BlockMoveCosmetique(), this);
		this.getServer().getPluginManager().registerEvents(new ClickPresentationCosmetique(), this);
		this.getServer().getPluginManager().registerEvents(new CreateBankAccount(), this);
		this.getServer().getPluginManager().registerEvents(new CreateStockAccount(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
		
		this.getServer().getPluginManager().registerEvents(new humine.events.stock.ClickArrowButton(), this);
		this.getServer().getPluginManager().registerEvents(new humine.events.stock.ClickCosmetique(), this);
		this.getServer().getPluginManager().registerEvents(new QuitStock(), this);
		this.getServer().getPluginManager().registerEvents(new humine.events.stock.ClickQuitButton(), this);
		this.getServer().getPluginManager().registerEvents(new ClickDisableButton(), this);
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

	public BankHumis getBankHumis() {
		return bankHumis;
	}

	public void setBankHumis(BankHumis bankHumis) {
		this.bankHumis = bankHumis;
	}

	public BankPixel getBankPixel()
	{
		return bankPixel;
	}
	
	public void setBankPixel(BankPixel bankPixel)
	{
		this.bankPixel = bankPixel;
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

	public File getBankHumisFile()
	{
		return bankHumisFile;
	}

	public File getBankPixelFile()
	{
		return bankPixelFile;
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
