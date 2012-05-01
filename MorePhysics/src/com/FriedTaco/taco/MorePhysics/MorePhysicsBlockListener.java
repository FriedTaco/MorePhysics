package com.FriedTaco.taco.MorePhysics;


import java.util.List;

import net.minecraft.server.EntityFallingBlock;
import net.minecraft.server.Packet51MapChunk;
import net.minecraft.server.WorldServer;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftFallingSand;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingSand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
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
	    			/*
	    			net.minecraft.server.World cWorld = ((CraftWorld)w).getHandle();
	  	    	      int id = block.getTypeId();
	    				if(event.getDirection().name().equalsIgnoreCase("up"))
	    				{
	    					EntityFallingBlock sand = new EntityFallingBlock(cWorld,block.getX(),block.getY()+2,block.getZ(),id,block.getData());
	    					CraftFallingSand c = new CraftFallingSand(cWorld.getServer(), sand);
	    					System.out.println("ID: "+id+" Data: "+block.getData());
	    					((WorldServer) cWorld).tracker.track(sand);
	    					System.out.println("Block Y: "+block.getY()+" Sand Y: "+sand.locY);
	    					cWorld.addEntity(sand);
	    					Entity e = sand.getBukkitEntity();
	    					//e.teleport(e.getLocation().add(.5,1.5,.5));
	    					//w.spawnCreature(e.getLocation().add(.5,4.5,.5), EntityType.FALLING_BLOCK);
	  	  	    	      	Vector vel = e.getVelocity();
	  	  	    	      	vel.add(new Vector(0,2,0));
	  	  	    	      	c.setVelocity(vel);
	  	  	    	      	//e.setVelocity(vel);
	  	  	    	      	destroyGhostEntity(block,e,new Vector(0,1,0));
	    				}
	    				
	    				else if(event.getDirection().name().equalsIgnoreCase("north"))
	    				{
	    					block.setTypeId(0);
	    					EntityFallingBlock sand = new EntityFallingBlock(cWorld,block.getX()-.5d,block.getY()+.5d,block.getZ()+.5d,id,0);
	    					Vector vel = sand.getBukkitEntity().getVelocity();
	    					vel.add(new Vector(-1.5,.2,0));
	  	  	    	      	cWorld.addEntity(sand);
	  	  	    	      	sand.getBukkitEntity().setVelocity(vel);
	  	  	    	      	destroyGhostEntity(block,sand.getBukkitEntity(),new Vector(-1,0,0));
	  	  	    	      	
	    				}
	    				else if(event.getDirection().name().equalsIgnoreCase("south"))
	    				{
	    					block.setTypeId(0);
	    					EntityFallingBlock sand = new EntityFallingBlock(cWorld,block.getX()-.5d,block.getY()+.5d,block.getZ()+.5d,id,0);
	    					Vector vel = sand.getBukkitEntity().getVelocity();
	    					vel.add(new Vector(1.5,.2,0));
	  	  	    	      	cWorld.addEntity(sand);
	  	  	    	      	sand.getBukkitEntity().setVelocity(vel);
	  	  	    	      	destroyGhostEntity(block,sand.getBukkitEntity(),new Vector(1,0,0));	
	    				}
	    				else if(event.getDirection().name().equalsIgnoreCase("west"))
	    				{
	    					block.setTypeId(0);
	    					EntityFallingBlock sand = new EntityFallingBlock(cWorld,block.getX()+.5d,block.getY()+.5d,block.getZ()-.5d,id,0);
	    					Vector vel = sand.getBukkitEntity().getVelocity();
	    					vel.add(new Vector(0,.2,1.5));
	    					cWorld.addEntity(sand);    
	  	  	    	      	sand.getBukkitEntity().setVelocity(vel);
	  	  	    	      	destroyGhostEntity(block,sand.getBukkitEntity(),new Vector(0,0,1));	
	    				}
	    				else if(event.getDirection().name().equalsIgnoreCase("east"))
	    				{
	    					block.setTypeId(0);
	    					EntityFallingBlock sand = new EntityFallingBlock(cWorld,block.getX()+.5d,block.getY()+.5d,block.getZ()-.5d,id,0);
	    					Vector vel = sand.getBukkitEntity().getVelocity();
	    					vel.add(new Vector(0,.2,-1.5));
	    					cWorld.addEntity(sand);    
	  	  	    	      	sand.getBukkitEntity().setVelocity(vel);
	  	  	    	      	destroyGhostEntity(block,sand.getBukkitEntity(),new Vector(0,0,-1));	    	
	    				}
	    			}
	    			else
	    			{
	    				break;
	    			}
	    		}	
	    		*/
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
                 			//b.set(i,block.getRelative(0,1,0));
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
                 			//b.set(i,block.getRelative(-1,0,0));
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
                 			//b.set(i,block.getRelative(1,0,0));
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
                 			//b.set(i,block.getRelative(0,0,1));
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
                 			//b.set(i,block.getRelative(0,0,-1));
                 		}
          				else
         				{
             				break;
                		}
          			}	
           		  }
	    		 }
	    			else
	    			{
	    				if(plugin.pistonsC)
	    				{
		    				for(Entity e : event.getBlock().getChunk().getEntities())
		    				{
		    					if(e.getLocation().distance(event.getBlock().getLocation()) < 2.2)
		    					{
		    						v = e.getVelocity();
		    						Block ent = e.getLocation().getBlock();
		    						if(event.getDirection().name().equalsIgnoreCase("up") && (ent.getRelative(0,-1,0).equals(block)))
		    						{
		    							if(e instanceof Player)
		    								v.setY(v.getY()+(plugin.pistonStrength/2-(plugin.weights.get(((Player)e).getName())*3)));
		    							else
		    								v.setY(v.getY()+plugin.pistonStrength/2);
		    							e.teleport(e.getLocation().add(0, 1, 0));	
		    						}
				    				else if(event.getDirection().name().equalsIgnoreCase("north"))
				              		{
				    					
				              		}
				    				else if(event.getDirection().name().equalsIgnoreCase("south"))
				              		{
				    					
				              		}
				    				else if(event.getDirection().name().equalsIgnoreCase("east"))
				              		{
				    					
				              		}
				    				else if(event.getDirection().name().equalsIgnoreCase("west"))
				              		{
				    					
				              		}
		    					}
		    				}
		    			}
	    			}
	    		}
	    	}
	    }
	    //
	    if(plugin.pistons)
	    {			
			for(Entity e : event.getBlock().getChunk().getEntities())
			{
				if(e.getLocation().distance(event.getBlock().getLocation()) < 2.2)
				{
					Player p = null;
					if(e instanceof Player)
						p = (Player)e;
					v = e.getVelocity();
					Block block = e.getLocation().getBlock();
					Block piston = event.getBlock();
					if(event.getDirection().name().equalsIgnoreCase("up") && (block.getRelative(0,-1,0).equals(piston)))
					{
						if(e instanceof Player)
							v.setY(v.getY()+(plugin.pistonStrength/2-(plugin.weights.get(p.getName())*3)));
						else
							v.setY(v.getY()+plugin.pistonStrength/2);
						e.teleport(e.getLocation().add(0, 1, 0));	
					}
					else if(event.getDirection().name().equalsIgnoreCase("north") && (block.getRelative(1,0,0).equals(piston) || block.getRelative(1,1,0).equals(piston)))
					{
						if(e instanceof Player)
							v.setX(v.getX()-(plugin.pistonStrength-(plugin.weights.get(p.getName()))));
						else
							v.setX(v.getX()-plugin.pistonStrength);
						e.teleport(e.getLocation().add(-1, 0, 0));
					}
					else if(event.getDirection().name().equalsIgnoreCase("south") && (block.getRelative(-1,0,0).equals(piston) || block.getRelative(-1,1,0).equals(piston)))
					{
						if(e instanceof Player)
							v.setX(v.getX()+(plugin.pistonStrength-(plugin.weights.get(p.getName()))));
						else
							v.setX(v.getX()+plugin.pistonStrength);
						e.teleport(e.getLocation().add(1, 0, 0));
					}
					else if(event.getDirection().name().equalsIgnoreCase("west") && (block.getRelative(0,0,-1).equals(piston) || block.getRelative(0,1,-1).equals(piston)))
					{
						if(e instanceof Player)
							v.setZ(v.getZ()+(plugin.pistonStrength-(plugin.weights.get(p.getName()))));
						else
							v.setZ(v.getZ()+plugin.pistonStrength);
						e.teleport(e.getLocation().add(0, 0, 1));
					}
					else if(event.getDirection().name().equalsIgnoreCase("east") && (block.getRelative(0,0,1).equals(piston) || block.getRelative(0,1,1).equals(piston)))
					{
						if(e instanceof Player)
							v.setZ(v.getZ()-(plugin.pistonStrength-(plugin.weights.get(p.getName()))));
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
