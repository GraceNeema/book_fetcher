package com.example.lenovo.bookfetcher;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;


import java.io.File;

/**
 * Created by Lenovo on 12/8/2018.
 */

public class Pdf_viewer extends AppCompatActivity implements OnPageChangeListener {
        Integer pageNumber = 0;
        PDFView pdfView;
        SearchView searchView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_viewer);
        pdfView=findViewById(R.id.pdfView);
        searchView=findViewById(R.id.search);


        //Load Pdf Doc
        pdfView.fitToWidth();

        pdfView.fromAsset("Des exercices donnés en évaluation.pdf")

                .enableSwipe(true) // Swiping
                .swipeHorizontal(false)
                .onPageChange(this)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(false)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                .spacing(2) // Espace entre pages (dp)
                .load();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                int page= Integer.parseInt(s);
                pdfView.jumpTo(page -1);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }


    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", "Page", page + 1, pageCount));
    }




}
