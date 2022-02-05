package com.example.newsapi.viewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import com.example.newsapi.DB.DAO;
import com.example.newsapi.DB.MyDataBase;
import com.example.newsapi.DB.User;
import com.example.newsapi.Repository.NewssHeadlinsRepo;

import java.util.Date;
import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    public DAO userDao;
    private MyDataBase myDataBase;
    LiveData<List<User>> mAllUsers;

    NewssHeadlinsRepo repo;

    public UserViewModel(@NonNull Application application) {
        super(application);

        myDataBase = MyDataBase.getInstance(application);
        repo = new NewssHeadlinsRepo(application);

        userDao = myDataBase.dao();
        mAllUsers = userDao.getAllUser();
       /* dao = myDataBase.dao();*/

     /*   mRepo = new NewssHeadlinsRepo(application);
        mAllUsers = mRepo.getAllData();*/

    }

    public void insertNew(User user) {
        new InsertAsyncTask((Dao) userDao).execute(user);
    }

    public LiveData<List<User>> getAlldata() {
        return mAllUsers;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }

    private class OperationsAsyncTask extends AsyncTask<User, Void, Void> {

        Dao mAsyncTaskDao;

        OperationsAsyncTask(DAO dao) {
            this.mAsyncTaskDao = (Dao) dao;
        }

        @Override
        protected Void doInBackground(User... notes) {
            return null;
        }

    }

    private class InsertAsyncTask extends OperationsAsyncTask {

        InsertAsyncTask(Dao mNoteDao) {
            super((DAO) mNoteDao);
        }

        @Override
        protected Void doInBackground(User... notes) {
            userDao.insertNew(notes[0]);
            return null;
        }
    }

}
