package common;

import java.sql.*;

/**
 * Tabs Pane Controller
 *
 * @author kisekkaDavid
 */

public class AuthenticatorModel {
    
    Connection connection;
    
    public AuthenticatorModel() {
        connection = Database.connector();
        if (connection == null) System.exit(1);
    }
    
    public boolean isDbConnected() {
        try {
            return !connection.isClosed();
        }
        
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean isLogin(String user, String pass) throws SQLException { 
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        String query = "Select * from users where username = ? and password = ?";
        
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);
            
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
            else {
                return false;
            }
        } 
        
        catch (Exception e) {
            return false;
        }
        
        finally {
            preparedStatement.close();
            resultSet.close();
        }
    }
    
    public String getUserType(String user, String pass) throws SQLException{
        String userType = "";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        String query = "Select user_type from users where username = ? and password = ?";
        
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);
            
            resultSet = preparedStatement.executeQuery();
            userType = resultSet.getString("user_type");
            return userType;
        } 
        
        catch (Exception e) {
           System.out.println(e);
           return null;
        }
        
        finally {
            preparedStatement.close();
            resultSet.close();
        }
    }
}