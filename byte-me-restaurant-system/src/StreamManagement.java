import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;

public class StreamManagement {

    // save user details on registraion
    public static void saveCustomerDetails(Customer customer) throws IOException {
        String fileName = "src/users.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(customer.getName() + "," +
                    customer.getMobile_no() + "," +
                    customer.getUsername() + "," +
                    customer.getPassword() + "," +
                    customer.getVIP());
            writer.newLine();
            writer.close();

            // Create Cart file
            String cartFileName = "src/Cart/" + customer.getName() + "_" + customer.getMobile_no() + ".txt";
            new File(cartFileName).createNewFile();

            // Create OrderHistory file
            String orderHistoryFileName = "src/OrderHistory/" + customer.getName() + "_" + customer.getMobile_no() + ".txt";
            new File(orderHistoryFileName).createNewFile();

        }
    }

    // load customer details on login
    public static Customer loadCustomerDetails(String username, String password) throws IOException {
        String fileName = "src/users.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            Customer customer = null;
            while ((line = reader.readLine()) != null) {
                // Split the line by commas to extract individual fields
                String[] details = line.split(",");

                if (details.length == 5) { // Ensure the line has all required details
                    String name = details[0];
                    String mobileNo = details[1];
                    String fileUsername = details[2];
                    String filePassword = details[3];
                    boolean isVIP = Boolean.parseBoolean(details[4]);

                    // Check if username and password match
                    if (fileUsername.equals(username) && filePassword.equals(password)) {
                        // Create and populate the customer object
                        customer = new Customer();
                        customer.setName(name);
                        customer.setMobile_no(mobileNo);
                        customer.setUsername(fileUsername);
                        customer.setPassword(filePassword);
                        customer.setVIP(isVIP);

                        StreamManagement.loadCart(customer); // load current stored cart for this customer
                        StreamManagement.loadOrderHistory(customer); // load order history for this customer
                    }
                }
            }

            return customer; // may be null if no match found;
        }
    }

    // save order history for each user
    public static void storeOrderHistory(Customer customer) throws IOException {
        String fileName = "src/OrderHistory/" + customer.getName() + "_" + customer.getMobile_no() + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
            for (FoodOrder order : customer.getOrderHistory()) {
                writer.write("Order ID: " + order.getOrder_id());
                writer.newLine();
                writer.write("Customer Name: " + order.getC_name());
                writer.newLine();
                writer.write("Customer Mobile: " + order.getC_Mno());
                writer.newLine();
                writer.write("Order Total: " + order.getTotal());
                writer.newLine();
                writer.write("Order Status: " + order.getStatus());
                writer.newLine();
                writer.write("Special Request: " + order.getSpecial_request());
                writer.newLine();
                writer.write("Request Status: " + order.getS_request());
                writer.newLine();
                writer.write("Refund Initiated: " + order.isRefundInitiated());
                writer.newLine();
                writer.write("Delivery Address: " + order.getDeliveryAddress());
                writer.newLine();
                writer.write("Payment Method: " + order.getPaymentMethod());
                writer.newLine();
                writer.write("Order Date: " + order.getOrderDate());
                writer.newLine();
                writer.write("Order Cart:");
                writer.newLine();

                for (Map.Entry<Food_Item, Integer> entry : order.getOrder_Cart().entrySet()) {

                    writer.write("  - " + entry.getKey().getName() + " x " + entry.getValue());
                    writer.newLine();
                }

                writer.write("---");
                writer.newLine();
            }
        }
    }

    // laod order history for each user on login
    public static void loadOrderHistory(Customer customer) throws IOException {
        String fileName = "src/OrderHistory/" + customer.getName() + "_" + customer.getMobile_no() + ".txt";
        File file = new File(fileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            FoodOrder order = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Order ID: ")) {
                    if (order != null) {
                        customer.getOrderHistory().add(order);
                    }
                    order = new FoodOrder();
                    order.putOrderid(Integer.parseInt(line.substring(10)));
                } else if (line.startsWith("Customer Name: ")) {

                    order.setC_name(line.substring(15));
                } else if (line.startsWith("Customer Mobile: ")) {

                    order.setC_Mno(line.substring(17));
                } else if (line.startsWith("Order Total: ")) {

                    order.put_total(Integer.parseInt(line.substring(13)));
                } else if (line.startsWith("Order Status: ")) {

                    order.setStatus(OrderStatus.valueOf(line.substring(14)));
                } else if (line.startsWith("Special Request: ")) {

                    order.setSpecial_request(line.substring(17));
                } else if (line.startsWith("Request Status: ")) {

                    order.setS_request(RequestStatus.valueOf(line.substring(16)));
                } else if (line.startsWith("Refund Initiated: ")) {

                    order.setRefundInitiated(Boolean.parseBoolean(line.substring(18)));
                } else if (line.startsWith("Delivery Address: ")) {

                    order.setDeliveryAddress(line.substring(19));
                } else if (line.startsWith("Payment Method: ")) {

                    order.setPaymentMethod(line.substring(17));
                } else if (line.startsWith("Order Date: ")) {

                    order.setOrderDate(); // Adjust to parse if necessary
                } else if (line.startsWith("  - ")) {

                    String[] parts = line.substring(4).split(" x ");
                    String itemName = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    Food_Item currItem = null;
                    for (Food_Item fItem : FoodMenu.Menu) {
                        if (fItem.getName().equals(itemName)) {
                            currItem = fItem;
                        }
                    }
                    order.addOrder_Cart(currItem, quantity);
                } else if (line.equals("---")) {
                    if (order != null) {
                        customer.getOrderHistory().add(order);
                        order = null;
                    }
                }
            }

            if (order != null) {
                customer.getOrderHistory().add(order);
            }
        }
    }

    // save cart to file
    public static void storeCart(Customer customer) throws IOException {
        // File name and directory
        String cart_file = "src/Cart/" + customer.getName() + "_" + customer.getMobile_no() + ".txt";

        // Retrieve the current order
        FoodOrder currentOrder = customer.getCurrentOrder();
        if (currentOrder == null) {
            throw new IllegalArgumentException("Customer does not have a current order.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cart_file))) {

            // Write the order cart
            writer.write("Order Cart:");
            writer.newLine();
            for (Map.Entry<Food_Item, Integer> entry : currentOrder.getOrder_Cart().entrySet()) {
                writer.write("  - " + entry.getKey().getName() + " x " + entry.getValue());
                writer.newLine();
            }
            writer.newLine();

            // Write the total
            writer.write("Total: " + currentOrder.getTotal());
            writer.newLine();

        }
    }

    // load cart for the user
    public static void loadCart(Customer customer) throws IOException {
        // File name and directory
        String cart_file = "src/Cart/" + customer.getName() + "_" + customer.getMobile_no() + ".txt";
        File file = new File(cart_file);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            FoodOrder currentOrder = new FoodOrder();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Total: ")) {
                    currentOrder.put_total(Integer.parseInt(line.substring(7)));
                } else if (line.startsWith("  - ")) {
                    String[] parts = line.substring(4).split(" x ");
                    String itemName = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    Food_Item currItem = null;
                    for (Food_Item fItem : FoodMenu.Menu) {
                        if (fItem.getName().equals(itemName)) {
                            currItem = fItem;
                        }
                    }
                    currentOrder.addOrder_Cart(currItem, quantity);
                }
            }
            reader.close();
            customer.setCurrentOrder(currentOrder); // Update the customer's current order
        }
    }

    public static void loadFoodMenu(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            HashSet<String> categorySet = new HashSet<>(); // To avoid duplicate categories

            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");

                if (details.length == 4) { // Ensure the line has all required fields
                    // Create and populate a Food_Item object
                    Food_Item item = new Food_Item();
                    item.setName(details[0]);
                    item.setPrice(Integer.parseInt(details[1]));
                    item.setCategory(details[2]);
                    item.setAvailability(details[3]);
                    item.setAvailable(details[3].equalsIgnoreCase("Available")); // Set boolean availability

                    // Add the item to the menu
                    FoodMenu.Menu.add(item);

                    // Add the category to the set
                    categorySet.add(details[2]);
                }
            }

            // Update the FoodCategories list with unique categories
            FoodMenu.FoodCategories.clear();
            FoodMenu.FoodCategories.addAll(categorySet);
        }
    }

    // save all orders in file
    public static void saveOrders() throws IOException {
        // Save Regular Orders
        String regularOrdersFile = "src/OrderHistory/Regular_Orders.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(regularOrdersFile))) {
            for (FoodOrder order : FoodOrder.RegularOrderList) {
                writer.write("Order ID: " + order.getOrder_id());
                writer.newLine();
                writer.write("Customer Name: " + order.getC_name());
                writer.newLine();
                writer.write("Customer Mobile: " + order.getC_Mno());
                writer.newLine();
                writer.write("Order Total: " + order.getTotal());
                writer.newLine();
                writer.write("Order Status: " + order.getStatus());
                writer.newLine();
                writer.write("Special Request: " + order.getSpecial_request());
                writer.newLine();
                writer.write("Request Status: " + order.getS_request());
                writer.newLine();
                writer.write("Refund Initiated: " + order.isRefundInitiated());
                writer.newLine();
                writer.write("Delivery Address: " + order.getDeliveryAddress());
                writer.newLine();
                writer.write("Payment Method: " + order.getPaymentMethod());
                writer.newLine();
                writer.write("Order Date: " + order.getOrderDate());
                writer.newLine();
                writer.write("Order Cart:");
                writer.newLine();
                for (var entry : order.getOrder_Cart().entrySet()) {
                    writer.write("  - " + entry.getKey().getName() + " x " + entry.getValue());
                    writer.newLine();
                }
                writer.write("---"); // Separator between orders
                writer.newLine();
            }
        }
    
        // Save VIP Orders
        String vipOrdersFile = "src/OrderHistory/VIP_Orders.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(vipOrdersFile))) {
            for (FoodOrder order : FoodOrder.VIPOrderList) {
                writer.write("Order ID: " + order.getOrder_id());
                writer.newLine();
                writer.write("Customer Name: " + order.getC_name());
                writer.newLine();
                writer.write("Customer Mobile: " + order.getC_Mno());
                writer.newLine();
                writer.write("Order Total: " + order.getTotal());
                writer.newLine();
                writer.write("Order Status: " + order.getStatus());
                writer.newLine();
                writer.write("Special Request: " + order.getSpecial_request());
                writer.newLine();
                writer.write("Request Status: " + order.getS_request());
                writer.newLine();
                writer.write("Refund Initiated: " + order.isRefundInitiated());
                writer.newLine();
                writer.write("Delivery Address: " + order.getDeliveryAddress());
                writer.newLine();
                writer.write("Payment Method: " + order.getPaymentMethod());
                writer.newLine();
                writer.write("Order Date: " + order.getOrderDate());
                writer.newLine();
                writer.write("Order Cart:");
                writer.newLine();
                for (var entry : order.getOrder_Cart().entrySet()) {
                    writer.write("  - " + entry.getKey().getName() + " x " + entry.getValue());
                    writer.newLine();
                }
                writer.write("---"); // Separator between orders
                writer.newLine();
            }
        }
    }
    public static void loadOrders() throws IOException {
        // Load Regular Orders
        String regularOrdersFile = "src/OrderHistory/Regular_Orders.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(regularOrdersFile))) {
            String line;
            FoodOrder currentOrder = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Order ID: ")) {
                    currentOrder = new FoodOrder();
                    currentOrder.putOrderid(Integer.parseInt(line.substring(10)));
                } else if (line.startsWith("Customer Name: ")) {
                    currentOrder.setC_name(line.substring(15));
                } else if (line.startsWith("Customer Mobile: ")) {
                    currentOrder.setC_Mno(line.substring(17));
                } else if (line.startsWith("Order Total: ")) {
                    currentOrder.put_total(Integer.parseInt(line.substring(13)));
                } else if (line.startsWith("Order Status: ")) {
                    currentOrder.setStatus(OrderStatus.valueOf(line.substring(14)));
                } else if (line.startsWith("Special Request: ")) {
                    currentOrder.setSpecial_request(line.substring(17));
                } else if (line.startsWith("Request Status: ")) {
                    currentOrder.setS_request(RequestStatus.valueOf(line.substring(16)));
                } else if (line.startsWith("Refund Initiated: ")) {
                    currentOrder.setRefundInitiated(Boolean.parseBoolean(line.substring(18)));
                } else if (line.startsWith("Delivery Address: ")) {
                    currentOrder.setDeliveryAddress(line.substring(18));
                } else if (line.startsWith("Payment Method: ")) {
                    currentOrder.setPaymentMethod(line.substring(16));
                } else if (line.startsWith("Order Date: ")) {
                    currentOrder.setOrderDate(); // Assuming date is current, adjust if stored in file
                } else if (line.startsWith("  - ")) {
                    String[] cartDetails = line.substring(4).split(" x ");
                    String itemName = cartDetails[0];
                    int quantity = Integer.parseInt(cartDetails[1]);
                    Food_Item item=null;
                    for (Food_Item food : FoodMenu.Menu) {
                        if (food.getName().equals(itemName)) {
                            item=food;
                        }
                    }
                    if (item != null) {
                        currentOrder.addOrder_Cart(item, quantity);
                    }
                } else if (line.equals("---")) {
                    // End of one order, add it to the list
                    if (currentOrder != null) {
                        FoodOrder.RegularOrderList.add(currentOrder);
                    }
                }
            }
        }
    
        // Load VIP Orders
        String vipOrdersFile = "src/OrderHistory/VIP_Orders.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(vipOrdersFile))) {
            String line;
            FoodOrder currentOrder = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Order ID: ")) {
                    currentOrder = new FoodOrder();
                    currentOrder.putOrderid(Integer.parseInt(line.substring(10)));
                } else if (line.startsWith("Customer Name: ")) {
                    currentOrder.setC_name(line.substring(15));
                } else if (line.startsWith("Customer Mobile: ")) {
                    currentOrder.setC_Mno(line.substring(17));
                } else if (line.startsWith("Order Total: ")) {
                    currentOrder.put_total(Integer.parseInt(line.substring(13)));
                } else if (line.startsWith("Order Status: ")) {
                    currentOrder.setStatus(OrderStatus.valueOf(line.substring(14)));
                } else if (line.startsWith("Special Request: ")) {
                    currentOrder.setSpecial_request(line.substring(17));
                } else if (line.startsWith("Request Status: ")) {
                    currentOrder.setS_request(RequestStatus.valueOf(line.substring(16)));
                } else if (line.startsWith("Refund Initiated: ")) {
                    currentOrder.setRefundInitiated(Boolean.parseBoolean(line.substring(18)));
                } else if (line.startsWith("Delivery Address: ")) {
                    currentOrder.setDeliveryAddress(line.substring(18));
                } else if (line.startsWith("Payment Method: ")) {
                    currentOrder.setPaymentMethod(line.substring(16));
                } else if (line.startsWith("Order Date: ")) {
                    currentOrder.setOrderDate(); // Assuming date is current, adjust if stored in file
                } else if (line.startsWith("  - ")) {
                    String[] cartDetails = line.substring(4).split(" x ");
                    String itemName = cartDetails[0];
                    int quantity = Integer.parseInt(cartDetails[1]);
                    Food_Item item=null;
                    for (Food_Item food : FoodMenu.Menu) {
                        if (food.getName().equals(itemName)) {
                            item=food;
                        }
                    }
                    if (item != null) {
                        currentOrder.addOrder_Cart(item, quantity);
                    }
                } else if (line.equals("---")) {
                    // End of one order, add it to the list
                    if (currentOrder != null) {
                        FoodOrder.VIPOrderList.add(currentOrder);
                    }
                }
            }
        }
    }
    
    

}
