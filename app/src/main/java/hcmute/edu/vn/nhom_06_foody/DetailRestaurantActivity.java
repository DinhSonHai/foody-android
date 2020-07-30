package hcmute.edu.vn.nhom_06_foody;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import hcmute.edu.vn.nhom_06_foody.Model.DbModelProduct;
import hcmute.edu.vn.nhom_06_foody.Model.DbModelRestaurant;
import hcmute.edu.vn.nhom_06_foody.Model.DbModelWifi;

public class DetailRestaurantActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private MyDbClass myDbClass;

    private ImageView imageViewMenu;
    private ImageView imageViewBack;
    private TextView textViewAddWifi;
    private TextView textViewProvince;
    private TextView textViewName;
    private TextView textViewMenu;
    private TextView textViewTime;
    private TextView textViewAddress;
    private TextView textViewDistance;
    private TextView textViewCategory;
    private TextView textViewPrice;
    private TextView textViewOpenStatus;
    private TextView textViewWifi;
    private Button buttonContact;
    private GoogleMap map;

    private int REQUEST_CODE_WIFI = 123;
    int id;
    private String name = "";
    private Date currentTime;
    private Date openTime;
    private Date closeTime;

    private DbModelRestaurant restaurant;
    private List<DbModelProduct> listProduct;
    private List<DbModelWifi> listWifi;
    private ArrayList<Integer> listPrice;

    private SimpleDateFormat inputParser = new SimpleDateFormat("HH:mm", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_restaurant);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.statusbar));

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String formattedDate = df.format(c.getTime());

        imageViewMenu = (ImageView)findViewById(R.id.imgMenu);
        imageViewBack = (ImageView) findViewById(R.id.back);
        textViewAddWifi = (TextView)findViewById(R.id.txtAddWifi);
        textViewProvince = (TextView) findViewById(R.id.txtProvinceRestaurant);
        textViewName = (TextView) findViewById(R.id.txtNameRestaurant);
        textViewMenu = (TextView) findViewById(R.id.textThucDon);
        textViewTime = (TextView) findViewById(R.id.time);
        textViewAddress = (TextView) findViewById(R.id.address);
        textViewDistance = (TextView) findViewById(R.id.distance);
        textViewCategory = (TextView) findViewById(R.id.category);
        textViewPrice = (TextView) findViewById(R.id.price);
        textViewOpenStatus = (TextView) findViewById(R.id.txtOpenStatus);
        textViewWifi = (TextView) findViewById(R.id.wifi);
        buttonContact = (Button) findViewById(R.id.contact);

        Intent intent = getIntent();
        id = intent.getExtras().getInt("RestaurantID");
        String province = intent.getExtras().getString("Province");
        String distance = intent.getExtras().getString("Distance");
        String category = intent.getExtras().getString("Category");

        textViewProvince.setText(province);

        if(ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    DetailRestaurantActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION
            );
        } else{
//            getCurrentLocation();
            MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.myMap);
            mapFragment.getMapAsync(this);
        }

        myDbClass = new MyDbClass(this);
        restaurant = new DbModelRestaurant();
        listProduct = new ArrayList<>();
        listWifi = new ArrayList<>();
        listPrice = new ArrayList<>();

        try{
            restaurant = myDbClass.getRestaurantByID(id);
            name = restaurant.getName();
            textViewName.setText(restaurant.getName());

            currentTime = parseDate(formattedDate);
            openTime = parseDate(restaurant.getOpen_time());
            closeTime = parseDate(restaurant.getClose_time());

            if(openTime.before(currentTime) && closeTime.after(currentTime)){
                textViewOpenStatus.setTextColor(Color.GREEN);
                textViewOpenStatus.setText("Đang mở cửa");
            }
            else{
                textViewOpenStatus.setTextColor(Color.RED);
                textViewOpenStatus.setText("Chưa mở cửa");
            }

            textViewTime.setText(restaurant.getOpen_time() + " - " + restaurant.getClose_time());
            textViewAddress.setText(restaurant.getAddress());
            //textViewDistance.setText(distance);
            textViewCategory.setText(category);
            textViewPrice.setText("10,000đ - 160,000đ");
            LatLng restaurantLatLng = new LatLng(restaurant.getLatitude(), restaurant.getLongtitude());
            Location restaurantLocation = new Location("Restaurant");
            restaurantLocation.setLatitude(restaurantLatLng.latitude);
            restaurantLocation.setLongitude(restaurantLatLng.longitude);

            if(HomeActivity.currentLocation != null){
                textViewDistance.setText(Math.round(HomeActivity.currentLocation.distanceTo(restaurantLocation)/1000) + " km (Từ vị trí hiện tại)");
            }
        }
        catch(Exception ex){
            Toast.makeText(this, "showData:-" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        try{
            listProduct = myDbClass.getProductByRestaurantID(id);
        }
        catch(Exception ex){
            Toast.makeText(this, "showData:-" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if(listProduct != null){
            for(int i = 0; i < listProduct.size(); i++){
                listPrice.add(listProduct.get(i).getPrice());
            }

            textViewPrice.setText(Collections.min(listPrice) + ",000đ - " + Collections.max(listPrice) + ",000đ");

            RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
            ImageDetailRestaurantAdapter myAdapter = new ImageDetailRestaurantAdapter(this, listProduct);
            myrv.setLayoutManager(new GridLayoutManager(this,2));
            myrv.setAdapter(myAdapter);
        }

        try{
            listWifi = myDbClass.GetWifi(id);
        }
        catch(Exception ex){
            Toast.makeText(this, "showData:-" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        if(listWifi != null){
            textViewAddWifi.setText(listWifi.get(0).getName());
            textViewWifi.setText(listWifi.get(0).getPassword());
        }
        else{
            textViewAddWifi.setText("Thêm wifi");
            textViewWifi.setText("Nhập mật khẩu");
        }

        textViewAddWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailRestaurantActivity.this, WifiActivity.class);
                intent.putExtra("RestaurantID", restaurant.getId());
                if(listWifi != null){
                    intent.putExtra("Name", listWifi.get(0).getName());
                    intent.putExtra("Password", listWifi.get(0).getPassword());
                }
                startActivityForResult(intent, REQUEST_CODE_WIFI);
            }
        });

        imageViewMenu.setOnClickListener(this);
        textViewMenu.setOnClickListener(this);

//        imageViewMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DetailRestaurantActivity.this, MenuAndImageDetailActivity.class);
//                intent.putExtra("RestaurantID", id);
//                intent.putExtra("RestaurantName", name);
//                startActivity(intent);
//            }
//        });

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!restaurant.getPhone_number().isEmpty()){
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + restaurant.getPhone_number()));
                    startActivity(intent);
                }
                else{
                    Toast.makeText(DetailRestaurantActivity.this, "Chưa có thông tin liên hệ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(DetailRestaurantActivity.this, MenuAndImageDetailActivity.class);
        intent.putExtra("RestaurantID", id);
        intent.putExtra("RestaurantName", name);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_CODE_WIFI && resultCode == RESULT_OK && data != null) {
            String name = data.getStringExtra("name");
            String password = data.getStringExtra("password");

            myDbClass.AddWifi(restaurant.getId(), name, password);

            try{
                listWifi = myDbClass.GetWifi(restaurant.getId());
            }
            catch(Exception ex){
                Toast.makeText(this, "showData:-" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

            if(listWifi != null){
                textViewAddWifi.setText(listWifi.get(0).getName());
                textViewWifi.setText(listWifi.get(0).getPassword());
            }
            else{
                textViewAddWifi.setText("Thêm wifi");
                textViewWifi.setText("Nhập mật khẩu");
            }
            recreate();

        } else {
            Toast.makeText(this, "Bạn chưa cập nhật Wifi", Toast.LENGTH_SHORT).show();

        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //getCurrentLocation();
                recreate();
            }
            else{
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng latLng = new LatLng(restaurant.getLatitude(), restaurant.getLongtitude());
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        map.addMarker(new MarkerOptions()
                .title(restaurant.getName())
                .snippet("")
                .position(latLng));
    }

    private Date parseDate(String date) {
        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }
}
