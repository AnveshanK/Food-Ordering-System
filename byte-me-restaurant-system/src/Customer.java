import java.util.ArrayList;

// main customer class
public class Customer extends user {

    public static ArrayList<Customer> customer_list = new ArrayList<>();
    // attributes related to customer details
    private String name;
    private String Mobile_no;
    public Boolean VIP;
    private ArrayList<FoodOrder> orderHistory; // array list to store all orders of a customer after checkout
    private FoodOrder CurrentOrder; // operate on this order until checkout

    public Customer() {
        // setup
        orderHistory = new ArrayList<>();
        CurrentOrder = new FoodOrder();
        this.setVIP(false);
    }

    // getters and setters for customer attributes
    public ArrayList<FoodOrder> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(ArrayList<FoodOrder> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public FoodOrder getCurrentOrder() {
        return CurrentOrder;
    }

    public void setCurrentOrder(FoodOrder currentOrder) {
        CurrentOrder = currentOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_no() {
        return Mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        Mobile_no = mobile_no;
    }

    public Boolean getVIP() {
        return VIP;
    }

    public void setVIP(Boolean vIP) {
        VIP = vIP;
    }

    public static ArrayList<Customer> getCustomer_list() {
        return customer_list;
    }

    public static void setCustomer_list(ArrayList<Customer> customer_list) {
        Customer.customer_list = customer_list;
    }

}
