package com.pixelpk.kixxmobile.Salesman.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.Salesman.ModelClasses.ClaimsList;

import java.util.List;

public class claimAdapter extends RecyclerView.Adapter<claimAdapter.ViewHolder> {
    private List<ClaimsList> listdata;
    Context context;

    public claimAdapter(List<ClaimsList> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.claimedcars_item, parent, false);
        claimAdapter.ViewHolder viewHolder = new claimAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull claimAdapter.ViewHolder holder, int position) {

        final ClaimsList myListData = listdata.get(position);

        holder.Claimscreen_date.setText(myListData.getDate());
        holder.Claimscreen_product.setText(myListData.getProductName());
        holder.Claimscreen_carnum.setText(myListData.getCarnumber());

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

       public TextView Claimscreen_date,Claimscreen_product,Claimscreen_carnum;

        public ViewHolder(View itemView) {
            super(itemView);
            this.Claimscreen_date = (TextView) itemView.findViewById(R.id.Claimscreen_date);
            this.Claimscreen_product = (TextView) itemView.findViewById(R.id.Claimscreen_product);
            this.Claimscreen_carnum = (TextView) itemView.findViewById(R.id.Claimscreen_carnum);

        }
    }
}
