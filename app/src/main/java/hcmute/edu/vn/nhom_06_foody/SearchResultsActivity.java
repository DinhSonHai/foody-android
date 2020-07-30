package hcmute.edu.vn.nhom_06_foody;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ImageView imgViewBack;
    private EditText etSearch;
    private TextView tvCancel;
    public static TextView tvProvince ;
    private ViewPager viewPager;
    private MenuViewPagerAdapter adapter;
    private Bundle bundle;

    private String keyword = "";
    private int provinceID;
    private int REQUEST_CODE_PROVINCE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        imgViewBack = (ImageView) findViewById(R.id.imgViewBack);
        etSearch = (EditText) findViewById(R.id.search);
        tvCancel = (TextView) findViewById(R.id.cancel);
        tvProvince = (TextView) findViewById(R.id.tvProvince);
        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        adapter = new MenuViewPagerAdapter(getSupportFragmentManager());

        Intent intent = getIntent();
        keyword = intent.getExtras().getString("Name");
        provinceID = intent.getExtras().getInt("ProvinceID");

        etSearch.setText(keyword);
        etSearch.setSelection(etSearch.getText().length());

        bundle = new Bundle();
        bundle.putString("Keyword", keyword);
        bundle.putInt("ProvinceID", provinceID);

        //Add fragment here

        FragmentMostRight fragmentMostRight = new FragmentMostRight();
        fragmentMostRight.setArguments(bundle);

        FragmentNearBy fragmentNearBy = new FragmentNearBy();
        fragmentNearBy.setArguments(bundle);

        adapter.AddFragment(fragmentMostRight, "Đúng nhất");
        adapter.AddFragment(fragmentNearBy, "Gần tôi");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        //etSearch.clearFocus();

        imgViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("ProvinceID", provinceID);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId){
                    case EditorInfo
                            .IME_ACTION_DONE:
                        //Toast.makeText(SearchResultsActivity.this, String.valueOf(bundle.size()), Toast.LENGTH_SHORT).show();
                        bundle.clear();
                        bundle.putString("Keyword", etSearch.getText().toString());
                        bundle.putInt("ProvinceID", provinceID);

                        //Add fragment here

                        FragmentMostRight fragmentMostRight = new FragmentMostRight();
                        fragmentMostRight.setArguments(bundle);

                        FragmentNearBy fragmentNearBy = new FragmentNearBy();
                        fragmentNearBy.setArguments(bundle);

                        adapter = new MenuViewPagerAdapter(getSupportFragmentManager());

                        adapter.AddFragment(fragmentMostRight, "Đúng nhất");
                        adapter.AddFragment(fragmentNearBy, "Gần tôi");

                        viewPager.setAdapter(adapter);
                        tabLayout.setupWithViewPager(viewPager);
                        break;
                }
                return false;
            }
        });

        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Resources r = getResources();
                    tvCancel.setVisibility(View.VISIBLE);
                    tvProvince.setVisibility(View.GONE);
                    ViewGroup.LayoutParams layoutParams = etSearch.getLayoutParams();
                    layoutParams.width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 280, r.getDisplayMetrics());
                    etSearch.setLayoutParams(layoutParams);
                }
                else{
                    hideKeyboard(v);
                    tvCancel.setVisibility(View.GONE);
                    tvProvince.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams layoutParams = etSearch.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    etSearch.setLayoutParams(layoutParams);
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.clearFocus();
            }
        });

        tvProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchResultsActivity.this, ProvinceActivity.class);
                intent.putExtra("ProvinceID", provinceID);
                startActivityForResult(intent, REQUEST_CODE_PROVINCE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_PROVINCE && resultCode == RESULT_OK && data != null){
            provinceID = data.getIntExtra("ProvinceID", 63);
            Toast.makeText(SearchResultsActivity.this, String.valueOf(provinceID), Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onRestart() {
        //Toast.makeText(SearchResultsActivity.this, String.valueOf(bundle.size()), Toast.LENGTH_SHORT).show();
        bundle.clear();
        bundle.putString("Keyword", etSearch.getText().toString());
        bundle.putInt("ProvinceID", provinceID);

        //Add fragment here

        FragmentMostRight fragmentMostRight = new FragmentMostRight();
        fragmentMostRight.setArguments(bundle);

        FragmentNearBy fragmentNearBy = new FragmentNearBy();
        fragmentNearBy.setArguments(bundle);

        adapter = new MenuViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(fragmentMostRight, "Đúng nhất");
        adapter.AddFragment(fragmentNearBy, "Gần tôi");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        super.onRestart();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
