// enum for maintaining order status
public enum OrderStatus {
    Pending, OrderReceived, Preparing, OutForDelivery, Delivered, Completed, Denied, Cancelled;
}

// enum for maintaing status of special request on orders
enum RequestStatus {
    NONE, Pending, Accepted, Rejected;
}
