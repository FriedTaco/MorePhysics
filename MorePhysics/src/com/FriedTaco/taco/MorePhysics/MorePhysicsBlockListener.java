package com.FriedTaco.taco.MorePhysics;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.util.Vector;

public class MorePhysicsBlockListener extends BlockListener {
	private final MorePhysics plugin;

    public MorePhysicsBlockListener(final MorePhysics plugin) {
        this.plugin = plugin;
    }
    
    public void onBlockPistonExtend(BlockPistonExtendEvent event)
    {    	
    	if(plugin.pistons)
    	{
	    	LivingEntity entity = null;
	    	Vector v = null;
			for(Entity e : event.getBlock().getChunk().getEntities())
			{
				if(e.getLocation().distance(event.getBlock().getLocation()) < 2)
				{
					if(e instanceof LivingEntity)
						entity = (LivingEntity) e;
					v = e.getVelocity();
					if(event.getDirection().name().equalsIgnoreCase("up") && (e.getWorld().getBlockAt(new Location(e.getWorld(), e.getLocation().getBlockX(),e.getLocation().getBlockY()-1,e.getLocation().getBlockZ())).equals(event.getBlock()) || e.getWorld().getBlockAt(new Location(e.getWorld(), e.getLocation().getBlockX(),e.getLocation().getBlockY()-2,e.getLocation().getBlockZ())).equals(event.getBlock())))
					{
						if(e instanceof Player)
							v.setY(v.getY()+(2-(plugin.getTotalWeight((Player) e)*3)));
						else
							v.setY(v.getY()+2);
					}
					else if(event.getDirection().name().equalsIgnoreCase("north") && (e.getWorld().getBlockAt(new Location(e.getWorld(), e.getLocation().getBlockX()+1,e.getLocation().getBlockY(),e.getLocation().getBlockZ())).equals(event.getBlock()) || e.getWorld().getBlockAt(new Location(e.getWorld(), e.getLocation().getBlockX()+2,e.getLocation().getBlockY(),e.getLocation().getBlockZ())).equals(event.getBlock())))
					{
						if(e instanceof Player)
							v.setX(v.getX()-(4-(plugin.getTotalWeight((Player) e))));
						else
							v.setX(v.getX()-4);
					}
					else if(event.getDirection().name().equalsIgnoreCase("south") && (e.getWorld().getBlockAt(new Location(e.getWorld(), e.getLocation().getBlockX()-1,e.getLocation().getBlockY(),e.getLocation().getBlockZ())).equals(event.getBlock()) || e.getWorld().getBlockAt(new Location(e.getWorld(), e.getLocation().getBlockX()-2,e.getLocation().getBlockY(),e.getLocation().getBlockZ())).equals(event.getBlock())))
					{
						if(e instanceof Player)
							v.setX(v.getX()+(4-(plugin.getTotalWeight((Player) e))));
						else
							v.setX(v.getX()+4);
					}
					else if(event.getDirection().name().equalsIgnoreCase("west") && e.getWorld().getBlockAt(new Location(e.getWorld(), e.getLocation().getBlockX(),e.getLocation().getBlockY(),e.getLocation().getBlockZ()-1)).equals(event.getBlock()))
					{
						if(e instanceof Player)
							v.setZ(v.getZ()+(4-(plugin.getTotalWeight((Player) e))));
						else
							v.setZ(v.getZ()+4);
					}
					else if(event.getDirection().name().equalsIgnoreCase("west") && e.getWorld().getBlockAt(new Location(e.getWorld(), e.getLocation().getBlockX(),e.getLocation().getBlockY(),e.getLocation().getBlockZ()+1)).equals(event.getBlock()))
					{
						if(e instanceof Player)
							v.setZ(v.getZ()-(4-(plugin.getTotalWeight((Player) e))));
						else
							v.setZ(v.getZ()-4);
					}
				}
			}
			if(v != null && entity != null && v != entity.getVelocity())
				entity.setVelocity(v);
	    	
	    }
    }
}
