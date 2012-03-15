package com.FriedTaco.taco.MorePhysics;


import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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
    	if(!p.hasPermission("morephysics.exempt"))
    	{
    		
    		Block in = p.getWorld().getBlockAt(event.getTo()).getRelative(0, 1, 0);
	    	if(in != null)
	    	{
	    		double modifier = plugin.getTotalWeight(p);
	    		if((in.getTypeId() == 9 || in.getTypeId() == 8) && plugin.swimming)
	    		{
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
	    		else 
	    		{
	    			int intensity = (int) (modifier*30);
	    			if(intensity > 0)
	    			{
	    				if(intensity>5)
	    					intensity=5;
	    				PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, 20, intensity);
	    	    		p.addPotionEffect(effect, true);
	    			}
	    		}
	    		/*Depreciated
	    		Location to = event.getTo();
				Location from = event.getFrom();
	    		Block under = p.getWorld().getBlockAt(event.getTo()).getRelative(0,-1,0);
	   			if(in.getTypeId() == 0)
	    		{
	    			double modifier = plugin.getTotalWeight(p);
	    			if(modifier > 0)
	    			{
	    				//Location loc = event.getTo();
	    				//double x = p.getVelocity().getX();
	    				double x = p.getVelocity().getX();
	    				double z = p.getVelocity().getZ();
	    				double diffX = to.getX() - from.getX();
	    				double diffZ = to.getZ() - from.getZ();
	    				double diffY = Math.abs((to.getY() - from.getY()));
	    				Vector v = p.getVelocity();
	    				if((diffY < .1) && (under.getTypeId() != 79) && (plugin.movement) && (!p.isSneaking()))
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
		    				if(!v.equals(p.getVelocity()))
		    					p.setVelocity(v);
		    				//p.teleport(loc);    				
		    			}
	    					
	    			}
	    		}
	    		*/
	    	}
    	}
    }
   }


