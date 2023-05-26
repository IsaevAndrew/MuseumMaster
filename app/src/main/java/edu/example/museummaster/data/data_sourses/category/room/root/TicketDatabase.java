package edu.example.museummaster.data.data_sourses.category.room.root;

import android.content.Context;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class TicketDatabase {
    private static final String COLLECTION_TICKETS = "Tickets";
    private FirebaseFirestore firestore;

    private static TicketDatabase instance;

    private TicketDatabase(Context context) {
        firestore = FirebaseFirestore.getInstance();
    }

    public static synchronized TicketDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new TicketDatabase(context);
        }
        return instance;
    }
    public CollectionReference getTicketsCollection() {
        return firestore.collection(COLLECTION_TICKETS);
    }
}
