package com.pixelpk.kixxmobile.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.pixelpk.kixxmobile.Constants;
import com.pixelpk.kixxmobile.ForgotPass_ChangePassword;
import com.pixelpk.kixxmobile.Login;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OTPScreen extends AppCompatActivity {

    EditText OTPScreen_codedigit1,OTPScreen_codedigit2,OTPScreen_codedigit3,OTPScreen_codedigit4,OTPScreen_codedigit5;
    String phoneNumber,referred_code,zeroexcluded_phonenumber;
    private String verificationId;
    private FirebaseAuth mAuth;
    Button OTPScreen_nextbtn;
    ProgressDialog progressDialog;
    TextView OTPScreen_coundown,OTPScreen_editcontnumber,OTPScreen_contactnumber;
    ImageView OPTScreen_backbtn;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String contact,password,fcm_id,referral;

    Intent getdata;

    private String number,id;

    String intentData;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_screen);

        InitializeViews();
        //  sendVerificationCode(phoneNumber);
        mAuth = FirebaseAuth.getInstance();

        sendVerificationCode();

        // mCountDownTimer.start();

        // Restore instance state
        // put this code after starting phone number verification
        if (verificationId == null && savedInstanceState != null)
        {
            onRestoreInstanceState(savedInstanceState);
        }

        OTPScreen_nextbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                String code = OTPScreen_codedigit1.getText().toString();

                //   Toast.makeText(OTPScreen.this, verificationId, Toast.LENGTH_SHORT).show();

                if(TextUtils.isEmpty(code)){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.entercode), Toast.LENGTH_SHORT).show();
                }
                else if(code.length()!=6){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.incorrect_data), Toast.LENGTH_SHORT).show();
                }
                else
                    {
                    //  loader.setVisibility(View.VISIBLE);


                    //  if (verificationId == null && savedInstanceState != null) {
                    //        onRestoreInstanceState(savedInstanceState);

                        verify_code(code);

//                        Toast.makeText(getApplicationContext(), code, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();

                        // }

                }


              /*  if(code.isEmpty() || code.length()<6)
                {
                   OTPScreen_codedigit1.setError("Invalid Code");
                   OTPScreen_codedigit1.requestFocus();
                   return;
                }

                progressDialog.show();
                verifyCode(code);*/

            }
        });

        OTPScreen_editcontnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OTPScreen.this,ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });

        OPTScreen_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OTPScreen.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        OTPScreen_coundown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                sendVerificationCode();
            }
        });



    }

    private void verify_code(String code)
    {
        try
        {
            //creating the credential
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id, code);
            //signing the user
            signInWithPhoneAuthCredential(credential);
        }

        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            Log.d("tag-exception",e.toString());
        }
    }

  /*  private void verifyCode(String code)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);

        signInAnonymously();
    }*/

    /*CountDownTimer mCountDownTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            //this will be called every second.
            OTPScreen_coundown.setText(String.valueOf(millisUntilFinished/1000));
        }

        @Override
        public void onFinish() {
            OTPScreen_editcontnumber.setVisibility(View.VISIBLE);
            //you are good to go.
            //30 seconds passed.
        }
    };*/


    private void sendVerificationCode() {

        new CountDownTimer(120000,1000){
            @Override
            public void onTick(long l) {
                OTPScreen_coundown.setText(""+l/1000);
                OTPScreen_coundown.setEnabled(false);
            }

            @Override
            public void onFinish() {
                OTPScreen_coundown.setText(getResources().getString(R.string.Resend));
                OTPScreen_coundown.setEnabled(true);
            }
        }.start();


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    /*@Override
                    public void onCodeSent(@NonNull String id, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        OTPScreen.this.id = id;
                    }*/
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken)
                    {
                        super.onCodeSent(s, forceResendingToken);
                        id = s;
                        // progress.dismiss();
                    }
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential)
                    {
//                        Toast.makeText(OTPScreen.this, "verify done", Toast.LENGTH_SHORT).show();
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        // Toast.makeText(getApplicationContext(), "You Login Has Been Blocked Due to 3 Wrong Attempts of Receiving OTP, Please Try Again Later After 1 Hour, Thank You!", Toast.LENGTH_SHORT).show();
                        //       Toast.makeText(getApplicationContext(), "You Login Has Been Blocked Due to 3 Wrong Attempts of Receiving OTP, Please Try Again Later After 1 Hour, Thank You!", Toast.LENGTH_SHORT).show();
                        //        Toast.makeText(OTPScreen.this, "Error Message =" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        //   Toast.makeText(OTPScreen.this, getResources().getString(R.string.verificationerror), Toast.LENGTH_SHORT).show();
                        if (e instanceof FirebaseAuthInvalidCredentialsException)
                        {
                            new AlertDialog.Builder(OTPScreen.this)
                                    .setMessage(getResources().getText(R.string.invalidphone))
                                    .setCancelable(false)
                                    .setNegativeButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            Intent intent = new Intent(getApplicationContext(), Login.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    })
                                    .show();

                            // Invalid request
                        }

                        else if (e instanceof FirebaseTooManyRequestsException)
                        {
//                            Toast.makeText(OTPScreen.this, getResources().getString(R.string.toomanyattempts), Toast.LENGTH_SHORT).show();

                            new AlertDialog.Builder(OTPScreen.this)
                                    .setMessage(getResources().getText(R.string.toomanyattempts))
                                    .setCancelable(false)
                                    .setNegativeButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            Intent intent = new Intent(getApplicationContext(), Login.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    })
                                    .show();

                            // The SMS quota for the project has been exceeded
                        }
                        // Toast.makeText(OTPScreen.this, "", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeAutoRetrievalTimeOut(@NonNull String s)
                    {
                        super.onCodeAutoRetrievalTimeOut(s);

//                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                        //      Toast.makeText(getApplicationContext(), "You Login Has Been Blocked Due to 3 Wrong Attempts of Receiving OTP, Please Try Again Later After 1 Hour, Thank You!", Toast.LENGTH_SHORT).show();

                        alerbox();
                        // Toast.makeText(OTPScreen.this, getResources().getText(R.string.network_problem_warning), Toast.LENGTH_SHORT).show();
                        //      Toast.makeText(OTPScreen.this, s, Toast.LENGTH_SHORT).show();
                    }
                });        //OnVerificationStateChangedCallbacks


    }

   /* private void signInAnonymously() {
mAuth.signInAnonymously()
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
           if(task.isSuccessful())
           {

               progressDialog.dismiss();

               getdata = getIntent();
               String intentData = getdata.getStringExtra("Screen");

               if(intentData.equals("1"))
               {
                   Intent intent = new Intent(OTPScreen.this,HomeScreen.class);
                   RegisterUser(contact,password,fcm_id,referral);
                   //   intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                   startActivity(intent);
               }
               else
               {
                   Intent intent = new Intent(OTPScreen.this, ForgotPass_ChangePassword.class);
                   intent.putExtra("phone",phoneNumber);
                   //   intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                   startActivity(intent);
               }
              // FirebaseUser user = mAuth.getCurrentUser();
             //  updateUI(user);
           }
           else
           {
               Toast.makeText(OTPScreen.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
             //  updateUI(null);
           }
            }
        });


    }

    private void sendVerificationCode(String phoneNumber) {

      PhoneAuthProvider.getInstance().verifyPhoneNumber(
              phoneNumber,
              60,
              TimeUnit.SECONDS,
              this,
              mCallBack
      );


    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if(code!=null)
            {
                verifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OTPScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };
*/




    private void InitializeViews() {

        OTPScreen_codedigit1 = findViewById(R.id.OTPScreen_codedigit1);
        OTPScreen_codedigit2 = findViewById(R.id.OTPScreen_codedigit2);
        OTPScreen_codedigit3 = findViewById(R.id.OTPScreen_codedigit3);
        OTPScreen_codedigit4 = findViewById(R.id.OTPScreen_codedigit4);
        OTPScreen_codedigit5 = findViewById(R.id.OTPScreen_codedigit5);

        phoneNumber = getIntent().getStringExtra("AuthPhone");
        zeroexcluded_phonenumber = getIntent().getStringExtra("withoutzeroAuthPhone");

        //   Toast.makeText(this, zeroexcluded_phonenumber, Toast.LENGTH_SHORT).show();


        number = phoneNumber;
        mAuth = FirebaseAuth.getInstance();



        progressDialog = new ProgressDialog(this);

        OTPScreen_nextbtn = findViewById(R.id.OTPScreen_nextbtn);

        OTPScreen_coundown = findViewById(R.id.OTPScreen_coundown);

        OTPScreen_editcontnumber = findViewById(R.id.OTPScreen_editcontnumber);

        OPTScreen_backbtn = findViewById(R.id.OPTScreen_backbtn);

        OTPScreen_contactnumber = findViewById(R.id.OTPScreen_contactnumber);

        OTPScreen_contactnumber.setText(phoneNumber);

        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        contact = sharedPreferences.getString(Constants.Signup_cont,"0");
        password = sharedPreferences.getString(Constants.Signup_password,"0");
        fcm_id = sharedPreferences.getString(Constants.Signup_fcmid,"0");
        referral = sharedPreferences.getString(Constants.Signup_referral,"0");
        referred_code = sharedPreferences.getString(Shared.Reffered_code,"0");

//        Toast.makeText(this, referred_code, Toast.LENGTH_SHORT).show();
//
//        Toast.makeText(this, referral, Toast.LENGTH_SHORT).show();

        if(referral=="")
        {
//            Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
            referral = "0";
        }


//        Toast.makeText(this, "new value"+ referral, Toast.LENGTH_SHORT).show();
        getdata = getIntent();
        intentData = getdata.getStringExtra("Screen");



        //  Toast.makeText(this, intentData, Toast.LENGTH_SHORT).show();
    }


    public void RegisterUsernoreferral(final String contact, final String password, final String fcm_id,String referred_code)
    {
        progressDialog.setMessage("Please Wait! we are signing you up");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.USER_SIGNUP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        //      Toast.makeText(Signup.this, response, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(OTPScreen.this, response, Toast.LENGTH_SHORT).show();


                        if(response.contains("Duplicate"))
                        {
                            progressDialog.dismiss();

                            new AlertDialog.Builder(OTPScreen.this)
                                    .setTitle(getResources().getString(R.string.user_already_exist_header))
                                    .setMessage(getResources().getString(R.string.useralreadyregistered))
                                    .setCancelable(false)
                                    .setNegativeButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            Intent intent = new Intent(getApplicationContext(),Signup.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .show();

                        }
                        else
                        {

                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                // JSONObject jsonObj_userexist = new JSONObject(response);
                                String user_exist_check = jsonObj.getString("message");



                                if(user_exist_check.equals("Login Successfull"))
                                {
                                    progressDialog.dismiss();

                                    Toast.makeText(OTPScreen.this, getResources().getString(R.string.usersuccessfullyregistered), Toast.LENGTH_SHORT).show();

                                    String jwt_token = jsonObj.getString("jwt_token");
                                    String message = jsonObj.getString("message");




                                    editor.putString(Shared.loggedIn_jwt,jwt_token).apply();

                                    //      Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                    //      Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                                    JSONObject data = jsonObj.getJSONObject("userdata");


                                    String user_id = data.getString("userid");

                                    editor.putString(Shared.loggedIn_user_id, user_id);


                                   /* if(user_id.length() == 1)
                                    {
                                        // Toast.makeText(getActivity(), "KX000" + user_id, Toast.LENGTH_SHORT).show();
                                        editor.putString(Shared.LoggedIn_fromatted_userid, "KX000" + user_id);
                                    }
                                    else if(user_id.length() == 2)
                                    {
                                        // Toast.makeText(getActivity(), "KX00" + user_id, Toast.LENGTH_SHORT).show();
                                        editor.putString(Shared.LoggedIn_fromatted_userid, "KX00" + user_id);
                                    }
                                    else if(user_id.length() == 3)
                                    {
                                        //    Toast.makeText(getActivity(), "KX0" + user_id, Toast.LENGTH_SHORT).show();
                                        editor.putString(Shared.LoggedIn_fromatted_userid, "KX0" + user_id);
                                    }
                                    else
                                    {
                                        // Toast.makeText(getActivity(), "KX" + user_id, Toast.LENGTH_SHORT).show();
                                        editor.putString(Shared.LoggedIn_fromatted_userid, "KX" + user_id);
                                    }*/


                                    editor.putString(Shared.User_login_logout_status,"1");
                                    editor.putString(Shared.User_promo,"1");
                                    editor.apply();

                                 /*   Intent intent = new Intent(OTPScreen.this, HomeScreen.class);
                                    startActivity(intent);*/

                                    Intent intent = new Intent(OTPScreen.this, Login.class);
                                    alerbox_welcome();
                                    startActivity(intent);

                                    (OTPScreen.this).finish();
                                }
                                else
                                {
                                    Toast.makeText(OTPScreen.this, getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                                }


                            } catch (final JSONException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {


                                    }
                                });
                            }

                        }



                        //     progressDialog.dismiss();
                        // Toast.makeText(Signup.this, response, Toast.LENGTH_SHORT).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                        progressDialog.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(OTPScreen.this, getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();

                        } else if (error instanceof AuthFailureError) {
                            //TODO
                            Toast.makeText(OTPScreen.this, getResources().getString(R.string.incorrectunamepass), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            //TODO
                            Toast.makeText(OTPScreen.this, getResources().getString(R.string.servermaintainence), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NetworkError) {
                            //TODO
                            Toast.makeText(OTPScreen.this, getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            //TODO
                            Toast.makeText(OTPScreen.this, getResources().getString(R.string.incorrect_data), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
//                            Toast.makeText(OTPScreen.this, error.toString(), Toast.LENGTH_SHORT).show();
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
                parameters.put("referal_code", referred_code);

                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(OTPScreen.this);
        requestQueue.add(stringRequest);
    }

    public void RegisterUser(final String contact, final String password, final String fcm_id, final String referral,final String refered_by)
    {

//        Toast.makeText(this, "register_with_referal1", Toast.LENGTH_SHORT).show();
        progressDialog.setMessage("Please Wait! we are signing you up");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.USER_SIGNUP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {

                        //      Toast.makeText(Signup.this, response, Toast.LENGTH_SHORT).show();

//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

//                        Toast.makeText(OTPScreen.this, response, Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();

                        if(response.contains("Duplicate"))
                        {
                            new AlertDialog.Builder(OTPScreen.this)
                                    .setTitle(getResources().getString(R.string.user_already_exist_header))
                                    .setMessage(getResources().getString(R.string.useralreadyregistered))
                                    .setCancelable(false)
                                    .setNegativeButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            Intent intent = new Intent(getApplicationContext(),Signup.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .show();

                        }
                        else
                        {
//                            Toast.makeText(OTPScreen.this, "registered_with_referal1", Toast.LENGTH_SHORT).show();
                            Toast.makeText(OTPScreen.this, getResources().getString(R.string.usersuccessfullyregistered), Toast.LENGTH_SHORT).show();


                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                // JSONObject jsonObj_userexist = new JSONObject(response);
                                String user_exist_check = jsonObj.getString("message");

                                if(user_exist_check.equals("Login Successfull"))
                                {

                                    String jwt_token = jsonObj.getString("jwt_token");
                                    String message = jsonObj.getString("message");



                                    editor.putString(Shared.loggedIn_jwt,jwt_token).apply();

                                    //      Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                    //      Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                                    JSONObject data = jsonObj.getJSONObject("userdata");


                                    String user_id = data.getString("userid");

                                    editor.putString(Shared.loggedIn_user_id, user_id);


                                   /* if(user_id.length() == 1)
                                    {
                                        // Toast.makeText(getActivity(), "KX000" + user_id, Toast.LENGTH_SHORT).show();
                                        editor.putString(Shared.LoggedIn_fromatted_userid, "KX000" + user_id);
                                    }
                                    else if(user_id.length() == 2)
                                    {
                                        // Toast.makeText(getActivity(), "KX00" + user_id, Toast.LENGTH_SHORT).show();
                                        editor.putString(Shared.LoggedIn_fromatted_userid, "KX00" + user_id);
                                    }
                                    else if(user_id.length() == 3)
                                    {
                                        //    Toast.makeText(getActivity(), "KX0" + user_id, Toast.LENGTH_SHORT).show();
                                        editor.putString(Shared.LoggedIn_fromatted_userid, "KX0" + user_id);
                                    }
                                    else
                                    {
                                        // Toast.makeText(getActivity(), "KX" + user_id, Toast.LENGTH_SHORT).show();
                                        editor.putString(Shared.LoggedIn_fromatted_userid, "KX" + user_id);
                                    }*/


                                    editor.putString(Shared.User_login_logout_status,"1");
                                    editor.putString(Shared.User_promo,"1");
                                    editor.apply();

                                    Intent intent = new Intent(OTPScreen.this, Login.class);
                                    alerbox_welcome();
                                    startActivity(intent);

                                 /*   Intent intent = new Intent(OTPScreen.this, HomeScreen.class);
                                    startActivity(intent);*/

                                    (OTPScreen.this).finish();

                                }
                                else
                                {
                                    Toast.makeText(OTPScreen.this, getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (final JSONException e)
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run()
                                    {

                                    }
                                });
                            }
                        }
                        //     progressDialog.dismiss();
                        // Toast.makeText(Signup.this, response, Toast.LENGTH_SHORT).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(OTPScreen.this, getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();

                        } else if (error instanceof AuthFailureError) {
                            //TODO
                            Toast.makeText(OTPScreen.this, R.string.usernotfound, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            //TODO
                            Toast.makeText(OTPScreen.this, getResources().getString(R.string.servermaintainence), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NetworkError) {
                            //TODO
                            Toast.makeText(OTPScreen.this, getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            //TODO
                            Toast.makeText(OTPScreen.this, getResources().getString(R.string.incorrect_data), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

//                            Toast.makeText(OTPScreen.this, error.toString(), Toast.LENGTH_SHORT).show();

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
                parameters.put("referral",referral);
                parameters.put("referal_code",refered_by);

                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(OTPScreen.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
        progressDialog.dismiss();
    }



    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
//        Toast.makeText(OTPScreen.this,"in sign_auth"+ referral, Toast.LENGTH_SHORT).show();

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //    loader.setVisibility(View.GONE);
                        if (task.isSuccessful())
                        {

                            // Toast.makeText(OTPScreen.this, intentData, Toast.LENGTH_SHORT).show();


//                            Toast.makeText(OTPScreen.this,"yeet1"+ referral, Toast.LENGTH_SHORT).show();
                            if(intentData.equals("1"))
                            {
                                //          progressDialog.dismiss();
//                                Toast.makeText(OTPScreen.this,"yeet2"+ referral, Toast.LENGTH_SHORT).show();
                                if(referral.equals("0"))
                                {

                                    Log.d("tag_referal","no");
//                                    Toast.makeText(OTPScreen.this, "no", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(OTPScreen.this, HomeScreen.class);
                                    RegisterUsernoreferral(contact, password, fcm_id,referred_code);
                          /*          alerbox_welcome();
                                    startActivity(intent);*/
                                }

                                else
                                {
                                    Log.d("tag_referal","yes");
//                                    Toast.makeText(OTPScreen.this, "yes", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(OTPScreen.this, HomeScreen.class);
                                    RegisterUser(contact, password, fcm_id, referral,referred_code);
                                /*    alerbox_welcome();
                                    startActivity(intent);*/

//                                    Log.d("tag_referal","no");
//                                    Intent intent = new Intent(OTPScreen.this, HomeScreen.class);
//                                    RegisterUsernoreferral(contact, password, fcm_id);
//                                    alerbox_welcome();
//                                    startActivity(intent);
                                }
                            }
                            else
                            {
                                //      progressDialog.dismiss();
//                                Toast.makeText(OTPScreen.this, "hello", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(OTPScreen.this, ForgotPass_ChangePassword.class);
                                intent.putExtra("phone",phoneNumber);
                                //   intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }

                         /* //  startActivity(new Intent(getApplicationContext(),ForgotPass_ChangePassword.class));
                            Intent intent = new Intent(OTPScreen.this,ForgotPass_ChangePassword.class);
                            intent.putExtra("phone",phoneNumber);
                            startActivity(intent);
                            finish();
                            FirebaseUser user = task.getResult().getUser();
                            // ...*/
                        }

                        else
                        {
                            alerbox_failedverification();
                            // Toast.makeText(getApplicationContext(), "Verification Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void alerbox()
    {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getText(R.string.timed_out_header))
                .setMessage(getResources().getText(R.string.timed_out_alert))
                .setCancelable(false)
                .setNegativeButton(getResources().getString(R.string.ok), null)
                .show();
    }

    public void alerbox_failedverification()
    {
        new AlertDialog.Builder(this)
                .setMessage(getResources().getText(R.string.verificationerror))
                .setCancelable(false)
                .setNegativeButton(getResources().getString(R.string.ok),null)
                .show();
    }

    public void alerbox_welcome()
    {
        new AlertDialog.Builder(this)
                .setMessage(getResources().getText(R.string.welcometokixx))
                .setCancelable(false)
                .setNegativeButton(getResources().getString(R.string.ok), null)
                .show();
    }

/*    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString(id,verificationId);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        verificationId = savedInstanceState.getString(id);
    }*/
}