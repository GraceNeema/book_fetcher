package com.example.lenovo.bookfetcher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lenovo on 12/7/2018.
 */

public class BooksAdapter extends BaseAdapter {
    private final BookActivity bookActivity;
    private final ArrayList<Book> books;

    public BooksAdapter(BookActivity bookActivity, ArrayList<Book> books) {
        this.bookActivity = bookActivity;
        this.books = books;
    }
    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int i) {
        return books.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
       // Subject subject= subjects.get(position);
        Book book=books.get(i);
        String title = book.title;
        String pagenumber = book.pagenumber;
        String auteur=book.auteur;


        View view = LayoutInflater.from(bookActivity).inflate(R.layout.book_list, null);

        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView descrTextView = (TextView) view.findViewById(R.id.pageTextView);



        titleTextView.setText(title);
        descrTextView.setText(pagenumber);



        return view;
    }
}
