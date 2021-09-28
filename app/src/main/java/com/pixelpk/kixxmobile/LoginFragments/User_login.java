package com.pixelpk.kixxmobile.LoginFragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.Settings;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.pixelpk.kixxmobile.Constants;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.ForgotPassword;
import com.pixelpk.kixxmobile.User.Fragments.HomeFragment;
import com.pixelpk.kixxmobile.User.HomeScreen;
import com.pixelpk.kixxmobile.User.MapsActivity;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
import com.pixelpk.kixxmobile.User.Signin;
import com.pixelpk.kixxmobile.User.Signup;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;
import static com.pixelpk.kixxmobile.User.Fragments.MapsFragment.MY_PERMISSIONS_REQUEST_LOCATION;


public class User_login extends Fragment {

    LinearLayout salesman_layout;
    ScrollView user_layout;
    TextView Signin_requestTV,Signin_forgotpass_TV,UserLogin_salesman_login,UserLogin_user_login;
    EditText Signin_userphET_txt,Signin_userpassET_txt,UserLogin_referral_ET;
    Button signin_userSignupBtn,Signin_userSigninBtn;
    String usercontact,userpassword;
    String refreshedToken,referral_code="";
    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Button signin_userSignunBtn;
    String visibility = "0";

    String phone;

    EditText Signin_countrycode_ET;

    String countrycode = "+966";
    String countrycode_seller = "+966";
    Fragment selectedFragment = null;

    EditText Signin_salesphET_txt,Signin_salespassET_txt;

    Button Signin_salesSigninBtn;
  /*  ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String refreshedToken;*/

    EditText Salesman_countrycode_ET;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  =  inflater.inflate(R.layout.fragment_user_login, container, false);

