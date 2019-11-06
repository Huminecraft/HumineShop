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
import humine.commands.OpenShop;
import humine.commands.RemoveCosmetique;
import humine.commands.RemoveMoney;
import humine.commands.ShowMoney;
import humine.events.BlockMoveCosmetique;
import humine.events.ClickMaterialInventory;
import humine.events.PlayerCustomHeadEvent;
import humine.events.PlayerQuit;
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
import humine.events.presentation.ClickPlusButton;
import humine.events.presentation.ClickTakeAllBlocksButton;
import humine.events.presentation.ClickTakeAllButton;
import humine.events.presentation.ClickTakeAllInventoryButton;
import humine.events.shops.RandomShopEvent;
import humine.events.shops.ShopEvent;
import humine.events.stocks.ClickDisableButton;
import humine.utils.BankItemShop;
import humine.utils.CustomHeadBlockInfo;
import humine.utils.ParticleScheduler;
import humine.utils.ShopperBank;
import humine.utils.menus.MenuAccueil;
import humine.utils.menus.MenuIntermediaire;
import humine.utils.shop.RandomShop;
import humine.utils.shop.Shop;

/**
 * Classe principal de HumineShop
 * @author miza
 */
public class MainShop extends JavaPlugin {

	private static MainShop instance;
	
	private MenuAccueil menuAccueil;
	private MenuIntermediaire menuIntermediaire;
	
	private Shop shop;
	private RandomShop randomShop;
	private Shop emperorShop;
	
	private HashMap<String, CustomHeadBlockInfo> playerCustomHeadList;
	
	private ShopperBank shopperBank;
	private BankItemShop itemShopBank;
	
	public static int CosmetiqueID;
	
	private final File shopFolder = new File(getDataFolder(), "Shop");
	private final File emperorShopFolder = new File(getDataFolder(), "EmperorShop");
	private final File randomShopFolder = new File(getDataFolder(), "RandomShop");
	private final File bankHumisFile = new File(getDataFolder(), "BankHumis.yml");
	private final File bankPixelFile = new File(getDataFolder(), "BankPixel.yml");
	private final File inventoriesFolder = new File(getDataFolder(), "Inventories");
	private final File IDFile = new File(getDataFolder(), "ID.yml");
	private final File CustomHeadBlockFile = new File(getDataFolder(), "CustomHeadBlockInfos");
	private final File shopperFolder = new File(getDataFolder(), "Shoppers");
	
	@Override
	public void onEnable() {
		instance = this;
		saveDefaultConfig();

		if(!CustomHeadBlockFile.exists())
			CustomHeadBlockFile.mkdirs();
		
		if(!shopperFolder.exists())
			shopperFolder.mkdirs();
		
		this.shopperBank = new ShopperBank();
		this.itemShopBank = new BankItemShop();
		CosmetiqueID = 0;
		
		this.shop = new Shop(null);
		this.randomShop = new RandomShop(null, false);
		this.emperorShop = new Shop(null, false);
		
		this.playerCustomHeadList = new HashMap<String, CustomHeadBlockInfo>();
		
		this.randomShop.update();
		
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
		
		for(File f : this.CustomHeadBlockFile.listFiles()) {
			CustomHeadBlockInfo info = CustomHeadBlockInfo.load(f);
			if(info != null)
				this.playerCustomHeadList.put(info.getOwner(), info);
		}
		
	}
	
	@Override
	public void onDisable()
	{
		this.shop.save(this.shopFolder);
		
		File dateFolder = new File(this.randomShopFolder, LocalDate.now().toString());
		this.randomShop.save(dateFolder);
		this.inventories.save(this.inventoriesFolder);
		this.emperorShop.save(this.emperorShopFolder);
		
		for(CustomHeadBlockInfo chb : this.playerCustomHeadList.values()) {
			File f = new File(this.CustomHeadBlockFile, chb.getOwner() + ".yml");
			try
			{
				CustomHeadBlockInfo.save(f, chb);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
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
		config.set("cosmetique", CosmetiqueID);

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
		this.getServer().getPluginManager().registerEvents(new InitializeEvents(), this);
		
		this.getServer().getPluginManager().registerEvents(new ShopEvent(), this);
		this.getServer().getPluginManager().registerEvents(new RandomShopEvent(), this);
		
		this.getServer().getPluginManager().registerEvents(new BlockMoveCosmetique(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerCustomHeadEvent(), this);
		
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
		
		this.getServer().getPluginManager().registerEvents(new ClickHumisBuyButton(), this);
		this.getServer().getPluginManager().registerEvents(new humine.events.presentation.ClickLinkButton(), this);
		this.getServer().getPluginManager().registerEvents(new ClickPixelBuyButton(), this);
		this.getServer().getPluginManager().registerEvents(new humine.events.presentation.ClickQuitButton(), this);
		this.getServer().getPluginManager().registerEvents(new ClickPlusButton(), this);
		this.getServer().getPluginManager().registerEvents(new ClickTakeAllButton(), this);
		this.getServer().getPluginManager().registerEvents(new ClickTakeAllBlocksButton(), this);
		this.getServer().getPluginManager().registerEvents(new ClickTakeAllInventoryButton(), this);
		
		this.getServer().getPluginManager().registerEvents(new humine.events.stocks.ClickCosmetiqueButton(), this);
		this.getServer().getPluginManager().registerEvents(new ClickDisableButton(), this);
		this.getServer().getPluginManager().registerEvents(new humine.events.stocks.ClickNextButton(), this);
		this.getServer().getPluginManager().registerEvents(new humine.events.stocks.ClickPreviousButton(), this);
		this.getServer().getPluginManager().registerEvents(new humine.events.stocks.ClickQuitButton(), this);
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
	
	public File getCustomHeadBlockFile()
	{
		return CustomHeadBlockFile;
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
	
	public Shop getEmperorShop() {
		return emperorShop;
	}
	
	public HashMap<String, CustomHeadBlockInfo> getPlayerCustomHeadList()
	{
		return playerCustomHeadList;
	}
	
	public ShopperBank getShopperBank()
	{
		return shopperBank;
	}
	
	public BankItemShop getItemShopBank()
	{
		return itemShopBank;
	}
	
	public File getShopperFolder()
	{
		return shopperFolder;
	}
}


