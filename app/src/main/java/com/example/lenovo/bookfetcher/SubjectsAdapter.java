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

public class SubjectsAdapter extends BaseAdapter  {
    private final MainActivity mainActivity;
    private final ArrayList<Subject> subjects;

    public SubjectsAdapter(MainActivity mainActivity, ArrayList<Subject> subjects) {
        this.mainActivity = mainActivity;
        this.subjects = subjects;
    }

    @Override
    public int getCount() {
        return subjects.size();
    }

    @Override
    public Object getItem(int position) {
        return subjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Subject subject= subjects.get(position);
        String title = subject.title;
        String description = subject.description;


        View view = LayoutInflater.from(mainActivity).inflate(R.layout.subject_list, null);

        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView descrTextView = (TextView) view.findViewById(R.id.descriptionTextView);


       titleTextView.setText(title);
        descrTextView.setText(description);


        return view;
    }
}
