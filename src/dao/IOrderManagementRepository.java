package dao; 
import entity.Product; 
import entity.User; 
import exception.UserNotFoundException; 
import exception.OrderNotFoundException; 
import java.util.List; 
public interface IOrderManagementRepository { 
void createOrder(User user, List<Product> products) throws UserNotFoundException; 
void cancelOrder(int userId, int orderId) throws UserNotFoundException, OrderNotFoundException; 
void createProduct(User user, Product product) throws UserNotFoundException; 
void createUser(User user); 
List<Product> getAllProducts(); 
List<Product> getOrderByUser(User user); 
}