package com.example.newsapi.Models;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsapi.CustomAdapter;
import com.example.newsapi.MainActivity;
import com.example.newsapi.R;
import com.example.newsapi.RequestManager;
import com.example.newsapi.onFetchDataListener;

import java.util.List;

public class RoomDataShow extends AppCompatActivity implements View.OnClickListener {

    SearchView searchView;
    RecyclerView showDataRecyclerView;
    CustomAdapter adap;
    private ProgressDialog dailog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_data_show);

        dailog = new ProgressDialog(this);
        dailog.setTitle("Please Wait");
        dailog.show();

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                dailog.setTitle("Please Wait!!! ");
                dailog.show();
                RequestManager manager = new RequestManager(RoomDataShow.this);
                manager.getNewsHeadLines(listener, "general", query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }

    private final onFetchDataListener<NewzApiResponse> listener = new onFetchDataListener<NewzApiResponse>() {
        @Override
        public void onFetchData(List<NewzzHeadlines> list, String message) {

            if(list.isEmpty()){
                Toast.makeText(RoomDataShow.this, "No Data Found!!!", Toast.LENGTH_SHORT).show();
            }

            showNewz(list);
            dailog.dismiss();
        }

        @Override
        public void onError(String message) {

            Toast.makeText(RoomDataShow.this, "An Error Occured!", Toast.LENGTH_SHORT).show();

        }
    };

    // show newzz in
    private void showNewz(List<NewzzHeadlines> list) {

        showDataRecyclerView = findViewById(R.id.recycler_view);
        showDataRecyclerView.setHasFixedSize(true);
        showDataRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adap = new CustomAdapter(this, list);
        showDataRecyclerView.setAdapter(adap);

    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String category = button.getText().toString();
        dailog.setTitle("Please Wait!!!. Data is Coming");
        dailog.show();
        RequestManager mg = new RequestManager(this );
        mg.getNewsHeadLines(listener, category, null);
    }
}