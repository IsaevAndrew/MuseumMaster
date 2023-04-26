package edu.example.museummaster.data.data_sourses.category.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.example.museummaster.data.data_sourses.category.models.NewsElement;

@Dao
public interface NewsElementDao {

    @Query("SELECT * FROM news_table ORDER BY id DESC")
    LiveData<List<NewsElement>> getAllNews();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNews(NewsElement newsElement);

    @Delete
    void deleteNews(NewsElement newsElement);

    @Query("DELETE FROM news_table")
    void deleteAllNews();
}
