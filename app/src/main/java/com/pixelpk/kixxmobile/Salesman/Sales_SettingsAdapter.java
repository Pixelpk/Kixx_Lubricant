package com.pixelpk.kixxmobile.Salesman;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.pixelpk.kixxmobile.Constants;
import com.pixelpk.kixxmobile.LanguageSelectionScreen;
import com.pixelpk.kixxmobile.Login;
import com.pixelpk.kixxmobile.LoginFragments.User_login;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.TermsScreen;
import com.pixelpk.kixxmobile.User.AboutUsScreen;
import com.pixelpk.kixxmobile.User.ChangePassword;
import com.pixelpk.kixxmobile.User.ContactUs;
import com.pixelpk.kixxmobile.User.Feedback;
import com.pixelpk.kixxmobile.User.ModelClasses.SettingsList;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
import com.pixelpk.kixxmobile.User.TutorialScreen;
import com.pixelpk.kixxmobile.User.UpdateUserProfile;
import com.pixelpk.kixxmobile.User.aboutus_pdf;
import com.pixelpk.kixxmobile.User.adapters.SettingsAdapter;

public class Sales_SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

    private SettingsList[] listdata;
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String rtl;

    public Sales_SettingsAdapter(SettingsList[] listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
        sharedPreferences = context.getSharedPreferences("Shared",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");
    }

    @Override
    public SettingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.settings_list_item, parent, false);
        SettingsAdapter.ViewHolder viewHolder = new SettingsAdapter.ViewHolder(listItem);
        return viewHolder;
        
    }

    @Override
    public void onBindViewHolder(SettingsAdapter.ViewHolder holder, final int position) {
        final SettingsList myListData = listdata[position];

        if(rtl.equals("1"))
        {
            if(position==1)
            {
                holder.Settings_optionTV.setGravity(Gravity.END);
            }


            /*name.setGravity(Gravity.END);
            email.setGravity(Gravity.END);
            //   mobile.setGravity(Gravity.END);
            adapter_occupat = new ArrayAdapter<String>(this,
                    R.layout.spinner_white_text,occupation_array_ar);*/


        }

        holder.Settings_optionsIV.setImageResource(myListData.getImageid());
        holder.Settings_optionTV.setText(myListData.getDescription());

        holder.Settings_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* if(position == 0)
                {
                    Intent intent = new Intent(context, AddCarScreen.class);
                    context.startActivity(intent);
                }
                else */
                if (position == 0) {
                    Intent intent = new Intent(context, Sales_UpdateProfile.class);
                    context.startActivity(intent);
                    //  Toast.makeText(context, "Module Under Development", Toast.LENGTH_SHORT).show();
                }
                else if (position == 1) {
                    Intent intent = new Intent(context, LanguageSelectionScreen.class);
                    context.startActivity(intent);
                    editor.putString(Shared.KIXX_APP_LANGUAGE_ISSET,"0").apply();
                    //dule Under Development", Toast.LENGTH_SHORT).show();
                }
                else if (position == 2) {
                    Intent intent = new Intent(context, aboutus_pdf.class);
                    context.startActivity(intent);
                } /*else if (position == 3) {
                    Intent intent = new Intent(context, ContactUs.class);
                    context.startActivity(intent);
                }*/ else if (position == 3) {

                    new AlertDialog.Builder(context)
                            .setMessage(context.getResources().getString(R.string.are_you_sure_to_logout))
                            .setCancelable(false)
                            .setPositiveButton(context.getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    editor.putString(Shared.sales_loggedIn_user_status,"0").apply();
                                    Intent intent = new Intent(context, Login.class);
                                    context.startActivity(intent);
                                    ((Activity)context).finish();

                                    /* HomeScreen.super.onBackPressed();
                                    finishAffinity();
                                    finish();*/
                                }
                            })
                            .setNegativeButton(context.getResources().getString(R.string.cancel), null)
                            .show();


                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout Settings_layout;
        public ImageView Settings_optionsIV;
        public TextView Settings_optionTV;

        public ViewHolder(View itemView) {
            super(itemView);

            this.Settings_optionsIV = (ImageView) itemView.findViewById(R.id.Settings_iconIV);
            this.Settings_optionTV = (TextView) itemView.findViewById(R.id.Settings_optionTV);
            this.Settings_layout = (LinearLayout) itemView.findViewById(R.id.Settings_settingsLL);

        }
    }
}