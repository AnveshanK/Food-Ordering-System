
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AppFunctionalites {
    public static Scanner scan = new Scanner(System.in);
    public static Admin admin;

    // Enter Application
    public void start() {
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit Application");

        System.out.print("Input: ");
        int input1 = Integer.parseInt(scan.nextLine());
        System.out.println();

        if (input1 == 1) {
            this.signup();
        } else if (input1 == 2) {
            this.signin();
        } else if (input1 == 3) {
            try {
                StreamManagement.saveOrders();
            } catch (IOException e) {
                // do nothing
            }
            System.exit(0);
        }

    }



    // SignUp function for user
    public void signup() {
        System.out.println("1. Admin");
        System.out.println("2. Customer");
        System.out.println("3. Go Back");

        System.out.print("Input: ");
        int input2 = Integer.parseInt(scan.nextLine());
        System.out.println();

        if (input2 == 1) {
            // sign up as Admin
            if (Admin.admin_list.size() == 1) {
                System.out.println("\nAdmin already registered!\n");
                this.start();
            } else {
                admin = new Admin();
                Admin.admin_list.add(admin);
                System.out.println("\nAdmin Registered Successfully ! \n");
                try {
                    StreamManagement.loadFoodMenu("src/Menu.txt");
                    StreamManagement.loadOrders();
                } catch (IOException e) {
                    // do nothing
                }

                this.start();
            }
        } else if (input2 == 2) {
            // sign up as customer
            Customer customer = new Customer();

            System.out.print("Enter Name : ");
            customer.setName(scan.nextLine());

            System.out.print("Enter Mobile no. : ");
            customer.setMobile_no(scan.nextLine());

            System.out.print("Enter Username : ");
            customer.setUsername(scan.nextLine());

            System.out.print("Enter Password : ");
            customer.setPassword(scan.nextLine());

            System.out.println("Become VIP customer (1000 Rs per year) ");
            System.out.print("YES/NO : ");
            String vipinput = scan.nextLine();
            if (vipinput.equals("YES")) {
                customer.setVIP(true);
            } else if (vipinput.equals("NO")) {
                customer.setVIP(false);
            }
            Customer.customer_list.add(customer);

            // Save customer details to file
            try {
                StreamManagement.saveCustomerDetails(customer);
            } catch (IOException e) {
                System.out.println("Failed to save customer details in users file");
            }

            System.out.println("\nCustomer Registered Successfully ! \n");

            this.start();

        } else {
            this.start();
        }
    }

    // Login function for user
    public void signin() {
        System.out.println("1. Admin");
        System.out.println("2. Customer");
        System.out.println("3. Go Back");

        System.out.print("Input: ");
        int input_signin = Integer.parseInt(scan.nextLine());
        System.out.println();
        Functionalites user = null;

        if (input_signin == 1) {
            // login as admin
            Admin current_Admin = null;

            System.out.print("Enter Username : ");
            String u = scan.nextLine();
            System.out.print("Enter Password : ");
            String p = scan.nextLine();
            for (Admin a : Admin.admin_list) {
                if (a.getUsername().equals(u) && a.getPassword().equals(p)) {
                    current_Admin = a;
                }
            }
            if (current_Admin != null) {
                user = new AdminFunctionalities(current_Admin);
                // access_admin.beginOperations();
                // this.start();
            } else {
                System.out.println("Invalid Credentials");
                this.start();
            }

        } else if (input_signin == 2) {
            // login as customer
            Customer current_customer = null;

            System.out.print("Enter Username : ");
            String u = scan.nextLine();
            System.out.print("Enter Password : ");
            String p = scan.nextLine();
            for (Customer c : Customer.customer_list) {
                if (c.getUsername().equals(u) && c.getPassword().equals(p)) {
                    current_customer = c;
                }
            }

            Customer this_Customer=null;
            try {
                this_Customer=StreamManagement.loadCustomerDetails(u, p);
            } catch (IOException e) {
                // do nothing
            }
            if (this_Customer!=null && current_customer==null) {
                Customer.customer_list.add(this_Customer);
                System.out.println("\nUser Details Retreived Successfully\n");
                user = new CustomerFunctionalites(this_Customer);
            }
            else if (current_customer != null) {
                user = new CustomerFunctionalites(current_customer);}
                // access_customer.beginOperations();
                // this.start();
            else {
                System.out.println("Invalid Credentials");
                this.start();
            }

        } else {
            this.start();
        }
        // using polymorphism
        this.startOperations(user); // any of the two types of user (admin / customer)
        this.start();
    }
    public Customer signin_customer(String u, String p)
    {
        Customer current_customer = null;

//        System.out.print("Enter Username : ");
//        String u = "username";
//        System.out.print("Enter Password : ");
//        String p = "password";
        for (Customer c : Customer.customer_list) {
            if (c.getUsername().equals(u) && c.getPassword().equals(p)) {
                current_customer = c;
            }
        }
        if (current_customer!=null)
        {
            return current_customer;
        }

        Customer this_Customer=null;
        try {
            this_Customer=StreamManagement.loadCustomerDetails(u, p);
        } catch (IOException e) {
            // do nothing
        }


        return this_Customer;
    }

    public Admin signin_admin(String u, String p)
    {
        Admin current_Admin = null;

        Admin ByteMeAdmin=new Admin();
        String Admin_Username="a";   //admin@ByteMe
        String Admin_Password="a";   //IIITD_ByteMe
        ByteMeAdmin.setUsername(Admin_Username);
        ByteMeAdmin.setPassword(Admin_Password);

//        System.out.print("Enter Username : ");
//        String u = "username";
//        System.out.print("Enter Password : ");
//        String p = "password";
        if (ByteMeAdmin.getUsername().equals(u) && ByteMeAdmin.getPassword().equals(p))
        {
            current_Admin=ByteMeAdmin;
        }
        return current_Admin;
    }


    public void startOperations(Functionalites user) {
        user.beginOperations();
        return;
    }
}
