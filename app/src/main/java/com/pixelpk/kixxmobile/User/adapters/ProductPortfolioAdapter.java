package com.pixelpk.kixxmobile.User.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.User.ModelClasses.Notificationlist;
import com.pixelpk.kixxmobile.User.ModelClasses.ProductPortfolioList;
import com.pixelpk.kixxmobile.User.ProductPortfolio_Detils;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;

import java.util.List;

public class ProductPortfolioAdapter extends RecyclerView.Adapter<ProductPortfolioAdapter.ViewHolder> {
    private List<ProductPortfolioList> listdata;
    Context context;
    SharedPreferences sharedPreferences;
    ImageView back_btn;


    // RecyclerView recyclerView;
    public ProductPortfolioAdapter(List<ProductPortfolioList> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;

    }

    @Override
    public ProductPortfolioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.product_portfolio_item, parent, false);
        ProductPortfolioAdapter.ViewHolder viewHolder = new ProductPortfolioAdapter.ViewHolder(listItem);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProductPortfolioAdapter.ViewHolder holder, int position)
    {
        final ProductPortfolioList myListData = listdata.get(position);

        sharedPreferences = context.getSharedPreferences("Shared", Context.MODE_PRIVATE);

        holder.Productportfolio_products_IV.setBackgroundResource(myListData.getImage());
        holder.Productportfolio_title.setText(myListData.getTitle());
        holder.Productportfolio_desc.setText(myListData.getMessage());
        holder.Productportfolio_price.setText(myListData.getPrice());



        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

        if(rtl.equals("1"))
        {
            holder.arrow_img.setImageResource(R.drawable.arrow_recycler_item);
        }


        //   Toast.makeText(context,rtl, Toast.LENGTH_SHORT).show();

 /*       if (rtl.equals("1")) {
            holder.notification_title.setGravity(Gravity.END);
            holder.notification_desc.setGravity(Gravity.END);
        }*/

        holder.Productportfolio_mainframe.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Toast.makeText(context, "Notification Clicked", Toast.LENGTH_SHORT).show();

                if(position == 0)
                {
                    Intent intent = new Intent(context, ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","1");
                    context.startActivity(intent);
                }
                else if(position == 1)
                {
                    Intent intent = new Intent(context, ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","2");
                    context.startActivity(intent);
                }
                else if(position == 2)
                {
                    Intent intent = new Intent(context, ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","3");
                    context.startActivity(intent);
                }

                else if(position == 3)
                {
                    Intent intent = new Intent(context, ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","4");
                    context.startActivity(intent);

                }

                else if(position == 4)
                {
                    Intent intent = new Intent(context, ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","5");
                    context.startActivity(intent);
                }
                else if(position == 5)
                {
                    Intent intent = new Intent(context, ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","6");
                    context.startActivity(intent);
                }
                else if(position == 6)
                {
                    Intent intent = new Intent(context, ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","7");
                    context.startActivity(intent);
                }
                else if(position == 7)
                {
                    Intent intent = new Intent(context, ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","8");
                    context.startActivity(intent);
                }
                else if(position == 8)
                {
                    Intent intent = new Intent(context, ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","9");
                    context.startActivity(intent);
                }
                else if(position == 9)
                {
                    Intent intent = new Intent(context, ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","10");
                    context.startActivity(intent);
                }
                else if(position == 10)
                {
                    Intent intent = new Intent(context, ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","11");
                    context.startActivity(intent);

                }else if(position == 11)
                {
                    Intent intent = new Intent(context, ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","12");
                    context.startActivity(intent);

                }else if(position == 12)
                {
                    Intent intent = new Intent(context, ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","13");
                    context.startActivity(intent);

                }else if(position == 13)
                {
                    Intent intent = new Intent(context, ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","14");
                    context.startActivity(intent);
                }
                else if(position == 14)
                {
                    Intent intent = new Intent(context, ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","15");
                    context.startActivity(intent);
                }
                else if(position == 15)
                {
                    Intent intent = new Intent(context, ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","16");
                    context.startActivity(intent);
                }
                else if(position == 16)
                {
                    Intent intent = new Intent(context, ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","17");
                    context.startActivity(intent);
                }
                else if(position == 17)
                {
                    Intent intent = new Intent(context, ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","18");
                    context.startActivity(intent);
                }
                else if(position == 18)
                {
                    Intent intent = new Intent(context, ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","19");
                    context.startActivity(intent);
                }
                else if(position == 19)
                {
                    Intent intent = new Intent(context, ProductPortfolio_Detils.class);
                    intent.putExtra("products_numb","20");
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

        public LinearLayout Productportfolio_mainframe;
        public ImageView Productportfolio_products_IV,arrow_img;
        TextView Productportfolio_title, Productportfolio_desc,Productportfolio_price;

        public ViewHolder(View itemView)
        {
            super(itemView);
            this.Productportfolio_products_IV = (ImageView) itemView.findViewById(R.id.Productportfolio_products_IV);
            this.Productportfolio_title       = (TextView) itemView.findViewById(R.id.Productportfolio_title);
            this.arrow_img                    = (ImageView) itemView.findViewById(R.id.back_btn_product_item_en);
            this.Productportfolio_desc        = (TextView) itemView.findViewById(R.id.Productportfolio_desc);
            this.Productportfolio_mainframe   = (LinearLayout) itemView.findViewById(R.id.Productportfolio_mainframe);
            this.Productportfolio_price       = (TextView) itemView.findViewById(R.id.Productportfolio_price);

        }
    }
}