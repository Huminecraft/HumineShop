package humine.main;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import humine.commands.CreateParticleCosmetique;
import humine.commands.Money;
import humine.commands.OpenShop;
import humine.events.BlockMoveCosmetique;
import humine.events.ClickCosmetique;
import humine.events.CreateBankAccount;
import humine.events.CreateStockAccount;
import humine.utils.Inventories;
import humine.utils.Shop;
import humine.utils.economy.Bank;

public class MainShop extends JavaPlugin {

	private static MainShop instance;
	private Shop shop;
	private Bank bank;
	private Inventories inventories;

	private final File shopFolder = new File(getDataFolder(), "Shop");
	private final File bankFile = new File(getDataFolder(), "Bank.yml");
	private final File inventoriesFolder = new File(getDataFolder(), "Inventories");

	@Override
	public void onEnable() {
		instance = this;
		saveDefaultConfig();

		this.shop = new Shop("Shop");
		this.bank = new Bank("Humins");
		this.inventories = new Inventories();

		this.shop.load(this.shopFolder);
		this.bank.load(this.bankFile);
		this.inventories.load(this.inventoriesFolder);

		initializeCommands();
		initializeEvents();
	}

	private void initializeCommands() {
		this.getCommand("money").setExecutor(new Money());
		this.getCommand("shop").setExecutor(new OpenShop());
		this.getCommand("ccp").setExecutor(new CreateParticleCosmetique());
	}
	
	private void initializeEvents() {
		this.getServer().getPluginManager().registerEvents(new BlockMoveCosmetique(), this);
		this.getServer().getPluginManager().registerEvents(new ClickCosmetique(), this);
		this.getServer().getPluginManager().registerEvents(new CreateBankAccount(), this);
		this.getServer().getPluginManager().registerEvents(new CreateStockAccount(), this);
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
}
