package humine.utils.cosmetiques;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import humine.main.MainShop;
import humine.utils.Prestige;
import humine.utils.cosmetiques.temporary.TemporaryMaterialHatCosmetique;
import humine.utils.cosmetiques.temporary.TemporaryParticleCosmetique;

public abstract class Cosmetique {

	private String id;
	private String name;
	private ItemStack itemShop;
	private Prestige prestige;
	
	private int humisPrice;
	private int PixelPrice;
	
	public static int NumId = 0;
	
	static {
		File file = MainShop.getInstance().getIDFile();
		if(file.exists()) {
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			if(config.contains("cosmetique")) {
				NumId = config.getInt("cosmetique");
			}
			else {
				config.set("cosmetique", NumId);
				try {
					config.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * Classe abstraite permettant la création des cosmetiques
	 * de HumineShop
	 * @param name le nom du cosmetique
	 * @param itemShop Le material servant a representer le cosmetique dans le shop
	 */
	public Cosmetique(String name, ItemStack itemShop, int humisPrice, int pixelPrice, Prestige prestige) {
		this.name = name;
		this.itemShop = itemShop;
		this.humisPrice = humisPrice;
		this.PixelPrice = pixelPrice;
		this.prestige = prestige;
		
		this.id = "" + LocalDate.now().getYear();
		this.id = this.id.substring(2);
		this.id += LocalDate.now().getDayOfYear();
		this.id += NumId;
		
		NumId++;
	}
	
	/**
	 * @return id l'id du cosmetique
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @return name le nom du cosmetique
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return itemShop le material representant le cosmetique
	 */
	public ItemStack getItemShop() {
		return itemShop;
	}
	

	public int getHumisPrice() {
		return humisPrice;
	}

	public void setHumisPrice(int humisPrice) {
		this.humisPrice = humisPrice;
	}

	public int getPixelPrice() {
		return PixelPrice;
	}

	public void setPixelPrice(int pixelPrice) {
		PixelPrice = pixelPrice;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setItemShop(ItemStack itemShop) {
		this.itemShop = itemShop;
	}
	
	public Prestige getPrestige() {
		return prestige;
	}

	/**
	 * sauvegarder le cosmetique dans un fichier
	 * @param file le fichier dans lequel le cosmetique doit etre enregistre
	 */
	public void save(File file) {
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.err.println("Erreur dans la creation du fichier");
				e.printStackTrace();
				return;
			}
		}
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("id", this.id);
		config.set("name", this.name);
		config.set("itemshop", this.itemShop);
		config.set("humisPrice", this.humisPrice);
		config.set("pixelPrice", this.PixelPrice);
		config.set("prestige", this.prestige.toString());
		
		try {
			config.save(file);
		} catch (IOException e) {
			System.err.println("Erreur enregistrement fichier");
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * charge le cosmetique dans un fichier
	 * @param file le fichier dans lequel le cosmetique doit etre charge
	 */
	public void load(File file) {
		if(!file.exists())
			return;
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		if(!config.contains("id") || !config.contains("name") || !config.contains("itemshop") || !config.contains("humisPrice") || !config.contains("pixelPrice") || !config.contains("prestige")) {
			System.err.println("Erreur parametre manquant dans le fichier " + file.getName());
			return;
		}
		
		this.id = config.getString("id");
		this.name = config.getString("name");
		this.itemShop = config.getItemStack("itemshop");
		this.humisPrice = config.getInt("humisPrice");
		this.PixelPrice = config.getInt("pixelPrice");
		this.prestige = Prestige.valueOf(config.getString("prestige"));
	}
	
	public static int getNumId() {
		return NumId;
	}

	
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + PixelPrice;
		result = prime * result + humisPrice;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((itemShop == null) ? 0 : itemShop.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((prestige == null) ? 0 : prestige.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cosmetique other = (Cosmetique) obj;
		if (PixelPrice != other.PixelPrice)
			return false;
		if (humisPrice != other.humisPrice)
			return false;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (itemShop == null)
		{
			if (other.itemShop != null)
				return false;
		}
		else if (!itemShop.equals(other.itemShop))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		if (prestige != other.prestige)
			return false;
		return true;
	}

	/**
	 * Convertir un cosmetique en itemStack
	 * @param c le cosmetique
	 */
	public static ItemStack cosmetiqueToItem(Cosmetique cosmetique) {

		ItemStack item = new ItemStack(cosmetique.getItemShop());
		ChatColor color = ChatColor.valueOf(cosmetique.getPrestige().getColor());
		
		ArrayList<String> lores = new ArrayList<String>();
		lores.add(" ");
		lores.add(color + "" + ChatColor.BOLD + cosmetique.getPrestige().name().toUpperCase());
		lores.add(" ");
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(color + "" + ChatColor.UNDERLINE + cosmetique.getName() + " #" + cosmetique.getId());

		
		if(cosmetique instanceof TemporaryParticleCosmetique) {
			lores.add(color + "Particules: " + ((TemporaryParticleCosmetique) cosmetique).getParticle().name().toLowerCase());
		}
		else if(cosmetique instanceof TemporaryMaterialHatCosmetique) {
			lores.add(color + "Chapeau: " + ((TemporaryMaterialHatCosmetique) cosmetique).getMaterialHat().name().toLowerCase());
		}
		else if(cosmetique instanceof ParticleCosmetique) {
			lores.add(color + "Particules: " + ((ParticleCosmetique) cosmetique).getParticleEffect().name().toLowerCase());
		}
		else if(cosmetique instanceof MaterialHatCosmetique) {
			lores.add(color + "Chapeau: " + ((MaterialHatCosmetique) cosmetique).getMaterialHat().name().toLowerCase());
		}

		meta.setLore(lores);
		item.setItemMeta(meta);
		return item;
	}

	@Override
	public String toString()
	{
		return "Cosmetique [id=" + id + ", name=" + name + ", itemShop=" + itemShop.toString() + ", prestige=" + prestige.toString()
				+ ", humisPrice=" + humisPrice + ", PixelPrice=" + PixelPrice + "]";
	}

	
	
	
}
