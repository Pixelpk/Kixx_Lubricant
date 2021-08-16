package com.pixelpk.kixxmobile.User.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.text.TextUtilsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
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
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.pixelpk.kixxmobile.Constants;
import com.pixelpk.kixxmobile.Location_Permission;
import com.pixelpk.kixxmobile.PermissionUtils;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.Salesman.HomeScreen;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.AddCar.AddCarScreen;
import com.pixelpk.kixxmobile.User.AddCarInfoScreen;
import com.pixelpk.kixxmobile.User.ClaimReward.ClaimRewardPointsScreen;
import com.pixelpk.kixxmobile.User.EditCarInfo;
import com.pixelpk.kixxmobile.User.ModelClasses.CarDetailsList;
import com.pixelpk.kixxmobile.User.ModelClasses.CarStatus;
import com.pixelpk.kixxmobile.User.ModelClasses.ImageSliderList;
import com.pixelpk.kixxmobile.User.ModelClasses.Notificationlist;
import com.pixelpk.kixxmobile.User.ModelClasses.PromosList;
import com.pixelpk.kixxmobile.User.OilRecommendation.OIl_Recommendation;
import com.pixelpk.kixxmobile.User.PromoDetailsScreen;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
import com.pixelpk.kixxmobile.User.Signin;
import com.pixelpk.kixxmobile.User.Splash;
import com.pixelpk.kixxmobile.User.TutorialScreen;
import com.pixelpk.kixxmobile.User.UpdateUserProfile;
import com.pixelpk.kixxmobile.User.adapters.CardDetailsAdapter;
import com.pixelpk.kixxmobile.User.adapters.ImageSliderAdapter;
import com.pixelpk.kixxmobile.User.adapters.ImageSlidingAdapter;
import com.pixelpk.kixxmobile.User.adapters.NotificationAdapter;
import com.pixelpk.kixxmobile.User.adapters.PromosAdapter;
import com.pixelpk.kixxmobile.User.adapters.ViewPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;
import static com.pixelpk.kixxmobile.Location_Permission.MY_PERMISSIONS_REQUEST_LOCATION;
import static com.pixelpk.kixxmobile.URLs.BASE_URL;
import static com.pixelpk.kixxmobile.URLs.update_car_mileage;


public class HomeFragment extends Fragment {

    ViewPager viewPager;
    LinearLayout sliderDotspanel, HomeFragment_findnearbyshop,layout_profile;
    private int dotscount;
    private ImageView[] dots;
    CarStatus carStatus;
    // Switch HomeFragment_changelanguage;



    ImageView HomeFragment_badges_IV;
    TextView HomeFragment_badges_TV;
    DecimalFormat numberFormat = new DecimalFormat("#.0");
    LabeledSwitch labeledSwitch;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String rep_odometer;
    int[] array_rep_odometer;

    String shown_str = "0";

    String shared_daily_mileage_str;

    String id_car;

    String car_number_str;
    String car_current_mileage;
    String status_average;

    ProgressDialog progressDialog;
    JSONArray cars, ads, promodata_json;
    JSONObject adsdata_json;
    Button HomeFragment_oilchangebtn;

    ArrayList<CarStatus> user_car_status;

    String date_differ;

    String update_link = BASE_URL+"Kixx-App/Secure-Portal/api/user/update-mileage.php";


    TextView HomeFragment_username, HomeFragment_contact, HomeFragment_loyalitypnts, HomeFragment_odometer;

    String user_id, user_name, user_email, user_ph, user_dp, user_gender, user_fcm_id, user_loyality_points = "0000", user_status = "0";
    String user_occupation_id, user_occupation_name, user_category, user_user_role, user_odometer = "0000000";

    JSONObject objcars, objads, objcars_activity;

    CircleImageView user_profile_image;

    RecyclerView HomeFragment_history_RV, HomeFragment_imageslider_RV, HomeFragment_ads_RV;

    ArrayList<ImageSliderList> imageSliderLists;
    ArrayList<ImageSliderList> adslist;

    ArrayList<Integer> arrayList_date1;
    ArrayList<Integer> arrayList_date2;

    ArrayList<Integer> arrayList_odometer1;
    ArrayList<Integer> arrayList_odometer2;
    ArrayList<Integer> arrayList_result_date;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;//    TextView test;


    ArrayList<Integer> arrayList_result_odometer;

    String KX_formatted_userid;

    String car_identity;

    TextView HomeFragment_User_id;
    LinearLayout HomeScreen_EditCar_LL;

    List<String> user_cars_list, car_id_list, please_select_car;
    List<CarDetailsList> myListData;
    String token;
    Spinner spinner;

    int length_data;

    ImageView HomeFragment_profile_IV, HomeFragment_titlebar_kixxlogo;
    LinearLayout HomeFragment_carhist_IV, HomeFragment_carnotfound_IV;
    Activity activity;

    TextView HomeFragment_editprofile;

    LinearLayout reward_background;

    String badge_activity_count;

    LinearLayout HomeFragment_refertoafriend;

    ProgressDialog createlink_progress;

//    TextView car_change_history;

    LinearLayout HomeFragment_badgegroup, HomeFragment_userid;

    ScrollView HomeFragment_LL;
    ConstraintLayout HomeFragment_nonet_LL;
    int index = 0;
    float result_sub = 0;
    float diff_date = 0;
    float result_div = 0;
    float result_div_float = 0;

    String Shared_denied_str = "0";

    String result_div_str;
    LinearLayout HomeFragment_oilrecommendation;

    final int duration = 1000;
    final int pixelsToMove = 1000;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    Button btn;
    private RecyclerView rv_autoScroll;
    int scrollY = 0;

