package com.humine.utils.shop.utils;

import org.bukkit.entity.Player;

import com.humine.utils.shop.Cosmetique;

public abstract class ApplicateEffect {

	
	/*@SuppressWarnings("deprecation")
	public static void playSkullEffect(Player player, Cosmetique cosmetique) {
		if(cosmetique.getBlock() != null) {
			ItemStack item = cosmetique.getBlock();
			SkullMeta meta = (SkullMeta) item.getItemMeta();
			meta.setOwner(player.getName());
			item.setItemMeta(meta);
			
			player.getInventory().setHelmet(item);
		}
	}*/
	
	public static void playEffectOnPlayer(Player player, Cosmetique cosmetique) {
		if(cosmetique.getType() != CosmetiqueEffect.NOTHING) {
			while(true) {
				player.getWorld().spawnParticle(cosmetique.getParticle(), player.getLocation(), 2);
				try
				{
					Thread.sleep(50);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
}
