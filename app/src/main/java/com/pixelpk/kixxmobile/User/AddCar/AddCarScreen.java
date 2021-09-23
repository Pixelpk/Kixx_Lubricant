package com.pixelpk.kixxmobile.User.AddCar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.AddCarInfoScreen;
import com.pixelpk.kixxmobile.User.HomeScreen;
import com.pixelpk.kixxmobile.User.ModelClasses.AddCarList;
import com.pixelpk.kixxmobile.User.ModelClasses.Notificationlist;
import com.pixelpk.kixxmobile.User.ModelClasses.PromosList;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
import com.pixelpk.kixxmobile.User.adapters.AddCarAdapter;
import com.pixelpk.kixxmobile.User.adapters.NotificationAdapter;
import com.pixelpk.kixxmobile.User.adapters.PromosAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCarScreen extends AppCompatActivity {

    FloatingActionButton AddCar_addcar_FAB;
    RecyclerView AddCar_carlist_RV;
    List<AddCarList> myListData;
    RecyclerView recyclerView;
    String userid;
    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    LinearLayout AddCar_backarrow_LL;
    String rtl;

    ImageView Addcar_backarrow_IV;

    String token;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_screen);



        InitializeView();
        getCarsData();


       /* AddCarAdapter adapter = new AddCarAdapter(myListData, AddCarScreen.this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddCarScreen.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);*/


        AddCar_addcar_FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddCarScreen.this, AddCarInfoScreen.class);
                intent.putExtra("oilchange","2");
                startActivity(intent);
                finish();
            }
        });

        AddCar_backarrow_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void InitializeView()
    {
       /* myListData = new AddCarList[] {
                new AddCarList("ABC 876","Jaguar","XF","2015"),
                new AddCarList("LLE 566","Audi","Q7","2020"),


        };*/

        myListData = new ArrayList<>();
        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        AddCar_addcar_FAB = findViewById(R.id.AddCar_addcar_FAB);
        recyclerView = (RecyclerView) findViewById(R.id.AddCar_carlist_RV);

        progressDialog = new ProgressDialog(this);

        userid = sharedPreferences.getString(Shared.loggedIn_user_id,"0");
        token = sharedPreferences.getString(Shared.loggedIn_jwt,"0");

        AddCar_backarrow_LL = findViewById(R.id.AddCar_backarrow_LL);

        Addcar_backarrow_IV = findViewById(R.id.Addcar_backarrow_IV);

        rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");
        //Toast.makeText(this, token, Toast.LENGTH_SHORT).show();

        if(rtl.equals("1"))
        {

            Addcar_backarrow_IV.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24_rwhite);

        }
        else {
        }

    }


    private void getCarsData() {

    //    Toast.makeText(this, userid, Toast.LENGTH_SHORT).show();
        myListData.clear();
//        progressDialog.setMessage("Please Wait! while we are getting car data");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GET_CARS_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    //      Toast.makeText(AddCarScreen.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");


                            if(message.contains("success"))
                            {

                                JSONArray manufacturer  = jsonObj.getJSONArray("resp");

                                for (int i = 0; i < manufacturer.length(); i++) {

                                    JSONObject m = manufacturer.getJSONObject(i);

                                    String car_id = m.getString("id");
                                    String car_name = m.getString("car_number");
                                    String name = m.getString("name");
                                    String model = m.getString("model");
                                    String company = m.getString("company");
                                    String odometer = m.getString("odometer");
                                    String daily_mileage = m.getString("daily_mileage");
                                    String year_of_manufacture = m.getString("year_of_manufacture");
                                    String engine_type = m.getString("engine_type");
                                    String cid = m.getString("car_id");



                                    //Toast.makeText(AddCarScreen.this, car_name +" "+ company + " "+ name + " " + model, Toast.LENGTH_SHORT).show();

                                  //  new AddCarList("ABC 876","Jaguar","XF","2015")
                                    AddCarList promosList = new AddCarList(car_name,company,name,model,car_id,odometer,daily_mileage,year_of_manufacture,engine_type,cid);
                                    myListData.add(promosList);

                                }


                                AddCarAdapter adapter = new AddCarAdapter(myListData, AddCarScreen.this,token,userid,recyclerView);
                                recyclerView.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddCarScreen.this);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                adapter.notifyDataSetChanged();
                                
                                //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setAdapter(adapter);

                            }
                            else
                            {
                                alerbox();
                               // Toast.makeText(AddCarScreen.this, getResources().getString(R.string.no_data_error), Toast.LENGTH_SHORT).show();
                              //  progressDialog.dismiss();
                            }

                        } catch (final JSONException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    /*Toast.makeText(AddCarScreen.this,
                                            "Json parsing error: " + e.getMessage(),
                                            Toast.LENGTH_LONG).show();*/
                                }
                            });
                        }


                        //      Toast.makeText(Signin.this, response, Toast.LENGTH_SHORT).show();


                    }
                },
                error -> {
                    //   progressDialog.dismiss();
            //        Toast.makeText(AddCarScreen.this, error.toString(), Toast.LENGTH_LONG).show();
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

                   parameters.put("id",userid);

                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(AddCarScreen.this);
        requestQueue.add(stringRequest);


    }


    public void alerbox()
    {
        new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.nocaradded))
                .setCancelable(false)
                .setNegativeButton(getResources().getString(R.string.ok), null)
                .show();
    }
}