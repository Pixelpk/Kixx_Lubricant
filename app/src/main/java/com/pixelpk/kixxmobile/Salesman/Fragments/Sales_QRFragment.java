package com.pixelpk.kixxmobile.Salesman.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.Salesman.QR.QRScanner;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.AddCar.AddCarScreen;
import com.pixelpk.kixxmobile.User.AddCarInfoScreen;
import com.pixelpk.kixxmobile.User.HomeScreen;
import com.pixelpk.kixxmobile.User.ModelClasses.AddCarList;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
import com.pixelpk.kixxmobile.User.adapters.AddCarAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

import static android.content.Context.MODE_PRIVATE;
// update

public class Sales_QRFragment extends Fragment {

    EditText SalesQR_userid_txt,SalesQR_carnumber_SP;
    Button SalesQR_search_btn,SalesQR_next_Btn,SalesQR_addcar_Btn;
    String uid;

    Spinner SalesQR_CarSelect_SP;
   // Spinner SalesQR_SelectCarBrand_SP,SalesQR_SelectCarManufacturer_SP;

    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    List<String> user_cars_list;
    List<String> user_car_id,car_list_id;

    String manufact,brandselect;

    String token;

    String pre_added_user_cars;

    String car_id;

    ImageView Sales_qr_titlebar_kixxlogo;

    String redeemed_points;

    EditText SalesQR_carcurrentodometer_SP;

    LinearLayout Sales_AddCarInfo_carmanufact_LL,Sales_AddCarInfo_carbrand_LL;

    SpinnerDialog Sales_spinnerDialog;
    SpinnerDialog Sales_spinnerDialog_carmanufact;

    List<String> carid_list;

    String car_idnumber="null";

    ArrayList<String> manufacture_id,cars_list,manufacturer_list;

    TextView Sales_AddCarInfo_carmanufact_TV,Sales_AddCar_carbrand_seletion_TV;
    String manufact_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sales__q_r, container, false);

        InitializeView(view);

        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE, "0");

        if(rtl.equals("1"))
        {
            Sales_qr_titlebar_kixxlogo.setImageResource(R.mipmap.kixx_ar);
           // SalesQR_carnumber_SP.setGravity(Gravity.END);

        }

       // get_cars_data(token);


        SalesQR_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid = SalesQR_userid_txt.getText().toString();

                if(uid.equals(""))
                {
                    SalesQR_userid_txt.setError("Please enter user id");
                }

                else
                {
                    getCarsData(uid);
                }

                InputMethodManager imm =(InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


            }
        });

