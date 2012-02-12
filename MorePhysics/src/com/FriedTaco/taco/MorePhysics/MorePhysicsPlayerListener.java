package com.FriedTaco.taco.MorePhysics;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;



public class MorePhysicsPlayerListener implements Listener 
{
	final MorePhysics plugin;
	double min = .001;

    public MorePhysicsPlayerListener(MorePhysics instance) 
    {
        plugin = instance;
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerMove(PlayerMoveEvent event)
    {
    	Player p = event.getPlayer();
    	if((MorePhysics.Permissions == null && !p.hasPermission("morephysics.exempt")) || (MorePhysics.Permissions != null && MorePhysics.Permissions.has(p, "morephysics.exempt")))
    	{
	    	Block in = p.getWorld().getBlockAt(event.getTo().add(0, 1, 0));
	    	Block under = p.getWorld().getBlockAt(new Location(p.getWorld(), event.getTo().getBlockX(),event.getTo().getBlockY()-1,event.getTo().getBlockZ()));
	    	if(in != null)
	    	{
	    		if(in.getType() == Material.WATER && plugin.swimming)
	    		{
	    			double modifier = plugin.getTotalWeight(p);
	    			if(modifier > 0)
	    			{
	    				Vector v = p.getVelocity();
	    				if(event.getTo().getY() > event.getFrom().getY())
	    					v.setY(0-modifier);
	    				else
	    					v.setY(0-modifier*4);
	    				p.setVelocity(v);
	    			}
	    			
	    		}
	    		else if(in.getTypeId() == 0)
	    		{
	    			double modifier = 0;
	    			for(ItemStack i : p.getInventory().getArmorContents())
	    			{
	    				if(i != null)
	    					modifier += plugin.weight(i.getTypeId());
	    			}
	    			if(modifier > 0)
	    			{
	    				//Location loc = event.getTo();
	    				//double x = p.getVelocity().getX();
	    				double x = p.getVelocity().getX();
	    				double z = p.getVelocity().getZ();
	    				double diffX = event.getTo().getX() - event.getFrom().getX();
	    				double diffZ = event.getTo().getZ() - event.getFrom().getZ();
	    				double diffY = Math.abs(event.getTo().getY() - event.getFrom().getY());
	    				Vector v = p.getVelocity();
	    				if(diffY < .01 && under.getTypeId() != 79 && plugin.movement && !p.isSneaking())
	    				{
		    				if(Math.abs(diffX) > .06 && Math.abs(diffX) < 1)
		    				{
		    					v.setX(x-(modifier/8));
		    					//loc.setX(loc.getX()-(modifier/3));
		    				}
		    				else if(diffX < .06 && Math.abs(diffX) < 1)
		    				{
		    					v.setX(x+(modifier/8));
		    					//loc.setX(loc.getX()+(modifier/3));
		    				}
		    				if(diffZ > .06 && Math.abs(diffZ) < 1)
		    				{
		    					v.setX(z-(modifier/8));
		    					//loc.setZ(loc.getZ()-(modifier/3));
		    				}
		    				else if(diffZ < .06 && Math.abs(diffZ) < 1)
		    				{
		    					v.setX(z+(modifier/8));
		    					//loc.setZ(loc.getZ()+(modifier/3));
		    				}
		    				if(event.getTo().distance(event.getFrom()) > .1)
		    					p.setVelocity(v);
		    				//p.teleport(loc);    				
		    			}
	    					
	    			}
	    		}
	    	}
    	}
    }
   }


