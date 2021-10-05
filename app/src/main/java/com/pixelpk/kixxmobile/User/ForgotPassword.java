package com.pixelpk.kixxmobile.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
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

    //Handle Button Clicks
    private long mLastClickTime = 0;


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
            public void onClick(View v)
            {
                // Button Handling

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

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
            public void onClick(View v)
            {
                // Button Handling

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                finish();
            }
        });


        ForgotPassword_userchangepassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // Button Handling

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                String only_phone = ForgotPassword_userphET_txt.getText().toString();

                if (ForgotPassword_userphET_txt.getText().toString().equals(""))
                {
                    ForgotPassword_userphET_txt.setError(getResources().getString(R.string.fill_fields));
                }
//                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();


                /*else if (ForgotPassword_userphET_txt.getText().toString().charAt(0) != '0') {

                    ForgotPassword_userphET_txt.setError(getResources().getString(R.string.phone_number_start_warning));

                }*/
                else
                {
                    String s = only_phone.substring(0,1);

                    check_field = if_field_Empty();

                    if(s.equals("0"))
                    {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.zero_error), Toast.LENGTH_SHORT).show();
                    }

                    else if (check_field == 3)
                    {
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
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        phone = countrycode + ForgotPassword_userphET_txt.getText().toString();
        zeroexcludednumber = ForgotPassword_userphET_txt.getText().toString().substring(1);


     //   Toast.makeText(ForgotPassword.this, phone, Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.USER_FORGOT_PASSWORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    //            Toast.makeText(ForgotPassword.this, response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");

                           // sendcode();

                            if(message.contains("success"))
                            {
                                progressDialog.dismiss();
                                sendcode(phone,zeroexcludednumber);
                            }
                            else
                            {
                                progressDialog.dismiss();
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
                        //  Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                        //   progressDialog.dismiss();\
                        progressDialog.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();

                        } else if (error instanceof AuthFailureError) {
                            //TODO

                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.usernotfound), Toast.LENGTH_SHORT).show();
                            //    Toast.makeText(getActivity(), R.string.usernotfound, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            //TODO
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.servermaintainence), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NetworkError) {
                            //TODO
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            //TODO
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                            //       Toast.makeText(getActivity(), getResources().getString(R.string.incorrectdata), Toast.LENGTH_SHORT).show();
                        }
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
    public void onBackPressed()
    {
        super.onBackPressed();

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
        {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

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