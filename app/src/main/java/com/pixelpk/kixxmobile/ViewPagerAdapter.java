package com.pixelpk.kixxmobile;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.pixelpk.kixxmobile.User.TutorialsFragments.Tutorial_fifth_Screen;
import com.pixelpk.kixxmobile.User.TutorialsFragments.Tutorial_first_Screen;
import com.pixelpk.kixxmobile.User.TutorialsFragments.Tutorial_fourth_Screen;
import com.pixelpk.kixxmobile.User.TutorialsFragments.Tutorial_second_Screen;
import com.pixelpk.kixxmobile.User.TutorialsFragments.Tutorial_third_Screen;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new Tutorial_first_Screen(); //ChildFragment1 at position 0
            case 1:
                return new Tutorial_second_Screen(); //ChildFragment2 at position 1
            case 2:
                return new Tutorial_fifth_Screen();
                 //ChildFragment3 at position 2
            case 3:
                return new Tutorial_third_Screen();
                 //ChildFragment3 at position 3
            case 4:
                return new Tutorial_fourth_Screen();

                 //ChildFragment3 at position 4
        }
        return null; //does not happen
    }

    @Override
    public int getCount() {
        return 5; //five fragments
    }
}