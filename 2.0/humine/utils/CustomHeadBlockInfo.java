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


public class CustomHeadBlockInfo
{
	private String owner;
	private Map<Block, ItemStack> blocks;
	
	public CustomHeadBlockInfo(String owner)
	{
		this(owner, new HashMap<Block, ItemStack>());
	}
	
	public CustomHeadBlockInfo(String owner, Map<Block, ItemStack> blocks)
	{
		this.owner = owner;
		this.blocks = blocks;
	}
	
	public String getOwner()
	{
		return owner;
	}
	
	public void setOwner(String owner)
	{
		this.owner = owner;
	}
	
	public Map<Block, ItemStack> getBlocks() {
		return blocks;
	}
	
	public void setBlocks(Map<Block, ItemStack> blocks) {
		this.blocks = blocks;
	}
	
	public void addBlock(Block block, ItemStack item) {
		this.blocks.put(block, item);
	}
	
	public void removeBlock(Block block) {
		this.blocks.remove(block);
	}
	
	public boolean contains(Block block) {
		for(Block b : this.blocks.keySet()) {
			if(b.getLocation().equals(block.getLocation()))
				return true;
		}
		return false;
	}
	
	public boolean contains(Location loc) {
		for(Block b : this.blocks.keySet()) {
			if(b.getLocation().equals(loc))
				return true;
		}
		return false;
	}
	
	public boolean contains(ItemStack item) {
		for(ItemStack i : this.blocks.values()) {
			if(i.isSimilar(item))
				return true;
		}
		return false;
	}
	
	public ItemStack getItemStack(Block block) {
		return this.blocks.get(block);
	}
	
	public Block getBlock(ItemStack item) {
		for(Entry<Block, ItemStack> entry : this.blocks.entrySet()) {
			if(entry.getValue().isSimilar(item))
				return entry.getKey();
		}
		return null;
	}
	
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
