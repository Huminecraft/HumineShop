package humine.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import humine.utils.Utils;

public class PlayerQuit implements Listener
{

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Utils.disableParticleCosmetique(event.getPlayer());
	}
}
