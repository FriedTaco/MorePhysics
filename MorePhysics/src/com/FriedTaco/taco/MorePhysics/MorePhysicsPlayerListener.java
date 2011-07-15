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
    double weight(int id)
    {
    	switch(id)
    	{
    		case 298:
    			return plugin.lhat;	
    		case 299:
    			return plugin.lshirt;	
    		case 300:
    			return plugin.lpants;	
    		case 301:
    			return plugin.lboots;	
    		case 302:
    			return plugin.chat;
    		case 303:
    			return plugin.cshirt;
    		case 304:
    			return plugin.cpants;
    		case 305:
    			return plugin.cboots;
    		case 306:
    			return plugin.ihat;
    		case 307:
    			return plugin.ishirt;
    		case 308:
    			return plugin.ipants;
    		case 309:
    			return plugin.iboots;
    		case 310:
    			return plugin.dhat;
    		case 311:
    			return plugin.dshirt;
    		case 312:
    			return plugin.dpants;
    		case 313:
    			return plugin.dboots;
    		case 314:
    			return plugin.ghat;
    		case 315:
    			return plugin.gshirt;
    		case 316:
    			return plugin.gpants;
    		case 317:
    			return plugin.gboots;
    		default:
    			return 0;
    	}
    }
    public void onPlayerMove(PlayerMoveEvent event)
    {
    	Block on = event.getPlayer().getWorld().getBlockAt(event.getTo());
    	Block under = event.getPlayer().getWorld().getBlockAt(new Location(event.getPlayer().getWorld(), event.getTo().getBlockX(),event.getTo().getBlockY()-1,event.getTo().getBlockZ()));
    	
    	if(on != null && under != null)
    	{
    		if((on.getTypeId() == 8 || on.getTypeId() == 9) && (under.getTypeId() == 8 || under.getTypeId() == 9) && plugin.swimming)
    		{
    			double modifier = 0;
    			for(ItemStack i : event.getPlayer().getInventory().getArmorContents())
    			{
    				if(i != null)
    					modifier += weight(i.getTypeId());
    			}
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
    					modifier += weight(i.getTypeId());
    			}
    			if(modifier > 0)
    			{
    				//Location loc = event.getTo();
    				//double x = event.getPlayer().getVelocity().getX();
    				double x = event.getPlayer().getVelocity().getX();
    				double z = event.getPlayer().getVelocity().getZ();
    				double diffX = event.getTo().getX() - event.getFrom().getX();
    				double diffZ = event.getTo().getZ() - event.getFrom().getZ();
    				Vector v = event.getPlayer().getVelocity();
    				Vector orig = v.clone();
    				if(((Math.abs(event.getTo().getY() - event.getFrom().getY()) < .1)) && (under.getTypeId() != 79) && plugin.movement && !event.getPlayer().isSneaking())
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
	    				System.out.println(x);
	    				//event.getPlayer().teleport(loc);    				
	    			}
    					
    			}
    		}
    	}
    }
   }


