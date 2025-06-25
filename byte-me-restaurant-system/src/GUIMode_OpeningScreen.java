import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

@SuppressWarnings("unused")
public class GUIMode_OpeningScreen extends Application {
    private Stage window;
    Button Close_button;

    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        // Set a fixed position for the stage
        primaryStage.setX(600); // Fixed horizontal position
        primaryStage.setY(300); // Fixed vertical position
        // primaryStage.
        // primaryStage.centerOnScreen();

        primaryStage.setTitle("ByteMe");
        primaryStage.setHeight(600);
        primaryStage.setWidth(1000);

        // opening screen layout setup
        Button menu_button = new Button();
        Button Orders_button = new Button();
        Close_button = new Button();

        menu_button.setText("Browse Food Menu");
        Orders_button.setText("View Pending Orders");
        Close_button.setText("Close Program");

        Close_button.setOnAction(e -> closeProgram());

        // Create an HBox layout
        HBox h_layout1 = new HBox(15); // 10px spacing between buttons
        h_layout1.setAlignment(Pos.CENTER);
        h_layout1.getChildren().addAll(menu_button, Orders_button);

        // Use VBox to arrange elements vertically
        VBox v_layout1 = new VBox(20); // 20px spacing between elements
        v_layout1.setAlignment(Pos.CENTER);

        // Create a label with a message
        Label messageLabel1 = new Label("Hello user, welcome to the IIIT D food ordering system!");
        messageLabel1.setStyle("-fx-font-size: 15px;" + "-fx-text-fill: #333333; " + "-fx-background-color: #f0f0f0; "
                + "-fx-padding: 10px; " + "-fx-border-color: #cccccc; " + "-fx-border-radius: 5px; "
                + "-fx-background-radius: 5px;");

        v_layout1.getChildren().addAll(messageLabel1, h_layout1);

        BorderPane openingScreenLayout = new BorderPane();
        openingScreenLayout.setCenter(v_layout1); // Center content
        openingScreenLayout.setBottom(Close_button); // Place close button at the bottom
        BorderPane.setAlignment(Close_button, Pos.BOTTOM_RIGHT); // Align close button to bottom-right
        BorderPane.setMargin(Close_button, new javafx.geometry.Insets(10)); // Add margin around close button

        Scene scene1 = new Scene(openingScreenLayout);

        // scene2-Browse Menu
        VBox v_layout2 = new VBox(10);
        Button back_menu = new Button();
        back_menu.setText("Back");
        back_menu.setOnAction(e -> primaryStage.setScene(scene1));

        BorderPane menuScreenLayout = new BorderPane();
        Label menuLabel = new Label("{ByteMe Menu}");
        menuLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        menuLabel.setAlignment(Pos.TOP_CENTER);
        menuScreenLayout.setTop(menuLabel); // Add label to top
        BorderPane.setAlignment(menuLabel, Pos.TOP_CENTER);

        menuScreenLayout.setBottom(back_menu);
        BorderPane.setAlignment(back_menu, Pos.BOTTOM_LEFT);
        BorderPane.setMargin(back_menu, new javafx.geometry.Insets(10)); // Add margin around back button

        // menu table of 4 columns
        TableView<GUI_Menu_Item> Menu_table = new TableView<>();

        // Create columns using the reusable method
        TableColumn<GUI_Menu_Item, String> nameColumn = createColumn("Name", "item_name", 249);
        TableColumn<GUI_Menu_Item, Integer> priceColumn = createColumn("Price (in Rupees)", "price", 249);
        TableColumn<GUI_Menu_Item, String> categoryColumn = createColumn("Category", "Category", 249);
        TableColumn<GUI_Menu_Item, String> availabilityColumn = createColumn("Availability", "Availability", 249);

        priceColumn.setSortable(true);
        nameColumn.setSortable(false);
        categoryColumn.setSortable(false);
        availabilityColumn.setSortable(false);

        Menu_table.setItems(GUI_Menu_Item.getList());
        Menu_table.getColumns().addAll(nameColumn, priceColumn, categoryColumn, availabilityColumn);

        // add table to the layout
        v_layout2.getChildren().add(Menu_table);
        menuScreenLayout.setCenter(v_layout2);

        Scene scene_2 = new Scene(menuScreenLayout);
        menu_button.setOnAction(e -> primaryStage.setScene(scene_2));

        // scene3-Pending Orders
        VBox v_layout3 = new VBox(10);
        Button back_orders = new Button();
        back_orders.setText("Back");
        back_orders.setOnAction(e -> primaryStage.setScene(scene1));

        BorderPane OrdersScreenLayout = new BorderPane();
        Label OrderLabel = new Label("{Pending Orders}");
        OrderLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;"); // Optional styling
        OrderLabel.setAlignment(Pos.TOP_CENTER); // Center alignment
        OrdersScreenLayout.setTop(OrderLabel); // Add label to top
        BorderPane.setAlignment(OrderLabel, Pos.CENTER); // Center it in the top region

        OrdersScreenLayout.setBottom(back_orders);
        BorderPane.setAlignment(back_orders, Pos.BOTTOM_LEFT);
        BorderPane.setMargin(back_orders, new javafx.geometry.Insets(10)); // Add margin around back button

        TableView<GUIOrder> Orders_table = new TableView<>();

        TableColumn<GUIOrder, Integer> idColumn = createCenteredColumn("Order Id", "order_id", 100);
        TableColumn<GUIOrder, Integer> totalColumn = createCenteredColumn("Order Total (in Rupees)", "order_total", 200);
        TableColumn<GUIOrder, String> itemsColumn = createCenteredColumn("Items", "order_items", 598);
        TableColumn<GUIOrder, OrderStatus> statusColumn = createCenteredColumn("Order Status", "order_Status", 100);

        idColumn.setSortable(false);
        totalColumn.setSortable(false);
        itemsColumn.setSortable(false);
        statusColumn.setSortable(false);

        Orders_table.setItems(GUIOrder.getList());
        Orders_table.getColumns().addAll(idColumn, totalColumn, itemsColumn, statusColumn);

        // add table to the layout
        v_layout3.getChildren().add(Orders_table);
        OrdersScreenLayout.setCenter(v_layout3);

        Scene scene_3 = new Scene(OrdersScreenLayout);
        Orders_button.setOnAction(e -> primaryStage.setScene(scene_3));

        primaryStage.setScene(scene1);
        // primaryStage.setFullScreen(true);
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    // This method is invoked when the "close" button is pressed.
    private void closeProgram() {
        window.close(); // Close the GUI window
    }

    // function to add column to menu table
    private <T> TableColumn<GUI_Menu_Item, T> createColumn(String title, String property, int minWidth) {
        TableColumn<GUI_Menu_Item, T> column = new TableColumn<>(title);
        column.setMinWidth(minWidth);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        column.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                    setAlignment(Pos.CENTER); // Center alignment
                }
            }
        });
        return column;
    }

    private <T, V> TableColumn<T, V> createCenteredColumn(String title, String property, double width) {
        TableColumn<T, V> column = new TableColumn<>(title);
        column.setMinWidth(width);
        column.setCellValueFactory(new PropertyValueFactory<>(property));

        // Center-align cell content
        column.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(V item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                    setAlignment(Pos.CENTER); // Center alignment
                }
            }
        });
        return column;
    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}
