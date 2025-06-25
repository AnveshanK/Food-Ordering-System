
import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeSet;

// comparator to sort food items in ascending order of their price
class FoodPriceComparator_ascending implements Comparator<Food_Item> {
    @Override
    public int compare(Food_Item item1, Food_Item item2) {
        return Integer.compare(item1.getPrice(), item2.getPrice());
    }
}

// comparator to sort food items in descending order of their price
class FoodPriceComparator_descending implements Comparator<Food_Item> {
    @Override
    public int compare(Food_Item item1, Food_Item item2) {
        return Integer.compare(item2.getPrice(), item1.getPrice());
    }
}

// use custormer functionalites
public class CustomerFunctionalites implements Functionalites {
    public static Scanner scan = new Scanner(System.in);

    private Customer current_Customer; // object of customer who logged in
    private FoodOrder CurrentOrder; // customer's current order

    public CustomerFunctionalites(Customer customer) {
        this.current_Customer = customer;
        this.CurrentOrder = current_Customer.getCurrentOrder();
    }

    public void beginOperations() {
        // customer interface
        System.out.println("1. Browse Menu");
        System.out.println("2. Cart Operations");
        System.out.println("3. Track Order");
        System.out.println("4. Item Reviews");
        System.out.println("5. Log Out");

        System.out.print("Input: ");
        int input_cus_op = Integer.parseInt(scan.nextLine());
        System.out.println();

        if (input_cus_op == 1) {
            // Menu related operations
            System.out.println("1. View All Items");
            System.out.println("2. Search Item");
            System.out.println("3. Filter By Category");
            System.out.println("4. Sort By Price");
            System.out.println("5. Go Back");

            System.out.print("Input: ");
            int input_menu = Integer.parseInt(scan.nextLine());
            System.out.println();
            if (input_menu == 1) {
                // view all items in the menu
                System.out.println("Item Name : Price (in Rs.) : Availability");
                for (Food_Item item : FoodMenu.Menu) {
                    System.out.println(item.getName() + " : " + item.getPrice() + " : " + item.getAvailability());
                }
            } else if (input_menu == 2) {
                // search for an item in menu by name
                Food_Item currItem = null;
                System.out.print("Enter keyword : ");
                String fname = scan.nextLine();
                for (Food_Item fItem : FoodMenu.Menu) {
                    if (fItem.getName().contains(fname)) {
                        currItem = fItem;
                        if (currItem != null) {
                            System.out.println(
                                    currItem.getName() + " : " + currItem.getPrice() + " : "
                                            + currItem.getAvailability());
                        }
                    }
                }
                if (currItem == null) {
                    System.out.println("Item not in the Food Menu !");
                }
            } else if (input_menu == 3) {
                // filter menu items by category
                System.out.println("Available Food Categories :-");

                for (String category : FoodMenu.FoodCategories) {
                    System.out.println(category);
                }
                System.out.println();
                System.out.print("Enter Category : ");
                String category = scan.nextLine();
                System.out.println("Item Name : Price (in Rs.) : Availability");

                for (Food_Item item : FoodMenu.Menu) {
                    if (item.getCategory().equals(category)) {
                        System.out.println(item.getName() + " : " + item.getPrice() + " : " + item.getAvailability());
                    }
                }
            } else if (input_menu == 4) {
                // sort menu by price using TreeSet and custom comparators
                System.out.println("1. Ascending");
                System.out.println("2. Descending");
                System.out.print("Input : ");
                int inputsort = Integer.parseInt(scan.nextLine());
                System.out.println("Item Name : Price (in Rs.) : Availability");

                if (inputsort == 1) {
                    TreeSet<Food_Item> sortPrice = new TreeSet<>(new FoodPriceComparator_ascending());
                    for (Food_Item item : FoodMenu.Menu) {
                        sortPrice.add(item);
                    }
                    for (Food_Item item : sortPrice) {
                        System.out.println(item.getName() + " : " + item.getPrice() + " : " + item.getAvailability());
                    }
                } else if (inputsort == 2) {
                    TreeSet<Food_Item> sortPrice = new TreeSet<>(new FoodPriceComparator_descending());
                    for (Food_Item item : FoodMenu.Menu) {
                        sortPrice.add(item);
                    }
                    for (Food_Item item : sortPrice) {
                        System.out.println(item.getName() + " : " + item.getPrice() + " : " + item.getAvailability());
                    }
                }
                System.out.println();
            } else if (input_menu == 5) {

            }
        } else if (input_cus_op == 2) {
            // cart realted operations
            System.out.println("1. Add Items");
            System.out.println("2. Modify Quantities");
            System.out.println("3. Remove Items");
            System.out.println("4. View Total");
            System.out.println("5. Checkout");
            System.out.println("6. Go Back");

            System.out.print("Input: ");
            int input_cart = Integer.parseInt(scan.nextLine());
            System.out.println();

            if (input_cart == 1) {
                // add item in the cart
                Food_Item currItem = null;
                System.out.print("Enter Food Name : ");
                String fname = scan.nextLine();
                System.out.print("Enter Food Quantitiy : ");
                int quantitiy = Integer.parseInt(scan.nextLine());
                for (Food_Item fItem : FoodMenu.Menu) {
                    if (fItem.getName().equals(fname)) {
                        currItem = fItem;
                    }
                }
                if (currItem != null) {
                    if (currItem.getAvailable() ) {
                        if (quantitiy>=0){
                            CurrentOrder.addOrder_Cart(currItem, quantitiy);
                            CurrentOrder.set_cart_Total();
                        }

                    }
                    else
                    {
                        System.out.println("Item currently not Available !");
                    }
                } else {
                    System.out.println("Item not in the Food Menu !");
                }

            } else if (input_cart == 2) {
                // modify quantity of an item in the cart
                Food_Item currItem = null;
                System.out.print("Enter Food Name : ");
                String fname = scan.nextLine();

                for (Food_Item fItem : CurrentOrder.getOrder_Cart().keySet()) {
                    if (fItem.getName().equals(fname)) {
                        currItem = fItem;
                    }
                }
                if (currItem != null) {
                    System.out.println(currItem.getName() + " : x" + CurrentOrder.getOrder_Cart().get(currItem));
                    System.out.print("Enter new Food Quantitiy : ");
                    int quantitiy = Integer.parseInt(scan.nextLine());
                    if (quantitiy>=0){
                        CurrentOrder.addOrder_Cart(currItem, quantitiy);
                        CurrentOrder.set_cart_Total();
                    }
                } else {
                    System.out.println("Item not in the Cart !");
                }

            } else if (input_cart == 3) {
                // remove an item from the cart
                Food_Item currItem = null;
                System.out.print("Enter Food Name : ");
                String fname = scan.nextLine();
                for (Food_Item fItem : CurrentOrder.getOrder_Cart().keySet()) {
                    if (fItem.getName().equals(fname)) {
                        currItem = fItem;
                    }
                }
                if (currItem != null) {
                    CurrentOrder.getOrder_Cart().remove(currItem);
                    CurrentOrder.set_cart_Total();
                } else {
                    System.out.println("Item not in the Cart !");
                }

            } else if (input_cart == 4) {
                // view total value of order
                CurrentOrder.set_cart_Total();
                int total = CurrentOrder.getTotal();
                for (Food_Item fItem : CurrentOrder.getOrder_Cart().keySet()) {
                    System.out.println(fItem.getName() + " : x" + CurrentOrder.getOrder_Cart().get(fItem) + " : "
                            + (fItem.getPrice() * CurrentOrder.getOrder_Cart().get(fItem)) + " Rs");
                }
                System.out.println("Cart Total = " + total + " Rs");
            } else if (input_cart == 5) {
                // proceed to checkout an order by entering address, payment mode and any
                // special request related to the order
                System.out.println();
                System.out.print("Enter Special Request (enter 0 if none) : ");
                CurrentOrder.setSpecial_request(scan.nextLine());
                System.out.print("Enter Delivery Address : ");
                CurrentOrder.setDeliveryAddress(scan.nextLine());
                System.out.print("Enter Payment Method : ");
                CurrentOrder.setPaymentMethod(scan.nextLine());

                if (!CurrentOrder.getSpecial_request().equals("0")) {
                    CurrentOrder.setS_request(RequestStatus.Pending);
                }

                CurrentOrder.setC_name(current_Customer.getName());
                CurrentOrder.setC_Mno(current_Customer.getMobile_no());
                CurrentOrder.setOrderDate();
                CurrentOrder.set_cart_Total();
                if (current_Customer.getVIP() == true) {
                    FoodOrder.VIPOrderList.add(CurrentOrder);
                } else {
                    FoodOrder.RegularOrderList.add(CurrentOrder);
                }

                current_Customer.getOrderHistory().add(CurrentOrder);
                System.out.println();
                System.out.println("Food Ordered Successfully !");
                System.out.println("Order Id = " + CurrentOrder.getOrder_id());
                System.out.println("* Please note the order id for future reference.");
                System.out.println();

                // update file of pending orders
                FileOperations.saveOrdersToFile();
                try {
                    StreamManagement.storeOrderHistory(current_Customer);
                } catch (IOException e) {
                    // do nothing
                }

                current_Customer.setCurrentOrder(new FoodOrder()); // after checkout you can create new order
                this.CurrentOrder = current_Customer.getCurrentOrder();

            } else if (input_cart == 6) {

            }

            // update cart file after every cart operation
            try {
                StreamManagement.storeCart(current_Customer);
            } catch (IOException e) {
                // do nothing
            }

        } else if (input_cus_op == 3) {
            // track your orders
            System.out.println("1. View Order Status");
            System.out.println("2. Cancel Order");
            System.out.println("3. Order History");
            System.out.println("4. Go Back");

            System.out.print("Input: ");
            int input_track_order = Integer.parseInt(scan.nextLine());
            System.out.println();

            if (input_track_order == 1) {
                // view status for your orders
                int num_orders = current_Customer.getOrderHistory().size();
                if (num_orders > 0) {
                    System.out.print("Enter Order id : ");
                    int orderid = Integer.parseInt(scan.nextLine());
                    FoodOrder this_order = null;
                    for (FoodOrder thisOrder : current_Customer.getOrderHistory()) {
                        if (thisOrder.getOrder_id() == orderid) {
                            this_order = thisOrder;
                        }
                    }
                    if (this_order != null) {
                        System.out.println(
                                "Order id : " + this_order.getOrder_id() + "\tStatus : " + this_order.getStatus());
                    } else {
                        System.out.println("Order not found !");
                    }
                    System.out.println();
                } else {
                    System.out.println("No Orders Made !");
                    System.out.println();
                }
            } else if (input_track_order == 2) {
                // cancel an order
                int num_orders = current_Customer.getOrderHistory().size();
                if (num_orders > 0) {
                    System.out.print("Enter Order id : ");
                    int orderid = Integer.parseInt(scan.nextLine());
                    FoodOrder this_order = null;
                    for (FoodOrder thisOrder : current_Customer.getOrderHistory()) {
                        if (thisOrder.getOrder_id() == orderid) {
                            this_order = thisOrder;
                        }
                    }
                    if (this_order != null) {
                        OrderStatus status = this_order.getStatus();
                        if (status == OrderStatus.Pending || status == OrderStatus.OrderReceived
                                || status == OrderStatus.Preparing) {
                            this_order.setStatus(OrderStatus.Cancelled);
                            System.out.println("Order Cancelled !");
                            try {
                                StreamManagement.storeOrderHistory(current_Customer); // updates order history
                            } catch (IOException e) {
                                // do nothing
                            }
                            System.out.println(
                                    "Order id : " + this_order.getOrder_id() + "\tStatus : " + this_order.getStatus());
                            if (current_Customer.getVIP()==true) {
                                for (FoodOrder order : FoodOrder.VIPOrderList) {
                                    if (order.getOrder_id()==orderid) {
                                        order.setStatus(OrderStatus.Cancelled);
                                    }
                                }
                            }
                            else if (current_Customer.getVIP()==false) {
                                for (FoodOrder order : FoodOrder.RegularOrderList) {
                                    if (order.getOrder_id()==orderid) {
                                        order.setStatus(OrderStatus.Cancelled);
                                    }
                                }
                            }

                        } else {
                            System.out.println(
                                    "Order id : " + this_order.getOrder_id() + "\tStatus : " + this_order.getStatus());
                            System.out.println("You can't cancel the order now !");
                        }
                    } else {
                        System.out.println("Order not found !");
                    }
                    System.out.println();
                } else {
                    System.out.println("No Orders Made !");
                    System.out.println();
                }

                // update file of pending orders
                FileOperations.saveOrdersToFile();

            } else if (input_track_order == 3) {
                // view your order history and repeat any order if you want
                for (FoodOrder order : current_Customer.getOrderHistory()) {
                    System.out.println("Customer Name : " + order.getC_name());
                    System.out.println("Mobile Number : " + order.getC_Mno());
                    System.out.println("Order Id : " + order.getOrder_id());
                    System.out.println("Date : " + order.getOrderDate());
                    System.out.println();
                    for (Food_Item fItem : order.getOrder_Cart().keySet()) {
                        System.out.println(fItem.getName() + " : x" + order.getOrder_Cart().get(fItem));
                    }
                    System.out.println();
                    System.out.println(order.getTotal() + " Rs");
                    System.out.println("Order Status : " + order.getStatus());
                    if (order.getStatus() == OrderStatus.Cancelled || order.getStatus() == OrderStatus.Denied) {
                        System.out.println("Refund Initiated : " + order.isRefundInitiated());
                    }
                    System.out.println("Special Request Status : " + order.getS_request());
                    System.out.println("Payment Method : " + order.getPaymentMethod());
                    System.out.println("Delivery Address : " + order.getDeliveryAddress());
                    System.out.println();
                    System.out.println();
                }
                // reorder any previously ordered meal if desired
                System.out.print("Repeat any order (0/1): "); // 0 means don't want to reorder any previous meal and 1
                                                              // means yes
                int input_reorder = Integer.parseInt(scan.nextLine());
                if (input_reorder == 1) {
                    System.out.print("Enter id for order to repeat : "); // enter id for which order cart to repeat
                    int reorder_id = Integer.parseInt(scan.nextLine());
                    FoodOrder reorder = null;
                    for (FoodOrder order : current_Customer.getOrderHistory()) {
                        if (order.getOrder_id() == reorder_id) {
                            reorder = order;
                        }
                    }
                    if (reorder != null) {
                        current_Customer.setCurrentOrder(new FoodOrder());
                        CurrentOrder = current_Customer.getCurrentOrder();
                        CurrentOrder.setOrder_Cart(reorder.getOrder_Cart());

                        CurrentOrder.set_cart_Total();
                        System.out.println("New order id : " + CurrentOrder.getOrder_id());
                        System.out.println("You can proceed to checkout.");
                        // update cart file after every cart operation
                        try {
                            StreamManagement.storeCart(current_Customer);
                        } catch (IOException e) {
                            // do nothing
                        }
                    } else {
                        System.out.println("Order not found !");
                    }
                    System.out.println();
                }
            } else if (input_track_order == 4) {

            }

        } else if (input_cus_op == 4) {
            // item review related options
            System.out.println("1. Provide Review");
            System.out.println("2. View Reviews");
            System.out.println("3. Go Back");
            System.out.print("Input : ");
            int input_review = Integer.parseInt(scan.nextLine());
            if (input_review == 1) {
                // provide review for any food item
                System.out.print("Enter Food Name : ");
                String fname = scan.nextLine();
                Food_Item reviewItem = null;
                for (Food_Item fItem : FoodMenu.Menu) {
                    if (fItem.getName().equals(fname)) {
                        reviewItem = fItem;
                    }
                }
                if (reviewItem != null) {
                    System.out.print("Enter Review : ");
                    String review = scan.nextLine();
                    reviewItem.addReview(review, current_Customer.getName());
                } else {
                    System.out.println("Food Item not in the Menu !");
                }
            } else if (input_review == 2) {
                // view reviews for any food item
                System.out.print("Enter Food Name : ");
                String fname = scan.nextLine();
                Food_Item reviewItem = null;
                for (Food_Item fItem : FoodMenu.Menu) {
                    if (fItem.getName().equals(fname)) {
                        reviewItem = fItem;
                    }
                }
                if (reviewItem != null) {
                    for (String reviewString : reviewItem.getReview()) {
                        System.out.println(reviewString);
                    }
                    System.out.println();
                } else {
                    System.out.println("Food Item not in the Menu !");
                }
            } else if (input_review == 3) {

            }

        } else if (input_cus_op == 5) {
            return; // log out
        }
        this.beginOperations();

    }

