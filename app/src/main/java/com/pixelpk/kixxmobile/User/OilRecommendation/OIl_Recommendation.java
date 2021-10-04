package com.pixelpk.kixxmobile.User.OilRecommendation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.AddCar.AddCarScreen;
import com.pixelpk.kixxmobile.User.AddCarInfoScreen;
import com.pixelpk.kixxmobile.User.EditCarInfo;
import com.pixelpk.kixxmobile.User.ModelClasses.AddCarList;
import com.pixelpk.kixxmobile.User.ModelClasses.CarRecommendationlistModelClass;
import com.pixelpk.kixxmobile.User.ModelClasses.ImageSliderList;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
import com.pixelpk.kixxmobile.User.UpdateUserProfile;
import com.pixelpk.kixxmobile.User.adapters.AddCarAdapter;
import com.pixelpk.kixxmobile.User.adapters.ImageSlidingAdapter;
import com.pixelpk.kixxmobile.User.adapters.OilRecommendationAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class OIl_Recommendation extends AppCompatActivity {

    LinearLayout OilRecommendation_back,Oil_recommendation_select_car_LL;
    Button Oil_recommendation_btn;

    String engine_type = "Gasoline";
   // String engine_type = "Diesel";
    int car_model = 2008;
    String token;
    RecyclerView OilRecommendation_5000km,OilRecommendation_10000km,OilRecommendation_15000km;
    ArrayList<CarRecommendationlistModelClass> arrayList_5000km,arrayList_10000km,arrayList_15000km;
    String userid;
    SpinnerDialog spinnerDialog_select_car;
  //  Spinner spinner;

    LinearLayout layout1,layout2,layout3;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    ProgressDialog progressDialog;

    ArrayList<AddCarList> myListData;
    ArrayList<String> myListData_str;
    ArrayList<String> car_list;

    LinearLayout rtl_back_eng,rtl_back_arabic;

    String rtl;

    LinearLayout Oil_Recommendation_mainframe,Oil_recommendation_yom_not_added,Oil_recommendation_no_Car_added;
    AddCarList promosList;

    TextView Oil_recommendation_select_car_TV;

    Button Oil_recommendation_add_car_btn,Oil_recommendation_Update_btn,oil_recommendation_Continue_btn;

    String getCar_Number="",getCar_id="",getCar_Manufacturer="",getCar_Brand="",getCar_Model="",getYear_of_manufacture="",getOdometer="",getDaily_mileage="",getEngine_type="",getCarid="";

    //Handle Button Clicks
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_il__recommendation);

        initializeViews();

        if(rtl.equals("1"))
        {
            rtl_back_eng.setVisibility(View.GONE);
            rtl_back_arabic.setVisibility(View.VISIBLE);
        }
        else
        {
            rtl_back_arabic.setVisibility(View.GONE);
            rtl_back_eng.setVisibility(View.VISIBLE);
        }


        Oil_recommendation_select_car_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                spinnerDialog_select_car.showSpinerDialog();
            }
        });

        Oil_recommendation_add_car_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                Intent intent = new Intent(OIl_Recommendation.this, AddCarInfoScreen.class);
                intent.putExtra("oilchange","1");
                startActivity(intent);
                finish();

            }
        });



        Oil_recommendation_Update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                Intent intent = new Intent(OIl_Recommendation.this, EditCarInfo.class);
                intent.putExtra("Carnumber",getCar_Number);
                intent.putExtra("CarId",getCar_id);
                intent.putExtra("Carmanufacturer",getCar_Manufacturer);
                intent.putExtra("Carbrand",getCar_Brand);
                intent.putExtra("CarModel",getCar_Model);
                intent.putExtra("year_of_manufacture",getYear_of_manufacture);
                intent.putExtra("odometer",getOdometer);
                intent.putExtra("enginetype",getEngine_type);
                intent.putExtra("dailymileage",getDaily_mileage);
                intent.putExtra("cid",getCarid);
                intent.putExtra("intent_val","2");
                startActivity(intent);
                finish();
            }
        });

        spinnerDialog_select_car.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position)
            {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                Oil_recommendation_select_car_TV.setText(item);
                //city_str = item;

                if(myListData.isEmpty())
                {
                    Oil_Recommendation_mainframe.setVisibility(View.GONE);
                    Oil_recommendation_no_Car_added.setVisibility(View.VISIBLE);
                    Oil_recommendation_yom_not_added.setVisibility(View.GONE);
                }
                else {

                    if (!Oil_recommendation_select_car_TV.getText().toString().equals(getResources().getString(R.string.selectyourcar))) {

                        String yom = myListData.get(position).getYear_of_manufacture();
                        String et = myListData.get(position).getEngine_type();

                   /*     Toast.makeText(OIl_Recommendation.this, yom, Toast.LENGTH_SHORT).show();
                        Toast.makeText(OIl_Recommendation.this, et, Toast.LENGTH_SHORT).show();*/

                        if ((!yom.equals("0")))
                        {

                            if(!et.equals("0"))
                            {
                                //        Toast.makeText(OIl_Recommendation.this, myListData.get(position).getYear_of_manufacture(), Toast.LENGTH_SHORT).show();
                                Oil_Recommendation_mainframe.setVisibility(View.VISIBLE);
                                Oil_recommendation_no_Car_added.setVisibility(View.GONE);
                                Oil_recommendation_yom_not_added.setVisibility(View.GONE);
                                Log.d("datastringnull", myListData.get(position).getYear_of_manufacture());
                                //   Toast.makeText(OIl_Recommendation.this, "Hello world!", Toast.LENGTH_SHORT).show();
                                try {

                                    int Y_O_M = Integer.parseInt(myListData.get(position).getYear_of_manufacture());
                                    String Engine = myListData.get(position).getEngine_type();
                                    if (Engine.equals("1")) {
                                        Engine = "Gasoline";
                                    } else if (Engine.equals("2")) {

                                        Engine = "Diesel";
                                    }

                                    ShowOilRecommendations(Engine, Y_O_M);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Oil_Recommendation_mainframe.setVisibility(View.GONE);
                                    Oil_recommendation_no_Car_added.setVisibility(View.GONE);
                                    Oil_recommendation_yom_not_added.setVisibility(View.VISIBLE);

                                    getCar_Number = myListData.get(position).getCar_Number();
                                    getCar_id = myListData.get(position).getCar_id();
                                    getCar_Manufacturer = myListData.get(position).getCar_Manufacturer();
                                    getCar_Brand = myListData.get(position).getCar_Brand();
                                    getCar_Model = myListData.get(position).getCar_Model();
                                    getYear_of_manufacture = myListData.get(position).getYear_of_manufacture();
                                    getOdometer = myListData.get(position).getOdometer();
                                    getDaily_mileage = myListData.get(position).getDaily_mileage();
                                    getEngine_type = myListData.get(position).getEngine_type();
                                    getCarid = myListData.get(position).getCid();

                                /*Toast.makeText(OIl_Recommendation.this, getCar_Number, Toast.LENGTH_SHORT).show();
                                Toast.makeText(OIl_Recommendation.this, getCar_id, Toast.LENGTH_SHORT).show();
                                Toast.makeText(OIl_Recommendation.this, getCar_Manufacturer, Toast.LENGTH_SHORT).show();
                                Toast.makeText(OIl_Recommendation.this, getCar_Brand, Toast.LENGTH_SHORT).show();
                                Toast.makeText(OIl_Recommendation.this, getCar_Model, Toast.LENGTH_SHORT).show();
                                Toast.makeText(OIl_Recommendation.this, getYear_of_manufacture, Toast.LENGTH_SHORT).show();
                                Toast.makeText(OIl_Recommendation.this, getOdometer, Toast.LENGTH_SHORT).show();
                                Toast.makeText(OIl_Recommendation.this, getDaily_mileage, Toast.LENGTH_SHORT).show();
                                Toast.makeText(OIl_Recommendation.this, getEngine_type, Toast.LENGTH_SHORT).show();
                                Toast.makeText(OIl_Recommendation.this, getCarid, Toast.LENGTH_SHORT).show();*/
                                }

                            }
                            else
                            {
                                Oil_Recommendation_mainframe.setVisibility(View.GONE);
                                Oil_recommendation_no_Car_added.setVisibility(View.GONE);
                                Oil_recommendation_yom_not_added.setVisibility(View.VISIBLE);


                                getCar_Number= myListData.get(position).getCar_Number();
                                getCar_id= myListData.get(position).getCar_id();
                                getCar_Manufacturer= myListData.get(position).getCar_Manufacturer();
                                getCar_Brand= myListData.get(position).getCar_Brand();
                                getCar_Model= myListData.get(position).getCar_Model();
                                getYear_of_manufacture= myListData.get(position).getYear_of_manufacture();
                                getOdometer= myListData.get(position).getOdometer();
                                getDaily_mileage= myListData.get(position).getDaily_mileage();
                                getEngine_type= myListData.get(position).getEngine_type();
                                getCarid= myListData.get(position).getCid();
                            }
                        }
                        else
                        {
                            Oil_Recommendation_mainframe.setVisibility(View.GONE);
                            Oil_recommendation_no_Car_added.setVisibility(View.GONE);
                            Oil_recommendation_yom_not_added.setVisibility(View.VISIBLE);


                            getCar_Number= myListData.get(position).getCar_Number();
                            getCar_id= myListData.get(position).getCar_id();
                            getCar_Manufacturer= myListData.get(position).getCar_Manufacturer();
                            getCar_Brand= myListData.get(position).getCar_Brand();
                            getCar_Model= myListData.get(position).getCar_Model();
                            getYear_of_manufacture= myListData.get(position).getYear_of_manufacture();
                            getOdometer= myListData.get(position).getOdometer();
                            getDaily_mileage= myListData.get(position).getDaily_mileage();
                            getEngine_type= myListData.get(position).getEngine_type();
                            getCarid= myListData.get(position).getCid();

                          /*  Toast.makeText(OIl_Recommendation.this, getCar_Number, Toast.LENGTH_SHORT).show();
                            Toast.makeText(OIl_Recommendation.this, getCar_id, Toast.LENGTH_SHORT).show();
                            Toast.makeText(OIl_Recommendation.this, getCar_Manufacturer, Toast.LENGTH_SHORT).show();
                            Toast.makeText(OIl_Recommendation.this, getCar_Brand, Toast.LENGTH_SHORT).show();
                            Toast.makeText(OIl_Recommendation.this, getCar_Model, Toast.LENGTH_SHORT).show();
                            Toast.makeText(OIl_Recommendation.this, getYear_of_manufacture, Toast.LENGTH_SHORT).show();
                            Toast.makeText(OIl_Recommendation.this, getOdometer, Toast.LENGTH_SHORT).show();
                            Toast.makeText(OIl_Recommendation.this, getDaily_mileage, Toast.LENGTH_SHORT).show();
                            Toast.makeText(OIl_Recommendation.this, getEngine_type, Toast.LENGTH_SHORT).show();
                            Toast.makeText(OIl_Recommendation.this, getCarid, Toast.LENGTH_SHORT).show();*/




                        }

                    }
                    else
                    {
                        Oil_Recommendation_mainframe.setVisibility(View.GONE);
                        Oil_recommendation_no_Car_added.setVisibility(View.GONE);
                        Oil_recommendation_yom_not_added.setVisibility(View.GONE);
                    }

                }
                //           Toast.makeText(UpdateUserProfile.this, city_str, Toast.LENGTH_SHORT).show();
            }
        });

