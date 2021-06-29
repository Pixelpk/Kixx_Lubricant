package com.pixelpk.kixxmobile.User;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.pixelpk.kixxmobile.Constants;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.AddCar.AddCarScreen;
import com.pixelpk.kixxmobile.User.OilRecommendation.OIl_Recommendation;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class AddCarInfoScreen extends AppCompatActivity {

    EditText AddCarInfo_carnumber_ET,AddCarInfo_carodometer_ET,AddCarInfo_Mileage_ET,AddCarInfo_dailycarmileage_ET;
    Spinner AddCarInfo_carmanufact_SP,AddCarInfo_carbrand_SP;
    Button AddCarInfo_addcar_Btn;

    ProgressDialog progressDialog;

    ArrayList<String> cars_list,manufacturer_list,manufacture_id;
    ArrayList<String> years_list,enginetype_list;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String token,car_idnumber="null";

    String uid,manufact_id;
    ImageView AddCarInfo_backarrow;

    SpinnerDialog spinnerDialog;
    SpinnerDialog spinnerDialog_carmanufact;
    SpinnerDialog spinnerDialog_year;
    SpinnerDialog spinnerDialog_enginetype;

    LinearLayout AddCarInfo_carbrand_LL,AddCarInfo_carmanufact_LL;
    LinearLayout AddCarInfo_yearofmanufacturing_LL,AddCarInfo_enginetype_LL,AddCar_back_LL;

    TextView AddCar_carbrand_seletion_TV;
    TextView AddCarInfo_carmanufact_TV;
    TextView AddCar_modelyear_TV;
    TextView AddCar_enginetype_TV;

    List<String> carid_list;
    String rtl;

    String Year_manufacture = "";
    String engine_type = "";

    String get_intentval = "";

    String car_odometer,daily_mileage,year_of_manufacture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_info_screen);

        InitializeViews();


        AddCarInfo_addcar_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(car_idnumber.equals("null"))
                {
                    //Toast.makeText(AddCarInfoScreen.this, "Please Check your internet Connection and try again", Toast.LENGTH_SHORT).show();
                    Toast.makeText(AddCarInfoScreen.this, getResources().getString(R.string.select_car), Toast.LENGTH_SHORT).show();
                }
                else if(AddCarInfo_carnumber_ET.getText().toString().equals(""))
                {
                    Toast.makeText(AddCarInfoScreen.this, getResources().getString(R.string.entercarnumber), Toast.LENGTH_SHORT).show();
                }
                else if (AddCarInfo_carnumber_ET.getText().toString().contains(" "))
                {
                    AddCarInfo_carnumber_ET.setError(getResources().getString(R.string.spacingerror));
                }

                else
                {
                    String val = AddCarInfo_carnumber_ET.getText().toString().toUpperCase();
                //  Toast.makeText(AddCarInfoScreen.this, val, Toast.LENGTH_SHORT).show();
                  /*Toast.makeText(AddCarInfoScreen.this, car_idnumber, Toast.LENGTH_SHORT).show();
                    Toast.makeText(AddCarInfoScreen.this, AddCarInfo_Mileage_ET.getText().toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(AddCarInfoScreen.this, AddCarInfo_carnumber_ET.getText().toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(AddCarInfoScreen.this, AddCarInfo_dailycarmileage_ET.getText().toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(AddCarInfoScreen.this, Year_manufacture, Toast.LENGTH_SHORT).show();
                    Toast.makeText(AddCarInfoScreen.this, engine_type, Toast.LENGTH_SHORT).show();*/

                    if(AddCarInfo_Mileage_ET.getText().toString().equals(""))
                    {
                        car_odometer = "";
                    }
                    else
                    {
                        car_odometer = AddCarInfo_Mileage_ET.getText().toString();
                    }
                    if(AddCarInfo_dailycarmileage_ET.getText().toString().equals(""))
                    {
                        daily_mileage = "";
                    }
                    else
                    {
                        daily_mileage = AddCarInfo_dailycarmileage_ET.getText().toString();
                    }
                    if( Year_manufacture == null || Year_manufacture.equals("") )
                    {
                        Year_manufacture = "";
                    }

                    if( engine_type == null || engine_type.equals("") )
                    {
                        engine_type = "";
                    }

                  Add_User_Car(car_idnumber,car_odometer,AddCarInfo_carnumber_ET.getText().toString(),daily_mileage,Year_manufacture,engine_type);

                }


            }
        });

        AddCar_back_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(get_intentval.equals("1"))
                {
                    Intent intent = new Intent(AddCarInfoScreen.this, OIl_Recommendation.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(AddCarInfoScreen.this,AddCarScreen.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

      /*  AddCarInfo_carbrand_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) findViewById(R.id.spinner_dropdown_tv_icon)).setTextColor(getResources().getColor(R.color.white));
                spinnerDialog.showSpinerDialog();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        /*AddCarInfo_carmanufact_SP.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
     *//*           // Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                car_idnumber = String.valueOf(position + 1);
              //  String cid = cars_list.get(Integer.parseInt(car_idnumber));
                Toast.makeText(AddCarInfoScreen.this, car_idnumber, Toast.LENGTH_SHORT).show();*//*
            }
        });*/

    /*    AddCarInfo_carbrand_SP.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                // Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                car_idnumber = String.valueOf(position+2);

                //  String cid = cars_list.get(Integer.parseInt(car_idnumber));
              //  Toast.makeText(AddCarInfoScreen.this, car_idnumber, Toast.LENGTH_SHORT).show();
            }
        });*/


/*
        AddCarInfo_carbrand_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                car_idnumber = String.valueOf(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        AddCarInfo_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(get_intentval.equals("1"))
                {
                    Intent intent = new Intent(AddCarInfoScreen.this, OIl_Recommendation.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(AddCarInfoScreen.this,AddCarScreen.class);
                    startActivity(intent);
                    finish();
                }


            }
        });

        spinnerDialog_carmanufact.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
              //  Toast.makeText(AddCarInfoScreen.this, item, Toast.LENGTH_SHORT).show();
              /*  car_idnumber = String.valueOf(position);
                Toast.makeText(AddCarInfoScreen.this, String.valueOf(car_idnumber), Toast.LENGTH_SHORT).show();
              */
                manufact_id = manufacture_id.get(position);

        //        Toast.makeText(AddCarInfoScreen.this, manufact_id, Toast.LENGTH_SHORT).show();
                get_specific_cars(manufact_id);
                AddCarInfo_carmanufact_TV.setText(item);
                // selectedItems.setText(item + " Position: " + position);
            }
        });

        AddCarInfo_carmanufact_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //     ((TextView) findViewById(R.id.spinner_dropdown_tv_icon)).setTextColor(getResources().getColor(R.color.white));
                spinnerDialog_carmanufact.showSpinerDialog();
            }
        });

        AddCarInfo_yearofmanufacturing_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //     ((TextView) findViewById(R.id.spinner_dropdown_tv_icon)).setTextColor(getResources().getColor(R.color.white));
                spinnerDialog_year.showSpinerDialog();
            }
        });

        AddCarInfo_enginetype_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //     ((TextView) findViewById(R.id.spinner_dropdown_tv_icon)).setTextColor(getResources().getColor(R.color.white));
                spinnerDialog_enginetype.showSpinerDialog();
            }
        });



        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                //  Toast.makeText(AddCarInfoScreen.this, item, Toast.LENGTH_SHORT).show();
                //car_idnumber = String.valueOf(position);
                car_idnumber = carid_list.get(position);
           //     Toast.makeText(AddCarInfoScreen.this, car_idnumber, Toast.LENGTH_SHORT).show();
             //   Toast.makeText(AddCarInfoScreen.this, String.valueOf(car_idnumber), Toast.LENGTH_SHORT).show();
                AddCar_carbrand_seletion_TV.setText(item);
                // selectedItems.setText(item + " Position: " + position);
            }
        });

        spinnerDialog_enginetype.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {

                AddCar_enginetype_TV.setText(item);
                engine_type = String.valueOf(position+1);

            }
        });


        spinnerDialog_year.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {

                AddCar_modelyear_TV.setText(item);
                Year_manufacture = item;
            }
        });

        AddCarInfo_carbrand_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     ((TextView) findViewById(R.id.spinner_dropdown_tv_icon)).setTextColor(getResources().getColor(R.color.white));
                spinnerDialog.showSpinerDialog();
            }
        });




        AddCarInfo_dailycarmileage_ET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


                /*.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView) findViewById(R.id.spinner_dropdown_tv_icon)).setTextColor(getResources().getColor(R.color.white));
                spinnerDialog.showSpinerDialog();
            }
        });*/

    }

    public void InitializeViews()
    {

        AddCarInfo_carnumber_ET = findViewById(R.id.AddCarInfo_carnumber_ET);
        AddCarInfo_carodometer_ET = findViewById(R.id.AddCarInfo_carodometer_ET);
      //  AddCarInfo_carmanufact_SP = findViewById(R.id.AddCarInfo_carmanufact_SP);
     //   AddCarInfo_carbrand_SP = findViewById(R.id.AddCarInfo_carbrand_SP);
        AddCarInfo_backarrow = findViewById(R.id.AddCarInfo_backarrow);
        AddCarInfo_carmanufact_LL = findViewById(R.id.AddCarInfo_carmanufact_LL);
        AddCarInfo_carbrand_LL = findViewById(R.id.AddCarInfo_carbrand_LL);

        AddCarInfo_addcar_Btn = findViewById(R.id.AddCarInfo_addcar_Btn);
        AddCarInfo_Mileage_ET = findViewById(R.id.AddCarInfo_Mileage_ET);
        AddCarInfo_dailycarmileage_ET = findViewById(R.id.AddCarInfo_dailycarmileage_ET);
        AddCar_enginetype_TV = findViewById(R.id.AddCar_enginetype_TV);
        AddCar_modelyear_TV = findViewById(R.id.AddCar_modelyear_TV);
        AddCarInfo_yearofmanufacturing_LL = findViewById(R.id.AddCarInfo_yearofmanufacturing_LL);
        AddCarInfo_enginetype_LL = findViewById(R.id.AddCarInfo_enginetype_LL);
        AddCar_back_LL = findViewById(R.id.AddCar_back_LL);

        progressDialog = new ProgressDialog(AddCarInfoScreen.this);

        cars_list = new ArrayList<>();
        manufacturer_list = new ArrayList<>();
        years_list = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.yearlist)));
        enginetype_list = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.enginetype)));
        manufacture_id = new ArrayList<>();

    //    cars_list.add(getResources().getString(R.string.select_car_brand));
        manufacturer_list.add(getResources().getString(R.string.select_car_manufacturer));

        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");
        //Toast.makeText(this, token, Toast.LENGTH_SHORT).show();

        if(rtl.equals("1"))
        {

            AddCarInfo_backarrow.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24_rwhite);

        }
        else
        {

        }


        uid = sharedPreferences.getString(Shared.loggedIn_user_id,"0");
        carid_list = new ArrayList<>();
       // Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();

        token = sharedPreferences.getString(Shared.loggedIn_jwt,"0");
      //  carid_list.add("select car brand");
        manufacture_id.add("select manufacturer");

        spinnerDialog=new SpinnerDialog(AddCarInfoScreen.this,cars_list,"Select or Search Car Brand",R.style.DialogAnimations_SmileWindow,getResources().getString(R.string.cancel));// With 	Animation
        spinnerDialog_carmanufact=new SpinnerDialog(AddCarInfoScreen.this,manufacturer_list,"Select or Search Car Manufacturer",R.style.DialogAnimations_SmileWindow,getResources().getString(R.string.cancel));// With 	Animation
        spinnerDialog_year =new SpinnerDialog(AddCarInfoScreen.this, years_list,"Select or Search Model/Year",R.style.DialogAnimations_SmileWindow,getResources().getString(R.string.cancel));// With 	Animation
        spinnerDialog_enginetype =new SpinnerDialog(AddCarInfoScreen.this,  enginetype_list,"Select or Search Engine type",R.style.DialogAnimations_SmileWindow,getResources().getString(R.string.cancel));// With 	Animation

        get_cars_data(token);

        get_intentval = getIntent().getStringExtra("oilchange");

     //   Toast.makeText(this, get_intentval, Toast.LENGTH_SHORT).show();

     /*   AddCarInfo_carmanufact_SP.setEnabled(false);
        AddCarInfo_carmanufact_SP.setClickable(false);
*/
        AddCarInfo_carmanufact_TV = findViewById(R.id.AddCarInfo_carmanufact_TV);

        AddCar_carbrand_seletion_TV = findViewById(R.id.AddCar_carbrand_seletion_TV);
    }

    public void get_cars_data(final String token)
    {
        //    Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
        progressDialog.show();
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CARS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                  //      Toast.makeText(AddCarInfoScreen.this, response, Toast.LENGTH_SHORT).show();
                       //     Toast.makeText(AddCarInfoScreen.this, response, Toast.LENGTH_SHORT).show();
                        //  Log.d("HTTP_AUTHORIZATION",token);
                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");



                            //    Toast.makeText(getContext(), user_exist_check, Toast.LENGTH_SHORT).show();

                            if(message.equals("success")) {



                          //      JSONArray cars  = jsonObj.getJSONArray("cars");
                                JSONArray manufact  = jsonObj.getJSONArray("manufacturer");

                            //    Toast.makeText(AddCarInfoScreen.this, manufact.toString(), Toast.LENGTH_SHORT).show();

                          //      manufacture_id.add("select_car");
                               /* for (int i = 0; i < cars.length(); i++) {

                                    JSONObject c = cars.getJSONObject(i);

                                    String car_brand = c.getString("name");
                                    String ident = c.getString("id");
                                    carid_list.add(ident);
                              //           Toast.makeText(AddCarInfoScreen.this, ident, Toast.LENGTH_SHORT).show();
                                    cars_list.add(car_brand);
                                }*/
                             //   manufacturer_list.add("select manufacturer");
                                for (int i = 0; i < manufact.length(); i++) {

                                    JSONObject c = manufact.getJSONObject(i);

                                    String car_manufacturer = c.getString("company");
                                    String car_manufactureid = c.getString("id");
                      //              Toast.makeText(AddCarInfoScreen.this, car_manufacturer, Toast.LENGTH_SHORT).show();
                                    manufacture_id.add(car_manufactureid);
                             //       Toast.makeText(AddCarInfoScreen.this, car_manufactureid, Toast.LENGTH_SHORT).show();
                                    //     Toast.makeText(UpdateUserProfile.this, occupation, Toast.LENGTH_SHORT).show();
                                    manufacturer_list.add(car_manufacturer);
                                }
                               // manufacturer_list.sort();
                          /*      for(String counter: manufacturer_list){
                                    System.out.println(counter);
                                }*/

                 /*               Collections.sort(manufacturer_list);

                                for(String counter: manufacture_id){
                                    System.out.println(counter);
                                }

                                Collections.sort(manufacture_id);*/

                  //              spinnerDialog_carmanufact=new SpinnerDialog(AddCarInfoScreen.this,manufacturer_list,"Select or Search Car Manufacturer",R.style.DialogAnimations_SmileWindow,getResources().getString(R.string.cancel));// With 	Animation


                          /*      ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(AddCarInfo_carbrand_SP.getContext(),
                                        R.layout.spinner_textview_layout_with_icon,R.id.spinner_dropdown_tv_icon,cars_list);

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddCarInfoScreen.this,
                                        R.layout.spinner_white_text,manufacturer_list);*/

                               /* spinnerDialog.setCancellable(true); // for cancellable
                                spinnerDialog.setShowKeyboard(false);// for open keyboard by default

                             //   AddCarInfo_carbrand_SP.setAdapter(adapter);
                    //            AddCarInfo_carmanufact_SP.setAdapter(adapter2);

                                for(String counter: manufacturer_list){
                                    System.out.println(counter);
                                    Log.d("data ",counter);
                                }*/

                            }
                            else
                            {
                                Toast.makeText(AddCarInfoScreen.this, R.string.no_data_error, Toast.LENGTH_SHORT).show();
                            }

                        } catch (final JSONException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    /*Toast.makeText(AddCarInfoScreen.this,
                                            "Json parsing error: " + e.getMessage(),
                                            Toast.LENGTH_LONG).show();*/
                                }
                            });
                        }
