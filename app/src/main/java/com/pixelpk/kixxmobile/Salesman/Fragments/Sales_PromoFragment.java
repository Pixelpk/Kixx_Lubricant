package com.pixelpk.kixxmobile.Salesman.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.SekizbitSwitch;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.ModelClasses.PromosList;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
import com.pixelpk.kixxmobile.User.adapters.PromosAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class Sales_PromoFragment extends Fragment {


    ProgressDialog progressDialog;
    List<PromosList> promos;
    RecyclerView recyclerView;

    ImageView Sales_PromoFragment_titlebar_kixxlogo;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sales_fragment_promo, container, false);

        progressDialog = new ProgressDialog(getActivity());

        sharedPreferences = getActivity().getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        promos = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.Sales_PromoFragment_promo_RV);
        Sales_PromoFragment_titlebar_kixxlogo = (ImageView) view.findViewById(R.id.Sales_PromoFragment_titlebar_kixxlogo);


        String value = getArguments().getString("promotion");


   //     Toast.makeText(getContext(), value, Toast.LENGTH_SHORT).show();

        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE, "0");

        final Activity that = getActivity();



        SekizbitSwitch mySwitch = new SekizbitSwitch(view.findViewById(R.id.sekizbit_switch));

        if(value.equals("2"))
        {
//            Toast.makeText(that, "promos", Toast.LENGTH_SHORT).show();
            mySwitch.setSelected(0);
            getPromoData();
        }
        else if(value.equals("1"))
        {
//            Toast.makeText(that, "ads", Toast.LENGTH_SHORT).show();
            mySwitch.setSelected(1);
            getadsData();
        }

        mySwitch.setOnChangeListener(new SekizbitSwitch.OnSelectedChangeListener() {
            @Override
            public void OnSelectedChange(SekizbitSwitch sender) {
                if(sender.getCheckedIndex() ==0 )
                {
               //     Toast.makeText(that,"Left Button Selected",Toast.LENGTH_SHORT).show();
                    promos.clear();
                    getPromoData();
                }
                else if(sender.getCheckedIndex() ==1 )
                {
                 //   Toast.makeText(that,"Right Button Selected", Toast.LENGTH_SHORT).show();
                    promos.clear();
                    getAdsData();
                }
            }
        });

        if(rtl.equals("1"))
        {
            Sales_PromoFragment_titlebar_kixxlogo.setImageResource(R.mipmap.kixx_ar);
        }
/*        PromosList[] myListData = new PromosList[] {

                new PromosList(R.drawable.slide1),
                new PromosList(R.drawable.slide2),
                new PromosList(R.drawable.slide3),

        };*/

        //getPromoData();

        return view;
    }

    private void getPromoData() {

        promos.clear();
        progressDialog.show();
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.PROMOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                     //      Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");


                            if(message.contains("success"))
                            {
                                JSONArray data  = jsonObj.getJSONArray("response");
                                JSONArray ads  = jsonObj.getJSONArray("ads");

                                for (int i = 0; i < data.length(); i++) {

                                    JSONObject c = data.getJSONObject(i);

                                    String banner = URLs.PROMO_IMAGE_URL + c.getString("banner");
                                    String promo_id = c.getString("id");

                                    PromosList promosList = new PromosList(banner,promo_id);
                                    promos.add(promosList);

                                    //   Toast.makeText(getContext(), banner , Toast.LENGTH_SHORT).show();
                                //    Log.d("Error",banner);
                                }





                                PromosAdapter adapter = new PromosAdapter(promos,getActivity(),"promos","seller");
                                recyclerView.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                recyclerView.setLayoutManager(linearLayoutManager);

                                recyclerView.setAdapter(adapter);

                                progressDialog.dismiss();

                            }
                            else
                            {
                                Toast.makeText(getActivity(), getResources().getString(R.string.no_data_error), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
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
                error -> {
                    //   progressDialog.dismiss();
                //    Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();

                //   parameters.put("id",);

                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }



    private void getAdsData() {


        progressDialog.show();
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.PROMOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                 //       Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");


                            if(message.contains("success"))
                            {
                                JSONArray data  = jsonObj.getJSONArray("response");
                                JSONArray ads  = jsonObj.getJSONArray("ads");

                                for (int i = 0; i < ads.length(); i++) {

                                    JSONObject cads = ads.getJSONObject(i);

                                    String banner = "https://syedu1.sg-host.com/Kixx-App/Secure-Portal//assets/images/advertisement-banners/" + cads.getString("banner");
                                    String promo_id = cads.getString("id");

                                   // Toast.makeText(getContext(), banner, Toast.LENGTH_SHORT).show();
                                  //  Log.d("link",banner);
                                    PromosList promosList = new PromosList(banner,promo_id);
                                    promos.add(promosList);

                                    //   Toast.makeText(getContext(), banner , Toast.LENGTH_SHORT).show();
                                    //    Log.d("Error",banner);
                                }





                                PromosAdapter adapter = new PromosAdapter(promos,getActivity(),"ads","seller");
                                recyclerView.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                recyclerView.setLayoutManager(linearLayoutManager);

                                recyclerView.setAdapter(adapter);

                                progressDialog.dismiss();

                            }
                            else
                            {
                                Toast.makeText(getActivity(), getResources().getString(R.string.no_data_error), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
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
                error -> {
                    //   progressDialog.dismiss();
                  //  Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();

                //   parameters.put("id",);

                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }




    private void getadsData() {

        promos.clear();
//        Toast.makeText(getContext(), "ads_data", Toast.LENGTH_SHORT).show();
        progressDialog.show();
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.PROMOS,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        //         Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");


                            if(message.contains("success"))
                            {
                                JSONArray data  = jsonObj.getJSONArray("ads");

                                for (int i = 0; i < data.length(); i++) {

                                    JSONObject c = data.getJSONObject(i);

                                    String banner = "https://syedu1.sg-host.com/Kixx-App/Secure-Portal//assets/images/advertisement-banners/" + c.getString("banner");
                                    String promo_id = c.getString("id");

                                    PromosList promosList = new PromosList(banner,promo_id);
                                    promos.add(promosList);

                                    //   Toast.makeText(getContext(), banner , Toast.LENGTH_SHORT).show();
                                    Log.d("Error",banner);
                                }


                                PromosAdapter adapter = new PromosAdapter(promos,getActivity(),"ads","user");
                                recyclerView.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,true);
                                //  linearLayoutManager.setReverseLayout(true);
                                recyclerView.setLayoutManager(linearLayoutManager);

                                recyclerView.setAdapter(adapter);

                                progressDialog.dismiss();

                            }
                            else
                            {
                                Toast.makeText(getActivity(), getResources().getString(R.string.no_data_error), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
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
                error -> {
                    //   progressDialog.dismiss();
                  //  Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();

                //   parameters.put("id",);

                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }

}