//        OilRecommendation_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        Oil_recommendation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                Intent intent = new Intent(OIl_Recommendation.this,Howtochooseoil.class);
                startActivity(intent);


            }
        });


        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(myListData.isEmpty())
                {
                    Oil_Recommendation_mainframe.setVisibility(View.GONE);
                    Oil_recommendation_no_Car_added.setVisibility(View.VISIBLE);
                    Oil_recommendation_yom_not_added.setVisibility(View.GONE);
                }
                else {

                    Oil_Recommendation_mainframe.setVisibility(View.VISIBLE);
                    Oil_recommendation_no_Car_added.setVisibility(View.GONE);
                    Oil_recommendation_yom_not_added.setVisibility(View.GONE);

                    if (myListData.get(position).getYear_of_manufacture() != null || !myListData.get(position).getYear_of_manufacture().equals("null")) {

                        int Y_O_M = Integer.parseInt(myListData.get(position).getYear_of_manufacture());
                        String Engine = myListData.get(position).getEngine_type();
                        if (Engine.equals("1")) {
                            Engine = "Gasoline";
                        } else if (Engine.equals("2")) {

                            Engine = "Diesel";
                        }
                        ShowOilRecommendations(Engine, Y_O_M);
                    }
                    else
                    {
                        Oil_Recommendation_mainframe.setVisibility(View.GONE);
                        Oil_recommendation_no_Car_added.setVisibility(View.GONE);
                        Oil_recommendation_yom_not_added.setVisibility(View.VISIBLE);
                    }
                }