        InitializeView(view);

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_LOCATION);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        refreshedToken = task.getResult().getToken();
                     //   Toast.makeText(getContext(), refreshedToken, Toast.LENGTH_SHORT).show();

                        // Log and toast
                        //   String msg = getString(R.string.msg_token_fmt, token);
                        //  Log.d(TAG, msg);
                        //  Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        InitializeViewSalesman(view);

        Signin_requestTV.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showSettingsAlert();
            }
        });

        Salesman_countrycode_ET.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                CountryPicker picker = CountryPicker.newInstance("Select Country");  // dialog title
                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {

                        countrycode_seller = dialCode;
                        Salesman_countrycode_ET.setText(countrycode_seller);
                        /* Toast.makeText(getContext(), code + " " + dialCode, Toast.LENGTH_SHORT).show();*/
                        picker.dismiss();

                    }
                });
                picker.show(getFragmentManager(), "COUNTRY_PICKER");
            }
        });




        Signin_countrycode_ET.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                CountryPicker picker = CountryPicker.newInstance("Select Country");  // dialog title
                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {

                        countrycode = dialCode;
                        Signin_countrycode_ET.setText(countrycode);
                       /* Toast.makeText(getContext(), code + " " + dialCode, Toast.LENGTH_SHORT).show();*/

                        picker.dismiss();

                    }
                });
                picker.show(getFragmentManager(), "COUNTRY_PICKER");
            }
        });

        UserLogin_user_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_layout.setVisibility(View.VISIBLE);
                salesman_layout.setVisibility(View.GONE);
            }
        });



        Signin_userpassET_txt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE, "0");

                if(!rtl.equals("1"))
                {
                  //  Toast.makeText(getContext(), "ltr", Toast.LENGTH_SHORT).show();
                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;

                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        if(event.getRawX() >= (Signin_userpassET_txt.getRight() - Signin_userpassET_txt.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            // your action here
                            if(visibility.equals("0"))
                            {
                                Signin_userpassET_txt.setTransformationMethod(null);
                                visibility = "1";
                            }
                            else
                            {
                                Signin_userpassET_txt.setTransformationMethod(new PasswordTransformationMethod());
                                visibility = "0";

                            }


                            return true;
                        }
                    }
                    return false;
                }
                else
                {

                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;

                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        if(event.getRawX() <= (Signin_userpassET_txt.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width()))  {    // your action here
                            if(visibility.equals("0"))
                            {
                                Signin_userpassET_txt.setTransformationMethod(null);
                                visibility = "1";
                            }
                            else
                            {
                                Signin_userpassET_txt.setTransformationMethod(new PasswordTransformationMethod());
                                visibility = "0";

                            }


                            return true;
                        }
                    }
                    return false;
                }


            }
        });




        Signin_salespassET_txt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE, "0");

                if(!rtl.equals("1"))
                {
                    //  Toast.makeText(getContext(), "ltr", Toast.LENGTH_SHORT).show();
                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;

                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        if(event.getRawX() >= (Signin_salespassET_txt.getRight() - Signin_salespassET_txt.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            // your action here
                            if(visibility.equals("0"))
                            {
                                Signin_salespassET_txt.setTransformationMethod(null);
                                visibility = "1";
                            }
                            else
                            {
                                Signin_salespassET_txt.setTransformationMethod(new PasswordTransformationMethod());
                                visibility = "0";

                            }


                            return true;
                        }
                    }
                    return false;
                }
                else
                {

                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;

                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        if(event.getRawX() <= (Signin_salespassET_txt.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width()))  {    // your action here
                            if(visibility.equals("0"))
                            {
                                Signin_salespassET_txt.setTransformationMethod(null);
                                visibility = "1";
                            }
                            else
                            {
                                Signin_salespassET_txt.setTransformationMethod(new PasswordTransformationMethod());
                                visibility = "0";

                            }


                            return true;
                        }
                    }
                    return false;
                }


            }
        });


        signin_userSignunBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Signup.class);
                startActivity(intent);
            }
        });

        Signin_forgotpass_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), ForgotPassword.class);
                startActivity(intent);
            }
        });

        Signin_userphET_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signin_userphET_txt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E3580B")));
            }
        });

        Signin_userSigninBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                usercontact = countrycode + Signin_userphET_txt.getText().toString();
                userpassword = Signin_userpassET_txt.getText().toString();
                String only_phone = Signin_userphET_txt.getText().toString();

//                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                if(usercontact.equals(""))
                {
                    Signin_userphET_txt.setError(getResources().getString(R.string.pleaseenterphonenumber));
                }
                else if (userpassword.equals(""))
                {
                    Signin_userpassET_txt.setError(getResources().getString(R.string.pleaseenterpassword));
                }



                else
                {


                    String s = only_phone.substring(0,1);

                    if(s.equals("0"))
                    {
                        Toast.makeText(getActivity(), getResources().getString(R.string.zero_error), Toast.LENGTH_SHORT).show();
                    }

                    else if(Signin_userphET_txt.getText().toString().equals(""))
                    {
                        Signin_userphET_txt.setError(getResources().getString(R.string.fill_fields));
                    }
                    else if(userpassword.length()<3)
                    {
                        Signin_userpassET_txt.setError(getResources().getString(R.string.passwordmusthavethreecharacters));
                    }
               /* else if (Signin_userphET_txt.getText().toString().charAt(0) != '0') {
                    // Signup_userpassET_txt.setError("Please fill this field");
                    // Toast.makeText(getContext(), String.valueOf(Signin_userphET_txt.getText().toString().charAt(0)), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), getResources().getString(R.string.phone_number_start_warning), Toast.LENGTH_SHORT).show();
                }*/
                    else if(refreshedToken == null)
                    {
                        Toast.makeText(getContext(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                    }
                    else {

                        Log.d("fcm",refreshedToken);

                        SigninUser(usercontact,userpassword,refreshedToken);

                    }
                }
            }
        });

        UserLogin_salesman_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                salesman_layout.setVisibility(View.VISIBLE);
                user_layout.setVisibility(View.GONE);
            }
        });

        signin_userSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send Data to server
                // Register User

            usercontact = Signin_userphET_txt.getText().toString();
            userpassword = Signin_userpassET_txt.getText().toString();

                if(!UserLogin_referral_ET.getText().toString().equals(""))
                {
                    if(UserLogin_referral_ET.getText().toString().contains(" "))
                    {
                        Toast.makeText(getContext(), getResources().getString(R.string.invalidreferralcode), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        referral_code = UserLogin_referral_ET.getText().toString();
                        User_Text_Field_Validity_check();
                    }

                }
                else
                {
                    User_Text_Field_Validity_check();
                }
            }
        });

