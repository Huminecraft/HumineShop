package humine.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.aypi.utils.Timer;
import com.aypi.utils.inter.TimerFinishListener;

import humine.utils.cosmetiques.AbstractParticleCosmetique;

/**
 * Package regroupant les outils de HumineShop
 * Classe gerant l'execution des particules sur les joueurs
 * 
 * @author miza
 */
public abstract class ParticleScheduler {

	private static HashMap<Player, AbstractParticleCosmetique> BuyList = new HashMap<>();
	private static Map<Player, AbstractParticleCosmetique> demoList = new HashMap<>();
	private static Plugin plugin;
	
	/**
	 * Active un effet de particule sur un joueur
	 * @param player le joueur a enregistrer
	 * @param cosmetique le cosmetique lié au joueur
	 */
	public static void playParticleCosmetique(Player player, AbstractParticleCosmetique cosmetique) {
		if(demoList.containsKey(player))
			demoList.remove(player);
		
		BuyList.put(player, cosmetique);
	}
	
	/**
	 * Active un effet de particule sur un joueur
	 * @param player le joueur a enregistrer
	 * @param cosmetique le cosmetique lié au joueur
	 */
	public static void playDemoParticleCosmetique(Player player, AbstractParticleCosmetique cosmetique) {
		if(BuyList.containsKey(player))
			BuyList.remove(player);

		demoList.put(player, cosmetique);
		
		Timer timer = new Timer(plugin, 10, new TimerFinishListener() {
			
			@Override
			public void execute() {
				demoList.remove(player);
			}
		});
		timer.start();
	}
	
	/**
	 * Permet de desactiver les particules d'un joueur
	 * @param player le joueur auquel il faut desactiver l'effet particule
	 */
	public static void disableParticleCosmetique(Player player) {
		BuyList.remove(player);
		demoList.remove(player);
	}
	
	/**
	 * Permet d'executer de facon repeter la boucle activant les effets de
	 * particule de chaque joueur dans le jeu qui l'ont lance
	 * 
	 * <b>ATTENTION a n'executer qu'une seul fois</b>
	 * @param plugin
	 */
	public static void startScheduler(Plugin p) {
		plugin = p;
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run()
			{
				for(Entry<Player, AbstractParticleCosmetique> entry : BuyList.entrySet()) {
					entry.getValue().playEffect(entry.getKey());
				}
			}
		}, 0L, 11L);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run()
			{
				for(Entry<Player, AbstractParticleCosmetique> entry : demoList.entrySet()) {
					entry.getValue().playDemo(entry.getKey());
				}
			}
		}, 0L, 11L);
	}
	
	/**
	 * @return la liste des joueurs ayant des particules activees
	 */
	public static HashMap<Player, AbstractParticleCosmetique> getBuyList() {
		return BuyList;
	}
	
	public static Map<Player, AbstractParticleCosmetique> getDemoList() {
		return demoList;
	}
}
