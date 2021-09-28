package com.pixelpk.kixxmobile.User;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.se.omapi.Session;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
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
import com.pixelpk.kixxmobile.GMailSender;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.PasswordAuthentication;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ContactUs extends AppCompatActivity {

    Button ContactUs_form_submitbtn;
    EditText ContactUs_form_subject,ContactUs_form_description;
    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String uid,token;

    ImageView ContactUs_backbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        initializeView();

        ContactUs_form_submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(ContactUs_form_subject.getText().toString().equals(""))
                {
                    ContactUs_form_subject.setError("Please Enter Subject");
                }
                else if(ContactUs_form_description.getText().toString().equals(""))
                {
                    ContactUs_form_description.setError("Please Enter Description");
                }
                else
                {
                    sendContactData(uid,ContactUs_form_subject.getText().toString(),ContactUs_form_description.getText().toString());
                }



            }
        });

        ContactUs_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }


    public void initializeView()
    {
        ContactUs_form_subject = findViewById(R.id.ContactUs_form_subject);
        ContactUs_form_description = findViewById(R.id.ContactUs_form_description);

        ContactUs_form_submitbtn = findViewById(R.id.ContactUs_form_submitbtn);

        ContactUs_backbtn = findViewById(R.id.ContactUs_backbtn);

        progressDialog = new ProgressDialog(this);

        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);

        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

//            Toast.makeText(getContext(),rtl, Toast.LENGTH_SHORT).show();

        if(rtl.equals("1"))
        {
            ContactUs_backbtn.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24_rwhite);
        }

        uid = sharedPreferences.getString(Shared.loggedIn_user_id,"0");
        token = sharedPreferences.getString(Shared.loggedIn_jwt,"0");



        /*Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();*/


    }

    public void sendContactData(String uid,String subject,String description)
    {
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.USER_CONTACT_US,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.d("tag_contact_res",response);

                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);

                            String message = jsonObject.getString("status");


                            if(message.equals("success"))
                            {
                                progressDialog.dismiss();

                                Toast.makeText(ContactUs.this, getResources().getString(R.string.thankyouforresponse), Toast.LENGTH_SHORT).show();
                                //       Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(ContactUs.this, getResources().getString(R.string.response_customer_failed), Toast.LENGTH_SHORT).show();
                            }
                        }

                        catch (JSONException e)
                        {
                            e.printStackTrace();
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
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();


                parameters.put("id", uid);
                parameters.put("subject", subject);
                parameters.put("description", description);

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
        RequestQueue requestQueue = Volley.newRequestQueue(ContactUs.this);

        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }



    public void sendEmail()
    {
        try {
            GMailSender sender = new GMailSender("username@gmail.com", "password");
            sender.sendMail("Test Subject",
                    "Test Message Body",
                    "uzair.haider94@gmail.com",
                    "uzair.haider94@gmail.com");
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
    }

}
    



