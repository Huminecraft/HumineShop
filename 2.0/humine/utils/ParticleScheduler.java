package humine.utils;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.aypi.utils.Timer;
import com.aypi.utils.inter.TimerFinishListener;

import humine.main.MainShop;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.cosmetiques.ParticleCosmetique;
import humine.utils.cosmetiques.temporary.TemporaryParticleCosmetique;

/**
 * Package regroupant les outils de HumineShop
 * Classe gerant l'execution des particules sur les joueurs
 * 
 * @author miza
 */
public abstract class ParticleScheduler {

	private static HashMap<Player, Cosmetique> BuyList = new HashMap<Player, Cosmetique>();
	
	/**
	 * Active un effet de particule sur un joueur
	 * @param player le joueur a enregistrer
	 * @param cosmetique le cosmetique lié au joueur
	 */
	public static void enableParticleCosmetique(Player player, Cosmetique cosmetique) {
		if(!(cosmetique instanceof ParticleCosmetique) && !(cosmetique instanceof TemporaryParticleCosmetique)) {
			return;
		}
		
		if(BuyList.containsKey(player))
			BuyList.replace(player, cosmetique);
		else
			BuyList.put(player, cosmetique);
		
		Timer t = new Timer(MainShop.getInstance(), 10, new TimerFinishListener() {
			
			@Override
			public void execute() {
				disableParticleCosmetique(player);
			}
		});
		t.start();
	}
	
	/**
	 * Permet de desactiver les particules d'un joueur
	 * @param player le joueur auquel il faut desactiver l'effet particule
	 */
	public static void disableParticleCosmetique(Player player) {
		BuyList.remove(player);
	}
	
	/**
	 * Permet d'executer de facon repeter la boucle activant les effets de
	 * particule de chaque joueur dans le jeu qui l'ont lance
	 * 
	 * <b>ATTENTION a n'executer qu'une seul fois</b>
	 * @param plugin
	 */
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
	
	/**
	 * @return la liste des joueurs ayant des particules activees
	 */
	public static HashMap<Player, Cosmetique> getBuyList() {
		return BuyList;
	}
}