//        Salesman_countrycode_ET.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//                CountryPicker picker = CountryPicker.newInstance("Select Country");  // dialog title
//                picker.setListener(new CountryPickerListener() {
//                    @Override
//                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID)
//                    {
//                        Salesman_countrycode_ET.setText(dialCode);
//                        picker.dismiss();
//                    }
//                });
//                picker.show(getFragmentManager(), "COUNTRY_PICKER");
//            }
//        });

        Signin_salesSigninBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
             /*   Intent intent = new Intent(getContext(), HomeScreen.class);
                startActivity(intent);*/


                String only_phone = Signin_salesphET_txt.getText().toString();

                phone = countrycode_seller + Signin_salesphET_txt.getText().toString();



                if(Signin_salesphET_txt.getText().toString().equals(""))
                {
                    Toast.makeText(getContext(),  getResources().getString(R.string.pleaseenterphonenumber), Toast.LENGTH_SHORT).show();
                }
                else if(Signin_salespassET_txt.getText().toString().equals(""))
                {
                    Toast.makeText(getContext(),  getResources().getString(R.string.pleaseenterpassword), Toast.LENGTH_SHORT).show();
                }
                else if(refreshedToken.equals(""))
                {
                  //  Toast.makeText(getContext(), "Network Problem! Please check your internet connection and restart app", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                }



                else
                {
                String s = only_phone.substring(0,1);

                if(s.equals("0"))
                {
                    Toast.makeText(getContext(), getResources().getString(R.string.zero_error), Toast.LENGTH_SHORT).show();
                }
                 //   Toast.makeText(getContext(), phone, Toast.LENGTH_SHORT).show();

//                    Toast.makeText(getContext(), phone, Toast.LENGTH_SHORT).show();
                else
                {
                    SigninSales(phone,Signin_salespassET_txt.getText().toString(),refreshedToken);

                }
                }

            }
        });



        return view;
    }

    public void InitializeView(View view)
    {
        Signin_requestTV = view.findViewById(R.id.Signin_requestTV);
        Signin_forgotpass_TV = view.findViewById(R.id.Signin_forgotpass_TV);
        signin_userSignupBtn = view.findViewById(R.id.signin_userSignupBtn);
        Signin_userSigninBtn = view.findViewById(R.id.Signin_userSigninBtn);
        Signin_userphET_txt = view.findViewById(R.id.Signin_userphET_txt);
        Signin_userpassET_txt = view.findViewById(R.id.Signin_userpassET_txt);
        UserLogin_referral_ET = view.findViewById(R.id.UserLogin_referral_ET);

        signin_userSignunBtn = view.findViewById(R.id.signin_userSignunBtn);

        progressDialog = new ProgressDialog(getContext());

        sharedPreferences = getContext().getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();


        //refreshedToken = FirebaseInstanceId.getInstance().getToken();
      //  Toast.makeText(getContext(), refreshedToken, Toast.LENGTH_SHORT).show();
        Signin_countrycode_ET = view.findViewById(R.id.Signin_countrycode_ET);

        Salesman_countrycode_ET = view.findViewById(R.id.Salesman_countrycode_ET);

        Signin_countrycode_ET.setFocusable(false);
        Signin_countrycode_ET.setClickable(true);

        /*editor.putString(Shared.KIXX_APP_LANGUAGE,"");
        editor.putString(Shared.KIXX_APP_LANGUAGE_ISSET,"");*/

        UserLogin_salesman_login = view.findViewById(R.id.UserLogin_salesman_login);

        user_layout = view.findViewById(R.id.User_login);
        salesman_layout = view.findViewById(R.id.Salesman_login);

        UserLogin_user_login = view.findViewById(R.id.UserLogin_user_login);

        Signin_countrycode_ET.setText("+966");

        Salesman_countrycode_ET.setText("+966");
    }


    public void InitializeViewSalesman(View view)
    {
        Signin_salesphET_txt = view.findViewById(R.id.Signin_salesphET_txt);
        Signin_salespassET_txt = view.findViewById(R.id.Signin_salespassET_txt);

        Signin_salesSigninBtn = view.findViewById(R.id.Signin_salesSigninBtn);

        progressDialog = new ProgressDialog(getContext());

        sharedPreferences = getContext().getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Salesman_countrycode_ET = view.findViewById(R.id.Salesman_countrycode_ET);

        Salesman_countrycode_ET.setFocusable(false);
        Salesman_countrycode_ET.setClickable(true);


    }


    public void RegisterUser(final String contact, final String password, final String fcm_id, final String referral)
    {

     //   Toast.makeText(getContext(), referral, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), fcm_id, Toast.LENGTH_SHORT).show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.USER_SIGNUP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    // Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();

                     if(response.contains("Duplicate"))
                     {
                         Toast.makeText(getActivity(), getResources().getString(R.string.useralreadyregistered), Toast.LENGTH_SHORT).show();
                     }
                     else
                     {
                         Toast.makeText(getActivity(), getResources().getString(R.string.usersuccessfullyregistered), Toast.LENGTH_SHORT).show();

                         try
                         {
                             JSONObject jsonObj = new JSONObject(response);
                             // JSONObject jsonObj_userexist = new JSONObject(response);
                             String user_exist_check = jsonObj.getString("message");

                             if(user_exist_check.equals("Login Successfull"))
                             {

                                 String jwt_token = jsonObj.getString("jwt_token");
                                 String message = jsonObj.getString("message");



                                 editor.putString(Shared.loggedIn_jwt,jwt_token).apply();

                                 //             Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                 //      Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                                 JSONObject data = jsonObj.getJSONObject("userdata");


                                 String user_id = data.getString("userid");

                                 editor.putString(Shared.loggedIn_user_id, user_id);


                                 if(user_id.length() == 1)
                                 {
                                     // Toast.makeText(getActivity(), "KX000" + user_id, Toast.LENGTH_SHORT).show();
                                     editor.putString(Shared.LoggedIn_fromatted_userid, /*"KX000" + */user_id);
                                 }
                                 else if(user_id.length() == 2)
                                 {
                                     // Toast.makeText(getActivity(), "KX00" + user_id, Toast.LENGTH_SHORT).show();
                                     editor.putString(Shared.LoggedIn_fromatted_userid, /*"KX00" +*/ user_id);
                                 }
                                 else if(user_id.length() == 3)
                                 {
                                     //    Toast.makeText(getActivity(), "KX0" + user_id, Toast.LENGTH_SHORT).show();
                                     editor.putString(Shared.LoggedIn_fromatted_userid, /*"KX0" +*/ user_id);
                                 }
                                 else
                                 {
                                     // Toast.makeText(getActivity(), "KX" + user_id, Toast.LENGTH_SHORT).show();
                                     editor.putString(Shared.LoggedIn_fromatted_userid, /*"KX" +*/ user_id);
                                 }


                                 editor.putString(Shared.User_login_logout_status,"1");
                                 editor.putString(Shared.User_promo,"1");
                                 editor.apply();

                                 Intent intent = new Intent(getContext(), HomeScreen.class);
                                 startActivity(intent);
                                 ((Activity)getActivity()).finish();


                             }
                             else
                             {
                                 Toast.makeText(getContext(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                             }


                         } catch (final JSONException e) {
                             getActivity().runOnUiThread(new Runnable() {
                                 @Override
                                 public void run() {

                                   /*  Toast.makeText(getContext(),
                                             "Json parsing error: " + e.getMessage(),
                                             Toast.LENGTH_LONG).show();*/
                                 }
                             });
                         }

                     }



                        progressDialog.dismiss();
                        // Toast.makeText(Signup.this, response, Toast.LENGTH_SHORT).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();

                        } else if (error instanceof AuthFailureError) {
                            //TODO
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getActivity(), R.string.usernotfound, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            //TODO
                            Toast.makeText(getActivity(), getResources().getString(R.string.servermaintainence), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NetworkError) {
                            //TODO
                            Toast.makeText(getActivity(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            //TODO
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            //  Toast.makeText(getActivity(), getResources().getString(R.string.incorrectdata), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();


                parameters.put("phone", contact);
                parameters.put("password", password);
                parameters.put("fcm_id", fcm_id);
                parameters.put("referral",referral.toUpperCase());

                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }






    public void SigninUser(final String contact, final String password, final String fcm_id)
    {


     //  Toast.makeText(getContext(), password, Toast.LENGTH_SHORT).show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.USER_SIGNIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.d("tag_login_res",response);
             //          Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                            try
                            {
                                JSONObject jsonObj = new JSONObject(response);
                                // JSONObject jsonObj_userexist = new JSONObject(response);
                                String user_exist_check = jsonObj.getString("message");


                                if (user_exist_check.equals("Login Successfull"))
                                {

                                    String jwt_token = jsonObj.getString("jwt_token");
                                    String message = jsonObj.getString("message");


                                    editor.putString(Shared.loggedIn_jwt, jwt_token).apply();

                                    //             Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                    //      Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                                    JSONObject data = jsonObj.getJSONObject("userdata");


                                    String user_id = data.getString("userid");

                                    editor.putString(Shared.loggedIn_user_id, user_id);


                                    if (user_id.length() == 1) {
                                        // Toast.makeText(getActivity(), "KX000" + user_id, Toast.LENGTH_SHORT).show();
                                        editor.putString(Shared.LoggedIn_fromatted_userid, /*"KX000" +*/ user_id);
                                    } else if (user_id.length() == 2) {
                                        // Toast.makeText(getActivity(), "KX00" + user_id, Toast.LENGTH_SHORT).show();
                                        editor.putString(Shared.LoggedIn_fromatted_userid, /*"KX00" +*/ user_id);
                                    } else if (user_id.length() == 3) {
                                        //    Toast.makeText(getActivity(), "KX0" + user_id, Toast.LENGTH_SHORT).show();
                                        editor.putString(Shared.LoggedIn_fromatted_userid, /*"KX0" +*/ user_id);
                                    } else {
                                        // Toast.makeText(getActivity(), "KX" + user_id, Toast.LENGTH_SHORT).show();
                                        editor.putString(Shared.LoggedIn_fromatted_userid, /*"KX" +*/ user_id);
                                    }


                                    editor.putString(Shared.User_login_logout_status, "1");
                                    editor.putString(Shared.User_promo, "1");
                                    editor.apply();

                                    Intent intent = new Intent(getContext(), HomeScreen.class);
                                    startActivity(intent);
                                    ((Activity) getActivity()).finish();
                                    Toast.makeText(getContext(), getResources().getString(R.string.welcometokixx), Toast.LENGTH_LONG).show();

                                }

                                else
                                {
                                    //  Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                                  Toast.makeText(getContext(), R.string.usernotfound, Toast.LENGTH_SHORT).show();
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
                      //  Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                        //   progressDialog.dismiss();\
                        progressDialog.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();

                        } else if (error instanceof AuthFailureError) {
                            //TODO

                            Toast.makeText(getActivity(), getResources().getString(R.string.usernotfound), Toast.LENGTH_SHORT).show();
                       //    Toast.makeText(getActivity(), R.string.usernotfound, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            //TODO
                            Toast.makeText(getActivity(), getResources().getString(R.string.servermaintainence), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NetworkError) {
                            //TODO
                            Toast.makeText(getActivity(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            //TODO
                            Toast.makeText(getActivity(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                     //       Toast.makeText(getActivity(), getResources().getString(R.string.incorrectdata), Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("phone",contact);
                parameters.put("password", password);
                parameters.put("fcm_id", fcm_id);


                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }



    public void User_Text_Field_Validity_check()
    {
        if(usercontact.equals(""))
        {
            Signin_userphET_txt.setError(getResources().getString(R.string.pleaseenterphonenumber));
        }
        else if (userpassword.equals(""))
        {
            Signin_userpassET_txt.setError(getResources().getString(R.string.pleaseenterpassword));
        }
        else
        {

            RegisterUser(usercontact,userpassword,refreshedToken,referral_code);
        }

    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        // Setting Dialog Title
        alertDialog.setTitle(R.string.alert_title);

        // Setting Dialog Message
        alertDialog.setMessage(R.string.alert_decs);

        // On pressing the Settings button.
        alertDialog.setPositiveButton(R.string.proceed, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        // On pressing the cancel button
        alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }



    public void SigninSales(final String contact, final String password, final String fcm_id)
    {
//        Toast.makeText(getActivity(), contact, Toast.LENGTH_SHORT).show();

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.SALES_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //            Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();


                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            // JSONObject jsonObj_userexist = new JSONObject(response);
                            String user_exist_check = jsonObj.getString("message");


                            if(user_exist_check.equals("Login Successfull"))
                            {
                                progressDialog.dismiss();
                                String jwt_token = jsonObj.getString("jwt_token");
                                String message = jsonObj.getString("message");

                                // Toast.makeText(getActivity(), jwt_token, Toast.LENGTH_SHORT).show();

                                editor.putString(Shared.sales_loggedIn_jwt,jwt_token).apply();

                                Log.d("tag_token_jwt",jwt_token);

                                //  Toast.makeText(getContext(), jwt_token, Toast.LENGTH_SHORT).show();
                                //      Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                                JSONObject data = jsonObj.getJSONObject("userdata");
                                for (int i = 0; i < 1; i++) {

                                    String user_id = data.getString("salesman_id");
                                    String shop_id = data.getString("shop_id");
                                    //Toast.makeText(getActivity(), user_id, Toast.LENGTH_SHORT).show();
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

                                    editor.putString(Shared.loggedIn_sales_id, user_id);
                                    editor.putString(Shared.loggedIn_sales_shopid, shop_id);
                                    editor.putString(Shared.sales_loggedIn_user_status,"1");
                                    //    editor.putString(Shared.loggedIn_user_id,);
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
                                Intent intent = new Intent(getContext(), com.pixelpk.kixxmobile.Salesman.HomeScreen.class);
                                intent.putExtra("promotion","1");
                                startActivity(intent);
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), R.string.usernotfound, Toast.LENGTH_SHORT).show();
                            }

                        } catch (final JSONException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                   /* Toast.makeText(getContext(),
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
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();

                        } else if (error instanceof AuthFailureError) {
                            //TODO
                            //     Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getActivity(), R.string.usernotfound, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            //TODO
                            Toast.makeText(getActivity(), getResources().getString(R.string.servermaintainence), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NetworkError) {
                            //TODO
                            Toast.makeText(getActivity(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            //TODO
                            Toast.makeText(getActivity(), getResources().getString(R.string.incorrectdata), Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("phone",contact);
                parameters.put("password", password);
                parameters.put("fcm_id", fcm_id);


                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }


}