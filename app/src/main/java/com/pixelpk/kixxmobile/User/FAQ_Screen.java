package com.pixelpk.kixxmobile.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;

public class FAQ_Screen extends AppCompatActivity
{
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    TextView faq_screen_back,faq_choose;

    ImageView faq_image_back;

    //Handle Button Clicks
    private long mLastClickTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_screen);

        faq_image_back = findViewById(R.id.back_btn_faq);

        faq_screen_back = findViewById(R.id.faq_noon_saudi);

        faq_choose = findViewById(R.id.faq_text_view_inquiring);

        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();


        faq_screen_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.noon.com/saudi-en/p-29072"));
                startActivity(browserIntent);
            }
        });

        faq_choose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.noon.com/saudi-en/p-29072"));
                startActivity(browserIntent);
            }
        });

        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

        if(rtl.equals("1"))
        {
            faq_image_back.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24_rwhite);
        }

    }

    public void go_back_to_home(View view)
    {
        finish();
    }
}