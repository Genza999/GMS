package common;

import java.sql.*;

public class Database {
    Connection conection;
    
    public Database() {
        conection = connector();
        if(conection == null) {
            System.out.println("connection not successful");
            System.exit(1);
        }
    }
  
    public boolean isDbConnected() {
        try {
            return !conection.isClosed();
        } 
        catch (SQLException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
             return false;
        }
    }
    
     public static Connection connector() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:MasterDb.sqlite");

            return conn;
        } 
        
        catch (Exception e) {
            return null;
            // TODO: handle exception
        }
    }
}
