package com.pixelpk.kixxmobile;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.pixelpk.kixxmobile.LoginFragments.Salesman_Login;
import com.pixelpk.kixxmobile.LoginFragments.User_login;
import com.pixelpk.kixxmobile.User.TutorialsFragments.Tutorial_first_Screen;
import com.pixelpk.kixxmobile.User.TutorialsFragments.Tutorial_fourth_Screen;
import com.pixelpk.kixxmobile.User.TutorialsFragments.Tutorial_second_Screen;
import com.pixelpk.kixxmobile.User.TutorialsFragments.Tutorial_third_Screen;

public class Login_Viewpager extends FragmentPagerAdapter {

    public Login_Viewpager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new User_login(); //ChildFragment1 at position 0
         /*   case 1:
                return new Salesman_Login(); //ChildFragment2 at position 1*/
        }
        return null; //does not happen
    }

    @Override
    public int getCount() {
        return 1; //four fragments
    }
}