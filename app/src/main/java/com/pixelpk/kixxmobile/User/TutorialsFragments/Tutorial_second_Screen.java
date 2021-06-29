package com.pixelpk.kixxmobile.User.TutorialsFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pixelpk.kixxmobile.LanguageSelectionScreen;
import com.pixelpk.kixxmobile.R;


public class Tutorial_second_Screen extends Fragment {

    TextView tutorial_second_screen_skip_TV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tutorial_second__screen, container, false);





        return view;
    }


}