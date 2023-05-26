package edu.example.museummaster.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.example.museummaster.data.data_sourses.category.models.NewsElement;
import edu.example.museummaster.data.data_sourses.category.repositories.NewsRepository;

public class NewsViewModel extends AndroidViewModel {
    private NewsRepository newsRepository;
    private LiveData<List<NewsElement>> allNewsLiveData;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        newsRepository = new NewsRepository(application);
        allNewsLiveData = newsRepository.getAllNewsLiveData();
    }

    public LiveData<List<NewsElement>> getAllNewsLiveData() {
        return allNewsLiveData;
    }

    public void insert(NewsElement news) {
        newsRepository.insert(news);
    }

    public void delete(NewsElement news) {
        newsRepository.delete(news);
    }

    public void deleteAll() {
        newsRepository.deleteAll();
    }
}
