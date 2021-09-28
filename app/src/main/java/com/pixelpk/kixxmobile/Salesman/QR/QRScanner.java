package com.pixelpk.kixxmobile.Salesman.QR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.Salesman.HomeScreen;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QRScanner extends AppCompatActivity {

    private CodeScanner mCodeScanner;

    EditText QRScanner_carodometer_ET;

    Spinner QRScanner_caroil_SP,QRScanner_carquantity_SP;

    CheckBox QRScanner_checkboxairfilter_CB,QRScanner_checkboxoilfilter_CB;

    TextView QRScanner_totalliters_SP,QRScanner_discountedqty_SP;

    Button QRScanner_finish_Btn;

    ProgressDialog progressDialog;

    List<String> product_list,required_qty,productid_list;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String token;

    String product_id,quantity;

    String uid,promocode;
    String qrid="",car_id;

    String salesman_id;

    String shop_id,oil_selected_qty="null";

    String product_milage="null";

    List<String> milage_list,expected_milage;

    ImageView QRScanner_backbtn;

    String redeemed_points;
    int redeemed_liters;

    String quant_set = "0",prod_set = "0";

    int quant_value = 1,product_value = 1;

    String consumedpoints = "0";

    int qr_discount_total=0;
    int qr_discount=0;

    LinearLayout QRScanner_back_LL;

    String prod_milage = "";
    int total_lit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_scanner);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.CAMERA}, 1);
            }
            else
            {
                QrScannerInitializer();
            }
        }



        InitializeViews();
        getProductsData();

        QRScanner_back_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        QRScanner_finish_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String current_odometer = QRScanner_carodometer_ET.getText().toString();
               // Toast.makeText(QRScanner.this, current_odometer + " " + next_odometer, Toast.LENGTH_SHORT).show();

                if(product_milage.equals("null"))
                {
                    Toast.makeText(QRScanner.this, getResources().getString(R.string.select_product), Toast.LENGTH_SHORT).show();
                }
                else if(uid.equals(""))
                {
                    Toast.makeText(QRScanner.this, getResources().getString(R.string.enter_id), Toast.LENGTH_SHORT).show();
                }
                else if(car_id.equals(""))
                {
                    Toast.makeText(QRScanner.this, getResources().getString(R.string.carid), Toast.LENGTH_SHORT).show();
                }
                else if(product_id.equals(""))
                {
                    Toast.makeText(QRScanner.this, getResources().getString(R.string.productid), Toast.LENGTH_SHORT).show();
                }
                else if(shop_id.equals(""))
                {
                    Toast.makeText(QRScanner.this, getResources().getString(R.string.shopid), Toast.LENGTH_SHORT).show();
                }
                else if(salesman_id.equals(""))
                {
                    Toast.makeText(QRScanner.this, getResources().getString(R.string.salesmanid), Toast.LENGTH_SHORT).show();
                }
                else if(oil_selected_qty.equals("null"))
                {
                    Toast.makeText(QRScanner.this, getResources().getString(R.string.select_quantity), Toast.LENGTH_SHORT).show();
                }
                else if(current_odometer.equals(""))
                {
                    Toast.makeText(QRScanner.this, getResources().getString(R.string.current_odometer), Toast.LENGTH_SHORT).show();
                }
                else
                {
                //    Toast.makeText(QRScanner.this, qrid, Toast.LENGTH_SHORT).show();

                     total_lit = Integer.valueOf(QRScanner_totalliters_SP.getText().toString().replace(" Liters",""));
                   // int discounted_lit = Integer.valueOf(QRScanner_discountedqty_SP.getText().toString());
                    int discounted_lit = redeemed_liters;

                  //  String consumed_points = calculateredeemedpoints(total_lit,discounted_lit);

              //      Toast.makeText(QRScanner.this, String.valueOf(qr_discount), Toast.LENGTH_SHORT).show();
              //      Toast.makeText(QRScanner.this, String.valueOf(discounted_lit), Toast.LENGTH_SHORT).show();

                    int consumed_points = Discounted_Liters(total_lit,qr_discount,discounted_lit);

                    if(consumed_points != -1) {
                   //     Toast.makeText(QRScanner.this, String.valueOf(total_lit), Toast.LENGTH_SHORT).show();

                        String next_odometer = String.valueOf(Integer.valueOf(current_odometer) + Integer.valueOf(prod_milage));
                        if (qrid.equals(""))
                        {
                                   update_user_data(uid,car_id,product_id,shop_id,salesman_id,oil_selected_qty,current_odometer,next_odometer);
                        }

                        else
                            {
                                update_user_data_qr_id(qrid,uid,car_id,product_id,shop_id,salesman_id,oil_selected_qty,current_odometer,next_odometer);
                        }
                    }

                }

            }


        });




