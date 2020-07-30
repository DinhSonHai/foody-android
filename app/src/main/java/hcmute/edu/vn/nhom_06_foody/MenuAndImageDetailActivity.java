package hcmute.edu.vn.nhom_06_foody;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class MenuAndImageDetailActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private Button btnBack;
    private ViewPager viewPager;
    private MenuViewPagerAdapter adapter;
    private TextView textViewName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_and_image_detail);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.statusbar));

        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        adapter = new MenuViewPagerAdapter(getSupportFragmentManager());
        btnBack = (Button) findViewById(R.id.buttonBackInMenuAndImage);
        textViewName = (TextView) findViewById(R.id.restaurantName);

        Intent intent = getIntent();
        int id = intent.getExtras().getInt("RestaurantID");
        String name = intent.getExtras().getString("RestaurantName");

        textViewName.setText(name);

        Bundle bundle = new Bundle();
        bundle.putInt("RestaurantID", id);

        //Add fragment here

        FragmentImage fragmentImage = new FragmentImage();
        fragmentImage.setArguments(bundle);

        FragmentMenu fragmentMenu = new FragmentMenu();
        fragmentMenu.setArguments(bundle);

        adapter.AddFragment(fragmentImage, "Ảnh");
        adapter.AddFragment(fragmentMenu, "Thực đơn");

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
