package com.pixelpk.kixxmobile.User.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.pixelpk.kixxmobile.Constants;
import com.pixelpk.kixxmobile.LanguageSelectionScreen;
import com.pixelpk.kixxmobile.Login;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.TermsScreen;
import com.pixelpk.kixxmobile.User.AboutUsScreen;
import com.pixelpk.kixxmobile.User.AddCar.AddCarScreen;
import com.pixelpk.kixxmobile.User.ChangePassword;
import com.pixelpk.kixxmobile.User.ContactUs;
import com.pixelpk.kixxmobile.User.FAQ_Screen;
import com.pixelpk.kixxmobile.User.Feedback;
import com.pixelpk.kixxmobile.User.HomeScreen;
import com.pixelpk.kixxmobile.User.MapsActivity;
import com.pixelpk.kixxmobile.User.ModelClasses.PromosList;
import com.pixelpk.kixxmobile.User.ModelClasses.SettingsList;
import com.pixelpk.kixxmobile.User.ProductPortfolio;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
import com.pixelpk.kixxmobile.User.Signin;
import com.pixelpk.kixxmobile.User.Signup;
import com.pixelpk.kixxmobile.User.TutorialScreen;
import com.pixelpk.kixxmobile.User.UpdateUserProfile;
import com.pixelpk.kixxmobile.User.aboutus_pdf;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder>{
    private SettingsList[] listdata;
    Context context;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String rtl;

    String uid;

    // RecyclerView recyclerView;
    public SettingsAdapter(SettingsList[] listdata, Context context) {
        this.listdata = listdata;
        this.context = context;

        sharedPreferences = context.getSharedPreferences("Shared",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");
        uid = sharedPreferences.getString(Shared.loggedIn_user_id,"0");



   //     Toast.makeText(context, uid, Toast.LENGTH_SHORT).show();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.settings_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final SettingsList myListData = listdata[position];

        holder.Settings_optionsIV.setImageResource(myListData.getImageid());
    //    Toast.makeText(context, myListData.getImageid(), Toast.LENGTH_SHORT).show();
        holder.Settings_optionTV.setText(myListData.getDescription());
        if(rtl.equals("1"))
        {
            if(position==6)
            {
                holder.Settings_optionTV.setGravity(Gravity.END);
            }


            /*name.setGravity(Gravity.END);
            email.setGravity(Gravity.END);
            //   mobile.setGravity(Gravity.END);
            adapter_occupat = new ArrayAdapter<String>(this,
                    R.layout.spinner_white_text,occupation_array_ar);*/


        }


        holder.Settings_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(position == 2)
                {
                    Intent intent = new Intent(context, TutorialScreen.class);
                    editor.putString(Constants.Tutorial_Screen_id,"0").apply();
                    context.startActivity(intent);
                }
                else  if(position == 0)
                {
                    Intent intent = new Intent(context, UpdateUserProfile.class);
                    context.startActivity(intent);
                  //  Toast.makeText(context, "Module Under Development", Toast.LENGTH_SHORT).show();
                }
                else if(position == 7)
                {
                    Intent intent = new Intent(context, ChangePassword.class);
                    context.startActivity(intent);
                    //  Toast.makeText(context, "Module Under Development", Toast.LENGTH_SHORT).show();
                }
                else if(position == 8)
                {
                    Intent intent = new Intent(context, TermsScreen.class);
                    context.startActivity(intent);
                }
                else if(position == 3)
                {

                    Intent intent = new Intent(context, ProductPortfolio.class);
                    context.startActivity(intent);
                   // Toast.makeText(context, "Module Under Development", Toast.LENGTH_SHORT).show();

                   // createlink();

                }
                /*else if(position == 9)
                {
                    Toast.makeText(context, "For FAQ`s", Toast.LENGTH_SHORT).show();
                   *//* Intent intent = new Intent(context, AboutUsScreen.class);
                    context.startActivity(intent);*//*
                }*/
                else if(position == 5)
                {
                    Intent intent = new Intent(context, ContactUs.class);
                    context.startActivity(intent);
                }
                else if(position == 6)
                {
                    //Toast.makeText(context, "Module Under Development", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, LanguageSelectionScreen.class);
                    context.startActivity(intent);
                    editor.putString(Shared.KIXX_APP_LANGUAGE_ISSET,"0").apply();
                }
                else if(position == 4)
                {
                    Intent intent = new Intent(context, Feedback.class);
                    context.startActivity(intent);
                 //   Toast.makeText(context, "Module Under Development", Toast.LENGTH_SHORT).show();
                }
                else if(position == 1)
                {

                    Intent intent = new Intent(context, aboutus_pdf.class);
                    context.startActivity(intent);
                    /*Intent intent = new Intent(context, HomeScreen.class);
                    intent.putExtra("promotion","2");
                    context.startActivity(intent);
                    editor.putString(Shared.User_promo,"2").apply();*/
                }

                else if(position == 9)
                {
                    Intent intent = new Intent(context, FAQ_Screen.class);
                    context.startActivity(intent);
                    /*Intent intent = new Intent(context, HomeScreen.class);
                    intent.putExtra("promotion","2");
                    context.startActivity(intent);
                    editor.putString(Shared.User_promo,"2").apply();*/

                }

                else if(position == 10)
                {

                    new AlertDialog.Builder(context)
                            .setMessage(context.getResources().getString(R.string.are_you_sure_to_logout))
                            .setCancelable(false)
                            .setPositiveButton(context.getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    editor.putString(Shared.User_login_logout_status,"0");
                                    Intent intent = new Intent(context, Login.class);
                                    editor.putString(Shared.User_promo,"2");
                                    context.startActivity(intent);
                                    editor.apply();
                                    ((Activity)context).finish();

                                    /* HomeScreen.super.onBackPressed();
                                    finishAffinity();
                                    finish();*/
                                }
                            })
                            .setNegativeButton(context.getResources().getString(R.string.cancel), null)
                            .show();



                    // ((Activity) context).finishActivity(1);


                }
               /* else if(position == 9)
                {

                }*/


            }

            
            private void createlink() {
                Log.e("main","create link");


                String sharelinktext = "https://Kixxmobile.page.link/?"+
                        "link=https://www.kixxoil.com/?uid="+uid+
                        "&apn="+ context.getPackageName()+
                        "&st="+"https://play.google.com/store/apps/details?id=com.pixelpk.kixxmobile"+
                        "&ad="+"20"+
                        "&si="+"https://syedu1.sg-host.com/Kixx-App/Kixx-App-Icon.png";


                DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                        .setLink(Uri.parse("https://www.kixxoil.com/"))
                        .setDomainUriPrefix("https://Kixxmobile.page.link")
                        // Open links with this app on Android
                        .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                        // Open links with com.example.ios on iOS
                        .setIosParameters(new DynamicLink.IosParameters.Builder("com.Kixx").setAppStoreId("1546898433").build())
                        .buildDynamicLink();

                Uri dynamicLinkUri = dynamicLink.getUri();
                Log.e("main"," Long refer "+ dynamicLink.getUri());




                Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                       // .setLink(dynamicLink.getUri())
                        .setLongLink(Uri.parse(sharelinktext))
                        .setDomainUriPrefix("https://example.page.link")
                        // Set parameters
                        // ...
                        .buildShortDynamicLink()
                        .addOnCompleteListener((Activity) context, new OnCompleteListener<ShortDynamicLink>() {
                            @Override
                            public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                                if (task.isSuccessful()) {
                                    // Short link created
                                    Uri shortLink = task.getResult().getShortLink();
                                    Uri flowchartLink = task.getResult().getPreviewLink();
                                    Log.e("main " , " short link "+ shortLink);

                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_SEND);
                                    intent.putExtra(intent.EXTRA_TEXT,shortLink.toString());
                                    intent.setType("text/plain");
                                    context.startActivity(intent);


                                } else {
                                    // Error
                                    // ...
                                    Log.e("main " , " error "+ task.getException());
                                }
                            }
                        });

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