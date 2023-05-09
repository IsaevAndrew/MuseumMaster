package edu.example.museummaster.data.data_sourses.category.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.example.museummaster.data.data_sourses.category.models.Exhibit;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExhibitDao {
    @Query("SELECT * FROM exhibits")
    LiveData<List<Exhibit>> getAll();

    @Query("SELECT * FROM exhibits WHERE id = :id")
    LiveData<Exhibit> getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Exhibit exhibit);

    @Delete
    void delete(Exhibit exhibit);

    @Update
    void update(Exhibit exhibit);
}
