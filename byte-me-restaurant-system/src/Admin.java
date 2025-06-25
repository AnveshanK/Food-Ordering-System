
import java.util.ArrayList;

// common user class for both Admin and Customer
abstract class user {

    // contains login credentials
    private String Username;
    private String Password;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}

public class Admin extends user {
    // Main Admin class

    public static ArrayList<Admin> admin_list = new ArrayList<>();

    public Admin() {
        // there can be only single admin account
        this.setUsername("a");   // admin@ByteMe
        this.setPassword("a");   // IIITD_ByteMe
    }

    public static ArrayList<Admin> getAdmin_list() {
        return admin_list;
    }

    public static void setAdmin_list(ArrayList<Admin> admin_list) {
        Admin.admin_list = admin_list;
    }

}
