package com.FriedTaco.taco.MorePhysics;

import org.bukkit.Location;
//import org.bukkit.block.Block;
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
	    	Vector v = null;
	    	LivingEntity entity = null;
	    	//Block block = null;
			for(final Entity e : event.getBlock().getChunk().getEntities())
			{
				if(e.getLocation().distance(event.getBlock().getLocation()) < 2 && e instanceof LivingEntity)
				{
					entity = (LivingEntity) e;
					v = e.getVelocity();
					System.out.println(e.getWorld().getBlockAt(new Location(e.getWorld(), (int)e.getLocation().getBlockX(),(int)e.getLocation().getBlockY()-1,(int)e.getLocation().getBlockZ())).getTypeId());
					if(event.getDirection().name().equalsIgnoreCase("up") && (e.getLocation().getBlock().getRelative(0,-1,0).equals(event.getBlock()) || e.getWorld().getBlockAt(new Location(e.getWorld(), (int)e.getLocation().getBlockX(),(int)e.getLocation().getBlockY()-1,(int)e.getLocation().getBlockZ())).equals(event.getBlock())))
					{
						/*
	                        if(e instanceof Player)
	                            v.setY(v.getY()+(2-(plugin.getTotalWeight((Player) e)*3)));
	                        else
	                            v.setY(v.getY()+2);
	                        final Vector vel = v;
	                        final LivingEntity entity = (LivingEntity) e;
	                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
	                            public void run() {
	                                entity.setVelocity(vel);
	                            }
	                        }, 3L);
	                        */
						
						//System.out.println("WOOSH!");
						if(e instanceof Player)
							v.setY(v.getY()+(2-(plugin.getTotalWeight((Player) e)*3)));
						else
							v.setY(v.getY()+2);
						entity.teleport(entity.getLocation().add(0, 1, 0));
							
						/*
						for(int y=0;y<=2;y++)
							for(int z=-1;z<=1;z++)
								for(int x=-1;x<=1;x++)
								{
									block = e.getWorld().getBlockAt(new Location(e.getWorld(), e.getLocation().getBlockX()+x,e.getLocation().getBlockY()-y,e.getLocation().getBlockZ()+z));
									if(block.getTypeId() == 33)
									{
										if(e instanceof Player)
											v.setY(v.getY()+(2-(plugin.getTotalWeight((Player) e)*3)));
										else
											v.setY(v.getY()+2);
										break;
									}
								}
						 */
								
					}
					else if(event.getDirection().name().equalsIgnoreCase("north") && e.getLocation().getBlock().getRelative(1,0,0).equals(event.getBlock()) || e.getWorld().getBlockAt(new Location(e.getWorld(), (int)e.getLocation().getBlockX()+1,(int)e.getLocation().getBlockY(),(int)e.getLocation().getBlockZ())).equals(event.getBlock()))
					{
						if(e instanceof Player)
							v.setX(v.getX()-(4-(plugin.getTotalWeight((Player) e))));
						else
							v.setX(v.getX()-4);
						entity.teleport(entity.getLocation().add(-1, 0, 0));
					}
					else if(event.getDirection().name().equalsIgnoreCase("south") && e.getLocation().getBlock().getRelative(-1,0,0).equals(event.getBlock()) || e.getWorld().getBlockAt(new Location(e.getWorld(), (int)e.getLocation().getBlockX()-1,(int)e.getLocation().getBlockY(),(int)e.getLocation().getBlockZ())).equals(event.getBlock()))
					{
						if(e instanceof Player)
							v.setX(v.getX()+(4-(plugin.getTotalWeight((Player) e))));
						else
							v.setX(v.getX()+4);
						entity.teleport(entity.getLocation().add(1, 0, 0));
					}
					else if(event.getDirection().name().equalsIgnoreCase("west") && e.getLocation().getBlock().getRelative(0,0,-1).equals(event.getBlock()) || e.getWorld().getBlockAt(new Location(e.getWorld(), (int)e.getLocation().getBlockX(),(int)e.getLocation().getBlockY(),(int)e.getLocation().getBlockZ()-1)).equals(event.getBlock()))
					{
						if(e instanceof Player)
							v.setZ(v.getZ()+(4-(plugin.getTotalWeight((Player) e))));
						else
							v.setZ(v.getZ()+4);
						entity.teleport(entity.getLocation().add(0, 0, 1));
					}
					else if(event.getDirection().name().equalsIgnoreCase("west") && e.getLocation().getBlock().getRelative(0,0,1).equals(event.getBlock()) || e.getWorld().getBlockAt(new Location(e.getWorld(), (int)e.getLocation().getBlockX(),(int)e.getLocation().getBlockY(),(int)e.getLocation().getBlockZ()+1)).equals(event.getBlock()))
					{
						if(e instanceof Player)
							v.setZ(v.getZ()-(4-(plugin.getTotalWeight((Player) e))));
						else
							v.setZ(v.getZ()-4);
						entity.teleport(entity.getLocation().add(0, 0, -1));
					}
				}
			}
			if(v != null && entity != null && v != entity.getVelocity())
			{
				entity.setVelocity(v);
			}
				
	    	
	    }
    }
}
