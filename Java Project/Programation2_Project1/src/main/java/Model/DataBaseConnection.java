package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class DataBaseConnection {

    public static String URL = "jdbc:mysql://localhost:3306/project1?autoReconnect=true&useSSL=false";
    public static String user = "root";
    public static String password = "1234";

    public Connection connection_;
    PreparedStatement ps;
    ResultSet rs;
    
    public DataBaseConnection(){
    
        Connection _connection = getConnection();
    }
    
    public Connection getConnection(){
    
        connection_ = null;
        
        try{
        
            connection_ = DriverManager.getConnection(URL, user, password);
            System.out.println("Successful Connection");
            
        }catch(Exception ex){
        
            System.err.println("Error " + ex);
        }
        
        return connection_;
    }
}
