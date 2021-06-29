package com.pixelpk.kixxmobile.User.OilRecommendation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;

public class Howtochooseoil extends AppCompatActivity
{
    LinearLayout OilRecommendation_back;
    TextView Oil_recommendation_link_TV;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howtochooseoil);

        initializeViews();

        OilRecommendation_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        Oil_recommendation_link_TV.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.kixxoil.com/support/categoryByPerformance"));
                startActivity(browserIntent);
            }
        });
    }

    private void initializeViews() {

        OilRecommendation_back = findViewById(R.id.OilRecommendation_back);
        Oil_recommendation_link_TV = findViewById(R.id.Oil_recommendation_link_TV);

        back_btn = findViewById(R.id.back_how_to_choose);

        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

        if(rtl.equals("1"))
        {
            back_btn.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24_rwhite);
        }
    }
}