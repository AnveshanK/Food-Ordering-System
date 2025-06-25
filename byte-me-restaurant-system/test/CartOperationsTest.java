import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartOperationsTest {
    CustomerFunctionalites customerFunctionalites;
    Customer customer;

    @BeforeEach
    void setUp() {
        // Initialize objects or state before each test
        customerFunctionalites = new CustomerFunctionalites(new Customer());
        try {
            StreamManagement.loadFoodMenu("src/Menu.txt");
        } catch (IOException e) {
            // do nothing
        }

        customer=new Customer();

        for (Food_Item fItem : FoodMenu.Menu) {
            if (fItem.getName().equals("Apple")) {  //"Apple = 100"
                customer.getCurrentOrder().addOrder_Cart(fItem,1);
                customer.getCurrentOrder().set_cart_Total();
                break;
            }
        }

    }

    @Test
    public void test_add_to_cart() {
        // food name to add to cart
        String food_name = "Maggi"; // "Maggi=20"
        int quantity=2;

        customerFunctionalites.add_cart(customer,food_name,quantity);

        int actual_total = customer.getCurrentOrder().getTotal();
        int expected_total=140;
        assertEquals(expected_total, actual_total,"\nFailed to update total cart value on adding item.\nTotal price updated inaccurately!");
    }

    @Test
    public void test_modify_cart() {
        // food name to modify cart
        String food_name = "Apple";
        int quantity=3; // -2

        customerFunctionalites.modify_cart(customer,food_name,quantity);

        int actual_total = customer.getCurrentOrder().getTotal();
        int expected_total=300; // 100
        assertEquals(expected_total, actual_total,"\nFailed to update total cart value on modifying item quantitiy.\nTotal price updated inaccurately!");
    }


}
