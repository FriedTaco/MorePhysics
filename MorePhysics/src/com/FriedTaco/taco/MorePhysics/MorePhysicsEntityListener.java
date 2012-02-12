package com.FriedTaco.taco.MorePhysics;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class MorePhysicsEntityListener implements Listener {
	
	private MorePhysics plugin;
	
	public MorePhysicsEntityListener(MorePhysics instance)
	{
		plugin=instance;
	}
	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamage(EntityDamageEvent event)
	{
		if(!plugin.bouncyBlocks.isEmpty())
		{
			if(event.getCause() == DamageCause.FALL && event.getEntity() instanceof Player)
			{
				if(plugin.bouncyBlocks.contains(Integer.toString(event.getEntity().getLocation().getBlock().getRelative(0, -1, 0).getTypeId())))
				{
					double damage=0;
					if(event.getDamage()/10 > 3)
						damage=3;
					else
						damage=event.getDamage()/10;
					event.setCancelled(true);
					event.getEntity().setVelocity(event.getEntity().getVelocity().setY(event.getEntity().getVelocity().getY()+damage));
				}
			}
		}
	}
}
