package com.pixelpk.kixxmobile.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.pixelpk.kixxmobile.Constants;
import com.pixelpk.kixxmobile.LanguageSelectionScreen;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
import com.pixelpk.kixxmobile.ViewPagerAdapter;
import com.rd.PageIndicatorView;

public class TutorialScreen extends AppCompatActivity {

    ViewPager viewPager;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    PageIndicatorView pageIndicatorView;
    Button TutorialScreen_next_btn,tutorial_first_screen_skip_TV,tutorialScreen_finish;

    //Handle Button Clicks
    private long mLastClickTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_screen);

        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        InitializeView();

        tutorial_first_screen_skip_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                Intent intent = new Intent(TutorialScreen.this, LanguageSelectionScreen.class);
                startActivity(intent);
                finish();
            }
        });

        tutorialScreen_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                Intent intent = new Intent(TutorialScreen.this, LanguageSelectionScreen.class);
                startActivity(intent);
                finish();
            }
        });

        TutorialScreen_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                viewPager.setCurrentItem(getItem(+1), true);
            }
        });

        String screenid = sharedPreferences.getString(Constants.Tutorial_Screen_id,"0");
     //   Toast.makeText(this, screenid, Toast.LENGTH_SHORT).show();

        if(screenid.equals("0"))
        {

            editor.putString(Constants.Tutorial_Screen_id,"2").apply();
            viewPager = findViewById(R.id.view_pager);
           // TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
      //     tabLayout.setupWithViewPager(viewPager, true);
            viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
            pageIndicatorView = findViewById(R.id.pageIndicatorView);
            pageIndicatorView.setCount(5); // specify total count of indicators

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {/*empty*/}

                @Override
                public void onPageSelected(int position) {
                 //   Toast.makeText(TutorialScreen.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                    if(position==4)
                    {
                        tutorialScreen_finish.setVisibility(View.VISIBLE);
                        TutorialScreen_next_btn.setVisibility(View.GONE);

                    }
                    else
                    {
                        tutorialScreen_finish.setVisibility(View.GONE);
                        TutorialScreen_next_btn.setVisibility(View.VISIBLE);
                    }
                    pageIndicatorView.setSelection(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {/*empty*/}
            });
        }
        else
        {
            Intent intent = new Intent(TutorialScreen.this, LanguageSelectionScreen.class);
            startActivity(intent);
        }



    }

    private void InitializeView() {

        TutorialScreen_next_btn = findViewById(R.id.TutorialScreen_next_btn);
        tutorialScreen_finish = findViewById(R.id.tutorialScreen_finish);
        tutorial_first_screen_skip_TV = findViewById(R.id.tutorial_first_screen_skip_TV);

    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }
}