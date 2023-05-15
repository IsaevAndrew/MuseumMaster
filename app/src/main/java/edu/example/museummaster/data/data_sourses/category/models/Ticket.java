package edu.example.museummaster.data.data_sourses.category.models;

public class Ticket {
    private String userId;
    private String tariff;
    private String purchaseDate;
    private String entryDate;

    public Ticket() {
        // Пустой конструктор необходим для Firebase Firestore
    }

    public Ticket(String userId, String tariff, String purchaseDate, String entryDate) {
        this.userId = userId;
        this.tariff = tariff;
        this.purchaseDate = purchaseDate;
        this.entryDate = entryDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }
}
