package com.pixelpk.kixxmobile.User.ClaimReward;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.pixelpk.kixxmobile.Login;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.Feedback;
import com.pixelpk.kixxmobile.User.HomeScreen;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ClaimRewardPointsScreen extends AppCompatActivity {

    ImageView ClaimReward_badge,back_btn;
    TextView ClaimReward_Totalpoints,ClaimReward_userbadgetext;
    LinearLayout ClaimReward_1liter,ClaimReward_2liter,ClaimReward_3liter,ClaimReward_4liter,Claimscreen_back;

    ProgressDialog progressDialog;
    String token="";
    String points,badge;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_reward_points_screen);

        initializeViews();

        points = intent.getStringExtra("points");
        badge = intent.getStringExtra("badge");

      //  Toast.makeText(this, badge, Toast.LENGTH_SHORT).show();



        badge_update(Integer.parseInt(badge));
        //Toast.makeText(this, badge, Toast.LENGTH_SHORT).show();

        ClaimReward_Totalpoints.setText(points);

        Claimscreen_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ClaimRewardPointsScreen.this,HomeScreen.class);
                startActivity(intent);
                finish();


            }
        });


        ClaimReward_1liter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                new AlertDialog.Builder(ClaimRewardPointsScreen.this)
                        .setMessage(getResources().getString(R.string.claim1liter))
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.redeem), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Redeempoints("1000","1");

                                    /* HomeScreen.super.onBackPressed();
                                    finishAffinity();
                                    finish();*/
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.cancel), null)
                        .show();


            }
        });

        ClaimReward_2liter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(ClaimRewardPointsScreen.this)
                        .setMessage(getResources().getString(R.string.claim2liter))
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.redeem), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Redeempoints("2000","2");

                                    /* HomeScreen.super.onBackPressed();
                                    finishAffinity();
                                    finish();*/
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.cancel), null)
                        .show();



            }
        });

        ClaimReward_3liter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(ClaimRewardPointsScreen.this)
                        .setMessage(getResources().getString(R.string.claim3liter))
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.redeem), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Redeempoints("3000","3");

                                    /* HomeScreen.super.onBackPressed();
                                    finishAffinity();
                                    finish();*/
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.cancel), null)
                        .show();



            }
        });

        ClaimReward_4liter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(ClaimRewardPointsScreen.this)
                        .setMessage(getResources().getString(R.string.claim4liter))
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.redeem), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Redeempoints("4000","4");

                                    /* HomeScreen.super.onBackPressed();
                                    finishAffinity();
                                    finish();*/
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.cancel), null)
                        .show();



            }
        });


    }

    public void initializeViews()
    {
        progressDialog = new ProgressDialog(ClaimRewardPointsScreen.this);
        Claimscreen_back = findViewById(R.id.Claim_back);
        ClaimReward_Totalpoints = findViewById(R.id.ClaimReward_Totalpoints);
        ClaimReward_1liter = findViewById(R.id.ClaimReward_1liter);
        ClaimReward_2liter = findViewById(R.id.ClaimReward_2liter);
        ClaimReward_3liter = findViewById(R.id.ClaimReward_3liter);
        ClaimReward_4liter = findViewById(R.id.ClaimReward_4liter);
        ClaimReward_userbadgetext = findViewById(R.id.ClaimReward_userbadgetext);
        ClaimReward_badge = findViewById(R.id.ClaimReward_badge);

        back_btn = findViewById(R.id.Claimscreen_back);

        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        token = sharedPreferences.getString(Shared.loggedIn_jwt,"0");

        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

        if(rtl.equals("1"))
        {
            back_btn.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24_rwhite);
        }


        //Toast.makeText(this, token, Toast.LENGTH_SHORT).show();

        intent = getIntent();


    }

    public void Redeempoints(String points,String liters)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.USER_loyality_claim,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                      //  Toast.makeText(ClaimRewardPointsScreen.this, response, Toast.LENGTH_SHORT).show();
                        //       Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                     //   finish();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");
                            if(message.contains("success"))
                            {
                                String resp = jsonObj.getString("resp");
                             //   Toast.makeText(ClaimRewardPointsScreen.this, resp, Toast.LENGTH_LONG).show();
                                new AlertDialog.Builder(ClaimRewardPointsScreen.this)
                                        .setMessage(getResources().getString(R.string.points_claimed))
                                        .setCancelable(false)
                                        .setNegativeButton("Ok", null)
                                        .show();
                                String remainingPoints = jsonObj.getString("remainingPoints");
                                ClaimReward_Totalpoints.setText(remainingPoints);
                                progressDialog.dismiss();

                            }
                            else
                            {
                                String resp = jsonObj.getString("resp");
                            //    Toast.makeText(ClaimRewardPointsScreen.this, resp, Toast.LENGTH_LONG).show();
                                new AlertDialog.Builder(ClaimRewardPointsScreen.this)
                                        .setMessage(getResources().getString(R.string.notenoughpoints))
                                        .setCancelable(false)
                                        .setNegativeButton("Ok", null)
                                        .show();
                                progressDialog.dismiss();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }



                        //    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   progressDialog.dismiss();
                        progressDialog.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError)
                        {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();

                        }

                        else if (error instanceof AuthFailureError)
                        {
                            //TODO
                            //   Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            //        Toast.makeText(getActivity(), R.string.usernotfound, Toast.LENGTH_SHORT).show();
                        }

                        else if (error instanceof ServerError)
                        {
                            //TODO
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.servermaintainence), Toast.LENGTH_SHORT).show();
                        }

                        else if (error instanceof NetworkError)
                        {
                            //TODO
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                        }

                        else if (error instanceof ParseError)
                        {
                            //TODO
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.incorrectdata), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();


                parameters.put("points", points);
                parameters.put("litres", liters);

                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    public void badge_update(Integer points)
    {
        if(points == 0)
        {
            current_badge("Bronze");
        }
        else if(points >= 1 && points < 10)
        {
            current_badge("Silver");
        }
        else if(points >=10 && points < 25)
        {
            current_badge("Gold");
        }
        else if(points >= 25)
        {
            current_badge("Platinum");
        }
    }

    public void current_badge(String badgetype)
    {
        if(badgetype.equals("Bronze"))
        {
            ClaimReward_badge.setImageResource(R.mipmap.bronze);
            ClaimReward_userbadgetext.setText(getResources().getString(R.string.your_are_a_bronze_user));
        }
        else if(badgetype.equals("Silver"))
        {
            ClaimReward_badge.setImageResource(R.mipmap.silver);
            ClaimReward_userbadgetext.setText(getResources().getString(R.string.your_are_a_silver_user));
        }
        else if(badgetype.equals("Gold"))
        {
            ClaimReward_badge.setImageResource(R.mipmap.gold);
            ClaimReward_userbadgetext.setText(getResources().getString(R.string.you_are_gold_user));
        }
        else if(badgetype.equals("Platinum"))
        {
            ClaimReward_badge.setImageResource(R.mipmap.platinum);
            ClaimReward_userbadgetext.setText(getResources().getString(R.string.your_are_a_platinum_user));
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ClaimRewardPointsScreen.this, HomeScreen.class);
        startActivity(intent);
    }
}