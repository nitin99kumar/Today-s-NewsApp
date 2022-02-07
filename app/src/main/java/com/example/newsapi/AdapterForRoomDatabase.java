package com.example.newsapi;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.IconCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.newsapi.DB.Converters;
import com.example.newsapi.DB.MyDataBase;
import com.example.newsapi.DB.User;
import com.example.newsapi.Models.NewzzHeadlines;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.zip.Inflater;

class AdapterForRoomDatabase extends RecyclerView.Adapter<AdapterForRoomDatabase.RoomViewHolder> {

    private static Context context;
    public List<User> usersList;

    public AdapterForRoomDatabase(Context context) {
        this.context = context;
    }

    public AdapterForRoomDatabase(Context applicationContext, List<NewzzHeadlines> list) {
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View item = LayoutInflater.from(context).inflate(R.layout.headline_list_view, parent, false);
        return new RoomViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, @SuppressLint("RecyclerView") int position) {

        User user = usersList.get(position);
        holder.setData(user.getFirstName(), user.getLastName(), user.getImageUrl(), position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                View popup = inflater.inflate(R.layout.popup, null);

                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;

                Boolean focusable = true;
                final PopupWindow popuP = new PopupWindow(popup, width, height, focusable);
                View view;
                Button btn = (Button) popup.findViewById(R.id.savedata);
                btn.setVisibility(View.GONE);

                popuP.showAtLocation(v,
                        Gravity.CENTER,
                        0,
                        0);
                popuP.setOutsideTouchable(true);

                ImageView image = (ImageView) popup.findViewById(R.id.image_data);
                TextView forAuthor = (TextView) popup.findViewById(R.id.AuthorName);
                TextView forDesc = (TextView) popup.findViewById(R.id.newscomplete);

                Glide.with(context).load(usersList.get(position).getImageUrl()).into(image);
                forAuthor.setText(usersList.get(position).getFirstName());
                forDesc.setText(usersList.get(position).getLastName());

            }
        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                MyDataBase INSTANCE;
                final String DATABASE_NAME = "MyDataBase";

                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MyDataBase.class,DATABASE_NAME)/*.addTypeConverter(con)*/.allowMainThreadQueries().fallbackToDestructiveMigration().build();

                Dialog dd = new Dialog(context,R.style.Widget_AppCompat_Light_ActionBar);


                AlertDialog.Builder dialogBuilder;
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(holder.itemView.getContext());
                alertDialog.setTitle(" Delete Data");
                alertDialog.setMessage("Are you sure. You ant to delete this data")
                        .setPositiveButton(R.string.Sure , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if(usersList != null) {
            return usersList.size();
        }else{
            return 0;
        }
    }

    public User getWordAtPosition(int position) {
        return usersList.get(position);
    }

    public void setUserList (List<User> users) {
        usersList = users;
        notifyDataSetChanged();
    }

    static class RoomViewHolder extends RecyclerView.ViewHolder {

        TextView forAuthor;
        TextView forDesc;
        int mPosition;
        ImageView ImageRDB;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);

            forAuthor = itemView.findViewById(R.id.text_source);
            forDesc = itemView.findViewById(R.id.text_title);
            ImageRDB = itemView.findViewById(R.id.image_headlines);

        }

     /*   public void setImageRDB(ImageView imageRDB) {
            ImageRDB = imageRDB;
        }*/

        public void setForAuthor(TextView forAuthor) {
            this.forAuthor = forAuthor;
        }

        public void setForDesc(TextView forDesc) {
            this.forDesc = forDesc;
        }

        public void setData (String authorName, String Desc, String imgUri, int position) {
            forDesc.setText(Desc);
            forAuthor.setText(authorName);
            Glide.with(context).load(imgUri).into(ImageRDB);
            mPosition = position;

            /*Picasso.get().load(imgUri).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });*/

        }
    }
}
