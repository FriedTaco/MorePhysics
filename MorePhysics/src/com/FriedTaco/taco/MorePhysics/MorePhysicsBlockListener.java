package com.FriedTaco.taco.MorePhysics;


import java.util.List;

import net.minecraft.server.EntityFallingBlock;
import net.minecraft.server.Packet51MapChunk;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.util.Vector;

public class MorePhysicsBlockListener extends BlockListener {
	private final MorePhysics plugin;

    public MorePhysicsBlockListener(final MorePhysics plugin) {
        this.plugin = plugin;
    }
    public void destroyGhostEntity(Block block, Entity entity)
    {
    	for(Player p : block.getWorld().getPlayers())
    		if(p.getLocation().distance(block.getLocation()) < 50)
    			((org.bukkit.craftbukkit.entity.CraftPlayer)p).getHandle().netServerHandler.sendPacket(new Packet51MapChunk(block.getX(),block.getY(),block.getZ(), 20, 20, 20, (((CraftWorld) block.getWorld()).getHandle())));
    }
    public void onBlockPistonExtend(BlockPistonExtendEvent event)
    {    	
    	
	    	Vector v = null;
	    	//Block block = null;
	    if(plugin.pistonsB)
	    {
	    	
	    	if(!event.getBlocks().isEmpty())
	    	{
	    		List<Block> b = event.getBlocks();
	    		//for(Block block : event.getBlocks())
	    		//	b.add(block);
	    		for(int i=b.size()-1; i>=0; i--)
	    		{
	    			if(b.get(i).getTypeId() == 12 || b.get(i).getTypeId() == 13)
	    			{
	    			net.minecraft.server.World cWorld = ((CraftWorld)event.getBlock().getWorld()).getHandle();
	  	    	      int id = b.get(i).getTypeId();
	    				if(event.getDirection().name().equalsIgnoreCase("up"))
	    				{
	    					/*
	    					b.get(i).setTypeId(0);
	    					FallingSand s = event.getBlock().getWorld().spawn(new Location(event.getBlock().getWorld(), b.get(i).getX()+.5d,b.get(i).getY()+1,b.get(i).getZ()+.5d), FallingSand.class);
	    					s.setVelocity(s.getVelocity().setY(2));
	    					*/
	    					EntityFallingBlock sand = new EntityFallingBlock(cWorld,b.get(i).getX(),b.get(i).getY(),b.get(i).getZ(),id,b.get(i).getData());
	    					Entity e = sand.getBukkitEntity();
	    					e.teleport(e.getLocation().add(.5,1.5,.5));
	  	  	    	      	//cWorld.addEntity(sand);
	  	  	    	      	Vector vel = sand.getBukkitEntity().getVelocity();
	  	  	    	      	vel.add(new Vector(0,10,0));
	  	  	    	      	sand.getBukkitEntity().setVelocity(vel);
	  	  	    	      	destroyGhostEntity(b.get(i),e);
	    				}
	    				
	    				else if(event.getDirection().name().equalsIgnoreCase("north"))
	    				{
	    					b.get(i).setTypeId(0);
	    					EntityFallingBlock sand = new EntityFallingBlock(cWorld,b.get(i).getX()-.5d,b.get(i).getY()+.5d,b.get(i).getZ()+.5d,id,0);
	    					Vector vel = sand.getBukkitEntity().getVelocity();
	    					vel.add(new Vector(-1.5,.2,0));
	  	  	    	      	cWorld.addEntity(sand);
	  	  	    	      	sand.getBukkitEntity().setVelocity(vel);
	  	  	    	      	destroyGhostEntity(b.get(i),sand.getBukkitEntity());
	  	  	    	      	
	    				}
	    				else if(event.getDirection().name().equalsIgnoreCase("south"))
	    				{
	    					b.get(i).setTypeId(0);
	    					EntityFallingBlock sand = new EntityFallingBlock(cWorld,b.get(i).getX()-.5d,b.get(i).getY()+.5d,b.get(i).getZ()+.5d,id,0);
	    					Vector vel = sand.getBukkitEntity().getVelocity();
	    					vel.add(new Vector(1.5,.2,0));
	  	  	    	      	cWorld.addEntity(sand);
	  	  	    	      	sand.getBukkitEntity().setVelocity(vel);
	  	  	    	      	destroyGhostEntity(b.get(i),sand.getBukkitEntity());	
	    				}
	    				else if(event.getDirection().name().equalsIgnoreCase("west"))
	    				{
	    					b.get(i).setTypeId(0);
	    					EntityFallingBlock sand = new EntityFallingBlock(cWorld,b.get(i).getX()+.5d,b.get(i).getY()+.5d,b.get(i).getZ()-.5d,id,0);
	    					Vector vel = sand.getBukkitEntity().getVelocity();
	    					vel.add(new Vector(0,.2,1.5));
	    					cWorld.addEntity(sand);    
	  	  	    	      	sand.getBukkitEntity().setVelocity(vel);
	  	  	    	      	destroyGhostEntity(b.get(i),sand.getBukkitEntity());	
	    				}
	    				else if(event.getDirection().name().equalsIgnoreCase("east"))
	    				{
	    					b.get(i).setTypeId(0);
	    					EntityFallingBlock sand = new EntityFallingBlock(cWorld,b.get(i).getX()+.5d,b.get(i).getY()+.5d,b.get(i).getZ()-.5d,id,0);
	    					Vector vel = sand.getBukkitEntity().getVelocity();
	    					vel.add(new Vector(0,.2,-1.5));
	    					cWorld.addEntity(sand);    
	  	  	    	      	sand.getBukkitEntity().setVelocity(vel);
	  	  	    	      	destroyGhostEntity(b.get(i),sand.getBukkitEntity());	    	
	    				}
	    				
	    					/*
	    					for(int x=1; x<16; x++)
	    					{
	    						if(b.get(i).getRelative(0,1,0).getTypeId() == 0)
	    						{
	    							b.get(i).getRelative(0,1,0).setTypeId(b.get(i).getTypeId());
	    							b.get(i).setTypeId(0);
	    							b.set(i,b.get(i).getRelative(0,1,0));
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
	    						if(b.get(i).getRelative(-1,0,0).getTypeId() == 0)
	    						{
	    							b.get(i).getRelative(-1,0,0).setTypeId(b.get(i).getTypeId());
	    							b.get(i).setTypeId(0);
	    							b.set(i,b.get(i).getRelative(-1,0,0));
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
	    						if(b.get(i).getRelative(1,0,0).getTypeId() == 0)
	    						{
	    							b.get(i).getRelative(1,0,0).setTypeId(b.get(i).getTypeId());
	    							b.get(i).setTypeId(0);
	    							b.set(i,b.get(i).getRelative(1,0,0));
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
	    						if(b.get(i).getRelative(0,0,1).getTypeId() == 0)
	    						{
	    							b.get(i).getRelative(0,0,1).setTypeId(b.get(i).getTypeId());
	    							b.get(i).setTypeId(0);
	    							b.set(i,b.get(i).getRelative(0,0,1));
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
	    						if(b.get(i).getRelative(0,0,-1).getTypeId() == 0)
	    						{
	    							b.get(i).getRelative(0,0,-1).setTypeId(b.get(i).getTypeId());
	    							b.get(i).setTypeId(0);
	    							b.set(i,b.get(i).getRelative(0,0,-1));
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
	    				break;
	    			}
	    		}	    			
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
					if(event.getDirection().name().equalsIgnoreCase("up") && (block.getRelative(0,-1,0).equals(piston) || e.getWorld().getBlockAt(new Location(e.getWorld(), (int)e.getLocation().getBlockX(),(int)e.getLocation().getBlockY()-1,(int)e.getLocation().getBlockZ())).equals(piston)))
					{
						if(e instanceof Player)
							v.setY(v.getY()+(2-(plugin.getTotalWeight((Player) e)*3)));
						else
							v.setY(v.getY()+2);
						e.teleport(e.getLocation().add(0, 1, 0));	
					}
					else if(event.getDirection().name().equalsIgnoreCase("north") && (block.getRelative(1,0,0).equals(piston) || block.getRelative(1,1,0).equals(piston)))
					{
						if(e instanceof Player)
							v.setX(v.getX()-(4-(plugin.getTotalWeight((Player) e))));
						else
							v.setX(v.getX()-4);
						e.teleport(e.getLocation().add(-1, 0, 0));
					}
					else if(event.getDirection().name().equalsIgnoreCase("south") && (block.getRelative(-1,0,0).equals(piston) || block.getRelative(-1,1,0).equals(piston)))
					{
						if(e instanceof Player)
							v.setX(v.getX()+(4-(plugin.getTotalWeight((Player) e))));
						else
							v.setX(v.getX()+4);
						e.teleport(e.getLocation().add(1, 0, 0));
					}
					else if(event.getDirection().name().equalsIgnoreCase("west") && (block.getRelative(0,0,-1).equals(piston) || block.getRelative(0,1,-1).equals(piston)))
					{
						if(e instanceof Player)
							v.setZ(v.getZ()+(4-(plugin.getTotalWeight((Player) e))));
						else
							v.setZ(v.getZ()+4);
						e.teleport(e.getLocation().add(0, 0, 1));
					}
					else if(event.getDirection().name().equalsIgnoreCase("east") && (block.getRelative(0,0,1).equals(piston) || block.getRelative(0,1,1).equals(piston)))
					{
						if(e instanceof Player)
							v.setZ(v.getZ()-(4-(plugin.getTotalWeight((Player) e))));
						else
							v.setZ(v.getZ()-4);
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