    public Food_Item order_items(String fname)
    {
        Food_Item currItem = null;
//        System.out.print("Enter Food Name : ");
//        String fname = scan.nextLine();
//        System.out.print("Enter Food Quantitiy : ");
//        int quantitiy = Integer.parseInt(scan.nextLine());
        for (Food_Item fItem : FoodMenu.Menu) {
            if (fItem.getName().equals(fname)) {
                currItem = fItem;
            }
        }
        if (currItem != null) {
            if (currItem.getAvailable()) {
//                CurrentOrder.addOrder_Cart(currItem, quantitiy);
//                CurrentOrder.set_cart_Total();
            }
            else
            {
                currItem=null;
//                System.out.println("Item currently not Available !");
            }
        } else {
//            System.out.println("Item not in the Food Menu !");
        }
        return currItem;
    }

    public void add_cart(Customer customer,String fname, int quantitiy)
    {
        Food_Item currItem = null;
        for (Food_Item fItem : FoodMenu.Menu) {
            if (fItem.getName().equals(fname)) {
                currItem = fItem;
            }
        }
        customer.getCurrentOrder().addOrder_Cart(currItem,quantitiy);
        customer.getCurrentOrder().set_cart_Total();
        return;
    }

    public void modify_cart(Customer customer,String fname, int quantitiy)
    {
        Food_Item currItem = null;
        for (Food_Item fItem : FoodMenu.Menu) {
            if (fItem.getName().equals(fname)) {
                currItem = fItem;
                break;
            }
        }
        if (quantitiy>=0)
        {
            customer.getCurrentOrder().addOrder_Cart(currItem,quantitiy);
            customer.getCurrentOrder().set_cart_Total();
        }
        return;
    }


}
