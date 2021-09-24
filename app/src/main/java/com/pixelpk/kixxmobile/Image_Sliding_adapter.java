package com.pixelpk.kixxmobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.pixelpk.kixxmobile.Salesman.Fragments.Sales_PromoFragment;
import com.pixelpk.kixxmobile.Salesman.ModelClasses.ClaimsList;
import com.pixelpk.kixxmobile.User.Fragments.PromoFragment;
import com.pixelpk.kixxmobile.User.ModelClasses.ImageSliderList;
import com.pixelpk.kixxmobile.User.adapters.ImageSlidingAdapter;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class Image_Sliding_adapter extends SliderViewAdapter<Image_Sliding_adapter.SliderAdapterVH> {

    public List<ImageSliderList> listdata;
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Fragment selectedFragment = null;
    String banner_type;
    String user_type;

    private boolean doNotifyDataSetChangedOnce = false;

    public Image_Sliding_adapter(List<ImageSliderList> listdata, Context context,String banner_type,String user_type )
    {
        this.listdata = listdata;
        this.context = context;
        this.banner_type = banner_type;
        this.user_type = user_type;
    }

    public void renewItems(List<ImageSliderList> listdata) {
        this.listdata = listdata;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.listdata.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(ImageSliderList ImageSliderList) {
        this.listdata.add(ImageSliderList);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.image_slider_layout, parent, false);
        Image_Sliding_adapter.SliderAdapterVH viewHolder = new Image_Sliding_adapter.SliderAdapterVH(listItem);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        ImageSliderList myListData = listdata.get(position);

        sharedPreferences = context.getSharedPreferences("Shared",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Glide.with(context)
                .load(myListData.getImage())
                .fitCenter()
                .into(viewHolder.ImageSliderLayout_IV);

        viewHolder.ImageSliderLayout_LL.setOnClickListener(new View.OnClickListener() {
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

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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
    public int getCount()
    {
        //slider view count could be dynamic size
        return listdata.size();

    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        public LinearLayout ImageSliderLayout_LL;
        public ImageView ImageSliderLayout_IV;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            this.ImageSliderLayout_LL = (LinearLayout) itemView.findViewById(R.id.Promos_promoLL);
            this.ImageSliderLayout_IV = (ImageView) itemView.findViewById(R.id.Promos_promoIV);
        }
    }

}
