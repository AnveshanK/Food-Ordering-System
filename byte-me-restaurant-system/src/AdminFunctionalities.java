
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class AdminFunctionalities implements Functionalites {
    // perform Admin Operations
    public static Scanner scan = new Scanner(System.in);
    @SuppressWarnings("unused")
    private Admin currentAdmin;

    AdminFunctionalities(Admin admin) {
        this.currentAdmin = admin;
    }

    public void beginOperations() {
        // Admin operations
        System.out.println("1. Manage Menu");
        System.out.println("2. Manage Order");
        System.out.println("3. Generate Report");
        System.out.println("4. Log Out");

        System.out.print("Input: ");
        int input_adm_op = Integer.parseInt(scan.nextLine());
        System.out.println();

        if (input_adm_op == 1) {
            // MENU related operations
            System.out.println("1. Add New Items");
            System.out.println("2. Update Items");
            System.out.println("3. Remove Items");
            System.out.println("4. Go Back");

            System.out.print("Input: ");
            int input_menu = Integer.parseInt(scan.nextLine());
            System.out.println();

            if (input_menu == 1) {
                // add an item to the Food Menu
                Food_Item item = new Food_Item();

                System.out.print("Enter Food Name : ");
                item.setName(scan.nextLine());
                System.out.print("Enter Food Category : ");
                item.setCategory(scan.nextLine());
                System.out.print("Enter Food Price : ");
                item.setPrice(Integer.parseInt(scan.nextLine()));
                System.out.print("Enter Food Availability (0/1) : ");
                int avail_input = Integer.parseInt(scan.nextLine());
                if (avail_input == 0) {
                    item.setAvailable(false);
                    item.setAvailability("Not Available");
                } else if (avail_input == 1) {
                    item.setAvailable(true);
                    item.setAvailability("Available");
                }
                // item.setAvailability(scan.nextLine());
                if (!FoodMenu.FoodCategories.contains(item.getCategory())) {
                    FoodMenu.FoodCategories.add(item.getCategory());
                }

                FoodMenu.Menu.add(item);
                System.out.println("Item added to the Menu !");
                System.out.println();

            } else if (input_menu == 2) {
                // admin can update Food item Price or Availability status
                Food_Item currItem = null;
                System.out.print("Enter Food Name : ");
                String fname = scan.nextLine();
                for (Food_Item fItem : FoodMenu.Menu) {
                    if (fItem.getName().equals(fname)) {
                        currItem = fItem;
                    }
                }
                if (currItem != null) {
                    System.out.println("1. Update Price");
                    System.out.println("2. Update Availability");
                    System.out.print("Input : ");
                    int input_update = Integer.parseInt(scan.nextLine());
                    if (input_update == 1) {
                        // change price of food item
                        System.out.print("Enter new Price : ");
                        currItem.setPrice(Integer.parseInt(scan.nextLine()));
                    } else if (input_update == 2) {
                        // change availability of food item
                        System.out.print("Enter Availability (0/1) : ");
                        int avail_input = Integer.parseInt(scan.nextLine());
                        if (avail_input == 0) {
                            currItem.setAvailable(false);
                            currItem.setAvailability("Not Available");
                        } else if (avail_input == 1) {
                            currItem.setAvailable(true);
                            currItem.setAvailability("Available");
                        }
                    }

                } else if (currItem == null) {
                    System.out.println("Item not found !");
                }
            } else if (input_menu == 3) {
                // Remove Items from Food Menu
                Food_Item currItem = null;
                System.out.print("Enter Food Name : ");
                String fname = scan.nextLine();
                for (Food_Item fItem : FoodMenu.Menu) {
                    if (fItem.getName().equals(fname)) {
                        currItem = fItem;
                    }
                }
                if (currItem != null) {
                    // if this item was in order pending or received orders but not processed yet
                    // then set order status to denied
                    FoodMenu.Menu.remove(currItem);
                    String item_name = currItem.getName();
                    if (FoodOrder.VIPOrderList.size() > 0) {
                        for (FoodOrder order : FoodOrder.VIPOrderList) {
                            for (Food_Item fItem : order.getOrder_Cart().keySet()) {
                                if (fItem.getName().equals(item_name) && (order.getStatus() == OrderStatus.Pending
                                        || order.getStatus() == OrderStatus.OrderReceived)) {
                                    order.setStatus(OrderStatus.Denied);
                                    // update order history of user also
                                    for (Customer c : Customer.customer_list) {
                                        if (c.getName().equals(order.getC_name())) {

                                            try {
                                                StreamManagement.storeOrderHistory(c);
                                            } catch (IOException e) {
                                                // do nothing
                                            }
                                        }
                                    }
                                    System.out.println("Order Id : " + order.getOrder_id() + " : " + "Order Denied");
                                }
                            }

                        }
                    }
                    if (FoodOrder.RegularOrderList.size() > 0) {
                        for (FoodOrder order : FoodOrder.RegularOrderList) {
                            for (Food_Item fItem : order.getOrder_Cart().keySet()) {
                                if ((fItem.getName().equals(item_name)) && (order.getStatus() == OrderStatus.Pending
                                        || order.getStatus() == OrderStatus.OrderReceived)) {
                                    order.setStatus(OrderStatus.Denied);
                                    // update order history of user also
                                    for (Customer c : Customer.customer_list) {
                                        if (c.getName().equals(order.getC_name())) {

                                            try {
                                                StreamManagement.storeOrderHistory(c);
                                            } catch (IOException e) {
                                                // do nothing
                                            }
                                        }
                                    }
                                    System.out.println("Order Id : " + order.getOrder_id() + " : " + "Order Denied");

                                }
                            }
                        }
                    }
                    // admin must initiate refund for these orders if payment was made on priority
                    // basis.

                    // some orders may get denied so update the file containing pending orders
                    FileOperations.saveOrdersToFile();

                } else if (currItem == null) {
                    System.out.println("Item not found !");
                }
            } else if (input_menu == 4) {

            }

            // update menu file
            FileOperations.save_menu();

        } else if (input_adm_op == 2) {
            // Order related Operations
            System.out.println("1. View Pending Orders");
            System.out.println("2. Update Order Status");
            System.out.println("3. Process Refunds");
            System.out.println("4. Handle Special Requests");
            System.out.println("5. Go Back");

            System.out.print("Input: ");
            int input_order = Integer.parseInt(scan.nextLine());
            System.out.println();

            if (input_order == 1) {
                // view list of pending orders

                // vip orders given first priority
                System.out.println("VIP Orders :-");
                if (FoodOrder.VIPOrderList.size() > 0) {
                    for (FoodOrder order : FoodOrder.VIPOrderList) {
                        if (order.getStatus() == OrderStatus.Pending || order.getStatus() == OrderStatus.Preparing
                                || order.getStatus() == OrderStatus.OrderReceived) {
                            System.out.println("Order Id : " + order.getOrder_id());
                            for (Food_Item item : order.getOrder_Cart().keySet()) {
                                System.out.println(item.getName() + " : x" + order.getOrder_Cart().get(item));
                            }
                            System.out.println("Special Request : " + order.getSpecial_request() + " Status : "
                                    + order.getS_request());
                            System.out.println();

                        }

                    }
                }
                System.out.println();

                // regular orders
                System.out.println("Regular Orders :-");
                if (FoodOrder.RegularOrderList.size() > 0) {
                    for (FoodOrder order : FoodOrder.RegularOrderList) {
                        if (order.getStatus() == OrderStatus.Pending || order.getStatus() == OrderStatus.Preparing
                                || order.getStatus() == OrderStatus.OrderReceived) {
                            System.out.println(order.getOrder_id());
                            for (Food_Item item : order.getOrder_Cart().keySet()) {
                                System.out.println(item.getName() + " : x" + order.getOrder_Cart().get(item));
                            }
                            System.out.println("Special Request : " + order.getSpecial_request() + " Status : "
                                    + order.getS_request());
                            System.out.println();

                        }

                    }
                }
                System.out.println();

            } else if (input_order == 2) {
                // Timely update status of orders
                System.out.print("Enter Order id : ");
                int input_orderid = Integer.parseInt(scan.nextLine());
                FoodOrder current_order = null;
                if (FoodOrder.VIPOrderList.size() > 0) {
                    for (FoodOrder order : FoodOrder.VIPOrderList) {
                        if (order.getOrder_id() == input_orderid) {
                            current_order = order;
                        }

                    }
                }
                if (FoodOrder.RegularOrderList.size() > 0) {
                    for (FoodOrder order : FoodOrder.RegularOrderList) {
                        if (order.getOrder_id() == input_orderid) {
                            current_order = order;
                        }
                    }
                }

                if (current_order != null) {
                    System.out.println(current_order.getOrder_id() + " : " + current_order.getStatus());
                    System.out.println();

                    // only 1 among these 8 status can be set for an order
                    System.out.println("1. " + OrderStatus.Pending);
                    System.out.println("2. " + OrderStatus.OrderReceived);
                    System.out.println("3. " + OrderStatus.Preparing);
                    System.out.println("4. " + OrderStatus.OutForDelivery);
                    System.out.println("5. " + OrderStatus.Delivered);
                    System.out.println("6. " + OrderStatus.Completed);
                    System.out.println("7. " + OrderStatus.Denied);
                    System.out.println("8. " + OrderStatus.Cancelled);
                    System.out.print("Input : ");
                    int input_status = Integer.parseInt(scan.nextLine());

                    if (input_status == 1) {
                        current_order.setStatus(OrderStatus.Pending);
                    } else if (input_status == 2) {
                        current_order.setStatus(OrderStatus.OrderReceived);
                    } else if (input_status == 3) {
                        current_order.setStatus(OrderStatus.Preparing);
                    } else if (input_status == 4) {
                        current_order.setStatus(OrderStatus.OutForDelivery);
                    } else if (input_status == 5) {
                        current_order.setStatus(OrderStatus.Delivered);
                    } else if (input_status == 6) {
                        current_order.setStatus(OrderStatus.Completed);
                    } else if (input_status == 7) {
                        current_order.setStatus(OrderStatus.Denied);
                    } else if (input_status == 8) {
                        current_order.setStatus(OrderStatus.Cancelled);
                    }

                    // update order history of user also
                    for (Customer c : Customer.customer_list) {
                        if (c.getName().equals(current_order.getC_name())) {

                            try {
                                StreamManagement.storeOrderHistory(c);
                            } catch (IOException e) {
                                // do nothing
                            }
                        }
                    }

                } else if (current_order == null) {
                    System.out.println("Order not found !");
                }

                // update file of pending orders
                FileOperations.saveOrdersToFile();

            } else if (input_order == 3) {
                // Process refund for orders which are cancelled or denied
                System.out.print("Enter Order id : ");
                int input_orderid = Integer.parseInt(scan.nextLine());
                FoodOrder current_order = null;

                // get order based on order id
                if (FoodOrder.VIPOrderList.size() > 0) {
                    for (FoodOrder order : FoodOrder.VIPOrderList) {
                        if (order.getOrder_id() == input_orderid) {
                            current_order = order;
                        }

                    }
                }
                if (FoodOrder.RegularOrderList.size() > 0) {
                    for (FoodOrder order : FoodOrder.RegularOrderList) {
                        if (order.getOrder_id() == input_orderid) {
                            current_order = order;
                        }
                    }
                }

                if (current_order != null) {
                    // set refund_initiated to true if order was denied or cancelled
                    if (current_order.getStatus() == OrderStatus.Denied
                            || current_order.getStatus() == OrderStatus.Cancelled) {
                        if (current_order.isRefundInitiated() == false) {
                            System.out.println("Refund initiated !");
                            current_order.setRefundInitiated(true);
                            // update order history of user also
                            for (Customer c : Customer.customer_list) {
                                if (c.getName().equals(current_order.getC_name())) {

                                    try {
                                        StreamManagement.storeOrderHistory(c);
                                    } catch (IOException e) {
                                        // do nothing
                                    }
                                }
                            }
                        } else if (current_order.isRefundInitiated() == true) {
                            System.out.println("Refund already intitated !");
                        }
                    } else {
                        System.out.println("Can't initiate refund, Order under process");
                    }

                } else if (current_order == null) {
                    System.out.println("Order not found !");
                }

            } else if (input_order == 4) {
                // Accept or Reject special requests of the customer for orders
                System.out.print("Enter Order id : ");
                int input_orderid = Integer.parseInt(scan.nextLine());
                FoodOrder current_order = null;
                if (FoodOrder.VIPOrderList.size() > 0) {
                    for (FoodOrder order : FoodOrder.VIPOrderList) {
                        if (order.getOrder_id() == input_orderid) {
                            current_order = order;
                        }

                    }
                }
                if (FoodOrder.RegularOrderList.size() > 0) {
                    for (FoodOrder order : FoodOrder.RegularOrderList) {
                        if (order.getOrder_id() == input_orderid) {
                            current_order = order;
                        }
                    }
                }

                if (current_order != null) {
                    System.out.println(current_order.getOrder_id() + " : " + current_order.getSpecial_request() + " : "
                            + current_order.getS_request());
                    System.out.println();
                    System.out.println("1. " + "Accept"); // to accept the request
                    System.out.println("2. " + "Reject"); // to reject the request
                    System.out.print("Input : "); // input any other number if you don't want to change request status
                    int input_status = Integer.parseInt(scan.nextLine());
                    if (input_status == 1) {
                        current_order.setS_request(RequestStatus.Accepted);
                    } else if (input_status == 2) {
                        current_order.setS_request(RequestStatus.Rejected);
                    }
                    // update order history of user also
                    for (Customer c : Customer.customer_list) {
                        if (c.getName().equals(current_order.getC_name())) {

                            try {
                                StreamManagement.storeOrderHistory(c);
                            } catch (IOException e) {
                                // do nothing
                            }
                        }
                    }

                } else if (current_order == null) {
                    System.out.println("Order not found !");
                }

            } else if (input_order == 5) {

            }

        } else if (input_adm_op == 3) {
            // generate report for sales ,total orders and most popular item for each day
            // for Admin
            int total_sales = 0;
            String MostPopular = "";
            int max_quant = 0;
            int total_orders = 0;
            LocalDate today = LocalDate.now(); // generate report for only this day orders
            System.out.println("Order Details");
            if (FoodOrder.VIPOrderList.size() > 0) {
                for (FoodOrder order : FoodOrder.VIPOrderList) {
                    // excluding orders which were either denied or cancelled
                    if (order.getOrderDate().equals(today) && order.getStatus() != OrderStatus.Denied
                            && order.getStatus() != OrderStatus.Cancelled) {
                        System.out.println("Order Id : " + order.getOrder_id());
                        for (Food_Item fItem : order.getOrder_Cart().keySet()) {
                            int quantitiy = order.getOrder_Cart().get(fItem);
                            if (quantitiy > max_quant) {
                                MostPopular = fItem.getName()+" ";
                                max_quant = quantitiy;
                            } else if (quantitiy==max_quant) {
                                MostPopular=MostPopular + fItem.getName() + " ";
                            }
                            System.out.println(fItem.getName() + " : x" + quantitiy);
                        }
                        total_orders++;
                        total_sales += order.getTotal();
                        System.out.println();
                    }
                }
            }

            if (FoodOrder.RegularOrderList.size() > 0) {
                for (FoodOrder order : FoodOrder.RegularOrderList) {
                    // excluding orders which were either denied or cancelled
                    if (order.getOrderDate().equals(today) && order.getStatus() != OrderStatus.Denied
                            && order.getStatus() != OrderStatus.Cancelled) {
                        System.out.println("Order Id : " + order.getOrder_id());
                        for (Food_Item fItem : order.getOrder_Cart().keySet()) {
                            int quantitiy = order.getOrder_Cart().get(fItem);
                            if (quantitiy > max_quant) {
                                MostPopular = fItem.getName();
                                max_quant = quantitiy;
                            }
                            System.out.println(fItem.getName() + " : x" + quantitiy);
                        }
                        total_orders++;
                        total_sales += order.getTotal();
                        System.out.println();
                    }
                }
            }

            // display final summary
            System.out.println("Daily Summary (" + today + ")");
            System.out.println("Total Orders : " + total_orders);
            System.out.println("Most Popular Item : " + MostPopular);
            System.out.println("Total Sales : " + total_sales + " Rs");
            System.out.println();
        } else if (input_adm_op == 4) {
            return; // log out
        }

        this.beginOperations();
    }

}
