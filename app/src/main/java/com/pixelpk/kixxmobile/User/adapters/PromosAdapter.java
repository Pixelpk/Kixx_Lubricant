package com.pixelpk.kixxmobile.User.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.User.ModelClasses.PromosList;
import com.pixelpk.kixxmobile.User.PromoDetailsScreen;

import java.util.List;

public class PromosAdapter extends RecyclerView.Adapter<PromosAdapter.ViewHolder>{
    private List<PromosList> listdata;
    Context context;
    String type;
    String user_type;

    //Handle Button Clicks
    private long mLastClickTime = 0;

    // RecyclerView recyclerView;
    public PromosAdapter(List<PromosList> listdata, Context context,String type,String user_type) {
        this.listdata = listdata;
        this.context = context;
        this.type = type;
        this.user_type = user_type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.promo_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PromosList myListData = listdata.get(position);



        Glide.with(context).load(myListData.getImageid()).into( holder.Promos_promoImage);
      //  holder.Promos_promoImage.setImageResource(myListData.getImageid());
        holder.notification_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                /*Intent intent = new Intent(context,PromoDetailsScreen.class);
                intent.putExtra("promo_id",myListData.getId());
                context.startActivity(intent);*/

                if(type.equals("ads"))
                {

                }
                else
                {
                    Intent intent = new Intent(context,PromoDetailsScreen.class);
                    intent.putExtra("promo_id",myListData.getId());
                    intent.putExtra("user_type",user_type);
                    context.startActivity(intent);
                }


            }
        });

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout notification_layout;
        public ImageView Promos_promoImage;

        public ViewHolder(View itemView) {
            super(itemView);
            this.Promos_promoImage = (ImageView) itemView.findViewById(R.id.Promos_promoIV);
            this.notification_layout = (LinearLayout) itemView.findViewById(R.id.Promos_promoLL);

        }
    }
}