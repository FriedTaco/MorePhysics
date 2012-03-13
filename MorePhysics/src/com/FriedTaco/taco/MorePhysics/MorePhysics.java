package com.FriedTaco.taco.MorePhysics;


import java.io.File;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.plugin.Plugin;




	public class MorePhysics extends JavaPlugin {
		public static final Logger log = Logger.getLogger("Minecraft");
		private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();  
		public static ArrayList<Boat> sinking = new ArrayList<Boat>();
		public List<String> bouncyBlocks = new ArrayList<String>();
		public static PermissionHandler Permissions;
		public boolean movement=true,swimming=true,boats=true,pistons=true,exemptions=true,pistonsB=true,arrows=true;		
		public double lhat,lshirt,lpants,lboots,ihat,ishirt,ipants,iboots,ghat,gshirt,gpants,gboots,dhat,dshirt,dpants,dboots,chat,cshirt,cpants,cboots,arhead,artorso,arlegs,arfeet;
		static String mainDirectory = "plugins/MorePhysics";
		static Properties properties = new Properties(); 
		protected static FileConfiguration Config;


		   
		 private void setupPermissions() {
		      Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");
		      if (MorePhysics.Permissions == null) 
		      {
		          if (test != null) {
		              MorePhysics.Permissions = ((Permissions)test).getHandler();
		              System.out.println("[MorePhysics] Permissions detected. Now using permissions.");
		          } else {
		             System.out.println("[MorePhysics] Permissions NOT detected. Giving permission to ops.");
		          }
		      }
		  }
		 public void loadConfig(){
			 try{
				File MorePhysics = new File("plugins" + File.separator + "MorePhysics" + File.separator + "config.yml");
				MorePhysics.mkdir();
	            Config = getConfig();
	            if(!Config.contains("general.Boats_Sink"))
	            	Config.set("general.Boats_Sink", true);
	            if(!Config.contains("general.Movement_Affected"))
	            	Config.set("general.Movement_Affected", true);
	            if(!Config.contains("general.Swimming_Affected"))
	            	Config.set("general.Swimming_Affected", true);
	            if(!Config.contains("general.Pistons_Launch_Entities"))
	            	Config.set("general.Pistons_Launch_Entities", true);
	            if(!Config.contains("general.Pistons_Launch_Blocks"))
	            	Config.set("general.Pistons_Launch_Blocks", true);
	            if(!Config.contains("general.Allow_Physics_Exemptions"))
	            	Config.set("general.Allow_Physics_Exemptions", true);
	            if(!Config.contains("general.Bounce_Causing_Blocks"))
	            	Config.set("general.Bounce_Causing_Blocks", "1 2 12 35");
	            if(!Config.contains("armour.Leather_Helm"))
	        	    Config.set("armour.Leather_Helm",2);
	            if(!Config.contains("armour.Leather_Chest"))
	                Config.set("armour.Leather_Chest",10);
	            if(!Config.contains("armour.Leather_Pants"))
	                Config.set("armour.Leather_Pants",8);
	            if(!Config.contains("armour.Leather_Boots"))
	                Config.set("armour.Leather_Boots",2);
	            if(!Config.contains("armour.Iron_Helm"))
	                Config.set("armour.Iron_Helm",20);
	            if(!Config.contains("armour.Iron_Chest"))
	                Config.set("armour.Iron_Chest",60);
	            if(!Config.contains("armour.Iron_Pants"))
	                Config.set("armour.Iron_Pants",40);
	            if(!Config.contains("armour.Iron_Boots"))
	                Config.set("armour.Iron_Boots",20);
	            if(!Config.contains("armour.Gold_Helm"))
	                Config.set("armour.Gold_Helm",40);
	            if(!Config.contains("armour.Gold_Chest"))
	                Config.set("armour.Gold_Chest",80);
	            if(!Config.contains("armour.Gold_Pants"))
	                Config.set("armour.Gold_Pants",70);
	            if(!Config.contains("armour.Gold_Boots"))
	                Config.set("armour.Gold_Boots",40);
	            if(!Config.contains("armour.Diamond_Helm"))
	                Config.set("armour.Diamond_Helm",5);
	            if(!Config.contains("armour.Diamond_Chest"))
	                Config.set("armour.Diamond_Chest",30);
	            if(!Config.contains("armour.Diamond_Pants"))
	                Config.set("armour.Diamond_Pants",20);
	            if(!Config.contains("armour.Diamond_Boots"))
	                Config.set("armour.Diamond_Boots",5);
	            if(!Config.contains("armour.Chain_Helm"))
	                Config.set("armour.Chain_Helm",10);
	            if(!Config.contains("armour.Chain_Chest"))
	                Config.set("armour.Chain_Chest",50);
	            if(!Config.contains("armour.Chain_Pants"))
	                Config.set("armour.Chain_Pants",30);
	            if(!Config.contains("armour.Chain_Boots"))
	                Config.set("armour.Chain_Boots",10);
	            if(!Config.contains("arrows.enabled"))
	                Config.set("arrows.enabled",true);
	            if(!Config.contains("arrows.head_modifier"))
	                Config.set("arrows.head_modifier",3);
	            if(!Config.contains("arrows.torso_modifier"))
	                Config.set("arrows.torso_modifier",2);
	            if(!Config.contains("arrows.legs_modifier"))
	                Config.set("arrows.legs_modifier",.8);
	            if(!Config.contains("arrows.feet_modifier"))
	                Config.set("arrows.feet_modifier",.2);
	            boats = Config.getBoolean("general.Boats_Sink", true);
	            swimming = Config.getBoolean("general.Movement_Affected", true);
	            movement = Config.getBoolean("general.Swimming_Affected", true);
	            lhat = Config.getDouble("armour.Leather_Helm",2)/1000;
	            lshirt = Config.getDouble("armour.Leather_Chest",10)/1000;
	            lpants = Config.getDouble("armour.Leather_Pants",8)/1000;
	            lboots = Config.getDouble("armour.Leather_Boots",2)/1000;
	            ihat = Config.getDouble("armour.Iron_Helm",20)/1000;
	            ishirt = Config.getDouble("armour.Iron_Chest",60)/1000;
	            ipants = Config.getDouble("armour.Iron_Pants",40)/1000;
	            iboots = Config.getDouble("armour.Iron_Boots",20)/1000;
	          	ghat = Config.getDouble("armour.Gold_Helm",40)/1000;
	          	gshirt = Config.getDouble("armour.Gold_Chest",80)/1000;
	          	gpants = Config.getDouble("armour.Gold_Pants",70)/1000;
	          	gboots = Config.getDouble("armour.Gold_Boots",40)/1000;
	          	dhat = Config.getDouble("armour.Diamond_Helm",5)/1000;
	          	dshirt = Config.getDouble("armour.Diamond_Chest",30)/1000;
	          	dpants = Config.getDouble("armour.Diamond_Pants",20)/1000;
	          	dboots = Config.getDouble("armour.Diamond_Boots",5)/1000;
	          	chat = Config.getDouble("armour.Chain_Helm",10)/1000;
	          	cshirt = Config.getDouble("armour.Chain_Chest",50)/1000;
	          	cpants = Config.getDouble("armour.Chain_Pants",30)/1000;
	          	cboots = Config.getDouble("armour.Chain_Boots",10)/1000;
	          	arhead = Config.getDouble("arrows.head_modifier",3);
	          	artorso = Config.getDouble("arrows.torso_modifier",2);
	          	arlegs = Config.getDouble("arrows.legs_modifier",.8);
	          	arfeet = Config.getDouble("arrows.feet_modifier",.2);
	          	pistons = Config.getBoolean("general.Pistons_Launch_Entities", true);
	          	pistonsB = Config.getBoolean("general.Pistons_Launch_Blocks", true);
	          	exemptions = Config.getBoolean("general.Allow_Physics_Exemptions", true);
	          	arrows = Config.getBoolean("arrows.enabled", true);
	          	bouncyBlocks = Arrays.asList(Config.getString("general.Bounce_Causing_Blocks", "").split(" "));
	            saveConfig();
	            System.out.println(swimming+" "+movement);
			 } catch(Exception e){
				 
			 }
	        }

	    public void onDisable() {
	    }
	    @Override
	    public void onEnable() {
			loadConfig();
	        PluginManager pm = getServer().getPluginManager();
	        pm.registerEvents(new MorePhysicsBlockListener(this), this);
	        pm.registerEvents(new MorePhysicsEntityListener(this), this);
	        pm.registerEvents(new MorePhysicsPlayerListener(this), this);
	        pm.registerEvents(new MorePhysicsVehicleListener(this), this);
	        PluginDescriptionFile pdfFile = this.getDescription();
	        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
	        setupPermissions();
	    }

	    public boolean isDebugging(final Player player) {
	        if (debugees.containsKey(player)) {
	            return debugees.get(player);
	        } else {
	            return false;
	        }
	    }

	    public void setDebugging(final Player player, final boolean value) {
	        debugees.put(player, value);
	    }

		public void recordEvent(PlayerLoginEvent event) {
			// TODO Auto-generated method stub
			
		}
		double weight(int id)
	    {
	    	switch(id)
	    	{
	    		case 298:
	    			return this.lhat;	
	    		case 299:
	    			return this.lshirt;	
	    		case 300:
	    			return this.lpants;	
	    		case 301:
	    			return this.lboots;	
	    		case 302:
	    			return this.chat;
	    		case 303:
	    			return this.cshirt;
	    		case 304:
	    			return this.cpants;
	    		case 305:
	    			return this.cboots;
	    		case 306:
	    			return this.ihat;
	    		case 307:
	    			return this.ishirt;
	    		case 308:
	    			return this.ipants;
	    		case 309:
	    			return this.iboots;
	    		case 310:
	    			return this.dhat;
	    		case 311:
	    			return this.dshirt;
	    		case 312:
	    			return this.dpants;
	    		case 313:
	    			return this.dboots;
	    		case 314:
	    			return this.ghat;
	    		case 315:
	    			return this.gshirt;
	    		case 316:
	    			return this.gpants;
	    		case 317:
	    			return this.gboots;
	    		default:
	    			return 0;
	    	}
	    }
		
		double getTotalWeight(Player player)
		{
			double modifier = 0;
			for(ItemStack i : player.getInventory().getArmorContents())
			{
				if(i != null)
					modifier += weight(i.getTypeId());
			}
			return modifier;
		}
	}




