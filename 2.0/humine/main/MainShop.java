package humine.main;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import humine.commands.AddMoney;
import humine.commands.CreateCustomHeadCosmetique;
import humine.commands.CreateMaterialHatCosmetique;
import humine.commands.CreateParticleCosmetique;
import humine.commands.CreateTemporaryCustomHeadCosmetique;
import humine.commands.CreateTemporaryMaterialHatCosmetique;
import humine.commands.CreateTemporaryParticleCosmetique;
import humine.commands.HelpList;
import humine.commands.ShowMoney;
import humine.commands.OpenShop;
import humine.commands.RemoveCosmetique;
import humine.commands.RemoveMoney;
import humine.events.BlockMoveCosmetique;
import humine.events.ClickPresentationCosmetique;
import humine.events.CreateBankAccount;
import humine.events.CreateStockAccount;
import humine.events.ExitInventory;
import humine.events.PlayerQuit;
import humine.events.inventory.ClickMaterialInventory;
import humine.events.menuaccueil.ClickCustomHeadButton;
import humine.events.menuaccueil.ClickHatStockButton;
import humine.events.menuaccueil.ClickLinkButton;
import humine.events.menuaccueil.ClickParticleStockButton;
import humine.events.menuaccueil.ClickPermanentShopButton;
import humine.events.menuaccueil.ClickQuitButton;
import humine.events.menuaccueil.ClickTemporaryShopButton;
import humine.events.menuintermediaire.ClickEmpereurButton;
import humine.events.menuintermediaire.ClickHatShopButton;
import humine.events.menuintermediaire.ClickParticleShopButton;
import humine.events.presentation.ClickHumisBuyButton;
import humine.events.presentation.ClickPixelBuyButton;
import humine.events.shops.ClickCosmetiqueButton;
import humine.events.shops.ClickNextButton;
import humine.events.shops.ClickPreviousButton;
import humine.events.stocks.ClickDisableButton;
import humine.utils.ParticleScheduler;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.economy.BankHumis;
import humine.utils.economy.BankPixel;
import humine.utils.menus.MenuAccueil;
import humine.utils.menus.MenuIntermediaire;
import humine.utils.shop.CustomHeadShop;
import humine.utils.shop.CustomHeadStock;
import humine.utils.shop.HatShop;
import humine.utils.shop.HatStock;
import humine.utils.shop.Inventories;
import humine.utils.shop.ParticleShop;
import humine.utils.shop.ParticleStock;
import humine.utils.shop.RandomShop;
import humine.utils.shop.Shop;

public class MainShop extends JavaPlugin {

	private static MainShop instance;
	
	private BankHumis bankHumis;
	private BankPixel bankPixel;
	
	private Inventories inventories;

	private MenuAccueil menuAccueil;
	private MenuIntermediaire menuIntermediaire;
	
	private Shop shop;
	private RandomShop randomShop;
	private ParticleShop particleShop;
	private HatShop hatShop;
	private CustomHeadShop customHeadShop;
	private Shop emperorShop;
	
	private HashMap<String, ParticleStock> particleStockList;
	private HashMap<String, HatStock> HatStockList;
	private HashMap<String, CustomHeadStock> customHeadStockList;
	
	private final File shopFolder = new File(getDataFolder(), "Shop");
	private final File emperorShopFolder = new File(getDataFolder(), "EmperorShop");
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
		this.randomShop = new RandomShop("RandomShop", false);
		this.particleShop = new ParticleShop("Boutique de particule");
		this.customHeadShop = new CustomHeadShop("Boutique de tete personnalisee");
		this.hatShop = new HatShop("Boutique de Chapeau");
		this.emperorShop = new Shop("Boutique Empereur", false);
		
		this.bankHumis = new BankHumis("Humis");
		this.bankPixel = new BankPixel("Pixel");
		this.inventories = new Inventories();

		this.menuAccueil = new MenuAccueil();
		this.menuIntermediaire = new MenuIntermediaire();
		
		this.shop.load(this.shopFolder);
		this.particleShop.filter(this.shop);
		this.hatShop.filter(this.shop);
		this.customHeadShop.filter(this.shop);
		
		this.particleStockList = new HashMap<String, ParticleStock>();
		this.HatStockList = new HashMap<String, HatStock>();
		this.customHeadStockList = new HashMap<String, CustomHeadStock>();
		
		this.randomShop.update();
		
		this.bankHumis.load(this.bankHumisFile);
		this.bankPixel.load(this.bankPixelFile);
		this.inventories.load(this.inventoriesFolder);
		
		this.emperorShop.load(this.emperorShopFolder);

		ParticleScheduler.startScheduler(this);
		
