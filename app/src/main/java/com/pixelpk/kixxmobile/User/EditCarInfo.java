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
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.AddCar.AddCarScreen;
import com.pixelpk.kixxmobile.User.ModelClasses.CarRecommendationlistModelClass;
import com.pixelpk.kixxmobile.User.ModelClasses.UseraddedcaridentityClass;
import com.pixelpk.kixxmobile.User.OilRecommendation.OIl_Recommendation;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class EditCarInfo extends AppCompatActivity {

    EditText AddCarInfo_carnumber_ET,AddCarInfo_carodometer_ET,AddCarInfo_Mileage_ET,AddCarInfo_dailycarmileage_ET;
    Spinner AddCarInfo_carmanufact_SP,AddCarInfo_carbrand_SP;
    Button AddCarInfo_addcar_Btn;

    ProgressDialog progressDialog;

    ArrayList<String> cars_list,manufacturer_list,manufacture_id;
    ArrayList<UseraddedcaridentityClass> caridentity,car_brand_arraylist;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String token,car_idnumber="null";

    String uid,manufact_id;
    LinearLayout AddCarInfo_backarrow;
    ImageView EditCarInfo_backarrow_IV;

    SpinnerDialog spinnerDialog;
    SpinnerDialog spinnerDialog_carmanufact;

    LinearLayout AddCarInfo_carbrand_LL,AddCarInfo_carmanufact_LL;

    TextView AddCar_carbrand_seletion_TV;
    TextView AddCarInfo_carmanufact_TV,AddCar_modelyear_TV;

    List<String> carid_list;
    String rtl;

    String carnumber,carmanufacturer,carbrand;
    TextView EditCarInfo_carbrand_seletion_TV,EditCarInfo_carmanufact_TV;

    ArrayList<String> years_list,enginetype_list;

    String CarId = "null",get_intent_val="";

    String car_odometer,daily_mileage;

    SpinnerDialog spinnerDialog_year;
    SpinnerDialog spinnerDialog_enginetype;

    LinearLayout AddCarInfo_yearofmanufacturing_LL,AddCarInfo_enginetype_LL;


    TextView AddCar_enginetype_TV;


    String Year_manufacture="";
    String engine_type="";
    String preselectedManufacture_id="";
    String preselectedbrand_id="";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car_info);

        InitializeViews();


        AddCarInfo_addcar_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(car_idnumber.equals("null"))
                {
                    //Toast.makeText(AddCarInfoScreen.this, "Please Check your internet Connection and try again", Toast.LENGTH_SHORT).show();
                    Toast.makeText(EditCarInfo.this, getResources().getString(R.string.select_car_model), Toast.LENGTH_SHORT).show();
                }
                else if(AddCarInfo_carnumber_ET.getText().toString().equals(""))
                {
                    Toast.makeText(EditCarInfo.this, getResources().getString(R.string.entercarnumber), Toast.LENGTH_SHORT).show();
                }
                else if (AddCarInfo_carnumber_ET.getText().toString().contains(" "))
                {
                    AddCarInfo_carnumber_ET.setError(getResources().getString(R.string.incorrect_data));
                }

                else
                {
                    String val = AddCarInfo_carnumber_ET.getText().toString().toUpperCase();
                    //    Toast.makeText(AddCarInfoScreen.this, val, Toast.LENGTH_SHORT).show();
                    /*Toast.makeText(EditCarInfo.this, car_idnumber, Toast.LENGTH_SHORT).show();
                    Toast.makeText(EditCarInfo.this, AddCarInfo_Mileage_ET.getText().toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(EditCarInfo.this, AddCarInfo_carnumber_ET.getText().toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(EditCarInfo.this, AddCarInfo_dailycarmileage_ET.getText().toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(EditCarInfo.this, Year_manufacture, Toast.LENGTH_SHORT).show();
                    Toast.makeText(EditCarInfo.this, engine_type, Toast.LENGTH_SHORT).show();*/

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
                        editor.putString("shared_daily_mileage",daily_mileage).apply();
                    }

                    if( Year_manufacture == null || Year_manufacture.equals("") )
                    {
                        Year_manufacture = "";
                    }

                    if( engine_type == null || engine_type.equals("") )
                    {
                        engine_type = "";
                    }

                    Upadate_User_Car(CarId,car_idnumber,AddCarInfo_Mileage_ET.getText().toString(),AddCarInfo_carnumber_ET.getText().toString(),AddCarInfo_dailycarmileage_ET.getText().toString(),Year_manufacture,engine_type);

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

                if(get_intent_val.equals("1"))
                {
                    Intent intent = new Intent(EditCarInfo.this,AddCarScreen.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(EditCarInfo.this, OIl_Recommendation.class);
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


                AddCar_carbrand_seletion_TV.setText("");
                car_idnumber = "null";

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



        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                //  Toast.makeText(AddCarInfoScreen.this, item, Toast.LENGTH_SHORT).show();
                //car_idnumber = String.valueOf(position);
                car_idnumber = carid_list.get(position);

            //    Toast.makeText(EditCarInfo.this, car_idnumber, Toast.LENGTH_SHORT).show();
              /*  Toast.makeText(EditCarInfo.this, car_idnumber, Toast.LENGTH_SHORT).show();
                preselectedbrand_id = car_idnumber;*/
              //  get_specific_cars(manufact_id);
                //     Toast.makeText(AddCarInfoScreen.this, car_idnumber, Toast.LENGTH_SHORT).show();
                //   Toast.makeText(AddCarInfoScreen.this, String.valueOf(car_idnumber), Toast.LENGTH_SHORT).show();

//                AddCarInfo_carmanufact_TV.setText("");

                AddCar_carbrand_seletion_TV.setText(item);
                // selectedItems.setText(item + " Position: " + position);
            }
        });

        AddCarInfo_carbrand_LL.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //     ((TextView) findViewById(R.id.spinner_dropdown_tv_icon)).setTextColor(getResources().getColor(R.color.white));
                spinnerDialog.showSpinerDialog();
            }
        });

        spinnerDialog_year.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {

                AddCar_modelyear_TV.setText(item);
                Year_manufacture = item;
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

        spinnerDialog_enginetype.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {

                AddCar_enginetype_TV.setText(item);
                engine_type = String.valueOf(position+1);

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

    public void InitializeViews() {

        AddCarInfo_carnumber_ET = findViewById(R.id.EditCarInfo_carnumber_ET);
        AddCarInfo_carodometer_ET = findViewById(R.id.EditCarInfo_carodometer_ET);
        //  AddCarInfo_carmanufact_SP = findViewById(R.id.AddCarInfo_carmanufact_SP);
        //   AddCarInfo_carbrand_SP = findViewById(R.id.AddCarInfo_carbrand_SP);
        AddCarInfo_backarrow = findViewById(R.id.EditCarInfo_backarrow);
        AddCarInfo_carmanufact_LL = findViewById(R.id.EditCarInfo_carmanufact_LL);
        AddCarInfo_carbrand_LL = findViewById(R.id.EditCarInfo_carbrand_LL);
        AddCarInfo_Mileage_ET = findViewById(R.id.EditCarInfo_Mileage_ET);

        AddCarInfo_addcar_Btn = findViewById(R.id.EditCarInfo_addcar_Btn);
        EditCarInfo_carbrand_seletion_TV = findViewById(R.id.EditCarInfo_carbrand_seletion_TV);
        EditCarInfo_carmanufact_TV = findViewById(R.id.EditCarInfo_carmanufact_TV);
        AddCar_modelyear_TV = findViewById(R.id.EditCarInfo_modelyear_TV);
        AddCarInfo_dailycarmileage_ET = findViewById(R.id.EditCarInfo_dailycarmileage_ET);
        AddCar_enginetype_TV = findViewById(R.id.EditCarInfo_enginetype_TV);
        AddCarInfo_enginetype_LL = findViewById(R.id.EditCarInfo_enginetype_LL);
        AddCarInfo_yearofmanufacturing_LL = findViewById(R.id.EditCarInfo_yearofmanufacturing_LL);
        EditCarInfo_backarrow_IV = findViewById(R.id.EditCarInfo_backarrow_IV);

        progressDialog = new ProgressDialog(EditCarInfo.this);


        cars_list = new ArrayList<>();
        manufacturer_list = new ArrayList<>();
        caridentity = new ArrayList<>();
        car_brand_arraylist = new ArrayList<>();
        manufacture_id = new ArrayList<>();
        years_list = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.yearlist)));
        enginetype_list = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.enginetype)));

        //    cars_list.add(getResources().getString(R.string.select_car_brand));
        manufacturer_list.add(getResources().getString(R.string.select_car_manufacturer));

        sharedPreferences = getSharedPreferences("Shared", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE, "0");
        //Toast.makeText(this, token, Toast.LENGTH_SHORT).show();

        if (rtl.equals("1")) {

            EditCarInfo_backarrow_IV.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24_rwhite);

        } else {
        }


        uid = sharedPreferences.getString(Shared.loggedIn_user_id, "0");
        carid_list = new ArrayList<>();
        // Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();

        token = sharedPreferences.getString(Shared.loggedIn_jwt, "0");
        //  carid_list.add("select car brand");
        manufacture_id.add("select manufacturer");

        spinnerDialog = new SpinnerDialog(EditCarInfo.this, cars_list, getResources().getString(R.string.select_search_car_model), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.cancel));// With 	Animation
        spinnerDialog_carmanufact = new SpinnerDialog(EditCarInfo.this, manufacturer_list, getResources().getString(R.string.select_search_car_brand), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.cancel));// With 	Animation
        spinnerDialog_year = new SpinnerDialog(EditCarInfo.this, years_list, getResources().getString(R.string.select_search_car_model_year), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.cancel));// With 	Animation
        spinnerDialog_enginetype = new SpinnerDialog(EditCarInfo.this, enginetype_list, getResources().getString(R.string.select_search_car_model_year), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.cancel));// With 	Animation

        get_cars_data(token);



     /*   AddCarInfo_carmanufact_SP.setEnabled(false);
        AddCarInfo_carmanufact_SP.setClickable(false);
*/
        AddCarInfo_carmanufact_TV = findViewById(R.id.EditCarInfo_carmanufact_TV);

        AddCar_carbrand_seletion_TV = findViewById(R.id.EditCarInfo_carbrand_seletion_TV);

        if(getIntent().getStringExtra("intent_val").equals("1"))
        {
            get_intent_val = "1";
        }
        else
        {
            get_intent_val = "2";
        }



        if (!getIntent().getStringExtra("Carnumber").equals("null")) {
            if(getIntent().getStringExtra("Carnumber").equals("0"))
            {
                AddCarInfo_carnumber_ET.getText().clear();
            }
            else
            {
                AddCarInfo_carnumber_ET.setText(getIntent().getStringExtra("Carnumber"));
            }

        }
        if (!getIntent().getStringExtra("Carbrand").equals("null"))
        {
            EditCarInfo_carbrand_seletion_TV.setText(getIntent().getStringExtra("Carbrand"));
        }

        if (!getIntent().getStringExtra("Carmanufacturer").equals("null"))
        {
            EditCarInfo_carmanufact_TV.setText(getIntent().getStringExtra("Carmanufacturer"));
        }
        /*if(getIntent().getStringExtra("CarModel")!=null)
        {
            AddCar_modelyear_TV.setText(getIntent().getStringExtra("CarModel"));
        }*/
        if (!getIntent().getStringExtra("year_of_manufacture").equals("null")) {

            if(getIntent().getStringExtra("year_of_manufacture").equals("0"))
            {
                AddCar_modelyear_TV.setText(getResources().getString(R.string.yearofmanufacturing));
            }
            else
            {
                AddCar_modelyear_TV.setText(getIntent().getStringExtra("year_of_manufacture"));
                Year_manufacture = getIntent().getStringExtra("year_of_manufacture");
            }

        }
        if (!getIntent().getStringExtra("odometer").equals("null")) {

            if(getIntent().getStringExtra("odometer").equals("0"))
            {
                AddCarInfo_Mileage_ET.getText().clear();
            }
            else
            {
                AddCarInfo_Mileage_ET.setText(getIntent().getStringExtra("odometer"));
            }

        }
        if (!getIntent().getStringExtra("enginetype").equals("null")) {

            if(getIntent().getStringExtra("enginetype").equals("0"))
            {
                AddCar_enginetype_TV.setText(getResources().getString(R.string.selectcarenginetype));
            }
            else
            {
                if(getIntent().getStringExtra("enginetype").equals("1"))
                {
                    AddCar_enginetype_TV.setText("Gasoline");
                }
                else
                {
                    AddCar_enginetype_TV.setText("Diesel");
                }
            }



            engine_type = getIntent().getStringExtra("enginetype");
        }
        if (!getIntent().getStringExtra("dailymileage").equals("null")) {
            if(getIntent().getStringExtra("dailymileage").equals("0"))
            {
                AddCarInfo_dailycarmileage_ET.getText().clear();
            }
            else
            {
                AddCarInfo_dailycarmileage_ET.setText(getIntent().getStringExtra("dailymileage"));
            }

        }
        if (!getIntent().getStringExtra("CarId").equals("null")) {
            CarId = getIntent().getStringExtra("CarId");
            // Toast.makeText(this, CarId, Toast.LENGTH_SHORT).show();
        }
        if (!getIntent().getStringExtra("cid").equals("null")) {
            car_idnumber = getIntent().getStringExtra("cid");
        //    Toast.makeText(this, getIntent().getStringExtra("cid"), Toast.LENGTH_SHORT).show();
            preselectedbrand_id = car_idnumber;
        }


        for(UseraddedcaridentityClass data : caridentity)
        {
            String name = data.getName();

            if(getIntent().getStringExtra("Carmanufacturer").equals(name))
            {
            //    Toast.makeText(this, getIntent().getStringExtra("Carmanufacturer"), Toast.LENGTH_SHORT).show();
                preselectedManufacture_id = data.getId();
            //    Toast.makeText(this, preselectedManufacture_id, Toast.LENGTH_SHORT).show();
            }
        }


    /*    for(UseraddedcaridentityClass data : car_brand_arraylist)
        {
            String name = data.getName();

            if(getIntent().getStringExtra("Carmanufacturer").equals(name))
            {
            //    Toast.makeText(this, getIntent().getStringExtra("Carmanufacturer"), Toast.LENGTH_SHORT).show();
                preselectedbrand_id = data.getId();
            //    Toast.makeText(this, preselectedManufacture_id, Toast.LENGTH_SHORT).show();
            }
        }
*/







    }
    public void get_cars_data(final String token)
    {
        Log.d("token",token);

        //    Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
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



                                      JSONArray cars  = jsonObj.getJSONArray("cars");
                                JSONArray manufact  = jsonObj.getJSONArray("manufacturer");
                             //   Log.d("getcarresponse",c.toString());
                                //    Toast.makeText(AddCarInfoScreen.this, manufact.toString(), Toast.LENGTH_SHORT).show();
                        //        Toast.makeText(EditCarInfo.this, cars.toString(), Toast.LENGTH_SHORT).show();
                                //      manufacture_id.add("select_car");
                                for (int i = 0; i < cars.length(); i++) {

                                    JSONObject c = cars.getJSONObject(i);

                                    String car_brand = c.getString("name");
                                    String ident = c.getString("id");

                                    UseraddedcaridentityClass useraddedcaridentityClass = new UseraddedcaridentityClass(car_brand,ident);
                                    car_brand_arraylist.add(useraddedcaridentityClass);
                                /*    carid_list.add(ident);
                              //           Toast.makeText(AddCarInfoScreen.this, ident, Toast.LENGTH_SHORT).show();
                                    cars_list.add(car_brand);*/
                                }
                                //   manufacturer_list.add("select manufacturer");
                                for (int i = 0; i < manufact.length(); i++) {

                                    JSONObject c = manufact.getJSONObject(i);

                                    String car_manufacturer = c.getString("company");
                                    String car_manufactureid = c.getString("id");
                                    //              Toast.makeText(AddCarInfoScreen.this, car_manufacturer, Toast.LENGTH_SHORT).show();

                                    UseraddedcaridentityClass useraddedcaridentityClass = new UseraddedcaridentityClass(car_manufacturer,car_manufactureid);
                                    caridentity.add(useraddedcaridentityClass);
                              //      car_brand_arraylist.add(useraddedcaridentityClass);


                                    manufacture_id.add(car_manufactureid);
                                    //       Toast.makeText(AddCarInfoScreen.this, car_manufactureid, Toast.LENGTH_SHORT).show();
                                    //     Toast.makeText(UpdateUserProfile.this, occupation, Toast.LENGTH_SHORT).show();
                                    manufacturer_list.add(car_manufacturer);
                                }


                                for(UseraddedcaridentityClass data : caridentity)
                                {
                                    String name = data.getName();

                                    if(getIntent().getStringExtra("Carmanufacturer").equals(name))
                                    {
                                    //    Toast.makeText(EditCarInfo.this, getIntent().getStringExtra("Carmanufacturer"), Toast.LENGTH_SHORT).show();
                                        preselectedManufacture_id = data.getId();
                                        get_specific_cars(preselectedManufacture_id);
                                    //    Toast.makeText(EditCarInfo.this, preselectedManufacture_id, Toast.LENGTH_SHORT).show();
                                    }
                                }

                                /*for(UseraddedcaridentityClass data : caridentity)
                                {
                                    String name = data.getName();

                                    if(getIntent().getStringExtra("Carmanufacturer").equals(name))
                                    {
                                    //    Toast.makeText(EditCarInfo.this, getIntent().getStringExtra("Carmanufacturer"), Toast.LENGTH_SHORT).show();
                                        preselectedbrand_id = data.getId();
                                        get_specific_cars(preselectedbrand_id);
                                    //    Toast.makeText(EditCarInfo.this, preselectedManufacture_id, Toast.LENGTH_SHORT).show();
                                    }
                                }*/

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
                            //    Toast.makeText(EditCarInfo.this, "Error", Toast.LENGTH_SHORT).show();
                                Toast.makeText(EditCarInfo.this, R.string.no_data_error, Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(EditCarInfo.this);

        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }







    public void get_specific_cars(String manufact_id_str)
    {
        cars_list.clear();
        carid_list.clear();
        //    Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.specific_car,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
            //            Toast.makeText(EditCarInfo.this, response, Toast.LENGTH_SHORT).show();
                        //      Toast.makeText(AddCarInfoScreen.this, response, Toast.LENGTH_SHORT).show();
                        //     Toast.makeText(AddCarInfoScreen.this, response, Toast.LENGTH_SHORT).show();
                        //  Log.d("HTTP_AUTHORIZATION",token);
                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");


                                 //   Toast.makeText(AddCarInfoScreen.this, response, Toast.LENGTH_SHORT).show();

                            if(message.equals("success")) {
                                //          carid_list.add("select car brand");


                                JSONArray cars  = jsonObj.getJSONArray("cars");
                                // JSONArray manufact  = jsonObj.getJSONArray("manufacturer");


                                //      manufacture_id.add("select_car");
                                for (int i = 0; i < cars.length(); i++) {

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
                                Toast.makeText(EditCarInfo.this, R.string.no_data_error, Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(EditCarInfo.this);

        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }



    public void Upadate_User_Car(final String user_car_id,final String car_idnumber, final String car_odometer, final String carnumberplate,final String daily_mileage,final String year_of_manufacture,final String engine_type)
    {

   //       Toast.makeText(this, user_car_id, Toast.LENGTH_SHORT).show();
    //      Toast.makeText(this, car_idnumber, Toast.LENGTH_SHORT).show();

        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.UPDATE_CARS_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();

//                              Toast.makeText(EditCarInfo.this, response, Toast.LENGTH_SHORT).show();

                        if(response.contains("Duplicate"))
                        {
                            Toast.makeText(EditCarInfo.this,  getResources().getString(R.string.caralreadyexists), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if(get_intent_val.equals("1"))
                            {
                                Intent intent = new Intent(EditCarInfo.this,AddCarScreen.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Intent intent = new Intent(EditCarInfo.this, OIl_Recommendation.class);
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
                            Toast.makeText(EditCarInfo.this, error.toString(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                })
        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("user_car_id", user_car_id);
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
        RequestQueue requestQueue = Volley.newRequestQueue(EditCarInfo.this);

        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }


}