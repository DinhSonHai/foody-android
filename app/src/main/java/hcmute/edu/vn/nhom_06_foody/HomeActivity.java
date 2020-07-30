package hcmute.edu.vn.nhom_06_foody;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.nhom_06_foody.Model.ModelRestaurant;

public class HomeActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    private MyDbClass myDbClass;
    private List<ModelRestaurant> listRestaurant;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private EditText etSearch;
    private TextView tvProvince ;

    private int provinceID;
    private int REQUEST_CODE_PROVINCE = 123;
    private int startPosition;
    private boolean isLoading = false;

    public static Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.statusbar));

        currentLocation = new Location("Current");
        getCurrentLocation();
        etSearch = (EditText) findViewById(R.id.search);
        tvProvince = (TextView) findViewById(R.id.tvProvince);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_id);

        myDbClass = new MyDbClass(this);
        listRestaurant = new ArrayList<>();
        provinceID = 63;

        populateData();
        initAdapter();
        initScrollListener();

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId){
                    case EditorInfo
                            .IME_ACTION_DONE:
                        if(!TextUtils.isEmpty(etSearch.getText())){
                            Intent intent = new Intent(HomeActivity.this, SearchResultsActivity.class);
                            intent.putExtra("Name", etSearch.getText().toString());
                            intent.putExtra("ProvinceID", provinceID);
                            startActivityForResult(intent, REQUEST_CODE_PROVINCE);
                        }
                        break;
                }
                return false;
            }
        });

        tvProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProvinceActivity.class);
                intent.putExtra("ProvinceID", provinceID);
                startActivityForResult(intent, REQUEST_CODE_PROVINCE);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        populateData();
        initAdapter();
        initScrollListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_PROVINCE && resultCode == RESULT_OK && data != null){
            provinceID = data.getIntExtra("ProvinceID", 63);
            //Toast.makeText(HomeActivity.this, String.valueOf(provinceID), Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void populateData() {
        startPosition = 0;
        try{
            listRestaurant = new ArrayList<>();
            listRestaurant = myDbClass.getRestaurantStartData(provinceID, startPosition, 10);
        }
        catch(Exception ex){
            Toast.makeText(this, "showData:-" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if(listRestaurant != null){
            if(provinceID == 65){
                tvProvince.setText("Toàn quốc");
            }
            else{
                tvProvince.setText(listRestaurant.get(0).getProvince());
            }
            startPosition += listRestaurant.size();
        }
    }

    private void initAdapter() {
        if(listRestaurant != null){
            recyclerViewAdapter = new RecyclerViewAdapter(HomeActivity.this, listRestaurant);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if(listRestaurant != null){
                    if (!isLoading) {
                        if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == listRestaurant.size() - 1) {
                            //bottom of list!
                            loadMore();
                            isLoading = true;
                        }
                    }
                }
            }
        });
    }

    private void loadMore() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<ModelRestaurant> listNew = new ArrayList<>();
                listNew = myDbClass.getRestaurantStartData(provinceID, startPosition, 10);
                if(listNew != null){
                    for(int i = 0; i< listNew.size(); i++){
                        listRestaurant.add(listNew.get(i));
                    }
                    startPosition += listNew.size();
                }
                recyclerViewAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);
    }

    public void getCurrentLocation(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    (Activity) this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION
            );
            recreate();
        }
        else{
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(3000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationServices.getFusedLocationProviderClient(this)
                    .requestLocationUpdates(locationRequest, new LocationCallback() {

                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            super.onLocationResult(locationResult);
                            LocationServices.getFusedLocationProviderClient(HomeActivity.this)
                                    .removeLocationUpdates(this);
                            if (locationResult != null && locationResult.getLocations().size() > 0) {
                                int lastestLocationIndex = locationResult.getLocations().size() - 1;
                                double latitude = locationResult.getLocations().get(lastestLocationIndex).getLatitude();
                                double longtitude = locationResult.getLocations().get(lastestLocationIndex).getLongitude();
                                currentLocation.setLatitude(latitude);
                                currentLocation.setLongitude(longtitude);
                                //Toast.makeText(HomeActivity.this, String.valueOf(currentLocation.getLatitude()), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, Looper.getMainLooper());
        }
    }
}
