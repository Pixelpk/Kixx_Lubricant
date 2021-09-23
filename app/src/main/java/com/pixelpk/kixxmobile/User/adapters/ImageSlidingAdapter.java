package com.pixelpk.kixxmobile.User.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.Salesman.Fragments.Sales_PromoFragment;
import com.pixelpk.kixxmobile.User.Fragments.HomeFragment;
import com.pixelpk.kixxmobile.User.Fragments.PromoFragment;
import com.pixelpk.kixxmobile.User.HomeScreen;
import com.pixelpk.kixxmobile.User.ModelClasses.ImageSliderList;
import com.pixelpk.kixxmobile.User.ModelClasses.MapFragmentRecyclerList;
import com.pixelpk.kixxmobile.User.PromoDetailsScreen;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;

import java.util.List;

public class ImageSlidingAdapter extends RecyclerView.Adapter<ImageSlidingAdapter.ViewHolder>{
    public List<ImageSliderList> listdata;
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Fragment selectedFragment = null;
    String banner_type;
    String user_type;

    // RecyclerView recyclerView;
    public ImageSlidingAdapter(List<ImageSliderList> listdata, Context context,String banner_type,String user_type ) {
        this.listdata = listdata;
        this.context = context;
        this.banner_type = banner_type;
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
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        ImageSliderList myListData = listdata.get(position);

        sharedPreferences = context.getSharedPreferences("Shared",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Glide.with(context).load(myListData.getImage()).into(holder.ImageSliderLayout_IV);

        holder.ImageSliderLayout_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent intent = new Intent(context, PromoDetailsScreen.class);
               // intent.putExtra("promo_id",myListData.getId());
                context.startActivity(intent);*/

                if (user_type.equals("user")) {

                    if (banner_type.equals("ads")) {
                   /* selectedFragment = new PromoFragment();
                    ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();*/

                        PromoFragment ldf = new PromoFragment();
                        Bundle args = new Bundle();
                        args.putString("promotion", "1");
                        ldf.setArguments(args);
//Inflate the fragment
                        ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, ldf).commit();
                    } else {
                        PromoFragment ldf = new PromoFragment();
                        Bundle args = new Bundle();
                        args.putString("promotion", "2");
                        ldf.setArguments(args);

//Inflate the fragment
                        ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, ldf).commit();
                    }
                }
                else
                {
                    if (banner_type.equals("ads")) {
                   /* selectedFragment = new PromoFragment();
                    ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();*/

                        Sales_PromoFragment ldf = new Sales_PromoFragment();
                        Bundle args = new Bundle();
                        args.putString("promotion", "1");
                        ldf.setArguments(args);
//Inflate the fragment
                        ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, ldf).commit();
                    } else {
                        Sales_PromoFragment ldf = new Sales_PromoFragment();
                        Bundle args = new Bundle();
                        args.putString("promotion", "2");
                        ldf.setArguments(args);

//Inflate the fragment
                        ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, ldf).commit();
                    }
                }

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
            this.ImageSliderLayout_LL = (LinearLayout) itemView.findViewById(R.id.Promos_promoLL);
            this.ImageSliderLayout_IV = (ImageView) itemView.findViewById(R.id.Promos_promoIV);

        }
    }
}