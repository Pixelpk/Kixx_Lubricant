package com.pixelpk.kixxmobile.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.pixelpk.kixxmobile.Fcm.FirebaseToken;
import com.pixelpk.kixxmobile.LanguageSelectionScreen;
import com.pixelpk.kixxmobile.Location_Permission;
import com.pixelpk.kixxmobile.Login;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;

import java.io.File;
import java.util.Locale;

import static com.pixelpk.kixxmobile.User.Fragments.MapsFragment.MY_PERMISSIONS_REQUEST_LOCATION;

public class Splash extends AppCompatActivity {

   // private final int SPLASH_DISPLAY_LENGTH = 1000;
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView Splash_welcome;

    TextView Splash_welmessage;

    private static int SPLASH_TIME_OUT = 1000;
    ImageView logo_kixx,pic_kixx,imageView_kixx_bottle_left,imageView_kixx_bottle_right,imageView_kixx_bottle_middle;
    TextView slogan;
    Animation animation_top,animation_bottom,animation_fade,animation_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Splash_welcome = findViewById(R.id.Splash_welcome);




        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();


        String value = sharedPreferences.getString("disclosure","0");

        //    Toast.makeText(this, value, Toast.LENGTH_SHORT).show();

        if(value.equals("0"))
        {
            permission_disclouser();
        }
        else
            {

            Splash_welmessage = findViewById(R.id.Splash_welmessage);

            Intent ser = new Intent(this, FirebaseToken.class);
            startService(ser);

            String lang = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");
            editor.putString(Shared.User_promo,"2").apply();

            getFirebaseDynamicLink();

            //   Toast.makeText(this, lang, Toast.LENGTH_SHORT).show();

//            ActivityCompat.requestPermissions(Splash.this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    MY_PERMISSIONS_REQUEST_LOCATION);

//            if (ContextCompat.checkSelfPermission(Splash.this,
//                    Manifest.permission.ACCESS_FINE_LOCATION)
//                    == PackageManager.PERMISSION_GRANTED)
//            {
//                editor.putString(Shared.permission_location,"1").apply();
//            }

                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        //Calling new Activity
                        //   startActivity(new Intent(getApplicationContext(), ));
                        Intent mainIntent = new Intent(Splash.this, TutorialScreen.class);
                        editor.putString(Shared.User_promo,"1");
                        startActivity(mainIntent);
                        finish();
                        //  finish();
                    }
                }, SPLASH_TIME_OUT);

            if(lang!=null)
            {
                if(lang.equals("1"))
                {

                    setApplicationlanguage("ar");
                    Splash_welcome.setBackgroundResource(R.drawable.welcomear);
                    Splash_welmessage.setText(getResources().getString(R.string.join));

                }
                else if(lang.equals("2"))
                {

                    setApplicationlanguage("en");
                    Splash_welcome.setBackgroundResource(R.drawable.welcome);
                    Splash_welmessage.setText(getResources().getString(R.string.join));
                }
            }



            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        logo_kixx = findViewById(R.id.kixx_logo);
            pic_kixx = findViewById(R.id.image_car_splash_kixx);
            imageView_kixx_bottle_left = findViewById(R.id.bottle_kixx_splash);
//        imageView_kixx_bottle_middle = findViewById(R.id.kixx_bottle_middle);
//        imageView_kixx_bottle_right = findViewById(R.id.kixx_bottle_right);
//        slogan = findViewById(R.id.kixx_slogan_splash);
            animation_bottom = AnimationUtils.loadAnimation(this, R.anim.animation_car);
