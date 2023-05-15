package edu.example.museummaster.data.data_sourses.category.repositories;

import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QuerySnapshot;

import edu.example.museummaster.data.data_sourses.category.models.Ticket;
import edu.example.museummaster.data.data_sourses.category.room.root.TicketDatabase;

public class TicketRepository {
    private TicketDatabase database;

    public TicketRepository(Context context) {
        database = TicketDatabase.getInstance(context);
    }

    public Task<DocumentReference> addTicket(String userId, String tariff, String purchaseDate, String entryDate) {
        Ticket ticket = new Ticket(userId, tariff, purchaseDate, entryDate);
        return database.getTicketsCollection().add(ticket);
    }

    public Task<QuerySnapshot> getTicketsByUserId(String userId) {
        CollectionReference ticketsRef = database.getTicketsCollection();
        return ticketsRef.whereEqualTo("userId", userId).get();
    }
}
