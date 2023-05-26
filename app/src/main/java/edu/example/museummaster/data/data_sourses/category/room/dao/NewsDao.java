package edu.example.museummaster.data.data_sourses.category.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.example.museummaster.data.data_sourses.category.models.NewsElement;

@Dao
public interface NewsDao {
    @Insert
    void insert(NewsElement news);

    @Delete
    void delete(NewsElement news);

    @Query("DELETE FROM news")
    void deleteAll();

    @Query("SELECT * FROM news")
    List<NewsElement> getAllNews();

    @Query("SELECT * FROM news")
    LiveData<List<NewsElement>> getAllNewsLiveData();
}
