package hcmute.edu.vn.nhom_06_foody.Model;

public class DbModelImage {
    private int id;
    private String link;
    private int restaurant_id;

    public DbModelImage(int id, String link, int restaurant_id) {
        this.id = id;
        this.link = link;
        this.restaurant_id = restaurant_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }
}
