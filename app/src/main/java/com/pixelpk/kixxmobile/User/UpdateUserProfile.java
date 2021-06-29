package com.pixelpk.kixxmobile.User;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.transition.CircularPropagation;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.AddCar.AddCarScreen;
import com.pixelpk.kixxmobile.User.ModelClasses.CarDetailsList;
import com.pixelpk.kixxmobile.User.ModelClasses.ImageSliderList;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
import com.pixelpk.kixxmobile.User.adapters.ImageSlidingAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import de.hdodenhof.circleimageview.CircleImageView;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class UpdateUserProfile extends AppCompatActivity {

    EditText name,city,email,mobile;
    Spinner occupation_sp;
    Spinner gender;
    String gender_str,gender_str_sharepref,occupation_str,occupation_id;
    ProgressDialog progressDialog;
    public  static final int RequestPermissionCode  = 1 ;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    BitmapDrawable drawable;
    private static final int CAMERA_IMAGE_REQUEST = 1;
    private String imageName;

    String jwt_token;

    List<String> occupation_data;

    Button Updatebtn,AddCarBtn;

    String KX_formatted_userid;

    TextView UpdateUserProfile_User_id;

    ImageView UpdateUserProfile_backarrow;
    List<ImageSliderList> imageSliderLists;

    private static final int MY_CAMERA_REQUEST_CODE = 100;

    CircleImageView Updateuserprofile_user_profile_image;

    String[] gend = {"Select Gender","Male","Female"};
    String[] occupat = {"Select Occupation","Others","Student","Job holder","Business"};
    String[] occupat_ar_title = {"اختر المهنة","Others","Student","Job holder","Business"};
    String[] occupation_array_ar = {"اختر المهنة","آخر","طالب علم","صاحب الوظيفة","اعمال"};
    Bitmap FixBitmap;

    String user_id,user_name,user_email,user_ph,user_dp,user_gender,user_fcm_id,user_loyality_points = "0000";
    String user_occupation_id,user_occupation_name,user_category,user_user_role,user_odometer = "0000000";

    String uname;
    String uphone;
    String uemail;
    String ucity;

    String[] occupation_array = {"Select Occupation","Others","Business","Job holder","Student"};

    JSONArray cars,ads;
    JSONObject objcars,objads,objcars_activity;
    List<String> user_cars_list,car_id_list;
    List<CarDetailsList> myListData;
    byte[] byteArray;
    String ConvertImage;

    //  private static final int MY_CAMERA_REQUEST_CODE = 100;

    @RequiresApi(api = Build.VERSION_CODES.M)

    ByteArrayOutputStream byteArrayOutputStream ;

    URL newurl;

    HttpURLConnection httpURLConnection ;

    OutputStream outputStream;

    BufferedWriter bufferedWriter ;
    int MY_PERMISSIONS_REQUEST_CAMERA=0;
    int RC ;

    BufferedReader bufferedReader ;

    StringBuilder stringBuilder;

    boolean check = true;

    String url = URLs.USER_PROFILE_UPDATE;

    String user_profile_image = "";
    String loggedIn_user_city = "";

    String username,usercontact,useroccupation,usergender,usercity,useremail;

    LinearLayout updateprofile_back;

    LinearLayout Updateuserprofile_City_LL;
    SpinnerDialog spinnerDialog_city;

    ArrayList<String> city_list;

    TextView Updateuserprofile_City_TV;

    String[] city_array_list;

    String city_str="";
    String rtl;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_profile);
        EnableRuntimePermission();
        InitializeViews();

        updateprofile_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Updateuserprofile_user_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(UpdateUserProfile.this)
                        .setMessage(getResources().getString(R.string.please_select_image_type))
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.Gallery), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //   Toast.makeText(UpdateUserProfile.this, "Gallery", Toast.LENGTH_SHORT).show();
                                getimagefromgallery();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.Camera), new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                if (ActivityCompat.shouldShowRequestPermissionRationale(UpdateUserProfile.this,
                                        Manifest.permission.CAMERA))
                                {

                                    ActivityCompat.requestPermissions(UpdateUserProfile.this,new String[]{
                                            Manifest.permission.CAMERA}, RequestPermissionCode);
                                    //    Toast.makeText(UpdateUserProfile.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();


                                } else {
                                    getimagefromcamera();


                                }


                             /*   if (ContextCompat.checkSelfPermission(UpdateUserProfile.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                                {
                                    if (ActivityCompat.shouldShowRequestPermissionRationale(UpdateUserProfile.this, Manifest.permission.CAMERA))
                                    {
                                        getimagefromcamera();
                                    }
                                    else
                                    {
                                        ActivityCompat.requestPermissions(UpdateUserProfile.this,new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA );
                                        getimagefromcamera();
                                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                                        // app-defined int constant. The callback method gets the
                                        // result of the request.
                                    }
                                }*/
                                // Toast.makeText(UpdateUserProfile.this, "Camera", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .show();
            }
        });

        Updatebtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v)
            {
                //     Update_User(jwt_token);
//                Toast.makeText(UpdateUserProfile.this, "in upload", Toast.LENGTH_SHORT).show();
                UploadImageToServer();

            }
        });

        AddCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(UpdateUserProfile.this, AddCarScreen.class);
                startActivity(intent);

            }
        });


       /* gender.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
               // Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                gender_str = item;
            }
        });*/

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender_str = gender.getSelectedItem().toString();
                //   Toast.makeText(UpdateUserProfile.this, gender_str, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerDialog_city.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                Updateuserprofile_City_TV.setText(item);
                city_str = item;
                //           Toast.makeText(UpdateUserProfile.this, city_str, Toast.LENGTH_SHORT).show();
            }
        });

        Updateuserprofile_City_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     ((TextView) findViewById(R.id.spinner_dropdown_tv_icon)).setTextColor(getResources().getColor(R.color.white));
                spinnerDialog_city.showSpinerDialog();
            }
        });
        /*occupation_sp.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
               // Toast.makeText(UpdateUserProfile.this, String.valueOf(id), Toast.LENGTH_SHORT).show();
                occupation_str = item;
                occupation_id = String.valueOf(id+1);
            }
        });*/


        occupation_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                occupation_str = occupation_sp.getSelectedItem().toString();
                occupation_id = String.valueOf(position);
                //      Toast.makeText(UpdateUserProfile.this, occupation_id, Toast.LENGTH_SHORT).show();
                // Toast.makeText(UpdateUserProfile.this, occupation_str, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        UpdateUserProfile_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void InitializeViews()
    {
        city_array_list = getResources().getStringArray(R.array.cities);
        UpdateUserProfile_backarrow = findViewById(R.id.UpdateUserProfile_backarrow);
        name = findViewById(R.id.Updateuserprofile_name_ET);
        Updateuserprofile_City_LL = findViewById(R.id.Updateuserprofile_City_LL);
        gender = findViewById(R.id.Updateuserprofile_gender_SP);
        occupation_sp = findViewById(R.id.Updateuserprofile_occupation_SP);


        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_white_text,getResources().getStringArray(R.array.gender_arr));

        adapter.setDropDownViewResource(R.layout.spinner_style);

        /*ArrayAdapter<String> adapter_occupat = new ArrayAdapter<String>(this,
                R.layout.spinner_white_text,occupat);*/

        rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");
        //    Toast.makeText(this, strcity, Toast.LENGTH_SHORT).show();

        ArrayAdapter<String> adapter_occupat;


        if(rtl.equals("1"))
        {
            adapter_occupat = new ArrayAdapter<String>(this,
                    R.layout.spinner_white_text,occupat_ar_title);

            adapter_occupat.setDropDownViewResource(R.layout.spinner_style);

            UpdateUserProfile_backarrow.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24_rwhite);
        }

        else
        {
            adapter_occupat = new ArrayAdapter<String>(this,
                    R.layout.spinner_white_text,occupat);

            adapter_occupat.setDropDownViewResource(R.layout.spinner_style);
            /*adapter_occupat = new ArrayAdapter<String>(this,
                    R.layout.spinner_white_text,occupat);*/
        }

        // gender.setItems(getResources().getString(R.string.select_gender),getResources().getString(R.string.male),getResources().getString(R.string.female));
        gender.setAdapter(adapter);
        occupation_sp.setAdapter(adapter_occupat);
        email = findViewById(R.id.Updateuserprofile_email_ET);
        mobile = findViewById(R.id.Updateuserprofile_mobile_ET);
        Updateuserprofile_City_TV = findViewById(R.id.Updateuserprofile_City_TV);
        Updatebtn = findViewById(R.id.Updateuserprofile_update_Btn);
        AddCarBtn = findViewById(R.id.Updateuserprofile_addcar_Btn);
        updateprofile_back = findViewById(R.id.updateprofile_back);

        occupation_data = new ArrayList<>();
        //  occupation_data.add("Select Occupation");
        //  occupation_sp.setItems("Select Occupation","Others","Student","Job holder","Business");
        //occupation_sp.setItems("Select Occupation","Others","Student","Job holder","Business");
        // occupation_data.set(getResources().getString(R.array.User_occupation));
        city_list = new ArrayList(Arrays.asList(city_array_list));
        progressDialog = new ProgressDialog(UpdateUserProfile.this);



        jwt_token = sharedPreferences.getString(Shared.loggedIn_jwt,"0");
        //  Toast.makeText(this, jwt_token, Toast.LENGTH_SHORT).show();

        editor = sharedPreferences.edit();

        KX_formatted_userid = sharedPreferences.getString(Shared.LoggedIn_fromatted_userid,"0");

        UpdateUserProfile_User_id = findViewById(R.id.UpdateUserProfile_User_id);

        UpdateUserProfile_User_id.setText(KX_formatted_userid);

        get_occupation_data(jwt_token);



        imageSliderLists = new ArrayList<>();
        user_cars_list = new ArrayList<>();
        car_id_list = new ArrayList<>();
        myListData = new ArrayList<>();

        uname = sharedPreferences.getString(Shared.loggedIn_user_name,"0");
        uphone = sharedPreferences.getString(Shared.loggedIn_user_ph,"0");
        uemail = sharedPreferences.getString(Shared.loggedIn_user_email,"0");
        ucity = sharedPreferences.getString(Shared.loggedIn_user_city,"0");
        user_id =sharedPreferences.getString(Shared.loggedIn_user_id,"0");

        if(ucity.equals(""))
        {

        }
        else
        {
            city_str = ucity;
        }

        if(rtl.equals("1"))
        {
            /*name.setGravity(Gravity.END);
            email.setGravity(Gravity.END);
            //   mobile.setGravity(Gravity.END);
            adapter_occupat = new ArrayAdapter<String>(this,
                    R.layout.spinner_white_text,occupation_array_ar);*/

            Updateuserprofile_City_TV.setGravity(Gravity.START);
            occupation_sp.setGravity(Gravity.START);
            email.setGravity(Gravity.END);
            name.setGravity(Gravity.END);
            occupation_sp.setGravity(Gravity.END);


        }
        else
        {
            /*adapter_occupat = new ArrayAdapter<String>(this,
                    R.layout.spinner_white_text,occupat);*/
        }

        //Toast.makeText(this, ucity, Toast.LENGTH_SHORT).show();

        checkUserDate(uname,uphone,ucity,uemail);

        Updateuserprofile_user_profile_image = findViewById(R.id.Updateuserprofile_user_profile_image);

        byteArrayOutputStream = new ByteArrayOutputStream();

        user_profile_image = sharedPreferences.getString(Shared.user_profile_image,"0");
        loggedIn_user_city = sharedPreferences.getString(Shared.loggedIn_user_city,"0");
        occupation_id = sharedPreferences.getString(Shared.loggedIn_user_occupation_id,"0");
        gender_str_sharepref = sharedPreferences.getString(Shared.loggedIn_user_gender,"0");

        //  Toast.makeText(this, occupation_id, Toast.LENGTH_SHORT).show();

        if(gender_str_sharepref.equals("null"))
        {
            usergender = gender_str;
        }
        else
        {
            usergender = gender_str_sharepref;
        }

        //      Toast.makeText(this, gender_str_sharepref, Toast.LENGTH_SHORT).show();
        //  Toast.makeText(this, user_profile_image, Toast.LENGTH_SHORT).show();

        if(user_profile_image.equals("null"))
        {

        }
        else
        {


            //Glide.with(UpdateUserProfile.this).load(URLs.USER_IMAGE_URL + user_profile_image).into(Updateuserprofile_user_profile_image);
            //       Glide.get(UpdateUserProfile.this).clearDiskCache();
            Glide.with(UpdateUserProfile.this)
                    .load(URLs.USER_IMAGE_URL + user_profile_image)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .dontTransform()
                    .dontAnimate()
                    .into(Updateuserprofile_user_profile_image);
        }





        if(!gender_str_sharepref.equals("null"))
        {
            if(gender_str_sharepref.equals(getResources().getString(R.string.male_gender))) {

                gender.setSelection(adapter.getPosition(getResources().getString(R.string.male_gender)));
            }
            else if(gender_str_sharepref.equals(getResources().getString(R.string.female_gender)))
            {
                gender.setSelection(adapter.getPosition(getResources().getString(R.string.female_gender)));
            }
            else
            {
                gender.setSelection(adapter.getPosition("Select Gender"));
            }
        }
        else{

            gender.setSelection(adapter.getPosition("Select Gender"));

        }

        //Toast.makeText(this, String.valueOf(gender.length()), Toast.LENGTH_SHORT).show();
        //    Toast.makeText(this, String.valueOf(occupation_sp.length()), Toast.LENGTH_SHORT).show();

        //   Toast.makeText(this, occupation_id, Toast.LENGTH_SHORT).show();

        if(occupation_id!=null || !occupation_id.equals("null"))
        {


            //         Toast.makeText(this, occupation_id, Toast.LENGTH_SHORT).show();
            if(occupation_id.equals("0")) {

                /*if(rtl.equals("1"))
                {
         *//*   name.setGravity(Gravity.END);
            email.setGravity(Gravity.END);
            //   mobile.setGravity(Gravity.END);*//*
               //     String[] occupation_array_ar = {"","آخر","طالب علم","صاحب الوظيفة","اعمال"};
                    occupation_sp.setSelection(adapter_occupat.getPosition("حدد المهنة"));


                }
                else
                {
                    occupation_sp.setSelection(adapter_occupat.getPosition("Select Occupation"));
                }*/



                if(rtl.equals("1"))
                {
            /*name.setGravity(Gravity.END);
            email.setGravity(Gravity.END);
            //   mobile.setGravity(Gravity.END);
            adapter_occupat = new ArrayAdapter<String>(this,
                    R.layout.spinner_white_text,occupation_array_ar);*/

                    /*Updateuserprofile_City_TV.setGravity(Gravity.START);
                    occupation_sp.setGravity(Gravity.START);*/
                    occupation_sp.setSelection(adapter_occupat.getPosition("Select Occupation"));

                }
                else
                {
                    occupation_sp.setSelection(adapter_occupat.getPosition("Select Occupation"));
            /*adapter_occupat = new ArrayAdapter<String>(this,
                    R.layout.spinner_white_text,occupat);*/
                }
            }
            else if(occupation_id.equals("1"))
            {

                /*if(rtl.equals("1"))
                {
         *//*   name.setGravity(Gravity.END);
            email.setGravity(Gravity.END);
            //   mobile.setGravity(Gravity.END);*//*
                  //  String[] occupation_array_ar = {"","","طالب علم","صاحب الوظيفة","اعمال"};
                    occupation_sp.setSelection(adapter_occupat.getPosition("آخر"));


                }
                else
                {
                    occupation_sp.setSelection(adapter_occupat.getPosition("Others"));
                }*/

                occupation_sp.setSelection(adapter_occupat.getPosition("Others"));
            }
            else if(occupation_id.equals("4"))
            {
                /*if(rtl.equals("1"))
                {
         *//*   name.setGravity(Gravity.END);
            email.setGravity(Gravity.END);
            //   mobile.setGravity(Gravity.END);*//*
                    //  String[] occupation_array_ar = {"","","طالب علم","صاحب الوظيفة",""};
                    occupation_sp.setSelection(adapter_occupat.getPosition("اعمال"));


                }
                else
                {
                    occupation_sp.setSelection(adapter_occupat.getPosition("Business"));
                }*/
                occupation_sp.setSelection(adapter_occupat.getPosition("Business"));

            }
            else if(occupation_id.equals("3"))
            {
                /*if(rtl.equals("1"))
                {
         *//*   name.setGravity(Gravity.END);
            email.setGravity(Gravity.END);
            //   mobile.setGravity(Gravity.END);*//*
                    //  String[] occupation_array_ar = {"","","طالب علم","",""};
                    occupation_sp.setSelection(adapter_occupat.getPosition("صاحب الوظيفة"));


                }
                else
                {

                    occupation_sp.setSelection(adapter_occupat.getPosition("Job holder"));
                }*/

                occupation_sp.setSelection(adapter_occupat.getPosition("Job holder"));


            }
            else if(occupation_id.equals("2"))
            {
                /*if(rtl.equals("1"))
                {
         *//*   name.setGravity(Gravity.END);
            email.setGravity(Gravity.END);
            //   mobile.setGravity(Gravity.END);*//*
                    //  String[] occupation_array_ar = {"","","","",""};
                    occupation_sp.setSelection(adapter_occupat.getPosition("طالب علم"));


                }
                else
                {

                    occupation_sp.setSelection(adapter_occupat.getPosition("Student"));
                }*/
                occupation_sp.setSelection(adapter_occupat.getPosition("Student"));
            }
        }
        else
        {
           /* if(rtl.equals("1"))
            {
         *//*   name.setGravity(Gravity.END);
            email.setGravity(Gravity.END);
            //   mobile.setGravity(Gravity.END);*//*
                //  String[] occupation_array_ar = {"","","","",""};
                occupation_sp.setSelection(adapter_occupat.getPosition("حدد المهنة"));


            }
            else
            {

                occupation_sp.setSelection(adapter_occupat.getPosition("Select Occupation"));
            }*/
            occupation_sp.setSelection(adapter_occupat.getPosition("Select Occupation"));
        }


        spinnerDialog_city=new SpinnerDialog(UpdateUserProfile.this, city_list,"Select or Search City",R.style.DialogAnimations_SmileWindow,getResources().getString(R.string.cancel));// With 	Animation

        //city.setText(loggedIn_user_city);


    }

    public void get_occupation_data(final String token)
    {
        progressDialog.show();
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.USER_PROFILE_OCCUPATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        //    Toast.makeText(UpdateUserProfile.this, response, Toast.LENGTH_SHORT).show();
                        //  Log.d("HTTP_AUTHORIZATION",token);
                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");



                            //    Toast.makeText(getContext(), user_exist_check, Toast.LENGTH_SHORT).show();

                            if(message.equals("success")) {



                                JSONArray data  = jsonObj.getJSONArray("resp");

                                for (int i = 0; i < data.length(); i++) {

                                    JSONObject c = data.getJSONObject(i);


                                    String occupation = c.getString("occupation");
                                    //     Toast.makeText(UpdateUserProfile.this, occupation, Toast.LENGTH_SHORT).show();
                                    occupation_data.add(occupation);
                                }

                                //   occupation_sp.setItems(occupation_data);

                            }
                            else
                            {
                                Toast.makeText(UpdateUserProfile.this,getResources().getString(R.string.user_not_found), Toast.LENGTH_SHORT).show();
                            }

                        } catch (final JSONException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    /*Toast.makeText(UpdateUserProfile.this,
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
                        progressDialog.dismiss();
                        //   Toast.makeText(UpdateUserProfile.this, error.toString(), Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(UpdateUserProfile.this);

        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);



    }


    public void Update_User(final String token)
    {



        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.USER_PROFILE_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        //     Toast.makeText(UpdateUserProfile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();
                        // Toast.makeText(Signup.this, response, Toast.LENGTH_SHORT).show();
                        get_user_data(user_id);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   progressDialog.dismiss();
//                        Toast.makeText(UpdateUserProfile.this, error.toString(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                })
        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();


                parameters.put("name", name.getText().toString());
                parameters.put("email", email.getText().toString());
                parameters.put("occupation", occupation_id);
                parameters.put("city", city.getText().toString());
                parameters.put("gender", gender_str);

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
        RequestQueue requestQueue = Volley.newRequestQueue(UpdateUserProfile.this);

        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    public void checkUserDate(String strname,String strphone,String strcity,String stremail)
    {

        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");
        //    Toast.makeText(this, strcity, Toast.LENGTH_SHORT).show();

        if(rtl.equals("1"))
        {
            name.setGravity(Gravity.END);
            email.setGravity(Gravity.END);
            //   mobile.setGravity(Gravity.END);

        }

        if(strname.equals("null"))
        {


            name.setHint(getResources().getString(R.string.update_name));
        }
        else
        {
            name.setText(strname);
        }

        if(!strphone.equals("null"))
        {
            mobile.setText(strphone);
            disableEditText(mobile);
        }

        if(strcity.equals("null"))
        {
            Updateuserprofile_City_TV.setHint(getResources().getString(R.string.update_city));
        }
        else
        {
            //Toast.makeText(this, strcity, Toast.LENGTH_SHORT).show();
            Updateuserprofile_City_TV.setText(strcity.trim());
        }

        if(stremail.equals("null"))
        {
            email.setHint(getResources().getString(R.string.update_email));
        }
        else
        {
            email.setText(stremail);
        }




    }


    private void get_user_data(final String id) {

        progressDialog.show();
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.END_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                  //          Toast.makeText(UpdateUserProfile.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");

                            if(message.contains("success"))
                            {
                                JSONArray data  = jsonObj.getJSONArray("user");
                                String cardata = jsonObj.getString("cars");
                                String adsdata = jsonObj.getString("ads");


                                if(!cardata.equals("null")) {
                                    cars = jsonObj.getJSONArray("cars");

                                    for (int i = 0; i < cars.length(); i++) {

                                        objcars = cars.getJSONObject(i);
                                        String id = objcars.getString("id");
                                        String car_number = objcars.getString("car_number");
                                        String company = objcars.getString("company");
                                        String name = objcars.getString("name");

                                        user_cars_list.add(car_number + " " + company + " " + name);
                                        car_id_list.add(id);

                                    }

                                    //  spinner.setItems(user_cars_list);


                                }


                                if(!adsdata.equals("null")) {
                                    ads = jsonObj.getJSONArray("ads");

                                    for (int i = 0; i < ads.length(); i++) {
                                        objads = ads.getJSONObject(i);
                                        String imagename = objads.getString("banner");
                                        // Toast.makeText(getActivity(), URLs.BANNER_IMAGES+imagename, Toast.LENGTH_SHORT).show();
                                        String url = URLs.BANNER_IMAGES + imagename;
                                        ImageSliderList imageSliderList = new ImageSliderList(url,"1");
                                        imageSliderLists.add(imageSliderList);

                                        Log.d("ImageURl",URLs.BANNER_IMAGES + imagename);

                                    }

                                    /*ImageSlidingAdapter adapter = new ImageSlidingAdapter(imageSliderLists,getActivity());
                                    HomeFragment_imageslider_RV.setHasFixedSize(true);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                                    HomeFragment_imageslider_RV.setLayoutManager(linearLayoutManager);
                                    //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    HomeFragment_imageslider_RV.setAdapter(adapter);*/

                                }



                                for (int i = 0; i < data.length(); i++) {

                                    JSONObject c = data.getJSONObject(i);

                                    if(!cardata.equals("null")) {

                                        objcars = cars.getJSONObject(i);
                                        user_odometer = objcars.getString("odometer");
                                        editor.putString(Shared.loggedIn_user_odometer,user_id);
                                    }
                                    user_name = c.getString("name");
                                    user_email = c.getString("email");
                                    user_ph = c.getString("phone");
                                    user_gender = c.getString("gender");
                                    user_occupation_id = c.getString("occupation_id");
                                    user_occupation_name = c.getString("occupation_name");

                                    editor.putString(Shared.loggedIn_user_id,user_id);
                                    editor.putString(Shared.loggedIn_user_name,user_name);
                                    editor.putString(Shared.loggedIn_user_email,user_email);
                                    editor.putString(Shared.loggedIn_user_ph,user_ph);
                                    editor.putString(Shared.loggedIn_user_dp,user_dp);
                                    editor.putString(Shared.loggedIn_user_gender,user_gender);
                                    editor.putString(Shared.loggedIn_user_fcm_id,user_fcm_id);
                                    editor.putString(Shared.loggedIn_loyality_points,user_loyality_points);
                                    editor.putString(Shared.loggedIn_user_occupation_id,user_occupation_id);
                                    editor.putString(Shared.loggedIn_user_occupation_name,user_occupation_name);
                                    editor.putString(Shared.loggedIn_user_category,user_category);
                                    editor.putString(Shared.loggedIn_user_user_role,user_user_role);
                                    editor.apply();

                                }



                            }
                            else
                            {
                                Toast.makeText(UpdateUserProfile.this, getResources().getString(R.string.user_not_found), Toast.LENGTH_SHORT).show();
                            }

                        } catch (final JSONException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    /*Toast.makeText(UpdateUserProfile.this,
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
                    public void onErrorResponse(VolleyError error) {
                        //   progressDialog.dismiss();
                        progressDialog.dismiss();
                     //   Toast.makeText(UpdateUserProfile.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("id", id);


                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(UpdateUserProfile.this);
        requestQueue.add(stringRequest);


    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        // editText.setBackgroundColor(Color.TRANSPARENT);
        // editText.setGravity(Gravity.LEFT);
        //editText.setPadding(0,10,0,0);
    }

    public void getimagefromgallery()
    {
        Intent intent = new Intent();

        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

    }


    public void getimagefromcamera()
    {

        captureImage();
     /*   Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(Intent.createChooser(takePictureIntent, "Select Image From Camera"), 1);*/

    }

    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();
          /*  Updateuserprofile_user_profile_image.setImageURI(uri);
            Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();*/


            try {

                FixBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                Updateuserprofile_user_profile_image.setImageBitmap(FixBitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        else    if (RC == 7 && RQC == RESULT_OK) {

            Bitmap bitmap = (Bitmap) I.getExtras().get("data");

            FixBitmap = bitmap;

            Updateuserprofile_user_profile_image.setImageBitmap(bitmap);
        }
          /*  Updateuserprofile_user_profile_image.setImageURI(uri);
            Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();*/


           /* try {

                FixBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                Updateuserprofile_user_profile_image.setImageBitmap(FixBitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }*/

    }





    @SuppressLint("NewApi")
    public void UploadImageToServer(){


//        Toast.makeText(this, "Before image", Toast.LENGTH_SHORT).show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
//        Toast.makeText(this, "After image", Toast.LENGTH_SHORT).show();


        if(!name.getText().toString().equals(""))
        {
            username = name.getText().toString();
        }
        else
        {
            username = "";
        }
        if(!email.getText().toString().equals(""))
        {
            useremail = email.getText().toString();
        }
        else
        {
            useremail = "";
        }

        if(!mobile.getText().toString().equals(""))
        {
            usercontact = mobile.getText().toString();
        }
        else
        {
            usercontact = "";
        }

        if(!occupation_id.equals(""))
        {
            useroccupation = occupation_id;
        }
        else
        {
            useroccupation = "";
        }


        if(!city_str.equals("") )
        {
            city_str = city_str;
        }
        else
        {
            city_str = "";
        }




        if(usergender!=null) {

            if (usergender.equals("") | !gender_str.equals("Select Gender"))
            {
                usergender = gender_str;
            }

            else if(!usergender.equals("") | gender_str.equals("Select Gender"))
            {
                usergender = user_gender;
            }

            else if(!usergender.equals("") | !gender_str.equals("Select Gender"))
            {
                usergender = user_gender;
            }

            else
            {
                usergender = "";
            }

        }
        else
        {
            if(gender_str.equals("Select Gender"))
            {
                usergender = "";
            }
            else
            {
                usergender = gender_str;
            }

        }
        if(FixBitmap!=null)
        {

//            Toast.makeText(this, "in bitmap", Toast.LENGTH_SHORT).show();
            FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

            byteArray = byteArrayOutputStream.toByteArray();

            ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);



            class AsyncTaskUploadClass extends AsyncTask<Void,Void,String>
            {

                @Override
                protected void onPreExecute()
                {
                    super.onPreExecute();
                    //         progressDialog = ProgressDialog.show(Academy_Reg.this,"Image is Uploading","Please Wait",false,false);
                }

                @Override
                protected void onPostExecute(String string1) {

                    super.onPostExecute(string1);

           //         Toast.makeText(UpdateUserProfile.this, string1, Toast.LENGTH_SHORT).show();
                    //        Toast.makeText(UpdateUserProfile.this,string1,Toast.LENGTH_LONG).show();

        /*            //       progressDialog.dismiss();

                        Toast.makeText(UpdateUserProfile.this,"User Updated Successfully",Toast.LENGTH_LONG).show();



                    JSONObject jsonObj = null;
                    try {
                        jsonObj = new JSONObject(string1);
                        String message = jsonObj.getString("status");

//                        Toast.makeText(UpdateUserProfile.this, message, Toast.LENGTH_SHORT).show();
                        Toast.makeText(UpdateUserProfile.this,"User Updated Successfully",Toast.LENGTH_LONG).show();

                        editor.putString(Shared.loggedIn_user_name,username);
                        editor.putString(Shared.loggedIn_user_email,useremail);
                        editor.putString(Shared.loggedIn_user_ph,usercontact);
                        editor.putString(Shared.loggedIn_user_gender,usergender);
                        editor.putString(Shared.loggedIn_user_occupation_id,useroccupation);
                        editor.apply();


                        Intent intent= new Intent(UpdateUserProfile.this,HomeScreen.class);
                        startActivity(intent);
                        finish();

                    } catch (JSONException e) {


                    }*/



                    JSONObject jsonObj = null;
                    try
                    {

                        jsonObj = new JSONObject(string1);
                        String message = jsonObj.getString("status");


                        if(message.equals("success"))
                        {
////                            Toast.makeText(UpdateUserProfile.this, usergender, Toast.LENGTH_SHORT).show();


                            Toast.makeText(UpdateUserProfile.this,getResources().getString(R.string.user_updated_Success),Toast.LENGTH_LONG).show();

                            editor.putString(Shared.loggedIn_user_name,username);
                            editor.putString(Shared.loggedIn_user_email,useremail);
                            editor.putString(Shared.loggedIn_user_ph,usercontact);
                            editor.putString(Shared.loggedIn_user_gender,usergender);
                            editor.putString(Shared.loggedIn_user_occupation_id,useroccupation);
                            editor.putString(Shared.loggedIn_user_city,city_str);
                            editor.apply();




                            //       progressDialog.dismiss();

                            //         Toast.makeText(UpdateUserProfile.this,string1,Toast.LENGTH_LONG).show();
                            Intent intent= new Intent(UpdateUserProfile.this,HomeScreen.class);
                            startActivity(intent);
                            finish();

                            progressDialog.dismiss();

                        }
                        else
                        {
                            Toast.makeText(UpdateUserProfile.this, getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        //         Toast.makeText(UpdateUserProfile.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }




                }

                @Override
                protected String doInBackground(Void... params) {

                    UpdateUserProfile.ImageProcessClass imageProcessClass = new UpdateUserProfile.ImageProcessClass();

                    HashMap<String,String> HashMapParams = new HashMap<String,String>();

                    // HashMapParams.put("image_tag", "image");
                    HashMapParams.put("image", ConvertImage);
                    HashMapParams.put("name", username);
                    HashMapParams.put("email", useremail);
                    HashMapParams.put("occupation", occupation_id);
                    HashMapParams.put("city", city_str);
                    HashMapParams.put("gender", usergender);



                    String FinalData = imageProcessClass.ImageHttpRequest(url, HashMapParams);

                    return FinalData;
                }
            }
            AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
            AsyncTaskUploadClassOBJ.execute();
        }
        else
        {

            drawable = (BitmapDrawable) Updateuserprofile_user_profile_image.getDrawable();

            if(drawable!= null)
            {
                FixBitmap = drawable.getBitmap();


                FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

                byteArray = byteArrayOutputStream.toByteArray();

                ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

                if(!ConvertImage.equals(""))
                {


                    // Toast.makeText(this, ConvertImage, Toast.LENGTH_SHORT).show();
/*            Toast.makeText(this, username, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, usercontact, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, usercity, Toast.LENGTH_SHORT).show();*/
                    //  Toast.makeText(this, useremail, Toast.LENGTH_SHORT).show();
  /*          Toast.makeText(this, useroccupation, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, usergender, Toast.LENGTH_SHORT).show();*/

                    //   Toast.makeText(this, occupation_id, Toast.LENGTH_SHORT).show();

                    class AsyncTaskUploadClass extends AsyncTask<Void,Void,String>
                    {

                        @Override
                        protected void onPreExecute() {

                            super.onPreExecute();

                            //         progressDialog = ProgressDialog.show(Academy_Reg.this,"Image is Uploading","Please Wait",false,false);
                        }

                        @Override
                        protected void onPostExecute(String string1) {

                            super.onPostExecute(string1);


                   //         Toast.makeText(UpdateUserProfile.this, string1, Toast.LENGTH_SHORT).show();

                            JSONObject jsonObj = null;
                            try {


                                jsonObj = new JSONObject(string1);
                                String message = jsonObj.getString("status");

                                if(message.equals("success"))
                                {
                                    progressDialog.dismiss();

//                                    Toast.makeText(UpdateUserProfile.this, usergender, Toast.LENGTH_SHORT).show();

                                    Toast.makeText(UpdateUserProfile.this,getResources().getString(R.string.user_updated_Success),Toast.LENGTH_LONG).show();
                                    //        Toast.makeText(UpdateUserProfile.this, message, Toast.LENGTH_SHORT).show();
                                    editor.putString(Shared.loggedIn_user_name,username);
                                    editor.putString(Shared.loggedIn_user_email,useremail);
                                    editor.putString(Shared.loggedIn_user_ph,usercontact);
                                    editor.putString(Shared.loggedIn_user_gender,usergender);
                                    editor.putString(Shared.loggedIn_user_occupation_id,useroccupation);
                                    editor.apply();




                                    //       progressDialog.dismiss();

                                    //         Toast.makeText(UpdateUserProfile.this,string1,Toast.LENGTH_LONG).show();
                                    Intent intent= new Intent(UpdateUserProfile.this,HomeScreen.class);
                                    startActivity(intent);
                                    finish();

                                }
                                else
                                {
                                    Toast.makeText(UpdateUserProfile.this, getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {

                                progressDialog.dismiss();
                          //      Toast.makeText(UpdateUserProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }




                        }

                        @Override
                        protected String doInBackground(Void... params) {

                            UpdateUserProfile.ImageProcessClass imageProcessClass = new UpdateUserProfile.ImageProcessClass();

                            HashMap<String,String> HashMapParams = new HashMap<String,String>();

                            // HashMapParams.put("image_tag", "image");
                            HashMapParams.put("image", ConvertImage);
                            HashMapParams.put("name", username);
                            if(useremail.equals(""))
                            {

                            }
                            else
                            {
                                HashMapParams.put("email", useremail);
                            }
                            HashMapParams.put("occupation", occupation_id);
                            HashMapParams.put("city", city_str);
                            HashMapParams.put("gender", usergender);

                            String FinalData = imageProcessClass.ImageHttpRequest(url, HashMapParams);

                            return FinalData;
                        }
                    }
                    AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
                    AsyncTaskUploadClassOBJ.execute();
                }
                else
                {
                    // Toast.makeText(this, ConvertImage, Toast.LENGTH_SHORT).show();
/*            Toast.makeText(this, username, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, usercontact, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, usercity, Toast.LENGTH_SHORT).show();*/
                    //  Toast.makeText(this, useremail, Toast.LENGTH_SHORT).show();
  /*          Toast.makeText(this, useroccupation, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, usergender, Toast.LENGTH_SHORT).show();*/

                    //   Toast.makeText(this, occupation_id, Toast.LENGTH_SHORT).show();

                    class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

                        @Override
                        protected void onPreExecute() {

                            super.onPreExecute();

                            //         progressDialog = ProgressDialog.show(Academy_Reg.this,"Image is Uploading","Please Wait",false,false);
                        }

                        @Override
                        protected void onPostExecute(String string1) {

                            super.onPostExecute(string1);



                            JSONObject jsonObj = null;
                            try {


                                jsonObj = new JSONObject(string1);
                                String message = jsonObj.getString("status");

                                if(message.equals("success"))
                                {
                                    progressDialog.dismiss();

////                                    Toast.makeText(UpdateUserProfile.this, usergender, Toast.LENGTH_SHORT).show();


                                    Toast.makeText(UpdateUserProfile.this,getResources().getString(R.string.user_updated_Success),Toast.LENGTH_LONG).show();

                                    editor.putString(Shared.loggedIn_user_name,username);
                                    editor.putString(Shared.loggedIn_user_email,useremail);
                                    editor.putString(Shared.loggedIn_user_ph,usercontact);
                                    editor.putString(Shared.loggedIn_user_gender,usergender);
                                    editor.putString(Shared.loggedIn_user_occupation_id,useroccupation);
                                    editor.apply();




                                    //       progressDialog.dismiss();

                                    //         Toast.makeText(UpdateUserProfile.this,string1,Toast.LENGTH_LONG).show();
                                    Intent intent= new Intent(UpdateUserProfile.this,HomeScreen.class);
                                    startActivity(intent);
                                    finish();

                                }
                                else
                                {
                                    Toast.makeText(UpdateUserProfile.this, getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {

                                progressDialog.dismiss();
                            }




                        }

                        @Override
                        protected String doInBackground(Void... params) {

                            UpdateUserProfile.ImageProcessClass imageProcessClass = new UpdateUserProfile.ImageProcessClass();

                            HashMap<String,String> HashMapParams = new HashMap<String,String>();

                            // HashMapParams.put("image_tag", "image");
                            HashMapParams.put("image", "");
                            HashMapParams.put("name", username);
                            HashMapParams.put("email", useremail);
                            HashMapParams.put("occupation", occupation_id);
                            HashMapParams.put("city", city_str);
                            HashMapParams.put("gender", usergender);

                            String FinalData = imageProcessClass.ImageHttpRequest(url, HashMapParams);
                            return FinalData;
                        }
                    }
                    AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
                    AsyncTaskUploadClassOBJ.execute();
                }
            }
            else
            {
                // Toast.makeText(this, ConvertImage, Toast.LENGTH_SHORT).show();
/*            Toast.makeText(this, username, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, usercontact, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, usercity, Toast.LENGTH_SHORT).show();*/
                //  Toast.makeText(this, useremail, Toast.LENGTH_SHORT).show();
  /*          Toast.makeText(this, useroccupation, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, usergender, Toast.LENGTH_SHORT).show();*/

                //   Toast.makeText(this, occupation_id, Toast.LENGTH_SHORT).show();

                class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

                    @Override
                    protected void onPreExecute() {

                        super.onPreExecute();

                        //         progressDialog = ProgressDialog.show(Academy_Reg.this,"Image is Uploading","Please Wait",false,false);
                    }

                    @Override
                    protected void onPostExecute(String string1) {

                        super.onPostExecute(string1);


                      //  Toast.makeText(UpdateUserProfile.this, string1, Toast.LENGTH_SHORT).show();

                        JSONObject jsonObj = null;
                        try {


                            jsonObj = new JSONObject(string1);
                            String message = jsonObj.getString("status");

                            if(message.equals("success"))
                            {
                                progressDialog.dismiss();

//                                Toast.makeText(UpdateUserProfile.this, usergender, Toast.LENGTH_SHORT).show();


                                Toast.makeText(UpdateUserProfile.this,getResources().getString(R.string.user_updated_Success),Toast.LENGTH_LONG).show();
                                //        Toast.makeText(UpdateUserProfile.this, message, Toast.LENGTH_SHORT).show();
                                editor.putString(Shared.loggedIn_user_name,username);
                                editor.putString(Shared.loggedIn_user_email,useremail);
                                editor.putString(Shared.loggedIn_user_ph,usercontact);
                                editor.putString(Shared.loggedIn_user_gender,usergender);
                                editor.putString(Shared.loggedIn_user_occupation_id,useroccupation);
                                editor.apply();




                                //       progressDialog.dismiss();

                                //         Toast.makeText(UpdateUserProfile.this,string1,Toast.LENGTH_LONG).show();
                                Intent intent= new Intent(UpdateUserProfile.this,HomeScreen.class);
                                startActivity(intent);
                                finish();

                            }
                            else
                            {
                                Toast.makeText(UpdateUserProfile.this, getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {

                            progressDialog.dismiss();
                         //   Toast.makeText(UpdateUserProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }




                    }

                    @Override
                    protected String doInBackground(Void... params) {

                        UpdateUserProfile.ImageProcessClass imageProcessClass = new UpdateUserProfile.ImageProcessClass();

                        HashMap<String,String> HashMapParams = new HashMap<String,String>();

                        // HashMapParams.put("image_tag", "image");
                        HashMapParams.put("image", "");
                        HashMapParams.put("name", username);
                        HashMapParams.put("email", useremail);
                        HashMapParams.put("occupation", occupation_id);
                        HashMapParams.put("city", city_str);
                        HashMapParams.put("gender", usergender);

                        String FinalData = imageProcessClass.ImageHttpRequest(url, HashMapParams);

                        return FinalData;
                    }
                }
                AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
                AsyncTaskUploadClassOBJ.execute();
            }


        }
    }

    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {
                newurl = new URL(requestURL);
                //Toast.makeText(UpdateUserProfile.this, jwt_token, Toast.LENGTH_SHORT).show();
                //Log.d("token","Authorization Bearer " + jwt_token);


                httpURLConnection = (HttpURLConnection) newurl.openConnection();

                httpURLConnection.setRequestProperty ("Authorization", "Bearer " + jwt_token);

                httpURLConnection.setReadTimeout(20000);

                httpURLConnection.setConnectTimeout(20000);

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoInput(true);

                httpURLConnection.setDoOutput(true);

                outputStream = httpURLConnection.getOutputStream();

                bufferedWriter = new BufferedWriter(

                        new OutputStreamWriter(outputStream, "UTF-8"));

                bufferedWriter.write(bufferedWriterDataFN(PData));

                bufferedWriter.flush();

                bufferedWriter.close();

                outputStream.close();

                RC = httpURLConnection.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReader.readLine()) != null){

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            stringBuilder = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {
                if (check)
                    check = false;
                else
                    stringBuilder.append("&");

                stringBuilder.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilder.append("=");

                stringBuilder.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilder.toString();
        }

    }

    public void captureImage() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, 7);

    }


    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getimagefromcamera();
               // Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }*/


  /*  public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_IMAGE_REQUEST) {

            Toast.makeText(getApplicationContext(), "Success",
                    Toast.LENGTH_SHORT).show();

            //Scan new image added
            MediaScannerConnection.scanFile(getApplicationContext(), new String[]{new File(Environment.getExternalStorageDirectory()
                    + "/AutoFare/" + imageName).getPath()}, new String[]{"image/png"}, null);


            // Work in few phones
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(Environment.getExternalStorageDirectory()
                        + "/AutoFare/" + imageName)));

            } else {
                getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse(Environment.getExternalStorageDirectory()
                        + "/AutoFare/" + imageName)));
            }
        } else {
            Toast.makeText(getApplicationContext(), "Take Picture Failed or canceled",
                    Toast.LENGTH_SHORT).show();
        }
    }*/


    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(UpdateUserProfile.this,
                Manifest.permission.CAMERA))
        {

        //    Toast.makeText(UpdateUserProfile.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();


        } else {

            ActivityCompat.requestPermissions(UpdateUserProfile.this,new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    //       Toast.makeText(UpdateUserProfile.this,"Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                  //  Toast.makeText(UpdateUserProfile.this,"Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}