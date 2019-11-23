package humine.main;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import humine.commands.AddMoney;
import humine.commands.CosmetiqueLoad;
import humine.commands.CreateCommandParticle;
import humine.commands.CreateCustomHeadCosmetique;
import humine.commands.CreateMaterialHatCosmetique;
import humine.commands.CreateParticleCosmetique;
import humine.commands.CreateTemporaryCustomHeadCosmetique;
import humine.commands.CreateTemporaryMaterialHatCosmetique;
import humine.commands.CreateTemporaryParticleCosmetique;
import humine.commands.HelpList;
import humine.commands.OpenShopCommand;
import humine.commands.RemoveCosmetique;
import humine.commands.RemoveMoney;
import humine.commands.ShowMoney;
import humine.events.BlockMoveCosmetique;
import humine.events.ClickMaterialInventory;
import humine.events.CreateShopperEvent;
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
import humine.utils.ParticleScheduler;
import humine.utils.Shopper;
import humine.utils.ShopperBank;
import humine.utils.shop.RandomShop;
import humine.utils.shop.Shop;

/**
 * Classe principal de HumineShop
 * @author miza
 */
public class MainShop extends JavaPlugin {

	private static MainShop instance;
	
	private Shop shop;
	private RandomShop randomShop;
	private Shop emperorShop;
	
	private static ShopperBank shopperBank;
	private BankItemShop itemShopBank;
	
	public static int CosmetiqueID;
	
	private final File randomShopFolder = new File(getDataFolder(), "RandomShop");
	private final File shopFolder = new File(getDataFolder(), "Shop");
	private final File emperorShopFolder = new File(getDataFolder(), "EmperorShop");
	
	private final File shopperFolder = new File(getDataFolder(), "Shoppers");
	private final File bankItemFile = new File(getDataFolder(), "ItemBank.yml");
	
	private final File cosmetiqueFile = new File(getDataFolder(), "Cosmetiques.hs");
	
	@Override
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		saveConfig();

		ParticleScheduler.startScheduler(this);
		
		initializeFiles();
		initializeVariables();
		initializeCommands();
		initializeEvents();
		
		getServer().getLogger().log(Level.INFO, "=================================");
		getServer().getLogger().log(Level.INFO, "HumineShop est lance et tu as le message");
		getServer().getLogger().log(Level.INFO, "=================================");
		
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run()
			{
				if(randomShop.getCurrentDate().isBefore(LocalDate.now()))
					randomShop.update();
			}
		}, 0L, (60 * 20));
		
		
	}
	
	private void initializeVariables() {
		shopperBank = new ShopperBank();
		if(getConfig().contains("cosmetique"))
			CosmetiqueID = getConfig().getInt("cosmetique");
		else
			CosmetiqueID = 0;
		
		try {
			this.itemShopBank = humine.utils.files.ShopLoader.loadBankItem(this.bankItemFile);
			this.shop = humine.utils.files.ShopLoader.loadShop(this.shopFolder);
			this.emperorShop = humine.utils.files.ShopLoader.loadShop(this.emperorShopFolder);
			this.randomShop = humine.utils.files.ShopLoader.loadRandomShop(LocalDate.now());
			
			for(Player player : getServer().getOnlinePlayers()) {
				Shopper shopper = humine.utils.files.ShopLoader.loadShopper(player);
				if(shopper == null)
					shopper = new Shopper(player);
				
				getServer().getLogger().log(Level.INFO, shopper.toString());
				shopperBank.addShopper(shopper);
			}
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if(this.itemShopBank == null)
			this.itemShopBank = new BankItemShop();
		if(this.shop == null)
			this.shop = new Shop(null);
		if(this.emperorShop == null)
			this.emperorShop = new Shop(null);
		if(this.randomShop == null)
			this.randomShop = new RandomShop(null);
	}

	@Override
	public void onDisable()
	{
		try {
			getConfig().set("cosmetique", CosmetiqueID);
			saveConfig();
			
			humine.utils.files.ShopSaver.saveShop(this.shop, this.shopFolder);
			humine.utils.files.ShopSaver.saveShop(this.emperorShop, this.emperorShopFolder);
			humine.utils.files.ShopSaver.saveRandomShop(this.randomShop, LocalDate.now());
			humine.utils.files.ShopSaver.saveBankItem(this.itemShopBank, this.bankItemFile);
			
			for(Shopper shopper : getShopperManager().getOnlyShoppers())
				humine.utils.files.ShopSaver.saveShopper(shopper);
			
		} catch (IOException e1) {
			getServer().getLogger().log(Level.SEVERE, "Probleme sauvegarde");
			e1.printStackTrace();
		}
	}

	private void initializeFiles() {
		if(!randomShopFolder.exists())
			randomShopFolder.mkdirs();
		
		if(!cosmetiqueFile.exists()) {
			try {
				cosmetiqueFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(!shopFolder.exists())
			shopFolder.mkdirs();
		
		if(!emperorShopFolder.exists())
			emperorShopFolder.mkdirs();
		
		if(!shopperFolder.exists())
			shopperFolder.mkdirs();
		
		if(!bankItemFile.exists()) {
			try {
				bankItemFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void initializeCommands() {
		this.getCommand("money").setExecutor(new ShowMoney());
		this.getCommand("shop").setExecutor(new OpenShopCommand());
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
		
		this.getCommand("cosmetiqueload").setExecutor(new CosmetiqueLoad());
		this.getCommand("cosmetiquecommand").setExecutor(new CreateCommandParticle());
	}
	
	private void initializeEvents() {
		this.getServer().getPluginManager().registerEvents(new InitializeEvents(), this);
		
		this.getServer().getPluginManager().registerEvents(new CreateShopperEvent(), this);
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

	public RandomShop getRandomShop() {
		return randomShop;
	}

	public void setRandomShop(RandomShop randomShop) {
		this.randomShop = randomShop;
	}

	public File getRandomShopFolder() {
		return randomShopFolder;
	}
	
	public Shop getEmperorShop() {
		return emperorShop;
	}
	
	public static ShopperBank getShopperManager()
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
	
	public File getCosmetiqueFile() {
		return cosmetiqueFile;
	}
}


