package hcmute.edu.vn.nhom_06_foody;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.nhom_06_foody.Model.DbModelImage;
import hcmute.edu.vn.nhom_06_foody.Model.DbModelProduct;
import hcmute.edu.vn.nhom_06_foody.Model.DbModelProduct_Category;
import hcmute.edu.vn.nhom_06_foody.Model.DbModelProvince;
import hcmute.edu.vn.nhom_06_foody.Model.DbModelRestaurant;
import hcmute.edu.vn.nhom_06_foody.Model.DbModelWifi;
import hcmute.edu.vn.nhom_06_foody.Model.ModelRestaurant;

public class MyDbClass extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "Nhom_06_Foody_Database.db";
    private static final int DATABASE_VERSION = 1;

    private Context context;

    public MyDbClass(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public ArrayList<DbModelProvince> getProvinceData(){
        try{
            ArrayList<DbModelProvince> listProvince = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            if (sqLiteDatabase != null){
                Cursor cursor = sqLiteDatabase.rawQuery("select * from province", null);
                if(cursor.getCount() != 0){
                    while (cursor.moveToNext()){
                        int id = cursor.getInt(0);
                        String name = cursor.getString(1);
                        listProvince.add(
                                new DbModelProvince(id, name)
                        );
                    }
                    return listProvince;
                }
                else{
                    //Toast.makeText(context, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
            else{
                Toast.makeText(context, "Dữ liệu rỗng", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        catch(Exception ex){
            Toast.makeText(context, "getAllData:-" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public ArrayList<DbModelProvince> getProvinceDataByKeyword(String keyword){
        try{
            ArrayList<DbModelProvince> listProvince = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            if (sqLiteDatabase != null){
                Cursor cursor = sqLiteDatabase.rawQuery("select * from province where name like '%" + keyword + "%' COLLATE [Vietnamese_CI_AI]", null);
                if(cursor.getCount() != 0){
                    while (cursor.moveToNext()){
                        int id = cursor.getInt(0);
                        String name = cursor.getString(1);
                        listProvince.add(
                                new DbModelProvince(id, name)
                        );
                    }
                    return listProvince;
                }
                else{
                    //Toast.makeText(context, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
            else{
                Toast.makeText(context, "Dữ liệu rỗng", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        catch(Exception ex){
            Toast.makeText(context, "getAllData:-" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public ArrayList<ModelRestaurant> getRestaurantStartData(int _id, int startPosition, int amount){
        Cursor cursor;
        try{
            ArrayList<ModelRestaurant> listRestaurant = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            if (sqLiteDatabase != null){
                if(_id == 65){
                    cursor = sqLiteDatabase.rawQuery("select * from restaurant, located, province, restaurant_category where restaurant.id = located.restaurant_id and located.province_id = province.id and restaurant.category_id = restaurant_category.id limit " + startPosition + ", " + amount + "", null);
                }
                else{
                    cursor = sqLiteDatabase.rawQuery("select * from restaurant, located, province, restaurant_category where restaurant.id = located.restaurant_id and located.province_id = province.id and restaurant.category_id = restaurant_category.id and province.id = " + _id + " limit " + startPosition + ", " + amount + "", null);
                }
                if(cursor.getCount() != 0){
                    while (cursor.moveToNext()){
                        int id = cursor.getInt(0);
                        String name = cursor.getString(1);
                        String description = cursor.getString(2);
                        String address = cursor.getString(3);
                        String avatar = cursor.getString(8);
                        String province = cursor.getString(14);
                        String category = cursor.getString(16);
                        double latitude = cursor.getDouble(9);
                        double longtitude = cursor.getDouble(10);
                        listRestaurant.add(
                                new ModelRestaurant(id, name, description, address, avatar, province, category, latitude, longtitude)
                        );
                    }
                    return listRestaurant;
                }
                else{
                    //Toast.makeText(context, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
            else{
                Toast.makeText(context, "Dữ liệu rỗng", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        catch(Exception ex){
            Toast.makeText(context, "getAllData:-" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public ArrayList<ModelRestaurant> getSearchLazyData(String keyword, int provinceID, int startPosition, int amount){
        Cursor cursor;
        try{
            ArrayList<ModelRestaurant> listRestaurant = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            if (sqLiteDatabase != null){
                if(provinceID == 65){
                    cursor = sqLiteDatabase.rawQuery("select * from restaurant, located, province, restaurant_category where restaurant.id = located.restaurant_id and located.province_id = province.id and restaurant.category_id = restaurant_category.id and restaurant.name like '%" + keyword + "%' COLLATE [Vietnamese_CI_AI] limit " + startPosition + ", " + amount + "", null);
                }
                else{
                    cursor = sqLiteDatabase.rawQuery("select * from restaurant, located, province, restaurant_category where restaurant.id = located.restaurant_id and located.province_id = province.id and restaurant.category_id = restaurant_category.id and located.province_id = " + provinceID + " and restaurant.name like '%" + keyword + "%' COLLATE [Vietnamese_CI_AI] limit " + startPosition + ", " + amount + "", null);
                }
                if(cursor.getCount() != 0){
                    while (cursor.moveToNext()){
                        int id = cursor.getInt(0);
                        String name = cursor.getString(1);
                        String description = cursor.getString(2);
                        String address = cursor.getString(3);
                        String avatar = cursor.getString(8);
                        String province = cursor.getString(14);
                        String category = cursor.getString(16);
                        double latitude = cursor.getDouble(9);
                        double longtitude = cursor.getDouble(10);
                        listRestaurant.add(
                                new ModelRestaurant(id, name, description, address, avatar, province, category, latitude, longtitude)
                        );
                    }
                    return listRestaurant;
                }
                else{
                    //Toast.makeText(context, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
            else{
                Toast.makeText(context, "Dữ liệu rỗng", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        catch(Exception ex){
            Toast.makeText(context, "getAllData:-" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public DbModelRestaurant getRestaurantByID(int _id){
        DbModelRestaurant restaurant = new DbModelRestaurant();
        try{
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            if (sqLiteDatabase != null){
                Cursor cursor = sqLiteDatabase.rawQuery("select * from restaurant where id = " + _id + "", null);
                if(cursor.getCount() != 0){
                    while (cursor.moveToNext()){
                        int id = cursor.getInt(0);
                        String name = cursor.getString(1);
                        String description = cursor.getString(2);
                        String address = cursor.getString(3);
                        String phone_number = cursor.getString(4);
                        String open_time = cursor.getString(5);
                        String close_time = cursor.getString(6);
                        int category_id = cursor.getInt(7);
                        String avatar = cursor.getString(8);
                        double latitude = cursor.getDouble(9);
                        double longtitude = cursor.getDouble(10);
                        restaurant = new DbModelRestaurant(id, name, description, address, phone_number, open_time, close_time, category_id, avatar, latitude, longtitude);
                    }
                    return restaurant;
                }
                else{
                    //Toast.makeText(context, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
            else{
                Toast.makeText(context, "Dữ liệu rỗng", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        catch(Exception ex){
            Toast.makeText(context, "getAllData:-" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public ArrayList<DbModelProduct> getProductByRestaurantID(int _id){
        try{
            ArrayList<DbModelProduct> listProduct = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            if (sqLiteDatabase != null){
                Cursor cursor = sqLiteDatabase.rawQuery("select * from product, product_category where product.category_id = product_category.id and product_category.restaurant_id = " + _id + "", null);
                if(cursor.getCount() != 0){
                    while (cursor.moveToNext()){
                        int id = cursor.getInt(0);
                        String name = cursor.getString(1);
                        String image = cursor.getString(2);
                        String description = cursor.getString(3);
                        int price = cursor.getInt(4);
                        int category_id = cursor.getInt(5);
                        listProduct.add(
                                new DbModelProduct(id, name, image, description, price, category_id)
                        );
                    }
                    return listProduct;
                }
                else{
                    //Toast.makeText(context, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
            else{
                Toast.makeText(context, "Dữ liệu rỗng", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        catch(Exception ex){
            Toast.makeText(context, "getAllData:-" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public ArrayList<DbModelImage> getAllRestaurantImage(int _id){
        try{
            ArrayList<DbModelImage> listImage = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            if (sqLiteDatabase != null){
                Cursor cursor = sqLiteDatabase.rawQuery("select * from image, restaurant where image.restaurant_id = restaurant.id and image.restaurant_id = " + _id + "", null);
                if(cursor.getCount() != 0){
                    while (cursor.moveToNext()){
                        int id = cursor.getInt(0);
                        String link = cursor.getString(1);
                        int restaurant_id = cursor.getInt(2);;
                        listImage.add(
                                new DbModelImage(id, link, restaurant_id)
                        );
                    }
                    return listImage;
                }
                else{
                    //Toast.makeText(context, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
            else{
                Toast.makeText(context, "Dữ liệu rỗng", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        catch(Exception ex){
            Toast.makeText(context, "getAllData:-" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public ArrayList<DbModelProduct_Category> getProductCategoryOfRestaurant(int _id){
        try{
            ArrayList<DbModelProduct_Category> listCategory = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            if (sqLiteDatabase != null){
                Cursor cursor = sqLiteDatabase.rawQuery("select * from product_category where restaurant_id = " + _id + "", null);
                if(cursor.getCount() != 0){
                    while (cursor.moveToNext()){
                        int id = cursor.getInt(0);
                        String name = cursor.getString(1);
                        int restaurant_id = cursor.getInt(2);
                        listCategory.add(
                                new DbModelProduct_Category(id, name, restaurant_id)
                        );
                    }
                    return listCategory;
                }
                else{
                    //Toast.makeText(context, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
            else{
                Toast.makeText(context, "Dữ liệu rỗng", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        catch(Exception ex){
            Toast.makeText(context, "getAllData:-" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public ArrayList<DbModelProduct> getProductByCategory(int _id){
        try{
            ArrayList<DbModelProduct> listProduct = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            if (sqLiteDatabase != null){
                Cursor cursor = sqLiteDatabase.rawQuery("select * from product where category_id = " + _id + "", null);
                if(cursor.getCount() != 0){
                    while (cursor.moveToNext()){
                        int id = cursor.getInt(0);
                        String name = cursor.getString(1);
                        String image = cursor.getString(2);
                        String description = cursor.getString(3);
                        int price = cursor.getInt(4);
                        int category_id = cursor.getInt(5);
                        listProduct.add(
                                new DbModelProduct(id, name, image, description, price, category_id)
                        );
                    }
                    return listProduct;
                }
                else{
                    //Toast.makeText(context, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
            else{
                Toast.makeText(context, "Dữ liệu rỗng", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        catch(Exception ex){
            Toast.makeText(context, "getAllData:-" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public List<DbModelWifi> GetWifi(int _id){
        try{
            ArrayList<DbModelWifi> listWifi = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            if (sqLiteDatabase != null){
                Cursor cursor = sqLiteDatabase.rawQuery("select * from wifi where restaurant_id = " + _id + "", null);
                if(cursor.getCount() != 0){
                    while (cursor.moveToNext()){
                        int id = cursor.getInt(0);
                        String name = cursor.getString(1);
                        String password = cursor.getString(2);
                        int restaurant_id = cursor.getInt(3);
                        listWifi.add(
                                new DbModelWifi(id, name, password, restaurant_id)
                        );
                    }
                    return listWifi;
                }
                else{
                    //Toast.makeText(context, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
            else{
                Toast.makeText(context, "Dữ liệu rỗng", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        catch(Exception ex){
            Toast.makeText(context, "getAllData:-" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public void AddWifi(int restaurantID, String name, String password){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.putNull("id");
        contentValues.put("name", name);
        contentValues.put("password", password);
        contentValues.put("restaurant_id", restaurantID);

        long result = sqLiteDatabase.insert("wifi", null, contentValues);
    }

}
