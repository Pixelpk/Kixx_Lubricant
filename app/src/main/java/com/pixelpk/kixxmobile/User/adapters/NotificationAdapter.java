package com.pixelpk.kixxmobile.User.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.User.ModelClasses.Notificationlist;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{
    private List<Notificationlist> listdata;
    Context context;
    SharedPreferences sharedPreferences;


    // RecyclerView recyclerView;
    public NotificationAdapter(List<Notificationlist> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.notification_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Notificationlist myListData = listdata.get(position);

        sharedPreferences = context.getSharedPreferences("Shared",Context.MODE_PRIVATE);

        holder.notification_time.setText(myListData.getNotification_time());
        holder.notification_desc.setText(myListData.getNotification_desc());
        holder.notification_title.setText(myListData.getNotification_title());

        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

     //   Toast.makeText(context,rtl, Toast.LENGTH_SHORT).show();

        if(rtl.equals("1"))
        {
            holder.notification_title.setGravity(Gravity.END);
            holder.notification_desc.setGravity(Gravity.END);
        }

        holder.notification_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context, "Notification Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout notification_layout;
        public TextView notification_time,notification_desc,notification_title;

        public ViewHolder(View itemView) {
            super(itemView);
            this.notification_time = (TextView) itemView.findViewById(R.id.notification_receive_time);
            this.notification_desc = (TextView) itemView.findViewById(R.id.notification_desc);
            this.notification_layout = (LinearLayout) itemView.findViewById(R.id.notification);
            this.notification_title = (TextView) itemView.findViewById(R.id.notification_title);

        }
    }
}