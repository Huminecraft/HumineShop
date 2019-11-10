package humine.utils.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import humine.main.MainShop;
import humine.utils.BankItemShop;
import humine.utils.Money;
import humine.utils.Shopper;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.shop.Page;
import humine.utils.shop.RandomShop;
import humine.utils.shop.Shop;

public abstract class ShopLoader {

	private static ObjectInputStream in;
	private static FileConfiguration config;
	
	public static RandomShop loadRandomShop(LocalDate date) throws FileNotFoundException, IOException, ClassNotFoundException {
		File f = new File(MainShop.getInstance().getRandomShopFolder(), date.toString());
		f.mkdirs();
		RandomShop shop = new RandomShop(null, false);
		
		for(int i = 0; i < f.listFiles().length; i++) {
			in = new ObjectInputStream(new FileInputStream(f.listFiles()[i]));
			Page page = (Page) in.readObject();
			shop.addPage(page);
		}
		
		return shop;
	}
	
	public static Shop loadShop(File folder) throws FileNotFoundException, IOException, ClassNotFoundException {
		folder.mkdirs();
		Shop shop = new Shop(null);
		
		for(int i = 0; i < folder.listFiles().length; i++) {
			in = new ObjectInputStream(new FileInputStream(folder.listFiles()[i]));
			Page page = (Page) in.readObject();
			shop.addPage(page);
		}
		
		return shop;
	}
	
	public static Shopper loadShopper(Player player) throws IOException, ClassNotFoundException {
		Shopper shopper = new Shopper(player);
		File f = shopper.getShopperFolder();
		if(!f.exists())
			return null;
		
		File playerConfig = new File(f, "shopper.yml");
		File cosmetiqueFolder = new File(f, "Cosmetiques");
		
		config = YamlConfiguration.loadConfiguration(playerConfig);
		
		
		shopper.setHumis(new Money(config.getInt("Humis"), shopper.getHumis().getName()));
		shopper.setPixel(new Money(config.getInt("Pixel"), shopper.getPixel().getName()));
		
		for(int i = 0; i < cosmetiqueFolder.listFiles().length; i++) {
			File cosmetiqueFile = new File(cosmetiqueFolder, i + ".cosmetique");
			in = new ObjectInputStream(new FileInputStream(cosmetiqueFile));
			Cosmetique cosmetique = (Cosmetique) in.readObject();
			shopper.getCosmetiques().add(cosmetique);
		}
		
		return shopper;
	}
	
	public static BankItemShop loadBankItem(File file) throws IOException {
		file.createNewFile();
		config = YamlConfiguration.loadConfiguration(file);
		BankItemShop bank = new BankItemShop();
		
		if(config.contains("Items")) {
			for(String key : config.getConfigurationSection("Items").getKeys(false))
				bank.put(key, config.getConfigurationSection("Items").getItemStack(key));
		}
		
		return bank;
	}
}
