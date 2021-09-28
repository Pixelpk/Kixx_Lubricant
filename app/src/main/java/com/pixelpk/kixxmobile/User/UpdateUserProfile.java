package com.pixelpk.kixxmobile.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.graphics.drawable.Drawable;
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
import android.util.Patterns;
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
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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

    String uname  =" ";
    String uphone =" ";
    String uemail =" ";
    String ucity  =" ";

    String[] occupation_array = {"Select Occupation","Others","Business","Job holder","Student"};

    JSONArray cars,ads;
    JSONObject objcars,objads,objcars_activity;
    List<String> user_cars_list,car_id_list;
    List<CarDetailsList> myListData;
    byte[] byteArray;
    String ConvertImage ="";

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

    String username,usercontact,useroccupation,usergender="",usercity,useremail;

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


                                }

                                else
                                {
                                    getimagefromcamera();
                                }
                            }
                        })
                        .show();
            }
        });

        Updatebtn.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v)
            {
                uname  = name.getText().toString();
                uemail = email.getText().toString();
                uphone = mobile.getText().toString();

                if(!uname.isEmpty())
                {
                    username = uname;
                }
                else
                {
                    username = "";
                }
                if(!uphone.isEmpty())
                {
                    usercontact = uphone;
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

                if(usergender!=null)
                {
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

                if(!uemail.isEmpty())
                {
                    if (!Patterns.EMAIL_ADDRESS.matcher(uemail).matches())
                    {
                        email.setError("Invalid email address");
                    }

                    else
                    {
                        email.setError(null);
                        useremail = uemail;
                        UploadImageToServer();
                    }
                }
                else
                {
                    email.setError(null);
                    useremail = "";
                    UploadImageToServer();
                }

            }
        });

        AddCarBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(UpdateUserProfile.this, AddCarScreen.class);
                startActivity(intent);
            }
        });

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                gender_str = gender.getSelectedItem().toString();
                //   Toast.makeText(UpdateUserProfile.this, gender_str, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        spinnerDialog_city.bindOnSpinerListener(new OnSpinerItemClick()
        {
            @Override
            public void onClick(String item, int position)
            {
                Updateuserprofile_City_TV.setText(item);
                city_str = item;
                //           Toast.makeText(UpdateUserProfile.this, city_str, Toast.LENGTH_SHORT).show();
            }
        });

        Updateuserprofile_City_LL.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //     ((TextView) findViewById(R.id.spinner_dropdown_tv_icon)).setTextColor(getResources().getColor(R.color.white));
                spinnerDialog_city.showSpinerDialog();
            }
        });

        occupation_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                occupation_str = occupation_sp.getSelectedItem().toString();
                occupation_id = String.valueOf(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        UpdateUserProfile_backarrow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

    }

    private void UploadImageToServer()
    {
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
//            progressDialog.setMessage("Updating Profile Please Wait");
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        if(city_str.equals("null"))
        {
            city_str = "";
        }

        if(usergender == null)
        {
            usergender = "";
        }

            if (FixBitmap != null)
            {
                FixBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byteArray = byteArrayOutputStream.toByteArray();
                ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

                String convert_str = ConvertImage.replace("/", "");

                Log.d("tag_img_profile", ConvertImage);
                Log.d("tag_img_profile_ch", convert_str);

                editor.putString("shared_convert_img",ConvertImage).apply();

//                writeToFile(ConvertImage,getApplicationContext());

                new Update_Profile().execute();
            }

            else
            {
                new Update_Profile().execute();
            }
    }


    private void writeToFile(String data,Context context)
    {

        try
        {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            Toast.makeText(context, "file created", Toast.LENGTH_SHORT).show();
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
        }
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

        mobile = findViewById(R.id.Updateuserprofile_mobile_ET);

        email = findViewById(R.id.Updateuserprofile_email_ET);
        Updateuserprofile_City_TV = findViewById(R.id.Updateuserprofile_City_TV);
        Updatebtn = findViewById(R.id.Updateuserprofile_update_Btn);
        AddCarBtn = findViewById(R.id.Updateuserprofile_addcar_Btn);
        updateprofile_back = findViewById(R.id.updateprofile_back);



        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_white_text,getResources().getStringArray(R.array.gender_arr));

        adapter.setDropDownViewResource(R.layout.spinner_style);

        rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

        ArrayAdapter<String> adapter_occupat;


        if(rtl.equals("1"))
        {
            adapter_occupat = new ArrayAdapter<String>(this,
                    R.layout.spinner_white_text,occupat_ar_title);

            adapter_occupat.setDropDownViewResource(R.layout.spinner_style);

            mobile.setGravity(Gravity.END);


            UpdateUserProfile_backarrow.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24_rwhite);
        }

        else
        {
            adapter_occupat = new ArrayAdapter<String>(this,
                    R.layout.spinner_white_text,occupat);
            adapter_occupat.setDropDownViewResource(R.layout.spinner_style);
        }

        gender.setAdapter(adapter);
        occupation_sp.setAdapter(adapter_occupat);


        occupation_data = new ArrayList<>();
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

        uname   = sharedPreferences.getString(Shared.loggedIn_user_name,"0");
        uphone  = sharedPreferences.getString(Shared.loggedIn_user_ph,"0");
        uemail  = sharedPreferences.getString(Shared.loggedIn_user_email,"0");
        ucity   = sharedPreferences.getString(Shared.loggedIn_user_city,"0");
        user_id = sharedPreferences.getString(Shared.loggedIn_user_id,"0");

        if(ucity.equals(""))
        {

        }
        else
        {
            city_str = ucity;
        }

        if(rtl.equals("1"))
        {
            Updateuserprofile_City_TV.setGravity(Gravity.START);
            occupation_sp.setGravity(Gravity.START);
            email.setGravity(Gravity.END);
            name.setGravity(Gravity.END);
            occupation_sp.setGravity(Gravity.END);
        }

        checkUserDate(uname,uphone,ucity,uemail);

        Updateuserprofile_user_profile_image = findViewById(R.id.Updateuserprofile_user_profile_image);

        byteArrayOutputStream = new ByteArrayOutputStream();

        user_profile_image = sharedPreferences.getString(Shared.user_profile_image,"0");
        loggedIn_user_city = sharedPreferences.getString(Shared.loggedIn_user_city,"0");
        occupation_id = sharedPreferences.getString(Shared.loggedIn_user_occupation_id,"0");
        gender_str_sharepref = sharedPreferences.getString(Shared.loggedIn_user_gender,"0");



        if(gender_str_sharepref.equals("null"))
        {
            usergender = gender_str;
        }

        else
        {
            usergender = gender_str_sharepref;
        }

        load_profile_img();

        if(!gender_str_sharepref.equals("null"))
        {
            if(gender_str_sharepref.equals(getResources().getString(R.string.male_gender)))
            {
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

        else
        {
            gender.setSelection(adapter.getPosition("Select Gender"));

        }

        if(occupation_id!=null || !occupation_id.equals("null"))
        {
            if(occupation_id.equals("0"))
            {
                if(rtl.equals("1"))
                {
                    occupation_sp.setSelection(adapter_occupat.getPosition("Select Occupation"));
                }
                else
                {
                    occupation_sp.setSelection(adapter_occupat.getPosition("Select Occupation"));
                }
            }
            else if(occupation_id.equals("1"))
            {
                occupation_sp.setSelection(adapter_occupat.getPosition("Others"));
            }
            else if(occupation_id.equals("2"))
            {
                occupation_sp.setSelection(adapter_occupat.getPosition("Student"));
            }
            else if(occupation_id.equals("3"))
            {
                occupation_sp.setSelection(adapter_occupat.getPosition("Job holder"));
            }
            else if(occupation_id.equals("4"))
            {
                occupation_sp.setSelection(adapter_occupat.getPosition("Business"));

            }
        }
        else
        {
            occupation_sp.setSelection(adapter_occupat.getPosition("Select Occupation"));
        }
        // With Animation

        spinnerDialog_city=new SpinnerDialog(UpdateUserProfile.this, city_list,"Select or Search City",R.style.DialogAnimations_SmileWindow,getResources().getString(R.string.cancel));
    }

    private void load_profile_img()
    {
//        progressDialog.setMessage("Please Wait! image is being Loaded");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Glide.with(getApplicationContext())
                .load(URLs.USER_IMAGE_URL + user_profile_image)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource)
                    {
                        progressDialog.dismiss();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressDialog.dismiss();
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(Updateuserprofile_user_profile_image);
    }


    class Update_Profile extends AsyncTask<Void, Void, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            //         progressDialog = ProgressDialog.show(Academy_Reg.this,"Image is Uploading","Please Wait",false,false);
        }

        @Override
        protected void onPostExecute(String string1)
        {

            super.onPostExecute(string1);
            JSONObject jsonObj = null;

            Log.d("profile_user_tag",string1);

            try
            {
                jsonObj = new JSONObject(string1);
                String message = jsonObj.getString("status");

                if(message.equals("success"))
                {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Profile Updated Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),HomeScreen.class);
                    startActivity(intent);
                    finish();
                }

                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Profile Update Failed", Toast.LENGTH_SHORT).show();
                }

            }

            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... params) {

            ImageProcessClass imageProcessClass = new ImageProcessClass();

            HashMap<String, String> HashMapParams = new HashMap<String, String>();

            HashMapParams.put("name", username);
            HashMapParams.put("email", useremail);
            HashMapParams.put("occupation", occupation_id);
            HashMapParams.put("city", city_str);
            HashMapParams.put("gender", usergender);
            HashMapParams.put("image", ConvertImage);


            String FinalData = imageProcessClass.ImageHttpRequest(url, HashMapParams);
            return FinalData;
        }
    }

    class Update_Profile_Data extends AsyncTask<Void, Void, String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            //         progressDialog = ProgressDialog.show(Academy_Reg.this,"Image is Uploading","Please Wait",false,false);
        }

        @Override
        protected void onPostExecute(String string1)
        {

            super.onPostExecute(string1);
            JSONObject jsonObj = null;

            Log.d("profile_user_tag",string1);

            try
            {
                jsonObj = new JSONObject(string1);
                String message = jsonObj.getString("status");

                if(message.equals("success"))
                {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Profile Updated Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),HomeScreen.class);
                    startActivity(intent);
                    finish();
                }

                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Profile Update Failed", Toast.LENGTH_SHORT).show();
                }

            }

            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... params) {

            ImageProcessClass imageProcessClass = new ImageProcessClass();

            HashMap<String, String> HashMapParams = new HashMap<String, String>();

            HashMapParams.put("name", username);
            HashMapParams.put("email", useremail);
            HashMapParams.put("occupation", occupation_id);
            HashMapParams.put("city", city_str);
            HashMapParams.put("gender", usergender);


            String FinalData = imageProcessClass.ImageHttpRequest(url, HashMapParams);
            return FinalData;
        }
    }

    public void get_occupation_data(final String token)
    {
//        progressDialog.setMessage("Please Wait While We Are Loading The Data");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.USER_PROFILE_OCCUPATION,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        progressDialog.dismiss();
                        try
                        {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");

                            if(message.equals("success"))
                            {
                                JSONArray data  = jsonObj.getJSONArray("resp");

                                for (int i = 0; i < data.length(); i++)
                                {
                                    JSONObject c = data.getJSONObject(i);
                                    String occupation = c.getString("occupation");
                                    occupation_data.add(occupation);
                                }
                            }
                            else
                            {
                                Toast.makeText(UpdateUserProfile.this,getResources().getString(R.string.user_not_found), Toast.LENGTH_SHORT).show();
                            }
                        }

                        catch (final JSONException e)
                        {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
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

                        } else if (error instanceof AuthFailureError) {
                            //TODO
                            //   Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            //        Toast.makeText(getActivity(), R.string.usernotfound, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            //TODO
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.servermaintainence), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NetworkError) {
                            //TODO
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();

                        } else if (error instanceof ParseError) {
                            //TODO
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.incorrectdata), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
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
        }

        if(strname.equals("null"))
        {

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


    private void disableEditText(EditText editText)
    {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
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
    }

    @Override
    protected void onActivityResult(int RC, int RQC, Intent I)
    {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null)
        {
            Uri uri = I.getData();

            try
            {
                FixBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Updateuserprofile_user_profile_image.setImageBitmap(FixBitmap);
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        else    if (RC == 7 && RQC == RESULT_OK)
        {
            Bitmap bitmap = (Bitmap) I.getExtras().get("data");

            FixBitmap = bitmap;

            Updateuserprofile_user_profile_image.setImageBitmap(bitmap);
        }
    }

    private Boolean validateUsernam()
    {
        String val_name = name.getText().toString();

        if(val_name.isEmpty())
        {
            username = "";
            return false;
        }

        else
        {
            name.setError(null);
            return true;
        }
    }

    private Boolean validateEmail()
    {
        String val_mail     = email.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val_mail.isEmpty())
        {
            useremail =" ";
            return false;
        }

        else if (!val_mail.matches(emailPattern))
        {
            email.setError("Invalid email address");
            return false;
        }

        else
        {
            email.setError(null);
            return true;
        }
    }


    public class ImageProcessClass
    {

        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData)
        {

            StringBuilder stringBuilder = new StringBuilder();

            try
            {
                newurl = new URL(requestURL);

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

    public void captureImage()
    {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 7);
    }


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


    public Uri getImageUri(Context inContext, Bitmap inImage)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}