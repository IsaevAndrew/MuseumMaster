package edu.example.museummaster.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import edu.example.museummaster.data.data_sourses.category.repositories.TicketRepository;

public class TicketViewModel extends AndroidViewModel {
    private TicketRepository ticketRepository;

    public TicketViewModel(@NonNull Application application) {
        super(application);
        ticketRepository = new TicketRepository(application);
    }

    public void addTicket(String userId, String tariff, String purchaseDate, String entryDate) {
        ticketRepository.addTicket(userId, tariff, purchaseDate, entryDate);
    }
}
