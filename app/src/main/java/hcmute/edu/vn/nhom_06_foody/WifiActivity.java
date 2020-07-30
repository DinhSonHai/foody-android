package hcmute.edu.vn.nhom_06_foody;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WifiActivity extends Activity {

    private EditText editTextName;
    private EditText editTextPassword;
    private Button buttonUpdateWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wifi);

        Intent intent = getIntent();
        String name = intent.getExtras().getString("Name");
        String password = intent.getExtras().getString("Password");

        editTextName = (EditText) findViewById(R.id.name);
        editTextPassword = (EditText) findViewById(R.id.password);
        buttonUpdateWifi = (Button) findViewById(R.id.update);

        if(name != null && password != null){
            editTextName.setText(name);
            editTextPassword.setText(password);
        }

        buttonUpdateWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(editTextName.getText().toString()) && !TextUtils.isEmpty(editTextPassword.getText().toString())){
                    Intent intent = new Intent();
                    intent.putExtra("name", editTextName.getText().toString());
                    intent.putExtra("password", editTextPassword.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else{
                    Toast.makeText(WifiActivity.this, "Tên đăng nhập hoặc mật khẩu rỗng", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
