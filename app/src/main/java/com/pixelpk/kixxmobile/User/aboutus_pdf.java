package com.pixelpk.kixxmobile.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.pixelpk.kixxmobile.R;
import com.pixelpk.kixxmobile.TermsScreen;
import com.pixelpk.kixxmobile.User.SharedPreferences.Shared;
import com.shockwave.pdfium.PdfDocument;

import java.util.List;

public class aboutus_pdf extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {

    public static final String SAMPLE_FILE = "kixxau.pdf";
    PDFView pdfView;
    String pdfFileName;
    Integer pageNumber = 0;
    private static final String TAG = TermsScreen.class.getSimpleName();
    LinearLayout aboutus_pdf__backarrow_LL;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView TermsScreen_backarrow;

    //Handle Button Clicks
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus_pdf);

        TermsScreen_backarrow = findViewById(R.id.TermsScreen_backarrow);

        sharedPreferences = getSharedPreferences("Shared",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String rtl = sharedPreferences.getString(Shared.KIXX_APP_LANGUAGE,"0");

        if(rtl.equals("1"))
        {
            TermsScreen_backarrow.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24forward);
        }


        aboutus_pdf__backarrow_LL = findViewById(R.id.aboutus_pdf__backarrow_LL);

        aboutus_pdf__backarrow_LL.setOnClickListener(new View.OnClickListener() {
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

        pdfView= (PDFView)findViewById(R.id.pdfView);
        displayFromAsset(SAMPLE_FILE);


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

