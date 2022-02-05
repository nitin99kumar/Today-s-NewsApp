package com.example.newsapi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.IconCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapi.DB.Converters;
import com.example.newsapi.DB.User;
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
    private List<User> usersList;

    public AdapterForRoomDatabase(Context context) {
        this.context = context;
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

                popuP.showAtLocation(v,
                        Gravity.CENTER,
                        0,
                        0);
                popuP.setOutsideTouchable(true);

                ImageView image = (ImageView) popup.findViewById(R.id.image_data);
                TextView forAuthor = (TextView) popup.findViewById(R.id.AuthorName);
                TextView forDesc = (TextView) popup.findViewById(R.id.newscomplete);

                /*image.setImageBitmap(Converters.convertByteArray2Image(usersList.get(position).ImagesData));*/

                /*if(usersList.get(position).getImagesData()!= null) {
                    image.setImageURI(Uri.parse(usersList.get(position).getImagesData()));
                }*/

                Glide.with(context).load(usersList.get(position).getImageUrl()).into(image);
                forAuthor.setText(usersList.get(position).getFirstName());
                forDesc.setText(usersList.get(position).getLastName());
                /*image.setImageBitmap(Converters.convertByteArray2Image(usersList.get(position).ImagesData));*/

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
            ImageRDB = itemView.findViewById(R.id.image_data);

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
            Picasso.get().load(imgUri).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
            /*Picasso.get().load(imgData).into(ImageRDB);*/
            /*Glide.with(AdapterForRoomDatabase.context).load(imgUri).into(ImageRDB);*/
           /* Glide.with(context).load(imgUri).apply(  ).into(ImageRDB);
            String imgData = imgUri;*/
            /*ImageRDB.setImageURI(Uri.parse(*//*imgData.toString()*//*));*/
            mPosition = position;
        }
    }
}
