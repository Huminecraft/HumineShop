package humine.utils.cosmetiques;

import java.io.Serializable;

import org.bukkit.Location;
import org.bukkit.World;

import humine.main.MainShop;

public class CustomHatLocation implements Serializable, Cloneable
{

	private static final long serialVersionUID = -6868066742601838051L;
	private int x;
	private int y;
	private int z;
	private String world;
	
	public CustomHatLocation(World world, int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world.getName();
	}
	
	public CustomHatLocation(Location location)
	{
		this(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}
	
	public void setLocation(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setlocation(Location location) {
		setLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}
	
	public void setLocation(CustomHatLocation location) {
		setLocation(location.getX(), location.getY(), location.getZ());
	}
	
	public Location convertToLocation() {
		return new Location(getWorld(), getX(), getY(), getZ());
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getZ()
	{
		return z;
	}

	public void setZ(int z)
	{
		this.z = z;
	}
	
	public World getWorld()
	{
		return MainShop.getInstance().getServer().getWorld(this.world);
	}
	
	public void setWorld(World world) {
		this.world = world.getName();
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((world == null) ? 0 : world.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
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
		CustomHatLocation other = (CustomHatLocation) obj;
		if (world == null)
		{
			if (other.world != null)
				return false;
		}
		else if (!world.equals(other.world))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

	public boolean equals(Location location) {
		if(location.getWorld().equals(this.getWorld()) && location.getBlockX() == x && location.getBlockY() == y && location.getBlockZ() == z)
			return true;
		return false;
	}

	@Override
	public String toString()
	{
		return "CustomHatLocation [x=" + x + ", y=" + y + ", z=" + z + ", world=" + world + "]";
	}
	
}
