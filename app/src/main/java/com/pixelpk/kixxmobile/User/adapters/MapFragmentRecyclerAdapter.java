package com.pixelpk.kixxmobile.User.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.Fragments.MapsFragment;
import com.pixelpk.kixxmobile.User.ModelClasses.MapFragmentRecyclerList;
import com.pixelpk.kixxmobile.User.ModelClasses.PromosList;
import com.pixelpk.kixxmobile.User.PromoDetailsScreen;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
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
import java.util.List;

public class MapFragmentRecyclerAdapter extends RecyclerView.Adapter<MapFragmentRecyclerAdapter.ViewHolder> /*implements TaskLoadedCallback*/ {
    private List<MapFragmentRecyclerList> listdata;
    Context context;
    String longi,lati;
    MapsFragment mapsFragment;
    SharedPreferences sharedPreferences;
    final int bufferSize = 1024;
    final char[] buffer = new char[bufferSize];
    /*private MarkerOptions place1, place2;
    Button getDirection;
    private Polyline currentPolyline;
*/
    String parsedDistance;
    public GoogleMap mMap;
    String response;

    //Handle Button Clicks
    private long mLastClickTime = 0;

    // RecyclerView recyclerView;
    public MapFragmentRecyclerAdapter(List<MapFragmentRecyclerList> listdata, Context context,GoogleMap mMap) {
        this.listdata = listdata;
        this.context = context;
        this.mMap = mMap;
        mapsFragment = new MapsFragment();

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.mapfrag_recycleritem, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        sharedPreferences = context.getSharedPreferences("Shared",Context.MODE_PRIVATE);
        final MapFragmentRecyclerList myListData = listdata.get(position);
        String url = URLs.Kixx_ShopImage_URL + myListData.getImageurl();

        if(myListData.getImageurl().equals("null")) {
            holder.mapfrag_item_image.setImageResource(myListData.getImageid());
        }
        else
        {
            Glide.with(context).load(url).into(holder.mapfrag_item_image);
        }
        //holder.mapfrag_item_area.setText(myListData.getArea());
        holder.mapfrag_item_title.setText(myListData.getTitle());
        holder.mapfrag_item_area.setText(myListData.getArea());
        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

        //   Toast.makeText(context,rtl, Toast.LENGTH_SHORT).show();

        String dist = getDistance(myListData.getLatLng().latitude,myListData.getLatLng().longitude,myListData.getLatlngdestin().latitude,myListData.getLatlngdestin().longitude);
        holder.mapfrag_item_distancecal.setText(dist);

        if(rtl.equals("1"))
        {
            holder.mapfrag_item_area.setGravity(Gravity.END);
            holder.mapfrag_item_title.setGravity(Gravity.END);
            holder.mapfrag_item_distancecal.setGravity(Gravity.END);
            holder.oilchange_rightarrow_IV.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24forward);
          //  holder.mapfrag_item_open.setGravity(Gravity.END);

        }

        /*holder.shopnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               *//* if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE},1);
                }
                else
                {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+myListData.getArea()));
                    context.startActivity(intent);
                }*//*


            }
        });*/


        /*holder.mapfrag_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             *//*   String val  = myListData.getLatlngdestin().replace("(","");
                String val2 = val.replace(")","");
          //      Toast.makeText(context, val2, Toast.LENGTH_SHORT).show();
                String[] latlong =  val2.split(",");
                double latitude = Double.parseDouble(latlong[0]);
                double longitude = Double.parseDouble(latlong[1]);*//*

                LatLng me = myListData.getLatlngdestin();

                            Uri navigationIntentUri = Uri.parse("google.navigation:q=" + me.latitude +"," + me.longitude);//creating intent with latlng
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });*/



