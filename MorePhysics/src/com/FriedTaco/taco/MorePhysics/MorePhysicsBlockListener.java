package com.FriedTaco.taco.MorePhysics;


import java.util.List;

import net.minecraft.server.EntityFallingBlock;
import net.minecraft.server.Packet51MapChunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingSand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.util.Vector;

@SuppressWarnings("unused")
public class MorePhysicsBlockListener implements Listener {
	private final MorePhysics plugin;

    public MorePhysicsBlockListener(final MorePhysics plugin) {
        this.plugin = plugin;
    }
    /* Depreciated
    public void destroyGhostEntity(Block block, Entity entity)
    {
    	for(Player p : block.getWorld().getPlayers())
    		if(p.getLocation().distance(block.getLocation()) < 50)
    			((org.bukkit.craftbukkit.entity.CraftPlayer)p).getHandle().netServerHandler.sendPacket(new Packet51MapChunk(block.getX(),block.getY(),block.getZ(), 20, 20, 20, (((CraftWorld) block.getWorld()).getHandle())));
    }
    */
    public void destroyGhostEntity(Block block, Entity entity)
    {
    	for(Player p : block.getWorld().getPlayers())
    		if(p.getLocation().distance(block.getLocation()) < 50)
    			p.sendBlockChange(block.getLocation(), 0, (byte) 0);
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPistonExtend(BlockPistonExtendEvent event)
    {    	
    	
	    	Vector v = null;
	    	//Block block = null;
	    if(plugin.pistonsB)
	    {
	    	if(!event.getBlocks().isEmpty())
	    	{
	    		List<Block> b = event.getBlocks();
	    		World w = event.getBlock().getWorld();
	    		for(int i=b.size()-1; i>=0; i--)
	    		{
	    			Block block = b.get(i);
	    			if(block.getTypeId() == 12 || block.getTypeId() == 13)
	    			{
	    			net.minecraft.server.World cWorld = ((CraftWorld)w).getHandle();
	  	    	      int id = block.getTypeId();
	    				if(event.getDirection().name().equalsIgnoreCase("up"))
	    				{
	    					EntityFallingBlock sand = new EntityFallingBlock(cWorld,block.getX(),block.getY(),block.getZ(),id,block.getData());
	    					cWorld.addEntity(sand);
	    					Entity e = sand.getBukkitEntity();
	    					e.setTicksLived(20);
	    					e.teleport(e.getLocation().add(.5,1.5,.5));
	  	  	    	      	Vector vel = e.getVelocity();
	  	  	    	      	vel.add(new Vector(0,10,0));
	  	  	    	      	e.setVelocity(vel);
	  	  	    	      	e.teleport(e.getLocation().getBlock().getRelative(0,10,0).getLocation());
	  	  	    	      	destroyGhostEntity(block,e);
	    				}
	    				
	    				else if(event.getDirection().name().equalsIgnoreCase("north"))
	    				{
	    					block.setTypeId(0);
	    					EntityFallingBlock sand = new EntityFallingBlock(cWorld,block.getX()-.5d,block.getY()+.5d,block.getZ()+.5d,id,0);
	    					Vector vel = sand.getBukkitEntity().getVelocity();
	    					vel.add(new Vector(-1.5,.2,0));
	  	  	    	      	cWorld.addEntity(sand);
	  	  	    	      	sand.getBukkitEntity().setVelocity(vel);
	  	  	    	      	destroyGhostEntity(block,sand.getBukkitEntity());
	  	  	    	      	
	    				}
	    				else if(event.getDirection().name().equalsIgnoreCase("south"))
	    				{
	    					block.setTypeId(0);
	    					EntityFallingBlock sand = new EntityFallingBlock(cWorld,block.getX()-.5d,block.getY()+.5d,block.getZ()+.5d,id,0);
	    					Vector vel = sand.getBukkitEntity().getVelocity();
	    					vel.add(new Vector(1.5,.2,0));
	  	  	    	      	cWorld.addEntity(sand);
	  	  	    	      	sand.getBukkitEntity().setVelocity(vel);
	  	  	    	      	destroyGhostEntity(block,sand.getBukkitEntity());	
	    				}
	    				else if(event.getDirection().name().equalsIgnoreCase("west"))
	    				{
	    					block.setTypeId(0);
	    					EntityFallingBlock sand = new EntityFallingBlock(cWorld,block.getX()+.5d,block.getY()+.5d,block.getZ()-.5d,id,0);
	    					Vector vel = sand.getBukkitEntity().getVelocity();
	    					vel.add(new Vector(0,.2,1.5));
	    					cWorld.addEntity(sand);    
	  	  	    	      	sand.getBukkitEntity().setVelocity(vel);
	  	  	    	      	destroyGhostEntity(block,sand.getBukkitEntity());	
	    				}
	    				else if(event.getDirection().name().equalsIgnoreCase("east"))
	    				{
	    					block.setTypeId(0);
	    					EntityFallingBlock sand = new EntityFallingBlock(cWorld,block.getX()+.5d,block.getY()+.5d,block.getZ()-.5d,id,0);
	    					Vector vel = sand.getBukkitEntity().getVelocity();
	    					vel.add(new Vector(0,.2,-1.5));
	    					cWorld.addEntity(sand);    
	  	  	    	      	sand.getBukkitEntity().setVelocity(vel);
	  	  	    	      	destroyGhostEntity(block,sand.getBukkitEntity());	    	
	    				}
	    			}
	    			else
	    			{
	    				break;
	    			}
	    		}	
	    		/* Old code, for use if sand won't work.
				if(event.getDirection().name().equalsIgnoreCase("up"))
          		{
                	for(int x=1; x<16; x++)
                	{
                		if(block.getRelative(0,1,0).getTypeId() == 0)
                 		{
                 			block.getRelative(0,1,0).setTypeId(block.getTypeId());
                			block.setTypeId(0);
                 			b.set(i,block.getRelative(0,1,0));
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
                 			b.set(i,block.getRelative(-1,0,0));
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
                 			b.set(i,block.getRelative(1,0,0));
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
                 			b.set(i,block.getRelative(0,0,1));
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
                 			b.set(i,block.getRelative(0,0,-1));
                 		}
          				else
         				{
             				break;
                		}
          			}	
           		}
       */ 
	     	}
    	}	
	    if(plugin.pistons)
	    {			
			for(Entity e : event.getBlock().getChunk().getEntities())
			{
				if(e.getLocation().distance(event.getBlock().getLocation()) < 2.2)
				{
					v = e.getVelocity();
					Block block = e.getLocation().getBlock();
					Block piston = event.getBlock();
					if(event.getDirection().name().equalsIgnoreCase("up") && (block.getRelative(0,-1,0).equals(piston)))
					{
						System.out.println(e.getType());
						if(e instanceof Player)
							v.setY(v.getY()+(plugin.pistonStrength/2-(plugin.getTotalWeight((Player) e)*3)));
						else
							v.setY(v.getY()+plugin.pistonStrength/2);
						e.teleport(e.getLocation().add(0, 1, 0));	
					}
					else if(event.getDirection().name().equalsIgnoreCase("north") && (block.getRelative(1,0,0).equals(piston) || block.getRelative(1,1,0).equals(piston)))
					{
						if(e instanceof Player)
							v.setX(v.getX()-(plugin.pistonStrength-(plugin.getTotalWeight((Player) e))));
						else
							v.setX(v.getX()-plugin.pistonStrength);
						e.teleport(e.getLocation().add(-1, 0, 0));
					}
					else if(event.getDirection().name().equalsIgnoreCase("south") && (block.getRelative(-1,0,0).equals(piston) || block.getRelative(-1,1,0).equals(piston)))
					{
						if(e instanceof Player)
							v.setX(v.getX()+(plugin.pistonStrength-(plugin.getTotalWeight((Player) e))));
						else
							v.setX(v.getX()+plugin.pistonStrength);
						e.teleport(e.getLocation().add(1, 0, 0));
					}
					else if(event.getDirection().name().equalsIgnoreCase("west") && (block.getRelative(0,0,-1).equals(piston) || block.getRelative(0,1,-1).equals(piston)))
					{
						if(e instanceof Player)
							v.setZ(v.getZ()+(plugin.pistonStrength-(plugin.getTotalWeight((Player) e))));
						else
							v.setZ(v.getZ()+plugin.pistonStrength);
						e.teleport(e.getLocation().add(0, 0, 1));
					}
					else if(event.getDirection().name().equalsIgnoreCase("east") && (block.getRelative(0,0,1).equals(piston) || block.getRelative(0,1,1).equals(piston)))
					{
						if(e instanceof Player)
							v.setZ(v.getZ()-(plugin.pistonStrength-(plugin.getTotalWeight((Player) e))));
						else
							v.setZ(v.getZ()-plugin.pistonStrength);
						e.teleport(e.getLocation().add(0, 0, -1));
					}
				}
				if(v != null && e != null && v != e.getVelocity())
				{
					e.setVelocity(v);
				}
				v=null;
			}
			
				
	    	
	    }
    }
}
