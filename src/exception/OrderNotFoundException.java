package exception;

public class OrderNotFoundException extends Exception {
    public OrderNotFoundException(String message) {
        super("Order not found: " + message);  // Provide a meaningful message
    }
}
