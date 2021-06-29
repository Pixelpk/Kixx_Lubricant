package com.pixelpk.kixxmobile.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pixelpk.kixxmobile.R;

public class AboutUsScreen extends AppCompatActivity {

    ImageView Aboutus_twitter_IV,Aboutus_instagram_IV,Aboutus_facebook_IV,Aboutus_backarrow,Aboutus_linkedin_IV;
    LinearLayout Aboutus_backarrow_LL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_screen);

        InitializeView();

      /*  Aboutus_backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        Aboutus_backarrow_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Aboutus_twitter_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Uri uri = Uri.parse("https://twitter.com/OilKixx/status/1334112146246995974?s=08"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);*/
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/OilKixx/status/1334112146246995974?s=08"));
                startActivity(browserIntent);
            }
        });

        Aboutus_instagram_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/p/CJqv6ODBqP6/?igshid=y4f3c9fr3f9n"));
                startActivity(browserIntent);
            /*    Uri uri = Uri.parse("https://www.instagram.com/p/CJqv6ODBqP6/?igshid=y4f3c9fr3f9n"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);*/
            }
        });

        Aboutus_facebook_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Uri uri = Uri.parse("https://www.facebook.com/kixx.oil.ksa/photos/a.143840844193668/153930119851407/?type=3"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);*/
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/kixx.oil.ksa/photos/a.143840844193668/153930119851407/?type=3"));
                startActivity(browserIntent);
            }
        });

        Aboutus_linkedin_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/posts/kixx-oil-ksa_aepaesaebaehaewaexaeyaev-aeraewaezaeyaep-activity-6752238531164332032-GT4w"));
                startActivity(browserIntent);
               /* Uri uri = Uri.parse("https://www.linkedin.com/posts/kixx-oil-ksa_aepaesaebaehaewaexaeyaev-aeraewaezaeyaep-activity-6752238531164332032-GT4w"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);*/
            }
        });

    }

    private void InitializeView() {

        Aboutus_facebook_IV = findViewById(R.id.Aboutus_facebook_IV);
        Aboutus_instagram_IV = findViewById(R.id.Aboutus_instagram_IV);
        Aboutus_twitter_IV = findViewById(R.id.Aboutus_twitter_IV);
        Aboutus_backarrow = findViewById(R.id.Aboutus_backarrow);
        Aboutus_linkedin_IV = findViewById(R.id.Aboutus_linkedin_IV);
        Aboutus_backarrow_LL = findViewById(R.id.Aboutus_backarrow_LL);

    }
}