       /* holder.mapfrag_item_layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                LatLng me = myListData.getLatlngdestin();

                Uri navigationIntentUri = Uri.parse("google.navigation:q=" + me.latitude +"," + me.longitude);//creating intent with latlng
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);


            }
        });

        holder.oilchange_rightsection_LL.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                LatLng me = myListData.getLatlngdestin();

                Uri navigationIntentUri = Uri.parse("google.navigation:q=" + me.latitude +"," + me.longitude);//creating intent with latlng
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);


            }
        });*/

        holder.mapfrag_item_mainframe.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                LatLng me = myListData.getLatlngdestin();

                Uri navigationIntentUri = Uri.parse("google.navigation:q=" + me.latitude +"," + me.longitude);//creating intent with latlng
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });



        /*holder.mapfrag_item_gotogooglemap_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LatLng me = myListData.getLatlngdestin();

                Uri navigationIntentUri = Uri.parse("google.navigation:q=" + me.latitude +"," + me.longitude);//creating intent with latlng
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);

            }
        });*/

        /*holder.oilchange_leftsection_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LatLng me = myListData.getLatlngdestin();

                Uri navigationIntentUri = Uri.parse("google.navigation:q=" + me.latitude +"," + me.longitude);//creating intent with latlng
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);

            }
        });


        holder.oilchange_rightsection_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LatLng me = myListData.getLatlngdestin();

                Uri navigationIntentUri = Uri.parse("google.navigation:q=" + me.latitude +"," + me.longitude);//creating intent with latlng
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);

            }
        });*/
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout mapfrag_item_layout;
        public LinearLayout mapfrag_midsection_LL;
        public ImageView mapfrag_item_image;
        public TextView mapfrag_item_title;
        public TextView mapfrag_item_area;
      //  public TextView mapfrag_item_open;
     //   public TextView mapfrag_item_distance;
        public ImageView mapfrag_direction;
        public LinearLayout shopnumber,oilchange_rightsection_LL,oilchange_leftsection_LL;
        public ImageView oilchange_rightarrow_IV;
        public TextView mapfrag_item_gotogooglemap_tv;
        public TextView mapfrag_item_distancecal;
        public ConstraintLayout mapfrag_item_mainframe;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mapfrag_item_layout = (LinearLayout) itemView.findViewById(R.id.mapfrag_item_layout);
            this.mapfrag_midsection_LL = (LinearLayout) itemView.findViewById(R.id.mapfrag_midsection_LL);
            this.oilchange_rightsection_LL = (LinearLayout) itemView.findViewById(R.id.oilchange_rightsection_LL);
            this.oilchange_leftsection_LL = (LinearLayout) itemView.findViewById(R.id.oilchange_leftsection_LL);
            this.mapfrag_item_image = (ImageView) itemView.findViewById(R.id.mapfrag_item_image);
            this.mapfrag_item_title = (TextView) itemView.findViewById(R.id.mapfrag_item_title);
            this.mapfrag_item_area = (TextView) itemView.findViewById(R.id.mapfrag_item_area);
//            this.mapfrag_item_open = (TextView) itemView.findViewById(R.id.mapfrag_item_open);
          //  this.mapfrag_item_distance = (TextView) itemView.findViewById(R.id.mapfrag_item_distance);
            this.mapfrag_direction = (ImageView) itemView.findViewById(R.id.mapfrag_direction);
            this.shopnumber = (LinearLayout) itemView.findViewById(R.id.shopnumber);
            this.oilchange_rightarrow_IV = (ImageView) itemView.findViewById(R.id.oilchange_rightarrow_IV);
            this.mapfrag_item_gotogooglemap_tv = (TextView) itemView.findViewById(R.id.mapfrag_item_gotogooglemap_tv);
            this.mapfrag_item_distancecal = (TextView) itemView.findViewById(R.id.mapfrag_item_distancecal);
            this.mapfrag_item_mainframe = (ConstraintLayout) itemView.findViewById(R.id.mapfrag_item_mainframe);

        }
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
                    String key = "key="+ context.getResources().getString(R.string.google_maps_key);
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

  /*  public String getUrl(LatLng origin, LatLng dest, String directionMode) {
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
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + context.getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }*/
}