/*        SalesQR_CarSelect_SP.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                // Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                pre_added_user_cars = String.valueOf(id);
                SalesQR_next_Btn.setVisibility(View.VISIBLE);
                SalesQR_addcar_Btn.setVisibility(View.GONE);
                SalesQR_carnumber_SP.setText("");
               *//* SalesQR_SelectCarBrand_SP.setSelectedIndex(0);
                SalesQR_SelectCarManufacturer_SP.setSelectedIndex(0);*//*

                if(user_cars_list.size()>1) {

                    if(SalesQR_CarSelect_SP.getSelectedIndex() == 0)
                    {

                    }
                    else
                    {
                        car_id = user_car_id.get(position - 1);
                        //  Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                        editor.putString(Shared.Sales_loggedIn_user_carid, car_id).apply();
                    }

                }

            }
        });*/


        SalesQR_CarSelect_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                pre_added_user_cars = String.valueOf(id);
                SalesQR_next_Btn.setVisibility(View.VISIBLE);
                SalesQR_addcar_Btn.setVisibility(View.GONE);
                SalesQR_carnumber_SP.setText("");
               /* SalesQR_SelectCarBrand_SP.setSelectedIndex(0);
                SalesQR_SelectCarManufacturer_SP.setSelectedIndex(0);*/

                if(user_cars_list.size()>1) {

                    if(position == 0)
                    {

                    }
                    else
                    {
                 //       car_id = user_car_id.get(position - 1);
                        car_id = user_car_id.get(position);
                        //  Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                        editor.putString(Shared.Sales_loggedIn_user_carid, car_id).apply();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
     /*   SalesQR_SelectCarBrand_SP.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                // Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
              //  brandselect = String.valueOf(id);
                if(position==0)
                {
                    brandselect = "Select Brand";
                }
                else
                {
                    brandselect = car_list_id.get(position-1);
                }

              //  Toast.makeText(getContext(), brandselect, Toast.LENGTH_SHORT).show();
                SalesQR_next_Btn.setVisibility(View.GONE);
                SalesQR_addcar_Btn.setVisibility(View.VISIBLE);

            }
        });

        SalesQR_SelectCarManufacturer_SP.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                // Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                manufact = String.valueOf(id);
                SalesQR_next_Btn.setVisibility(View.GONE);
                SalesQR_addcar_Btn.setVisibility(View.VISIBLE);

            }
        });*/

        Sales_spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                //  Toast.makeText(AddCarInfoScreen.this, item, Toast.LENGTH_SHORT).show();
                //car_idnumber = String.valueOf(position);
                SalesQR_next_Btn.setVisibility(View.GONE);
                SalesQR_addcar_Btn.setVisibility(View.VISIBLE);
                car_idnumber = carid_list.get(position);

//                Toast.makeText(getContext(), car_idnumber, Toast.LENGTH_SHORT).show();

                //     Toast.makeText(AddCarInfoScreen.this, car_idnumber, Toast.LENGTH_SHORT).show();
                //   Toast.makeText(AddCarInfoScreen.this, String.valueOf(car_idnumber), Toast.LENGTH_SHORT).show();
                Sales_AddCar_carbrand_seletion_TV.setText(item);
                // selectedItems.setText(item + " Position: " + position);
            }
        });

        Sales_AddCarInfo_carbrand_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     ((TextView) findViewById(R.id.spinner_dropdown_tv_icon)).setTextColor(getResources().getColor(R.color.white));
                Sales_spinnerDialog.showSpinerDialog();
            }
        });


        Sales_spinnerDialog_carmanufact.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                //  Toast.makeText(AddCarInfoScreen.this, item, Toast.LENGTH_SHORT).show();
              /*  car_idnumber = String.valueOf(position);
                Toast.makeText(AddCarInfoScreen.this, String.valueOf(car_idnumber), Toast.LENGTH_SHORT).show();
              */
                SalesQR_next_Btn.setVisibility(View.GONE);
                SalesQR_addcar_Btn.setVisibility(View.VISIBLE);
                manufact_id = manufacture_id.get(position);

                //        Toast.makeText(AddCarInfoScreen.this, manufact_id, Toast.LENGTH_SHORT).show();
                get_specific_cars(manufact_id);
                Sales_AddCarInfo_carmanufact_TV.setText(item);
                // selectedItems.setText(item + " Position: " + position);
            }
        });

        Sales_AddCarInfo_carmanufact_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     ((TextView) findViewById(R.id.spinner_dropdown_tv_icon)).setTextColor(getResources().getColor(R.color.white));
                Sales_spinnerDialog_carmanufact.showSpinerDialog();
            }
        });


        /*SalesQR_SelectCarManufacturer_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                manufact = String.valueOf(id);
                SalesQR_next_Btn.setVisibility(View.GONE);
                SalesQR_addcar_Btn.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        SalesQR_SelectCarManufacturer_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                //  brandselect = String.valueOf(id);
                if(position==0)
                {
                    brandselect = "Select Brand";
                }
                else
                {
                    brandselect = car_list_id.get(position-1);
                }

                //  Toast.makeText(getContext(), brandselect, Toast.LENGTH_SHORT).show();
                SalesQR_next_Btn.setVisibility(View.GONE);
                SalesQR_addcar_Btn.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        SalesQR_next_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(SalesQR_userid_txt.getText().toString().equals(""))
                {
                   // Toast.makeText(getContext(), getResources().getString(R.string.enter_id), Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(getContext())
                            .setMessage(getResources().getString(R.string.enter_id))
                            .setCancelable(false)
                            .setNegativeButton(getResources().getString(R.string.ok), null)
                            .show();
                }
                else if(pre_added_user_cars.equals("Select Car"))
                {
                   // Toast.makeText(getContext(), getResources().getString(R.string.select_your_car), Toast.LENGTH_SHORT).show();

                    new AlertDialog.Builder(getContext())
                            .setMessage(getResources().getString(R.string.select_your_car))
                            .setCancelable(false)
                            .setNegativeButton(getResources().getString(R.string.ok), null)
                            .show();
                }
                else if(pre_added_user_cars.equals("0"))
                {
                  //  Toast.makeText(getContext(), getResources().getString(R.string.select_your_car), Toast.LENGTH_SHORT).show();

                    new AlertDialog.Builder(getContext())
                            .setMessage(getResources().getString(R.string.select_your_car))
                            .setCancelable(false)
                            .setNegativeButton(getResources().getString(R.string.ok), null)
                            .show();
                }
                else {

                    get_car_oil_change_data(car_id);


                }


                /*Intent intent = new Intent(getContext(), QRScanner.class);
                startActivity(intent);*/
            }
        });


        SalesQR_addcar_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(SalesQR_carnumber_SP.getText().toString().equals(""))
                {
                 //   Toast.makeText(getContext(), getResources().getString(R.string.car_number), Toast.LENGTH_SHORT).show();

                    new AlertDialog.Builder(getContext())
                            .setMessage(getResources().getString(R.string.car_number))
                            .setCancelable(false)
                            .setNegativeButton(getResources().getString(R.string.ok), null)
                            .show();
                }
                else if(SalesQR_carcurrentodometer_SP.getText().toString().equals(""))
                {
                    //Toast.makeText(getContext(), getResources().getString(R.string.please_add_current_odometer), Toast.LENGTH_SHORT).show();

                    new AlertDialog.Builder(getContext())
                            .setMessage(getResources().getString(R.string.please_add_current_odometer))
                            .setCancelable(false)
                            .setNegativeButton(getResources().getString(R.string.ok), null)
                            .show();
                }
                else if (car_idnumber.equals("null"))
                {
               //     Toast.makeText(getContext(), getResources().getString(R.string.select_car), Toast.LENGTH_SHORT).show();

                    new AlertDialog.Builder(getContext())
                            .setMessage(getResources().getString(R.string.select_car))
                            .setCancelable(false)
                            .setNegativeButton(getResources().getString(R.string.ok), null)
                            .show();
                }
                else  if(SalesQR_carnumber_SP.getText().toString().contains(" "))
                {
                   // Toast.makeText(getContext(), getResources().getString(R.string.incorrect_data), Toast.LENGTH_SHORT).show();

                    new AlertDialog.Builder(getContext())
                            .setMessage(getResources().getString(R.string.incorrect_data))
                            .setCancelable(false)
                            .setNegativeButton(getResources().getString(R.string.ok), null)
                            .show();

                }
                else
                {
                    Add_User_Car(car_idnumber, SalesQR_carnumber_SP.getText().toString(),SalesQR_carcurrentodometer_SP.getText().toString());
                }

            }
        });


        SalesQR_carnumber_SP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalesQR_next_Btn.setVisibility(View.GONE);
                SalesQR_addcar_Btn.setVisibility(View.VISIBLE);
          //      SalesQR_CarSelect_SP.setSelectedIndex(0);
