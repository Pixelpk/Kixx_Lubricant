package com.pixelpk.kixxmobile.User.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.User.ModelClasses.CarRecommendationlistModelClass;
import com.pixelpk.kixxmobile.User.ModelClasses.ProductPortfolioList;
import com.pixelpk.kixxmobile.User.ProductPortfolio_Detils;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;

import java.util.List;

public class OilRecommendationAdapter extends RecyclerView.Adapter<OilRecommendationAdapter.ViewHolder>
{
    private List<CarRecommendationlistModelClass> listdata;
    Context context;
    SharedPreferences sharedPreferences;

    //Handle Button Clicks
    private long mLastClickTime = 0;

    // RecyclerView recyclerView;
    public OilRecommendationAdapter(List<CarRecommendationlistModelClass> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;

    }

    @Override
    public OilRecommendationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.oilrecommendationitem, parent, false);
        OilRecommendationAdapter.ViewHolder viewHolder = new OilRecommendationAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OilRecommendationAdapter.ViewHolder holder, int position) {
        final CarRecommendationlistModelClass myListData = listdata.get(position);

        sharedPreferences = context.getSharedPreferences("Shared", Context.MODE_PRIVATE);

        holder.oilrecommend_products_IV.setBackgroundResource(myListData.getImage());
        holder.oilrecommend_title.setText(myListData.getName());
        holder.oilrecommend_desc.setText(myListData.getDistance());
     //   holder.oilrecommend_price.setText(myListData.getPrice());




        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

        if(rtl.equals("1"))
        {
            holder.back_btn.setImageResource(R.drawable.arrow_recycler_item);
        }


        //   Toast.makeText(context,rtl, Toast.LENGTH_SHORT).show();

 /*       if (rtl.equals("1")) {
            holder.notification_title.setGravity(Gravity.END);
            holder.notification_desc.setGravity(Gravity.END);
        }*/

        holder.oilrecommend_mainframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // Toast.makeText(context, "Notification Clicked", Toast.LENGTH_SHORT).show();

                String val = myListData.getId();
          //      Toast.makeText(context, val, Toast.LENGTH_SHORT).show();

                if(val.equals("1") || val.equals("4") || val.equals("6"))
                {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                    {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    Intent intent = new Intent(context,ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","17");
                    context.startActivity(intent);
                }
                else if(val.equals("3") || val.equals("5") || val.equals("7"))
                {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                    {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    Intent intent = new Intent(context,ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","20");
                    context.startActivity(intent);
                }
                else if(val.equals("10") || val.equals("14") || val.equals("16"))
                {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                    {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    Intent intent = new Intent(context,ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","5");
                    context.startActivity(intent);
                }
                else if(val.equals("8") || val.equals("13"))
                {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                    {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    Intent intent = new Intent(context,ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","15");
                    context.startActivity(intent);
                }
                else if(val.equals("11") || val.equals("15"))
                {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                    {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    Intent intent = new Intent(context,ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","19");
                    context.startActivity(intent);
                }
                else if(val.equals("9"))
                {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                    {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    Intent intent = new Intent(context,ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","4");
                    context.startActivity(intent);
                }
                else if(val.equals("12"))
                {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                    {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    Intent intent = new Intent(context,ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","6");
                    context.startActivity(intent);
                }
                else if(val.equals("17"))
                {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                    {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    Intent intent = new Intent(context,ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","16");
                    context.startActivity(intent);
                }
                else if(val.equals("2"))
                {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                    {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    Intent intent = new Intent(context,ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","18");
                    context.startActivity(intent);
                }
                else if(val.equals("18"))
                {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                    {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    Intent intent = new Intent(context,ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","10");
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

        public LinearLayout oilrecommend_mainframe;
        public ImageView oilrecommend_products_IV,back_btn;
        TextView oilrecommend_title, oilrecommend_desc,oilrecommend_price;

        public ViewHolder(View itemView) {
            super(itemView);
            this.oilrecommend_products_IV = (ImageView) itemView.findViewById(R.id.oilrecommend_products_IV);
            this.back_btn = (ImageView) itemView.findViewById(R.id.back_btn_product_item_ar);
            this.oilrecommend_title = (TextView) itemView.findViewById(R.id.oilrecommend_title);
            this.oilrecommend_desc = (TextView) itemView.findViewById(R.id.oilrecommend_desc);
            this.oilrecommend_mainframe = (LinearLayout) itemView.findViewById(R.id.oilrecommend_mainframe);
            this.oilrecommend_price = (TextView) itemView.findViewById(R.id.oilrecommend_price);

        }
    }
}