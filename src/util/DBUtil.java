package util; 
 
import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException; 
 
public class DBUtil { 
    public static Connection getDBConn() throws SQLException { 
        String url = "jdbc:mysql://localhost:3306/ordermanagement";  
        String username = "root";  
        String password = "welcome123";  
 
        return DriverManager.getConnection(url, username, password); 
    }
}