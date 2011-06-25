package com.FriedTaco.taco.MorePhysics;

import org.bukkit.Location;
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
    	if(event.getVehicle() instanceof Boat && !MorePhysics.sinking.contains((Boat) event.getVehicle()) && !event.getVehicle().isDead() && event.getDamage() > 2 && plugin.boats)
    	{
    		MorePhysics.sinking.add((Boat) event.getVehicle());
    	}
    }
	public void onVehicleDestroy(VehicleDestroyEvent event) 	
	{
		if(event.getVehicle() instanceof Boat && MorePhysics.sinking.contains((Boat) event.getVehicle()) && plugin.boats)
    	{
    		MorePhysics.sinking.remove((Boat) event.getVehicle());
    	}		
	}
	public void onVehicleMove(VehicleMoveEvent event)
	{
		if(plugin.boats)
		{
			Block on = event.getVehicle().getWorld().getBlockAt(event.getTo());
			Block under = event.getVehicle().getWorld().getBlockAt(new Location(event.getVehicle().getWorld(), event.getTo().getBlockX(), event.getTo().getBlockY()-1,event.getTo().getBlockZ()));
			if(event.getVehicle() instanceof Boat && MorePhysics.sinking.contains((Boat) event.getVehicle()) && ((on.getTypeId() == 8 || on.getTypeId() == 9) || (under.getTypeId() == 8 || under.getTypeId() == 9)))
				event.getVehicle().setVelocity(new Vector(event.getVehicle().getVelocity().getX(), -.05, event.getVehicle().getVelocity().getZ()));
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