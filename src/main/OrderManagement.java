package main; 
 
import dao.OrderProcessor; 

import entity.Product; 
import entity.User; 
import exception.UserNotFoundException; 
import exception.OrderNotFoundException; 
 
import java.util.List; 
import java.util.Scanner; 
 
public class OrderManagement { 
    static OrderProcessor orderProcessor = new OrderProcessor(); 
 
    public static void main(String[] args) { 
        Scanner scanner = new Scanner(System.in); 
        boolean exit = false; 
 
        while (!exit) { 
            System.out.println("Order Management System"); 
            System.out.println("1. Create User"); 
            System.out.println("2. Create Product"); 
            System.out.println("3. Place Order"); 
            System.out.println("4. Cancel Order"); 
            System.out.println("5. Get All Products"); 
            System.out.println("6. Get Order By User"); 
            System.out.println("7. Exit"); 
            System.out.print("Enter your choice: "); 
 
            int choice = scanner.nextInt(); 
            scanner.nextLine(); // Consume newline 
 
            try { 
                switch (choice) { 
                    case 1: 
                        createUser(scanner); 
                        break; 
                    case 2: 
                        createProduct(scanner); 
                        break; 
                    case 3: 
                        placeOrder(scanner); 
                        break; 
                    case 4: 
                        cancelOrder(scanner); 
                        break; 
                    case 5: 
                        getAllProducts(); 
                        break; 
                    case 6: 
                        getOrderbyUser(scanner); 
 
                        break; 
                    case 7: 
                        System.out.println("Exiting the system..."); 
                        exit = true; 
                        break; 
                    default: 
                        System.out.println("Invalid choice, please try again."); 
                } 
            } catch (UserNotFoundException | OrderNotFoundException e) { 
                System.out.println("Error: " + e.getMessage()); 
            } 
        } 
        scanner.close(); 
    } 
 
    private static void createUser(Scanner scanner) throws UserNotFoundException { 
        System.out.print("Enter user ID: "); 
        int userId = scanner.nextInt(); 
        scanner.nextLine(); 
        System.out.print("Enter username: "); 
        String username = scanner.nextLine(); 
        System.out.print("Enter password: "); 
        String password = scanner.nextLine(); 
        System.out.print("Enter role (User/Admin): "); 
        String role = scanner.nextLine(); 
 
        User user = new User(userId, username, password, role); 
        orderProcessor.createUser(user); 
        System.out.println("User created successfully!"); 
    } 
 
    private static void createProduct(Scanner scanner) throws UserNotFoundException { 
        System.out.print("Enter admin ID: "); 
        int adminId = scanner.nextInt(); 
        scanner.nextLine(); 
        System.out.print("Enter product name: "); 
        String productName = scanner.nextLine(); 
        System.out.print("Enter product description: "); 
        String productDescription = scanner.nextLine(); 
        System.out.print("Enter product price: "); 
        double price = scanner.nextDouble(); 
        System.out.print("Enter product stock quantity: "); 
        int stockQuantity = scanner.nextInt(); 
        scanner.nextLine();  
        System.out.print("Enter product category: "); 
        String category = scanner.nextLine(); 
 
        User admin = new User(adminId, "Admin", "admin123", "Admin");  
        Product product = new Product(0, productName, productDescription, price, stockQuantity, category);  
        orderProcessor.createProduct(admin, product); 
        System.out.println("Product created successfully!"); 
    } 
 
    private static void cancelOrder(Scanner scanner) throws OrderNotFoundException, UserNotFoundException { 
        System.out.print("Enter user ID: "); 
        int userId = scanner.nextInt(); 
        System.out.print("Enter order ID: "); 
        int orderId = scanner.nextInt(); 
 
        orderProcessor.cancelOrder(userId, orderId); 
        System.out.println("Order cancelled successfully!"); 
    } 
 
    private static void getAllProducts() { 
        List<Product> products = orderProcessor.getAllProducts(); 
        System.out.println("All Products:"); 
        for (Product product : products) { 
            System.out.println(product.getProductName()); 
        } 
    } 
 
    private static void getOrderbyUser(Scanner scanner) { 
        System.out.print("Enter user ID: "); 
        int userId = scanner.nextInt(); 
        User user = new User(userId, "User", "password", "User"); 
        List<Product> products = orderProcessor.getOrderByUser(user); 
        System.out.println("Orders by " + user.getUsername() + ":"); 
        for (Product product : products) { 
            System.out.println(product.getProductName()); 
        } 
    } 
 
    private static void placeOrder(Scanner scanner) throws UserNotFoundException { 
        System.out.print("Enter your user ID: "); 
        int userId = scanner.nextInt(); 
        scanner.nextLine(); 
        User user = new User(userId, "User", "password", "User");  
        List<Product> products = orderProcessor.getAllProducts(); 
        System.out.println("Available Products:"); 
        for (int i = 0; i < products.size(); i++) { 
            System.out.println((i + 1) + ". " + products.get(i).getProductName()); 
        } 
 
        System.out.print("Enter product number to add to order: "); 
        int productChoice = scanner.nextInt(); 
        Product selectedProduct = products.get(productChoice - 1); 
        List<Product> orderProducts = List.of(selectedProduct); 
        orderProcessor.createOrder(user, orderProducts); 
 
        System.out.println("Order placed successfully!"); 
    } 
}