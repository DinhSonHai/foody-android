package hcmute.edu.vn.nhom_06_foody.Model;

public class DbModelProduct_Category {

    private int id;
    private String name;
    private int restaurant_id;

    public DbModelProduct_Category(int id, String name, int restaurant_id) {
        this.id = id;
        this.name = name;
        this.restaurant_id = restaurant_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }
}