    private final Runnable SCROLLING_RUNNABLE = new Runnable() {

        @Override
        public void run() {
            HomeFragment_ads_RV.smoothScrollBy(pixelsToMove, scrollY);
            mHandler.postDelayed(this, duration);
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_new, container, false);

        sharedPreferences = getActivity().getSharedPreferences("Shared", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        shared_daily_mileage_str = sharedPreferences.getString("shared_daily_mileage", "0");

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);

        String userid = sharedPreferences.getString(Shared.loggedIn_user_id, "0");
        KX_formatted_userid = sharedPreferences.getString(Shared.LoggedIn_fromatted_userid, "0");
        //   Toast.makeText(getActivity(), userid, Toast.LENGTH_SHORT).show();

        initializers(view);


        HomeFragment_oilchangebtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent(getContext(),HomeScreen.class);
                intent.putExtra("oilchange","map");
                startActivity(intent);*/
                //  MapsFragment mapsFragment = new MapsFragment();
                replaceFragments();
            }
        });

        labeledSwitch.setLabelOff(getResources().getString(R.string.arabic));
        labeledSwitch.setLabelOn("EN");
        labeledSwitch.setOnToggledListener(new OnToggledListener()
        {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {

                if (isOn == true)
                {
                    editor.putString(Shared.KIXX_APP_LANGUAGE, "1").apply();
                    Intent intent = new Intent(getContext(), Splash.class);
                    startActivity(intent);
                }

                else
                {
                    editor.putString(Shared.KIXX_APP_LANGUAGE, "2").apply();
                    Intent intent = new Intent(getContext(), Splash.class);
                    startActivity(intent);
                }


            }

        });

        HomeFragment_userid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdateUserProfile.class);
                startActivity(intent);
            }
        });

        HomeFragment_titlebar_kixxlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TutorialScreen.class);
                editor.putString(Constants.Tutorial_Screen_id, "0").apply();
                startActivity(intent);
            }
        });

        HomeFragment_badgegroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ClaimRewardPointsScreen.class);
                intent.putExtra("points", user_loyality_points);
                intent.putExtra("badge", badge_activity_count);
                startActivity(intent);
                getActivity().finish();
            }
        });


        HomeFragment_refertoafriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createlink_progress.show();
                createlink();
            }
        });

        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE, "0");

//            Toast.makeText(getContext(),rtl, Toast.LENGTH_SHORT).show();

        if (rtl.equals("1"))
        {
            HomeFragment_titlebar_kixxlogo.setImageResource(R.mipmap.kixx_ar);
        }

        if (activity != null && isAdded())
        {
            imageslider();
            get_user_data(userid);
            //    Toast.makeText(activity, user_loyality_points, Toast.LENGTH_SHORT).show();
        }

        layout_profile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), UpdateUserProfile.class);
                startActivity(intent);
            }
        });

        HomeFragment_username.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), UpdateUserProfile.class);
                startActivity(intent);
            }
        });


        HomeFragment_profile_IV.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               /* Intent intent = new Intent(getContext(), UpdateUserProfile.class);
                startActivity(intent);*/
            }
        });


        HomeFragment_carhist_IV.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), AddCarInfoScreen.class);
                intent.putExtra("oilchange", "2");
                startActivity(intent);
            }
        });

        reward_background.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), ClaimRewardPointsScreen.class);
                intent.putExtra("points", user_loyality_points);
                intent.putExtra("badge", badge_activity_count);
                startActivity(intent);
                getActivity().finish();
            }
        });

        user_profile_image.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), UpdateUserProfile.class);
                startActivity(intent);
            }
        });

        getPromoData();
        getadsData();


       /*// spinner.setItems("BMW", "Chevrolette", "KIA", "Nissan", "Mercedes Benz");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
         //       Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

                if(!car_id_list.isEmpty())
                {
                    String car_identity = car_id_list.get(position);
                    get_user_car_activity(car_identity);
                  //  Toast.makeText(getContext(), car_identity, Toast.LENGTH_SHORT).show();

                    HomeFragment_carhist_IV.setVisibility(View.GONE);
                    HomeFragment_history_RV.setVisibility(View.VISIBLE);
                }
                else
                {
                    HomeFragment_carhist_IV.setVisibility(View.VISIBLE);
                    HomeFragment_history_RV.setVisibility(View.GONE);
                }




            }
        });*/

   /*     HomeFragment_changelanguage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!car_id_list.isEmpty()) {


                    ((TextView) view.findViewById(R.id.spinner_dropdown_tv_icon)).setTextColor(getResources().getColor(R.color.white));
                    car_identity = car_id_list.get(position);

                    if (position != 0) {
                        get_user_car_activity(car_identity);
                        //  Toast.makeText(getContext(), car_identity, Toast.LENGTH_SHORT).show();

                        HomeFragment_carhist_IV.setVisibility(View.GONE);
                        HomeFragment_history_RV.setVisibility(View.VISIBLE);
                    } else {

                        HomeFragment_carhist_IV.setVisibility(View.VISIBLE);
                        HomeFragment_history_RV.setVisibility(View.GONE);
                    }
                } else {
                    ((TextView) view.findViewById(R.id.spinner_dropdown_tv_icon)).setTextColor(getResources().getColor(R.color.white));
                    HomeFragment_carhist_IV.setVisibility(View.VISIBLE);
                    HomeFragment_history_RV.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        HomeFragment_carnotfound_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddCarInfoScreen.class);
                intent.putExtra("oilchange", "2");
                startActivity(intent);
            }
        });

        HomeFragment_oilrecommendation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OIl_Recommendation.class);
                startActivity(intent);
            }
        });

        HomeScreen_EditCar_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddCarScreen.class);
                startActivity(intent);
            }
        });


        return view;

    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
//    {
//        switch (requestCode)
//        {
//            case MY_PERMISSIONS_REQUEST_LOCATION:
//            {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                {
//                    // permission was granted, yay! Do the
//                    // location-related task you need to do.
//                    if (ContextCompat.checkSelfPermission(getActivity(),
//                            Manifest.permission.ACCESS_FINE_LOCATION)
//                            == PackageManager.PERMISSION_GRANTED)
//                    {
//                        Toast.makeText(activity, "grant", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//
//                else
//                {
//                    PermissionUtils.setShouldShowStatus(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
////                    finish();
//                }
//
//                return;
//            }}}

    @Override
    public void onResume() {
        super.onResume();

       /* String user_id = sharedPreferences.getString(Shared.loggedIn_user_id,"0");

        if(sharedPreferences.getString(Shared.loggedIn_user_ph,"0").equals(""))
        {
            get_user_data(user_id);
        }
        else
        {
            get_data_from_shared_preferences();
        }*/


    }

    public void initializers(View view)
    {
        String lang = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE, "0");
        //  editor.putString(Shared.User_promo,"2").apply();

        //  Toast.makeText(this, lang, Toast.LENGTH_SHORT).show();
        adslist = new ArrayList<>();

        arrayList_date1 = new ArrayList<>();
        arrayList_date2 = new ArrayList<>();
        arrayList_odometer1 = new ArrayList<>();
        arrayList_odometer2 = new ArrayList<>();