//                SalesQR_CarSelect_SP.setSelection(0);

            }
        });

        return view;
    }

    private void InitializeView(View view) {

        SalesQR_userid_txt = view.findViewById(R.id.SalesQR_userid_txt);
        SalesQR_search_btn = view.findViewById(R.id.SalesQR_search_btn);
        Sales_qr_titlebar_kixxlogo = view.findViewById(R.id.Sales_qr_titlebar_kixxlogo);
        SalesQR_carcurrentodometer_SP = view.findViewById(R.id.SalesQR_carcurrentodometer_SP);
        SalesQR_CarSelect_SP = view.findViewById(R.id.SalesQR_CarSelect_SP);

        SalesQR_carnumber_SP = view.findViewById(R.id.SalesQR_carnumber_SP);

        Sales_AddCarInfo_carmanufact_TV = view.findViewById(R.id.Sales_AddCarInfo_carmanufact_TV);
        Sales_AddCar_carbrand_seletion_TV = view.findViewById(R.id.Sales_AddCar_carbrand_seletion_TV);

        carid_list = new ArrayList<>();
        manufacture_id = new ArrayList<>();
 /*       SalesQR_SelectCarBrand_SP = view.findViewById(R.id.SalesQR_SelectCarBrand_SP);

        SalesQR_SelectCarManufacturer_SP = view.findViewById(R.id.SalesQR_SelectCarManufacturer_SP);*/

        manufacture_id.add("select manufacturer");
        Sales_AddCarInfo_carmanufact_LL = view.findViewById(R.id.Sales_AddCarInfo_carmanufact_LL);
        Sales_AddCarInfo_carbrand_LL = view.findViewById(R.id.Sales_AddCarInfo_carbrand_LL);
        SalesQR_next_Btn = view.findViewById(R.id.SalesQR_next_Btn);

        progressDialog = new ProgressDialog(getContext());

        sharedPreferences = getContext().getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();


        token = sharedPreferences.getString(Shared.sales_loggedIn_jwt,"0");
   //     Toast.makeText(getContext(), token, Toast.LENGTH_SHORT).show();

        cars_list = new ArrayList<>();
        manufacturer_list = new ArrayList<>();
        user_cars_list = new ArrayList<>();
        user_car_id = new ArrayList<>();
        car_list_id = new ArrayList<>();



    //    cars_list.add(getResources().getString(R.string.select_car_brand));
        manufacturer_list.add(getResources().getString(R.string.select_car_manufacturer));
        user_cars_list.add(getResources().getString(R.string.select_car));


        ArrayAdapter<String> user_cars_list_adapter = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_white_text,user_cars_list);

        user_cars_list_adapter.setDropDownViewResource(R.layout.spinner_style);

        SalesQR_CarSelect_SP.setAdapter(user_cars_list_adapter);

 //       SalesQR_CarSelect_SP.setItems(user_cars_list);

        SalesQR_addcar_Btn = view.findViewById(R.id.SalesQR_addcar_Btn);


        pre_added_user_cars = "Select Car";
        brandselect = "Select Brand";
        manufact = "Select Manufacturer";

      //  editor.putString(Shared.Sales_loggedIn_user_id,"").apply();
        user_car_id.add("select car");
        Sales_spinnerDialog=new SpinnerDialog(getActivity(),cars_list,"Select or Search Car Brand",R.style.DialogAnimations_SmileWindow,getResources().getString(R.string.cancel));// With 	Animation
        Sales_spinnerDialog_carmanufact=new SpinnerDialog(getActivity(),manufacturer_list,"Select or Search Car Manufacturer",R.style.DialogAnimations_SmileWindow,getResources().getString(R.string.cancel));// With 	Animation

        get_cars_data(token);

    }

    private void getCarsData(String userid) {
        user_cars_list.clear();
        user_cars_list.add(getResources().getString(R.string.select_car));
        user_car_id.clear();
        user_car_id.add("select car");

        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GET_CARS_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                          //    Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        try
                        {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");

                           // Toast.makeText(getActivity(), redeemed_points, Toast.LENGTH_SHORT).show();


                            if(message.contains("success"))
                            {

                                JSONArray manufacturer  = jsonObj.getJSONArray("resp");
                                redeemed_points = jsonObj.getString("redeemed_points");

                                for (int i = 0; i < manufacturer.length(); i++) {

                                    JSONObject m = manufacturer.getJSONObject(i);

                                    String car_name = m.getString("car_number");
                                    String name = m.getString("name");
                                    String model = m.getString("model");
                                    String company = m.getString("company");
                                    String car_identity_number = m.getString("id");

                                  //  Toast.makeText(getActivity(), car_identity_number, Toast.LENGTH_SHORT).show();

                                    String user_car = car_name /*+ company */ + " " + name /*+ model*/;

                                    //Toast.makeText(AddCarScreen.this, car_name +" "+ company + " "+ name + " " + model, Toast.LENGTH_SHORT).show();

                                    //  new AddCarList("ABC 876","Jaguar","XF","2015")
                                    user_cars_list.add(user_car);
                                    user_car_id.add(car_identity_number);



                                }
                                ArrayAdapter<String> user_cars_list_adapter = new ArrayAdapter<String>(getContext(),
                                        R.layout.spinner_white_text,user_cars_list);

                                user_cars_list_adapter.setDropDownViewResource(R.layout.spinner_style);

                                SalesQR_CarSelect_SP.setAdapter(user_cars_list_adapter);

                            }
                            else
                            {
                            //    Toast.makeText(getActivity(), getResources().getString(R.string.nocaradded), Toast.LENGTH_SHORT).show();

                                new AlertDialog.Builder(getContext())
                                        .setMessage(getResources().getString(R.string.nocaradded))
                                        .setCancelable(false)
                                        .setNegativeButton(getResources().getString(R.string.ok), null)
                                        .show();


                                progressDialog.dismiss();
                            }

                        } catch (final JSONException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    /*Toast.makeText(getActivity(),
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
                   // Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }




    public void Add_User_Car(final String car_idnumber, final String carnumberplate,final String milage)
    {

        //  Toast.makeText(this, refreshedToken, Toast.LENGTH_SHORT).show();
       /* Toast.makeText(getContext(), uid, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), car_idnumber, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), carnumberplate, Toast.LENGTH_SHORT).show();*/

//        Toast.makeText(getActivity(), car_idnumber, Toast.LENGTH_SHORT).show();


        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ADD_CARS_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

           //             Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                        // Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

                        JSONObject jsonObj = null;
                        try
                        {
                            jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");
                            String response1 = jsonObj.getString("response");
                            if(message.contains("success"))
                            {

                         //       Toast.makeText(getContext(), getResources().getString(R.string.caraddedsuccessfully), Toast.LENGTH_SHORT).show();

                                new AlertDialog.Builder(getContext())
                                        .setMessage(getResources().getString(R.string.caraddedsuccessfully))
                                        .setCancelable(false)
                                        .setNegativeButton(getResources().getString(R.string.ok), null)
                                        .show();


                                progressDialog.dismiss();

                                uid = SalesQR_userid_txt.getText().toString();
                                user_cars_list.clear();
                                getCarsData(uid);

                            }
                            else
                            {
                                if(response1.contains("Duplicate"))
                                {
                                  //  Toast.makeText(getContext(), getResources().getString(R.string.caralreadyexists), Toast.LENGTH_SHORT).show();

                                    new AlertDialog.Builder(getContext())
                                        .setMessage(getResources().getString(R.string.caralreadyexists))
                                        .setCancelable(false)
                                        .setNegativeButton(getResources().getString(R.string.ok), null)
                                        .show();
                                }
                                else
                                {
                                 //   Toast.makeText(getContext(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();

                                    new AlertDialog.Builder(getContext())
                                            .setMessage(getResources().getString(R.string.networkerror))
                                            .setCancelable(false)
                                            .setNegativeButton(getResources().getString(R.string.ok), null)
                                            .show();
                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }





                     //   Intent intent = new Intent(getContext(),QRScanner.class);
                        // Toast.makeText(Signup.this, response, Toast.LENGTH_SHORT).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   progressDialog.dismiss();
                 //       Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();

                        new AlertDialog.Builder(getContext())
                                .setMessage(getResources().getString(R.string.enter_user_id))
                                .setCancelable(false)
                                .setNegativeButton(getResources().getString(R.string.ok), null)
                                .show();
                        progressDialog.dismiss();
                    }
                })
        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();


                parameters.put("userId", uid);
                parameters.put("car_id", car_idnumber);
                parameters.put("odometer", milage);
                parameters.put("carNumber", carnumberplate.toUpperCase());

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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }


 /*   public void get_cars_data(final String token)
    {
        //    Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
        progressDialog.show();
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CARS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                      // Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                        //    Toast.makeText(UpdateUserProfile.this, response, Toast.LENGTH_SHORT).show();
                        //  Log.d("HTTP_AUTHORIZATION",token);
                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");



                           //     Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                            if(message.equals("success")) {



                                JSONArray cars  = jsonObj.getJSONArray("cars");
                                JSONArray manufact  = jsonObj.getJSONArray("manufacturer");


                                for (int i = 0; i < cars.length(); i++) {

                                    JSONObject c = cars.getJSONObject(i);

                                    String car_id_number = c.getString("id");
                                    String car_brand = c.getString("name");
                                  //  Toast.makeText(getContext(), car_id_number, Toast.LENGTH_SHORT).show();
                                    //     Toast.makeText(UpdateUserProfile.this, occupation, Toast.LENGTH_SHORT).show();
                                    cars_list.add(car_brand);
                                    car_list_id.add(car_id_number);
                                }

                                for (int i = 0; i < manufact.length(); i++) {

                                    JSONObject c = manufact.getJSONObject(i);

                                    String car_manufacturer = c.getString("company");
                                    //     Toast.makeText(UpdateUserProfile.this, occupation, Toast.LENGTH_SHORT).show();
                                    manufacturer_list.add(car_manufacturer);
                                }

                                ArrayAdapter<String> cars_list_adapter = new ArrayAdapter<String>(getContext(),
                                        R.layout.spinner_white_text,cars_list);

                                ArrayAdapter<String> manufacturer_list_adapter = new ArrayAdapter<String>(getContext(),
                                        R.layout.spinner_white_text,manufacturer_list);


                                *//*SalesQR_SelectCarBrand_SP.setAdapter(cars_list_adapter);
                                SalesQR_SelectCarManufacturer_SP.setAdapter(manufacturer_list_adapter);*//*

                            }
                            else
                            {
                                Toast.makeText(getContext(), getResources().getString(R.string.usernotfound), Toast.LENGTH_SHORT).show();
                            }

                        } catch (final JSONException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(getContext(),
                                            "Json parsing error: " + e.getMessage(),
                                            Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {


        *//*
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("HTTP_AUTHORIZATION", "Bearer " + token);
                return headers;
            }*//*




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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }
*/



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
                             //   Toast.makeText(getContext(), R.string.no_data_error, Toast.LENGTH_SHORT).show();

                                new AlertDialog.Builder(getContext())
                                        .setMessage(getResources().getString(R.string.no_data_error))
                                        .setCancelable(false)
                                        .setNegativeButton(getResources().getString(R.string.ok), null)
                                        .show();


                            }

                        } catch (final JSONException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    /*Toast.makeText(getContext(),
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
                       // Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }







    public void get_specific_cars(String manufact_id_str)
    {
        cars_list.clear();
        carid_list.clear();
        //    Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
        progressDialog.setMessage("Please wait while we are fetching your car data");
        progressDialog.setCanceledOnTouchOutside(false);
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

                        Log.d("tag_response_add",response);

                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");



                            //        Toast.makeText(AddCarInfoScreen.this, response, Toast.LENGTH_SHORT).show();

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


//                                for(int i = 0; i<carid_list.size(); i++)
//                                {
//                                    Toast.makeText(getContext(), car_idnumber, Toast.LENGTH_SHORT).show();
//                                }


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
                             //   Toast.makeText(getContext(), R.string.usernotfound, Toast.LENGTH_SHORT).show();

                                new AlertDialog.Builder(getContext())
                                        .setMessage(getResources().getString(R.string.no_data_error))
                                        .setCancelable(false)
                                        .setNegativeButton(getResources().getString(R.string.ok), null)
                                        .show();


                            }

                        } catch (final JSONException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    /*Toast.makeText(getContext(),
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
                      //  Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }




    public void get_car_oil_change_data(final String car_identity)
    {
        //    Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CAR_OIL_CHANGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    //    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();
                        //      Toast.makeText(AddCarInfoScreen.this, response, Toast.LENGTH_SHORT).show();
                        //     Toast.makeText(AddCarInfoScreen.this, response, Toast.LENGTH_SHORT).show();
                        //  Log.d("HTTP_AUTHORIZATION",token);
                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");


                          //  Toast.makeText(, "", Toast.LENGTH_SHORT).show();

                            //    Toast.makeText(getContext(), user_exist_check, Toast.LENGTH_SHORT).show();

                            if(message.equals("success")) {
                                String total_days = jsonObj.getString("days_since_last_activity");
                  //              if()
                                if(total_days!="null") {

                                    int t_days = Integer.parseInt(total_days);
                                    int total = Integer.parseInt(total_days);


                                    if (t_days <= 10) {
                                        int rem = 10 - total;

                                        new AlertDialog.Builder(getContext())
                                                .setMessage(getResources().getString(R.string.oilalreadychanged)+" "+ String.valueOf(rem)+" "+getResources().getString(R.string.days))
                                                .setCancelable(false)
                                                .setNegativeButton("ok", null)
                                                .show();
                                    } else {
                                        Intent intent = new Intent(getContext(), QRScanner.class);
                                        intent.putExtra("uid", uid);
                                        intent.putExtra("redeemed_points", redeemed_points);
                                        startActivity(intent);
                                    }

                                }
                                else
                                {
                                    Intent intent = new Intent(getContext(), QRScanner.class);
                                    intent.putExtra("uid", uid);
                                    intent.putExtra("redeemed_points", redeemed_points);
                                    startActivity(intent);
                                }

                                /*Intent intent = new Intent(getContext(),QRScanner.class);
                                intent.putExtra("uid",uid);
                                intent.putExtra("redeemed_points",redeemed_points);
                                startActivity(intent);*/


                            }
                            else
                            {
                               // Toast.makeText(getContext(), R.string.no_data_error, Toast.LENGTH_SHORT).show();


                                new AlertDialog.Builder(getContext())
                                        .setMessage(getResources().getString(R.string.no_data_error))
                                        .setCancelable(false)
                                        .setNegativeButton(getResources().getString(R.string.ok), null)
                                        .show();
                            }

                        } catch (final JSONException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    /*Toast.makeText(getContext(),
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
                      //  Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
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

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("car_id", car_identity);


                return parameters;
            }

        };
        int socketTimeout = 10000; // 10 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }


}