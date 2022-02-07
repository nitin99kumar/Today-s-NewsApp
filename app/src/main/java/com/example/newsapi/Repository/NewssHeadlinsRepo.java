package com.example.newsapi.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import com.example.newsapi.DB.DAO;
import com.example.newsapi.DB.MyDataBase;
import com.example.newsapi.DB.User;
import com.example.newsapi.Models.NewzzHeadlines;

import java.util.List;

public class NewssHeadlinsRepo {

    private DAO userDao;
    private LiveData<List<User>> getAllData;

    public NewssHeadlinsRepo(Application application){
        MyDataBase db = MyDataBase.getInstance(application);
        userDao = db.dao();
        getAllData = userDao.getAllUser();
    }

    public void insert (User Users) {
        new InsertAsynTesk((MyDataBase) userDao).execute(Users);
    }

    public LiveData<List<User>> getAllData() {
        return getAllData();
    }

    public void deleteWord(User user)  {
        new deleteWordAsyncTask((DAO) userDao).execute(user);
    }

  /*  public void insertUsers(final User users) {
        new InsertAsynTesk<Void, Void, Void>() {
            @Override
            protected Void doInBackground(List<User>... lists) {
                db.dao().insertNew(users);
                return null;
            }
        }.execute();
    }*/

   private static class InsertAsynTesk extends AsyncTask<User, Void, Void> {
        DAO dao;
        InsertAsynTesk(MyDataBase myDataBase) {
            dao = myDataBase.dao();
        }

       @Override
       protected Void doInBackground(User... lists) {
            dao.insertNew((User) lists[0]);
           return null;
       }

   }

    private static class deleteWordAsyncTask extends AsyncTask<User, Void, Void> {
        private DAO mAsyncTaskDao;

        deleteWordAsyncTask(DAO dao) {
            mAsyncTaskDao = (DAO) dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

}
