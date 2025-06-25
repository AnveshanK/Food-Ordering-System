
import java.util.ArrayList;

// class for each food item
public class Food_Item {

    // attributes for each item
    private String Name;
    private int price;
    private String Category;
    private boolean Available; 
    private String Availability; // taking availability as string as any item may be Available or Not Available
                                 // or only specific few quantities may be left
    private ArrayList<String> Review;

    public Food_Item() {
        // inititalising array list for Review
        Review = new ArrayList<>();
    }

    // getters and setters for food items
    public ArrayList<String> getReview() {
        return Review;
    }

    public boolean getAvailable() {
        return Available;
    }

    public void setAvailable(boolean available) {
        this.Available=available;
    }

    public String getAvailability() {
        return Availability;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setAvailability(String availability) {
        Availability = availability;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    

    public void addReview(String review, String cus_name) {
        this.Review.add(cus_name + " : " + review);
    }

}