//        arrayList_result_odometer = new int[]{};
        arrayList_result_date = new ArrayList<>();

        HomeFragment_oilrecommendation = view.findViewById(R.id.HomeFragment_oilrecommendation);

        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        arrayList_result_odometer = new ArrayList<>();

        progressDialog = new ProgressDialog(getContext());


        user_profile_image = view.findViewById(R.id.user_profile_image);

        sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);
        HomeFragment_username = (TextView) view.findViewById(R.id.HomeFragment_username);
        HomeFragment_contact = (TextView) view.findViewById(R.id.HomeFragment_contact);
        HomeFragment_loyalitypnts = (TextView) view.findViewById(R.id.HomeFragment_loyalitypnts);
        HomeFragment_User_id = view.findViewById(R.id.HomeFragment_User_id);
        HomeFragment_titlebar_kixxlogo = view.findViewById(R.id.HomeFragment_titlebar_kixxlogo);
        reward_background = view.findViewById(R.id.reward_background);
        HomeFragment_badges_IV = view.findViewById(R.id.HomeFragment_badges_IV);
        HomeFragment_badges_TV = view.findViewById(R.id.HomeFragment_badges_TV);
        HomeFragment_badgegroup = view.findViewById(R.id.HomeFragment_badgegroup);
        HomeScreen_EditCar_LL = view.findViewById(R.id.HomeScreen_EditCar_LL);

        layout_profile = view.findViewById(R.id.layout_edit_profile);

