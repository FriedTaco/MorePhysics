package mcstuff;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.Skull;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.permissions.*;
import org.bukkit.plugin.Plugin;

public class MCStuff extends JavaPlugin {
		

	    public void onDisable() {
	    }
            
	    @Override
	    public void onEnable() {
		ItemStack wings = new ItemStack(Material.ELYTRA);
                ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.WITHER.ordinal());
                ShapedRecipe wingRecipe = new ShapedRecipe(wings);
                ShapedRecipe skullRecipe = new ShapedRecipe(skull);
                
                skullRecipe.shape("NON","NKN","SSS");
                wingRecipe.shape("OLO","LDL","LDL");
                
                wingRecipe.setIngredient('O', Material.OBSIDIAN);
                wingRecipe.setIngredient('L', Material.LEATHER);
                wingRecipe.setIngredient('D', Material.DIAMOND);
                //skullRecipe.setIngredient('N', Material.AIR);
                skullRecipe.setIngredient('K', Material.SKULL_ITEM);
                skullRecipe.setIngredient('S', Material.SOUL_SAND);
                skullRecipe.setIngredient('O', Material.OBSIDIAN);
                
               this.getServer().addRecipe(wingRecipe);
               this.getServer().addRecipe(skullRecipe);
               
               PluginManager pm = getServer().getPluginManager();
	       pm.registerEvents(new StuffEntityListener(this), this);
               pm.registerEvents(new StuffPlayerListener(this), this);
	    }

		
	}




