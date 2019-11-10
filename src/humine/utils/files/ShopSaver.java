package humine.utils.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.Map.Entry;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import humine.main.MainShop;
import humine.utils.BankItemShop;
import humine.utils.Shopper;
import humine.utils.shop.RandomShop;
import humine.utils.shop.Shop;

public abstract class ShopSaver {

	private static ObjectOutputStream out;
	private static FileConfiguration config;
	
	public static void saveRandomShop(RandomShop shop, LocalDate date) throws FileNotFoundException, IOException {
		if(shop == null) return;
		
		File f = new File(MainShop.getInstance().getRandomShopFolder(), date.toString());
		f.mkdirs();
		
		for(int i = 0; i < shop.getPages().size(); i++) {
			File pageFile = new File(f, i + ".page");
			pageFile.createNewFile();
			out = new ObjectOutputStream(new FileOutputStream(pageFile));
			out.writeObject(shop.getPage(i));
		}
	}
	
	public static void saveShop(Shop shop, File folder) throws IOException {
		if(shop == null) return;
		
		folder.mkdirs();
		for(int i = 0; i < shop.getPages().size(); i++) {
			File pageFile = new File(folder, i + ".page");
			pageFile.createNewFile();
			out = new ObjectOutputStream(new FileOutputStream(pageFile));
			out.writeObject(shop.getPage(i));
		}
	}
	
	public static void saveShopper(Shopper shopper) throws IOException {
		File f = shopper.getShopperFolder();
		File playerConfig = new File(f, "shopper.yml");
		File cosmetiqueFolder = new File(f, "Cosmetiques");
		f.mkdirs();
		playerConfig.createNewFile();
		cosmetiqueFolder.mkdirs();
		
		config = YamlConfiguration.loadConfiguration(playerConfig);
		config.set("Player", shopper.getPlayer().getName());
		config.set("Humis", shopper.getHumis().getAmount());
		config.set("Pixel", shopper.getPixel().getAmount());
		config.save(playerConfig);
		
		for(int i = 0; i < shopper.getCosmetiques().size(); i++) {
			File cosmetiqueFile = new File(cosmetiqueFolder, i + ".cosmetique");
			cosmetiqueFile.createNewFile();
			out = new ObjectOutputStream(new FileOutputStream(cosmetiqueFile));
			out.writeObject(shopper.getCosmetiques().get(i));
		}
	}
	
	public static void saveBankItem(BankItemShop bank, File file) throws IOException {
		file.createNewFile();
		
		config = YamlConfiguration.loadConfiguration(file);
		config.createSection("Items");
		for(Entry<String, ItemStack> entry : bank.getItemShops().entrySet()) {
			config.getConfigurationSection("Items").set(entry.getKey(), entry.getValue());
		}
		config.save(file);
	}
}
