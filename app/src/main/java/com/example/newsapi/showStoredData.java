package com.example.newsapi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.newsapi.DB.MyDataBase;
import com.example.newsapi.DB.User;
import com.example.newsapi.Models.NewzApiResponse;
import com.example.newsapi.Models.NewzzHeadlines;
import com.example.newsapi.viewModel.UserViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

public class showStoredData extends AppCompatActivity {

    private static final int NEW_USER_ACTIVITY_REQUEST_CODE = 1;
    private final String TAG = this.getClass().getSimpleName();
    UserViewModel modelView;
    public AdapterForRoomDatabase adap;

    private ProgressDialog dailog;

    SearchView searchView;
    RecyclerView recyclerView;
    public static Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_stored_data);

        recyclerView = (RecyclerView) findViewById(R.id.room_data_show);
        adap = new AdapterForRoomDatabase(this );
        recyclerView.setAdapter(adap);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dailog = new ProgressDialog(this);
        dailog.setTitle("Please Wait");
        dailog.show();

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        User user = adap.getWordAtPosition(position);
                        Toast.makeText(showStoredData.this, "Deleting Data ", Toast.LENGTH_SHORT).show();

                        // Delete the word
                        modelView.deleteWord(user);
                    }
                });

        helper.attachToRecyclerView(recyclerView);

       /* searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

               *//* dailog.setTitle("Please Wait!!! ");
                dailog.show();
                RequestManager manager = new RequestManager(showStoredData.this);

                manager.getNewsHeadLines(  , "general", query);*//*

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {



                return true;
            }
        });*/

        modelView = new ViewModelProvider(this).get(UserViewModel.class);

        modelView.getAlldata().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                adap.setUserList(users);
                Log.d(TAG, " on changed " + users.toString());
                Log.d(TAG, " on changed " + users.size());
            }
        });

    }



   /* public static void saveImage(Bitmap bit, File dir, String fileName) {

        boolean successDirCreated = false;
        if(dir.exists()) {
            successDirCreated = dir.mkdir();
        }

        if(successDirCreated) {
            File imageFile = new File(dir,fileName);
            String saveImagePath = imageFile.getAbsolutePath();
            try{
                OutputStream fOut = new FileOutputStream(imageFile);
                bit.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
                Toast.makeText(ctx, "Image Saved!", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(ctx, " Error while Saving Image!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(ctx, "Failed To Make Folder!", Toast.LENGTH_SHORT).show();
        }

    }*/

   /* public static boolean verifyPermission() {

        int permissionExternalMemory = ActivityCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionExternalMemory != PackageManager.PERMISSION_GRANTED) {
            String[] STORAGE_PERMISSION = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions((Activity) ctx,STORAGE_PERMISSION,1);
            return false;
        }
        return true;
    }*/

  /*  void downloadImage(String imageURL){

        if (!verifyPermission()) {
            return;
        }

        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getString(R.string.app_name) + "/";

        final File dir = new File(dirPath);

        final String fileName = imageURL.substring(imageURL.lastIndexOf('/') + 1);

        Glide.with(ctx)
                .load(imageURL)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                        Bitmap bitmap = ((BitmapDrawable)resource).getBitmap();
                        Toast.makeText(ctx, "Saving Image...", Toast.LENGTH_SHORT).show();
                            saveImage(bitmap, dir, fileName);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);

                        Toast.makeText(ctx, "Failed to Download Image! Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                });

    }*/

   /* public void getAllDataFromRoomDatabase(View view) {
        LiveData<List<User>> userList = MyDataBase.getInstance(getApplicationContext()).dao().getAllUser();

        userList.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                Log.d(TAG, "on changed" + users.toString());
                Log.d(TAG, "on changed" + users.size());
            }
        });
    }*/





}