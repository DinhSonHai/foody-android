package hcmute.edu.vn.nhom_06_foody.Model;

public class DbModelRestaurant {

    private int id;
    private String name;
    private String description;
    private String address;
    private String phone_number;
    private String open_time;
    private String close_time;
    private int category_id;
    private String avatar;
    private double latitude;
    private double longtitude;

    public DbModelRestaurant() {
    }

    public DbModelRestaurant(int id, String name, String description, String address, String phone_number, String open_time, String close_time, int category_id, String avatar, double latitude, double longtitude) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.phone_number = phone_number;
        this.open_time = open_time;
        this.close_time = close_time;
        this.category_id = category_id;
        this.avatar = avatar;
        this.latitude = latitude;
        this.longtitude = longtitude;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getOpen_time() {
        return open_time;
    }

    public void setOpen_time(String open_time) {
        this.open_time = open_time;
    }

    public String getClose_time() {
        return close_time;
    }

    public void setClose_time(String close_time) {
        this.close_time = close_time;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }
}
