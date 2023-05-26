package edu.example.museummaster.data.data_sourses.category.repositories;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.example.museummaster.data.data_sourses.category.models.NewsElement;
import edu.example.museummaster.data.data_sourses.category.room.dao.NewsDao;
import edu.example.museummaster.data.data_sourses.category.room.root.NewsDatabase;

public class NewsRepository {
    private NewsDao newsDao;
    private LiveData<List<NewsElement>> allNewsLiveData;

    public NewsRepository(Context context) {
        NewsDatabase db = NewsDatabase.getInstance(context);
        newsDao = db.newsDao();
        allNewsLiveData = newsDao.getAllNewsLiveData();
    }

    public LiveData<List<NewsElement>> getAllNewsLiveData() {
        return allNewsLiveData;
    }


    public void insert(NewsElement news) {
        new InsertAsyncTask(newsDao).execute(news);
    }

    public void delete(NewsElement news) {
        new DeleteAsyncTask(newsDao).execute(news);
    }


    public void deleteAll() {
        new DeleteAllNewsAsyncTask(newsDao).execute();
    }
    private static class DeleteAllNewsAsyncTask extends AsyncTask<Void, Void, Void> {
        private NewsDao newsDao;

        private DeleteAllNewsAsyncTask(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            newsDao.deleteAll();
            return null;
        }
    }
    private static class InsertAsyncTask extends AsyncTask<NewsElement, Void, Void> {
        private NewsDao newsDao;

        public InsertAsyncTask(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(NewsElement... newsElements) {
            newsDao.insert(newsElements[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<NewsElement, Void, Void> {
        private NewsDao newsDao;

        public DeleteAsyncTask(NewsDao newsDao) {
            this.newsDao = newsDao;
        }


        @Override
        protected Void doInBackground(NewsElement... newsElements) {
            newsDao.delete(newsElements[0]);
            return null;
        }
    }
}
