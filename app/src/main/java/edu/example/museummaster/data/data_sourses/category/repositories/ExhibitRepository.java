package edu.example.museummaster.data.data_sourses.category.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.example.museummaster.data.data_sourses.category.models.Exhibit;
import edu.example.museummaster.data.data_sourses.category.room.dao.ExhibitDao;
import edu.example.museummaster.data.data_sourses.category.room.root.ExhibitDatabase;
import edu.example.museummaster.data.data_sourses.category.room.root.NewsDatabase;

public class ExhibitRepository {
    private ExhibitDao exhibitDao;
    private LiveData<List<Exhibit>> allExhibits;

    public ExhibitRepository(Application application) {
        ExhibitDatabase db = ExhibitDatabase.getDatabase(application);
        exhibitDao = db.exhibitDao();
        allExhibits = exhibitDao.getAll();
    }

    public LiveData<List<Exhibit>> getAllExhibits() {
        return allExhibits;
    }

    public LiveData<Exhibit> getExhibitById(long id) {
        return exhibitDao.getById(id);
    }

    public void insert(Exhibit exhibit) {
        ExhibitDatabase.databaseWriteExecutor.execute(() -> {
            exhibitDao.insert(exhibit);
        });
    }

    public void delete(Exhibit exhibit) {
        ExhibitDatabase.databaseWriteExecutor.execute(() -> {
            exhibitDao.delete(exhibit);
        });
    }

    public void update(Exhibit exhibit) {
        ExhibitDatabase.databaseWriteExecutor.execute(() -> {
            exhibitDao.update(exhibit);
        });
    }
}
