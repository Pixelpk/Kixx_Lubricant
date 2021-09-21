package com.pixelpk.kixxmobile.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.ModelClasses.CarDetailsList;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
import com.pixelpk.kixxmobile.User.adapters.CardDetailsAdapter;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity {

    TextInputEditText ForgotPassword_userphET_txt;
    Button ForgotPassword_userchangepassBtn;
    ProgressDialog progressDialog;
    int check_field;

    ImageView back_btn;

    String phone;

    EditText ForgotPassword_countrycode_ET;

    String countrycode="+966";
    String Completenumber;

    String zeroexcludednumber;

    ImageView ForgotPassword_back_btn;

    LinearLayout ForgotPass_back_LL;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        InitializingView();

        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

        if(rtl.equals("1"))
        {
            ForgotPassword_back_btn.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24_rwhite);
        }

        ForgotPassword_countrycode_ET.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                CountryPicker picker = CountryPicker.newInstance("Select Country");  // dialog title
                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {

                        countrycode = dialCode;
                  //      Toast.makeText(ForgotPassword.this, dialCode, Toast.LENGTH_SHORT).show();
                        ForgotPassword_countrycode_ET.setText(countrycode);
                        //countrycode = dialCode;
                        picker.dismiss();

                    }
                });
                picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
            }
        });

        ForgotPass_back_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ForgotPassword_userchangepassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String only_phone = ForgotPassword_userphET_txt.getText().toString();

                String s = only_phone.substring(0,1);

                if (ForgotPassword_userphET_txt.getText().toString().equals(""))
                {
                    ForgotPassword_userphET_txt.setError(getResources().getString(R.string.fill_fields));
                }
//                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                else if(s.equals("0"))
                {
                    Toast.makeText(getApplicationContext(), "The number should not start with 0", Toast.LENGTH_SHORT).show();
                }
                /*else if (ForgotPassword_userphET_txt.getText().toString().charAt(0) != '0') {

                    ForgotPassword_userphET_txt.setError(getResources().getString(R.string.phone_number_start_warning));

                }*/
                else
                {
                    check_field = if_field_Empty();

                    if (check_field == 3) {

                        get_user_car_activity();

                    }
                }
            }
        });

    }

    public void InitializingView()
    {
        ForgotPassword_userphET_txt = findViewById(R.id.ForgotPassword_userphET_txt);
        ForgotPassword_userchangepassBtn = findViewById(R.id.ForgotPassword_userchangepassBtn);
        ForgotPassword_back_btn = findViewById(R.id.ForgotPassword_back_btn);

        progressDialog = new ProgressDialog(ForgotPassword.this);

        ForgotPassword_countrycode_ET = findViewById(R.id.ForgotPassword_countrycode_ET);

        ForgotPassword_countrycode_ET.setFocusable(false);
        ForgotPassword_countrycode_ET.setClickable(true);

        ForgotPass_back_LL = findViewById(R.id.ForgotPass_back_LL);

        ForgotPassword_countrycode_ET.setText("+966");

        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public int if_field_Empty()
    {
        if(ForgotPassword_userphET_txt.getText().toString().equals(""))
        {
            ForgotPassword_userphET_txt.setError(getResources().getString(R.string.fill_fields));
            return 1;

        }
        /*else if(ForgotPassword_userphET_txt.getText().toString().charAt(0) != '0')
        {
            alerbox();
            //Toast.makeText(this, getResources().getString(R.string.phonenumbermuststartwith), Toast.LENGTH_SHORT).show();
            return 2;

        }*/
        else
        {
            return  3;
        }
    }

    public void get_user_car_activity()
    {


        phone = countrycode + ForgotPassword_userphET_txt.getText().toString();
        zeroexcludednumber = ForgotPassword_userphET_txt.getText().toString().substring(1);


     //   Toast.makeText(ForgotPassword.this, phone, Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.USER_FORGOT_PASSWORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    //            Toast.makeText(ForgotPassword.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");

                           // sendcode();

                            if(message.contains("success"))
                            {
                                sendcode(phone,zeroexcludednumber);
                            }
                            else
                            {
                                Toast.makeText(ForgotPassword.this, R.string.user_not_found, Toast.LENGTH_SHORT).show();
                            }

                        } catch (final JSONException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    /*Toast.makeText(ForgotPassword.this,
                                            "Json parsing error: " + e.getMessage(),
                                            Toast.LENGTH_LONG).show();*/
                                }
                            });
                        }


                        //      Toast.makeText(Signin.this, response, Toast.LENGTH_SHORT).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   progressDialog.dismiss();
                        progressDialog.dismiss();
                     //   Toast.makeText(ForgotPassword.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("phone", phone);


                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ForgotPassword.this);
        requestQueue.add(stringRequest);
    }


    public void sendcode(String completenumber,String zeroexcludednumber)
    {
    //    Toast.makeText(this, zeroexcludednumber, Toast.LENGTH_SHORT).show();

        if (completenumber.length() <= 8 || completenumber.length() >= 16) {
            ForgotPassword_userphET_txt.setError("Invalid Format");
        } else {
            Intent intent = new Intent(ForgotPassword.this, OTPScreen.class);
            intent.putExtra("AuthPhone", completenumber);
            intent.putExtra("withoutzeroAuthPhone",countrycode + zeroexcludednumber);
            intent.putExtra("Screen","2");
            startActivity(intent);
            finish();
          //  Toast.makeText(ForgotPassword.this, "User Login", Toast.LENGTH_SHORT).show();
        }
    }

    public String removeFirstChar(String s)
    {
        return s.substring(1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void alerbox()
    {
        new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.phonenumbermuststartwith))
                .setCancelable(false)
                .setNegativeButton("Ok", null)
                .show();
    }
}