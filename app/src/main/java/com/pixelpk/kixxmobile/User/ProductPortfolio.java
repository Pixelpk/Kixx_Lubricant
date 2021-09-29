package com.pixelpk.kixxmobile.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.User.AddCar.AddCarScreen;
import com.pixelpk.kixxmobile.User.ModelClasses.AddCarList;
import com.pixelpk.kixxmobile.User.ModelClasses.ProductPortfolioList;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
import com.pixelpk.kixxmobile.User.adapters.AddCarAdapter;
import com.pixelpk.kixxmobile.User.adapters.ProductPortfolioAdapter;

import java.util.Arrays;
import java.util.List;

public class ProductPortfolio extends AppCompatActivity
{
    RecyclerView ProductPortfolio_productlist_RV;
    ImageView Productporfolio_backarrow;
    List<ProductPortfolioList> myListData;

    LinearLayout Productportfolio_backarrow_LL;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    //Handle Button Clicks
    private long mLastClickTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_portfolio);

        ProductPortfolio_productlist_RV = findViewById(R.id.ProductPortfolio_productlist_RV);
        Productporfolio_backarrow = findViewById(R.id.Productporfolio_backarrow);
        Productportfolio_backarrow_LL = findViewById(R.id.Productportfolio_backarrow_LL);

        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

        if(rtl.equals("1"))
        {
            Productporfolio_backarrow.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24_rwhite);
        }

        Productportfolio_backarrow_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000)
                {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                finish();
            }
        });
         myListData = Arrays.asList(new ProductPortfolioList[]{
                 new ProductPortfolioList("Kixx G1 SN PLUS 10W-30", "1 Litre", R.drawable.can1,"SR 379"),
                 new ProductPortfolioList("Kixx G1 SN PLUS 10W-40", "1 Litre", R.drawable.can2/*products02*/,"SR 349"),
                 new ProductPortfolioList("Kixx G1 SN PLUS 20W-50", "1 Litre", R.drawable.can3/*products03*/,"SR 379"),
                 new ProductPortfolioList("Kixx G1 SN PLUS 5W-20", "1 Litre", R.drawable.can4,"SR 329"),
                 new ProductPortfolioList("Kixx G1 SN PLUS 5W-30", "1 Litre", R.drawable.can5,"SR 311"),
                 new ProductPortfolioList("Kixx G1 SN PLUS 5W-40", "1 Litre", R.drawable.can_6,"SR 379"),
                 new ProductPortfolioList("Kixx CVTF", "1 Litre", R.drawable.can7,"SR 372"),
                 new ProductPortfolioList("Kixx ATF DX-III", "1 Litre", R.drawable.can8,"SR 309"),
                 new ProductPortfolioList("Kixx ATF DX-VI", "1 Litre", R.drawable.can9,"SR 554"),
                 new ProductPortfolioList("Kixx HD1 CI-4/E7 15W-40 (1L)", "1 Litre", R.drawable.can10,"SR 579"),
                 new ProductPortfolioList("Kixx HD1 CI-4/E7 15W-40 (4L)", "4 Litre", R.drawable.can_11,"SR 670"),
                 new ProductPortfolioList("Kixx HD1 CI-4/E7 20W-50", "4 Litre", R.drawable.can_12,"SR 749"),
                 new ProductPortfolioList("Kixx Geartec GL-5 80W-90", "4 Litre", R.drawable.can_13,"SR 769"),
                 new ProductPortfolioList("Kixx Geartec GL-5 85W-140", "4 Litre", R.drawable.can_14,"SR 769"),
                 new ProductPortfolioList(getResources().getString(R.string.products16_name), getResources().getString(R.string.products16_unit), R.drawable.can15,"SR 769"),
                 new ProductPortfolioList(getResources().getString(R.string.products17_name), getResources().getString(R.string.products17_unit), R.drawable.can16,"SR 769"),
                 new ProductPortfolioList(getResources().getString(R.string.products18_name), getResources().getString(R.string.products18_unit), R.drawable.can17,"SR 769"),
                 new ProductPortfolioList(getResources().getString(R.string.products19_name), getResources().getString(R.string.products19_unit), R.drawable.can18,"SR 769"),
                 new ProductPortfolioList(getResources().getString(R.string.products20_name), getResources().getString(R.string.products20_unit), R.drawable.can19,"SR 769"),
                 new ProductPortfolioList(getResources().getString(R.string.products21_name), getResources().getString(R.string.products21_unit), R.drawable.can20,"SR 769"),



         });


        ProductPortfolioAdapter adapter = new ProductPortfolioAdapter(myListData, ProductPortfolio.this);
        ProductPortfolio_productlist_RV.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductPortfolio.this);
        ProductPortfolio_productlist_RV.setLayoutManager(linearLayoutManager);
        //    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ProductPortfolio_productlist_RV.setAdapter(adapter);


    }


}