package com.pixelpk.kixxmobile.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.iid.FirebaseInstanceId;
import com.pixelpk.kixxmobile.Constants;
import com.pixelpk.kixxmobile.Login;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.TermsScreen;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Signup extends AppCompatActivity {

    TextView Signup_signinTV;
    Button Signup_userSignupBtn;
    EditText Signup_userphET_txt,Signup_userpassET_txt;
    int check_field;
    String refreshedToken="";
    ProgressDialog progressDialog;

    String usercontact,userpassword;
    String referral_code="";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText Signup_countrycode_ET,editTextNumber4,editTextNumber3,editTextNumber2,editTextNumber;
    String uppercase_referral;
    CheckBox Signup_termsnconditions;

    String countycode="+966";
    String phone = "",zeroexcludedphonenumber;

    boolean termsandcondition_set = false;

    TextView Signup_termsandconditions_tv;

    String terms_en = "https://syedu1.sg-host.com/Kixx-App/terms_en.pdf";
    String terms_ar = "https://syedu1.sg-host.com/Kixx-App/term_ar.pdf";

    TextView Signup_alreadyauser_tv;

    TextView Signup_referralcode_tv;
    TextView Signup_signup_tv;

    String visibility = "0";

    //Handle Button Clicks
    private long mLastClickTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        initializingViews();

        refreshedToken = FirebaseInstanceId.getInstance().getToken();

      //  Toast.makeText(this, refreshedToken, Toast.LENGTH_SHORT).show();
        Signup_termsandconditions_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                String lang = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");
                //  editor.putString(Shared.User_promo,"2").apply();

                //  Toast.makeText(this, lang, Toast.LENGTH_SHORT).show();

              /*  if(lang!=null) {
                    if (lang.equals("1")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);

                        intent.setDataAndType(Uri.parse( terms_ar), "text/html");

                        startActivity(intent);

                    } else if (lang.equals("2")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);

                        intent.setDataAndType(Uri.parse(terms_en), "text/html");

                        startActivity(intent);
                    }*/
               // }

                Intent intent = new Intent(Signup.this, TermsScreen.class);
                startActivity(intent);
            }
        });

        Signup_signinTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);

            }
        });



        editTextNumber4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(editTextNumber4.getText().toString().length()==1)     //size as per your requirement
                {
                    editTextNumber3.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        editTextNumber3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(editTextNumber3.getText().toString().length()==1)     //size as per your requirement
                {
                    editTextNumber2.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        editTextNumber2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(editTextNumber2.getText().toString().length()==1)     //size as per your requirement
                {
                    editTextNumber.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        Signup_userpassET_txt.setOnTouchListener(new View.OnTouchListener() {
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
                        if(event.getRawX() >= (Signup_userpassET_txt.getRight() - Signup_userpassET_txt.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            // your action here
                            if(visibility.equals("0"))
                            {
                                Signup_userpassET_txt.setTransformationMethod(null);
                                visibility = "1";
                            }
                            else
                            {
                                Signup_userpassET_txt.setTransformationMethod(new PasswordTransformationMethod());
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
                        if(event.getRawX() <= (Signup_userpassET_txt.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width()))  {    // your action here
                            if(visibility.equals("0"))
                            {
                                Signup_userpassET_txt.setTransformationMethod(null);
                                visibility = "1";
                            }
                            else
                            {
                                Signup_userpassET_txt.setTransformationMethod(new PasswordTransformationMethod());
                                visibility = "0";

                            }


                            return true;
                        }
                    }
                    return false;
                }


            }
        });


        Signup_termsnconditions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    termsandcondition_set = true;
                }
                else
                {
                    termsandcondition_set = false;
                }
            }
        });



        Signup_countrycode_ET.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                CountryPicker picker = CountryPicker.newInstance("Select Country");  // dialog title
                picker.setListener(new CountryPickerListener()
                {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID)
                    {
                        // Implement your code here
                        // Signup_countrycode_SP.setSel

                        countycode = dialCode;
                   //     Toast.makeText(Signup.this, countycode, Toast.LENGTH_SHORT).show();
                        Signup_countrycode_ET.setText(dialCode);

                        // countycode = dialCode;
                        picker.dismiss();
                      //  Signup_countrycode_ET.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(Signup.this,flagDrawableResID), null, null , null);

                    }
                });
                picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
            }
        });



        Signup_userSignupBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                check_field = if_field_Empty();

          //      Toast.makeText(Signup.this, String.valueOf(check_field), Toast.LENGTH_SHORT).show();

                if(check_field == 4)
                {
                    String only_phone = Signup_userphET_txt.getText().toString();

                    String s = only_phone.substring(0,1);

//                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                    if(s.equals("0"))
                    {
                        Toast.makeText(getApplicationContext(), "The number should not start with 0", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        phone = countycode + only_phone;

                        zeroexcludedphonenumber = countycode + Signup_userphET_txt.getText().toString().substring(1);

                        if(!editTextNumber4.getText().toString().equals("") || !editTextNumber4.getText().toString().equals("") ||
                                !editTextNumber4.getText().toString().equals("") || !editTextNumber4.getText().toString().equals(""))
                        {

                            referral_code = editTextNumber4.getText().toString() +
                                    editTextNumber3.getText().toString() +
                                    editTextNumber2.getText().toString() +
                                    editTextNumber.getText().toString();

                            //         Toast.makeText(Signup.this, referral_code, Toast.LENGTH_SHORT).show();

                            if(referral_code.equals(""))
                            {
                                Toast.makeText(Signup.this, getResources().getString(R.string.invalidreferralcode), Toast.LENGTH_SHORT).show();
                            }
                            else
                            {


                                if(termsandcondition_set == true) {
                                    usercontact = Signup_userphET_txt.getText().toString();
                                    userpassword = Signup_userpassET_txt.getText().toString();





                                    //    Toast.makeText(Signup.this, referral_code, Toast.LENGTH_SHORT).show();

                                    AuthenticateContactNumber(phone, userpassword, refreshedToken, referral_code);

                           /* Intent intent = new Intent(Signup.this,HomeScreen.class);
                            startActivity(intent);*/
                                }
                                else
                                {
                                    Toast.makeText(Signup.this, getResources().getString(R.string.pleaseagreetoterms), Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                        else
                        {

                            if(termsandcondition_set == true)
                            {
                                usercontact = Signup_userphET_txt.getText().toString();
                                userpassword = Signup_userpassET_txt.getText().toString();

                                //    Toast.makeText(Signup.this, referral_code, Toast.LENGTH_SHORT).show();

//                                progressDialog.setCanceledOnTouchOutside(false);
//                                progressDialog.show();
//                                progressDialog.setContentView(R.layout.progress_layout);
//                                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                AuthenticateContactNumber(phone, userpassword, refreshedToken, referral_code);

                            }

                            else
                            {
                                Toast.makeText(Signup.this,  getResources().getString(R.string.pleaseagreetoterms), Toast.LENGTH_SHORT).show();
                            }
                        /*Intent intent = new Intent(Signup.this,HomeScreen.class);
                        startActivity(intent);*/
                        }
                    }
                }


            }
        });



    }

    public void initializingViews()
    {
        Signup_signinTV = findViewById(R.id.Signup_signinTV);
        Signup_userSignupBtn = findViewById(R.id.Signup_userSignupBtn);
        Signup_userphET_txt = findViewById(R.id.Signup_userphET_txt);
        Signup_userpassET_txt = findViewById(R.id.Signup_userpassET_txt);

        progressDialog = new ProgressDialog(Signup.this);

        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Signup_countrycode_ET = findViewById(R.id.Signup_countrycode_ET);



        Signup_countrycode_ET.setFocusable(false);
        Signup_countrycode_ET.setClickable(true);

        editTextNumber4 = findViewById(R.id.editTextNumber4);
        editTextNumber3 = findViewById(R.id.editTextNumber3);
        editTextNumber2 = findViewById(R.id.editTextNumber2);
        editTextNumber = findViewById(R.id.editTextNumber);

        Signup_termsnconditions = findViewById(R.id.Signup_termsnconditions);

        Signup_termsandconditions_tv = findViewById(R.id.Signup_termsandconditions_tv);
        Signup_signup_tv = findViewById(R.id.Signup_signup_tv);

        Signup_alreadyauser_tv = findViewById(R.id.Signup_alreadyauser_tv);

        Signup_referralcode_tv = findViewById(R.id.Signup_referralcode_tv);

        String lang = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

        Signup_countrycode_ET.setText("+966");
        //  editor.putString(Shared.User_promo,"2").apply();

        //  Toast.makeText(this, lang, Toast.LENGTH_SHORT).show();

        if(lang!=null)
        {
            if(lang.equals("1"))
            {
                setApplicationlanguage("ar");
                rtl_switch();


            }
            else if(lang.equals("2"))
            {
                setApplicationlanguage("en");
                rtl_switch();

            }
        }





    }

    public int if_field_Empty() {
        if (Signup_userphET_txt.getText().toString().equals("")) {
            Signup_userphET_txt.setError(getResources().getString(R.string.fill_fields));
            return 1;

        } else if (Signup_userpassET_txt.getText().toString().equals("")) {
            Signup_userpassET_txt.setError(getResources().getString(R.string.fill_fields));
            return 2;
        }
        else if(Signup_userpassET_txt.getText().toString().length()<3)
        {

            Toast.makeText(this,  getResources().getString(R.string.passwordmusthavethreecharacters), Toast.LENGTH_SHORT).show();
            return 6;
        }
        /*else if (Signup_userphET_txt.getText().toString().charAt(0) != '0') {
           // Signup_userpassET_txt.setError("Please fill this field");
            Toast.makeText(this,  getResources().getString(R.string.phonenumbermuststartwith), Toast.LENGTH_SHORT).show();
            return 5;
        }*/
        else if(refreshedToken==null)
        {
            Toast.makeText(this, getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
            return 7;
        }
        else if (refreshedToken.equals("")) {
            Toast.makeText(this, getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
            return 3;
        }  else {
        return 4;
    }}

/*    private void Signup_user() {

      //  Toast.makeText(this, refreshedToken, Toast.LENGTH_SHORT).show();


        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.USER_SIGNUP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


//                      Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();
                       // Toast.makeText(Signup.this, response, Toast.LENGTH_SHORT).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   progressDialog.dismiss();
                        Toast.makeText(Signup.this, error.toString(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                })
        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();


                parameters.put("phone", Signup_userphET_txt.getText().toString());
                parameters.put("password", Signup_userpassET_txt.getText().toString());
                parameters.put("fcm_id", refreshedToken);
                parameters.put("referral", "");

                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(Signup.this);
        requestQueue.add(stringRequest);


    }*/



    public void AuthenticateContactNumber(final String contact, final String password, final String fcm_id, final String referral)
    {
//        Toast.makeText(getApplicationContext(), contact, Toast.LENGTH_SHORT).show();
//        progressDialog.setMessage("Please Wait! we are signing you up");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.signup_user_validation_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.d("tag_signup_user",response);

                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);

                            String message     = jsonObject.getString("status");
                            String res_referal = jsonObject.getString("response");

                            if(message.equals("success"))
                            {

                                progressDialog.dismiss();

                                editor.putString(Constants.Signup_cont,phone);
                                editor.putString(Constants.Signup_password,password);
                                editor.putString(Constants.Signup_fcmid,fcm_id);
                                editor.putString(Constants.Signup_referral,referral);
                                editor.apply();

                                sendcode(phone,zeroexcludedphonenumber);
                                progressDialog.dismiss();
                            }

                            else
                            {
                                if(res_referal.equals("Unknown Referral"))
                                {
                                    progressDialog.dismiss();
                                    new AlertDialog.Builder(Signup.this)
                                            .setTitle(getResources().getString(R.string.invalid_refferal_code_header))
                                            .setMessage(getResources().getString(R.string.invalid_refferal_code))
                                            .setCancelable(false)
                                            .setNegativeButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which)
                                                {
                                                    editTextNumber4.setText("");
                                                    editTextNumber3.setText("");
                                                    editTextNumber2.setText("");
                                                    editTextNumber .setText("");
                                                    referral_code="";

                                                    dialog.dismiss();
                                                }
                                            })
                                            .show();
                                }

                                else
                                {
                                    progressDialog.dismiss();
                                    new AlertDialog.Builder(Signup.this)
                                            .setTitle(getResources().getString(R.string.user_already_exist_header))
                                            .setMessage(getResources().getString(R.string.useralreadyregistered))
                                            .setCancelable(false)
                                            .setNegativeButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which)
                                                {
                                                    finish();
                                                }
                                            })
                                            .show();
                                }

                            }



                        }


                        catch (JSONException e)
                        {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                        progressDialog.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(Signup.this, getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();

                        } else if (error instanceof AuthFailureError) {
                            //TODO
                            Toast.makeText(Signup.this, getResources().getString(R.string.incorrectunamepass), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            //TODO
                            Toast.makeText(Signup.this, getResources().getString(R.string.servermaintainence), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NetworkError) {
                            //TODO
                            Toast.makeText(Signup.this, getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            //TODO
                            Toast.makeText(Signup.this, getResources().getString(R.string.incorrect_data), Toast.LENGTH_SHORT).show();
                        }

                    }
                })
        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();


                parameters.put("phone", contact);
                parameters.put("referral", referral);

                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(Signup.this);
        requestQueue.add(stringRequest);
    }

    public void sendcode(String completecontact,String zeroexcludedphonenumber) {
        progressDialog.dismiss();
      //  Toast.makeText(this, completecontact, Toast.LENGTH_SHORT).show();
        if (countycode.equals("")) {

            Toast.makeText(this, "Please Select Country Code", Toast.LENGTH_SHORT).show();

        } else
            {
//            if (completecontact.length() <= 8 || completecontact.length() >= 16)
//            {
              //  progressDialog.dismiss();
                /*Signup_userphET_txt.setError( getResources().getString(R.string.onlyforksa));*/
//            }
//            else {
             //   progressDialog.dismiss();
              //  Toast.makeText(this, completecontact, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Signup.this, OTPScreen.class);
                intent.putExtra("AuthPhone", completecontact);
                intent.putExtra("withoutzeroAuthPhone",zeroexcludedphonenumber);
                intent.putExtra("Screen", "1");
                startActivity(intent);
                finish();
                //Toast.makeText(Signup.this, "User Login", Toast.LENGTH_SHORT).show();
//            }
        }
    }


    public void rtl_switch()
    {

        Signup_signinTV.setText(R.string.signin);
        Signup_alreadyauser_tv.setText(R.string.alreadyauser);
        Signup_userphET_txt.setHint(R.string.Mobile_number);
        Signup_userpassET_txt.setHint(R.string.password);
        Signup_referralcode_tv.setText(R.string.Do_you_have_a_referral_code);
        Signup_termsnconditions.setText(R.string.agreetoterms);
        Signup_termsandconditions_tv.setText(R.string.term_of_use);
        Signup_signup_tv.setText(R.string.sign_up);
        Signup_userSignupBtn.setText(R.string.sign_up);
    }

    public void setApplicationlanguage(String language) {
        Resources res = Signup.this.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(new Locale(language)); // API 17+ only.
        } else {
            conf.locale = new Locale(language);
        }
        res.updateConfiguration(conf, dm);
    }

}