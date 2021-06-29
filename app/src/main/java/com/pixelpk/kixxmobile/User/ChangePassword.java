package com.pixelpk.kixxmobile.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
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
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {

    TextInputEditText ChangePassword_newpassET_txt;
    EditText ChangePassword_confpassET_txt;
    Button ChangePassword_changepassBtn;
    String token;

    SharedPreferences sharedPreferences;
    String visibility = "0";

    LinearLayout ChangePassword_backbtn;

    ImageView ContactUs_backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ChangePassword_changepassBtn = findViewById(R.id.ChangePassword_changepassBtn);
        ChangePassword_newpassET_txt = findViewById(R.id.ChangePassword_newpassET_txt);
        ChangePassword_confpassET_txt = findViewById(R.id.ChangePassword_confpassET_txt);
        ChangePassword_backbtn = findViewById(R.id.ChangePassword_backbtn);
        ContactUs_backbtn = findViewById(R.id.ContactUs_backbtn);

        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);

        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

//            Toast.makeText(getContext(),rtl, Toast.LENGTH_SHORT).show();

        if(rtl.equals("1"))
        {
            ContactUs_backbtn.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24_rwhite);
        }

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
                        Toast.makeText(ChangePassword.this,  getResources().getString(R.string.mismatchedpasswords), Toast.LENGTH_SHORT).show();
                    }
                }


            }

        });

        ChangePassword_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*ChangePassword_confpassET_txt.setOnTouchListener(new View.OnTouchListener() {
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
        });*/

        ChangePassword_confpassET_txt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE, "0");

                if(!rtl.equals("1"))
                {
                    //  Toast.makeText(getContext(), "ltr", Toast.LENGTH_SHORT).show();
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
                else
                {

                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;

                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        if(event.getRawX() <= (ChangePassword_confpassET_txt.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width()))  {    // your action here
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


            }
        });



    }

    private void Changepassword(final String ch_pass) {

        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.USER_CHANGE_PASSWORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                      //  Toast.makeText(ChangePassword.this, response, Toast.LENGTH_SHORT).show();

                        Toast.makeText(ChangePassword.this,  getResources().getString(R.string.passwordchangedsuccessfully), Toast.LENGTH_SHORT).show();
                     //   Toast.makeText(ChangePassword.this, "Your password changed successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ChangePassword.this,HomeScreen.class);
                        startActivity(intent);
                        finish();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   progressDialog.dismiss();
                  //      Toast.makeText(ChangePassword.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("password", ch_pass);


                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ChangePassword.this);
        requestQueue.add(stringRequest);


    }

    public int if_field_Empty(String pass,String confpass)
    {
        if(pass.equals(""))
        {
            ChangePassword_newpassET_txt.setError(getResources().getString(R.string.fill_fields));
            return 1;

        }
        else if(pass.length()<3)
        {
            ChangePassword_newpassET_txt.setError(getResources().getString(R.string.password_must_have_error));
            return 4;
        }
        else if(confpass.equals(""))
        {
            ChangePassword_confpassET_txt.setError(getResources().getString(R.string.fill_fields));
            return 2;
        }
        else if(confpass.length()<3)
        {
            ChangePassword_confpassET_txt.setError(getResources().getString(R.string.password_must_have_error));
            return 5;
        }
        else
        {
            return  3;
        }
    }





}