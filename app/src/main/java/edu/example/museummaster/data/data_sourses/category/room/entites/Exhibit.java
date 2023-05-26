package edu.example.museummaster.data.data_sourses.category.room.entites;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "exhibits")
public class Exhibit {
    @PrimaryKey()
    private long id;
    private String name;
    private String description;
    private String photoName;
    private String audioName;

    public Exhibit(long id, String name, String description, String photoName, String audioName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photoName = photoName;
        this.audioName = audioName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getAudioName() {
        return audioName;
    }

    public void setAudioName(String audioName) {
        this.audioName = audioName;
    }
}
