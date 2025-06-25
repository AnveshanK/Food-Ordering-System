import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GUIOrder {
    public static ArrayList<GUIOrder> order_GUI=new ArrayList<>();

    private int order_id;
    private OrderStatus order_Status;
    private int order_total;
    private HashMap<String,Integer> order_cart;
    private String order_items;
    

    
    public String getOrder_items() {
        return order_items;
    }
    public void setOrder_items(String order_items) {
        this.order_items = order_items;
    }
    public GUIOrder() {
        order_cart = new HashMap<>();
    }
    public int getOrder_id() {
        return order_id;
    }
    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
    public OrderStatus getOrder_Status() {
        return order_Status;
    }
    public void setOrder_Status(OrderStatus order_Status) {
        this.order_Status = order_Status;
    }
    public int getOrder_total() {
        return order_total;
    }
    public void setOrder_total(int order_total) {
        this.order_total = order_total;
    }
    public HashMap<String, Integer> getOrder_cart() {
        return order_cart;
    }
    public void setOrder_cart(HashMap<String, Integer> order_cart) {
        this.order_cart = order_cart;
    }

    public static ObservableList<GUIOrder> getList()
    {
        ObservableList<GUIOrder> orders=FXCollections.observableArrayList();
        for (GUIOrder order : GUIOrder.order_GUI) {
            String order_string="";
            for (Map.Entry<String,Integer> item : order.getOrder_cart().entrySet()) {
                order_string=order_string+item.getKey()+" (x"+item.getValue()+"), ";
            }
            order.setOrder_items(order_string);
            orders.add(order);
        }
        return orders;
    }
    
}
