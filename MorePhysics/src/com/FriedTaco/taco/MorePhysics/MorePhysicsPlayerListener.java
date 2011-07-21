package com.FriedTaco.taco.MorePhysics;


import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;



public class MorePhysicsPlayerListener extends PlayerListener 
{
	final MorePhysics plugin;
	double min = .001;

    public MorePhysicsPlayerListener(MorePhysics instance) 
    {
        plugin = instance;
    }
    
    public void onPlayerMove(PlayerMoveEvent event)
    {
    	if((MorePhysics.Permissions == null && !event.getPlayer().hasPermission("morephysics.exempt")) || (MorePhysics.Permissions != null && MorePhysics.Permissions.has(event.getPlayer(), "morephysics.exempt")))
    	{
	    	Block on = event.getPlayer().getWorld().getBlockAt(event.getTo());
	    	Block under = event.getPlayer().getWorld().getBlockAt(new Location(event.getPlayer().getWorld(), event.getTo().getBlockX(),event.getTo().getBlockY()-1,event.getTo().getBlockZ()));
	    	if(on != null && under != null)
	    	{
	    		if((on.getTypeId() == 8 || on.getTypeId() == 9) && (under.getTypeId() == 8 || under.getTypeId() == 9) && plugin.swimming)
	    		{
	    			double modifier = plugin.getTotalWeight(event.getPlayer());
	    			if(modifier > 0)
	    			{
	    				if(event.getTo().getY() > event.getFrom().getY())
	    					event.getPlayer().setVelocity(new Vector(event.getPlayer().getVelocity().getX(),0-modifier,event.getPlayer().getVelocity().getZ()));
	    				else
	    					event.getPlayer().setVelocity(new Vector(event.getPlayer().getVelocity().getX(),0-(modifier*4),event.getPlayer().getVelocity().getZ()));
	    			}
	    			
	    		}
	    		else if(on.getTypeId() == 0)
	    		{
	    			double modifier = 0;
	    			for(ItemStack i : event.getPlayer().getInventory().getArmorContents())
	    			{
	    				if(i != null)
	    					modifier += plugin.weight(i.getTypeId());
	    			}
	    			if(modifier > 0)
	    			{
	    				//Location loc = event.getTo();
	    				//double x = event.getPlayer().getVelocity().getX();
	    				double x = event.getPlayer().getVelocity().getX();
	    				double z = event.getPlayer().getVelocity().getZ();
	    				double diffX = event.getTo().getX() - event.getFrom().getX();
	    				double diffZ = event.getTo().getZ() - event.getFrom().getZ();
	    				double diffY = Math.abs(event.getTo().getY() - event.getFrom().getY());
	    				Vector v = event.getPlayer().getVelocity();
	    				if(diffY < .01 && under.getTypeId() != 79 && plugin.movement && !event.getPlayer().isSneaking())
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
		    					event.getPlayer().setVelocity(v);
		    				//event.getPlayer().teleport(loc);    				
		    			}
	    					
	    			}
	    		}
	    	}
    	}
    }
   }


