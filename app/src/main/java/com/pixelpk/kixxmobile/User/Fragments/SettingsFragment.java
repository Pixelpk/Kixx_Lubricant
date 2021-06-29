package com.pixelpk.kixxmobile.User.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pixelpk.kixxmobile.Constants;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.User.ModelClasses.PromosList;
import com.pixelpk.kixxmobile.User.ModelClasses.SettingsList;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
import com.pixelpk.kixxmobile.User.TutorialScreen;
import com.pixelpk.kixxmobile.User.UpdateUserProfile;
import com.pixelpk.kixxmobile.User.adapters.PromosAdapter;
import com.pixelpk.kixxmobile.User.adapters.SettingsAdapter;

import static android.content.Context.MODE_PRIVATE;


public class SettingsFragment extends Fragment {

    String KX_formatted_userid;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    TextView SettingsFragment_User_id;

    ImageView SettingsFragment_titlebar_kixxlogo;

    LinearLayout Settings_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        InitializeViews(view);
        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

//            Toast.makeText(getContext(),rtl, Toast.LENGTH_SHORT).show();


        Settings_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(getActivity()!=null)
                {
                    Intent intent = new Intent(getActivity(), UpdateUserProfile.class);
                    startActivity(intent);
                }
            }
        });
        SettingsFragment_titlebar_kixxlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TutorialScreen.class);
                editor.putString(Constants.Tutorial_Screen_id,"0").apply();
                startActivity(intent);
            }
        });

        if(rtl.equals("1"))
        {
            SettingsFragment_titlebar_kixxlogo.setImageResource(R.mipmap.kixx_ar);
        }

        SettingsList[] myListData = new SettingsList[] {

              //new SettingsList(R.drawable.becomeseller,"Add Car"),
                new SettingsList(R.drawable.settingsprofile, getResources().getString(R.string.profile)),
                new SettingsList(R.drawable.aboutus,getResources().getString(R.string.About_us)),
                new SettingsList(R.drawable.tutorials,getResources().getString(R.string.Tutorials)),
                new SettingsList(R.drawable.ic_productportfoilo,getResources().getString(R.string.Product_portfolio)),
                new SettingsList(R.drawable.feedback,getResources().getString(R.string.feedback_and_suggest)),
                new SettingsList(R.drawable.contactus,getResources().getString(R.string.contact_customer_service)),
                new SettingsList(R.drawable.ic_language, getResources().getString(R.string.change_language_eng)+" / "+getResources().getString(R.string.change_language_arab) ),
                new SettingsList(R.drawable.changepassword,getResources().getString(R.string.Change_password)),
                new SettingsList(R.drawable.ic_termsconditions,getResources().getString(R.string.terms)),
                new SettingsList(R.drawable.ic_faq_icon,getResources().getString(R.string.faqs)),
                new SettingsList(R.drawable.signout,getResources().getString(R.string.Signout)),

        };

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.Settings_settingRV);
        SettingsAdapter adapter = new SettingsAdapter(myListData,getActivity());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void InitializeViews(View view) {

        sharedPreferences = getActivity().getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        SettingsFragment_titlebar_kixxlogo = view.findViewById(R.id.SettingsFragment_titlebar_kixxlogo);

        KX_formatted_userid = sharedPreferences.getString(Shared.LoggedIn_fromatted_userid,"0");

        SettingsFragment_User_id = view.findViewById(R.id.SettingsFragment_User_id);

        SettingsFragment_User_id.setText(getResources().getString(R.string.User_id)+": "+KX_formatted_userid);

        Settings_id = view.findViewById(R.id.Settings_id);

    }


}