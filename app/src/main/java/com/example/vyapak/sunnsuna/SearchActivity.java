package com.example.vyapak.sunnsuna;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class SearchActivity extends AppCompatActivity {

    String result;
    String api_key= "475b39aabf68d83f73d29159276bec0d";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;

    public class DownloadTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpsURLConnection urlConnection = null;

            try{
                url = new URL(urls[0]);
                urlConnection = (HttpsURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while(data != -1){
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;

            }catch (Exception e){
                e.printStackTrace();
                return e.toString();
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.searchToolBar);
        setSupportActionBar(toolbar);

        SearchView searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                DownloadTask downloadTask= new DownloadTask();
                String queryString = s.replaceAll(" ", "%20");

                try {
                    result = downloadTask.execute("https://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=" + queryString.trim() +"&api_key=" + api_key + "&format=json").get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Log.i("Contents", result);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

        listItems = new ArrayList<>();

        for(int i=0; i<=10; i++){
            ListItem listItem = new ListItem(
                    "heading " + (i+1),
                    "Lorem Ipsum"
            );
            listItems.add(listItem);
        }

        for(int j = 0; j<=10; j++){
            Log.i("item", listItems.get(j).getHead());
        }


        ListAdapter listAdapter = new ListAdapter(listItems, SearchActivity.this);
        adapter = listAdapter;

        recyclerView.setAdapter(adapter);

    }

}
