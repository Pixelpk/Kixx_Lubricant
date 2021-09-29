package com.pixelpk.kixxmobile.User.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.User.HomeScreen;
import com.pixelpk.kixxmobile.User.ModelClasses.ImageSliderList;

import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ViewHolder>{
    public List<ImageSliderList> listdata;
    Context context;

    //Handle Button Clicks
    private long mLastClickTime = 0;

    // RecyclerView recyclerView;
    public ImageSliderAdapter(List<ImageSliderList> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }
    @Override
    public ImageSliderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.imagesliderlayout, parent, false);
        ImageSliderAdapter.ViewHolder viewHolder = new ImageSliderAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageSliderAdapter.ViewHolder holder, int position) {
        ImageSliderList myListData = listdata.get(position);
       // Log.d("URL",myListData.getImage());
        Glide.with(context).load("https://news.kixxoil.com/wp-content/uploads/2020/09/1280x720.jpg").into(holder.ImageSliderLayout_IV);

        holder.ImageSliderLayout_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                Intent intent = new Intent(context, HomeScreen.class);
                intent.putExtra("promotion","2");
                context.startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout ImageSliderLayout_LL;
        public ImageView ImageSliderLayout_IV;

        public ViewHolder(View itemView) {
            super(itemView);
            this.ImageSliderLayout_LL = (LinearLayout) itemView.findViewById(R.id.ImageSliderLayout_LL);
            this.ImageSliderLayout_IV = (ImageView) itemView.findViewById(R.id.ImageSliderLayout_IV);

        }
    }
}