package com.FriedTaco.taco.MorePhysics;

import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
                Entity e = event.getEntity();
                if(e == null)
                    return;
                /* Temporary removal
		if(!plugin.bouncyBlocks.isEmpty())
		{
			if(event.getCause() == DamageCause.FALL && event.getEntity() instanceof Player)
			{
				
				if(plugin.bouncyBlocks.contains(Integer.toString(e.getLocation().getBlock().getRelative(0, -1, 0).getTypeId())))
				{
					double damage=0;
					if(event.getDamage()/10 > 3)
						damage=3;
					else
						damage=event.getDamage()/10;
					event.setCancelled(true);
					e.setVelocity(e.getVelocity().setY(e.getVelocity().getY()+damage));
				}
			}
		}
                        */
		if(event instanceof EntityDamageByEntityEvent)
		{
			if(plugin.arrows)
			{
				EntityDamageByEntityEvent EByEevent = (EntityDamageByEntityEvent) event;
				if(EByEevent.getDamager() instanceof Arrow)
				{
				Location dmg = EByEevent.getDamager().getLocation();
				Location ent = EByEevent.getEntity().getLocation();
					if(EByEevent.getEntity() instanceof Player)
					{
						
						double diffY = dmg.getY()-ent.getY();
						double modifier = 1;
						if(diffY < 2 && diffY > 1.55)
							modifier = plugin.arhead;
						else if(diffY < 1.55 && diffY > .8)
							modifier = plugin.artorso;
						else if(diffY < .8 && diffY > .45)
							modifier = plugin.arlegs;
						else if(diffY < .45 && diffY > 0)
							modifier = plugin.arfeet;
						EByEevent.setDamage((int) (EByEevent.getDamage()*modifier));
					}
				}
			}
		}
	}
}
