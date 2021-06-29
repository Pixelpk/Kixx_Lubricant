package com.pixelpk.kixxmobile.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pixelpk.kixxmobile.Login;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.User.Fragments.HomeFragment;
import com.pixelpk.kixxmobile.User.Fragments.MapsFragment;
import com.pixelpk.kixxmobile.User.Fragments.NotificationsFragment;
import com.pixelpk.kixxmobile.User.Fragments.PromoFragment;
import com.pixelpk.kixxmobile.User.Fragments.SettingsFragment;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;

public class HomeScreen extends AppCompatActivity {

    Fragment selectedFragment = null;
    FloatingActionButton explore_frag_fab;
    ImageView ccard,mydeck,dropdeck,more;

    Intent intent_val;

    SharedPreferences sharedPreferences;
    String set_frag;

    TextView oilchange_button_TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        sharedPreferences = getSharedPreferences("Shared", MODE_PRIVATE);

        String check_if_loggedin = sharedPreferences.getString(Shared.User_login_logout_status, "0");

     //   Toast.makeText(this, check_if_loggedin, Toast.LENGTH_SHORT).show();

//        oilchange_button_TV = findViewById(R.id.oilchange_button_TV);

        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

//            Toast.makeText(getContext(),rtl, Toast.LENGTH_SHORT).show();
//        oilchange_button_TV.setText(getResources().getString(R.string.oilchange));


    /*    if(rtl.equals("1"))
        {

           // HomeFragment_titlebar_kixxlogo.setImageResource(R.mipmap.kixx_ar);
        }
        else
        {

        }*/

        if (check_if_loggedin.equals("1")) {




            BottomAppBar bottomNav = findViewById(R.id.bottomAppBar);
            explore_frag_fab = findViewById(R.id.explore_frag_fab);
            ccard = findViewById(R.id.createc);
            mydeck = findViewById(R.id.mydec);
            dropdeck = findViewById(R.id.dropdec);
            more = findViewById(R.id.moreopt);

            intent_val = getIntent();

             set_frag = sharedPreferences.getString(Shared.User_promo,"0");

            //    Toast.makeText(this, set_frag, Toast.LENGTH_SHORT).show();
            // bottomNav.setOnMenuItemClickListener(navListener);
            //I added this if statement to keep the selected fragment when rotating the device

            if (set_frag.equals("1")) {
                selectedFragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
            } else {
                selectedFragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
            }

 /*       selectedFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();*/

            explore_frag_fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedFragment = new MapsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                }
            });

            ccard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedFragment = new HomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                }
            });

            mydeck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedFragment = new NotificationsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                }
            });

            dropdeck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  /*  selectedFragment = new PromoFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();*/

                    PromoFragment ldf = new PromoFragment();
                    Bundle args = new Bundle();
                    args.putString("promotion", "2");
                    ldf.setArguments(args);

//Inflate the fragment
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, ldf).commit();
                }
            });

            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedFragment = new SettingsFragment();
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
            Intent intent = new Intent(HomeScreen.this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HomeScreen.super.onBackPressed();
                        finishAffinity();
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }
}