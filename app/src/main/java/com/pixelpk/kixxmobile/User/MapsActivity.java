package com.pixelpk.kixxmobile.User;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pixelpk.kixxmobile.Login;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.pixelpk.kixxmobile.User.Fragments.MapsFragment.MY_PERMISSIONS_REQUEST_LOCATION;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, ConnectionCallbacks, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private Marker markerCenter;

    EditText MapsActivity_Contactnum_ET,MapsActivity_Email_ET,MapsActivity_Shopname_ET;
    Button MapsActivity_Submit_btn;

    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    GoogleApiClient mGoogleApiClient;
    Marker mCurrLocationMarker;
    TextView MapsActivity_seller_tv;
    ImageView Becomaseler_back_IV;

    String countrycode = "+966";

    LinearLayout Becomeaseler_back;

    String rtl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        initiaizeviews();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(MapsActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_LOCATION);

        MapsActivity_Submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LatLng centerLatLang =
                        mMap.getProjection().getVisibleRegion().latLngBounds.getCenter();

                String cont = countrycode + MapsActivity_Contactnum_ET.getText().toString();
                String email = MapsActivity_Email_ET.getText().toString();
                String shop = MapsActivity_Shopname_ET.getText().toString();

            //    Toast.makeText(MapsActivity.this, cont, Toast.LENGTH_SHORT).show();

                if(cont.equals(""))
                {
                    MapsActivity_Contactnum_ET.setError(getResources().getString(R.string.fill_fields));
                }
                else if(email.equals(""))
                {
                    MapsActivity_Email_ET.setError(getResources().getString(R.string.fill_fields));
                }
                else if(shop.equals(""))
                {
                    MapsActivity_Shopname_ET.setError(getResources().getString(R.string.fill_fields));
                }
                else if(cont.length() <9 ||  cont.length() > 16)
                {
                    MapsActivity_Contactnum_ET.setError(getResources().getString(R.string.incorrect_data));
                }
                else
                {

                    Register_Shop_Request(String.valueOf(centerLatLang.longitude),String.valueOf(centerLatLang.latitude),shop,cont,email);
                }

         //       Toast.makeText(MapsActivity.this, String.valueOf(centerLatLang.longitude) + "" + String.valueOf(centerLatLang.latitude), Toast.LENGTH_SHORT).show();
            }
        });

        Becomeaseler_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

       /* // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        mMap = googleMap;

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(mMap.getCameraPosition().target);
        markerCenter = mMap.addMarker(markerOptions);

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            public void onCameraMove() {


                markerCenter.setPosition(mMap.getCameraPosition().target);
            }
        });

        GPSTracker gps = new GPSTracker(getApplicationContext());
        buildGoogleApiClient();
        mGoogleApiClient.connect();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                googleMap.setMyLocationEnabled(true);
            }
        }
        else {
            googleMap.setMyLocationEnabled(true);
        }

    }


    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
        editText.setGravity(Gravity.CENTER);
    }

    public void initiaizeviews()
    {

        Becomaseler_back_IV = findViewById(R.id.Becomaseler_back_IV);
        MapsActivity_Contactnum_ET = findViewById(R.id.MapsActivity_Contactnum_ET);
        MapsActivity_Email_ET = findViewById(R.id.MapsActivity_Email_ET);
        MapsActivity_Shopname_ET = findViewById(R.id.MapsActivity_Shopname_ET);

        MapsActivity_Submit_btn = findViewById(R.id.MapsActivity_Submit_btn);
        MapsActivity_seller_tv = findViewById(R.id.MapsActivity_seller_tv);

        progressDialog = new ProgressDialog(this);

        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

        Becomeaseler_back = findViewById(R.id.Becomeaseler_back);

        if(rtl.equals("1"))
        {


            Becomaseler_back_IV.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24_rwhite);


        }
        else
        {

        }

        String lang = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");
        //  editor.putString(Shared.User_promo,"2").apply();

        //  Toast.makeText(this, lang, Toast.LENGTH_SHORT).show();

        if(lang!=null)
        {
            if(lang.equals("1"))
            {
                setApplicationlanguage("ar");
                //  Splash_welcome.setBackgroundResource(R.drawable.welcomear);
                MapsActivity_seller_tv.setText(R.string.Become_a_seller);
                MapsActivity_Contactnum_ET.setHint(R.string.Mobile_number);
                MapsActivity_Email_ET.setHint(R.string.update_email);
                MapsActivity_Shopname_ET.setHint(R.string.shop_name);
                MapsActivity_Submit_btn.setText(R.string.Submit);

            }
            else if(lang.equals("2"))
            {
                setApplicationlanguage("en");

                MapsActivity_seller_tv.setText(R.string.Become_a_seller);
                MapsActivity_Contactnum_ET.setHint(R.string.Mobile_number);
                MapsActivity_Email_ET.setHint(R.string.update_email);
                MapsActivity_Shopname_ET.setHint(R.string.shop_name);
                //   Splash_welcome.setBackgroundResource(R.drawable.welcome);
            }
        }




    }



    private void Register_Shop_Request(String longi,String lati,String shopname,String contact,String email) {

        //  Toast.makeText(this, refreshedToken, Toast.LENGTH_SHORT).show();


        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.REQUEST_SHOP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    //    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String resp = jsonObj.getString("response");
                   //         Toast.makeText(MapsActivity.this, resp, Toast.LENGTH_SHORT).show();
                            String message = jsonObj.getString("status");

                            if (message.contains("success"))
                            {
                                Toast.makeText(MapsActivity.this, getResources().getString(R.string.foroilchange), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MapsActivity.this, Login.class);
                                startActivity(intent);
                            }

                            else if (resp.contains("Duplicate"))
                            {
                                Toast.makeText(MapsActivity.this, getResources().getString(R.string.useralreadyregisteredunregistered), Toast.LENGTH_SHORT).show();
                            }

                            else
                            {
                                Toast.makeText(MapsActivity.this, getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();
                            }
                        }

                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }



                        progressDialog.dismiss();
                         Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                       progressDialog.dismiss();
                    }
                })
        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();


                parameters.put("longitude", longi);
                parameters.put("latitude", lati);
                parameters.put("shop_name", shopname);
                parameters.put("phone", contact);
                parameters.put("email", email);

                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }




    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(MapsActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                        mMap.setMyLocationEnabled(true);

                        //Request location updates:
                        //  locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }

                } else {
                    Toast.makeText(MapsActivity.this, getResources().getString(R.string.permissiondenied), Toast.LENGTH_SHORT).show();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }}}


    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(MapsActivity.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void setApplicationlanguage(String language) {
        Resources res = MapsActivity.this.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(new Locale(language)); // API 17+ only.
        } else {
            conf.locale = new Locale(language);
        }
        res.updateConfiguration(conf, dm);
    }

    @Override
    public void onDestroy()
    {
        GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
        gpsTracker.stopUsingGPS();
        super.onDestroy();
    }

    @Override
    protected void onPause()
    {
        GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
        gpsTracker.stopUsingGPS();
        super.onPause();
    }
}