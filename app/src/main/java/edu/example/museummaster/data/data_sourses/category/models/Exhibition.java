package edu.example.museummaster.data.data_sourses.category.models;

public class Exhibition {
    private Integer id;
    private String title;
    private String photoName;

    public Exhibition(Integer id, String title, String photoName) {
        this.id = id;
        this.title = title;
        this.photoName = photoName;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPhotoName() {
        return photoName;
    }
}
