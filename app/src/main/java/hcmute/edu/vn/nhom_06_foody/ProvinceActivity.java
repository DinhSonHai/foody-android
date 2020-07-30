package hcmute.edu.vn.nhom_06_foody;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.nhom_06_foody.Model.DbModelProvince;


public class ProvinceActivity extends AppCompatActivity {

    private TextView tvHuy;
    private TextView tvXong;
    private EditText etSearch;
    private MyDbClass myDbClass;
    private List<DbModelProvince> listProvince;
    public static int provinceID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.statusbar));

        tvHuy = (TextView) findViewById(R.id.huy);
        tvXong = (TextView) findViewById(R.id.xong);
        etSearch = (EditText) findViewById(R.id.search_province);

        myDbClass = new MyDbClass(this);
        listProvince = new ArrayList<>();

        Intent intent = getIntent();
        provinceID = intent.getExtras().getInt("ProvinceID");

        try{
            listProvince = myDbClass.getProvinceData();
        }
        catch(Exception ex){
            Toast.makeText(this, "showData:-" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if(listProvince != null){
            RecyclerView myrv = (RecyclerView) findViewById(R.id.province_view);
            ProvinceAdapter myAdapter = new ProvinceAdapter(this, listProvince);
            myrv.setLayoutManager(new GridLayoutManager(this,1));
            myrv.setAdapter(myAdapter);
        }


        tvHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        tvXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("ProvinceID", provinceID);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    listProvince = myDbClass.getProvinceDataByKeyword(etSearch.getText().toString());
                }
                catch(Exception ex){
                    Toast.makeText(ProvinceActivity.this, "showData:-" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                if(listProvince != null){
                    RecyclerView myrv = (RecyclerView) findViewById(R.id.province_view);
                    ProvinceAdapter myAdapter = new ProvinceAdapter(ProvinceActivity.this, listProvince);
                    myrv.setLayoutManager(new GridLayoutManager(ProvinceActivity.this,1));
                    myrv.setAdapter(myAdapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