//                Toast.makeText(OIl_Recommendation.this, String.valueOf(Y_O_M), Toast.LENGTH_SHORT).show();
            //    Toast.makeText(OIl_Recommendation.this, Engine, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

    }

    private void ShowOilRecommendations(String enginetype,int model) {

        arrayList_5000km.clear();
        arrayList_10000km.clear();
        arrayList_15000km.clear();

        if(enginetype.equals("Gasoline") && model>=2008)
        {
            OilRecommendation_10000km.setVisibility(View.VISIBLE);
            OilRecommendation_15000km.setVisibility(View.VISIBLE);

            CarRecommendationlistModelClass carRecommendationlistModelClass = new CarRecommendationlistModelClass(R.drawable.can17,"Kixx G1 10W-30","5000 KM","1");
            CarRecommendationlistModelClass carRecommendationlistModelClass2 = new CarRecommendationlistModelClass(R.drawable.can18,"Kixx G1 10W-40","5000 KM","2");
            CarRecommendationlistModelClass carRecommendationlistModelClass3 = new CarRecommendationlistModelClass(R.drawable.can20,"Kixx G1 20W-50","5000 KM","3");

            arrayList_5000km.add(carRecommendationlistModelClass);
            arrayList_5000km.add(carRecommendationlistModelClass2);
            arrayList_5000km.add(carRecommendationlistModelClass3);

            OilRecommendationAdapter oilRecommendationAdapter =  new OilRecommendationAdapter(arrayList_5000km,OIl_Recommendation.this);
            OilRecommendation_5000km.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(OIl_Recommendation.this);
            OilRecommendation_5000km.setLayoutManager(linearLayoutManager2);
            //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            oilRecommendationAdapter.notifyDataSetChanged();
            OilRecommendation_5000km.setAdapter(oilRecommendationAdapter);

            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.VISIBLE);


        }
        else if(enginetype.equals("Gasoline") && (model>=2000 && model<=2007))
        {
            OilRecommendation_10000km.setVisibility(View.VISIBLE);
            OilRecommendation_15000km.setVisibility(View.VISIBLE);

            CarRecommendationlistModelClass carRecommendationlistModelClass = new CarRecommendationlistModelClass(R.drawable.can17,"Kixx G1 10W-30","5000 KM","4");
            CarRecommendationlistModelClass carRecommendationlistModelClass3 = new CarRecommendationlistModelClass(R.drawable.can20,"Kixx G1 20W-50","5000 KM","5");

            arrayList_5000km.add(carRecommendationlistModelClass);
            arrayList_5000km.add(carRecommendationlistModelClass3);

            OilRecommendationAdapter oilRecommendationAdapter =  new OilRecommendationAdapter(arrayList_5000km,OIl_Recommendation.this);
            OilRecommendation_5000km.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(OIl_Recommendation.this);
            OilRecommendation_5000km.setLayoutManager(linearLayoutManager2);
            //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            oilRecommendationAdapter.notifyDataSetChanged();
            OilRecommendation_5000km.setAdapter(oilRecommendationAdapter);

            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.VISIBLE);
        }
        else if(enginetype.equals("Gasoline") && (model<=1999))
        {
            OilRecommendation_10000km.setVisibility(View.VISIBLE);
            OilRecommendation_15000km.setVisibility(View.VISIBLE);

            CarRecommendationlistModelClass carRecommendationlistModelClass = new CarRecommendationlistModelClass(R.drawable.can17,"Kixx G1 10W-30","5000 KM","6");
            CarRecommendationlistModelClass carRecommendationlistModelClass3 = new CarRecommendationlistModelClass(R.drawable.can20,"Kixx G1 20W-50","5000 KM","7");

            arrayList_5000km.add(carRecommendationlistModelClass);
            arrayList_5000km.add(carRecommendationlistModelClass3);

            OilRecommendationAdapter oilRecommendationAdapter =  new OilRecommendationAdapter(arrayList_5000km,OIl_Recommendation.this);
            OilRecommendation_5000km.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(OIl_Recommendation.this);
            OilRecommendation_5000km.setLayoutManager(linearLayoutManager2);
            //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            oilRecommendationAdapter.notifyDataSetChanged();
            OilRecommendation_5000km.setAdapter(oilRecommendationAdapter);

            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.VISIBLE);
        }

        if(enginetype.equals("Gasoline") && model>=2008)
        {
            OilRecommendation_10000km.setVisibility(View.VISIBLE);
            OilRecommendation_15000km.setVisibility(View.VISIBLE);

            CarRecommendationlistModelClass carRecommendationlistModelClass = new CarRecommendationlistModelClass(R.drawable.can15,"Kixx G1 Dexos1 0W-20","10000 KM","8");
            CarRecommendationlistModelClass carRecommendationlistModelClass2 = new CarRecommendationlistModelClass(R.drawable.can4,"Kixx G1 5W-20","10000 KM","9");
            CarRecommendationlistModelClass carRecommendationlistModelClass3 = new CarRecommendationlistModelClass(R.drawable.can5,"Kixx G1 5W-30","10000 KM","10");
            CarRecommendationlistModelClass carRecommendationlistModelClass4 = new CarRecommendationlistModelClass(R.drawable.can19,"Kixx G1 Dexos1 5W-30","10000 KM","11");
            CarRecommendationlistModelClass carRecommendationlistModelClass5 = new CarRecommendationlistModelClass(R.drawable.can_6,"Kixx G1 5W-40","10000 KM","12");

            arrayList_10000km.add(carRecommendationlistModelClass);
            arrayList_10000km.add(carRecommendationlistModelClass2);
            arrayList_10000km.add(carRecommendationlistModelClass3);
            arrayList_10000km.add(carRecommendationlistModelClass4);
            arrayList_10000km.add(carRecommendationlistModelClass5);

            OilRecommendationAdapter oilRecommendationAdapter2 =  new OilRecommendationAdapter(arrayList_10000km,OIl_Recommendation.this);
            OilRecommendation_10000km.setHasFixedSize(true);
            OilRecommendation_10000km.getLayoutParams().height = 500;
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(OIl_Recommendation.this);
            OilRecommendation_10000km.setLayoutManager(linearLayoutManager2);
            //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            oilRecommendationAdapter2.notifyDataSetChanged();
            OilRecommendation_10000km.setAdapter(oilRecommendationAdapter2);

            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.VISIBLE);

        }
        else if(enginetype.equals("Gasoline") && (model>=2000 && model<=2007))
        {
            OilRecommendation_10000km.setVisibility(View.VISIBLE);
            OilRecommendation_15000km.setVisibility(View.VISIBLE);
            CarRecommendationlistModelClass carRecommendationlistModelClass = new CarRecommendationlistModelClass(R.drawable.can15,"Kixx G1 Dexos1 0W-20","10000 KM","13");
         //   CarRecommendationlistModelClass carRecommendationlistModelClass2 = new CarRecommendationlistModelClass(R.drawable.can_14,"Kixx G1 5W-20","10000 KM");
            CarRecommendationlistModelClass carRecommendationlistModelClass3 = new CarRecommendationlistModelClass(R.drawable.can5,"Kixx G1 5W-30","10000 KM","14");
            CarRecommendationlistModelClass carRecommendationlistModelClass4 = new CarRecommendationlistModelClass(R.drawable.can19,"Kixx G1 Dexos1 5W-30","10000 KM","15");
      //      CarRecommendationlistModelClass carRecommendationlistModelClass5 = new CarRecommendationlistModelClass(R.drawable.can_14,"Kixx G1 5W-40","10000 KM");

            arrayList_10000km.add(carRecommendationlistModelClass);
         //   arrayList_10000km.add(carRecommendationlistModelClass2);
            arrayList_10000km.add(carRecommendationlistModelClass3);
            arrayList_10000km.add(carRecommendationlistModelClass4);
         //   arrayList_10000km.add(carRecommendationlistModelClass5);

            OilRecommendationAdapter oilRecommendationAdapter2 =  new OilRecommendationAdapter(arrayList_10000km,OIl_Recommendation.this);
            OilRecommendation_10000km.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(OIl_Recommendation.this);
            OilRecommendation_10000km.setLayoutManager(linearLayoutManager2);
            OilRecommendation_10000km.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            oilRecommendationAdapter2.notifyDataSetChanged();
            OilRecommendation_10000km.setAdapter(oilRecommendationAdapter2);

            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.VISIBLE);
        }
        else if(enginetype.equals("Gasoline") && (model<=1999))
        {
            OilRecommendation_10000km.setVisibility(View.VISIBLE);
            OilRecommendation_15000km.setVisibility(View.VISIBLE);
        //    CarRecommendationlistModelClass carRecommendationlistModelClass = new CarRecommendationlistModelClass(R.drawable.can_14,"Kixx G1 Dexos1 0W-20","10000 KM");
            //   CarRecommendationlistModelClass carRecommendationlistModelClass2 = new CarRecommendationlistModelClass(R.drawable.can_14,"Kixx G1 5W-20","10000 KM");
            CarRecommendationlistModelClass carRecommendationlistModelClass3 = new CarRecommendationlistModelClass(R.drawable.can5,"Kixx G1 5W-30","10000 KM","16");
       //     CarRecommendationlistModelClass carRecommendationlistModelClass4 = new CarRecommendationlistModelClass(R.drawable.can_14,"Kixx G1 Dexos1 5W-30","10000 KM");
            //      CarRecommendationlistModelClass carRecommendationlistModelClass5 = new CarRecommendationlistModelClass(R.drawable.can_14,"Kixx G1 5W-40","10000 KM");

         //   arrayList_10000km.add(carRecommendationlistModelClass);
            //   arrayList_10000km.add(carRecommendationlistModelClass2);
            arrayList_10000km.add(carRecommendationlistModelClass3);
          //  arrayList_10000km.add(carRecommendationlistModelClass4);
            //   arrayList_10000km.add(carRecommendationlistModelClass5);

            OilRecommendationAdapter oilRecommendationAdapter2 =  new OilRecommendationAdapter(arrayList_10000km,OIl_Recommendation.this);
            OilRecommendation_10000km.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(OIl_Recommendation.this);
            OilRecommendation_10000km.setLayoutManager(linearLayoutManager2);
            OilRecommendation_10000km.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            oilRecommendationAdapter2.notifyDataSetChanged();
            OilRecommendation_10000km.setAdapter(oilRecommendationAdapter2);

            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.VISIBLE);
        }



        if(enginetype.equals("Gasoline"))
        {

            OilRecommendation_10000km.setVisibility(View.VISIBLE);
            OilRecommendation_15000km.setVisibility(View.VISIBLE);
            CarRecommendationlistModelClass carRecommendationlistModelClass = new CarRecommendationlistModelClass(R.drawable.can16,"Kixx PAO1 0W-40","15000 KM","17");
           /* CarRecommendationlistModelClass carRecommendationlistModelClass2 = new CarRecommendationlistModelClass(R.drawable.can_14,"Kixx G1 10W-40","5000 KM");
            CarRecommendationlistModelClass carRecommendationlistModelClass3 = new CarRecommendationlistModelClass(R.drawable.can_14,"Kixx G1 20W-50","5000 KM");
*/
            arrayList_15000km.add(carRecommendationlistModelClass);
       /*     arrayList_5000km.add(carRecommendationlistModelClass2);
            arrayList_5000km.add(carRecommendationlistModelClass3);
*/
            OilRecommendationAdapter oilRecommendationAdapter3 =  new OilRecommendationAdapter(arrayList_15000km,OIl_Recommendation.this);
            OilRecommendation_15000km.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(OIl_Recommendation.this);
            OilRecommendation_15000km.setLayoutManager(linearLayoutManager2);
            //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            oilRecommendationAdapter3.notifyDataSetChanged();
            OilRecommendation_15000km.setAdapter(oilRecommendationAdapter3);

            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.VISIBLE);


        }

        if(enginetype.equals("Diesel"))
        {

            CarRecommendationlistModelClass carRecommendationlistModelClass = new CarRecommendationlistModelClass(R.drawable.can10,"Kixx HD1 Cl-4/E7 15W-40","5000 KM","18");
           /* CarRecommendationlistModelClass carRecommendationlistModelClass2 = new CarRecommendationlistModelClass(R.drawable.can_14,"Kixx G1 10W-40","5000 KM");
            CarRecommendationlistModelClass carRecommendationlistModelClass3 = new CarRecommendationlistModelClass(R.drawable.can_14,"Kixx G1 20W-50","5000 KM");
*/
            arrayList_5000km.add(carRecommendationlistModelClass);
       /*     arrayList_5000km.add(carRecommendationlistModelClass2);
            arrayList_5000km.add(carRecommendationlistModelClass3);
*/
            OilRecommendationAdapter oilRecommendationAdapter3 =  new OilRecommendationAdapter(arrayList_5000km,OIl_Recommendation.this);
            OilRecommendation_5000km.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(OIl_Recommendation.this);
            OilRecommendation_5000km.setLayoutManager(linearLayoutManager2);
            //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            oilRecommendationAdapter3.notifyDataSetChanged();
            OilRecommendation_5000km.setAdapter(oilRecommendationAdapter3);

            OilRecommendation_10000km.setVisibility(View.GONE);
            OilRecommendation_15000km.setVisibility(View.GONE);


            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.GONE);

        }
        /*else if(enginetype.equals("Diesel") && (model>=2000 && model<=2007))
        {
            CarRecommendationlistModelClass carRecommendationlistModelClass = new CarRecommendationlistModelClass(R.drawable.can_14,"Kixx HD1 Cl-4/E7 15W-40","15000 KM");
           *//* CarRecommendationlistModelClass carRecommendationlistModelClass2 = new CarRecommendationlistModelClass(R.drawable.can_14,"Kixx G1 10W-40","5000 KM");
            CarRecommendationlistModelClass carRecommendationlistModelClass3 = new CarRecommendationlistModelClass(R.drawable.can_14,"Kixx G1 20W-50","5000 KM");
*//*
            arrayList_15000km.add(carRecommendationlistModelClass);
       *//*     arrayList_5000km.add(carRecommendationlistModelClass2);
            arrayList_5000km.add(carRecommendationlistModelClass3);
*//*
            OilRecommendationAdapter oilRecommendationAdapter3 =  new OilRecommendationAdapter(arrayList_15000km,OIl_Recommendation.this);
            OilRecommendation_15000km.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(OIl_Recommendation.this);
            OilRecommendation_15000km.setLayoutManager(linearLayoutManager2);
            //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            oilRecommendationAdapter3.notifyDataSetChanged();
            OilRecommendation_15000km.setAdapter(oilRecommendationAdapter3);
        }*/


    }

    public void initializeViews()
    {
        OilRecommendation_back = findViewById(R.id.OilRecommendation_back);
        Oil_recommendation_btn = findViewById(R.id.Oil_recommendation_btn);
        Oil_recommendation_Update_btn = findViewById(R.id.Oil_recommendation_Update_btn);
        Oil_recommendation_add_car_btn = findViewById(R.id.Oil_recommendation_add_car_btn);

        rtl_back_eng = findViewById(R.id.OilRecommendation_back_eng);
        rtl_back_arabic = findViewById(R.id.OilRecommendation_back_arabic);

        OilRecommendation_5000km = findViewById(R.id.OilRecommendation_5000km);
        OilRecommendation_10000km = findViewById(R.id.OilRecommendation_10000km);
        OilRecommendation_15000km = findViewById(R.id.OilRecommendation_15000km);
        Oil_recommendation_select_car_LL = findViewById(R.id.Oil_recommendation_select_car_LL);
       // OilRecommendation_10000km.setNestedScrollingEnabled(false);

        arrayList_5000km = new ArrayList<>();
        arrayList_10000km = new ArrayList<>();
        arrayList_15000km = new ArrayList<>();
        myListData_str = new ArrayList<>();
        car_list = new ArrayList<>();

        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        userid = sharedPreferences.getString(Shared.loggedIn_user_id,"0");

        rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

        myListData = new ArrayList<>();

        token = sharedPreferences.getString(Shared.loggedIn_jwt,"0");
      //  spinner = findViewById(R.id.Updateuserprofile_occupation_SP);
        progressDialog = new ProgressDialog(OIl_Recommendation.this);
        Oil_Recommendation_mainframe = findViewById(R.id.Oil_Recommendation_mainframe);
        Oil_recommendation_yom_not_added = findViewById(R.id.Oil_recommendation_yom_not_added);
        Oil_recommendation_no_Car_added = findViewById(R.id.Oil_recommendation_no_Car_added);
        Oil_recommendation_select_car_TV = findViewById(R.id.Oil_recommendation_select_car_TV);

        spinnerDialog_select_car = new SpinnerDialog(OIl_Recommendation.this, car_list,"Select or Search Car",R.style.DialogAnimations_SmileWindow,getResources().getString(R.string.cancel));// With 	Animation

        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);

        getCarsData();

    }



    private void getCarsData() {

        myListData.clear();
//        progressDialog.setMessage("Please Wait! while we are loading the car data");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GET_CARS_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    //    Toast.makeText(OIl_Recommendation.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");

                       //     Toast.makeText(OIl_Recommendation.this, response, Toast.LENGTH_SHORT).show();
                            if(message.contains("success"))
                            {

                                JSONArray manufacturer  = jsonObj.getJSONArray("resp");

                                for (int i = 0; i < manufacturer.length(); i++) {

                                    JSONObject m = manufacturer.getJSONObject(i);

                                    String car_id = m.getString("id");
                                    String car_name = m.getString("car_number");
                                    String name = m.getString("name");
                                    String model = m.getString("model");
                                    String company = m.getString("company");
                                    String odometer = m.getString("odometer");
                                    String daily_mileage = m.getString("daily_mileage");
                                    String year_of_manufacture = m.getString("year_of_manufacture");
                                    String engine_type = m.getString("engine_type");
                                    String cid = m.getString("car_id");

                                    //Toast.makeText(AddCarScreen.this, car_name +" "+ company + " "+ name + " " + model, Toast.LENGTH_SHORT).show();

                                    //  new AddCarList("ABC 876","Jaguar","XF","2015")

                                    /*if(engine_type.equals("null"))
                                    {
                                        engine_type = "3";
                                    }
                                    if(year_of_manufacture.equals("null"))
                                    {
                                        year_of_manufacture = "1";
                                    }*/
                                    promosList = new AddCarList(car_name,company,name,model,car_id,odometer,daily_mileage,year_of_manufacture,engine_type,cid);
                                    myListData.add(promosList);
                                    myListData_str.add(name+" "+car_name);
                                    car_list.add(name+" "+car_name);

                                }

                            /*    //ShowOilRecommendations(engine_type,car_model);
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(spinner.getContext(),
                                        R.layout.spinner_textview_layout_with_icon_white,R.id.spinner_dropdown_tv_icon,myListData_str);
                                //   adapter.setDropDownViewResource(R.layout.spinner_textview_layout_with_icon);

                                spinner.setAdapter(adapter);*/

                                if(car_list.isEmpty())
                                {
                                    Oil_Recommendation_mainframe.setVisibility(View.GONE);
                                    Oil_recommendation_no_Car_added.setVisibility(View.VISIBLE);
                                    Oil_recommendation_yom_not_added.setVisibility(View.GONE);
                                }
                                else {


                                    if (!Oil_recommendation_select_car_TV.getText().toString().equals(getResources().getString(R.string.selectyourcar))) {

                                        if (!promosList.getYear_of_manufacture().equals("null") || !promosList.getEngine_type().equals("null")) {

                                            Oil_Recommendation_mainframe.setVisibility(View.GONE);
                                            Oil_recommendation_no_Car_added.setVisibility(View.GONE);
                                            Oil_recommendation_yom_not_added.setVisibility(View.GONE);
                                            //        Toast.makeText(OIl_Recommendation.this, promosList.getYear_of_manufacture(), Toast.LENGTH_SHORT).show();
                                            //  if(!promosList.getYear_of_manufacture().equals(null)) {

                                            Log.d("datastringnullprmolist", promosList.getYear_of_manufacture());

                                            try {
                                                int Y_O_M = Integer.parseInt(promosList.getYear_of_manufacture());
                                                String Engine = promosList.getEngine_type();
                                                if (Engine.equals("1")) {
                                                    Engine = "Gasoline";
                                                } else if (Engine.equals("2")) {

                                                    Engine = "Diesel";
                                                }
                                                ShowOilRecommendations(Engine, Y_O_M);

                                            } catch (NumberFormatException e) {
                                                e.printStackTrace();
                                                //   Toast.makeText(OIl_Recommendation.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                Oil_Recommendation_mainframe.setVisibility(View.GONE);
                                                Oil_recommendation_no_Car_added.setVisibility(View.GONE);
                                                Oil_recommendation_yom_not_added.setVisibility(View.VISIBLE);
                                            }
                                        } else {
                                            Oil_Recommendation_mainframe.setVisibility(View.GONE);
                                            Oil_recommendation_no_Car_added.setVisibility(View.GONE);
                                            Oil_recommendation_yom_not_added.setVisibility(View.VISIBLE);
                                        }
                                    }
                                    else
                                    {
                                        Oil_Recommendation_mainframe.setVisibility(View.GONE);
                                        Oil_recommendation_no_Car_added.setVisibility(View.GONE);
                                        Oil_recommendation_yom_not_added.setVisibility(View.GONE);
                                    }
                                }
/*
                                AddCarAdapter adapter = new AddCarAdapter(myListData, OIl_Recommendation.this,token,userid,recyclerView);
                                recyclerView.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OIl_Recommendation.this);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                adapter.notifyDataSetChanged();
                                //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setAdapter(adapter);*/

                            }
                            else
                            {
                                Oil_Recommendation_mainframe.setVisibility(View.GONE);
                                Oil_recommendation_no_Car_added.setVisibility(View.VISIBLE);
                                Oil_recommendation_yom_not_added.setVisibility(View.GONE);
                               // alerbox();
                                // Toast.makeText(AddCarScreen.this, getResources().getString(R.string.no_data_error), Toast.LENGTH_SHORT).show();
                                //  progressDialog.dismiss();
                            }

                        } catch (final JSONException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    /*Toast.makeText(OIl_Recommendation.this,
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
                    //        Toast.makeText(AddCarScreen.this, error.toString(), Toast.LENGTH_LONG).show();
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

                parameters.put("id",userid);

                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(OIl_Recommendation.this);
        requestQueue.add(stringRequest);


    }


    public void go_back_ar(View view)
    {
        finish();
    }

    public void go_back_eng(View view)
    {
        finish();
    }
}