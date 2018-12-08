package com.example.lenovo.bookfetcher;

/**
 * Created by Lenovo on 12/7/2018.
 */

public class Book {
    public String title;
    public String pagenumber;
    public String auteur;
    public String icon;

    public Book(String pagenumber,String title) {
        this.title = title;
        this.pagenumber = pagenumber;
        //  this.icon = icon;
    }
}

