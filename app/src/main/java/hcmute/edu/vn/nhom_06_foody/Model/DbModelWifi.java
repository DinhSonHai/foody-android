package hcmute.edu.vn.nhom_06_foody.Model;

public class DbModelWifi {

    private int id;
    private String name;
    private String password;
    private int restaurant_id;

    public DbModelWifi(){

    }

    public DbModelWifi(int id, String name, String password, int restaurant_id) {
        this.id = id;
        this.name = name;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }
}
