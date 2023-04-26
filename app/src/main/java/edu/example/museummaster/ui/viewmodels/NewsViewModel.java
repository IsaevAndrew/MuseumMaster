package edu.example.museummaster.ui.viewmodels;

//import ru.example.samsungproject.sql.NewsDB;
//import ru.example.samsungproject.sql.NewsDao;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.example.museummaster.data.data_sourses.category.models.NewsElement;
import edu.example.museummaster.data.data_sourses.category.repositories.NewsRepository;

public class NewsViewModel extends AndroidViewModel {

    private NewsRepository mRepository;
    private LiveData<List<NewsElement>> mAllNews;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        mRepository = new NewsRepository(application);
        mAllNews = mRepository.getAllNews();
    }

    public LiveData<List<NewsElement>> getAllNews() {
        return mAllNews;
    }

    public void insert(NewsElement newsElement) {
        mRepository.insert(newsElement);
    }

    public void delete(NewsElement newsElement) {
        mRepository.delete(newsElement);
    }

    public void deleteAllNews() {
        mRepository.deleteAllNews();
    }
}
