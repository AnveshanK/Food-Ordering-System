import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LoginFunctionalityTest {
    AppFunctionalites app;

    @BeforeEach
    void setUp() {
        // Initialize objects or state before each test
        app = new AppFunctionalites();

    }

    @Test
    public void test_admin_login() {

        // admin username and password for testing
        String admin_username = "a"; //admin@ByteMe
        String admin_password = "a"; //IIITD_ByteMe

        Admin get_admin = app.signin_admin(admin_username, admin_password);
        assertNotNull(get_admin, "\nAdmin login failed.\nInvalid Credentials!");
    }

    @Test
    public void test_customer_login() {

        // customer username and password for testing
        String customer_username = "b";  // register user first through cli before testing
        String customer_password = "b";

        Customer get_customer = app.signin_customer(customer_username, customer_password);
        assertNotNull(get_customer, "\nCustom Message: Customer login failed.\nInvalid Credentials!");
    }
}
