package de.bewusstlos.api;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class SystemFileManager {
	
	private static File mkdir = BewusstloseAPI.instance.getDataFolder();
	private static File mysql = new File(BewusstloseAPI.instance.getDataFolder().getPath(), "mysql.yml");
	
	public static void setup(){
		if(!(mkdir.exists())) {
			mkdir.mkdir();
			try {
				mysql.createNewFile();
				getMySQLConfiguration().addDefault("host", "localhost");
				getMySQLConfiguration().addDefault("user", "user");
				getMySQLConfiguration().addDefault("password", "password");
				getMySQLConfiguration().addDefault("database", "database");
				getMySQLConfiguration().addDefault("port", 3306);
				getMySQLConfiguration().save(mysql);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static File getMySQLfile(){
		return mysql;
	}
	public static FileConfiguration getMySQLConfiguration(){
		return YamlConfiguration.loadConfiguration(mysql);
	}
	
}
