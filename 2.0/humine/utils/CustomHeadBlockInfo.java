package humine.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

/**
 * Package regroupant les outils de HumineShop
 * Classe regroupant les informations sur les customs heads d'un joueur
 * (tete posée, etc)
 * 
 * @author miza
 */
public class CustomHeadBlockInfo
{
	private String owner;
	private Map<Block, ItemStack> blocks;
	
	/**
	 * Constructeur de la classe
	 * @param owner le nom du proprietaire
	 */
	public CustomHeadBlockInfo(String owner)
	{
		this(owner, new HashMap<Block, ItemStack>());
	}
	
	/**
	 * Constructeur de la classe
	 * @param owner le nom du proprietaire
	 * @param blocks la liste de blocks qu'il appartient
	 */
	public CustomHeadBlockInfo(String owner, Map<Block, ItemStack> blocks)
	{
		this.owner = owner;
		this.blocks = blocks;
	}
	
	/**
	 * @return le proprietaire
	 */
	public String getOwner()
	{
		return owner;
	}
	
	/**
	 * @param owner le nouveau proprietaire
	 */
	public void setOwner(String owner)
	{
		this.owner = owner;
	}
	
	/**
	 * @return les blocks et les items liés au block du joueur
	 */
	public Map<Block, ItemStack> getBlocks() {
		return blocks;
	}
	
	/**
	 * @param blocks mettre une nouvelle liste de block/items
	 */
	public void setBlocks(Map<Block, ItemStack> blocks) {
		this.blocks = blocks;
	}
	
	/**
	 * Ajoute un nouveau block appartenant au proprietaire
	 * @param block le block en question
	 * @param item l'item lié au block
	 */
	public void addBlock(Block block, ItemStack item) {
		this.blocks.put(block, item);
	}
	
	/**
	 * Supprime un block appartenant au proprietaire
	 * @param block le block en question
	 */
	public void removeBlock(Block block) {
		this.blocks.remove(block);
	}
	
	/**
	 * @param block le block a rechercher
	 * @return true si le proprietaire contient ce block, sinon false
	 */
	public boolean contains(Block block) {
		for(Block b : this.blocks.keySet()) {
			if(b.getLocation().equals(block.getLocation()))
				return true;
		}
		return false;
	}
	
	/**
	 * @param loc la location a rechercher
	 * @return true si le proprietaire contient le block se trouvant a cette
	 * location, sinon false
	 */
	public boolean contains(Location loc) {
		for(Block b : this.blocks.keySet()) {
			if(b.getLocation().equals(loc))
				return true;
		}
		return false;
	}
	
	/**
	 * @param item l'item a rechercher
	 * @return true si le proprietaire contient cette item, sinon false
	 */
	public boolean contains(ItemStack item) {
		for(ItemStack i : this.blocks.values()) {
			if(i.isSimilar(item))
				return true;
		}
		return false;
	}
	
	/**
	 * @param block le block en question
	 * @return l'itemStack lié au block, return null si le block est inexistant
	 */
	public ItemStack getItemStack(Block block) {
		return this.blocks.get(block);
	}
	
	/**
	 * @param item l'item en question
	 * @return le block lié a l'item, return null si l'item est inexistant
	 */
	public Block getBlock(ItemStack item) {
		for(Entry<Block, ItemStack> entry : this.blocks.entrySet()) {
			if(entry.getValue().isSimilar(item))
				return entry.getKey();
		}
		return null;
	}
	
	/**
	 * Permet de sauvegarder dans un fichier les donnees d'une classe CustomHeadBlockInfo
	 * @param file dans le fichier a sauvegarder
	 * @param info la classe a sauvegarder
	 * @throws IOException si impossible de creer le fichier (si inexistant de base)
	 */
	public static void save(File file, CustomHeadBlockInfo info) throws IOException {
		if(!file.exists()) {
			file.createNewFile();
		}
		
		List<Location> loc = new ArrayList<>();
		for(Block b : info.getBlocks().keySet())
			loc.add(b.getLocation());
		
		List<ItemStack> items = new ArrayList<>();
		items.addAll(info.getBlocks().values());
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("Owner", info.getOwner());
		config.set("Blocks", loc);
		config.set("Items", items);
		
		config.save(file);
	}
	
	/**
	 * Permet de charger un fichier pour recuperer les donnees d'une de ces classes
	 * @param file le fichier a charger
	 * @return un CustomHeadBlockInfo, null si fichier inexistant
	 */
	@SuppressWarnings("unchecked")
	public static CustomHeadBlockInfo load(File file) {
		if(!file.exists())
			return null;
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		CustomHeadBlockInfo info = new CustomHeadBlockInfo(config.getString("Owner"));
		
		List<Location> loc = (List<Location>) config.getList("Blocks", new ArrayList<Location>());
		List<ItemStack> items = (List<ItemStack>) config.getList("Items", new ArrayList<ItemStack>());
		
		List<Block> blocks = new ArrayList<Block>();
		for(Location l : loc) {
			blocks.add(l.getWorld().getBlockAt(l));
		}
		
		Map<Block, ItemStack> maps = new HashMap<Block, ItemStack>();
		for(int i = 0; i < blocks.size(); i++) {
			maps.put(blocks.get(i), items.get(i));
		}
		
		info.setBlocks(maps);
		
		return info;
	}
}
