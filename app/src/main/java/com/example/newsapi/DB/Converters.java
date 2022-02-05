package com.example.newsapi.DB;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.ByteArrayBuffer;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Converters extends AppCompatActivity {

   /* @TypeConverter
    public static Bitmap getBitMapFromUrl(String imgUrl) throws MalformedURLException {

        URL url = new URL(imgUrl);

        try {

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

           *//* conn.setDoInput(true);
            conn.connect();*//*

            InputStream input = conn.getInputStream();
            Bitmap myBitmap = Bitmap.createScaledBitmap()
            return myBitmap;

        } catch (IOException e) {

            e.printStackTrace();
            return null;

        }

    }*/

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*public static Bitmap getBitmap(String url) {
        Bitmap bit = null;
        Picasso.get().load(url).into(
                object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                bmp =  bitmap
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
        })
        return bmp
    }*/

    public static byte[] convertUrlToBitmap(URL toDownload) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try{
            byte[] chunk = new byte[4096];
            int bytesRead;
            InputStream stream = toDownload.openStream();

            while((bytesRead = stream.read(chunk)) > 0) {
                outputStream.write(chunk,0, bytesRead);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return outputStream.toByteArray();

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @TypeConverter
    public static byte[] convertImage2ByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
        return stream.toByteArray();

    }

    @TypeConverter
    public static Bitmap convertByteArray2Image(byte[] array) {
        return BitmapFactory.decodeByteArray(array,0, array.length);
    }



    public static byte[] getByteArrayImage(String url){
        try {
            URL imageUrl = new URL(url);
            URLConnection ucon = imageUrl.openConnection();

            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

            ByteArrayBuffer baf = new ByteArrayBuffer(500);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }

            return baf.toByteArray();
        } catch (Exception e) {
            Log.d("ImageManager", "Error: " + e.toString());
        }
        return null;
    }


  /*  public static String getByteArrayFromURL(final  String url) {
        String base64Image = "";
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> result = executor.submit(new Callable<String>() {
            public String call() throws Exception {
                try {

                    URL imageUrl = new URL(url);
                    URLConnection ucon = imageUrl.openConnection();
                    InputStream is = ucon.getInputStream();
                    return Base64.encodeToString(IOUtils.toByteArray(is), Base64.NO_WRAP);
                } catch (Exception e) {

                    e.printStackTrace();
                }
                return null;
            }
        });

        try {
            base64Image = result.get();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return base64Image;
    }*/


    /**
     * @param bitmap
     * @return converting bitmap and return a string
     */
    public static String fromBitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    /**
     * @param encodedString
     * @return bitmap (from given string)
     */
    public static Bitmap fromStringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    @TypeConverter
    public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        if(temp==null)
        {
            return null;
        }
        else
            return temp;
    }

    @TypeConverter
    public static Bitmap StringToBitMap(String encodedString){
        try {
            byte[] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            if(bitmap==null)
            {
                return null;
            }
            else
            {
                return bitmap;
            }

        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }






}
