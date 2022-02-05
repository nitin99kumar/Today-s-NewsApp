package com.example.newsapi;

import static com.example.newsapi.DB.MyDataBase.getInstance;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.newsapi.DB.Converters;
import com.example.newsapi.DB.DAO;
import com.example.newsapi.DB.MyDataBase;
import com.example.newsapi.DB.User;
import com.example.newsapi.Models.NewzzHeadlines;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;

import retrofit2.http.Url;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private static final String TAG = "MyActivity";

    public static Context context;
    private List<NewzzHeadlines> headlines;

    public CustomAdapter(Context context, List<NewzzHeadlines> headlines) {
        this.context = context;
        this.headlines = headlines;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.headline_list_view, parent, false) );
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.text_title.setText(headlines.get(position).getTitle());
        holder.text_source.setText(headlines.get(position).getSource().getName());
        if(headlines.get(position).getUrlToImage() != " "){
            Picasso.get().load(headlines.get(position).getUrlToImage()).into(holder.img_headline);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());*/

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                 View popup = inflater.inflate(R.layout.popup, null);

                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;

                Boolean focusable = true;
                final PopupWindow popuP = new PopupWindow(popup, width, height , focusable);
                popuP.showAtLocation(popup, Gravity.CENTER, 0, 0);
                popuP.setOutsideTouchable(true);

                ImageView image = (ImageView) popup.findViewById(R.id.image_data);
                TextView forAuthor = (TextView) popup.findViewById(R.id.AuthorName);
                TextView forDesc = (TextView) popup.findViewById(R.id.newscomplete);

                Button saveData = (Button) popup.findViewById(R.id.savedata);

                if(headlines.get(position).getUrlToImage() != null){
                    if(headlines.get(position).getAuthor() != null){
                        Picasso.get().load(headlines.get(position).getUrlToImage()).into(image);
                    }
                }else{
                    image.setVisibility(View.GONE);
                }

                String imgData = headlines.get(position).getUrlToImage();
                forAuthor.setText(headlines.get(position).getAuthor());
                forDesc.setText(headlines.get(position).getDescription());

                /*Bitmap pic = null;
                pic = BitmapFactory.decodeFile(headlines.get(position).getUrlToImage());*/

               /* String imageData = Glide.with(context).load(headlines.get(position).getUrlToImage()).into(image).toString();*/

                String imageData = imgData;
                String AuthorName = forAuthor.getText().toString();
                String Description = forDesc.getText().toString();

                saveData.setOnClickListener(new View.OnClickListener()  {
                    @Override
                    public void onClick(View v) {


                        User user = new User();

                       /* Glide.with(context)
                                .load(headlines.get(position).getUrlToImage())
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                .preload();*/

                        /*Converters.convertImage2ByteArray(bitmap);*/

                        /*user.ImagesData = Glide.with(context).load(headlines.get(position).getUrlToImage()).*/

                       /* Glide.with(context)
                                .load(headlines.get(position).getUrlToImage())
                                .into(image).
                                .into(new SimpleTarget<Bitmap>(100,100) {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation)  {
                                        saveImage(resource);
                                    }
                                });*/
                      /*  user.ImagesData = Glide.with(context)
                                .asBitmap().load(headlines.get(position).getUrlToImage())
                                .into(new CustomTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                    }



                                } ));*/

                       /* Picasso.get().load(headlines.get(position).getUrlToImage()).into(getTarget(imageData));*/

                        /*user.ImagesData = Converters.convertImage2ByteArray(bitmap);*/
                      /*  user.ImageUrl = imageData;*/

                        user.firstName = AuthorName;
                        user.lastName = Description;

                        if(!verifyPermission()) {
                            return;
                        }

                        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" ;
                        final File dir = new File(dirPath);

                        final String fileName = imageData.substring(imageData.lastIndexOf('/' ) + 1);

                        Glide.with(context).load(headlines.get(position).getUrlToImage()).into(new CustomTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                                Bitmap bit = ((BitmapDrawable) resource).getBitmap();
                                Toast.makeText(context, "Saving Image..!", Toast.LENGTH_SHORT).show();
                                saveImage(bit ,dir ,fileName);
                            }



                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }

                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);

                                Toast.makeText(context, "Failed to download image. Please try again later!", Toast.LENGTH_SHORT).show();

                            }
                        });

                        Glide.with(context).load(headlines.get(position).getUrlToImage())
                                .thumbnail(0.5f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(new CustomTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                        image.setImageDrawable(resource);
                                        image.setDrawingCacheEnabled(true);
                                        saveImage();
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {

                                    }

                                });
                               /* {
                                    @Override
                                    public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                        apd_image.setImageDrawable(glideDrawable);
                                        apd_image.setDrawingCacheEnabled(true);
                                        saveImage();
                                    }});*/


                        PopulateAsynTask pop = new PopulateAsynTask();
                        pop.execute(user);

                        Toast.makeText(context.getApplicationContext(), "Data inserted successfully",Toast.LENGTH_SHORT).show();

                        popuP.dismiss();

                    }

                    public void saveImage(Bitmap bit, File dir, String fileName) {

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
                                Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {
                                Toast.makeText(context, " Error while Saving Image!", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                        else {
                            Toast.makeText(context, "Failed To Make Folder!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    public boolean verifyPermission() {

                        int permissionExternalMemory = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        if(permissionExternalMemory != PackageManager.PERMISSION_GRANTED) {
                            String[] STORAGE_PERMISSION = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                           /* ActivityCompat.requestPermissions(context,STORAGE_PERMISSION,1);*/
                            return false;
                        }
                        return true;
                    }
                });
            }

         /*   private Target getTarget(String url) {
                Target target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                File file = new File(Environment.getExternalStorageState().getClass() + "/" + url);
                                try{
                                    file.createNewFile();
                                    try  {
                                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fileOutputStream);
                                        fileOutputStream.flush();
                                        fileOutputStream.close();
                                    } catch (IOException e) {
                                        Log.e("IOException", e.getLocalizedMessage());
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                };
                return target;
            }
            */


        });
    }



    @Override
    public int getItemCount() {
        return headlines.size();
    }

    public void getRoomDatabase(){
    }

    public static class PopulateAsynTask extends AsyncTask<User, Void, Void> {

        private DAO dao;

        public PopulateAsynTask() {
        }

        public PopulateAsynTask(MyDataBase myDataBase) {
            dao = MyDataBase.INSTANCE.dao();
        }

        @Override
        protected Void doInBackground(User... users) {
            getInstance(context).dao().insertNew(users[0]);
            return null;
        }

    }




}
