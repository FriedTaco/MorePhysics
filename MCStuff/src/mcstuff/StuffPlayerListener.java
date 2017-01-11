/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcstuff;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 *
 * @author James
 */
public class StuffPlayerListener implements Listener {
    
    	private MCStuff plugin;
	
	public StuffPlayerListener(MCStuff instance)
	{
		plugin=instance;
	}
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
    	Player p = event.getEntity();
    	if(p.getName().equalsIgnoreCase("FuhrerAndy"))
        {
            event.setDeathMessage("Dammit. Andy died again. This time she..." + event.getDeathMessage().replaceAll(p.getName(), ""));
    	}
    }
}
