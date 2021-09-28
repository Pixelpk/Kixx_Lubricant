package com.pixelpk.kixxmobile.Salesman.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.ModelClasses.Notificationlist;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
import com.pixelpk.kixxmobile.User.adapters.NotificationAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class Sales_NotificationsFragment extends Fragment {

    String KX_formatted_userid;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    TextView Notification_User_id;

    ProgressDialog progressDialog;
    String uid;

    List<Notificationlist> myListData;
    RecyclerView recyclerView;

    ImageView Sales_Notification_titlebar_kixxlogo;

    String jwt;

    Boolean check_flag = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sales_notifications, container, false);

        InitializeViews(view);

        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE, "0");

        jwt = sharedPreferences.getString(Shared.sales_loggedIn_jwt,"0");


        if(rtl.equals("1"))
        {
            Sales_Notification_titlebar_kixxlogo.setImageResource(R.mipmap.kixx_ar);
        }

        get_user_data();

      /*  Notificationlist[] myListData = new Notificationlist[] {
                new Notificationlist("Kixx Pakistan sent you a new offer avail the limited time offer","5:50 PM"),
                new Notificationlist("Kixx Pakistan sent you a new offer avail the limited time offer","10:00 PM"),
                new Notificationlist("Kixx Pakistan sent you a new offer avail the limited time offer","11:00 AM"),

        };*/


     /*   NotificationAdapter adapter = new NotificationAdapter(myListData,getActivity());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);*/



        return view;
    }

    private void InitializeViews(View view)
    {
        recyclerView = (RecyclerView) view.findViewById(R.id.Sales_Notification_notifRV);
        Sales_Notification_titlebar_kixxlogo = (ImageView) view.findViewById(R.id.Sales_Notification_titlebar_kixxlogo);

        sharedPreferences = getActivity().getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        KX_formatted_userid = sharedPreferences.getString(Shared.LoggedIn_fromatted_userid,"0");

        progressDialog = new ProgressDialog(getContext());

        uid = sharedPreferences.getString(Shared.loggedIn_sales_id,"0");

        myListData = new ArrayList<>();

    }


    private void get_user_data()
    {
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.seller_notification_url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
//                        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                        Log.d("tag_notification_data_res",response);

                        try
                        {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");

                            if(message.contains("success"))
                            {
                                progressDialog.dismiss();

                                JSONArray shop_notifications  = jsonObj.getJSONArray("shop_notifications");

                                    for (int i = 0; i < shop_notifications.length(); i++)
                                    {
                                        JSONObject objads = shop_notifications.getJSONObject(i);
                                        String notif_title = objads.getString("title");
                                        String notif_message = objads.getString("message");
                                        String notif_date = objads.getString("date");

                                        Notificationlist notificationList = new Notificationlist(notif_message,notif_date,notif_title);
                                        myListData.add(notificationList);
                                    }

                                    NotificationAdapter adapter = new NotificationAdapter(myListData,getActivity());
                                    recyclerView.setHasFixedSize(true);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
                                    recyclerView.setLayoutManager(linearLayoutManager);
                                    //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    recyclerView.setAdapter(adapter);


                            }

                            else
                            {
                                progressDialog.dismiss();
                                alerbox();
//                                Toast.makeText(getActivity(), getResources().getString(R.string.nonotification), Toast.LENGTH_SHORT).show();
                            }

                        }

                        catch (final JSONException e)
                        {
                            getActivity().runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
//                                    Toast.makeText(getContext(),
//                                            "Json parsing error: " + e.getMessage(),
//                                            Toast.LENGTH_LONG).show();
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
                            Toast.makeText(getActivity(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();

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
                            Toast.makeText(getActivity(), getResources().getString(R.string.servermaintainence), Toast.LENGTH_SHORT).show();
                        }

                        else if (error instanceof NetworkError)
                        {
                            //TODO
                            Toast.makeText(getActivity(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();

                        }

                        else if (error instanceof ParseError)
                        {
                            //TODO
                            Toast.makeText(getActivity(), getResources().getString(R.string.incorrectdata), Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + jwt);
                return headers;
            }

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> parameters = new HashMap<String, String>();

                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }


    public void alerbox()
    {
        new AlertDialog.Builder(getContext())
                .setMessage(getResources().getString(R.string.nonotification))
                .setCancelable(false)
                .setNegativeButton(getResources().getString(R.string.ok), null)
                .show();
    }

}