package com.FriedTaco.taco.MorePhysics;


import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

@SuppressWarnings("unused")
public class MorePhysicsBlockListener implements Listener {
	private final MorePhysics plugin;

    public MorePhysicsBlockListener(final MorePhysics plugin) {
        this.plugin = plugin;
    }
    /* Deprecated
    public void destroyGhostEntity(Block block, Entity entity)
    {
    	for(Player p : block.getWorld().getPlayers())
    		if(p.getLocation().distance(block.getLocation()) < 50)
    			((org.bukkit.craftbukkit.entity.CraftPlayer)p).getHandle().netServerHandler.sendPacket(new Packet51MapChunk(block.getX(),block.getY(),block.getZ(), 20, 20, 20, (((CraftWorld) block.getWorld()).getHandle())));
    }
    */
    
    /*
    / Deprecated 
    public void destroyGhostEntity(Block block, Entity entity, Vector add)
    {
    	Location bLoc = block.getLocation();
    	Location adj = bLoc.add(add);
    	for(Player p : block.getWorld().getPlayers())
    	{
    		if(p.getLocation().distance(block.getLocation()) < 50)
    		{
    			p.sendBlockChange(bLoc, 0, (byte) 0);
    			p.sendBlockChange(adj, 0, (byte) 0);
    		}
    	}
    }
    */
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled=true)
    public void onBlockPistonExtend(BlockPistonExtendEvent event)
    {    	
	    Vector v = null;
	    if(plugin.pistonBlocks)
	    {
	    	if(!event.getBlocks().isEmpty())
	    	{
	    		List<Block> b = event.getBlocks();
	    		World w = event.getBlock().getWorld();
	    		for(int i=b.size()-1; i>=0; i--)
	    		{
                            Block block = b.get(i);
                                if(block.getType().equals(Material.SAND) || block.getType().equals(Material.GRAVEL))
	    			{
                                    event.setCancelled(true);
                                    Location tmp = block.getLocation();
                                    tmp.setY(tmp.getY()+0.1);
                                    MaterialData mat = new MaterialData(block.getType());
                                    FallingBlock f = w.spawnFallingBlock(tmp, mat);
                                    v = f.getVelocity().clone();
                                    // block.setType(Material.AIR); // Not working?
                                    // plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                    //  public void run() {
                                    block.setType(Material.AIR);
                                    block.getState().update();
                                    //     }
                                    //}, 20L);
                                    Location loc = block.getLocation();
                                    Block loc1 = f.getLocation().getBlock();
                                    Block loc2 = loc1.getRelative(BlockFace.UP);
                                    Block piston = event.getBlock();
                                    Block rel = piston.getRelative(event.getDirection());
                                    if(!loc1.equals(rel) == !loc2.equals(rel))
                                        rel=rel.getRelative(event.getDirection());
                                    if(loc1.equals(rel) || loc2.equals(rel))
                                    {
					Vector diff = rel.getLocation().subtract(piston.getLocation()).toVector();
					diff.multiply(plugin.pistonStrength/5000); // Block is too fast, need to slow it down a bit.
					v.add(diff);
					f.setVelocity(v);
                                    }
                                    /*
		    		// Old code, for use if sand won't work. CURRENTLY IN USE.
                                    if(event.getDirection().name().equalsIgnoreCase("up"))
                                    {
                                    for(int x=1; x<16; x++)
                                    {
                                            if(block.getRelative(0,1,0).getTypeId() == 0)
                                            {
                                                    block.getRelative(0,1,0).setTypeId(block.getTypeId());
                                                    block.setTypeId(0);
                                                    block = block.getRelative(0,1,0);
                                                    if(i==0 && x==1)
                                                    {
                                                            block.getRelative(0,-1,0).setTypeId(0);
                                                    }
                                            }
                                                    else
                                                    {
                                                    break;
                                            }
                                            }	
                                    }
					else if(event.getDirection().name().equalsIgnoreCase("north"))
	          		{
	                	for(int x=1; x<8; x++)
	                	{
	                		if(block.getRelative(-1,0,0).getTypeId() == 0)
	                 		{
	                 			block.getRelative(-1,0,0).setTypeId(block.getTypeId());
	                			block.setTypeId(0);
	                			block = block.getRelative(-1,0,0);
	                 			if(i==0 && x==1)
	                 			{
	                 				block.getRelative(1,0,0).setTypeId(0);
	                 			}
	                 		}
	          				else
	         				{
	             				break;
	                		}
	          			}	
	           		}	  	
					else if(event.getDirection().name().equalsIgnoreCase("south"))
	          		{
	                	for(int x=1; x<8; x++)
	                	{
	                		if(block.getRelative(1,0,0).getTypeId() == 0)
	                 		{
	                 			block.getRelative(1,0,0).setTypeId(block.getTypeId());
	                			block.setTypeId(0);
	                			block = block.getRelative(1,0,0);
	                 			if(i==0 && x==1)
	                 			{
	                 				block.getRelative(-1,0,0).setTypeId(0);
	                 			}
	                 		}
	          				else
	         				{
	             				break;
	                		}
	          			}	
	           		}	
					else if(event.getDirection().name().equalsIgnoreCase("west"))
	          		{
	                	for(int x=1; x<8; x++)
	                	{
	                		if(block.getRelative(0,0,1).getTypeId() == 0)
	                 		{
	                 			block.getRelative(0,0,1).setTypeId(block.getTypeId());
	                			block.setTypeId(0);
	                			block = block.getRelative(0,0,1);
	                 			if(i==0 && x==1)
	                 			{
	                 				block.getRelative(0,0,-1).setTypeId(0);
	                 			}
	                 		}
	          				else
	         				{
	             				break;
	                		}
	          			}	
	           		}	
	        		else if(event.getDirection().name().equalsIgnoreCase("east"))
	          		{
	                	for(int x=1; x<8; x++)
	                	{
	                		if(block.getRelative(0,0,-1).getTypeId() == 0)
	                 		{
	                 			block.getRelative(0,0,-1).setTypeId(block.getTypeId());
	                			block.setTypeId(0);
	                			block = block.getRelative(0,0,-1);
	                 			if(i==0 && x==1)
	                 			{
	                 				block.getRelative(0,0,1).setTypeId(0);
	                 			}
	                 		}
	          				else
	         				{
	             				break;
	                		}
	          			}	
	           		  }
                                    */
	    		 }
	    			else
	    			{
	    				if(plugin.pistonPushBlockEntities)
	    				{
		    				for(Entity e : event.getBlock().getChunk().getEntities())
		    				{
		    					v = e.getVelocity().clone();
		    					BlockFace dir = event.getDirection();
		    					Block loc1 = e.getLocation().getBlock();
		    					Block loc2 = loc1.getRelative(BlockFace.UP);
		    					Block rel = block.getRelative(event.getDirection());
		    					if(!loc1.equals(rel) == !loc2.equals(rel))
		    						rel=rel.getRelative(event.getDirection());
		    					if(loc1.equals(rel) || loc2.equals(rel))
		    					{
		    						Vector diff = rel.getLocation().subtract(block.getLocation()).toVector();
		    						e.teleport(e.getLocation().add(diff));
		    						double weight = 0;
		    						if(e instanceof Player)
		    						{
		    							Player p = (Player) e;
		    							weight = plugin.weights.get(p.getName());
		    						}
		    						if(dir.equals(BlockFace.UP))
		    							diff.multiply(plugin.pistonStrength/2-weight);
		    						else
		    							diff.multiply(plugin.pistonStrength-weight);
		    						v.add(diff);
		    						e.setVelocity(v);
		    					}
		    					
		    				}
		    			}
	    			}
	    		}
	    	}
	    }
	    if(plugin.pistonEntities)
	    {			
			for(Entity e : event.getBlock().getChunk().getEntities())
			{
				
				v = e.getVelocity().clone();
				BlockFace dir = event.getDirection();
				Block loc1 = e.getLocation().getBlock();
				Block loc2 = loc1.getRelative(BlockFace.UP);
				Block piston = event.getBlock();
				Block rel = piston.getRelative(event.getDirection());
				if(!loc1.equals(rel) == !loc2.equals(rel))
					rel=rel.getRelative(event.getDirection());
				if(loc1.equals(rel) || loc2.equals(rel))
				{
					Vector diff = rel.getLocation().subtract(piston.getLocation()).toVector();
					e.teleport(e.getLocation().add(diff));
					diff.multiply(plugin.pistonStrength);
					v.add(diff);
					e.setVelocity(v);
				}
			}
	    }
    }
}
