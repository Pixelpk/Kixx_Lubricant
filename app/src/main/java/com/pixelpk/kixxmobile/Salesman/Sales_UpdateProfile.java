package com.pixelpk.kixxmobile.Salesman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;

public class Sales_UpdateProfile extends AppCompatActivity {

    TextView sales_salesprofile_salesid_TV;
    EditText sales_salesprofile_name_ET,sales_salesprofile_contact_ET;

    String shopid,shopname,shopcontact;

    ImageView back_btn;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    LinearLayout Sales_profile_back_LL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales__update_profile);

        InitializerView();

        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

        if(rtl.equals("1"))
        {
            back_btn.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24_rwhite);
        }

        Sales_profile_back_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void InitializerView() {

        sales_salesprofile_salesid_TV = findViewById(R.id.sales_salesprofile_salesid_TV);
        sales_salesprofile_name_ET = findViewById(R.id.sales_salesprofile_name_ET);
        sales_salesprofile_contact_ET = findViewById(R.id.sales_salesprofile_contact_ET);
        Sales_profile_back_LL = findViewById(R.id.Sales_profile_back_LL);

        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        shopid = sharedPreferences.getString(Shared.loggedIn_sales_shopid,"0");
        shopname = sharedPreferences.getString(Shared.sales_loggedIn_user_name,"0");
        shopcontact = sharedPreferences.getString(Shared.sales_loggedIn_user_ph,"0");

        sales_salesprofile_salesid_TV.setText(shopid);
        sales_salesprofile_name_ET.setText(shopname);
        sales_salesprofile_contact_ET.setText(shopcontact);

        back_btn = findViewById(R.id.sales_back_btn_profile);

        disableEditText(sales_salesprofile_name_ET);
        disableEditText(sales_salesprofile_contact_ET);


    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
       // editText.setBackgroundColor(Color.TRANSPARENT);
        editText.setGravity(Gravity.LEFT);
    }
}