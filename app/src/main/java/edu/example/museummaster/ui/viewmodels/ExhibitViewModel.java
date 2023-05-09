package edu.example.museummaster.ui.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.example.museummaster.data.data_sourses.category.models.Exhibit;
import edu.example.museummaster.data.data_sourses.category.repositories.ExhibitRepository;

public class ExhibitViewModel extends AndroidViewModel {
    private ExhibitRepository repository;
    private LiveData<List<Exhibit>> allExhibits;

    public ExhibitViewModel(Application application) {
        super(application);
        repository = new ExhibitRepository(application);
        allExhibits = repository.getAllExhibits();
    }

    public LiveData<List<Exhibit>> getAllExhibits() {
        return allExhibits;
    }

    public LiveData<Exhibit> getExhibitById(long id) {
        return repository.getExhibitById(id);
    }

    public void insert(Exhibit exhibit) {
        repository.insert(exhibit);
    }

    public void delete(Exhibit exhibit) {
        repository.delete(exhibit);
    }

    public void update(Exhibit exhibit) {
        repository.update(exhibit);
    }
}
