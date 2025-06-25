import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class GUI_Menu_Item {
    public static ArrayList<GUI_Menu_Item> GUI_Menu = new ArrayList<>();

    private final SimpleStringProperty item_name;
    private final SimpleStringProperty category;
    private final SimpleIntegerProperty price;
    private final SimpleStringProperty availability;

    public GUI_Menu_Item(String item_name, String category, int price, String availability) {
        this.item_name = new SimpleStringProperty(item_name);
        this.category = new SimpleStringProperty(category);
        this.price = new SimpleIntegerProperty(price);
        this.availability = new SimpleStringProperty(availability);
    }

    // Getters
    public String getItem_name() {
        return item_name.get();
    }

    public String getCategory() {
        return category.get();
    }

    public int getPrice() {
        return price.get();
    }

    public String getAvailability() {
        return availability.get();
    }

    // Properties (for JavaFX bindings)
    public SimpleStringProperty item_nameProperty() {
        return item_name;
    }

    public SimpleStringProperty categoryProperty() {
        return category;
    }

    public SimpleIntegerProperty priceProperty() {
        return price;
    }

    public SimpleStringProperty availabilityProperty() {
        return availability;
    }

    // Populate an ObservableList from the static ArrayList
    public static ObservableList<GUI_Menu_Item> getList() {
        return FXCollections.observableArrayList(GUI_Menu);
    }
}
