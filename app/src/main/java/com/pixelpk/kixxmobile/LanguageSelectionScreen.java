package com.pixelpk.kixxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.pixelpk.kixxmobile.User.HomeScreen;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;

import java.util.Locale;

public class LanguageSelectionScreen extends AppCompatActivity {

    RadioButton LanguageSelectionScreen_arabic_RB,LanguageSelectionScreen_english_RB;
    Button LanguageSelectionScreen_confirm_Btn;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String language = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InitializePreferences();

       /* Toast.makeText(this, sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE_ISSET,"0"), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0"), Toast.LENGTH_SHORT).show();*/

        if(sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE_ISSET,"0").equals("2") || sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE_ISSET,"0").equals("1"))
        {
            String sales = sharedPreferences.getString(Shared.sales_loggedIn_user_status,"0");
            String user = sharedPreferences.getString(Shared.User_login_logout_status,"0");


            if(sales.equals("1"))
            {
                Intent intent = new Intent(LanguageSelectionScreen.this, com.pixelpk.kixxmobile.Salesman.HomeScreen.class);
                startActivity(intent);
            }
            else if(user.equals("1"))
            {
                Intent intent = new Intent(LanguageSelectionScreen.this, HomeScreen.class);
                startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(LanguageSelectionScreen.this, Login.class);
                startActivity(intent);
            }


        }
        else {

            setContentView(R.layout.activity_language_selection_screen);

            ViewDialog alert = new ViewDialog();
            alert.showDialog(LanguageSelectionScreen.this);

          //  InitializeView();



        }


    }
    public void InitializePreferences()
    {
        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public class ViewDialog {

        public void InitializeView(Dialog dialog)
        {

            LanguageSelectionScreen_arabic_RB = dialog.findViewById(R.id.LanguageSelectionScreen_arabic_RB);
            LanguageSelectionScreen_english_RB = dialog.findViewById(R.id.LanguageSelectionScreen_english_RB);
            LanguageSelectionScreen_confirm_Btn = dialog.findViewById(R.id.LanguageSelectionScreen_confirm_Btn);

          //  LanguageSelectionScreen_confirm_Btn.setText(getResources().getString(R.string.confirm));
            LanguageSelectionScreen_confirm_Btn.setText("Continue / "+ getResources().getString(R.string.Continue_language));


        }



        public void showDialog(Activity activity){
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.languageselectpopup);
            InitializeView(dialog);


            LanguageSelectionScreen_arabic_RB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked == true) {
                        // editor.putString(Shared.KIXX_APP_LANGUAGE,"1").apply();
                        language = "arabic";
                        LanguageSelectionScreen_english_RB.setChecked(false);
                    }


                }
            });

            LanguageSelectionScreen_english_RB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked == true) {
                        language = "english";
                        LanguageSelectionScreen_arabic_RB.setChecked(false);
                    }

                }
            });


            LanguageSelectionScreen_confirm_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (language.equals("null")) {

                        alerbox();
                   //     Toast.makeText(LanguageSelectionScreen.this, getResources().getString(R.string.select_language), Toast.LENGTH_SHORT).show();
                    } else {

                        if (language.equals("arabic")) {
                            editor.putString(Shared.KIXX_APP_LANGUAGE, "1").apply();
                            setApplicationlanguage("ar");
                        } else if (language.equals("english")) {
                            editor.putString(Shared.KIXX_APP_LANGUAGE, "2").apply();
                            setApplicationlanguage("en");
                        }

                        String check_if_loggedin = sharedPreferences.getString(Shared.User_login_logout_status, "0");
                        String check_if_loggedin_sales = sharedPreferences.getString(Shared.sales_loggedIn_user_status, "0");

                //        Toast.makeText(activity, check_if_loggedin_sales, Toast.LENGTH_SHORT).show();

                        if(check_if_loggedin.equals("1"))
                        {
                            Intent intent = new Intent(LanguageSelectionScreen.this, HomeScreen.class);
                            startActivity(intent);
                            editor.putString(Shared.KIXX_APP_LANGUAGE_ISSET, "2").apply();
                            dialog.dismiss();
                            finish();
                        }
                        else if(check_if_loggedin_sales.equals("1"))
                        {  Intent intent = new Intent(LanguageSelectionScreen.this, com.pixelpk.kixxmobile.Salesman.HomeScreen.class);
                            startActivity(intent);
                            editor.putString(Shared.KIXX_APP_LANGUAGE_ISSET, "2").apply();
                            dialog.dismiss();
                            finish();

                        }
                        else
                        {
                            Intent intent = new Intent(LanguageSelectionScreen.this, Login.class);
                            startActivity(intent);
                            editor.putString(Shared.KIXX_APP_LANGUAGE_ISSET, "2").apply();
                            dialog.dismiss();
                            finish();
                        }

                      //  if (check_if_loggedin.equals("1")) {



                       /* }
                        else
                        {
                            Intent intent = new Intent(LanguageSelectionScreen.this, Login.class);
                            startActivity(intent);
                            editor.putString(Shared.KIXX_APP_LANGUAGE_ISSET, "2").apply();
                            dialog.dismiss();
                            finish();
                        }*/
                    }

                }
            });

            dialog.show();

        }
    }


    public void setApplicationlanguage(String language) {
        Resources res = LanguageSelectionScreen.this.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(new Locale(language)); // API 17+ only.
        } else {
            conf.locale = new Locale(language);
        }
        res.updateConfiguration(conf, dm);
    }

    public void alerbox()
    {
        new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.select_language))
                .setCancelable(false)
                .setNegativeButton(getResources().getString(R.string.ok), null)
                .show();
    }
}