package com.pixelpk.kixxmobile.User.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.User.ModelClasses.CarDetailsList;
import com.pixelpk.kixxmobile.User.ModelClasses.Notificationlist;

import java.util.List;

public class CardDetailsAdapter extends RecyclerView.Adapter<CardDetailsAdapter.ViewHolder>{
    private List<CarDetailsList> listdata;
    Context context;



    // RecyclerView recyclerView;
    public CardDetailsAdapter(List<CarDetailsList> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }
    @Override
    public CardDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.cardetails, parent, false);
        CardDetailsAdapter.ViewHolder viewHolder = new CardDetailsAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CardDetailsAdapter.ViewHolder holder, int position) {
        final CarDetailsList myListData = listdata.get(position);

        holder.cardetails_date_TV.setText(myListData.getDate());
        holder.cardetails_odometer_TV.setText(myListData.getOdometer());
        holder.cardetails_nextodometer_TV.setText(myListData.getNext_odometer());
        holder.cardetails_nextdate_TV.setText(myListData.getNextdate());

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

      //  public LinearLayout notification_layout;
        public TextView cardetails_date_TV, cardetails_odometer_TV,cardetails_nextodometer_TV,cardetails_nextdate_TV;

        public ViewHolder(View itemView) {
            super(itemView);
            this.cardetails_date_TV = (TextView) itemView.findViewById(R.id.cardetails_date_TV);
            this.cardetails_odometer_TV = (TextView) itemView.findViewById(R.id.cardetails_odometer_TV);
            this.cardetails_nextodometer_TV = (TextView) itemView.findViewById(R.id.cardetails_nextodometer_TV);
            this.cardetails_nextdate_TV = (TextView) itemView.findViewById(R.id.cardetails_nextdate_TV);
        //    this.notification_layout = (LinearLayout) itemView.findViewById(R.id.notification);

        }
    }}