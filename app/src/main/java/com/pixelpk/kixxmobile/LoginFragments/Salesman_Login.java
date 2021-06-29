package com.pixelpk.kixxmobile.LoginFragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.Salesman.HomeScreen;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.AddCar.AddCarScreen;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class Salesman_Login extends Fragment {

    EditText Signin_salesphET_txt,Signin_salespassET_txt;

    Button Signin_salesSigninBtn;
    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String refreshedToken;

    EditText Salesman_countrycode_ET;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_salesman__login, container, false);

        refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //Toast.makeText(getContext(), refreshedToken, Toast.LENGTH_SHORT).show();

        InitializeView(view);

        Salesman_countrycode_ET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountryPicker picker = CountryPicker.newInstance("Select Country");  // dialog title
                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {

                        Salesman_countrycode_ET.setText(dialCode);
                        picker.dismiss();

                    }
                });
                picker.show(getFragmentManager(), "COUNTRY_PICKER");
            }
        });

        Signin_salesSigninBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent intent = new Intent(getContext(), HomeScreen.class);
                startActivity(intent);*/
if(Signin_salesphET_txt.getText().toString().equals(""))
{
    Toast.makeText(getContext(), "Please enter phone number", Toast.LENGTH_SHORT).show();
}
else if(Signin_salespassET_txt.getText().toString().equals(""))
{
    Toast.makeText(getContext(), "Please enter password", Toast.LENGTH_SHORT).show();
}
else if (Signin_salesphET_txt.getText().toString().charAt(0) != '0') {
    // Signup_userpassET_txt.setError("Please fill this field");
    Toast.makeText(getContext(), "Phone number must start with 0", Toast.LENGTH_SHORT).show();
}
else if(refreshedToken.equals(""))
{
    Toast.makeText(getContext(), "Network Problem! Please check your internet connection and restart app", Toast.LENGTH_SHORT).show();
}
else
{
    SigninSales(Signin_salesphET_txt.getText().toString(),Signin_salespassET_txt.getText().toString(),refreshedToken);
}

            }
        });

        return  view;
    }

    public void InitializeView(View view)
    {
        Signin_salesphET_txt = view.findViewById(R.id.Signin_salesphET_txt);
        Signin_salespassET_txt = view.findViewById(R.id.Signin_salespassET_txt);

        Signin_salesSigninBtn = view.findViewById(R.id.Signin_salesSigninBtn);

        progressDialog = new ProgressDialog(getContext());

        sharedPreferences = getContext().getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Salesman_countrycode_ET = view.findViewById(R.id.Salesman_countrycode_ET);

        Salesman_countrycode_ET.setFocusable(false);
        Salesman_countrycode_ET.setClickable(true);


    }


    public void SigninSales(final String contact, final String password, final String fcm_id)
    {

        progressDialog.show();
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.SALES_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

            //            Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            // JSONObject jsonObj_userexist = new JSONObject(response);
                            String user_exist_check = jsonObj.getString("message");

                  //          Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();


                            if(user_exist_check.equals("Login Successfull")) {

                                String jwt_token = jsonObj.getString("jwt_token");
                                String message = jsonObj.getString("message");

                               // Toast.makeText(getActivity(), jwt_token, Toast.LENGTH_SHORT).show();

                                editor.putString(Shared.sales_loggedIn_jwt,jwt_token).apply();

                                //  Toast.makeText(getContext(), jwt_token, Toast.LENGTH_SHORT).show();
                                //      Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                                    JSONObject data = jsonObj.getJSONObject("userdata");
                                    for (int i = 0; i < 1; i++) {

                                        String user_id = data.getString("salesman_id");
                                        String shop_id = data.getString("shop_id");
                                        //Toast.makeText(getActivity(), user_id, Toast.LENGTH_SHORT).show();
                  /*                  String user_name = data.getString("name");
                                    String user_email = data.getString("email");
                                    String user_ph = data.getString("phone");
                                    String user_dp = data.getString("profile_img");
                                    String user_gender = data.getString("gender");
                                    String user_fcm_id = data.getString("fcm_id");
                                    String user_loyality_points = data.getString("loyality_points");
                                    String user_occupation_id = data.getString("occupation_id");
                                    String user_occupation_name = data.getString("occupation_name");
                                    String user_category = data.getString("category");
                                    String user_user_role = data.getString("user_role");*/

                                        editor.putString(Shared.loggedIn_sales_id, user_id);
                                        editor.putString(Shared.loggedIn_sales_shopid, shop_id);
                                        //    editor.putString(Shared.loggedIn_user_id,);
/*                                    editor.putString(Shared.loggedIn_user_name,user_name);
                                    editor.putString(Shared.loggedIn_user_email,user_email);
                                    editor.putString(Shared.loggedIn_user_ph,user_ph);
                                    editor.putString(Shared.loggedIn_user_dp,user_dp);
                                    editor.putString(Shared.loggedIn_user_gender,user_gender);
                                    editor.putString(Shared.loggedIn_user_fcm_id,user_fcm_id);
                                    editor.putString(Shared.loggedIn_loyality_points,user_loyality_points);
                                    editor.putString(Shared.loggedIn_user_occupation_id,user_occupation_id);
                                    editor.putString(Shared.loggedIn_user_occupation_name,user_occupation_name);
                                    editor.putString(Shared.loggedIn_user_category,user_category);
                                    editor.putString(Shared.loggedIn_user_user_role,user_user_role);*/
                                        editor.apply();


                                }
                                Intent intent = new Intent(getContext(), com.pixelpk.kixxmobile.Salesman.HomeScreen.class);
                                intent.putExtra("promotion","1");
                                startActivity(intent);
                                getActivity().finish();
                            }
                            else
                            {
                                Toast.makeText(getContext(), R.string.usernotfound, Toast.LENGTH_SHORT).show();
                            }

                        } catch (final JSONException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(getContext(),
                                            "Json parsing error: " + e.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        }
//                      Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();


                        //      Toast.makeText(Signin.this, response, Toast.LENGTH_SHORT).show();


                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   progressDialog.dismiss();\
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("phone",contact);
                parameters.put("password", password);
                parameters.put("fcm_id", fcm_id);


                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
}