		initializeCommands();
		initializeEvents();
		
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run()
			{
				if(randomShop.getCurrentDate().isBefore(LocalDate.now()))
					randomShop.update();
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
		this.emperorShop.save(this.emperorShopFolder);
		
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
		config.set("cosmetique", Cosmetique.getNumId());

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
		this.getCommand("money").setExecutor(new ShowMoney());
		this.getCommand("shop").setExecutor(new OpenShop());
		this.getCommand("ccp").setExecutor(new CreateParticleCosmetique());
		this.getCommand("ccmh").setExecutor(new CreateMaterialHatCosmetique());
		this.getCommand("chelp").setExecutor(new HelpList());
		this.getCommand("tccp").setExecutor(new CreateTemporaryParticleCosmetique());
		this.getCommand("tccmh").setExecutor(new CreateTemporaryMaterialHatCosmetique());
		this.getCommand("rc").setExecutor(new RemoveCosmetique());
		this.getCommand("store").setExecutor(new AddMoney());
		this.getCommand("storedelete").setExecutor(new RemoveMoney());
		this.getCommand("ccch").setExecutor(new CreateCustomHeadCosmetique());
		this.getCommand("tccch").setExecutor(new CreateTemporaryCustomHeadCosmetique());
	}
	
	private void initializeEvents() {
		this.getServer().getPluginManager().registerEvents(new BlockMoveCosmetique(), this);
		this.getServer().getPluginManager().registerEvents(new ClickPresentationCosmetique(), this);
		this.getServer().getPluginManager().registerEvents(new CreateBankAccount(), this);
		this.getServer().getPluginManager().registerEvents(new CreateStockAccount(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
		this.getServer().getPluginManager().registerEvents(new ExitInventory(), this);
		
		this.getServer().getPluginManager().registerEvents(new ClickMaterialInventory(), this);
		
		this.getServer().getPluginManager().registerEvents(new ClickCustomHeadButton(), this);
		this.getServer().getPluginManager().registerEvents(new ClickHatStockButton(), this);
		this.getServer().getPluginManager().registerEvents(new ClickLinkButton(), this);
		this.getServer().getPluginManager().registerEvents(new ClickParticleStockButton(), this);
		this.getServer().getPluginManager().registerEvents(new ClickPermanentShopButton(), this);
		this.getServer().getPluginManager().registerEvents(new ClickQuitButton(), this);
		this.getServer().getPluginManager().registerEvents(new ClickTemporaryShopButton(), this);
		
		this.getServer().getPluginManager().registerEvents(new humine.events.menuintermediaire.ClickCustomHeadButton(), this);
		this.getServer().getPluginManager().registerEvents(new ClickEmpereurButton(), this);
		this.getServer().getPluginManager().registerEvents(new ClickHatShopButton(), this);
		this.getServer().getPluginManager().registerEvents(new ClickParticleShopButton(), this);
		this.getServer().getPluginManager().registerEvents(new humine.events.menuintermediaire.ClickQuitButton(), this);
		
		this.getServer().getPluginManager().registerEvents(new ClickCosmetiqueButton(), this);
		this.getServer().getPluginManager().registerEvents(new ClickNextButton(), this);
		this.getServer().getPluginManager().registerEvents(new ClickPreviousButton(), this);
		this.getServer().getPluginManager().registerEvents(new humine.events.shops.ClickQuitButton(), this);
		
		this.getServer().getPluginManager().registerEvents(new ClickHumisBuyButton(), this);
		this.getServer().getPluginManager().registerEvents(new humine.events.presentation.ClickLinkButton(), this);
		this.getServer().getPluginManager().registerEvents(new ClickPixelBuyButton(), this);
		this.getServer().getPluginManager().registerEvents(new humine.events.presentation.ClickQuitButton(), this);
		
		this.getServer().getPluginManager().registerEvents(new humine.events.stocks.ClickCosmetiqueButton(), this);
		this.getServer().getPluginManager().registerEvents(new ClickDisableButton(), this);
		this.getServer().getPluginManager().registerEvents(new humine.events.stocks.ClickNextButton(), this);
		this.getServer().getPluginManager().registerEvents(new humine.events.stocks.ClickPreviousButton(), this);
		this.getServer().getPluginManager().registerEvents(new humine.events.stocks.ClickQuitButton(), this);
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
	
	public MenuAccueil getMenuAccueil() {
		return menuAccueil;
	}
	
	public MenuIntermediaire getMenuIntermediaire() {
		return menuIntermediaire;
	}
	
	public ParticleShop getParticleShop() {
		return particleShop;
	}
	
	public HatShop getHatShop() {
		return hatShop;
	}
	
	public CustomHeadShop getCustomHeadShop() {
		return customHeadShop;
	}
	
	public Shop getEmperorShop() {
		return emperorShop;
	}
	
	public HashMap<String, ParticleStock> getParticleStockList()
	{
		return particleStockList;
	}
	
	public HashMap<String, HatStock> getHatStockList()
	{
		return HatStockList;
	}
	
	public HashMap<String, CustomHeadStock> getCustomHeadStockList() {
		return customHeadStockList;
	}
}


