package com.pixelpk.kixxmobile.Salesman.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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

    Boolean check_flag = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sales_notifications, container, false);

        InitializeViews(view);

        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE, "0");

        if(rtl.equals("1"))
        {
            Sales_Notification_titlebar_kixxlogo.setImageResource(R.mipmap.kixx_ar);
        }

        get_user_data(uid);

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

    private void InitializeViews(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.Sales_Notification_notifRV);
        Sales_Notification_titlebar_kixxlogo = (ImageView) view.findViewById(R.id.Sales_Notification_titlebar_kixxlogo);

        sharedPreferences = getActivity().getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        KX_formatted_userid = sharedPreferences.getString(Shared.LoggedIn_fromatted_userid,"0");

      //  Notification_User_id = view.findViewById(R.id.Notification_User_id);

//        Notification_User_id.setText(KX_formatted_userid);

        progressDialog = new ProgressDialog(getContext());

        uid = sharedPreferences.getString(Shared.loggedIn_sales_id,"0");

        myListData = new ArrayList<>();

    }


    private void get_user_data(String id)
    {
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.USER_SHOP_NOTIFICATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //       Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");

                            if(message.contains("success"))
                            {
                                JSONArray all_notifications  = jsonObj.getJSONArray("all_notifications");
                                JSONArray generic_notifications  = jsonObj.getJSONArray("generic_notifications");
                             /*   String cardata = jsonObj.getString("cars");
                                String adsdata = jsonObj.getString("ads");*/
                           //     Toast.makeText(getContext(), all_notifications.toString(), Toast.LENGTH_SHORT).show();
                                if(!all_notifications.equals("null")) {

                                    for (int i = 0; i < all_notifications.length(); i++) {
                                        JSONObject objads = all_notifications.getJSONObject(i);
                                        String notif_id = objads.getString("id");
                                        String notif_title = objads.getString("title");
                                        String notif_message = objads.getString("message");
                                        String notif_date = objads.getString("date");

                                        if(notif_id.equals("1"))
                                        {
                                           // myListData = null;
                                            check_flag = false;
                                        }
                                        else if (notif_id.equals("2"))
                                        {
                                            check_flag = false;
                                        }
                                        else
                                        {
                                            Notificationlist notificationList = new Notificationlist(notif_message,notif_date,notif_title);
                                            myListData.add(notificationList);
                                            check_flag = true;
                                        }



                                    }

                                }

                        /*        if(!generic_notifications.equals("null")) {

                                    for (int i = 0; i < generic_notifications.length(); i++) {
                                        JSONObject objads = generic_notifications.getJSONObject(i);
                                        String notif_title = objads.getString("title");
                                        String notif_message = objads.getString("message");
                                        String notif_date = objads.getString("date");

                                        Notificationlist notificationList = new Notificationlist(notif_message,notif_date,notif_title);
                                        myListData.add(notificationList);

                                    }

                                }*/

                                if(check_flag == true)
                                {
                                    NotificationAdapter adapter = new NotificationAdapter(myListData,getActivity());
                                    recyclerView.setHasFixedSize(true);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                    recyclerView.setLayoutManager(linearLayoutManager);
                                    //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    recyclerView.setAdapter(adapter);
                                }
                                else
                                {
                                    if(getActivity()!=null) {
                                        alerbox();
                                    }
                                }




                            }
                            else
                            {
                                alerbox();
                             //   Toast.makeText(getActivity(), getResources().getString(R.string.usernotfound), Toast.LENGTH_SHORT).show();
                            }

                        } catch (final JSONException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    /*Toast.makeText(getContext(),
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
//                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("id", id);


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