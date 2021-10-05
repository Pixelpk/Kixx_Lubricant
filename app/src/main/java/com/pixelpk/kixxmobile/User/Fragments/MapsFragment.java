package com.pixelpk.kixxmobile.User.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.GPSTracker;
import com.pixelpk.kixxmobile.User.HomeScreen;
import com.pixelpk.kixxmobile.User.ModelClasses.AddLangLatList;
import com.pixelpk.kixxmobile.User.ModelClasses.DistanceModelClass;
import com.pixelpk.kixxmobile.User.ModelClasses.MapFragmentRecyclerList;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
import com.pixelpk.kixxmobile.User.adapters.MapFragmentRecyclerAdapter;
import com.pixelpk.kixxmobile.directionhelpers.TaskLoadedCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MapsFragment extends Fragment  implements
        LocationListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener,TaskLoadedCallback, ResultCallback<Status> {

    RecyclerView mapfragmentRV;
    ArrayList<LatLng> latlngs;
    ProgressDialog progressDialog;
    LatLng  geofencelatlng;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String token;
    private Marker geoFenceMarker;
    LocationManager locationManager;
    LocationListener locationListener;


    public GoogleMap mMap;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    GoogleMap map;

    String parsedDistance;
    String response;

    ArrayList<Float> directdistancearray;

    ArrayList<Geofence> mGeofenceList;

    List<AddLangLatList> addLangLatLists;

    MarkerOptions options;

    List<MapFragmentRecyclerList> mapFragmentRecyclerLists;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private MarkerOptions place1, place2;
    Button getDirection;
    private Polyline currentPolyline;

    String permission_data = "";
    final int bufferSize = 1024;
    final char[] buffer = new char[bufferSize];

    Location center;
    Location test;
    Location location;

    LatLng origin;

    String denied_str;

    ArrayList<DistanceModelClass> distanceModelClasses;

    boolean getlocation_flag = false;

    //Handle Button Clicks
    private long mLastClickTime = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
//        checkPermission();
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        sharedPreferences = getActivity().getSharedPreferences("Shared", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        mapFragmentRecyclerLists = new ArrayList<>();

        addLangLatLists = new ArrayList<>();
        center = new Location("dummyprovider");
        test = new Location("dummyprovider");
        directdistancearray = new ArrayList<>();


        //     Toast.makeText(getContext(), String.valueOf(center.distanceTo(test)), Toast.LENGTH_SHORT).show();

        mGeofenceList = new ArrayList<>();
        distanceModelClasses = new ArrayList<>();
        token = sharedPreferences.getString("loggedIn_jwt", "0");

        permission_data = sharedPreferences.getString(Shared.permission_location,"0");

        denied_str = sharedPreferences.getString("Shared_denied","0");

//        Toast.makeText(getContext(), denied_str, Toast.LENGTH_SHORT).show();

        mapfragmentRV = view.findViewById(R.id.mapfragmentRV);

        progressDialog = new ProgressDialog(getActivity());

        locationManager = (LocationManager)
                getActivity().getSystemService(Context.LOCATION_SERVICE);

        locationListener = new MapsFragment();

//        ActivityCompat.requestPermissions(getActivity(),
//                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                MY_PERMISSIONS_REQUEST_LOCATION);

        if(ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_DENIED)
        {
            pop();
        }

        else if(ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(getContext(), getResources().getString(R.string.checking_shops_string), Toast.LENGTH_SHORT).show();
        }


/*        LatLng latLngFrom = new LatLng(31.4775,74.2803);
        LatLng latLngTo = new LatLng(31.4469,74.2682);

        double dis = SphericalUtil.computeDistanceBetween(latLngFrom, latLngTo);

        Toast.makeText(getContext(), String.valueOf(dis), Toast.LENGTH_SHORT).show();*/



        //dist();

     /*   LatLng origion = new LatLng(31.475782,74.279018);
        LatLng destination = new LatLng(31.484249,74.326139);
        place1 = new MarkerOptions().position(new LatLng(31.475782,74.279018)).title("Origin");
        place2 = new MarkerOptions().position(new LatLng(31.484249,74.326139)).title("Destination");*/





        /*MapFragmentRecyclerList[] myListData = new MapFragmentRecyclerList[] {
                new MapFragmentRecyclerList(R.drawable.slide1,"Liberty Kixx Oil Change","Lahore"),
                new MapFragmentRecyclerList(R.drawable.slide2,"DHA Kixx Oil Change","Lahore"),
                new MapFragmentRecyclerList(R.drawable.slide3,"Bahria Kixx Oil Change","Lahore"),
        };
*/
        return view;
    }

    private void pop()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Location Access is required for the app to work. Please provide location Access. Please permit the permission through"
                + "Settings screen.\n\nSelect Permissions -> Enable permission");
        builder.setCancelable(false);
        builder.setPositiveButton("Permit Manually", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
/*                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();*/

                dialog.dismiss();
                Intent intent = new Intent();
                intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
/*                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();*/

                Intent intent = new Intent(getActivity(),HomeScreen.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        builder.show();
    }


    protected Marker createMarker(double latitude, double longitude,GoogleMap googleMap) {

        return googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);*/

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

    }


    public void get_shop_locations(String token,MarkerOptions options,GoogleMap googleMap,LatLng origin)
    {
        distanceModelClasses.clear();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.SHOPS,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        // Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                        //    Toast.makeText(UpdateUserProfile.this, response, Toast.LENGTH_SHORT).show();
                        //  Log.d("HTTP_AUTHORIZATION",token);

                        Log.d("response_shops",response);

                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");



                            //    Toast.makeText(getContext(), user_exist_check, Toast.LENGTH_SHORT).show();

                            if(message.equals("success")) {



                                JSONArray data  = jsonObj.getJSONArray("response");

                                for (int i = 0; i < data.length(); i++) {

                                    JSONObject c = data.getJSONObject(i);

                                    String id = c.getString("id");
                                    String longitued = c.getString("shop_long");
                                    String latitued = c.getString("shop_lat");
                                    String shop_name = c.getString("shop_name");
                                    String shop_phone = c.getString("shop_phone");
                                    String shop_image = c.getString("shop_image").replace("\\/", "/");


                                    //Toast.makeText(getActivity(), shop_image, Toast.LENGTH_SHORT).show();

                                    if(!longitued.equals("null") && !latitued.equals("null")) {

                                        float longi = Float.valueOf(longitued);
                                        float lati = Float.valueOf(latitued);

                                        LatLng latLng = new LatLng(lati, longi);
                                        Log.d("latlngs",String.valueOf(latLng));

                                        float val = caldirectdistance(origin,latLng);
                                        DistanceModelClass distanceModelClassvar = new DistanceModelClass(id,shop_name,origin,latLng,val,shop_image);
                                        distanceModelClasses.add(distanceModelClassvar);

                                        // creategeofence(id,latLng,500);



                                        AddLangLatList addLangLatList = new AddLangLatList(id,shop_name,shop_phone,origin,latLng);
                                        addLangLatLists.add(addLangLatList);

                                        latlngs.add(latLng);
                                       /* markerForGeofence(latLng);
                                        startGeofence();
                                        drawGeofence(latLng);*/
                                    }
                                }

                                if(distanceModelClasses.isEmpty())
                                {
                                    Toast.makeText(getContext(), "NO data", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Collections.sort(distanceModelClasses, Comparator.comparing(DistanceModelClass::getDistance));
                                    int count = 0;
                                    for(DistanceModelClass counter: distanceModelClasses){
                                        // System.out.println(counter);
                                        //   Log.d("sorteddirection",String.valueOf(counter.getArea()));
                                        if(count<5)
                                        {
                                            MapFragmentRecyclerList mapdatalist = new MapFragmentRecyclerList(R.drawable.caltex,counter.getTitle(),counter.getShopemail(),counter.getOrigin(),counter.getArea(),counter.getImageurl());
                                            mapFragmentRecyclerLists.add(mapdatalist);
                                        }

                                        count++;
                                    }
                                }

                                for (LatLng point : latlngs) {
                                    options.position(point);
                                    options.title("Oil Change Shops");
                                    options.snippet("someDesc");
                                    googleMap.addMarker(options);


                                }

                                // getGeofencingRequest();


                                MapFragmentRecyclerAdapter adapter = new MapFragmentRecyclerAdapter(mapFragmentRecyclerLists,getActivity(),mMap);
                                mapfragmentRV.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                                mapfragmentRV.setLayoutManager(linearLayoutManager);
                                //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                mapfragmentRV.setAdapter(adapter);


                            }
                            else
                            {
                                // Toast.makeText(getActivity(), "No location Marked", Toast.LENGTH_SHORT).show();
                            }

                        } catch (final JSONException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    /*Toast.makeText(getContext(),
                                            "Json parsing error: " + e.getMessage(),
                                            Toast.LENGTH_LONG).show();*/
                                }
                            });
                        }
