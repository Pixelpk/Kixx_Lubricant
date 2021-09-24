package com.pixelpk.kixxmobile.User.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.service.notification.NotificationListenerService;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.pixelpk.kixxmobile.Constants;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.ModelClasses.ImageSliderList;
import com.pixelpk.kixxmobile.User.ModelClasses.Notificationlist;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
import com.pixelpk.kixxmobile.User.TutorialScreen;
import com.pixelpk.kixxmobile.User.UpdateUserProfile;
import com.pixelpk.kixxmobile.User.adapters.ImageSlidingAdapter;
import com.pixelpk.kixxmobile.User.adapters.NotificationAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class NotificationsFragment extends Fragment {


    String KX_formatted_userid;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    TextView Notification_User_id;

    ProgressDialog progressDialog;
    String uid;

    List<Notificationlist> myListData;
    RecyclerView recyclerView;

    ImageView NotificationsFragment_titlebar_kixxlogo;

    LinearLayout Notification_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        InitializeViews(view);

        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

//            Toast.makeText(getContext(),rtl, Toast.LENGTH_SHORT).show();
        NotificationsFragment_titlebar_kixxlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TutorialScreen.class);
                editor.putString(Constants.Tutorial_Screen_id,"0").apply();
                startActivity(intent);
            }
        });


        if(rtl.equals("1"))
        {
            NotificationsFragment_titlebar_kixxlogo.setImageResource(R.mipmap.kixx_ar);
        }

        Notification_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getActivity()!=null)
                {
                    Intent intent = new Intent(getActivity(), UpdateUserProfile.class);
                    startActivity(intent);
                }



            }
        });



        get_user_data(uid);






        return view;
    }

    private void InitializeViews(View view) {

        sharedPreferences = getActivity().getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        KX_formatted_userid = sharedPreferences.getString(Shared.LoggedIn_fromatted_userid,"0");

        Notification_User_id = view.findViewById(R.id.Notification_User_id);
        NotificationsFragment_titlebar_kixxlogo = view.findViewById(R.id.NotificationsFragment_titlebar_kixxlogo);

        Notification_User_id.setText(getResources().getString(R.string.User_id)+": "+KX_formatted_userid);

        progressDialog = new ProgressDialog(getContext());

       recyclerView = (RecyclerView) view.findViewById(R.id.Notification_notifRV);

        uid = sharedPreferences.getString(Shared.loggedIn_user_id,"0");

        myListData = new ArrayList<>();

        Notification_id = view.findViewById(R.id.Notification_id);

    }


    private void get_user_data(String id) {

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.END_USER,
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

                                if(!generic_notifications.equals("null")) {

                                    for (int i = 0; i < generic_notifications.length(); i++) {
                                        JSONObject objads = generic_notifications.getJSONObject(i);
                                        String notif_title = objads.getString("title");
                                        String notif_message = objads.getString("message");
                                        String notif_date = objads.getString("date");

                                        Notificationlist notificationList = new Notificationlist(notif_message,notif_date,notif_title);
                                        myListData.add(notificationList);

                                    }

                                }

                                if(!all_notifications.equals("null")) {

                                    for (int i = 0; i < all_notifications.length(); i++) {
                                        JSONObject objads = all_notifications.getJSONObject(i);
                                        String notif_title = objads.getString("title");
                                        String notif_message = objads.getString("message");
                                        String notif_date = objads.getString("date");

                                        Notificationlist notificationList = new Notificationlist(notif_message,notif_date,notif_title);
                                        myListData.add(notificationList);

                                    }

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
                                Toast.makeText(getActivity(), getResources().getString(R.string.usernotfound), Toast.LENGTH_SHORT).show();
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
                        if (error instanceof TimeoutError || error instanceof NoConnectionError)
                        {
                            Toast.makeText(getActivity(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();

                        } else if (error instanceof AuthFailureError) {
                            //TODO
                            //   Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            //        Toast.makeText(getActivity(), R.string.usernotfound, Toast.LENGTH_SHORT).show();
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

                parameters.put("id", id);


                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }


}