package de.bewusstlos.api;
import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import de.bewusstlos.api.sql.MySQL;
import de.bewusstlos.api.sql.SQLiteConnection;

public class BewusstloseAPI extends JavaPlugin {
	
	public static BewusstloseAPI instance;
	public MySQL mysql = null;
	public SQLiteConnection sqlite = null;
	private PluginLoader ploader = null;
	private Logger log = this.getLogger();

	public void onEnable() {
		this.saveDefaultConfig();
		instance = this;
		SystemFileManager.setup();
		ploader = this.getPluginLoader();
		startUp();
		
		
	}
	public void onDisable(){
		if(mysql != null) {
			if(mysql.isConnected()) {
				mysql.close();
				log.log(Level.INFO, "close MySQL connection");
			}
		}
		if(sqlite != null) {
			if(sqlite.isConnected()) {
				try {
					sqlite.getConnection().close();
					log.log(Level.INFO, "close SQLite connection");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		log.log(Level.WARNING, "disable BewusstloseAPI...");
	}
	
	
	private void startUp(){
		if(this.getConfig().getBoolean("enable-api") == false) {
			log.log(Level.WARNING, "disable api, if you wish to enable the api, change the value enable-api (false) in the config to true");
			new BukkitRunnable() {
				@Override
				public void run() {
					ploader.disablePlugin(instance);
				}
			}.runTaskLaterAsynchronously(this, 10);
		}else {
			log.log(Level.INFO, "starting BewusstloseAPI...");
			log.log(Level.INFO, "reading configuration file...");
			if(localsql()) {
				sqlite = new SQLiteConnection(new File(this.getDataFolder().getPath(), "local.db"));
				sqlite.connect();
				log.log(Level.INFO, "using sqlite as database!");
				sqlite.update("CREATE TABLE IF NOT EXISTS `user` (id int AUTO_INCREMENT, username text, uuid text, PRIMARY KEY (id));");
			}else  {
				mysql = new MySQL(
						SystemFileManager.getMySQLConfiguration().getString("mysql-host"),
						SystemFileManager.getMySQLConfiguration().getString("mysql-user"),
						SystemFileManager.getMySQLConfiguration().getString("mysql-password"),
						SystemFileManager.getMySQLConfiguration().getString("mysql-database"),
						SystemFileManager.getMySQLConfiguration().getInt("mysql-port"));
				log.log(Level.INFO, "using mysql as database!");
				mysql.update("CREATE TABLE IF NOT EXISTS `user` (id int AUTO_INCREMENT, username text, uuid text, PRIMARY KEY (id));");
			}
			registerEvents();
			registerCommands();
			log.log(Level.FINE, "BewusstloseAPI was enabled successfull");
		}
	}
	private void registerEvents(){
		log.log(Level.INFO, "register events...");
		
		
	}
	private void registerCommands() {
		log.log(Level.INFO, "register commands...");
		
		
		
	}
	private boolean localsql(){
		if(this.getConfig().getString("database").equalsIgnoreCase("local")) {
			return true;
		}
		return false;
	}

}
