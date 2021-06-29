package com.pixelpk.kixxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.pixelpk.kixxmobile.User.ChangePassword;
import com.pixelpk.kixxmobile.User.HomeScreen;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;

import java.util.HashMap;
import java.util.Map;

public class ForgotPass_ChangePassword extends AppCompatActivity {

    TextInputEditText ChangePassword_newpassET_txt,ChangePassword_confpassET_txt;
    Button ChangePassword_changepassBtn;
    String token;

    SharedPreferences sharedPreferences;

    String getdata;

    String visibility = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass__change_password);

        ChangePassword_changepassBtn = findViewById(R.id.ForgotPass_ChangePassword_changepassBtn);
        ChangePassword_newpassET_txt = findViewById(R.id.ForgotPass_ChangePassword_newpassET_txt);
        ChangePassword_confpassET_txt = findViewById(R.id.ForgotPass_ChangePassword_confpassET_txt);

        getdata = getIntent().getStringExtra("phone");

//        Toast.makeText(this, getdata, Toast.LENGTH_SHORT).show();

        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);

        token = sharedPreferences.getString(Shared.loggedIn_jwt,"0");



        ChangePassword_changepassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newpass = ChangePassword_newpassET_txt.getText().toString();
                String confpass = ChangePassword_confpassET_txt.getText().toString();

                int check = if_field_Empty(newpass, confpass);

                if (check == 3)
                {
                    if(newpass.equals(confpass))
                    {
                        Changepassword(newpass);
                    }
                    else
                    {
                        alerbox();
                      //  Toast.makeText(ForgotPass_ChangePassword.this, getResources().getString(R.string.mismatchedpasswords), Toast.LENGTH_SHORT).show();
                    }
                }


            }

        });


        ChangePassword_confpassET_txt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (ChangePassword_confpassET_txt.getRight() - ChangePassword_confpassET_txt.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        if(visibility.equals("0"))
                        {
                            ChangePassword_confpassET_txt.setTransformationMethod(null);
                            visibility = "1";
                        }
                        else
                        {
                            ChangePassword_confpassET_txt.setTransformationMethod(new PasswordTransformationMethod());
                            visibility = "0";

                        }


                        return true;
                    }
                }
                return false;
            }
        });


    }





    private void Changepassword(final String ch_pass) {

     //   Toast.makeText(this, getdata, Toast.LENGTH_SHORT).show();
     //   Toast.makeText(this, ch_pass, Toast.LENGTH_SHORT).show();

        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.USER_FORGOT_PASS_CHANGE_PASSWORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                //       Toast.makeText(ForgotPass_ChangePassword.this, response, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ForgotPass_ChangePassword.this, HomeScreen.class);
                        startActivity(intent);
                        finish();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   progressDialog.dismiss();
//                        Toast.makeText(ForgotPass_ChangePassword.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("password", ch_pass);
                parameters.put("phone", getdata);


                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ForgotPass_ChangePassword.this);
        requestQueue.add(stringRequest);


    }

    public int if_field_Empty(String pass,String confpass)
    {
        if(pass.equals(""))
        {
            ChangePassword_newpassET_txt.setError("Please fill this field");
            return 1;

        }
        else if(confpass.equals(""))
        {
            ChangePassword_confpassET_txt.setError("Please fill this field");
            return 2;
        }
        else
        {
            return  3;
        }
    }

    public void alerbox()
    {
        new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.mismatchedpasswords))
                .setCancelable(false)
                .setNegativeButton("Ok", null)
                .show();
    }


}