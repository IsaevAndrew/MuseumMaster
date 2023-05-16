package edu.example.museummaster.data.data_sourses.category.models;

public class Exhibition {
    private String title;
    private String photoName;

    public Exhibition(String title, String photoName) {
        this.title = title;
        this.photoName = photoName;
    }

    public String getTitle() {
        return title;
    }

    public String getPhotoName() {
        return photoName;
    }
}
