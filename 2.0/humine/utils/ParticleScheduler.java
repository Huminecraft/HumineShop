package humine.utils;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import humine.utils.cosmetiques.Cosmetique;
import humine.utils.cosmetiques.ParticleCosmetique;
import humine.utils.cosmetiques.temporary.TemporaryParticleCosmetique;

public abstract class ParticleScheduler {

	private static HashMap<Player, Cosmetique> BuyList = new HashMap<Player, Cosmetique>();
	
	public static void enableParticleCosmetique(Player player, Cosmetique cosmetique) {
		if(!(cosmetique instanceof ParticleCosmetique) && !(cosmetique instanceof TemporaryParticleCosmetique)) {
			return;
		}
		
		if(BuyList.containsKey(player))
			BuyList.replace(player, cosmetique);
		else
			BuyList.put(player, cosmetique);
	}
	
	public static void disableParticleCosmetique(Player player) {
		BuyList.remove(player);
	}
	
	public static void startScheduler(Plugin plugin) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run()
			{
				for(Entry<Player, Cosmetique> entry : BuyList.entrySet()) {
					ParticleCosmetique c = (ParticleCosmetique) entry.getValue();
					entry.getKey().getWorld().spawnParticle(c.getParticleEffect(), entry.getKey().getLocation().getX(), entry.getKey().getLocation().getY()+1.0, entry.getKey().getLocation().getZ(), 30, 0.3, 0.3, 0.3, 1.0, null);
				}
			}
		}, 0L, 15L);
	}
	
	public static HashMap<Player, Cosmetique> getBuyList() {
		return BuyList;
	}
}