//        car_change_history = view.findViewById(R.id.textView_car_history);
        //  HomeFragment_odometer = (TextView) view.findViewById(R.id.HomeFragment_odometer);
        // ViewUtils.isLayoutRtl(HomeFragment_username);
        HomeFragment_User_id.setText(getResources().getString(R.string.User_id) + ": " + KX_formatted_userid);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext());

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        HomeFragment_history_RV = view.findViewById(R.id.HomeFragment_history_RV);
        HomeFragment_imageslider_RV = view.findViewById(R.id.HomeFragment_imageslider_RV);
        HomeFragment_ads_RV = view.findViewById(R.id.HomeFragment_ads_RV);
        HomeFragment_nonet_LL = view.findViewById(R.id.HomeFragment_nonet_LL);
        HomeFragment_LL = view.findViewById(R.id.HomeFragment_LL);
        // HomeFragment_adsbannerslider_RV = view.findViewById(R.id.HomeFragment_adsbannerslider_RV);

        imageSliderLists = new ArrayList<>();
        user_cars_list = new ArrayList<>();
        car_id_list = new ArrayList<>();
        myListData = new ArrayList<>();
        please_select_car = new ArrayList<>();
        user_car_status = new ArrayList<>();

        token = sharedPreferences.getString(Shared.loggedIn_jwt, "0");
        spinner = (Spinner) view.findViewById(R.id.spinner);
        HomeFragment_carhist_IV = (LinearLayout) view.findViewById(R.id.HomeFragment_carhist_IV);

     /*   ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,list);

        spinner.setAdapter(adapter);*/
        labeledSwitch = view.findViewById(R.id.HomeFragment_changelanguage);
        HomeFragment_refertoafriend = view.findViewById(R.id.HomeFragment_refertoafriend);

        HomeFragment_profile_IV = view.findViewById(R.id.HomeFragment_profile_IV);

        activity = getActivity();

        HomeFragment_editprofile = view.findViewById(R.id.HomeFragment_editprofile);
        HomeFragment_userid = view.findViewById(R.id.HomeFragment_userid);

     /*   CarDetailsList list  = new CarDetailsList("25-12-2020","20000","25000");
        CarDetailsList list2 = new CarDetailsList("14-04-2021","25000","30000");
        CarDetailsList list3 = new CarDetailsList("03-06-2021","30000","35000");
        CarDetailsList list4 = new CarDetailsList("07-09-2021","40000","45000");
        CarDetailsList list5 = new CarDetailsList("20-12-2021","50000","55000");
        CarDetailsList list6 = new CarDetailsList("21-03-2022","55000","60000");
        myListData.add(list);
        myListData.add(list2);
        myListData.add(list3);
        myListData.add(list4);
        myListData.add(list5);
        myListData.add(list6);*/

        //    HomeFragment_changelanguage = view.findViewById(R.id.HomeFragment_changelanguage);

        HomeFragment_oilchangebtn = view.findViewById(R.id.HomeFragment_oilchangebtn);

        shown_str = sharedPreferences.getString("shown_shared", "0");

        createlink_progress = new ProgressDialog(getContext());

        if (lang != null)
        {
            if (lang.equals("1"))
            {
                labeledSwitch.setOn(true);
            }

            else if (lang.equals("2"))
            {
                labeledSwitch.setOn(false);
            }
        }


        CardDetailsAdapter adapter2 = new CardDetailsAdapter(myListData, getContext());
        HomeFragment_history_RV.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        HomeFragment_history_RV.setLayoutManager(linearLayoutManager2);
        //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter2.notifyDataSetChanged();
        HomeFragment_history_RV.setAdapter(adapter2);

        HomeFragment_carnotfound_IV = view.findViewById(R.id.HomeFragment_carnotfound_IV);


    }


    public void imageslider()
    {
        for (int i = 0; i < dotscount; i++)
        {
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
    }


    public void setdata_to_fields(String name, String contact, String loyality_pnts, String odometer, String profile_image) {
        // Toast.makeText(getContext(), badge_activity_count, Toast.LENGTH_SHORT).show();
        editor.putString(Shared.user_profile_image, profile_image).apply();
        // Toast.makeText(activity, profile_image, Toast.LENGTH_SHORT).show();
        if (!name.equals("null")) {
            String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE, "0");

//            Toast.makeText(getContext(),rtl, Toast.LENGTH_SHORT).show();

            if (rtl.equals("1")) {
                HomeFragment_username.setGravity(Gravity.END);
            }

            HomeFragment_username.setVisibility(View.VISIBLE);
            HomeFragment_username.setText(name);
        } else {
            HomeFragment_username.setVisibility(View.VISIBLE);
            HomeFragment_username.setText("No Name");
        }

        if (!contact.equals("null")) {
            /*HomeFragment_contact.setVisibility(View.GONE);
            HomeFragment_contact.setText(contact);*/
        } else {
            //  HomeFragment_contact.setVisibility(View.GONE);
        }

        if (!loyality_pnts.equals("null")) {
            //   Toast.makeText(activity, loyality_pnts, Toast.LENGTH_SHORT).show();
            HomeFragment_loyalitypnts.setVisibility(View.VISIBLE);
            HomeFragment_loyalitypnts.setText(loyality_pnts);
        } else {
            //    Toast.makeText(activity, loyality_pnts, Toast.LENGTH_SHORT).show();
            HomeFragment_loyalitypnts.setVisibility(View.VISIBLE);
            HomeFragment_loyalitypnts.setText("0000000");
        }

      /*  if(!odometer.equals("null"))
        {
            HomeFragment_odometer.setVisibility(View.VISIBLE);
            HomeFragment_odometer.setText(odometer + " KM");
        }
        else
        {
            HomeFragment_odometer.setVisibility(View.INVISIBLE);
        }*/


        if (!profile_image.equals("null")) {
            user_profile_image.setVisibility(View.VISIBLE);

            if (getActivity() != null) {
   /*         new Thread(new Runnable() {
                @Override
                public void run() {
                    Glide.get(getActivity()).clearDiskCache();
                }
            }).start();*/

                load_img_profile(profile_image);


            }
            //  Log.d("profile",URLs.USER_IMAGE_URL + profile_image);
        } else {
            if (getActivity() != null) {
                user_profile_image.setVisibility(View.VISIBLE);
                user_profile_image.setImageDrawable(getResources().getDrawable(R.drawable.profilepic));
            /*if(getActivity()!=null) {
                Glide.with(getContext())
                        .load(URLs.USER_IMAGE_URL + profile_image)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(user_profile_image);
            }*/
            }
        }


        progressDialog.dismiss();


    }

    private void load_img_profile(String profile_image)
    {
        progressDialog.setMessage("Please Wait While We Are Loading the Image");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Log.d("profile_user_tag",URLs.USER_IMAGE_URL + profile_image);

        Glide.with(getContext())
                .load(URLs.USER_IMAGE_URL + profile_image)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource)
                    {
                        progressDialog.dismiss();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressDialog.dismiss();
                        return false;
                    }
                })
                .skipMemoryCache(true)
                .dontAnimate()
                .into(user_profile_image);
    }


    private void get_user_data(final String id) {
        user_car_status.clear();

        progressDialog.show();
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.END_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //   Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                        Log.d("END_USER", response);
                        HomeFragment_nonet_LL.setVisibility(View.GONE);
                        HomeFragment_LL.setVisibility(View.VISIBLE);
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");
                            String car_number_globe = "";
                            car_id_list.add("-1");
                            if (message.contains("success")) {
                                JSONArray data = jsonObj.getJSONArray("user");
                                // JSONArray points  = jsonObj.getJSONArray("points");
                                String cardata = jsonObj.getString("cars");
                                user_loyality_points = jsonObj.getString("points");
                                String adsdata = jsonObj.getString("ads");
                                String promodata = jsonObj.getString("promo");

                                //    Toast.makeText(activity, promodata, Toast.LENGTH_SHORT).show();

                                badge_activity_count = jsonObj.getString("activity_count");
//                                badge_activity_count = "30";

                                //     Toast.makeText(getContext(), badge_activity_count, Toast.LENGTH_SHORT).show();
                                if (getActivity() != null) {
                                    user_cars_list.add(getResources().getString(R.string.select_your_car));
                                }

                                if (!cardata.equals("null")) {
                                    cars = jsonObj.getJSONArray("cars");

                                    //   Toast.makeText(activity, cars.toString(), Toast.LENGTH_SHORT).show();


                                    for (int i = 0; i < cars.length(); i++) {
                                        objcars = cars.getJSONObject(i);
                                        String id = objcars.getString("id");
                                        String car_number = objcars.getString("car_number");
                                        String company = objcars.getString("company");
                                        String name = objcars.getString("name");
                                        String car_status = objcars.getString("car_status");
                                        String car_activity = objcars.getString("car_activity");

                                        car_number_globe = car_number;

                                        user_cars_list.add(car_number/* + " " + company*/ + " " + name);
                                        car_id_list.add(id);
                                        //                          Toast.makeText(getContext(), car_activity + " " + car_status, Toast.LENGTH_SHORT).show();
                                        if (car_status.equals("0")) {
                                            if (car_activity.equals("1")) {
                                                carStatus = new CarStatus("1", id, car_number);
                                                user_car_status.add(carStatus);
                                            }
                                        }
                                    }

                                    if (!user_car_status.isEmpty()) {
                                        Mileage_dialogbox(user_car_status.get(index).getCar_id(), user_car_status.get(index).getStatus(), user_car_status.get(index).getCar_number(), index);
                                    }


                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(spinner.getContext(),
                                            R.layout.spinner_textview_layout_with_icon, R.id.spinner_dropdown_tv_icon, user_cars_list);
                                    //   adapter.setDropDownViewResource(R.layout.spinner_textview_layout_with_icon);

                                    spinner.setAdapter(adapter);
                                    spinner.setSelection(1);
//                                    car_change_history.setText(R.string.oil_change);
                                    //spinner.setItems(user_cars_list);
                                    HomeFragment_carnotfound_IV.setVisibility(View.GONE);


                                } else {

                                    if (getActivity() != null) {
                                        please_select_car.add(getResources().getString(R.string.select_your_car));
                                    }
                                    //   please_select_car.add("Please select a car");
                                   /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                            android.R.layout.simple_spinner_item,please_select_car);*/

                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(spinner.getContext(),
                                            R.layout.spinner_textview_layout_with_icon, R.id.spinner_dropdown_tv_icon, please_select_car);

                                    //   adapter.setDropDownViewResource(R.layout.spinner_textview_layout);
                                    spinner.setAdapter(adapter);
                                    HomeFragment_carhist_IV.setVisibility(View.VISIBLE);
                                }


                      /*          if(!promodata.equals("null")) {
                                    //promodata_json = jsonObj.getJSONObject("promo");
                                    promodata_json = jsonObj.getJSONArray("promo");
                                    Toast.makeText(activity, "in", Toast.LENGTH_SHORT).show();

                                    for (int i = 0; i < promodata_json.length(); i++) {
                                       JSONObject promo_ads = promodata_json.getJSONObject(i);
                                        String id = promo_ads.getString("id");
                                        String banner = promo_ads.getString("banner");
                                        Toast.makeText(getContext(), banner, Toast.LENGTH_SHORT).show();
                                        ImageSliderList imageSliderList = new ImageSliderList(URLs.USER_Active_promo+banner,id);
                                        imageSliderLists.add(imageSliderList);

                                    }



                                    ImageSlidingAdapter adapter = new ImageSlidingAdapter(imageSliderLists,getContext());
                                    HomeFragment_imageslider_RV.setHasFixedSize(true);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                                    HomeFragment_imageslider_RV.setLayoutManager(linearLayoutManager);
                                    //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    HomeFragment_imageslider_RV.setAdapter(adapter);

                                        *//*user_cars_list.add(car_number + " " + company + " " + name);
                                        car_id_list.add(id);*//*



                                 *//*   spinner.setItems(user_cars_list);
                                    HomeFragment_carnotfound_IV.setVisibility(View.GONE);*//*


                                }
                                else
                                {
                                    //HomeFragment_carnotfound_IV.setVisibility(View.VISIBLE);
                                }
*/


                         /*       if(!adsdata.equals("null")) {
                                    adsdata_json = jsonObj.getJSONObject("ads");

                                        String id = adsdata_json.getString("id");
                                    *//*    String title = promodata_json.getString("title");
                                        String description = promodata_json.getString("description");*//*
                                        String banner = "https://syedu1.sg-host.com/Kixx-App/Secure-Portal//assets/images/advertisement-banners/" + adsdata_json.getString("banner");
                                   //     String qr_image = promodata_json.getString("qr_image");

                                    //    Log.d("promolink",banner);

                           //         Toast.makeText(getContext(), banner, Toast.LENGTH_SHORT).show();
                                    ImageSliderList imageSliderList = new ImageSliderList(banner,id);
                                    adslist.add(imageSliderList);

                                    ImageSlidingAdapter adapter = new ImageSlidingAdapter(adslist,getContext());
                                    HomeFragment_ads_RV.setHasFixedSize(true);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                                    HomeFragment_ads_RV.setLayoutManager(linearLayoutManager);
                                    //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    HomeFragment_ads_RV.setAdapter(adapter);

                                        *//*user_cars_list.add(car_number + " " + company + " " + name);
                                        car_id_list.add(id);*//*



                                 *//*   spinner.setItems(user_cars_list);
                                    HomeFragment_carnotfound_IV.setVisibility(View.GONE);*//*


                                }
                                else
                                {
                                    //HomeFragment_carnotfound_IV.setVisibility(View.VISIBLE);
                                }
*/


                              /*  if(!adsdata.equals("null")) {
                                    ads = jsonObj.getJSONArray("ads");

                                    for (int i = 0; i < ads.length(); i++) {
                                        objads = ads.getJSONObject(i);
                                        String imagename = objads.getString("banner");
                                        String id = objads.getString("id");
                                       // Toast.makeText(getActivity(), URLs.BANNER_IMAGES+imagename, Toast.LENGTH_SHORT).show();
                                        String url = URLs.BANNER_IMAGES + imagename;
                                        ImageSliderList imageSliderList = new ImageSliderList(url,id);
                                        imageSliderLists.add(imageSliderList);

                                        Log.d("ImageURl",URLs.BANNER_IMAGES + imagename);

                                    }

                                    ImageSlidingAdapter adapter = new ImageSlidingAdapter(imageSliderLists,getContext());
                                    HomeFragment_imageslider_RV.setHasFixedSize(true);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                                    HomeFragment_imageslider_RV.setLayoutManager(linearLayoutManager);
                                    //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    HomeFragment_imageslider_RV.setAdapter(adapter);

                                }*/


                                for (int i = 0; i < data.length(); i++) {

                                    JSONObject c = data.getJSONObject(i);

                                    if (!cardata.equals("null")) {

                                        objcars = cars.getJSONObject(i);
                                        user_odometer = objcars.getString("odometer");
                                        editor.putString(Shared.loggedIn_user_odometer, user_id);
                                    }

                                    user_id = c.getString("userid");
                                    user_status = c.getString("status");
                                    user_name = c.getString("name");
                                    user_email = c.getString("email");
                                    user_ph = c.getString("phone");
                                    user_dp = c.getString("profile_img");
                                    user_gender = c.getString("gender");
                                    user_fcm_id = c.getString("fcm_id");
                                    //user_loyality_points = c.getString("loyality_points");
                                    user_occupation_id = c.getString("occupation_id");
                                    user_occupation_name = c.getString("occupation_name");
                                    user_category = c.getString("category");
                                    user_user_role = c.getString("user_role");
                                    user_user_role = c.getString("city");


                                    //   Toast.makeText(getContext(), badge_activity_count, Toast.LENGTH_SHORT).show();

                                    editor.putString(Shared.loggedIn_user_city, user_user_role);
                                    editor.putString(Shared.loggedIn_user_id, user_id);
                                    editor.putString(Shared.loggedIn_user_name, user_name);
                                    editor.putString(Shared.loggedIn_user_email, user_email);
                                    editor.putString(Shared.loggedIn_user_ph, user_ph);
                                    editor.putString(Shared.loggedIn_user_dp, user_dp);
                                    editor.putString(Shared.loggedIn_user_gender, user_gender);
                                    editor.putString(Shared.loggedIn_user_fcm_id, user_fcm_id);
                                    editor.putString(Shared.loggedIn_loyality_points, user_loyality_points);
                                    editor.putString(Shared.loggedIn_user_occupation_id, user_occupation_id);
                                    editor.putString(Shared.loggedIn_user_occupation_name, user_occupation_name);
                                    editor.putString(Shared.loggedIn_user_category, user_category);
                                    editor.putString(Shared.loggedIn_user_user_role, user_user_role);
                                    editor.apply();

                                }

                                //    Toast.makeText(activity, user_status, Toast.LENGTH_SHORT).show();

                                if (user_status.equals("1")) {
                                    /*ViewDialog alert = new ViewDialog();
                                    alert.showDialog(getActivity());*/
                                } else {
                                    ViewDialog alert = new ViewDialog();
                                    alert.showDialog(getActivity());
                                }

                                //   Toast.makeText(activity, user_dp, Toast.LENGTH_SHORT).show();

                                if (Integer.parseInt(badge_activity_count) == 0) {
                                    current_badge("Bronze");
                                } else if (Integer.parseInt(badge_activity_count) >= 1 && Integer.parseInt(badge_activity_count) < 10) {
                                    current_badge("Silver");
                                } else if (Integer.parseInt(badge_activity_count) >= 10 && Integer.parseInt(badge_activity_count) < 25) {
                                    current_badge("Gold");
                                } else if (Integer.parseInt(badge_activity_count) >= 25) {
                                    current_badge("Platinum");
                                }
                                setdata_to_fields(user_name, user_ph, user_loyality_points, user_odometer, user_dp);


                            } else {
                                Toast.makeText(getContext(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                            }

                        } catch (final JSONException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                  /*  Toast.makeText(getContext(),
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
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            //  Toast.makeText(getActivity(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();

                            HomeFragment_nonet_LL.setVisibility(View.VISIBLE);
                            HomeFragment_LL.setVisibility(View.GONE);

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
                            HomeFragment_nonet_LL.setVisibility(View.VISIBLE);
                            HomeFragment_LL.setVisibility(View.GONE);
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

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }

 /*   public void get_data_from_shared_preferences()
    {
        String get_name = sharedPreferences.getString(Shared.loggedIn_user_name,"0");
        String get_ph = sharedPreferences.getString(Shared.loggedIn_user_ph,"0");
        String get_loyality = sharedPreferences.getString(Shared.loggedIn_loyality_points,"0");
        String get_odometer_reading = sharedPreferences.getString(Shared.loggedIn_user_odometer,"0");
        String get_profile_picture = sharedPreferences.getString(Shared.loggedIn_user_dp,"0");

        setdata_to_fields(get_name,get_ph,get_loyality,get_odometer_reading,get_profile_picture);

    }*/


    public void get_user_car_activity(String car_id)
    {
        myListData.clear();
        progressDialog.show();
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.USER_GET_CAR_ACTIVITY_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        //     Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        progressDialog.setCanceledOnTouchOutside(false);

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");

                            if (message.contains("success"))
                            {
                                JSONArray data = jsonObj.getJSONArray("resp");
                                date_differ = jsonObj.getString("date_difference_between_last_two_activities");
                                status_average = jsonObj.getString("average_status");

//                                Toast.makeText(activity, date_differ, Toast.LENGTH_SHORT).show();


                                if (!data.equals("null"))
                                {

                                    for (int i = 0; i < data.length(); i++)
                                    {
                                        objcars_activity = data.getJSONObject(i);
                                        String previous_odometer = objcars_activity.getString("previous_odometer");
                                        String next_odometer = objcars_activity.getString("next_odometer");
                                        String date = objcars_activity.getString("date");
                                        car_number_str = objcars_activity.getString("car_number");
                                        car_current_mileage = objcars_activity.getString("car_daily_mileage");
                                        id_car              = objcars_activity.getString("car_id");
                                        String nextdate = objcars_activity.getString("next_oil_change_date");

                                        arrayList_odometer1.add(Integer.valueOf(previous_odometer));

                                       /* String odometer_1 = arrayList_odometer1.get(0).toString();

                                        String odometer_2 = arrayList_odometer1.get(1).toString();

                                        array_rep_odometer = new int[]{Integer.parseInt(odometer_1)};*/

//                                        rep_odometer = String.valueOf(array_rep_odometer[i]);

//                                        Log.d("result", String.valueOf(arrayList_odometer1));

                                        String val = "";
                                        String nextmileageval = "";
                                        if (nextdate.equals("null")) {
                                            val = "-----";
                                            nextmileageval = "-----";
                                        } else {
                                            val = nextdate;
                                            nextmileageval = next_odometer;
                                        }

                                        if (i <= data.length()) {
                                            CarDetailsList list = new CarDetailsList(date, previous_odometer, nextmileageval, val);
                                            myListData.add(list);
                                        }
                                    }

                                    CardDetailsAdapter adapter2 = new CardDetailsAdapter(myListData, getContext());
                                    HomeFragment_history_RV.setHasFixedSize(true);
                                    LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, true);
                                    HomeFragment_history_RV.setLayoutManager(linearLayoutManager2);
                                    //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    adapter2.notifyDataSetChanged();
                                    HomeFragment_history_RV.setAdapter(adapter2);
/*
                                    for(int i=0;i<data.length()-1;i++)
                                    {

                                    }
*/
                                    /*Log.d("result_sub", String.valueOf(arrayList_result_odometer));*/
                                    //num++;

                                    //spinner.setItems(user_cars_list);

//                                    length_data = data.length();

                                    if (data.length() > 1)
                                    {
                                        diff_date = Float.parseFloat(date_differ);
//                                      Toast.makeText(activity, "it works", Toast.LENGTH_SHORT).show();
                                        LayoutInflater factory = LayoutInflater.from(getContext());
                                        final View pop_view = factory.inflate(R.layout.oil_change_popup, null);
                                        final AlertDialog pop_dialog = new AlertDialog.Builder(getContext()).create();
                                        pop_dialog.setView(pop_view);

//                                        if (length_data < data.length())
//                                        {
//                                            editor.putString("shown_shared","0").apply();
//                                        }

                                        for (int i = 0; i < data.length()- 1; i++)
                                        {
                                            if (arrayList_odometer1.get(i) > arrayList_odometer1.get(i + 1))
                                            {
                                                arrayList_result_odometer.add(arrayList_odometer1.get(i) - arrayList_odometer1.get(i + 1));
                                                Log.d("result_tag", String.valueOf(arrayList_result_odometer));

                                                result_sub = Integer.parseInt(arrayList_result_odometer.get(i).toString());

                                                Log.d("tag_sub_result", String.valueOf(result_sub));
                                            }

                                            if (arrayList_odometer1.get(i + 1) > arrayList_odometer1.get(i))
                                            {
                                                arrayList_result_odometer.add(arrayList_odometer1.get(i + 1) - arrayList_odometer1.get(i));
                                                result_sub = Integer.parseInt(arrayList_result_odometer.get(i).toString());
                                            }
                                        }

                                        result_div = result_sub / diff_date;
                                        result_div_float = Float.parseFloat(numberFormat.format(result_div));
                                        result_div_str = String.valueOf(result_div_float);

                                        TextView pop_textview = pop_view.findViewById(R.id.textView_popup_oil_change);
                                        TextView pop_textview2 = pop_view.findViewById(R.id.textView2_popup_oil_change);
                                        TextView pop_textview3 = pop_view.findViewById(R.id.textView3_popup_oil_change);

//                                        Toast.makeText(activity, shown_str, Toast.LENGTH_SHORT).show();

                                                if(status_average.equals("0"))
                                                {
                                                    if (!car_current_mileage.equals(result_div_str))
                                                    {
                                                        pop_dialog.show();

                                                        pop_textview.setText(getResources().getString(R.string.thank_you_pop)  +
                                                                "\n" + car_number_str);

                                                        pop_textview2.setText(result_div_str);

                                                        pop_textview3.setText(getResources().getString(R.string.update_average_pop)+" "+ car_current_mileage);

                                                    }
                                                }

                                        pop_view.findViewById(R.id.update_btn_popup_oil_change).setOnClickListener(new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(View v)
                                            {
                                                editor.putString("shown_shared","1").apply();
                                                update_car_data(car_id,result_div_str);
                                                pop_dialog.dismiss();
                                            }
                                        });

                                        pop_view.findViewById(R.id.cancel_dialog_icon_oil_change).setOnClickListener(new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(View v)
                                            {
                                                pop_dialog.dismiss();
                                            }
                                        });

                                        pop_view.findViewById(R.id.keep_btn_popup_oil_change).setOnClickListener(new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(View v)
                                            {
                                                update_car_data(car_id,car_current_mileage);
                                                pop_dialog.dismiss();
                                            }
                                        });
                                    }
                                }


                            }

                            else
                            {
                                HomeFragment_carhist_IV.setVisibility(View.VISIBLE);
                                HomeFragment_history_RV.setVisibility(View.GONE);
                      /*          Toast.makeText(getActivity(), "No Car Activity", Toast.LENGTH_SHORT).show();

                                CardDetailsAdapter adapter2 = new CardDetailsAdapter(myListData,getActivity());
                                HomeFragment_history_RV.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
                                HomeFragment_history_RV.setLayoutManager(linearLayoutManager2);
                                //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                adapter2.notifyDataSetChanged();
                                HomeFragment_history_RV.setAdapter(adapter2);*/
                            }

