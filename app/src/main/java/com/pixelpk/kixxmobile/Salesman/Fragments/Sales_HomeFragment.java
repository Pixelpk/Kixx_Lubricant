package com.pixelpk.kixxmobile.Salesman.Fragments;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.Salesman.ClaimsScreen;
import com.pixelpk.kixxmobile.Salesman.ModelClasses.ClaimsList;
import com.pixelpk.kixxmobile.Salesman.Sales_UpdateProfile;
import com.pixelpk.kixxmobile.Salesman.adapters.claimAdapter;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.ModelClasses.ImageSliderList;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
import com.pixelpk.kixxmobile.User.Splash;
import com.pixelpk.kixxmobile.User.adapters.ImageSlidingAdapter;
import com.pixelpk.kixxmobile.User.adapters.ViewPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Sales_HomeFragment extends Fragment {

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    JSONArray promodata_json;
    JSONObject promodata_jsonobj;
    LabeledSwitch labeledSwitch;
    LinearLayout Sales_HomeFrag_Claims;

    ProgressDialog progressDialog;
    JSONArray cars,ads;

    JSONObject objcars,objads;
    List<ImageSliderList> imageSliderLists;

    RecyclerView Sales_HomeFragment_imageslider_RV;

    TextView Sales_HomeFragment_shopname_ET,Sales_HomeFragment_shopcontact_ET,Sales_HomeFragment_carclaim_ET,HomeFragment_odometer;

    String user_id,user_name,user_email,user_ph,user_dp,user_gender,user_fcm_id,user_loyality_points = "0000";
    String user_occupation_id,user_occupation_name,user_category,user_user_role,user_odometer = "0000000";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

  //  Switch Sales_HomeFragment_changelang;

    ImageView Sales_HomeFragment_shopimage_IV;

    String sales_userid,bearer_token;
    String sales_shopname,sales_shoph;

    String salesman_id;

    ImageView Sales_HomeFrag_titlebar_kixxlogo;

    Button Sales_HomeFragment_claimoil_ET;

    LinearLayout Sales_HomeScreen_Shopprofile;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sales_fragment_home_new, container, false);

        initializers(view);

        Sales_HomeFragment_claimoil_ET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openweb("https://syedu1.sg-host.com/Kixx-App/Kixx-Shop/auth-login");
            }
        });

        Sales_HomeScreen_Shopprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Sales_UpdateProfile.class);
                startActivity(intent);
            }
        });

        labeledSwitch.setLabelOff(getResources().getString(R.string.arabic));
        labeledSwitch.setLabelOn("EN");
        labeledSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {

                if(isOn == true)
                {
                    editor.putString(Shared.KIXX_APP_LANGUAGE,"1").apply();
                    Intent intent = new Intent(getContext(), Splash.class);
                    startActivity(intent);
                }
                else
                {
                    editor.putString(Shared.KIXX_APP_LANGUAGE,"2").apply();
                    Intent intent = new Intent(getContext(), Splash.class);
                    startActivity(intent);
                }


            }

        });


        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE, "0");

        /*Sales_HomeFragment_changelang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked == true)
                {
                    editor.putString(Shared.KIXX_APP_LANGUAGE,"1").apply();
                    Intent intent = new Intent(getContext(), Splash.class);
                    startActivity(intent);
                }
                else
                {
                    editor.putString(Shared.KIXX_APP_LANGUAGE,"2").apply();
                    Intent intent = new Intent(getContext(), Splash.class);
                    startActivity(intent);
                }

                // do something, the isChecked will be
                // true if the switch is in the On position
            }
        });*/

      if(rtl.equals("1"))
      {
          Sales_HomeFragment_shopname_ET.setGravity(Gravity.END);
          Sales_HomeFrag_titlebar_kixxlogo.setImageResource(R.mipmap.kixx_ar);
      }


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity());

        viewPager.setAdapter(viewPagerAdapter);

        Sales_HomeFrag_Claims.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ClaimsScreen.class);
                startActivity(intent);
            }
        });


        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    public void initializers(View view)
    {
        Sales_HomeScreen_Shopprofile = view.findViewById(R.id.Sales_HomeScreen_Shopprofile);

        Sales_HomeFrag_Claims = view.findViewById(R.id.Sales_HomeFrag_Claims);

     //   viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        labeledSwitch = view.findViewById(R.id.Sales_HomeFragment_changelang);
        progressDialog = new ProgressDialog(getActivity());
        Sales_HomeFragment_shopimage_IV = view.findViewById(R.id.Sales_HomeFragment_shopimage_IV);
        sharedPreferences = getContext().getSharedPreferences("Shared", Context.MODE_PRIVATE);

        editor = sharedPreferences.edit();
  //      user_profile_image = view.findViewById(R.id.user_profile_image);
        String shopidforclaim = sharedPreferences.getString(Shared.loggedIn_sales_shopid,"0");
   //     sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);
       // Toast.makeText(getContext(), shopidforclaim, Toast.LENGTH_SHORT).show();
        Sales_HomeFragment_shopname_ET = (TextView) view.findViewById(R.id.Sales_HomeFragment_shopname_ET);
        Sales_HomeFragment_shopcontact_ET = (TextView) view.findViewById(R.id.Sales_HomeFragment_shopcontact_ET);
        Sales_HomeFrag_titlebar_kixxlogo = (ImageView) view.findViewById(R.id.Sales_HomeFrag_titlebar_kixxlogo);
      //  HomeFragment_loyalitypnts = (TextView) view.findViewById(R.id.HomeFragment_loyalitypnts);
        //  HomeFragment_odometer = (TextView) view.findViewById(R.id.HomeFragment_odometer);
        Sales_HomeFragment_imageslider_RV = view.findViewById(R.id.Sales_HomeFragment_imageslider_RV);

        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);
        Sales_HomeFragment_claimoil_ET = view.findViewById(R.id.Sales_HomeFragment_claimoil_ET);
      //  ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity());

     //   viewPager.setAdapter(viewPagerAdapter);

    //    dotscount = viewPagerAdapter.getCount();
    //    dots = new ImageView[dotscount];

        Sales_HomeFragment_carclaim_ET = view.findViewById(R.id.Sales_HomeFragment_carclaim_ET);


        imageSliderLists = new ArrayList<>();
        sales_userid = sharedPreferences.getString(Shared.loggedIn_sales_id,"0");
        bearer_token = sharedPreferences.getString(Shared.sales_loggedIn_jwt,"0");
        get_user_data(sales_userid);
        get_claims_data(shopidforclaim);

        labeledSwitch = view.findViewById(R.id.Sales_HomeFragment_changelang);
        String lang = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

       /* if(lang!=null)
        {
            if(lang.equals("1"))
            {
                labeledSwitch.setOn(true);

            }
            else if(lang.equals("2"))
            {
                labeledSwitch.setOn(false);
            }
        }*/

        if(lang!=null)
        {
            if(lang.equals("1"))
            {
                labeledSwitch.setOn(true);

            }
            else if(lang.equals("2"))
            {
                labeledSwitch.setOn(false);
            }
        }

    //  Toast.makeText(getContext(), bearer_token, Toast.LENGTH_SHORT).show();

    }

    private void get_user_data(final String id) {

        progressDialog.show();
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.SALES_END,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                  //   Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");

                            if(message.contains("success"))
                            {
                                JSONArray data  = jsonObj.getJSONArray("user");
                            //    String cardata = jsonObj.getString("cars");
                                String adsdata = jsonObj.getString("ads");
                                String promodata = jsonObj.getString("promo");

                         //       Toast.makeText(getContext(), promodata, Toast.LENGTH_SHORT).show();
                              /*  if(!cardata.equals("null")) {
                                    cars = jsonObj.getJSONArray("cars");
                                }*/

                         //       Toast.makeText(getContext(), promodata, Toast.LENGTH_SHORT).show();

                                if(!promodata.equals("null")) {



                                    String banner = null;
                                    String id = null;




                                    promodata_json = jsonObj.getJSONArray("promo");

                                    for (int i = 0; i < 1; i++) {
                                        promodata_jsonobj = promodata_json.getJSONObject(i);

                                        id = promodata_jsonobj.getString("id");
                                        String title = promodata_jsonobj.getString("title");
                                        String description = promodata_jsonobj.getString("description");
                                        banner = promodata_jsonobj.getString("banner");
                                        String qr_image = promodata_jsonobj.getString("qr_image");


                                    }

                                    ImageSliderList imageSliderList = new ImageSliderList(URLs.USER_Active_promo+banner,id);
                                    imageSliderLists.add(imageSliderList);

                                    ImageSlidingAdapter adapter = new ImageSlidingAdapter(imageSliderLists,getContext(),"promos","sales");
                                    Sales_HomeFragment_imageslider_RV.setHasFixedSize(true);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                                    Sales_HomeFragment_imageslider_RV.setLayoutManager(linearLayoutManager);
                                    //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    Sales_HomeFragment_imageslider_RV.setAdapter(adapter);

                                        /*user_cars_list.add(car_number + " " + company + " " + name);
                                        car_id_list.add(id);*/



                                 /*   spinner.setItems(user_cars_list);
                                    HomeFragment_carnotfound_IV.setVisibility(View.GONE);*/


                                }
                                else
                                {
                                    //HomeFragment_carnotfound_IV.setVisibility(View.VISIBLE);
                                }

                               /* if(!adsdata.equals("null")) {

                                    ads = jsonObj.getJSONArray("ads");

                                    for (int i = 0; i < ads.length(); i++) {
                                        objads = ads.getJSONObject(i);
                                        String imagename = objads.getString("banner");
                                        // Toast.makeText(getActivity(), URLs.BANNER_IMAGES+imagename, Toast.LENGTH_SHORT).show();
                                        String url = URLs.BANNER_IMAGES + imagename;
                                        ImageSliderList imageSliderList = new ImageSliderList(url,"1");
                                        imageSliderLists.add(imageSliderList);

                                  //      Log.d("ImageURl",URLs.BANNER_IMAGES + imagename);

                                    }

                                    ImageSlidingAdapter adapter = new ImageSlidingAdapter(imageSliderLists,getActivity(),"ads","sales");
                                    Sales_HomeFragment_imageslider_RV.setHasFixedSize(true);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                                    Sales_HomeFragment_imageslider_RV.setLayoutManager(linearLayoutManager);
                                    //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    Sales_HomeFragment_imageslider_RV.setAdapter(adapter);

                                }*/



                                for (int i = 0; i < data.length(); i++) {

                                    JSONObject c = data.getJSONObject(i);

                                    /*if(!cardata.equals("null")) {

                                        objcars = cars.getJSONObject(i);
                                        user_odometer = objcars.getString("odometer");
                                        editor.putString(Shared.loggedIn_user_odometer,user_id);
                                    }*/
                            /*        user_id = c.getString("userid");
                                    user_name = c.getString("name");
                                    user_email = c.getString("email");
                                    user_ph = c.getString("phone");
                                    user_dp = c.getString("profile_img");
                                    user_gender = c.getString("gender");
                                    user_fcm_id = c.getString("fcm_id");
                                    user_loyality_points = c.getString("loyality_points");
                                    user_occupation_id = c.getString("occupation_id");
                                    user_occupation_name = c.getString("occupation_name");
                                    user_category = c.getString("category");
                                    user_user_role = c.getString("user_role");*/

                                    sales_shopname = c.getString("shop_name");
                                    sales_shoph = c.getString("shop_phone");
                                    salesman_id = c.getString("salesman_id");

                                //    Toast.makeText(getActivity(), salesman_id, Toast.LENGTH_SHORT).show();

                                    //        Toast.makeText(getContext(), user_dp, Toast.LENGTH_SHORT).show();

                                    editor.putString(Shared.Sales_loggedIn_salesman_id,salesman_id);
                                    editor.putString(Shared.sales_loggedIn_user_name,sales_shopname);
                                    editor.putString(Shared.sales_loggedIn_user_ph,sales_shoph);
                        /*            editor.putString(Shared.loggedIn_user_email,user_email);
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


                                setdata_to_fields(sales_shopname,sales_shoph,"10");



                            }
                            else
                            {
                               // Toast.makeText(getActivity(), getResources().getString(R.string.usernotfound), Toast.LENGTH_SHORT).show();
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
                       // Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + bearer_token);
                return headers;
            }


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

    public void setdata_to_fields(String name,String contact,String loyality_pnts)
    {
        if(!name.equals("null"))
        {
            Sales_HomeFragment_shopname_ET.setVisibility(View.VISIBLE);
            Sales_HomeFragment_shopname_ET.setText(name);
        }
        else
        {
            Sales_HomeFragment_shopname_ET.setVisibility(View.VISIBLE);
            Sales_HomeFragment_shopname_ET.setText("No Name");
        }

        if(!contact.equals("null"))
        {
            Sales_HomeFragment_shopcontact_ET.setVisibility(View.VISIBLE);
            Sales_HomeFragment_shopcontact_ET.setText(contact);
        }
        else
        {
            Sales_HomeFragment_shopcontact_ET.setVisibility(View.INVISIBLE);
        }

      /*  if(!loyality_pnts.equals("null"))
        {
            Sales_HomeFragment_carclaim_ET.setVisibility(View.VISIBLE);
            Sales_HomeFragment_carclaim_ET.setText(loyality_pnts + " pnts");
        }
        else
        {
            Sales_HomeFragment_carclaim_ET.setVisibility(View.VISIBLE);
            Sales_HomeFragment_carclaim_ET.setText("0" + " Claims");
        }*/

      /*  if(!odometer.equals("null"))
        {
            HomeFragment_odometer.setVisibility(View.VISIBLE);
            HomeFragment_odometer.setText(odometer + " KM");
        }
        else
        {
            HomeFragment_odometer.setVisibility(View.INVISIBLE);
        }*/

       /* if(!profile_image.equals("null") && !profile_image.equals(""))
        {
            user_profile_image.setVisibility(View.VISIBLE);
            Glide.with(getActivity()).load(profile_image).into( user_profile_image);
        }
        else
        {
            user_profile_image.setVisibility(View.VISIBLE);
            Glide.with(getActivity()).load(URLs.USER_PROFILE_IMAGE_DEFAULT).into( user_profile_image);
        }*/


        progressDialog.dismiss();


    }



    private void get_claims_data(String id) {

        progressDialog.show();
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.SALES_SHOP_CLAIMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //         Toast.makeText(ClaimsScreen.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");


                            if(message.contains("success"))
                            {
                                JSONArray claims  = jsonObj.getJSONArray("resp");
                                String claimable_litres = jsonObj.getString("claimable_litres");
                               // Toast.makeText(getContext(), claimable_litres, Toast.LENGTH_SHORT).show();

                                if(!claims.equals("null")) {

                                 //   Toast.makeText(getActivity(), String.valueOf(claims.length()), Toast.LENGTH_SHORT).show();

                                    if(getActivity()!=null)
                                    {
                                        Sales_HomeFragment_carclaim_ET.setText(claimable_litres + " " + getResources().getString(R.string.liters));
                                    }



                                }




                            }
                            else
                            {
                                /*if(getContext()!=null) {
                            //        Toast.makeText(getContext(), getResources().getString(R.string.usernotfound), Toast.LENGTH_SHORT).show();
                                }*/
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
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + bearer_token);
                return headers;
            }


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("shop_id", id);


                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }

    public void openweb(String urlString)
    {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        try {
            this.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            // Chrome browser presumably not installed so allow user to choose instead
            intent.setPackage(null);
            this.startActivity(intent);
        }
    }

}