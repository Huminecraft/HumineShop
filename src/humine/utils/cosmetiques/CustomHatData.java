package humine.utils.cosmetiques;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class CustomHatData implements Serializable, Cloneable
{
	private static final long serialVersionUID = -671775460698689721L;
	private int inStock;
	private int inInventory;
	
	private List<CustomHatLocation> locations;
	
	public CustomHatData()
	{
		this.inStock = 1;
		this.inInventory = 0;
		this.locations = new ArrayList<CustomHatLocation>();
	}
	
	public int getInStock()
	{
		return inStock;
	}
	
	public void setInStock(int inStock)
	{
		this.inStock = inStock;
	}
	
	public int getInInventory()
	{
		return inInventory;
	}
	
	public void setInInventory(int inInventory)
	{
		this.inInventory = inInventory;
	}
	
	public int getInWorld()
	{
		return this.locations.size();
	}
	
	public List<CustomHatLocation> getLocations()
	{
		return locations;
	}
	
	public void resetLocation() {
		this.locations.clear();
	}
	
	public boolean addLocation(CustomHatLocation loc) {
		return this.locations.add(loc);
	}
	
	public boolean removeLocation(CustomHatLocation loc) {
		return this.locations.remove(loc);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + inInventory;
		result = prime * result + inStock;
		result = prime * result + ((locations == null) ? 0 : locations.hashCode());
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
		CustomHatData other = (CustomHatData) obj;
		if (inInventory != other.inInventory)
			return false;
		if (inStock != other.inStock)
			return false;
		if (locations == null)
		{
			if (other.locations != null)
				return false;
		}
		else if (!locations.equals(other.locations))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "CustomHatData [inStock=" + inStock + ", inInventory=" + inInventory + ", locations=" + locations + "]";
	}
	
}
