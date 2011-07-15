package com.FriedTaco.taco.MorePhysics;


	import java.io.File;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.util.logging.Level;
	import java.util.logging.Logger;
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.Properties;
	import org.bukkit.entity.Boat;
	import org.bukkit.entity.Player;
	import org.bukkit.event.Event.Priority;
	import org.bukkit.event.Event;
	import org.bukkit.event.player.PlayerLoginEvent;
	import org.bukkit.plugin.PluginDescriptionFile;
	import org.bukkit.plugin.java.JavaPlugin;
	import org.bukkit.plugin.PluginManager;
	import com.nijiko.permissions.PermissionHandler;
	import com.nijikokun.bukkit.Permissions.Permissions;
	import org.bukkit.plugin.Plugin;
	import org.yaml.snakeyaml.Yaml;
	import org.yaml.snakeyaml.constructor.SafeConstructor;




	public class MorePhysics extends JavaPlugin {
		public static final Logger log = Logger.getLogger("Minecraft");
		private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();  
	    private final MorePhysicsPlayerListener playerListener  = new MorePhysicsPlayerListener(this);
	    private final MorePhysicsVehicleListener VehicleListener = new MorePhysicsVehicleListener(this);
		public static ArrayList<Boat> sinking = new ArrayList<Boat>();
		@SuppressWarnings("unused")
		private static Yaml yaml = new Yaml(new SafeConstructor());
		public static PermissionHandler Permissions;
		public boolean movement,swimming,boats;		
		public double lhat,lshirt,lpants,lboots,ihat,ishirt,ipants,iboots,ghat,gshirt,gpants,gboots,dhat,dshirt,dpants,dboots,chat,cshirt,cpants,cboots;
		static String mainDirectory = "plugins/MorePhysics";
		static File config = new File(mainDirectory + File.separator + "config.dat");
		static Properties properties = new Properties(); 

	   
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
		 
		 public void loadSettings() throws Exception
		 {
			 if (!this.getDataFolder().exists())
			 {
				 	this.getDataFolder().mkdirs();
			 }
			 final String dir = "plugins/MorePhysics";
		        if (!new File(dir + File.separator + "MorePhysics.properties").exists()) {
		            FileWriter writer = null;
		            try {
		                writer = new FileWriter(dir + File.separator + "MorePhysics.properties");
		                writer.write("MorePhysics v 1.2 configuration\r\n\n");
		                writer.write("#Allow boats to sink.\r\n");
		                writer.write("BoatsSink=true \r\n\n");
		                writer.write("#Allow armour to affect movement on land.\r\n");
		                writer.write("MovementAffected=true\r\n");
		                writer.write("#Allow armour to affect movement in water.\r\n");
		                writer.write("SwimmingAffected=true\r\n\n");
		                writer.write("#The following are the weights of armour.\r\n");
		                writer.write("#These are values out of 100 and are predefined by default.\r\n");
		                writer.write("#Tampering with these values may result in players becoming conscious of their weight.\r\n");
		                writer.write("#THESE VALUES ARE CURRENTLY NOT READ! This will come in the next update!\r\n");
		                writer.write("Leather_Helm=2\r\n");
		                writer.write("Leather_Chest=10\r\n");
		                writer.write("Leather_Pants=8\r\n");
		                writer.write("Leather_Boots=2\r\n");
		                writer.write("Iron_Helm=20\r\n");
		                writer.write("Iron_Chest=60\r\n");
		                writer.write("Iron_Pants=40\r\n");
		                writer.write("Iron_Boots=20\r\n");
		                writer.write("Gold_Helm=40\r\n");
		                writer.write("Gold_Chest=80\r\n");
		                writer.write("Gold_Pants=70\r\n");
		                writer.write("Gold_Boots=40\r\n");
		                writer.write("Diamond_Helm=5\r\n");
		                writer.write("Diamond_Chest=30\r\n");
		                writer.write("Diamond_Pants=20\r\n");
		                writer.write("Diamond_Boots=5\r\n");
		                writer.write("Chain_Helm=10\r\n");
		                writer.write("Chain_Chest=50\r\n");
		                writer.write("Chain_Pants=30\r\n");
		                writer.write("Chain_Boots=10\r\n");
		                
		                } catch (Exception e) {
		                log.log(Level.SEVERE,
		                        "Exception while creating MorePhysics.properties", e);
		                try {
		                    if (writer != null)
		                        writer.close();
		                } catch (IOException ex) {
		                    log
		                            .log(
		                                    Level.SEVERE,
		                                    "Exception while closing writer for MorePhysics.properties",
		                                    ex);
		                }
		            } finally {
		                try {
		                    if (writer != null)
		                        writer.close();
		                } catch (IOException e) {
		                    log
		                            .log(
		                                    Level.SEVERE,
		                                    "Exception while closing writer for MorePhysics.properties",
		                                    e);
		                }
		            }
		        }
		        PropertiesFile properties = new PropertiesFile(dir + File.separator + "MorePhysics.properties");
		        try {
		          boats = properties.getBoolean("BoatsSink", true);
		          swimming = properties.getBoolean("MovementAffected", true);
		          movement = properties.getBoolean("SwimmingAffected", true);
		          lhat = properties.getDouble("Leather_Helm",2)/100;
		          lshirt = properties.getDouble("Leather_Chest",10)/100;
		          lpants = properties.getDouble("Leather_Pants",8)/100;
		          lboots = properties.getDouble("Leather_Boots",2)/100;
		          ihat = properties.getDouble("Iron_Helm",20)/100;
		          ishirt = properties.getDouble("Iron_Chest",60)/100;
		          ipants = properties.getDouble("Iron_Pants",40)/100;
		          iboots = properties.getDouble("Iron_Boots",20)/100;
		          ghat = properties.getDouble("Gold_Helm",40)/100;
		          gshirt = properties.getDouble("Gold_Chest",80)/100;
		          gpants = properties.getDouble("Gold_Pants",70)/100;
		          gboots = properties.getDouble("Gold_Boots",40)/100;
		          dhat = properties.getDouble("Diamond_Helm",5)/100;
		          dshirt = properties.getDouble("Diamond_Chest",30)/100;
		          dpants = properties.getDouble("Diamond_Pants",20)/100;
		          dboots = properties.getDouble("Diamond_Boots",5)/100;
		          chat = properties.getDouble("Chain_Helm",10)/100;
		          cshirt = properties.getDouble("Chain_Chest",50)/100;
		          cpants = properties.getDouble("Chain_Pants",30)/100;
		          cboots = properties.getDouble("Chain_Boots",10)/100;
		        } catch (Exception e) {
		            log.log(Level.SEVERE,
		                    "Exception while reading from MorePhysics.properties", e);
		        }

		 }
	    public void onDisable() {
	    }
	    @Override
	    public void onEnable() {
	    	try {
				loadSettings();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        PluginManager pm = getServer().getPluginManager();
	        pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Priority.Normal, this);
	        pm.registerEvent(Event.Type.VEHICLE_DAMAGE, VehicleListener, Priority.Normal, this);
	        pm.registerEvent(Event.Type.VEHICLE_DESTROY, VehicleListener, Priority.Normal, this);
	        pm.registerEvent(Event.Type.VEHICLE_MOVE, VehicleListener, Priority.Normal, this);
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
	}




