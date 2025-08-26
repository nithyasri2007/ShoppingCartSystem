import java.util.*;

class Product {
    private int id;
    private String name;
    private double price;
    private int stock;

    public Product(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return id + " | " + name + " | $" + price + " | Stock: " + stock;
    }
}

class Order {
    private static int counter = 1;
    private int orderId;
    private List<Product> items;
    private double total;

    public Order(List<Product> items) {
        this.orderId = counter++;
        this.items = new ArrayList<>(items);
        this.total = items.stream().mapToDouble(Product::getPrice).sum();
    }

    @Override
    public String toString() {
        return "Order ID: " + orderId + " | Items: " + items.size() + " | Total: $" + total;
    }
}

public class ShoppingCartSystem {
    static Scanner sc = new Scanner(System.in);
    static List<Product> products = new ArrayList<>();
    static List<Product> cart = new ArrayList<>();
    static List<Order> orderHistory = new ArrayList<>();

    public static void main(String[] args) {
        // Title Banner
        System.out.println("====================================");
        System.out.println("         Shopping Cart System       ");
        System.out.println("====================================");

        // Sample products
        products.add(new Product(1, "Laptop", 55000, 5));
        products.add(new Product(2, "Headphones", 2000, 10));
        products.add(new Product(3, "Keyboard", 1500, 8));

        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. View Products");
            System.out.println("2. Add to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Remove from Cart");
            System.out.println("5. Place Order");
            System.out.println("6. View Order History");
            System.out.println("7. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> viewProducts();
                case 2 -> addToCart();
                case 3 -> viewCart();
                case 4 -> removeFromCart();
                case 5 -> placeOrder();
                case 6 -> viewOrderHistory();
                case 7 -> {
                    exit = true;
                    System.out.println("Thank you for using Shopping Cart System!");
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void viewProducts() {
        System.out.println("\n--- Product List ---");
        for (Product p : products) {
            System.out.println(p);
        }
    }

    static void addToCart() {
        System.out.print("Enter Product ID to add: ");
        int id = sc.nextInt();
        for (Product p : products) {
            if (p.getId() == id && p.getStock() > 0) {
                cart.add(p);
                p.setStock(p.getStock() - 1);
                System.out.println(p.getName() + " added to cart!");
                return;
            }
        }
        System.out.println("Product not available!");
    }

    static void viewCart() {
        System.out.println("\n--- Your Cart ---");
        if (cart.isEmpty()) {
            System.out.println("Cart is empty!");
            return;
        }
        for (int i = 0; i < cart.size(); i++) {
            Product p = cart.get(i);
            System.out.println((i + 1) + ". " + p.getName() + " | $" + p.getPrice());
        }
    }

    static void removeFromCart() {
        viewCart();
        if (cart.isEmpty()) return;

        System.out.print("Enter item number to remove: ");
        int index = sc.nextInt();
        if (index > 0 && index <= cart.size()) {
            Product removed = cart.remove(index - 1);
            // restore stock
            for (Product p : products) {
                if (p.getId() == removed.getId()) {
                    p.setStock(p.getStock() + 1);
                    break;
                }
            }
            System.out.println(removed.getName() + " removed from cart.");
        } else {
            System.out.println("Invalid selection!");
        }
    }

    static void placeOrder() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty!");
            return;
        }
        Order order = new Order(cart);
        orderHistory.add(order);
        cart.clear();
        System.out.println("Order placed successfully! " + order);
    }

    static void viewOrderHistory() {
        System.out.println("\n--- Order History ---");
        if (orderHistory.isEmpty()) {
            System.out.println("No orders yet!");
            return;
        }
        for (Order o : orderHistory) {
            System.out.println(o);
        }
    }
}

