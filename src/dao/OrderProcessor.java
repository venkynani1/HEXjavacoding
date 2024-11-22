package dao; 
import dao.IOrderManagementRepository; 
import entity.Product; 
import entity.User; 
import exception.UserNotFoundException; 
import exception.OrderNotFoundException; 
import util.DBUtil; 
import java.sql.*; 
import java.util.ArrayList; 
import java.util.List; 
public class OrderProcessor implements IOrderManagementRepository { 
    @Override 
    public void createOrder(User user, List<Product> products) throws UserNotFoundException { 
        if (user == null) { 
            throw new UserNotFoundException("User not found"); 
        } 
 
        try (Connection connection = DBUtil.getDBConn()) { 
            for (Product product : products) { 
                String query = "INSERT INTO orders (userId, productId, quantity) VALUES (?, ?, ?)"; 
                try (PreparedStatement statement = connection.prepareStatement(query)) { 
                    statement.setInt(1, user.getUserId()); 
                    statement.setInt(2, product.getProductId()); 
                    statement.setInt(3, 1);  
                    statement.executeUpdate(); 
                } 
            } 
            System.out.println("Order created successfully!"); 
        } catch (SQLException e) { 
            e.printStackTrace(); 
        } 
    } 
 
    @Override 
    public void cancelOrder(int userId, int orderId) throws UserNotFoundException, OrderNotFoundException { 
        try (Connection connection = DBUtil.getDBConn()) { 
            String checkUserQuery = "SELECT * FROM users WHERE userId = ?"; 
            try (PreparedStatement userStatement = connection.prepareStatement(checkUserQuery)) { 
                userStatement.setInt(1, userId); 
                ResultSet rs = userStatement.executeQuery(); 
                if (!rs.next()) { 
                    throw new UserNotFoundException("User not found"); 
                } 
            } 
 
            String checkOrderQuery = "SELECT * FROM orders WHERE orderId = ?"; 
            try (PreparedStatement orderStatement = connection.prepareStatement(checkOrderQuery)) { 
                orderStatement.setInt(1, orderId); 
                ResultSet rs = orderStatement.executeQuery(); 
                if (!rs.next()) { 
                    throw new OrderNotFoundException("Order not found"); 
                } 
            } 
 
            String cancelOrderQuery = "DELETE FROM orders WHERE orderId = ?"; 
            try (PreparedStatement cancelStatement = connection.prepareStatement(cancelOrderQuery)) { 
                cancelStatement.setInt(1, orderId); 
                cancelStatement.executeUpdate(); 
            } 
 
            System.out.println("Order canceled successfully!"); 
        } catch (SQLException e) { 
            e.printStackTrace(); 
        } 
    } 
 
    @Override 
    public void createProduct(User user, Product product) throws UserNotFoundException { 
        if (user == null || !user.getRole().equals("Admin")) { 
            throw new UserNotFoundException("Admin user is required to add products"); 
        } 
 
        try (Connection connection = DBUtil.getDBConn()) { 
            String query = "INSERT INTO products (productId, productName, description, price, quantityInStock,type) VALUES (?, ?, ?, ?, ?, ?)"; 
            try (PreparedStatement statement = connection.prepareStatement(query)) { 
                statement.setInt(1, product.getProductId()); 
                statement.setString(2, product.getProductName()); 
                statement.setString(3, product.getDescription()); 
                statement.setDouble(4, product.getPrice()); 
                statement.setInt(5, product.getQuantityInStock()); 
                statement.setString(6, product.getType()); 
                statement.executeUpdate(); 
            } 
            System.out.println("Product created successfully!"); 
        } catch (SQLException e) { 
            e.printStackTrace(); 
        } 
    } 
 
    @Override 
    public void createUser(User user) { 
        try (Connection connection = DBUtil.getDBConn()) { 
            String query = "INSERT INTO users (userId, username, password, role) VALUES (?, ?, ?, ?)"; 
            try (PreparedStatement statement = connection.prepareStatement(query)) { 
                statement.setInt(1, user.getUserId()); 
                statement.setString(2, user.getUsername()); 
                statement.setString(3, user.getPassword()); 
                statement.setString(4, user.getRole()); 
                statement.executeUpdate(); 
            } 
            System.out.println("User created successfully!"); 
        } catch (SQLException e) { 
            e.printStackTrace(); 
        } 
    } 
 
    @Override 
    public List<Product> getAllProducts() { 
        List<Product> products = new ArrayList<>(); 
        try (Connection connection = DBUtil.getDBConn()) { 
            String query = "SELECT * FROM products"; 
            try (PreparedStatement statement = connection.prepareStatement(query)) { 
                ResultSet rs = statement.executeQuery(); 
                while (rs.next()) { 
                    Product product = new Product( 
                            rs.getInt("productId"), 
                            rs.getString("productName"), 
                            rs.getString("description"), 
                            rs.getDouble("price"), 
                            rs.getInt("quantityInStock"), 
                            rs.getString("type") 
                    ); 
                    products.add(product); 
                } 
            } 
        } catch (SQLException e) { 
            e.printStackTrace(); 
        } 
        return products; 
    } 
 
    @Override 
    public List<Product> getOrderByUser(User user) { 
        List<Product> products = new ArrayList<>(); 
        try (Connection connection = DBUtil.getDBConn()) { 
            String query =
            		"SELECT p.productId, p.productName, p.description, p.price, p.quantityInStock, p.type FROM orders o JOIN products p ON o.productId = p.productId WHERE o.userId = ?"; 
            try (PreparedStatement statement = connection.prepareStatement(query)) { 
                statement.setInt(1, user.getUserId()); 
                ResultSet rs = statement.executeQuery(); 
                while (rs.next()) {
                    Product product = new Product( 
                            rs.getInt("productId"), 
                            rs.getString("productName"), 
                            rs.getString("description"), 
                            rs.getDouble("price"), 
                            rs.getInt("quantityInStock"), 
                            rs.getString("type") 
                    ); 
                    products.add(product); 
                } 
            } 
        } catch (SQLException e) { 
            e.printStackTrace(); 
        } 
        return products; 
    } 
} 