//                      Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();


                        //      Toast.makeText(Signin.this, response, Toast.LENGTH_SHORT).show();


                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   progressDialog.dismiss();\
                        error.printStackTrace();
                        progressDialog.dismiss();
                    //    Toast.makeText(AddCarInfoScreen.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {


        /*
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("HTTP_AUTHORIZATION", "Bearer " + token);
                return headers;
            }*/




            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }

        };
        int socketTimeout = 10000; // 10 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        RequestQueue requestQueue = Volley.newRequestQueue(AddCarInfoScreen.this);

        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }







    public void get_specific_cars(String manufact_id_str)
    {
        cars_list.clear();
        //    Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
        progressDialog.show();
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.specific_car,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                       //      Toast.makeText(AddCarInfoScreen.this, response, Toast.LENGTH_SHORT).show();
                        //     Toast.makeText(AddCarInfoScreen.this, response, Toast.LENGTH_SHORT).show();
                        //  Log.d("HTTP_AUTHORIZATION",token);
                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");



                       //        Toast.makeText(AddCarInfoScreen.this, response, Toast.LENGTH_SHORT).show();

                            if(message.equals("success")) {
                      //          carid_list.add("select car brand");


                                JSONArray cars  = jsonObj.getJSONArray("cars");
                               // JSONArray manufact  = jsonObj.getJSONArray("manufacturer");

                                //      manufacture_id.add("select_car");
                                for (int i = 0; i < cars.length(); i++)
                                {
                                    JSONObject c = cars.getJSONObject(i);

                                    String car_brand = c.getString("name");
                                    String ident = c.getString("id");
                                    carid_list.add(ident);
                                    //           Toast.makeText(AddCarInfoScreen.this, ident, Toast.LENGTH_SHORT).show();
                                    cars_list.add(car_brand);
                                }
                        //        spinnerDialog=new SpinnerDialog(AddCarInfoScreen.this,cars_list,"Select or Search Car Brand",R.style.DialogAnimations_SmileWindow,getResources().getString(R.string.cancel));// With 	Animation

                             /*   for (int i = 0; i < manufact.length(); i++) {

                                    JSONObject c = manufact.getJSONObject(i);

                                    String car_manufacturer = c.getString("company");
                                    String car_manufactureid = c.getString("id");
                                    manufacture_id.add(car_manufactureid);
                                    //       Toast.makeText(AddCarInfoScreen.this, car_manufactureid, Toast.LENGTH_SHORT).show();
                                    //     Toast.makeText(UpdateUserProfile.this, occupation, Toast.LENGTH_SHORT).show();
                                    manufacturer_list.add(car_manufacturer);
                                }
                                // manufacturer_list.sort();
                                for(String counter: manufacturer_list){
                                    System.out.println(counter);
                                }

                                Collections.sort(manufacturer_list);


                          *//*      ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(AddCarInfo_carbrand_SP.getContext(),
                                        R.layout.spinner_textview_layout_with_icon,R.id.spinner_dropdown_tv_icon,cars_list);

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddCarInfoScreen.this,
                                        R.layout.spinner_white_text,manufacturer_list);*//*

                                spinnerDialog.setCancellable(true); // for cancellable
                                spinnerDialog.setShowKeyboard(false);// for open keyboard by default

                                //   AddCarInfo_carbrand_SP.setAdapter(adapter);
                                //            AddCarInfo_carmanufact_SP.setAdapter(adapter2);

                                for(String counter: manufacturer_list){
                                    System.out.println(counter);
                                    Log.d("data ",counter);
                                }*/

                            }
                            else
                            {
                                Toast.makeText(AddCarInfoScreen.this, R.string.no_data_error, Toast.LENGTH_SHORT).show();
                            }

                        } catch (final JSONException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                   /* Toast.makeText(AddCarInfoScreen.this,
                                            "Json parsing error: " + e.getMessage(),
                                            Toast.LENGTH_LONG).show();*/
                                }
                            });
                        }
