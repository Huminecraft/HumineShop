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
import humine.utils.ParticleScheduler;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.economy.BankHumis;
import humine.utils.economy.BankPixel;
import humine.utils.menus.MenuAccueil;
import humine.utils.menus.MenuIntermediaire;
import humine.utils.shop.HatShop;
import humine.utils.shop.Inventories;
import humine.utils.shop.ParticleShop;
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
	private Shop emperorShop;
	
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
		this.randomShop = new RandomShop("RandomShop");
		this.particleShop = new ParticleShop("Boutique de particule");
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
		
		File dateFolder = new File(this.randomShopFolder, LocalDate.now().toString());
		this.randomShop.load(dateFolder);
		
		this.bankHumis.load(this.bankHumisFile);
		this.bankPixel.load(this.bankPixelFile);
		this.inventories.load(this.inventoriesFolder);
		
		this.emperorShop.load(this.emperorShopFolder);

		ParticleScheduler.startScheduler(this);
		
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
	
	public Shop getEmperorShop() {
		return emperorShop;
	}
}


