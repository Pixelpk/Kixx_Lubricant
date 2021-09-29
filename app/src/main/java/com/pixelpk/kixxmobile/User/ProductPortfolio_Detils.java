package com.pixelpk.kixxmobile.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;

public class ProductPortfolio_Detils extends AppCompatActivity {

    TextView title,type,model,barcode,unit,specification;
    Intent intent;
    ImageView ProductPortfolioDetails_backarrow;
    ImageView Productportfoliodetils_image;

    LinearLayout Productporfoliodetail_backarrow_LL;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    //Handle Button Clicks
    private long mLastClickTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_portfolio__detils);

        InitializeViews();

        Productporfoliodetail_backarrow_LL.setOnClickListener(new View.OnClickListener() {
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
    }

    private void InitializeViews() {

        ProductPortfolioDetails_backarrow = findViewById(R.id.ProductPortfolioDetails_backarrow);

        title = findViewById(R.id.ProductPortfolioDetails_title);
        type = findViewById(R.id.ProductPortfolioDetails_type);
        model = findViewById(R.id.ProductPortfolioDetails_model);
        barcode = findViewById(R.id.ProductPortfolioDetails_productcode);
        unit = findViewById(R.id.ProductPortfolioDetails_unit);
//        description = findViewById(R.id.ProductPortfolioDetails_description);
        specification = findViewById(R.id.ProductPortfolioDetails_specification);
        Productportfoliodetils_image = findViewById(R.id.Productportfoliodetils_image);

        intent = getIntent();
        String products_numb = intent.getStringExtra("products_numb");
        Productporfoliodetail_backarrow_LL = findViewById(R.id.Productporfoliodetail_backarrow_LL);
       // Toast.makeText(this, title_str, Toast.LENGTH_SHORT).show();
        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

        if(rtl.equals("1"))
        {
            ProductPortfolioDetails_backarrow.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24_rwhite);
        }

        if(products_numb.equals("1"))
        {
            title.setText(R.string.products01_name);
            type.setText(R.string.products01_type);
            model.setText(R.string.products01_model);
            barcode.setText(R.string.products01_barcode);
            unit.setText(R.string.products01_unit);
          //  description.setText(R.string.products01_description);
            specification.setText(R.string.products01_specification);
            Productportfoliodetils_image.setImageResource(R.drawable.can1);
        }
        else if(products_numb.equals("2"))
        {
            title.setText(R.string.products02_name);
            type.setText(R.string.products02_type);
            model.setText(R.string.products02_model);
            barcode.setText(R.string.products02_barcode);
            unit.setText(R.string.products02_unit);
         //   description.setText(R.string.products02_description);
            specification.setText(R.string.products02_specification);
            Productportfoliodetils_image.setImageResource(R.drawable.can2);
        }
        else if(products_numb.equals("3"))
        {
            title.setText(R.string.products03_name);
            type.setText(R.string.products03_type);
            model.setText(R.string.products03_model);
            barcode.setText(R.string.products03_barcode);
            unit.setText(R.string.products03_unit);
        //    description.setText(R.string.products03_description);
            specification.setText(R.string.products03_specification);
            Productportfoliodetils_image.setImageResource(R.drawable.can3);
        }else if(products_numb.equals("4"))
        {
            title.setText(R.string.products04_name);
            type.setText(R.string.products04_type);
            model.setText(R.string.products04_model);
            barcode.setText(R.string.products04_barcode);
            unit.setText(R.string.products04_unit);
        //    description.setText(R.string.products04_description);
            specification.setText(R.string.products04_specification);
            Productportfoliodetils_image.setImageResource(R.drawable.can4);
        }else if(products_numb.equals("5"))
        {
            title.setText(R.string.products05_name);
            type.setText(R.string.products05_type);
            model.setText(R.string.products05_model);
            barcode.setText(R.string.products05_barcode);
            unit.setText(R.string.products05_unit);
          //  description.setText(R.string.products05_description);
            specification.setText(R.string.products05_specification);
            Productportfoliodetils_image.setImageResource(R.drawable.can5);
        }else if(products_numb.equals("6"))
        {
            title.setText(R.string.products06_name);
            type.setText(R.string.products06_type);
            model.setText(R.string.products06_model);
            barcode.setText(R.string.products06_barcode);
            unit.setText(R.string.products06_unit);
        //    description.setText(R.string.products06_description);
            specification.setText(R.string.products06_specification);
            Productportfoliodetils_image.setImageResource(R.drawable.can_6);
        }else if(products_numb.equals("7"))
        {
            title.setText(R.string.products07_name);
            type.setText(R.string.products07_type);
            model.setText(R.string.products07_model);
            barcode.setText(R.string.products07_barcode);
            unit.setText(R.string.products07_unit);
          //  description.setText(R.string.products07_description);
            specification.setText(R.string.products07_specification);
            Productportfoliodetils_image.setImageResource(R.drawable.can7);
        }else if(products_numb.equals("8"))
        {
            title.setText(R.string.products08_name);
            type.setText(R.string.products08_type);
            model.setText(R.string.products08_model);
            barcode.setText(R.string.products08_barcode);
            unit.setText(R.string.products08_unit);
          //  description.setText(R.string.products08_description);
            specification.setText(R.string.products08_specification);
            Productportfoliodetils_image.setImageResource(R.drawable.can8);
        }else if(products_numb.equals("9"))
        {
            title.setText(R.string.products09_name);
            type.setText(R.string.products09_type);
            model.setText(R.string.products09_model);
            barcode.setText(R.string.products09_barcode);
            unit.setText(R.string.products09_unit);
          //  description.setText(R.string.products09_description);
            specification.setText(R.string.products09_specification);
            Productportfoliodetils_image.setImageResource(R.drawable.can9);
        }else if(products_numb.equals("10"))
        {
            title.setText(R.string.products10_name);
            type.setText(R.string.products10_type);
            model.setText(R.string.products10_model);
            barcode.setText(R.string.products10_barcode);
            unit.setText(R.string.products10_unit);
         //   description.setText(R.string.products10_description);
            specification.setText(R.string.products10_specification);
            Productportfoliodetils_image.setImageResource(R.drawable.can10);
        }/*else if(products_numb.equals("11"))
        {
            title.setText(R.string.products11_name);
            type.setText(R.string.products11_type);
            model.setText(R.string.products11_model);
            barcode.setText(R.string.products11_barcode);
            unit.setText(R.string.products11_unit);
         //   description.setText(R.string.products11_description);
            specification.setText(R.string.products11_specification);
            Productportfoliodetils_image.setImageResource(R.drawable.can1);
        }*/else if(products_numb.equals("11"))
        {
            title.setText(R.string.products12_name);
            type.setText(R.string.products12_type);
            model.setText(R.string.products12_model);
            barcode.setText(R.string.products12_barcode);
            unit.setText(R.string.products12_unit);
         //   description.setText(R.string.products12_description);
            specification.setText(R.string.products12_specification);
            Productportfoliodetils_image.setImageResource(R.drawable.can_11);
        }else if(products_numb.equals("12"))
        {
            title.setText(R.string.products13_name);
            type.setText(R.string.products13_type);
            model.setText(R.string.products13_model);
            barcode.setText(R.string.products13_barcode);
            unit.setText(R.string.products13_unit);
          //  description.setText(R.string.products13_description);
            specification.setText(R.string.products13_specification);
            Productportfoliodetils_image.setImageResource(R.drawable.can_12);
        }else if(products_numb.equals("13"))
        {
            title.setText(R.string.products14_name);
            type.setText(R.string.products14_type);
            model.setText(R.string.products14_model);
            barcode.setText(R.string.products14_barcode);
            unit.setText(R.string.products14_unit);
          //  description.setText(R.string.products14_description);
            specification.setText(R.string.products14_specification);
            Productportfoliodetils_image.setImageResource(R.drawable.can_13);
        }
        else if(products_numb.equals("14"))
        {
            title.setText(R.string.products15_name);
            type.setText(R.string.products15_type);
            model.setText(R.string.products15_model);
            barcode.setText(R.string.products15_barcode);
            unit.setText(R.string.products15_unit);
            //  description.setText(R.string.products14_description);
            specification.setText(R.string.products15_specification);
            Productportfoliodetils_image.setImageResource(R.drawable.can_14);
        }
        else if(products_numb.equals("15"))
        {
            title.setText(R.string.products16_name);
            type.setText(R.string.products16_type);
            model.setText(R.string.products16_model);
            barcode.setText(R.string.products16_barcode);
            unit.setText(R.string.products16_unit);
            //  description.setText(R.string.products14_description);
            specification.setText(R.string.products16_specification);
            Productportfoliodetils_image.setImageResource(R.drawable.can15);
        }
        else if(products_numb.equals("16"))
        {
            title.setText(R.string.products17_name);
            type.setText(R.string.products17_type);
            model.setText(R.string.products17_model);
            barcode.setText(R.string.products17_barcode);
            unit.setText(R.string.products17_unit);
            //  description.setText(R.string.products14_description);
            specification.setText(R.string.products17_specification);
            Productportfoliodetils_image.setImageResource(R.drawable.can16);
        }
        else if(products_numb.equals("17"))
        {
            title.setText(R.string.products18_name);
            type.setText(R.string.products18_type);
            model.setText(R.string.products18_model);
            barcode.setText(R.string.products18_barcode);
            unit.setText(R.string.products18_unit);
            //  description.setText(R.string.products14_description);
            specification.setText(R.string.products18_specification);
            Productportfoliodetils_image.setImageResource(R.drawable.can17);
        }
        else if(products_numb.equals("18"))
        {
            title.setText(R.string.products19_name);
            type.setText(R.string.products19_type);
            model.setText(R.string.products19_model);
            barcode.setText(R.string.products19_barcode);
            unit.setText(R.string.products19_unit);
            //  description.setText(R.string.products14_description);
            specification.setText(R.string.products19_specification);
            Productportfoliodetils_image.setImageResource(R.drawable.can18);
        }
        else if(products_numb.equals("19"))
        {
            title.setText(R.string.products20_name);
            type.setText(R.string.products20_type);
            model.setText(R.string.products20_model);
            barcode.setText(R.string.products20_barcode);
            unit.setText(R.string.products20_unit);
            //  description.setText(R.string.products14_description);
            specification.setText(R.string.products20_specification);
            Productportfoliodetils_image.setImageResource(R.drawable.can19);
        }
        else if(products_numb.equals("20"))
        {
            title.setText(R.string.products21_name);
            type.setText(R.string.products21_type);
            model.setText(R.string.products21_model);
            barcode.setText(R.string.products21_barcode);
            unit.setText(R.string.products21_unit);
            //  description.setText(R.string.products14_description);
            specification.setText(R.string.products21_specification);
            Productportfoliodetils_image.setImageResource(R.drawable.can20);
        }



    }
}