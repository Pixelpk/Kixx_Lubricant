package com.pixelpk.kixxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
import com.shockwave.pdfium.PdfDocument;

import java.util.List;

public class TermsScreen extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {

    TextView Terms_tv;
    ImageView TermsScreen_backarrow;

    private static final String TAG = TermsScreen.class.getSimpleName();
    public static final String SAMPLE_FILE = "terms_en.pdf";
    public static final String SAMPLE_FILE2 = "terms_en.pdf";
    PDFView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;

    LinearLayout TermsScreen_backarrow_LL;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_screen);





        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String lang = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");
        TermsScreen_backarrow_LL = findViewById(R.id.TermsScreen_backarrow_LL);

        /*String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

//            Toast.makeText(getContext(),rtl, Toast.LENGTH_SHORT).show();

        if(rtl.equals("1"))
        {
            TermsScreen_backarrow.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24_rwhite);
        }*/


        //  editor.putString(Shared.User_promo,"2").apply();

        //  Toast.makeText(this, lang, Toast.LENGTH_SHORT).show();
       // Toast.makeText(this, lang, Toast.LENGTH_SHORT).show();

        if(lang!=null)
        {
            if(lang.equals("1"))
            {
                pdfView= (PDFView)findViewById(R.id.pdfView);
                displayFromAsset(SAMPLE_FILE2);
                TermsScreen_backarrow = findViewById(R.id.TermsScreen_backarrow);
                TermsScreen_backarrow.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24forward);

            }
            else if(lang.equals("2"))
            {
                pdfView= (PDFView)findViewById(R.id.pdfView);
                displayFromAsset(SAMPLE_FILE);
                TermsScreen_backarrow = findViewById(R.id.TermsScreen_backarrow);

            }
        }

       // Terms_tv.setText(R.string.terms);

        TermsScreen_backarrow_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void displayFromAsset(String assetFileName) {
        pdfFileName = assetFileName;

        pdfView.fromAsset(pdfFileName)
                .defaultPage(pageNumber)
                .enableSwipe(true)

                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }


    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }


    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }
}