//                      Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();


                        //      Toast.makeText(Signin.this, response, Toast.LENGTH_SHORT).show();


                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        //   progressDialog.dismiss();
                        progressDialog.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError)
                        {
                              Toast.makeText(getActivity(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();

                        } else if (error instanceof AuthFailureError) {
                            //TODO
                            //   Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            //        Toast.makeText(getActivity(), R.string.usernotfound, Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            //TODO
                            Toast.makeText(getActivity(), getResources().getString(R.string.servermaintainence), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NetworkError) {
                            //TODO
                            Toast.makeText(getActivity(), getResources().getString(R.string.networkerror), Toast.LENGTH_SHORT).show();

                        } else if (error instanceof ParseError) {
                            //TODO
                            Toast.makeText(getActivity(), getResources().getString(R.string.incorrectdata), Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {


        /*
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("HTTP_AUTHORIZATION", "Bearer " + token);
                return headers;
            }*/

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }

        };
        int socketTimeout = 10000; // 10 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);



    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();



    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
       /* mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);*/
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        /*     if (mLastLocation != null) {
         *//*    lat = mLastLocation.getLatitude();
            lng = mLastLocation.getLongitude();*//*

        }*/

        if(mLastLocation!=null)
        {
            LatLng lat = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
            origin = lat;
        }
        else
        {
            statusCheck();
        }

        if (getActivity() != null) {

            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        }



    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onLocationChanged(Location location)
    {

        double val1 = 31.4469;
        double val2 = 74.2682;

        mLastLocation = location;
        if (mCurrLocationMarker != null)
        {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mCurrLocationMarker = mMap.addMarker(markerOptions);
        origin = latLng;
        options = new MarkerOptions();
        if (getActivity() != null)
        {
            get_shop_locations(token, options, map, origin);
        }
        /*if(getlocation_flag==false) {
            getlocation_flag=true;
            get_shop_locations(token, options, map, origin);

        }
        else
        {
            get_shop_locations(token, options, map, origin);
        }*/
        // Toast.makeText(getContext(), String.valueOf(distanceModelClasses.size()), Toast.LENGTH_SHORT).show();



        //  LatLng latlng = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
       /* ArrayList<Float> distance = caculatedirectdistance(origin,latlngs);
        Collections.sort(distance);*/

     /*   for(float counter: distance){
            // System.out.println(counter);
            Log.d("sorteddirectionarray",String.valueOf(counter/ 1000));
        }

        int count = 0;
        while (distance.size() > count) {
            System.out.println(distance.get(count));
            count++;
        }*/
        //double res = distance(location.getLatitude(),location.getLongitude(),val1,val2);
        //  double res = getKmFromLatLong(location.getLatitude(),location.getLongitude(),val1,val2);
      /*  if(getActivity()!=null)
        {
           // String res = getDistance(location.getLatitude(),location.getLongitude(),val1,val2);
        //    Toast.makeText(getContext(), String.valueOf(res), Toast.LENGTH_SHORT).show();
        }*/


        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap)
        {
            mMap = googleMap;
            map = googleMap;

            mLocationRequest = LocationRequest.create();
            mLocationRequest.setInterval(1000);
            mLocationRequest.setFastestInterval(1000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            latlngs = new ArrayList<>();
            MarkerOptions options = new MarkerOptions();
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
            {
                @Override
                public boolean onMarkerClick(Marker marker)
                {


             /*       Toast.makeText(getContext(), String.valueOf(marker.getPosition().longitude), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), String.valueOf(marker.getPosition().latitude), Toast.LENGTH_SHORT).show();*/


                    GPSTracker gps = new GPSTracker(getContext());

                    // Check if GPS enabled
                    if(gps.canGetLocation()) {

                   /*     Uri navigationIntentUri = Uri.parse("google.navigation:q=" + 12f +"," + 2f);//creating intent with latlng
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);*/


                        Uri navigationIntentUri = Uri.parse("google.navigation:q=" + marker.getPosition().latitude +"," + marker.getPosition().longitude);//creating intent with latlng
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);



                     /*   double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();

                        LatLng origin = new LatLng(latitude,longitude);
                        LatLng destination = new LatLng(marker.getPosition().latitude,marker.getPosition().longitude);



                        new FetchURL(getContext()).execute(getUrl(origin, destination, "driving"), "driving");*/
                        // \n is for new line
                        //   Toast.makeText(getContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                    } else {
                        // Can't get location.
                        // GPS or network is not enabled.
                        // Ask user to enable GPS/network in settings.
                        gps.showSettingsAlert();
                    }

                    return false;
                }
            });



            buildGoogleApiClient();
            mGoogleApiClient.connect();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED)
                {
                    googleMap.setMyLocationEnabled(true);
                }
            }
            else
            {
                googleMap.setMyLocationEnabled(true);
            }

            //directdistancearray.add(distance);

            //getGeofencingRequest();

           /* LatLng origion = new LatLng(31.475782,74.279018);
            LatLng destination = new LatLng(31.484249,74.326139);*/

            //    new FetchURL((Activity) getContext()).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
   /*         LatLng me = new LatLng(31.47582964, -285.72093344);
            googleMap.addMarker(new MarkerOptions().position(me).title("Me"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(me));

            latlngs = new ArrayList<>();
            MarkerOptions options = new MarkerOptions();

            get_shop_locations(token,options,googleMap);

            *//*latlngs.add(new LatLng(31.47638963, -285.72006226));
            latlngs.add(new LatLng(31.47939632, -285.71980476));
            latlngs.add(new LatLng(31.47693497, -285.71585226));
            latlngs.add(new LatLng(31.47307356, -285.71337604));*//*

            float zoomLevel = 16.0f; //This goes up to 21
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(me, zoomLevel));*/


        }
    };


  /*  @Override
    public void onMapReady(GoogleMap googleMap) {

        latlngs = new ArrayList<>();
        MarkerOptions options = new MarkerOptions();

        get_shop_locations(token, options, googleMap);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                googleMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            googleMap.setMyLocationEnabled(true);
        }
            *//*latlngs.add(new LatLng(31.47638963, -285.72006226));
            latlngs.add(new LatLng(31.47939632, -285.71980476));
            latlngs.add(new LatLng(31.47693497, -285.71585226));
            latlngs.add(new LatLng(31.47307356, -285.71337604));*//*



     *//*       float zoomLevel = 16.0f; //This goes up to 21
            LatLng me = new LatLng(31.47582964, -285.72093344);
            googleMap.addMarker(new MarkerOptions().position(me).title("Me"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(me));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(me, zoomLevel));*//*


    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults)
    {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                        mMap.setMyLocationEnabled(true);

                        //Request location updates:
                        //  locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }

                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.permissiondenied), Toast.LENGTH_SHORT).show();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }}}

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    public String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getContext().getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }


    public String getDistance(final double lat1, final double lon1, final double lat2, final double lon2){

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    // URL url = new URL("https://maps.googleapis.com/maps/api/directions/json?origin=" + lat1 + "," + lon1 + "&destination=" + lat2 + "," + lon2 + "&sensor=false&units=metric&mode=driving");
                    // URL url = new URL("http://maps.googleapis.com/maps/api/distancematrix/json?origins" + lat1 + "," + lon1 + "&destinations=" + lat2 + "," + lon2 + "&mode=driving&language=en-EN&sensor=false");
                    String str_origin = "origin=" + lat1 + "," + lon1;
                    // Destination of route
                    String str_dest = "destination=" + lat2 + "," + lon2;

                    // Sensor enabled
                    String sensor = "sensor=true";
                    String mode = "mode=driving";
                    String key = "key="+getResources().getString(R.string.google_maps_key);
                    // Building the parameters to the web service
                    String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode + "&" + key;

                    // Output format
                    String output_json = "json";

                    // Building the url to the web service
                    String url2 = "https://maps.googleapis.com/maps/api/directions/" + output_json + "?" + parameters;
                    URL url = new URL(url2);
                    final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    final StringBuilder output = new StringBuilder();
                    // response = IOUtils.toString(in, "UTF-8");
                    Reader streamReader = new InputStreamReader(in, StandardCharsets.UTF_8);
                    int character = 0;
                    while ((character = streamReader.read(buffer, 0, buffer.length)) > 0) {
                        output.append(buffer, 0, character);
                    }
                    //     Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                    response = output.toString();
                    Log.d("success",response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("routes");
                    JSONObject routes = array.getJSONObject(0);
                    JSONArray legs = routes.getJSONArray("legs");
                    JSONObject steps = legs.getJSONObject(0);
                    JSONObject distance = steps.getJSONObject("distance");
                    parsedDistance=distance.getString("text");

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.d("error",e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("ioerror",e.getMessage());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("error",e.getMessage());
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return parsedDistance;
    }


//    private void markerForGeofence(LatLng latLng)
//    {
//        Log.i(TAG, "markerForGeofence("+latLng+")");
//        String title = latLng.latitude + ", " + latLng.longitude;
//        // Define marker options
//        MarkerOptions markerOptions = new MarkerOptions()
//                .position(latLng)
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
//                .title(title);
//
//        geoFenceMarker = map.addMarker(markerOptions);
//
//
//    }

    // Start Geofence creation process
//    private void startGeofence() {
//        Log.i("start", "startGeofence()");
//        if( geoFenceMarker != null ) {
//            Geofence geofence = createGeofence( geoFenceMarker.getPosition(), GEOFENCE_RADIUS );
//            GeofencingRequest geofenceRequest = createGeofenceRequest( geofence );
//            addGeofence( geofenceRequest );
//        } else {
//            Log.e(TAG, "Geofence marker is null");
//        }
//    }

//    private void addGeofence(GeofencingRequest request) {
//        Log.d("addgeo", "addGeofence");
//        if (checkPermission())
//            Log.d("permissiongranted","permission granted");
//        LocationServices.GeofencingApi.addGeofences(
//                mGoogleApiClient,
//                request,
//                createGeofencePendingIntent()
//        );
//    }

//    private static final long GEO_DURATION = 60 * 60 * 1000;
//    private static final String GEOFENCE_REQ_ID = "My Geofence";
//    private static final float GEOFENCE_RADIUS = 500.0f; // in meters

    // Create a Geofence
//    private Geofence createGeofence( LatLng latLng, float radius ) {
//        Log.d(TAG, "createGeofence");
//        return new Geofence.Builder()
//                .setRequestId(GEOFENCE_REQ_ID)
//                .setCircularRegion( latLng.latitude, latLng.longitude, radius)
//                .setExpirationDuration( GEO_DURATION )
//                .setTransitionTypes( Geofence.GEOFENCE_TRANSITION_ENTER
//                        | Geofence.GEOFENCE_TRANSITION_EXIT )
//                .build();
//    }

    // Create a Geofence Request
//    private GeofencingRequest createGeofenceRequest( Geofence geofence ) {
//        Log.d(TAG, "createGeofenceRequest");
//        return new GeofencingRequest.Builder()
//                .setInitialTrigger( GeofencingRequest.INITIAL_TRIGGER_ENTER )
//                .addGeofence( geofence )
//                .build();
//    }

 /*   private void addGeofence(GeofencingRequest request) {
        Log.d(TAG, "addGeofence");
        if (checkPermission())
            LocationServices.GeofencingApi.addGeofences(
                    googleApiClient,
                    request,
                    createGeofencePendingIntent()
            ).setResultCallback(this);
    }*/

//    private Circle geoFenceLimits;
//    private void drawGeofence(LatLng latLng) {
//        Log.d(TAG, "drawGeofence()");
//
///*        if ( geoFenceLimits != null )
//            geoFenceLimits.remove();*/
//
//        CircleOptions circleOptions = new CircleOptions()
//                .center( latLng)
//                .strokeColor(Color.argb(100, 150,150,150))
//                .fillColor( Color.argb(100, 150,150,150) )
//                .radius( GEOFENCE_RADIUS );
//        geoFenceLimits = map.addCircle( circleOptions );
//    }

//    private PendingIntent geoFencePendingIntent;
//    private final int GEOFENCE_REQ_CODE = 0;
//    private PendingIntent createGeofencePendingIntent()
//    {
//        Log.d(TAG, "createGeofencePendingIntent");
//        if ( geoFencePendingIntent != null ) {
//            Log.d("notnull","Not NUll");
//            return geoFencePendingIntent;
//        }
//        Intent intent = new Intent( getContext(), GeofenceTrasitionService.class);
//        return PendingIntent.getService(
//                getContext(), GEOFENCE_REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT );
//    }

//    private static final String NOTIFICATION_MSG = "NOTIFICATION MSG";
//    // Create a Intent send by the notification
//    public static Intent makeNotificationIntent(Context context, String msg)
//    {
//        Intent intent = new Intent( context, HomeScreen.class);
//        intent.putExtra( NOTIFICATION_MSG, msg );
//        return intent;
//    }

    @Override
    public void onResult(@NonNull Status status) {
        Log.i(TAG, "onResult: " + status);
        if ( status.isSuccess() ) {
            //  saveGeofence();
            //  drawGeofence();
        } else {
            // inform about fail
        }
    }


//    private final int REQ_PERMISSION = 999;
//
//    // Check for permission to access Location
//    private boolean checkPermission()
//    {
//        Log.d(TAG, "checkPermission()");
//        // Ask for permission if it wasn't granted yet
//        return (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED );
//    }
//
//    // Asks for permission
//    private void askPermission() {
//        Log.d(TAG, "askPermission()");
//        ActivityCompat.requestPermissions(
//                getActivity(),
//                new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
//                REQ_PERMISSION
//        );
//    }



//    public ArrayList<Float> caculatedirectdistance(LatLng origin,ArrayList<LatLng> destin)
//    {
//        ArrayList<Float> distt;
//        distt = new ArrayList<>();
//
//        center.setLatitude(origin.latitude);
//        center.setLongitude(origin.longitude);
//
//        for(LatLng counter: destin){
//            // System.out.println(counter);
//
//            test.setLatitude(counter.latitude);
//            test.setLongitude(counter.longitude);
//
//            distt.add(center.distanceTo(test));
//
//
//        }
//
//
//
//
//        return distt;
//    }

    public Float caldirectdistance(LatLng origin,LatLng destin)
    {

        float val;

        center.setLatitude(origin.latitude);
        center.setLongitude(origin.longitude);

        test.setLatitude(destin.latitude);
        test.setLongitude(destin.longitude);

        val = center.distanceTo(test);

        return val;
    }


    public void statusCheck() {
        final LocationManager manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id)
                    {
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                        {
                            return;
                        }
                        mLastClickTime = SystemClock.elapsedRealtime();

                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onDestroy()
    {
        GPSTracker gpsTracker = new GPSTracker(getContext());
        gpsTracker.stopUsingGPS();
        super.onDestroy();
    }

    @Override
    public void onPause()
    {
        GPSTracker gpsTracker = new GPSTracker(getContext());
        gpsTracker.stopUsingGPS();
        super.onPause();
    }
}