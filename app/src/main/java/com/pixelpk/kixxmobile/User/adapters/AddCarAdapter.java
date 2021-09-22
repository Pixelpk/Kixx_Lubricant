package com.pixelpk.kixxmobile.User.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.URLs;
import com.pixelpk.kixxmobile.User.AddCar.AddCarScreen;
import com.pixelpk.kixxmobile.User.EditCarInfo;
import com.pixelpk.kixxmobile.User.HomeScreen;
import com.pixelpk.kixxmobile.User.ModelClasses.AddCarList;
import com.pixelpk.kixxmobile.User.ModelClasses.CarDetailsList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCarAdapter extends RecyclerView.Adapter<AddCarAdapter.ViewHolder>{
    private List<AddCarList> listdata;
    Context context;
    String token;
    String uid;
    String car_id;
    RecyclerView recyclerView;

    // RecyclerView recyclerView;
    public AddCarAdapter(List<AddCarList> listdata, Context context,String token,String uid,RecyclerView recyclerView) {
        this.listdata = listdata;
        this.context = context;
        this.token = token;
        this.uid = uid;
        this.recyclerView = recyclerView;
    }
    @Override
    public AddCarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.car_list_item, parent, false);
        AddCarAdapter.ViewHolder viewHolder = new AddCarAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AddCarAdapter.ViewHolder holder, int position)
    {
        final AddCarList myListData = listdata.get(position);

        holder.AddCar_CarNumber_TV.setText(myListData.getCar_Number());
        holder.AddCar_CarBrand_TV.setText(myListData.getCar_Manufacturer()/*+ " " + myListData.getCar_Brand()*/);
        if(!myListData.getYear_of_manufacture().equals("0"))
        {
            holder.AddCar_CarModel_TV.setText(myListData.getCar_Model() + " / " + myListData.getYear_of_manufacture());
        }
        else
        {
            holder.AddCar_CarModel_TV.setText(myListData.getCar_Model());
        }
        car_id = myListData.getCar_id();

        holder.AddCar_editcar_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, EditCarInfo.class);
                intent.putExtra("Carnumber",myListData.getCar_Number());
                intent.putExtra("CarId",myListData.getCar_id());
                intent.putExtra("Carmanufacturer",myListData.getCar_Manufacturer());
                intent.putExtra("Carbrand",myListData.getCar_Brand());
                intent.putExtra("CarModel",myListData.getCar_Model());
                intent.putExtra("year_of_manufacture",myListData.getYear_of_manufacture());
                intent.putExtra("odometer",myListData.getOdometer());
                intent.putExtra("enginetype",myListData.getEngine_type());
                intent.putExtra("dailymileage",myListData.getDaily_mileage());
                intent.putExtra("cid",myListData.getCid());
                intent.putExtra("intent_val","1");


               /* Toast.makeText(context, myListData.getCar_Brand(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, myListData.getCid(), Toast.LENGTH_SHORT).show();*/

                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });

        holder.Addcar_carlist_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context)
                        .setMessage(context.getResources().getString(R.string.deletecar))
                        .setCancelable(false)
                        .setPositiveButton(context.getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deletecar(uid,car_id,token);
                            }
                        })
                        .setNegativeButton(context.getResources().getString(R.string.cancel), null)
                        .show();

            }
        });

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

      //  public LinearLayout notification_layout;
        public TextView AddCar_CarNumber_TV, AddCar_CarBrand_TV,AddCar_CarModel_TV;
        public LinearLayout Addcar_carlist_LL,AddCar_editcar_LL;
        public ViewHolder(View itemView) {
            super(itemView);

            this.AddCar_CarNumber_TV = (TextView) itemView.findViewById(R.id.AddCar_CarNumber_TV);
            this.AddCar_CarBrand_TV = (TextView) itemView.findViewById(R.id.AddCar_CarBrand_TV);
            this.AddCar_CarModel_TV = (TextView) itemView.findViewById(R.id.AddCar_CarModel_TV);
            this.Addcar_carlist_LL = (LinearLayout) itemView.findViewById(R.id.Addcar_carlist_LL);
            this.AddCar_editcar_LL = (LinearLayout) itemView.findViewById(R.id.AddCar_editcar_LL);
        //    this.notification_layout = (LinearLayout) itemView.findViewById(R.id.notification);

        }
    }



    private void deletecar(String uid,String car_id,String token) {

        /*Toast.makeText(context, uid, Toast.LENGTH_SHORT).show();
        Toast.makeText(context, car_id, Toast.LENGTH_SHORT).show();
        Toast.makeText(context, token, Toast.LENGTH_SHORT).show();*/

         StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.Delete_CARS_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");


                            if (message.contains("success"))
                            {
                                //  Toast.makeText(context, "Car deleted successfully", Toast.LENGTH_SHORT).show();

                                getCarsData();

                             /*   new AlertDialog.Builder(context)
                                        .setMessage(context.getResources().getString(R.string.cardeletedsuccessfully))
                                        .setCancelable(false)
                                        .setNegativeButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which)
                                            {

                                            }
                                        })
                                        .show();*/
                            }
                            else
                            {
                                new AlertDialog.Builder(context)
                                      //  .setMessage("Cannot delete! Activity exists for the selected car")
                                        .setMessage(context.getResources().getString(R.string.cannotdeletecar))
                                        .setCancelable(false)
                                        .setNegativeButton(context.getResources().getString(R.string.ok), null)
                                        .show();
                            }

                        }catch (final JSONException e) {

                                 //   progressDialog.dismiss();
                                    Toast.makeText(context,
                                            "Json parsing error: " + e.getMessage(),
                                            Toast.LENGTH_LONG).show();

                        }
                    //          Toast.makeText(context, response, Toast.LENGTH_SHORT).show();


                    }
                },
                error -> {
                    //   progressDialog.dismiss();
                    Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("id",uid);
                parameters.put("car_id",car_id);

                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }




    private void getCarsData() {

        listdata.clear();
      //  progressDialog.show();
        //final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GET_CARS_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject jsonObj = new JSONObject(response);
                            String message = jsonObj.getString("status");


                            if(message.contains("success"))
                            {
                                JSONArray manufacturer  = jsonObj.getJSONArray("resp");

                                for (int i = 0; i < manufacturer.length(); i++) {

                                    JSONObject m = manufacturer.getJSONObject(i);

                                    String car_id = m.getString("id");
                                    String car_name = m.getString("car_number");
                                    String name = m.getString("name");
                                    String model = m.getString("model");
                                    String company = m.getString("company");
                                    String odometer = m.getString("odometer");
                                    String daily_mileage = m.getString("daily_mileage");
                                    String year_of_manufacture = m.getString("year_of_manufacture");
                                    String engine_type = m.getString("engine_type");
                                    String cid = m.getString("car_id");

                                    //Toast.makeText(AddCarScreen.this, car_name +" "+ company + " "+ name + " " + model, Toast.LENGTH_SHORT).show();

                                    //  new AddCarList("ABC 876","Jaguar","XF","2015")
                                    AddCarList promosList = new AddCarList(car_name,company,name,model,car_id,odometer,daily_mileage,year_of_manufacture,engine_type,cid);
                                    listdata.add(promosList);
                                }
                                AddCarAdapter adapter = new AddCarAdapter(listdata, context,token,uid,recyclerView);
                                recyclerView.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                adapter.notifyDataSetChanged();
                                //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setAdapter(adapter);

                            }
                            else
                            {
                                notifyDataSetChanged();
                                alerbox();
                                // Toast.makeText(AddCarScreen.this, getResources().getString(R.string.no_data_error), Toast.LENGTH_SHORT).show();
                                //  progressDialog.dismiss();
                            }

                        } catch (final JSONException e) {
                            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                   // progressDialog.dismiss();
                                    Toast.makeText(context,
                                            "Json parsing error: " + e.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        }


                        //      Toast.makeText(Signin.this, response, Toast.LENGTH_SHORT).show();


                    }
                },
                error -> {
                    //   progressDialog.dismiss();
                    Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("id",uid);

                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

    public void alerbox()
    {
        new AlertDialog.Builder(context)
                .setMessage(context.getResources().getString(R.string.nocaradded))
                .setCancelable(false)
                .setNegativeButton("Ok", null)
                .show();
    }

}


