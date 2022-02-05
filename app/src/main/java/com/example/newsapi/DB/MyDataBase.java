package com.example.newsapi.DB;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.newsapi.CustomAdapter;
import com.example.newsapi.Models.NewzzHeadlines;

@Database(entities = User.class, version = 3,exportSchema = false)
@TypeConverters(Converters.class)
public abstract class MyDataBase extends RoomDatabase{

    private static final String DATABASE_NAME = "MyDataBase";

    public abstract DAO dao();

    public static volatile MyDataBase INSTANCE;

    public static MyDataBase getInstance(final Context context){
        if(INSTANCE == null) {
            synchronized (MyDataBase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MyDataBase.class,DATABASE_NAME)/*.addTypeConverter(con)*/.allowMainThreadQueries().fallbackToDestructiveMigration().addCallback(callback).build();
                }
            }
        }
        return INSTANCE;
    }

    static Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new CustomAdapter.PopulateAsynTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(MyDataBase instance) {
            Dao dao = (Dao) instance.dao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

}