//                      Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();


                        //      Toast.makeText(Signin.this, response, Toast.LENGTH_SHORT).show();


                    }


                },
                new Response.ErrorListener() {



                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   progressDialog.dismiss();\
                        error.printStackTrace();
                        progressDialog.dismiss();
                        //Toast.makeText(AddCarInfoScreen.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {


        /*
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("HTTP_AUTHORIZATION", "Bearer " + token);
                return headers;
            }*/


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("m_id", manufact_id_str);

                return parameters;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }

        };
        int socketTimeout = 10000; // 10 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        RequestQueue requestQueue = Volley.newRequestQueue(AddCarInfoScreen.this);

        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }












    public void Add_User_Car(final String car_idnumber, String car_odometer, final String carnumberplate, String daily_mileage, String year_of_manufacture, String engine_type)
    {

    /*    Toast.makeText(this, car_odometer, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, daily_mileage, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, year_of_manufacture, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, engine_type, Toast.LENGTH_SHORT).show();*/


        //  Toast.makeText(this, refreshedToken, Toast.LENGTH_SHORT).show();

        Toast.makeText(this, car_idnumber, Toast.LENGTH_SHORT).show();


        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ADD_CARS_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();

                      //  Toast.makeText(AddCarInfoScreen.this, response, Toast.LENGTH_SHORT).show();

                        if(response.contains("Duplicate"))
                        {
                            Toast.makeText(AddCarInfoScreen.this,  getResources().getString(R.string.caralreadyexists), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                        //    Toast.makeText(AddCarInfoScreen.this, get_intentval, Toast.LENGTH_SHORT).show();
                            if(get_intentval.equals("1"))
                            {
                                Intent intent = new Intent(AddCarInfoScreen.this, OIl_Recommendation.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Intent intent = new Intent(AddCarInfoScreen.this,AddCarScreen.class);
                                startActivity(intent);
                                finish();
                            }

                        }

              // Toast.makeText(Signup.this, response, Toast.LENGTH_SHORT).show();



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   progressDialog.dismiss();
                        Toast.makeText(AddCarInfoScreen.this, error.toString(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                })
        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("userId", uid);
                parameters.put("car_id", car_idnumber);
                parameters.put("odometer", car_odometer);
                parameters.put("carNumber", carnumberplate.toUpperCase());
                parameters.put("daily_mileage", daily_mileage);
                parameters.put("year_of_manufacture", year_of_manufacture);
                parameters.put("engine_type", engine_type);

                return parameters;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }

        };
        int socketTimeout = 10000; // 10 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        RequestQueue requestQueue = Volley.newRequestQueue(AddCarInfoScreen.this);

        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }


}