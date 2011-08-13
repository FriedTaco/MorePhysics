package com.FriedTaco.taco.MorePhysics;


import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.EntityFallingSand;
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
                List<Block> b = new ArrayList<Block>();
                for(Block block : event.getBlocks())
                    if (plugin.configurationfile.getList("exclude_blocks", "\\,").contains(Integer.toString(block.getTypeId())))
                    {
                        continue;
                    }
                    else
                    {
                        b.add(block);
                    }
                for(int i=b.size()-1; i>=0; i--)
                {
                    if(b.get(i).getTypeId() == 12 || b.get(i).getTypeId() == 13)
                    {
                        net.minecraft.server.World cWorld = ((CraftWorld)event.getBlock().getWorld()).getHandle();
                        int id = b.get(i).getTypeId();
                        if(event.getDirection().name().equalsIgnoreCase("up"))
                        {
                            b.get(i).setTypeId(0);
                            EntityFallingSand sand = new EntityFallingSand(cWorld,b.get(i).getX()+.5d,b.get(i).getY()+1,b.get(i).getZ()+.5d,id);
                            cWorld.addEntity(sand);
                            destroyGhostEntity(b.get(i),sand.getBukkitEntity());
                            sand.getBukkitEntity().setVelocity(sand.getBukkitEntity().getVelocity().setY(2));
                        }
                        else if(event.getDirection().name().equalsIgnoreCase("north"))
                        {
                            b.get(i).setTypeId(0);
                            EntityFallingSand sand = new EntityFallingSand(cWorld,b.get(i).getX()-.5d,b.get(i).getY()+.5d,b.get(i).getZ()+.5d,id);
                            Vector vel = sand.getBukkitEntity().getVelocity();
                            vel.add(new Vector(-1.5,.2,0));
                            cWorld.addEntity(sand);
                            sand.getBukkitEntity().setVelocity(vel);
                            destroyGhostEntity(b.get(i),sand.getBukkitEntity());
                            
                        }
                        else if(event.getDirection().name().equalsIgnoreCase("south"))
                        {
                            b.get(i).setTypeId(0);
                            EntityFallingSand sand = new EntityFallingSand(cWorld,b.get(i).getX()-.5d,b.get(i).getY()+.5d,b.get(i).getZ()+.5d,id);
                            Vector vel = sand.getBukkitEntity().getVelocity();
                            vel.add(new Vector(1.5,.2,0));
                            cWorld.addEntity(sand);
                            sand.getBukkitEntity().setVelocity(vel);
                            destroyGhostEntity(b.get(i),sand.getBukkitEntity());    
                        }
                        else if(event.getDirection().name().equalsIgnoreCase("west"))
                        {
                            b.get(i).setTypeId(0);
                            EntityFallingSand sand = new EntityFallingSand(cWorld,b.get(i).getX()+.5d,b.get(i).getY()+.5d,b.get(i).getZ()-.5d,id);
                            Vector vel = sand.getBukkitEntity().getVelocity();
                            vel.add(new Vector(0,.2,1.5));
                            cWorld.addEntity(sand);    
                            sand.getBukkitEntity().setVelocity(vel);
                            destroyGhostEntity(b.get(i),sand.getBukkitEntity());    
                        }
                        else if(event.getDirection().name().equalsIgnoreCase("east"))
                        {
                            b.get(i).setTypeId(0);
                            EntityFallingSand sand = new EntityFallingSand(cWorld,b.get(i).getX()+.5d,b.get(i).getY()+.5d,b.get(i).getZ()-.5d,id);
                            Vector vel = sand.getBukkitEntity().getVelocity();
                            vel.add(new Vector(0,.2,-1.5));
                            cWorld.addEntity(sand);    
                            sand.getBukkitEntity().setVelocity(vel);
                            destroyGhostEntity(b.get(i),sand.getBukkitEntity());            
                        }
                        // removed: see exclusion.txt for excluded code
                    }
                    else
                    {
                        break;
                    }
                }
                //event.getBlock().getWorld().refreshChunk(event.getBlock().getX(), event.getBlock().getZ());
            }
        }   
        if(plugin.pistons)
        {           
            for(Entity e : event.getBlock().getChunk().getEntities())
            {
                if(e.getLocation().distance(event.getBlock().getLocation()) < 2)// && e instanceof LivingEntity)
                {
                    v = e.getVelocity();                    
                    if(event.getDirection().name().equalsIgnoreCase("up") && (e.getLocation().getBlock().getRelative(0,-1,0).equals(event.getBlock()) || e.getWorld().getBlockAt(new Location(e.getWorld(), (int)e.getLocation().getBlockX(),(int)e.getLocation().getBlockY()-1,(int)e.getLocation().getBlockZ())).equals(event.getBlock())))
                    {
                        if(e instanceof Player)
                            v.setY(v.getY()+(2-(plugin.getTotalWeight((Player) e)*3)));
                        else
                            v.setY(v.getY()+2);
                        e.teleport(e.getLocation().add(0, 1, 0));   
                    }
                    else if(event.getDirection().name().equalsIgnoreCase("north") && e.getLocation().getBlock().getRelative(1,0,0).equals(event.getBlock()) || e.getWorld().getBlockAt(new Location(e.getWorld(), (int)e.getLocation().getBlockX()+1,(int)e.getLocation().getBlockY(),(int)e.getLocation().getBlockZ())).equals(event.getBlock()))
                    {
                        if(e instanceof Player)
                            v.setX(v.getX()-(4-(plugin.getTotalWeight((Player) e))));
                        else
                            v.setX(v.getX()-4);
                        e.teleport(e.getLocation().add(-1, 0, 0));
                    }
                    else if(event.getDirection().name().equalsIgnoreCase("south") && e.getLocation().getBlock().getRelative(-1,0,0).equals(event.getBlock()) || e.getWorld().getBlockAt(new Location(e.getWorld(), (int)e.getLocation().getBlockX()-1,(int)e.getLocation().getBlockY(),(int)e.getLocation().getBlockZ())).equals(event.getBlock()))
                    {
                        if(e instanceof Player)
                            v.setX(v.getX()+(4-(plugin.getTotalWeight((Player) e))));
                        else
                            v.setX(v.getX()+4);
                        e.teleport(e.getLocation().add(1, 0, 0));
                    }
                    else if(event.getDirection().name().equalsIgnoreCase("west") && e.getLocation().getBlock().getRelative(0,0,-1).equals(event.getBlock()) || e.getWorld().getBlockAt(new Location(e.getWorld(), (int)e.getLocation().getBlockX(),(int)e.getLocation().getBlockY(),(int)e.getLocation().getBlockZ()-1)).equals(event.getBlock()))
                    {
                        if(e instanceof Player)
                            v.setZ(v.getZ()+(4-(plugin.getTotalWeight((Player) e))));
                        else
                            v.setZ(v.getZ()+4);
                        e.teleport(e.getLocation().add(0, 0, 1));
                    }
                    else if(event.getDirection().name().equalsIgnoreCase("west") && e.getLocation().getBlock().getRelative(0,0,1).equals(event.getBlock()) || e.getWorld().getBlockAt(new Location(e.getWorld(), (int)e.getLocation().getBlockX(),(int)e.getLocation().getBlockY(),(int)e.getLocation().getBlockZ()+1)).equals(event.getBlock()))
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
