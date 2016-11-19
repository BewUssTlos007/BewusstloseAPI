package de.bewusstlos.api.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {

	private String host, user, password, database;
	private int port;
	public Connection con;
	
	public MySQL(String host, String user, String password, String database){
		this.host = host;
		this.user = user;
		this.password = password;
		this.database = database;
		this.port = 3306;
	}
	public MySQL(String host, String user, String password, String database, int port){
		this.host = host;
		this.user = user;
		this.password = password;
		this.database = database;
		this.port = port;
	}
	
	public void connect(){
		try{
			con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", user, password);
			System.out.println("MySQL -> connection to mysql database: ✔");
		}catch(SQLException e){
			System.err.println("MySQL -> connection to mysql database: ✘");
			e.printStackTrace();
		}
	}
	public void close(){
		if(isConnected()) {
			try {
				con.close();
				System.out.println("MySQL -> close mysql connection... DONE");
			} catch (SQLException e) {
				System.err.println("MySQL -> can´t close mysql connection. no connection available!");
				e.printStackTrace();
			}
		}
	}
	public void update(String qry){
		if(isConnected()) {
			try {
				Statement st = con.createStatement();
				System.out.println("MySQL -> executing update...");
				st.executeUpdate(qry);
				st.close();
				System.out.println("MySQL -> update successfull executed");
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println("MySQL -> can´t execute update. no mysql connection!");
			}
			
		}
	}
	public ResultSet query(String qry){
		ResultSet rs = null;
		try {
			Statement st = con.createStatement();
			rs = st.executeQuery(qry);
			System.out.println("MySQL -> sucessfull got resultset");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("MySQL -> can´t get resultset. no mysql connection!");
		}
		return rs;
	}
	public boolean isConnected(){
		return con != null;
	}
	
}
