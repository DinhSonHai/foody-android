package hcmute.edu.vn.nhom_06_foody.Model;

public class ModelRestaurant {

    private int id;
    private String name;
    private String description;
    private String address;
    private String avatar;
    private String province;
    private String category;
    private double latitude;
    private double longtitude;

    public ModelRestaurant(int id, String name, String description, String address, String avatar, String province, String category, double latitude, double longtitude) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.avatar = avatar;
        this.province = province;
        this.category = category;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