//                            Toast.makeText(activity, response, Toast.LENGTH_SHORT).show();
                            Log.d("tag_response_oil_change", response);
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
                        //    Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("car_id", car_id);


                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void update_car_data(String car_id, String result_div_str)
    {
//        myListData.clear();

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, update_link,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        //     Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        try
                        {
                            JSONObject jsonObj = new JSONObject(response);
                            String status_car = jsonObj.getString("status");

                            if(status_car.equals("success"))
                            {
                                Toast.makeText(activity, "Mileage Updated Successfully", Toast.LENGTH_SHORT).show();
                            }
//                            Toast.makeText(activity, response, Toast.LENGTH_SHORT).show();
                            Log.d("update_milleage_tag", response);
//                            Toast.makeText(activity, result_div_str, Toast.LENGTH_SHORT).show();
                        }

                        catch (final JSONException e)
                        {
                            getActivity().runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                }
                            });
                        }


                        //      Toast.makeText(Signin.this, response, Toast.LENGTH_SHORT).show();


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        //   progressDialog.dismiss();
                        progressDialog.dismiss();
                        //    Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }


            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("car_id", car_id);
                parameters.put("daily_mileage", result_div_str);


                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }


    public void current_badge(String badgetype) {
        if (getActivity() != null) {


            if (badgetype.equals("Bronze")) {
                HomeFragment_badges_IV.setImageResource(R.mipmap.bronzesmall);
                HomeFragment_badges_TV.setText(getResources().getString(R.string.bronze));
            } else if (badgetype.equals("Silver")) {
                HomeFragment_badges_IV.setImageResource(R.mipmap.silversmall);
                HomeFragment_badges_TV.setText(getResources().getString(R.string.silver));
            } else if (badgetype.equals("Gold")) {
                HomeFragment_badges_IV.setImageResource(R.mipmap.goldsmall);
                HomeFragment_badges_TV.setText(getResources().getString(R.string.gold));
            } else if (badgetype.equals("Platinum")) {
                HomeFragment_badges_IV.setImageResource(R.mipmap.platinumsmall);
                HomeFragment_badges_TV.setText(getResources().getString(R.string.platinum));
            }
        }
    }


    private void createlink() {
        Log.e("main", "create link");
        String uid = sharedPreferences.getString(Shared.loggedIn_user_id, "0");

        String sharelinktext = "https://Kixxmobile.page.link/?" +
                "link=https://www.kixxoil.com/?uid=" + uid +
                "&apn=" + getActivity().getPackageName() +
                "&st=" + "https://play.google.com/store/apps/details?id=com.pixelpk.kixxmobile" +
                "&ad=" + "20" +
                "&si=" + "https://syedu1.sg-host.com/Kixx-App/Kixx-App-Icon.png";


        Task<ShortDynamicLink> dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://www.kixxoil.com/?uid=" + uid))
                //  .setLink(Uri.parse("https://www.kixxoil.com/?uid="+uid))
                .setDomainUriPrefix("https://Kixxmobile.page.link")
                .setIosParameters(
                        new DynamicLink.IosParameters.Builder("com.Kixx")
                                //    .setMinimumVersion("1.1.3")
                                .setAppStoreId("1546898433")
                                .build())
                // Open links with this app on Android
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder("com.pixelpk.kixxmobile").build())
                .setSocialMetaTagParameters(
                        new DynamicLink.SocialMetaTagParameters.Builder()
                                .setTitle("Kixx Lubricant")
                                .setDescription("Kixx - All ways with You")
                                .setImageUrl(Uri.parse("https://syedu1.sg-host.com/Kixx-App/Kixx-App-Icon.png"))
                                .build())
                // Open links with com.example.ios on iOS
                //.setIosParameters(new DynamicLink.IosParameters.Builder("com.Kixx").setAppStoreId("1546898433").build())
                .buildShortDynamicLink().addOnCompleteListener(getActivity(), new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        createlink_progress.dismiss();
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();
                            //      Toast.makeText(activity, shortLink.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("main ", " short link " + shortLink);

                            if (HomeFragment_username.getText().toString().equals("No Name")) {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_SEND);
                                intent.putExtra(intent.EXTRA_TEXT, getResources().getString(R.string.hello) + getResources().getString(R.string.yourfriendwantsyoutoavail) + "\n\n" + shortLink.toString());
                                intent.setType("text/plain");
                                getActivity().startActivity(intent);
                            } else {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_SEND);
                                intent.putExtra(intent.EXTRA_TEXT, getResources().getString(R.string.hello) + " " + HomeFragment_username.getText().toString() + " " + getResources().getString(R.string.yourfriendwantsyoutoavail) + "\n\n" + shortLink.toString());
                                intent.setType("text/plain");
                                getActivity().startActivity(intent);
                            }


                        } else {
                            // Error
                            // ...
                            Log.e("main ", " error " + task.getException());
                        }
                    }
                });
        ;

    /*    Uri dynamicLinkUri = dynamicLink.getUri();
        Log.e("main"," Long refer "+ dynamicLink.getUri());*/




       /* Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                // .setLink(dynamicLink.getUri())
                .setLongLink(dynamicLinkUri)
                .setDomainUriPrefix("https://example.page.link")
                // Set parameters
                // ...
                .buildShortDynamicLink()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        createlink_progress.dismiss();
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();
                            Log.e("main " , " short link "+ shortLink);

                            if(HomeFragment_username.getText().toString().equals("No Name"))
                            {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_SEND);
                                intent.putExtra(intent.EXTRA_TEXT,"Hello, Your Friend wants you to avail Free Liters of Oil on Kixx Lubricants App. Enjoy!\n\n" + dynamicLinkUri.toString());
                                intent.setType("text/plain");
                                getActivity().startActivity(intent);
                            }
                            else
                            {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_SEND);
                                intent.putExtra(intent.EXTRA_TEXT,"Hello, Your Friend "+ HomeFragment_username.getText().toString() + " wants you to avail Free Liters of Oil on Kixx Lubricants App. Enjoy!\n\n" + dynamicLinkUri.toString());
                                intent.setType("text/plain");
                                getActivity().startActivity(intent);
                            }




                        } else {
                            // Error
                            // ...
                            Log.e("main " , " error "+ task.getException());
                        }
                    }
                });*/

    }


    private void getPromoData() {
        imageSliderLists.clear();
        // promos.clear();
        progressDialog.show();
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.PROMOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //     Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");

                            if (message.contains("success")) {
                                JSONArray data = jsonObj.getJSONArray("response");

                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject c = data.getJSONObject(i);

                                    String banner = URLs.PROMO_IMAGE_URL + c.getString("banner");
                                    String promo_id = c.getString("id");

                                    ImageSliderList imageSliderList = new ImageSliderList(banner, promo_id);
                                    imageSliderLists.add(imageSliderList);

                                    Log.d("Error", banner);
                                }

                                ArrayList<ImageSliderList> list = imageSliderLists;
                                Collections.reverse(list);

                                ImageSlidingAdapter adapter = new ImageSlidingAdapter(imageSliderLists, getContext(), "promos", "user");
                                HomeFragment_imageslider_RV.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                                HomeFragment_imageslider_RV.setLayoutManager(linearLayoutManager);
                                //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                HomeFragment_imageslider_RV.setAdapter(adapter);

                                progressDialog.dismiss();

                            } else {
                                Toast.makeText(getActivity(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
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
                    //           Toast.makeText(getActivity()   , error.toString(), Toast.LENGTH_LONG).show();
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
        adslist.clear();
        //   promos.clear();
        progressDialog.show();
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.PROMOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");

                            //       Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                            if (message.contains("success")) {
                                JSONArray data = jsonObj.getJSONArray("ads");

                                for (int i = 0; i < data.length(); i++) {

                                    JSONObject c = data.getJSONObject(i);

                                    String banner = "https://syedu1.sg-host.com/Kixx-App/Secure-Portal//assets/images/advertisement-banners/" + c.getString("banner");
                                    String promo_id = c.getString("id");

                                    ImageSliderList imageSliderList = new ImageSliderList(banner, promo_id);
                                    adslist.add(imageSliderList);

                                    //   Toast.makeText(getContext(), banner , Toast.LENGTH_SHORT).show();
                                    Log.d("Error", banner);
                                }

                                ArrayList<ImageSliderList> list = adslist;
                                Collections.reverse(list);

                                ImageSlidingAdapter adapter = new ImageSlidingAdapter(adslist, getContext(), "ads", "user");
                                HomeFragment_ads_RV.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                                HomeFragment_ads_RV.setLayoutManager(linearLayoutManager);


                                HomeFragment_ads_RV.setAdapter(adapter);


                                HomeFragment_ads_RV.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                    @Override
                                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                        super.onScrolled(recyclerView, dx, dy);
                                        scrollY = HomeFragment_LL.getScrollY();
                                        int lastItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                                        if (lastItem == linearLayoutManager.getItemCount() - 1) {
                                            mHandler.removeCallbacks(SCROLLING_RUNNABLE);
                                            Handler postHandler = new Handler();
                                            postHandler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
//                                                    HomeFragment_ads_RV.setAdapter(null);
                                                    HomeFragment_ads_RV.setAdapter(adapter);
                                                    mHandler.postDelayed(SCROLLING_RUNNABLE, 5000);
                                                }
                                            }, 5000);
                                        }
                                    }
                                });
                                mHandler.postDelayed(SCROLLING_RUNNABLE, 5000);


                                //      adapter.notifyDataSetChanged();
                                progressDialog.dismiss();

                            } else {
                                Toast.makeText(getActivity(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
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
                error ->
                {
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

    public class ViewDialog {

        public void showDialog(Activity activity) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.popup);

            Button pop_ok = (Button) dialog.findViewById(R.id.HomeFragment_popup_ok);
            //     text.setText(msg);

            //   Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
            pop_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }
    }

    public void replaceFragments() {

        Fragment newFragment = new MapsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();

    }

    public void Mileage_dialogbox(String car_id, String status, String car_number, int index)
    {
        LayoutInflater factory = LayoutInflater.from(getContext());
        final View milage_dialog = factory.inflate(R.layout.milage_dialog, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(getContext()).create();
        deleteDialog.setTitle("Average Daily Car Travel ( " + car_number + " )");
        deleteDialog.setCanceledOnTouchOutside(false);
        deleteDialog.setView(milage_dialog);

        EditText mileage = milage_dialog.findViewById(R.id.HomeScreen_dialog_carmileage_ET);

        milage_dialog.findViewById(R.id.HomeScreen_dialog_update_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String val = mileage.getEditableText().toString();
                //     Toast.makeText(getContext(),  car_id+" "+status+" "+val, Toast.LENGTH_SHORT).show();
                Log.d("calculation", car_id + " " + status + " " + val);
                updatemileagestatus("1", car_id, val, "1");

                deleteDialog.dismiss();

            }
        });

        milage_dialog.findViewById(R.id.HomeScreen_dialog_cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                updatemileagestatus("1", car_id, shared_daily_mileage_str, "2");
                deleteDialog.dismiss();
            }
        });

        deleteDialog.show();
    }


    private void updatemileagestatus(String status, String car_id, String mileage, String val) {

        //  Toast.makeText(activity, status+" "+car_id+" "+mileage, Toast.LENGTH_SHORT).show();

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, update_car_mileage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        if (val == "1") {
                            Toast.makeText(activity, "Daily mileage updated successfully", Toast.LENGTH_SHORT).show();

                        }
                        index++;
                        if (index <= user_car_status.size() - 1) {

                            Mileage_dialogbox(user_car_status.get(index).getCar_id(), user_car_status.get(index).getStatus(), user_car_status.get(index).getCar_number(), index);
                        }

                    }
                },
                error ->
                {
                    progressDialog.dismiss();
                    Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show();


                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("user_car_id", car_id);
                parameters.put("daily_mileage", mileage);
                parameters.put("car_status", status);

                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }
}