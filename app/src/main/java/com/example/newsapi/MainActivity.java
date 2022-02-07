package com.example.newsapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.newsapi.Models.NewzApiResponse;
import com.example.newsapi.Models.NewzzHeadlines;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private List<NewzzHeadlines> llist;
    private ProgressDialog dailog;
    RecyclerView recyclerView;
    CustomAdapter adap;
    SearchView searchView;
    Button b1,b2,b3,b4,b5,b6,b7;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dailog = new ProgressDialog(this);
        dailog.setTitle("Please Wait");
        dailog.show();

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                dailog.setTitle("Please Wait!!! ");
                dailog.show();
                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getNewsHeadLines(listener, "general", query);

                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });


        b1 = (Button) findViewById(R.id.busines_id);
        b1.setOnClickListener(this);
        b2 = (Button) findViewById(R.id.entertainment_id);
        b2.setOnClickListener(this);
        b3 = (Button) findViewById(R.id.general_id);
        b3.setOnClickListener(this);
        b4 = (Button) findViewById(R.id.health_id);
        b4.setOnClickListener(this);
        b5 = (Button) findViewById(R.id.science_id);
        b5.setOnClickListener(this);
        b6 = (Button) findViewById(R.id.sports_id);
        b6.setOnClickListener(this);
        b7 = (Button) findViewById(R.id.technology_id);
        b7.setOnClickListener(this);

        RequestManager mg = new RequestManager(this );
        mg.getNewsHeadLines(listener, "general", null);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.getData:
                Intent i = new Intent(this, showStoredData.class);
                startActivity(i);
                break;

            /*case R.id.title2:
                Toast.makeText(this, "title2 is pressed", Toast.LENGTH_SHORT).show();
                break;
*/
            default:
                break;
        }
        return true;
    }

    private final onFetchDataListener<NewzApiResponse> listener = new onFetchDataListener<NewzApiResponse>() {
        @Override
        public void onFetchData(List<NewzzHeadlines> list, String message) {

            if(list.isEmpty()){
                Toast.makeText(MainActivity.this, "No Data Found!!!", Toast.LENGTH_SHORT).show();
            }

            showNewz(list);
            dailog.dismiss();
        }

        @Override
        public void onError(String message) {

            Toast.makeText(MainActivity.this, "An Error Occured!", Toast.LENGTH_SHORT).show();

        }
    };

    private void showNewz(List<NewzzHeadlines> list) {

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adap = new CustomAdapter(this, list);
        recyclerView.setAdapter(adap);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dailog.dismiss();
    }

    /* public void createNewDailog(){
        dailogBuilder = new AlertDialog.Builder(this);
        final View popupWindow = getLayoutInflater().inflate(R.layout.popup, null);
        imageView = (ImageView) findViewById(R.id.image_data);
        txtVieww = (TextView) findViewById(R.id.news);

        dailogBuilder.setView(popupWindow);
        dailog = dailogBuilder.create();
        dailog.show();

        recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });

    }*/



}