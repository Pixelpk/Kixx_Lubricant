package com.pixelpk.kixxmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;

public class Login extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    TabItem Login_tabitem_user,Login_tabitem_salesman;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        InitializeViewpager();
        InititializePreferences();

   /*     String rtl_value = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

        if(rtl_value.equals("1"))
        {
            Right_to_Left_Layout();
        }
        else
        {
            Left_to_Right_Layout();
        }*/


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    public void InitializeViewpager()
    {
        tabLayout = findViewById(R.id.Login_tablayout);
        viewPager = findViewById(R.id.Login_tablayout_Viewpager);
        viewPager.setAdapter(new Login_Viewpager(getSupportFragmentManager()));
        Login_tabitem_user = findViewById(R.id.Login_tabitem_user);
        Login_tabitem_salesman = findViewById(R.id.Login_tabitem_salesman);


    }

    public void InititializePreferences()
    {
        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
    }

   /* public void Left_to_Right_Layout()
    {

    }

    public void Right_to_Left_Layout()
    {
        tabLayout.setRotationX(R.integer.locale_mirror_flip_180);
       *//* Login_tabitem_user.setRotationX(180);
        Login_tabitem_salesman.setRotationX(180);*//*

        tabLayout.getTabAt(0).setText(R.string.User_arabic);
        tabLayout.getTabAt(1).setText(R.string.salesman_arabic);

    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }
}