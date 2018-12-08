package com.example.lenovo.bookfetcher;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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

/**
 * Created by Lenovo on 12/7/2018.
 */

public class BookActivity extends AppCompatActivity {
    MyViews mViews;
    private static final String URL =
            "http://www.json-generator.com/api/json/get/cgsVRbrLoy?indent=2";
    private static final String URL_2="http://www.json-generator.com/api/json/get/cjyJOYOXFK?indent=2";
    private BooksAdapter booksAdapter;
    private ArrayList<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);
        mViews = new MyViews();
        books=new ArrayList<>();


        String method = super.getIntent().getExtras().getString("method");


        if (method.equals("myMethod")) {
            test();
            new BookDownloadTask().execute(URL);
        }
        if(method.equals("myMethod_2")){
            new BookDownloadTask().execute(URL_2);
        }
        if(method.equals("myMethod_3")){
            new BookDownloadTask().execute(URL_2);
        }

    }

    public void test() {
        Toast.makeText(getApplicationContext(), "ok 200", Toast.LENGTH_LONG).show();
    }

    class BookDownloadTask extends AsyncTask<String, Void, String> { //Class which establish connexion with the Url
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
        protected void onPostExecute(String response) { // Once connexion is done, display the response by calling parseJsonResponse method
            mViews.progressBar.setVisibility(View.GONE);

            if (response != null) {
                parseJsonResponse(response);
            }
        }
    }

    private void parseJsonResponse(String response) { // fetch and parse the url response
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray bookArray = jsonObject.getJSONArray("books");

            for (int i = 0; i < bookArray.length(); i++) {
                JSONObject bookObject = bookArray.getJSONObject(i);
                String pagenumber = bookObject.getString("pagenumber");
                String title = bookObject.getString("title");


                books.add(new Book(pagenumber, title));
            }

            booksAdapter = new BooksAdapter(BookActivity.this, books);
            mViews.bookList.setAdapter(booksAdapter);
            mViews.bookList.setVisibility(View.VISIBLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mViews.bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), Pdf_viewer.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
        class MyViews {
            final ListView bookList;
            final ProgressBar progressBar;


            public MyViews() {
                bookList = (ListView) findViewById(R.id.booksLst);
                progressBar = (ProgressBar) findViewById(R.id.progressBr);


            }

        }
    }

