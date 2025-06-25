import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class FileOperations {

    // write menu to file
    public static void save_menu() {
        try (BufferedWriter in = new BufferedWriter(new FileWriter("src/Menu.txt", false))) {
            ArrayList<Food_Item> menu=FoodMenu.Menu;
            for (Food_Item item : menu) {
                // Create a string representation of the food item
                String line = item.getName() + "," +
                        item.getPrice() + "," +
                        item.getCategory() + "," +
                        item.getAvailability();
                in.write(line);
                in.newLine(); // Add a new line after each food item
            }
            // System.out.println("Menu has been written to the file.");
        } catch (IOException e) {
            System.out.println("Error saving the menu to file: " + e.getMessage());
        }
    }

    // write pending orders to file
    public static void saveOrdersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/PendingOrders.txt"))) {
            // Save each order in the list (e.g., RegularOrderList, VIPOrderList)
            for (FoodOrder order : FoodOrder.VIPOrderList) {
                if (order.getStatus()!=OrderStatus.Cancelled && order.getStatus()!=OrderStatus.Completed && order.getStatus()!=OrderStatus.Delivered && order.getStatus()!=OrderStatus.Denied) {
                    
                    writeOrderToFile(writer, order);
                }
            }
            for (FoodOrder order : FoodOrder.RegularOrderList) {
                if (order.getStatus()!=OrderStatus.Cancelled && order.getStatus()!=OrderStatus.Completed && order.getStatus()!=OrderStatus.Delivered && order.getStatus()!=OrderStatus.Denied) {
                    writeOrderToFile(writer, order);
                }
            }
            // System.out.println("Orders have been saved to the file.");
        } catch (IOException e) {
            System.out.println("Error saving orders to file: " + e.getMessage());
        }
    }

    private static void writeOrderToFile(BufferedWriter writer, FoodOrder order) throws IOException {
        writer.write("Order ID: " + order.getOrder_id());
        writer.newLine();
        writer.write("Status: " + order.getStatus());
        writer.newLine();
        writer.write("Total: " + order.getTotal());
        writer.newLine();
        writer.write("Items Ordered:");
        writer.newLine();

        // Write food items and quantities
        for (Map.Entry<Food_Item, Integer> entry : order.getOrder_Cart().entrySet()) {
            Food_Item item = entry.getKey();
            int quantity = entry.getValue();
            writer.write(item.getName() + " x " + quantity);
            writer.newLine();
        }

        writer.write("----------------------------------------");
        writer.newLine(); // Separate orders
    }

    // load pending orders from file
    public static void loadOrdersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/PendingOrders.txt"))) {
            String line;
            GUIOrder currentOrder = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("Order ID: ")) {
                    if (currentOrder != null) {
                        // If there's an existing order, save it before moving to the next
                        GUIOrder.order_GUI.add(currentOrder);
                    }
                    currentOrder = new GUIOrder();
                    currentOrder.setOrder_id(Integer.parseInt(line.substring(10).trim())); // Extract Order ID
                } else if (line.startsWith("Status: ")) {
                    currentOrder.setOrder_Status(OrderStatus.valueOf(line.substring(8).trim())); // Extract Status
                } else if (line.startsWith("Total: ")) {
                    currentOrder.setOrder_total(Integer.parseInt(line.substring(7).trim())); // Extract Total
                } else if (line.startsWith("Items Ordered:")) {
                    // Process the items
                    while ((line = reader.readLine()) != null
                            && !line.trim().equals("----------------------------------------")) {
                        line = line.trim();
                        if (!line.isEmpty()) {
                            String[] parts = line.split(" x ");
                            String itemName = parts[0];
                            int quantity = Integer.parseInt(parts[1]);

                            currentOrder.getOrder_cart().put(itemName, quantity);
                            
                        }
                    }
                }
            }

            // Add the last order if any
            if (currentOrder != null) {
                GUIOrder.order_GUI.add(currentOrder);
            }

            System.out.println("Orders have been loaded from the file.");
        } catch (IOException e) {
            System.out.println("Error loading orders from file: " + e.getMessage());
        }
    }

    // load menu from file
    public static void loadMenuFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/Menu.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line by commas to extract the fields
                String[] fields = line.split(",");
                
                // Create a new Food_Item object
                GUI_Menu_Item item = new GUI_Menu_Item(fields[0],fields[2],(Integer.parseInt(fields[1])),fields[3]);
                // item.setItem_name(fields[0]);
                // item.setPrice(Integer.parseInt(fields[1]));
                // item.setCategory(fields[2]);
                // item.setAvailability(fields[3]);
                
                GUI_Menu_Item.GUI_Menu.add(item);
            }
            System.out.println("Menu has been loaded from the file.");
        } catch (IOException e) {
            System.out.println("Error loading the menu from file: " + e.getMessage());
        }
    }

    

    

}