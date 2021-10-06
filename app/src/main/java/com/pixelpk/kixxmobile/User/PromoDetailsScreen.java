package com.pixelpk.kixxmobile.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.bumptech.glide.Glide;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.ModelClasses.PromosList;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
import com.pixelpk.kixxmobile.User.adapters.PromosAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PromoDetailsScreen extends AppCompatActivity {

    String promo_id,user_type;
    Intent intent;
    ProgressDialog progressDialog;

    TextView PromoDetailsScreen_title,PromoDetailsScreen_description;

    ImageView PromoDetailsScreen_banner_image,PromoDetailsScreen_qr_image,PromoDetailsScreen_backarrow;


    String banner;
    String qr_image;
    String title;
    String description;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    LinearLayout PromoDetailsScreen_back_LL;

    //Handle Button Clicks
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_details_screen);

        intent = getIntent();
        progressDialog = new ProgressDialog(PromoDetailsScreen.this);

        PromoDetailsScreen_title = findViewById(R.id.PromoDetailsScreen_title);
        PromoDetailsScreen_description = findViewById(R.id.PromoDetailsScreen_description);
        PromoDetailsScreen_banner_image = findViewById(R.id.PromoDetailsScreen_banner_image);
        PromoDetailsScreen_qr_image = findViewById(R.id.PromoDetailsScreen_qr_image);
        PromoDetailsScreen_backarrow = findViewById(R.id.PromoDetailsScreen_backarrow);
        PromoDetailsScreen_back_LL = findViewById(R.id.PromoDetailsScreen_back_LL);


        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);

        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

        //   Toast.makeText(context,rtl, Toast.LENGTH_SHORT).show();

        if(rtl.equals("1"))
        {
            PromoDetailsScreen_title.setGravity(Gravity.END);
            PromoDetailsScreen_description.setGravity(Gravity.END);
            PromoDetailsScreen_backarrow.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24);
            PromoDetailsScreen_backarrow.setRotation(180);
        }

        promo_id = intent.getStringExtra("promo_id");
        user_type = intent.getStringExtra("user_type");

     //   Toast.makeText(this, user_type, Toast.LENGTH_SHORT).show();

        if(user_type.equals("user"))
        {
            PromoDetailsScreen_qr_image.setVisibility(View.VISIBLE);
        }
        else
        {
            PromoDetailsScreen_qr_image.setVisibility(View.GONE);
        }
       // Toast.makeText(this, promo_id, Toast.LENGTH_SHORT).show();
        getPromoData();

        PromoDetailsScreen_back_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                finish();
            }
        });

    }


    private void getPromoData()
    {
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.PROMOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //   Toast.makeText(PromoDetailsScreen.this, response, Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");
                            progressDialog.dismiss();

                            if(message.contains("success"))
                            {
                                JSONArray data  = jsonObj.getJSONArray("response");

                                for (int i = 0; i < data.length(); i++) {

                                    JSONObject c = data.getJSONObject(i);

                                    banner = URLs.PROMO_IMAGE_URL + c.getString("banner");
                                    qr_image = URLs.PROMO_IMAGE_URL + c.getString("qr_image");
                                    title = c.getString("title");
                                    description = c.getString("description");



                                //    Toast.makeText(PromoDetailsScreen.this, banner + " " + qr_image + " " + title + " " + description, Toast.LENGTH_SHORT).show();

                                }


                                Glide.with(PromoDetailsScreen.this).load(banner).into( PromoDetailsScreen_banner_image);
                                Glide.with(PromoDetailsScreen.this).load(qr_image).into( PromoDetailsScreen_qr_image);
                                PromoDetailsScreen_title.setText(title);
                                PromoDetailsScreen_description.setText(description);

                                PromoDetailsScreen_banner_image.setVisibility(View.VISIBLE);
                             //   PromoDetailsScreen_qr_image.setVisibility(View.VISIBLE);
                                PromoDetailsScreen_title.setVisibility(View.VISIBLE);
                                PromoDetailsScreen_description.setVisibility(View.VISIBLE);



                            }
                            else
                            {
                             //   Toast.makeText(PromoDetailsScreen.this, R.string.usernotfound, Toast.LENGTH_SHORT).show();
                            }

                        } catch (final JSONException e) {
                            PromoDetailsScreen.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    /*Toast.makeText(PromoDetailsScreen.this,
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
                            //   Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            //        Toast.makeText(getActivity(), R.string.usernotfound, Toast.LENGTH_SHORT).show();
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
            protected Map<String, String> getParams()
            {
                   Map<String, String> parameters = new HashMap<String, String>();
                   parameters.put("id",promo_id);

                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(PromoDetailsScreen.this);
        requestQueue.add(stringRequest);


    }
}