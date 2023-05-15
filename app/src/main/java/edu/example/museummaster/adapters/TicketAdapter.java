package edu.example.museummaster.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.example.museummaster.R;
import edu.example.museummaster.data.data_sourses.category.models.Ticket;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {
    private List<Ticket> ticketList;

    public TicketAdapter(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Ticket ticket = ticketList.get(position);
        holder.bind(ticket);
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public class TicketViewHolder extends RecyclerView.ViewHolder {
        private TextView tariffTextView;
        private TextView purchaseDateTextView;
        private TextView entryDateTextView;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            tariffTextView = itemView.findViewById(R.id.tariffTextView);
            purchaseDateTextView = itemView.findViewById(R.id.purchaseDateTextView);
            entryDateTextView = itemView.findViewById(R.id.entryDateTextView);
        }

        public void bind(Ticket ticket) {
            tariffTextView.setText(ticket.getTariff());
            purchaseDateTextView.setText(ticket.getPurchaseDate());
            entryDateTextView.setText(ticket.getEntryDate());
        }
    }
}
