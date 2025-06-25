import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class OrderingItemsTest {
    CustomerFunctionalites customerFunctionalites;
    Customer customer;

    @BeforeEach
    void setUp() {
        // Initialize objects or state before each test
        customer=new Customer();
        customerFunctionalites = new CustomerFunctionalites(customer);
        try {
            StreamManagement.loadFoodMenu("src/Menu.txt");
        } catch (IOException e) {
            // do nothing
        }

    }

    @Test
    public void test_order() {
        // food name to test if possible to order
//        String food_name = "Maggi";
        String food_name = "Crax";


        Food_Item food_item = customerFunctionalites.order_items(food_name);
        assertNotNull(food_item, "\nFailed to order item.\nItem out of stock!");
    }

}
