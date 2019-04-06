package humine.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import humine.utils.ParticleScheduler;

public class PlayerQuit implements Listener
{

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		ParticleScheduler.disableParticleCosmetique(event.getPlayer());
	}
}
