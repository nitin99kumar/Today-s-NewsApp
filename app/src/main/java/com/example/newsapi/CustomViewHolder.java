package com.example.newsapi;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    // views for recyclerViews
    TextView text_title, text_source;
    ImageView img_headline;
    CardView cardView;


    //Views for Popup-window
    ScrollView scroller;
    ImageView imgData;
    TextView forAuthor;
    TextView forDesc;
    Button saveData;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

        text_title = itemView.findViewById(R.id.text_title);
        text_source = itemView.findViewById(R.id.text_source);
        img_headline = itemView.findViewById(R.id.image_headlines);
        cardView = itemView.findViewById(R.id.main_container);

        // send the data of the particular itemView
        scroller = (ScrollView) itemView.findViewById(R.id.scroll_Id);

        imgData = (ImageView) itemView.findViewById(R.id.image_data);
        forAuthor = (TextView) itemView.findViewById(R.id.AuthorName);
        forDesc = (TextView) itemView.findViewById(R.id.newscomplete);
        saveData = (Button) itemView.findViewById(R.id.savedata);

    }

    private void scrollToBottom(){
        scroller.post(new Runnable() {
            @Override
            public void run() {
                scroller.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

}