/*        QRScanner_caroil_SP.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {


                product_id = productid_list.get(position);
                //   product_milage = get_milage_by_productid(product_id);
                product_milage = milage_list.get(position);

                product_value = Integer.valueOf(product_milage);

            //    Toast.makeText(QRScanner.this, product_milage, Toast.LENGTH_SHORT).show();
                if(quant_set.equals("1"))
                {
                    int result = quant_value * product_value;
                    QRScanner_totalliters_SP.setText(String.valueOf(result) + " Liters");
                }
                else
                {
                    quant_set = "1";

                }




            }
        });


        QRScanner_carquantity_SP.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                oil_selected_qty = String.valueOf(position);

                quant_value = Integer.valueOf(oil_selected_qty);

                if(prod_set.equals("1"))
                {
                    int result = quant_value * product_value;
                    QRScanner_totalliters_SP.setText(String.valueOf(result) + " Liters");
                }
                else
                {
                    prod_set = "1";
                }



            }
        });*/

        QRScanner_caroil_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                product_id = productid_list.get(position);
                //   product_milage = get_milage_by_productid(product_id);
                product_milage = milage_list.get(position);
                prod_milage = expected_milage.get(position);
     //           Toast.makeText(QRScanner.this, prod_milage, Toast.LENGTH_SHORT).show();
                product_value = Integer.valueOf(product_milage);

                //    Toast.makeText(QRScanner.this, product_milage, Toast.LENGTH_SHORT).show();
                if(quant_set.equals("1"))
                {
                    int result = quant_value * product_value;
                //    Toast.makeText(QRScanner.this, String.valueOf(result), Toast.LENGTH_SHORT).show();
                 //   Toast.makeText(QRScanner.this, String.valueOf(product_value), Toast.LENGTH_SHORT).show();
             //       Toast.makeText(QRScanner.this, String.valueOf(result), Toast.LENGTH_SHORT).show();
                    QRScanner_totalliters_SP.setText(String.valueOf(result) + " Liters");
                }
                else
                {
                    quant_set = "1";

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        QRScanner_carquantity_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                oil_selected_qty = String.valueOf(position);

                quant_value = Integer.valueOf(oil_selected_qty);

                if(prod_set.equals("1"))
                {
                    int result = quant_value * product_value;
                 //   Toast.makeText(QRScanner.this, String.valueOf(result), Toast.LENGTH_SHORT).show();
                    QRScanner_totalliters_SP.setText(String.valueOf(result) + " Liters");
                }
                else
                {
                    prod_set = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




      /*  scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    QrScannerInitializer();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.declinedcamerapermission), Toast.LENGTH_SHORT).show();
              //      Toast.makeText(this, "You declined Camera Permission", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }



    public void InitializeViews()
    {
        QRScanner_carodometer_ET = findViewById(R.id.QRScanner_carodometer_ET);
        QRScanner_caroil_SP = findViewById(R.id.QRScanner_caroil_SP);
        QRScanner_carquantity_SP = findViewById(R.id.QRScanner_carquantity_SP);
        QRScanner_checkboxairfilter_CB = findViewById(R.id.QRScanner_checkboxairfilter_CB);
        QRScanner_checkboxoilfilter_CB = findViewById(R.id.QRScanner_checkboxoilfilter_CB);
        QRScanner_totalliters_SP = findViewById(R.id.QRScanner_totalliters_SP);
        QRScanner_discountedqty_SP = findViewById(R.id.QRScanner_discountedqty_SP);
        QRScanner_finish_Btn = findViewById(R.id.QRScanner_finish_Btn);
        QRScanner_back_LL = findViewById(R.id.QRScanner_back_LL);

        progressDialog = new ProgressDialog(QRScanner.this);

        product_list = new ArrayList<>();
        required_qty = new ArrayList<>();
        productid_list = new ArrayList<>();
        expected_milage = new ArrayList<>();

        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        token = sharedPreferences.getString(Shared.sales_loggedIn_jwt,"0");

        product_list.add(getResources().getString(R.string.Select_Product));
        required_qty.add(getResources().getString(R.string.Select_Quantity));

        for(int i=1;i<=15;i++)
        {
            required_qty.add(String.valueOf(i));
        }


        ArrayAdapter<String> adapter_qty = new ArrayAdapter<String>(QRScanner.this,
                R.layout.spinner_white_text,required_qty);

        adapter_qty.setDropDownViewResource(R.layout.spinner_style);


      //  QRScanner_caroil_SP.setItems(product_list);

        QRScanner_carquantity_SP.setAdapter(adapter_qty);

        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        redeemed_points = intent.getStringExtra("redeemed_points");
   //     Toast.makeText(this, redeemed_points, Toast.LENGTH_SHORT).show();
        int redem = Integer.valueOf(redeemed_points);

        if(redem<1000) {
            redeemed_liters = 0;
        }
        else
        {
            redeemed_liters = Integer.parseInt(redeemed_points) / 1000;
        }
        redeemed_points = String.valueOf(redeemed_liters);

        QRScanner_discountedqty_SP.setText(String.valueOf(redeemed_points));

     //   Toast.makeText(this, redeemed_points, Toast.LENGTH_SHORT).show();

      //  Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();

        car_id = sharedPreferences.getString(Shared.Sales_loggedIn_user_carid,"0");
     //   Toast.makeText(this, car_id, Toast.LENGTH_SHORT).show();
        salesman_id = sharedPreferences.getString(Shared.Sales_loggedIn_salesman_id,"0");
        shop_id = sharedPreferences.getString(Shared.loggedIn_sales_shopid,"0");

        milage_list = new ArrayList<>();

        QRScanner_backbtn = findViewById(R.id.QRScanner_backbtn);



    }


    private void getProductsData()
    {
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GET_PRODUCT_SALES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                         //    Toast.makeText(QRScanner.this, response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");

                            milage_list.add("-1");
                            expected_milage.add("-1");
                            productid_list.add("Select");
                            if(message.equals("success"))
                            {
                                progressDialog.dismiss();

                                JSONArray manufacturer  = jsonObj.getJSONArray("resp");

                                for (int i = 0; i < manufacturer.length(); i++) {

                                    JSONObject m = manufacturer.getJSONObject(i);

                                    String product_id_list_item = m.getString("id");
                                    String product_name = m.getString("product_name");
                                    String product_code = m.getString("product_code");
                                    String oil_grade = m.getString("oil_grade");
                                    String milage = m.getString("measure_unit");
                                    String product_price = m.getString("product_price");
                                    String product_mileage = m.getString("milage");
                       //             Toast.makeText(QRScanner.this, product_mileage, Toast.LENGTH_SHORT).show();
                                    expected_milage.add(product_mileage);
                                    //
                                    milage_list.add(milage);

                                    productid_list.add(product_id_list_item);

                                 //   String products = product_name + " " + product_code + " " + oil_grade + " " + milage + " " + product_price;
                               //     Toast.makeText(QRScanner.this, products, Toast.LENGTH_SHORT).show();

                                    product_list.add(product_name);
                             //       Toast.makeText(AddCarScreen.this, car_name +" "+ company + " "+ name + " " + model, Toast.LENGTH_SHORT).show();

                                    //  new AddCarList("ABC 876","Jaguar","XF","2015")
                                   // product_list.add(user_car);



                                }

                                ArrayAdapter<String> adapter_product = new ArrayAdapter<String>(QRScanner.this,
                                        R.layout.spinner_white_text,product_list);

                                adapter_product.setDropDownViewResource(R.layout.spinner_style);

                                QRScanner_caroil_SP.setAdapter(adapter_product);



                            }
                            else
                            {
                                Toast.makeText(QRScanner.this, getResources().getString(R.string.no_data_error), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }

                        catch (final JSONException e)
                        {
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    progressDialog.dismiss();
                           /*         Toast.makeText(QRScanner.this,
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
                            //        Toast.makeText(getApplicationContext()(), R.string.usernotfound, Toast.LENGTH_SHORT).show();
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
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }

/*
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("id",userid);

                return parameters;
            }*/

        };

        RequestQueue requestQueue = Volley.newRequestQueue(QRScanner.this);
        requestQueue.add(stringRequest);


    }



    public void QR_authentication(String user_id,String promo_code)
    {
        //   Toast.makeText(getContext(), referral, Toast.LENGTH_SHORT).show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.QR_AUTH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                  //     Toast.makeText(QRScanner.this, response, Toast.LENGTH_SHORT).show();


                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");


                            if(message.contains("success"))
                            {
                                progressDialog.dismiss();
                                JSONArray manufacturer  = jsonObj.getJSONArray("resp");

                                for (int i = 0; i < manufacturer.length(); i++)
                                {

                                    JSONObject m = manufacturer.getJSONObject(i);

                                    qrid = m.getString("id");

                             /*       String product_code = m.getString("product_code");
                                    String oil_grade = m.getString("oil_grade");
                                    String milage = m.getString("milage");
                                    String product_price = m.getString("product_price");*/

                                    //   String products = product_name + " " + product_code + " " + oil_grade + " " + milage + " " + product_price;
                                    //     Toast.makeText(QRScanner.this, products, Toast.LENGTH_SHORT).show();

                            //        product_list.add(product_name);
                                    //       Toast.makeText(AddCarScreen.this, car_name +" "+ company + " "+ name + " " + model, Toast.LENGTH_SHORT).show();

                                    //  new AddCarList("ABC 876","Jaguar","XF","2015")
                                    // product_list.add(user_car);

                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(QRScanner.this,
                                        R.layout.spinner_white_text,product_list);

                           //     QRScanner_caroil_SP.setAdapter(adapter);

                            }

                            else
                            {
                                qrid = "";
                                Toast.makeText(QRScanner.this, getResources().getString(R.string.qrnotfound), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }

                        catch (final JSONException e)
                        {
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    progressDialog.dismiss();
                                   /* Toast.makeText(QRScanner.this,
                                            "Json parsing error: " + e.getMessage(),
                                            Toast.LENGTH_LONG).show();*/
                                }
                            });
                        }
                        // Toast.makeText(Signup.this, response, Toast.LENGTH_SHORT).show();


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
                            //        Toast.makeText(getApplicationContext()(), R.string.usernotfound, Toast.LENGTH_SHORT).show();
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


                parameters.put("id", user_id);
                parameters.put("qr", promo_code);

                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(QRScanner.this);
        requestQueue.add(stringRequest);
    }



    public void QrScannerInitializer()
    {
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                      //  Toast.makeText(QRScanner.this, result.getText(), Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jsonObj = new JSONObject(result.getText());


                            String QR = jsonObj.getString("QR");
                            String Discount = jsonObj.getString("Discount");

                            //   Toast.makeText(QRScanner.this, QR+" "+Discount, Toast.LENGTH_SHORT).show();
                            QR_authentication(uid,QR);



                            qr_discount_total = Integer.valueOf(Discount) + redeemed_liters;
                            qr_discount =  Integer.valueOf(Discount);

          //                  Toast.makeText(QRScanner.this, String.valueOf(qr_discount_total), Toast.LENGTH_SHORT).show();
            //                Toast.makeText(QRScanner.this, String.valueOf(qr_discount), Toast.LENGTH_SHORT).show();


                            if(QRScanner_discountedqty_SP.getText().toString()!="0") {
                                QRScanner_discountedqty_SP.setText(String.valueOf(qr_discount));
                            }
                            else
                            {
                                QRScanner_discountedqty_SP.setText(String.valueOf(0));
                            }
                            //     QRScanner_caroil_SP.setItems(product_list);





                        } catch (final JSONException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                   /* Toast.makeText(QRScanner.this,
                                            "Json parsing error: " + e.getMessage(),
                                            Toast.LENGTH_LONG).show();*/
                                }
                            });
                        }



                    }
                });
            }
        });
        mCodeScanner.startPreview();
        //Start your camera handling here
    }


    public void update_user_data_qr_id(String qr_id,String user_id,String car_id,String product_id,String shop_id,String shop_user_id,String quantity,String previous_odometer,String next_odometer)
    {
        //  Toast.makeText(this, qr_id + " " + user_id + " " + car_id + " " + product_id + " " + shop_id+ " " + shop_user_id + " " + quantity +" " + previous_odometer + " " + next_odometer, Toast.LENGTH_SHORT).show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CHANGE_OIL_SALES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


//                        Toast.makeText(QRScanner.this, response, Toast.LENGTH_SHORT).show();


                        if (response.contains("success"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(QRScanner.this, getResources().getString(R.string.oil_change_successful), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(QRScanner.this, HomeScreen.class);
                            startActivity(intent);
                        }
                        else if (response.contains("Expired"))
                        {
                            progressDialog.dismiss();

                            Toast.makeText(QRScanner.this, getResources().getString(R.string.qrinvalid), Toast.LENGTH_SHORT).show();
                            mCodeScanner.startPreview();
                        }
                        else if(response.contains("Already Redeemed"))
                        {
                            progressDialog.dismiss();

                            Toast.makeText(QRScanner.this, "QR already redeemed", Toast.LENGTH_SHORT).show();
                            mCodeScanner.startPreview();
                        }

                        // Toast.makeText(Signup.this, response, Toast.LENGTH_SHORT).show();


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
                            //        Toast.makeText(getApplicationContext()(), R.string.usernotfound, Toast.LENGTH_SHORT).show();
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


                parameters.put("qr_id", qr_id);
                parameters.put("user_id", user_id);
                parameters.put("car_id", car_id);
                parameters.put("product_id", product_id);
                parameters.put("shop_id", shop_id);
                parameters.put("shop_user_id", shop_user_id);
                parameters.put("quantity", String.valueOf(total_lit));
                parameters.put("previous_odometer", previous_odometer);
                parameters.put("next_odometer", next_odometer);
                parameters.put("redeemed_litres", consumedpoints);
                parameters.put("total_discounted_litres",QRScanner_discountedqty_SP.getText().toString());

                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(QRScanner.this);
        requestQueue.add(stringRequest);

    }





    public void update_user_data(String user_id,String car_id,String product_id,String shop_id,String shop_user_id,String quantity,String previous_odometer,String next_odometer)
    {
        //  Toast.makeText(this, qr_id + " " + user_id + " " + car_id + " " + product_id + " " + shop_id+ " " + shop_user_id + " " + quantity +" " + previous_odometer + " " + next_odometer, Toast.LENGTH_SHORT).show()
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CHANGE_OIL_SALES,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                      //  Toast.makeText(QRScanner.this, response, Toast.LENGTH_SHORT).show();


                        if (response.contains("success"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(QRScanner.this, getResources().getString(R.string.oil_change_successful), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(QRScanner.this, HomeScreen.class);
                            startActivity(intent);
                        }

                        else if (response.contains("Expired"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(QRScanner.this, getResources().getString(R.string.qrinvalid), Toast.LENGTH_SHORT).show();
                        }

                        else if(response.contains("Already Redeemed"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(QRScanner.this, "QR already redeemed", Toast.LENGTH_SHORT).show();
                        }


                        // Toast.makeText(Signup.this, response, Toast.LENGTH_SHORT).show();


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
                            //        Toast.makeText(getApplicationContext()(), R.string.usernotfound, Toast.LENGTH_SHORT).show();
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


                parameters.put("user_id", user_id);
                parameters.put("car_id", car_id);
                parameters.put("product_id", product_id);
                parameters.put("shop_id", shop_id);
                parameters.put("shop_user_id", shop_user_id);
                parameters.put("quantity", String.valueOf(total_lit));
                parameters.put("previous_odometer", previous_odometer);
                parameters.put("next_odometer", next_odometer);
                parameters.put("redeemed_litres", consumedpoints);
                parameters.put("total_discounted_litres",QRScanner_discountedqty_SP.getText().toString());

                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(QRScanner.this);
        requestQueue.add(stringRequest);

    }

        //   Toast.makeText(getContext(), referral, Toast.LENGTH_SHORT).show();

      /*  progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CHANGE_OIL_SALES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Toast.makeText(QRScanner.this, response, Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();
                        // Toast.makeText(Signup.this, response, Toast.LENGTH_SHORT).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   progressDialog.dismiss();
                        Toast.makeText(QRScanner.this, error.toString(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
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


                parameters.put("qr_id", qr_id);
                parameters.put("user_id", user_id);
                parameters.put("car_id", car_id);
                parameters.put("product_id", product_id);
                parameters.put("shop_id", shop_id);
                parameters.put("shop_user_id", shop_user_id);
                parameters.put("quantity", quantity);
                parameters.put("previous_odometer", previous_odometer);
                parameters.put("next_odometer", next_odometer);

                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(QRScanner.this);
        requestQueue.add(stringRequest);
    }*/

    public String get_milage_by_productid(String prod_id)
    {
        if(prod_id.equals("Select")) {
            return "-1";

        }
        else
        {
            return milage_list.get(Integer.parseInt(prod_id));
        }


    }

    public int Discounted_Liters(int total_liters,int qr_liters,int claim_liters)
    {
        int cl = 0;

        int temp_total_liters = redeemed_liters;

        temp_total_liters = Math.abs(qr_liters - total_liters);

    //    Toast.makeText(this, String.valueOf(total_liters), Toast.LENGTH_SHORT).show();
     //   Toast.makeText(this, String.valueOf(temp_total_liters), Toast.LENGTH_SHORT).show();

        if(qr_liters>total_liters)
        {
            Toast.makeText(this, getResources().getString(R.string.totallitersgreater), Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, "Total liters should be equal or greater then discounted liters", Toast.LENGTH_SHORT).show();

            return -1;
        }
        else
        {
            cl = Integer.valueOf(calculateredeemedpoints(temp_total_liters,redeemed_liters));

            return cl;
        }



      //  Toast.makeText(this, String.valueOf(cl), Toast.LENGTH_SHORT).show();


    }

    public String calculateredeemedpoints(int TL,int DL) {


        if(TL>DL)
        {
            consumedpoints = String.valueOf(DL);

        }
        else if(DL>TL)
        {
            consumedpoints = String.valueOf(TL);

        }
        else if(TL==DL)
        {
            consumedpoints = String.valueOf(DL);
        }
        else if(DL==0)
        {
            consumedpoints = "0";
        }

  //    Toast.makeText(this, consumedpoints, Toast.LENGTH_SHORT).show();

        return consumedpoints;
    }
}