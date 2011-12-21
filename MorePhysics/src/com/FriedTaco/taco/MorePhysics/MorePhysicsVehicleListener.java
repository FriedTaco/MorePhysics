package com.FriedTaco.taco.MorePhysics;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Boat;
import org.bukkit.event.Cancellable;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;


public class MorePhysicsVehicleListener extends VehicleListener implements Cancellable
{
	private final MorePhysics plugin;
	
    public MorePhysicsVehicleListener(MorePhysics instance) {
        plugin = instance;
    }
    
    public void onVehicleDamage(VehicleDamageEvent	event)
    {
    	if(plugin.boats && event.getVehicle() instanceof Boat)
    	{
    		Boat b = (Boat)event.getVehicle();
	    	if(!MorePhysics.sinking.contains(b) && !b.isDead() && (event.getDamage() >= 2))
	    	{
	    		MorePhysics.sinking.add(b);
	    		b.setVelocity(b.getVelocity().subtract(new Vector(0,.2,0)));
	    	}
    	}
    }
    
	public void onVehicleDestroy(VehicleDestroyEvent event) 	
	{
		if(event.getVehicle() instanceof Boat && MorePhysics.sinking.contains((Boat) event.getVehicle()))
    		MorePhysics.sinking.remove((Boat) event.getVehicle());		
	}
	
	public void onVehicleMove(VehicleMoveEvent event)
	{
		if(plugin.boats)
		{
			if(event.getVehicle() instanceof Boat && MorePhysics.sinking.contains((Boat) event.getVehicle()))
			{
				Block on = event.getVehicle().getWorld().getBlockAt(event.getTo());
				Block under = event.getVehicle().getWorld().getBlockAt(new Location(event.getVehicle().getWorld(), event.getTo().getBlockX(), event.getTo().getBlockY()-1,event.getTo().getBlockZ()));
				if(on.getType() == Material.WATER || under.getType() == Material.WATER)
				{
					Vector v  = event.getVehicle().getVelocity();
					v.subtract(new Vector(0,.05,0));
					event.getVehicle().setVelocity(v);
				}
			}
		}
	}
	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setCancelled(boolean arg0) {
		// TODO Auto-generated method stub
		
	}
	
}