//        animation_right = AnimationUtils.loadAnimation(this, R.anim.slide_animation_to_right);
//        animation_top = AnimationUtils.loadAnimation(this, R.anim.appear_from_bottom_animation);
            animation_fade = AnimationUtils.loadAnimation(this,R.anim.animation_bottles);
            imageView_kixx_bottle_left.setAnimation(animation_fade);
            pic_kixx.setAnimation(animation_bottom);


            /* New Handler to start the Menu-Activity
             * and close this Splash-Screen after some seconds.*/

        }



    }

    public void setApplicationlanguage(String language) {
        Resources res = Splash.this.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(new Locale(language)); // API 17+ only.
        } else {
            conf.locale = new Locale(language);
        }
        res.updateConfiguration(conf, dm);
    }





    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults)
    {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(Splash.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED)
                    {

                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                //Calling new Activity
                                //   startActivity(new Intent(getApplicationContext(), ));
                                Intent mainIntent = new Intent(Splash.this, TutorialScreen.class);
                                editor.putString(Shared.User_promo,"1");
                                startActivity(mainIntent);
                                finish();
                                //  finish();
                            }
                        }, SPLASH_TIME_OUT);

                        //setTimer();
                        //Request location updates:
                        //  locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }

                }

                else
                    {
                        Intent mainIntent = new Intent(Splash.this, Location_Permission.class);
                        editor.putString(Shared.User_promo,"1");
                        startActivity(mainIntent);
                        finish();

//                    new Handler().postDelayed(new Runnable()
//                    {
//                        @Override
//                        public void run()
//                        {
//                            //Calling new Activity
//                            //   startActivity(new Intent(getApplicationContext(), ));
//                            Intent mainIntent = new Intent(Splash.this, TutorialScreen.class);
//                            editor.putString(Shared.User_promo,"1");
//                            startActivity(mainIntent);
//                            finish();
//                            //  finish();
//                        }
//                    }, SPLASH_TIME_OUT);

                    //      setTimer();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }}}


/*    public void setTimer()
    {
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                *//* Create an Intent that will start the Menu-Activity. *//*





            }
        }, SPLASH_DISPLAY_LENGTH);
    }*/

    public void getlinkdata(String user_id)
    {

        String sharelinktext = "https://Kixxmobile.page.link/?"+
                "link=https://www.kixxoil.com/?uid="+user_id+
                "&apn="+ getPackageName()+
                "&st="+"https://play.google.com/store/apps/details?id=com.pixelpk.kixxmobile"+
                "&ad="+"20"+
                "&si="+"https://syedu1.sg-host.com/Kixx-App/Kixx-App-Icon.png";


        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://www.kixxoil.com/"))
                .setDomainUriPrefix("https://Kixxmobile.page.link")
                // Open links with this app on Android
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                // Open links with com.example.ios on iOS
                .setIosParameters(new DynamicLink.IosParameters.Builder("com.Kixx").setAppStoreId("1546898433").build())
                .buildDynamicLink();

        Uri dynamicLinkUri = dynamicLink.getUri();
        Log.e("main"," Long refer "+ dynamicLink.getUri());




        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                // .setLink(dynamicLink.getUri())
                .setLongLink(Uri.parse(sharelinktext))
                .setDomainUriPrefix("https://example.page.link")
                // Set parameters
                // ...
                .buildShortDynamicLink()
                .addOnCompleteListener(Splash.this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();
                            Log.e("main " , " short link "+ shortLink);

                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_SEND);
                            intent.putExtra(intent.EXTRA_TEXT,shortLink.toString());
                            intent.setType("text/plain");
                            startActivity(intent);


                        } else {
                            // Error
                            // ...
                            Log.e("main " , " error "+ task.getException());
                        }
                    }
                });


        }




        public void getFirebaseDynamicLink()
        {
            FirebaseDynamicLinks.getInstance()
                    .getDynamicLink(getIntent())
                    .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                        @Override
                        public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                            // Get deep link from result (may be null if no link is found)
                            Uri deepLink = null;
                            if (pendingDynamicLinkData != null)
                            {
                                deepLink = pendingDynamicLinkData.getLink();
                                String link = deepLink.toString();
                                try {

                                    link = link.substring(link.lastIndexOf("=")+1);

                              //      Toast.makeText(Splash.this, link, Toast.LENGTH_SHORT).show();

                                    Log.d("Refer :", link);

                             //       Toast.makeText(Splash.this, link, Toast.LENGTH_SHORT).show();

                                    editor.putString(Shared.Reffered_code,link).apply();

//                                    Toast.makeText(Splash.this, link, Toast.LENGTH_SHORT).show();

                                }catch (Exception e)
                                {
                                    Log.e("exception",e.getMessage());
                                }

                  //              Toast.makeText(Splash.this, deepLink.toString(), Toast.LENGTH_SHORT).show();
                                Log.e("link","my refered link = "+ deepLink.toString());
                            }


                            // Handle the deep link. For example, open the linked
                            // content, or apply promotional credit to the user's
                            // account.
                            // ...

                            // ...
                        }
                    })
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("linkfailed", "getDynamicLink:onFailure", e);
                        }
                    });
        }


    void permission_disclouser()
    {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.custom_prominent_disclosure, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setCanceledOnTouchOutside(false);
        deleteDialog.setView(deleteDialogView);
        deleteDialogView.findViewById(R.id.Custom_prominent_disclouser_Yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //your business logic
                deleteDialog.dismiss();
                editor.putString("disclosure","1").apply();
                Splash_welmessage = findViewById(R.id.Splash_welmessage);

                Intent ser = new Intent(Splash.this, FirebaseToken.class);
                startService(ser);

                String lang = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");
                editor.putString(Shared.User_promo,"2").apply();

                getFirebaseDynamicLink();

                //   Toast.makeText(this, lang, Toast.LENGTH_SHORT).show();

//                ActivityCompat.requestPermissions(Splash.this,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_LOCATION);
//
//                if (ContextCompat.checkSelfPermission(Splash.this,
//                        Manifest.permission.ACCESS_FINE_LOCATION)
//                        == PackageManager.PERMISSION_GRANTED)
//                {
//
//                    editor.putString(Shared.permission_location,"1").apply();
//
//                }

                if(lang!=null)
                {
                    if(lang.equals("1"))
                    {

                        setApplicationlanguage("ar");
                        Splash_welcome.setBackgroundResource(R.drawable.welcomear);
                        Splash_welmessage.setText(getResources().getString(R.string.join));

                    }
                    else if(lang.equals("2"))
                    {

                        setApplicationlanguage("en");
                        Splash_welcome.setBackgroundResource(R.drawable.welcome);
                        Splash_welmessage.setText(getResources().getString(R.string.join));
                    }
                }

                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        //Calling new Activity
                        //   startActivity(new Intent(getApplicationContext(), ));
                        Intent mainIntent = new Intent(Splash.this, TutorialScreen.class);
                        editor.putString(Shared.User_promo,"1");
                        startActivity(mainIntent);
                        finish();
                        //  finish();
                    }
                }, SPLASH_TIME_OUT);

                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        logo_kixx = findViewById(R.id.kixx_logo);
                pic_kixx = findViewById(R.id.image_car_splash_kixx);
                imageView_kixx_bottle_left = findViewById(R.id.bottle_kixx_splash);
