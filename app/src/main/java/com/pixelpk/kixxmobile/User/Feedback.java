package com.pixelpk.kixxmobile.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity {

    RatingBar ratingBar;
    Button ContactUs_form_submitbtn;
    EditText ContactUs_feedback_description;

    ProgressDialog progressDialog;
    String token;

    String feeds;

    String uid;

    SharedPreferences sharedPreferences;

    String shop_id = "11";

    Spinner ContactUs_spinner_question1,ContactUs_spinner_question2;

    ArrayList<String> hobbies,age;

    ImageView ContactUs_backbtn;

    LinearLayout Feedback_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        InitializeView();

        Feedback_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ContactUs_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ContactUs_form_submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(!ContactUs_feedback_description.getText().toString().equals(""))
                {
                    feeds = ContactUs_feedback_description.getText().toString();
                }
                else
                {
                    feeds = "null";
                }

                if(ratingBar.getRating() == 0)
                {

                }
                else
                {
                    String rating = String.valueOf(ratingBar.getRating());
                   // Toast.makeText(Feedback.this, rating, Toast.LENGTH_LONG).show();

                    sendFeedback(rating,feeds,uid,shop_id);
                }

            }
        });


    }

    public void InitializeView()
    {
        ratingBar = findViewById(R.id.ContactUs_ratingbar);
        ContactUs_form_submitbtn = findViewById(R.id.ContactUs_form_submitbtn);
        ContactUs_feedback_description = findViewById(R.id.ContactUs_feedback_description);
        ContactUs_backbtn = findViewById(R.id.ContactUs_backbtn);
        Feedback_back = findViewById(R.id.Feedback_back);
        progressDialog = new ProgressDialog(this);

        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
//        editor = sharedPreferences.edit();

        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

//            Toast.makeText(getContext(),rtl, Toast.LENGTH_SHORT).show();

        if(rtl.equals("1"))
        {
            ContactUs_backbtn.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24_rwhite);
        }


        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#E3580B"), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(Color.parseColor("#c3c3c3"), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.parseColor("#E3580B"), PorterDuff.Mode.SRC_ATOP);

        sharedPreferences = getSharedPreferences("Shared", Context.MODE_PRIVATE);

        token = sharedPreferences.getString("loggedIn_jwt","0");
        uid = sharedPreferences.getString(Shared.loggedIn_user_id,"0");

        ContactUs_spinner_question1 = findViewById(R.id.ContactUs_spinner_question1);
        ContactUs_spinner_question2 = findViewById(R.id.ContactUs_spinner_question2);

        hobbies = new ArrayList<>();
        age = new ArrayList<>();

        hobbies.add("Select Hobbies");
        hobbies.add("Sports");
        hobbies.add("Reading");
        hobbies.add("Cooking");
        hobbies.add("Blogging");
        hobbies.add("Painting");
        hobbies.add("Art");

        age.add("Select Age");
        age.add("< 18");
        age.add("19");
        age.add("20");
        age.add("21");
        age.add("22");
        age.add("23");
        age.add("24");
        age.add("25");
        age.add("26");
        age.add("27");
        age.add("28");
        age.add("29");
        age.add("30");
        age.add("31");
        age.add("32");
        age.add("33");
        age.add("34");
        age.add("35");
        age.add("36");
        age.add("37");
        age.add("38");
        age.add("39");
        age.add("40");
        age.add("41");
        age.add("42");
        age.add("43");
        age.add("44");
        age.add("45");
        age.add("46");
        age.add("47");
        age.add("48");
        age.add("49");
        age.add("50");
        age.add("> 50");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
               R.layout.spinner_white_text,hobbies);
        adapter.setDropDownViewResource(R.layout.spinner_textview_layout);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                R.layout.spinner_white_text,age);
        adapter2.setDropDownViewResource(R.layout.spinner_textview_layout);

        ContactUs_spinner_question1.setAdapter(adapter);
        ContactUs_spinner_question2.setAdapter(adapter2);

    }


    public void sendFeedback(String rate,String Feedback,String user_id,String shop_id)
    {
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.USER_SHOP_FEEDBACK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(Feedback.this, getResources().getString(R.string.feedbacksubmitted), Toast.LENGTH_SHORT).show();
                        //       Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                        finish();


                        progressDialog.dismiss();
                    //    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   progressDialog.dismiss();
                     //   Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
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


                parameters.put("user_id", user_id);
                parameters.put("shop_id", shop_id);
                parameters.put("rating", rate);
                parameters.put("feedback", Feedback);

                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }



}