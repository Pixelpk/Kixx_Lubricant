package com.pixelpk.kixxmobile.Salesman;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pixelpk.kixxmobile.Login;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.Salesman.Fragments.Sales_HomeFragment;
import com.pixelpk.kixxmobile.Salesman.Fragments.Sales_NotificationsFragment;
import com.pixelpk.kixxmobile.Salesman.Fragments.Sales_PromoFragment;
import com.pixelpk.kixxmobile.Salesman.Fragments.Sales_QRFragment;
import com.pixelpk.kixxmobile.Salesman.Fragments.Sales_SettingsFragment;
import com.pixelpk.kixxmobile.User.Fragments.HomeFragment;
import com.pixelpk.kixxmobile.User.Fragments.MapsFragment;
import com.pixelpk.kixxmobile.User.Fragments.NotificationsFragment;
import com.pixelpk.kixxmobile.User.Fragments.PromoFragment;
import com.pixelpk.kixxmobile.User.Fragments.SettingsFragment;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;

public class HomeScreen extends AppCompatActivity
{
    Fragment selectedFragment = null;
    FloatingActionButton explore_frag_fab;
    ImageView ccard,mydeck,dropdeck,more;
    SharedPreferences sharedPreferences;

    TextView oilchange_button_TV;

    //Handle Button Clicks
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("Shared", MODE_PRIVATE);

     //   oilchange_button_TV = findViewById(R.id.oilchange_button_TV_sales);

        String check_if_loggedin = sharedPreferences.getString(Shared.sales_loggedIn_user_status, "0");

     //   oilchange_button_TV = findViewById(R.id.oilchange_button_TV);

        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

//            Toast.makeText(getContext(),rtl, Toast.LENGTH_SHORT).show();
//        oilchange_button_TV.setText(getResources().getString(R.string.oilchange));
        //   Toast.makeText(this, check_if_loggedin, Toast.LENGTH_SHORT).show();

        if (check_if_loggedin.equals("1")) {


            setContentView(R.layout.activity_salesman_home_screen);

            BottomAppBar bottomNav = findViewById(R.id.bottomAppBar);
            explore_frag_fab = findViewById(R.id.explore_frag_fab);
            ccard = findViewById(R.id.createc);
            mydeck = findViewById(R.id.mydec);
            dropdeck = findViewById(R.id.dropdec);
            more = findViewById(R.id.moreopt);
            // bottomNav.setOnMenuItemClickListener(navListener);
            //I added this if statement to keep the selected fragment when rotating the device

            selectedFragment = new Sales_HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();


            explore_frag_fab.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                    {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    selectedFragment = new Sales_QRFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                }
            });

            ccard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                    {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    selectedFragment = new Sales_HomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                }
            });

            mydeck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                    {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    selectedFragment = new Sales_NotificationsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                }
            });

            dropdeck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                    {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    Sales_PromoFragment ldf = new Sales_PromoFragment();
                    Bundle args = new Bundle();
                    args.putString("promotion", "2");
                    ldf.setArguments(args);

//Inflate the fragment
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ldf).commit();
                }
            });

            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                    {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    selectedFragment = new Sales_SettingsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                }
            });



     /*  bottomNav.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               selectedFragment = new Create_Card_Fragment();
               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                       selectedFragment).commit();
           }
       });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Create_Card_Fragment()).commit();
        }*/
        }
        else
        {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
            {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();

            Intent intent = new Intent(HomeScreen.this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
     //   super.onBackPressed();
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        com.pixelpk.kixxmobile.Salesman.HomeScreen.super.onBackPressed();
                        finishAffinity();
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}