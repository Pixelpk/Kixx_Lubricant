package com.pixelpk.kixxmobile.Salesman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.pixelpk.kixxmobile.CheckNetworkConnection;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.Salesman.ModelClasses.ClaimsList;
import com.pixelpk.kixxmobile.Salesman.adapters.claimAdapter;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.ModelClasses.Notificationlist;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
import com.pixelpk.kixxmobile.User.adapters.NotificationAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClaimsScreen extends AppCompatActivity {

    RecyclerView ClaimScreen_carsserviced;
    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String token,shopid,bearer_token;

    List<ClaimsList> myListData;

    ImageView CarsClaim_backarrow;

    claimAdapter adapter;

    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claims_screen);

        InitializeView();
        get_user_data(shopid);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                swipeRefreshLayout.setRefreshing(false);

                new CheckNetworkConnection(getApplicationContext(), new CheckNetworkConnection.OnConnectionCallback()
                {
                    @Override
                    public void onConnectionSuccess()
                    {
                        get_user_data(shopid);
                    }

                    @Override
                    public void onConnectionFail(String msg)
                    {
                        ClaimScreen_carsserviced.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                    }
                }).execute();
            }
        });

        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

        if(rtl.equals("1"))
        {
            CarsClaim_backarrow.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24_rwhite);
        }

        CarsClaim_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void InitializeView() {

        ClaimScreen_carsserviced = findViewById(R.id.ClaimScreen_carsserviced);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_claims);

        progressDialog = new ProgressDialog(this);


        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        token = sharedPreferences.getString(Shared.sales_loggedIn_jwt,"0");
        shopid = sharedPreferences.getString(Shared.loggedIn_sales_shopid,"0");

        bearer_token = sharedPreferences.getString(Shared.sales_loggedIn_jwt,"0");

   //     Toast.makeText(this, shopid, Toast.LENGTH_SHORT).show();

        myListData = new ArrayList<>();

        CarsClaim_backarrow = findViewById(R.id.CarsClaim_backarrow);



    }

    private void get_user_data(String id) {

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.SALES_SHOP_CLAIMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                     //         Toast.makeText(ClaimsScreen.this, response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");

                        //    Toast.makeText(ClaimsScreen.this, response, Toast.LENGTH_SHORT).show();

                            if(message.contains("success"))
                            {
                                progressDialog.dismiss();
                                ClaimScreen_carsserviced.setVisibility(View.VISIBLE);

                                JSONArray claims  = jsonObj.getJSONArray("resp");

                                if(!claims.equals("null"))
                                {

                                    for (int i = 0; i < claims.length(); i++) {
                                        JSONObject objads = claims.getJSONObject(i);
                                        String date = objads.getString("date");
                                        String product_name = objads.getString("product_name");
                                        String car_number = objads.getString("car_number");

                                //        Toast.makeText(ClaimsScreen.this, car_number, Toast.LENGTH_SHORT).show();

                                        ClaimsList claimsList = new ClaimsList(date,product_name,car_number);
                                        myListData.add(claimsList);

                                    }

                                    adapter = new claimAdapter(myListData,ClaimsScreen.this);
                                    ClaimScreen_carsserviced.setHasFixedSize(true);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ClaimsScreen.this);
                                    ClaimScreen_carsserviced.setLayoutManager(linearLayoutManager);
                                    //    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()()));
                                    ClaimScreen_carsserviced.setAdapter(adapter);

                                }




                            }
                            else
                            {
                                progressDialog.dismiss();
                                ClaimScreen_carsserviced.setVisibility(View.VISIBLE);
                                Toast.makeText(ClaimsScreen.this, getResources().getString(R.string.no_data_error), Toast.LENGTH_SHORT).show();
                            }

                        } catch (final JSONException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                   /* Toast.makeText(ClaimsScreen.this,
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
                    public void onErrorResponse(VolleyError error)
                    {
                        //   progressDialog.dismiss();
                        progressDialog.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError)
                        {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                        }

                        else if(error instanceof AuthFailureError)
                        {
                            //TODO
                            //   Toast.makeText(getApplicationContext()(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            //   Toast.makeText(getApplicationContext()(), R.string.usernotfound, Toast.LENGTH_SHORT).show();
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
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + bearer_token);
                return headers;
            }


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("shop_id", id);


                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ClaimsScreen.this);
        requestQueue.add(stringRequest);


    }

}