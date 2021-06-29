package com.pixelpk.kixxmobile.User;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.pixelpk.kixxmobile.Constants;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.Salesman.Fragments.Sales_HomeFragment;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Signin extends AppCompatActivity {

    // Declaration of Relevant Variables

    Button Signin_userSigninBtn;
    TextView Signin_signupTV;
    EditText Signin_userphET_txt,Signin_userpassET_txt;
    int check_field;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    ProgressDialog progressDialog;

    TextView Signin_forgotpass_TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        InitializingView();

        Signin_userSigninBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check_field = if_field_Empty();

                if(check_field == 3)
                {
                    progressDialog.show();
                    login_user();

                //    Toast.makeText(Signin.this, "User Login", Toast.LENGTH_SHORT).show();
                }


            }
        });

        Signin_forgotpass_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Signin.this, "Module Under Development", Toast.LENGTH_SHORT).show();
            }
        });

        Signin_signupTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signin.this,Signup.class);
                startActivity(intent);
            }
        });
    }


    public void InitializingView()
    {
        Signin_userSigninBtn = findViewById(R.id.Signin_userSigninBtn);
        Signin_userpassET_txt = findViewById(R.id.Signin_userpassET_txt);
        Signin_userphET_txt = findViewById(R.id.Signin_userphET_txt);
        Signin_signupTV = findViewById(R.id.Signin_signupTV);
        Signin_forgotpass_TV = findViewById(R.id.Signin_forgotpass_TV);

        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        progressDialog = new ProgressDialog(Signin.this);

    }

    public int if_field_Empty()
    {
        if(Signin_userphET_txt.getText().toString().equals(""))
        {
            Signin_userphET_txt.setError("Please fill this field");
            return 1;

        }
        else if(Signin_userpassET_txt.getText().toString().equals(""))
        {
            Signin_userpassET_txt.setError("Please fill this field");
            return 2;
        }
        else
        {
            return  3;
        }
    }

    private void login_user() {


        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.USER_SIGNIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("message");

       //                     Toast.makeText(Signin.this, response, Toast.LENGTH_SHORT).show();

                            if(message.contains("Successful login."))
                            {
                                JSONObject data  = jsonObj.getJSONObject("data");
                                for (int i = 0; i < 1; i++) {

                                    String user_id = data.getString("userid");
                  /*                  String user_name = data.getString("name");
                                    String user_email = data.getString("email");
                                    String user_ph = data.getString("phone");
                                    String user_dp = data.getString("profile_img");
                                    String user_gender = data.getString("gender");
                                    String user_fcm_id = data.getString("fcm_id");
                                    String user_loyality_points = data.getString("loyality_points");
                                    String user_occupation_id = data.getString("occupation_id");
                                    String user_occupation_name = data.getString("occupation_name");
                                    String user_category = data.getString("category");
                                    String user_user_role = data.getString("user_role");*/

                                    editor.putString(Shared.loggedIn_user_id,user_id);
/*                                    editor.putString(Shared.loggedIn_user_name,user_name);
                                    editor.putString(Shared.loggedIn_user_email,user_email);
                                    editor.putString(Shared.loggedIn_user_ph,user_ph);
                                    editor.putString(Shared.loggedIn_user_dp,user_dp);
                                    editor.putString(Shared.loggedIn_user_gender,user_gender);
                                    editor.putString(Shared.loggedIn_user_fcm_id,user_fcm_id);
                                    editor.putString(Shared.loggedIn_loyality_points,user_loyality_points);
                                    editor.putString(Shared.loggedIn_user_occupation_id,user_occupation_id);
                                    editor.putString(Shared.loggedIn_user_occupation_name,user_occupation_name);
                                    editor.putString(Shared.loggedIn_user_category,user_category);
                                    editor.putString(Shared.loggedIn_user_user_role,user_user_role);*/
                                    editor.apply();

                                }
                            }
                            else
                            {
                         //       Toast.makeText(Signin.this, getResources().getString(R.string.usernotfound), Toast.LENGTH_SHORT).show();
                            }

                        } catch (final JSONException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

//                                    Toast.makeText(getApplicationContext(),
//                                            "Json parsing error: " + e.getMessage(),
//                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        }
//                      Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Signin.this,HomeScreen.class);
                        startActivity(intent);

                  //      Toast.makeText(Signin.this, response, Toast.LENGTH_SHORT).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   progressDialog.dismiss();\
                        progressDialog.dismiss();
//                        Toast.makeText(Signin.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("phone", Signin_userphET_txt.getText().toString());
                parameters.put("password", Signin_userpassET_txt.getText().toString());


                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(Signin.this);
        requestQueue.add(stringRequest);


    }
}