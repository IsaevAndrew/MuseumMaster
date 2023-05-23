package edu.example.museummaster.data.data_sourses.category.models;

public class Favourite {
    private String userId;
    private int exhibitId;
    private String exhibitTitle;

    public Favourite(String userId, int exhibitId, String exhibitTitle) {
        this.userId = userId;
        this.exhibitId = exhibitId;
        this.exhibitTitle = exhibitTitle;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getExhibitId() {
        return exhibitId;
    }

    public void setExhibitId(int exhibitId) {
        this.exhibitId = exhibitId;
    }

    public String getExhibitTitle() {
        return exhibitTitle;
    }

    public void setExhibitTitle(String exhibitTitle) {
        this.exhibitTitle = exhibitTitle;
    }
}
