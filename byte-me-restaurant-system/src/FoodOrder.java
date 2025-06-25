import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

// class for each order 
public class FoodOrder {
    public static ArrayList<FoodOrder> RegularOrderList = new ArrayList<>(); // list of orders made by regular users
    public static ArrayList<FoodOrder> VIPOrderList = new ArrayList<>(); // list of orders made by vip users
    public static int orderid = 0; // Unique Order id for each order

    // attributes realted to each order
    private int total; // total order value
    private OrderStatus status; // stores status of your order
    private String Special_request; // stores special request on order
    private RequestStatus s_request; // stores status of your request on order (Accepted/Rejected)
    private boolean refundInitiated; // stores status for refund initiation
    private HashMap<Food_Item, Integer> Order_Cart; // contains food items in customer cart along with their quantities
    private String DeliveryAddress; // stores Delivery address of customer address
    private int Order_id; // unique order id
    private String paymentMethod; // payment method for your order
    private LocalDate OrderDate; // date of your order maintained to generate daily report

    private String C_name;  // keeps name of customer by whom order is made
    private String C_Mno;   // keeps mobile number of customer by which order is made

    public FoodOrder() {
        this.Order_Cart = new HashMap<>();
        this.setOrder_id();
        this.refundInitiated = false;
        this.status = OrderStatus.Pending;
        this.s_request = RequestStatus.NONE;
        this.total = 0;
    }

    // getters and setters for each order attributes

    public String getC_name() {
        return C_name;
    }

    public void setC_name(String c_name) {
        C_name = c_name;
    }

    public String getC_Mno() {
        return C_Mno;
    }

    public void setC_Mno(String c_Mno) {
        C_Mno = c_Mno;
    }

    public LocalDate getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate() {
        this.OrderDate = LocalDate.now();
    }

    public int getTotal() {
        return total;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDeliveryAddress() {
        return DeliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        DeliveryAddress = deliveryAddress;
    }

    public void setOrder_Cart(HashMap<Food_Item, Integer> order_Cart) {
        Order_Cart = order_Cart;
    }

    public void set_cart_Total() {
        this.total = 0;
        for (Food_Item fitem : this.getOrder_Cart().keySet()) {
            total += fitem.getPrice() * (this.getOrder_Cart().get(fitem));
        }
        return;
    }
    public void put_total(int t)
    {
        total=t;
    }

    public int getOrder_id() {
        return Order_id;
    }

    public void setOrder_id() {
        Order_id = (++FoodOrder.orderid);
    }
    public void putOrderid(int id){
        Order_id=id;
    }
    

    public OrderStatus getStatus() {
        return status;
    }

    public HashMap<Food_Item, Integer> getOrder_Cart() {
        return Order_Cart;
    }

    public void addOrder_Cart(Food_Item item, int quantitiy) {
        this.Order_Cart.put(item, quantitiy);
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getSpecial_request() {
        return Special_request;
    }

    public void setSpecial_request(String special_request) {
        Special_request = special_request;
    }

    public RequestStatus getS_request() {
        return s_request;
    }

    public void setS_request(RequestStatus s_request) {
        this.s_request = s_request;
    }

    public boolean isRefundInitiated() {
        return refundInitiated;
    }

    public void setRefundInitiated(boolean refundInitiated) {
        this.refundInitiated = refundInitiated;
    }

}
