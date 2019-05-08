package humine.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


public class CustomHeadBlockInfo
{
	private String owner;
	private List<Block> blocks;
	
	public CustomHeadBlockInfo(String owner)
	{
		this(owner, new ArrayList<Block>());
	}
	
	public CustomHeadBlockInfo(String owner, List<Block> blocks)
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
	
	public List<Block> getBlocks()
	{
		return blocks;
	}
	
	public void setBlocks(List<Block> blocks)
	{
		this.blocks = blocks;
	}
	
	public boolean addBlock(Block block) {
		return this.blocks.add(block);
	}
	
	public boolean removeBlock(Block block) {
		return this.blocks.remove(block);
	}
	
	public boolean contains(Block block) {
		for(Block b : this.blocks) {
			if(b.getLocation().equals(block.getLocation()))
				return true;
		}
		return false;
	}
	
	public boolean contains(Location loc) {
		for(Block b : this.blocks) {
			if(b.getLocation().equals(loc))
				return true;
		}
		return false;
	}
	
	public static void save(File file, CustomHeadBlockInfo info) throws IOException {
		if(!file.exists()) {
			file.createNewFile();
		}
		
		List<Location> loc = new ArrayList<>();
		for(Block b : info.getBlocks())
			loc.add(b.getLocation());
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("Owner", info.getOwner());
		config.set("Blocks", loc);

		config.save(file);
	}
	
	@SuppressWarnings("unchecked")
	public static CustomHeadBlockInfo load(File file) {
		if(!file.exists())
			return null;
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		CustomHeadBlockInfo info = new CustomHeadBlockInfo(config.getString("Owner"));
		
		List<Location> loc = (List<Location>) config.getList("Blocks", new ArrayList<Location>());
		List<Block> blocks = new ArrayList<Block>();
		for(Location l : loc) {
			blocks.add(l.getWorld().getBlockAt(l));
		}
		
		info.setBlocks(blocks);
		
		return info;
	}
}
