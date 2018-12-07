package com.example.lenovo.bookfetcher;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Views mViews;
    private static final String URL =
            "http://www.json-generator.com/api/json/get/bVatIxAYPm?indent=2"; // l'api subjects

    private SubjectsAdapter subjectsAdapter;
    private ArrayList<Subject> subjects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViews = new Views();
        subjects=new ArrayList<>();
        new BookDownloadTask().execute(URL);

    }
    //connexion avec l'api
    class BookDownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            mViews.progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String[] params) {
            try {
                java.net.URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);

                StringBuilder stringBuilder = new StringBuilder();
                String tempString;

                while ((tempString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(tempString);
                    stringBuilder.append("\n");
                }

                return stringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            mViews.progressBar.setVisibility(View.GONE);

            if (response != null) {
                parseJsonResponse(response);
            }
        }
    }
    private void parseJsonResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray bookArray = jsonObject.getJSONArray("subjects");

            for (int i = 0; i < bookArray.length(); i++) {
                JSONObject bookObject = bookArray.getJSONObject(i);
                String description = bookObject.getString("description");
                String title = bookObject.getString("title");



                subjects.add(new Subject(description, title));
            }

            subjectsAdapter = new SubjectsAdapter(MainActivity.this, subjects);
            mViews.bookList.setAdapter(subjectsAdapter);
            mViews.bookList.setVisibility(View.VISIBLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class Views {
        final ListView bookList;
        final ProgressBar progressBar;


        public Views() {
            bookList = (ListView) findViewById(R.id.booksList);
            progressBar = (ProgressBar) findViewById(R.id.progressBar);


        }

    }
}
