package com.pixelpk.kixxmobile.Salesman.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.Salesman.Sales_SettingsAdapter;
import com.pixelpk.kixxmobile.User.ModelClasses.SettingsList;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
import com.pixelpk.kixxmobile.User.adapters.SettingsAdapter;

import static android.content.Context.MODE_PRIVATE;


public class Sales_SettingsFragment extends Fragment {


    ImageView Sales_settings_titlebar_kixxlogo;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sales_fragment_settings, container, false);

        Sales_settings_titlebar_kixxlogo = view.findViewById(R.id.Sales_settings_titlebar_kixxlogo);
        sharedPreferences = getContext().getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE, "0");

        if(rtl.equals("1"))
        {
            Sales_settings_titlebar_kixxlogo.setImageResource(R.mipmap.kixx_ar);
        }

        SettingsList[] myListData = new SettingsList[] {
                new SettingsList(R.drawable.settingsprofile,getResources().getString(R.string.Profile)),
                new SettingsList(R.drawable.aboutus,getResources().getString(R.string.change_language_eng)+" / "+getResources().getString(R.string.change_language_arab)),
                new SettingsList(R.drawable.aboutus,getResources().getString(R.string.About_us)),
           //     new SettingsList(R.drawable.contactus,getResources().getString(R.string.Contact_us)),
                new SettingsList(R.drawable.signout,getResources().getString(R.string.Signout)),



        };

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.Sales_fragment_Settings_settingRV);
        Sales_SettingsAdapter adapter = new Sales_SettingsAdapter(myListData,getActivity());
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
}