package edu.example.museummaster.data.data_sourses.category.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.example.museummaster.data.data_sourses.category.models.NewsElement;
import edu.example.museummaster.data.data_sourses.category.room.dao.NewsElementDao;
import edu.example.museummaster.data.data_sourses.category.room.root.NewsDatabase;

public class NewsRepository {

    private NewsElementDao mNewsDao;
    private LiveData<List<NewsElement>> mAllNews;

    public NewsRepository(Application application) {
        NewsDatabase db = NewsDatabase.getDatabase(application);
        mNewsDao = db.newsDao();
        mAllNews = mNewsDao.getAllNews();
    }

    public LiveData<List<NewsElement>> getAllNews() {
        return mAllNews;
    }

    public void insert(NewsElement newsElement) {
        NewsDatabase.databaseWriteExecutor.execute(() -> {
            mNewsDao.insertNews(newsElement);
        });
    }

    public void delete(NewsElement newsElement) {
        NewsDatabase.databaseWriteExecutor.execute(() -> {
            mNewsDao.deleteNews(newsElement);
        });
    }

    public void deleteAllNews() {
        NewsDatabase.databaseWriteExecutor.execute(() -> {
            mNewsDao.deleteAllNews();
        });
    }
}
