/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcstuff;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author James
 */
public class StuffEntityListener implements Listener {
	
	private MCStuff plugin;
	
	public StuffEntityListener(MCStuff instance)
	{
		plugin=instance;
	}
        
        @EventHandler
        public void onEntityDeath(EntityDeathEvent e)
        {
            Entity ent = e.getEntity(); 
            if(ent instanceof Monster)
            {
                 EntityType type = ent.getType();   
                 Monster m = (Monster)ent;
                 int sType = 0;
                 int chance = 0;
                 switch(type)
                 {
                     case CREEPER:
                         sType = SkullType.CREEPER.ordinal();
                         chance = 5;
                         break;
                    case ENDER_DRAGON:
                         sType = SkullType.DRAGON.ordinal();
                         chance = 80;
                         break;
                    case SKELETON:
                         sType = SkullType.SKELETON.ordinal();
                         chance = 15;
                         break;
                    case WITHER_SKULL:
                         sType = SkullType.WITHER.ordinal();
                         chance = 40;
                    case ZOMBIE:
                         sType = SkullType.ZOMBIE.ordinal();
                         chance = 25;
                    default:
                        sType = -1;
                 }
                 if(sType == -1)
                     return;
                 int rand = (int) (Math.random() * 100);
                 if(rand < chance)
                 {
                    ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) sType);
                    ent.getWorld().dropItemNaturally(ent.getLocation(), skull);
                 }
            }
        }
}