//        imageView_kixx_bottle_middle = findViewById(R.id.kixx_bottle_middle);
//        imageView_kixx_bottle_right = findViewById(R.id.kixx_bottle_right);
//        slogan = findViewById(R.id.kixx_slogan_splash);
                animation_bottom = AnimationUtils.loadAnimation(Splash.this, R.anim.animation_car);
//        animation_right = AnimationUtils.loadAnimation(this, R.anim.slide_animation_to_right);
//        animation_top = AnimationUtils.loadAnimation(this, R.anim.appear_from_bottom_animation);
                animation_fade = AnimationUtils.loadAnimation(Splash.this,R.anim.animation_bottles);
                imageView_kixx_bottle_left.setAnimation(animation_fade);
                pic_kixx.setAnimation(animation_bottom);


                /* New Handler to start the Menu-Activity
                 * and close this Splash-Screen after some seconds.*/


            }
        });

        deleteDialogView.findViewById(R.id.cancel_dialog_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //your business logic
                deleteDialog.dismiss();
                editor.putString("disclosure","1").apply();
                Splash_welmessage = findViewById(R.id.Splash_welmessage);

                Intent ser = new Intent(Splash.this, FirebaseToken.class);
                startService(ser);

                String lang = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");
                editor.putString(Shared.User_promo,"2").apply();

                getFirebaseDynamicLink();

                //   Toast.makeText(this, lang, Toast.LENGTH_SHORT).show();

//                ActivityCompat.requestPermissions(Splash.this,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_LOCATION);
//
//                if (ContextCompat.checkSelfPermission(Splash.this,
//                        Manifest.permission.ACCESS_FINE_LOCATION)
//                        == PackageManager.PERMISSION_GRANTED) {
//
//                    editor.putString(Shared.permission_location,"1").apply();
//
//                }

                if(lang!=null)
                {
                    if(lang.equals("1"))
                    {

                        setApplicationlanguage("ar");
                        Splash_welcome.setBackgroundResource(R.drawable.welcomear);
                        Splash_welmessage.setText(getResources().getString(R.string.join));

                    }
                    else if(lang.equals("2"))
                    {

                        setApplicationlanguage("en");
                        Splash_welcome.setBackgroundResource(R.drawable.welcome);
                        Splash_welmessage.setText(getResources().getString(R.string.join));
                    }
                }

                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        //Calling new Activity
                        //   startActivity(new Intent(getApplicationContext(), ));
                        Intent mainIntent = new Intent(Splash.this, TutorialScreen.class);
                        editor.putString(Shared.User_promo,"1");
                        startActivity(mainIntent);
                        finish();
                        //  finish();
                    }
                }, SPLASH_TIME_OUT);

                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        logo_kixx = findViewById(R.id.kixx_logo);
                pic_kixx = findViewById(R.id.image_car_splash_kixx);
                imageView_kixx_bottle_left = findViewById(R.id.bottle_kixx_splash);
//        imageView_kixx_bottle_middle = findViewById(R.id.kixx_bottle_middle);
//        imageView_kixx_bottle_right = findViewById(R.id.kixx_bottle_right);
//        slogan = findViewById(R.id.kixx_slogan_splash);
                animation_bottom = AnimationUtils.loadAnimation(Splash.this, R.anim.animation_car);
//        animation_right = AnimationUtils.loadAnimation(this, R.anim.slide_animation_to_right);
//        animation_top = AnimationUtils.loadAnimation(this, R.anim.appear_from_bottom_animation);
                animation_fade = AnimationUtils.loadAnimation(Splash.this,R.anim.animation_bottles);
                imageView_kixx_bottle_left.setAnimation(animation_fade);
                pic_kixx.setAnimation(animation_bottom);


                /* New Handler to start the Menu-Activity
                 * and close this Splash-Screen after some seconds.*/


            }
        });

        deleteDialog.show();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}