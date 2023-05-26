package edu.example.museummaster.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.example.museummaster.R;
import edu.example.museummaster.adapters.TicketAdapter;
import edu.example.museummaster.data.data_sourses.category.models.Ticket;
import edu.example.museummaster.data.data_sourses.category.repositories.TicketRepository;
import edu.example.museummaster.databinding.FragmentMyTicketsBinding;

public class My_tickets extends Fragment {

    private FragmentMyTicketsBinding mBinding;
    private TicketRepository ticketRepository;
    private RecyclerView recyclerView;
    private TicketAdapter ticketAdapter;
    private List<Ticket> ticketList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentMyTicketsBinding.inflate(inflater, container, false);
        Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getContext().getColor(R.color.black_green));
        }
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, new ProfileFragment()).addToBackStack(null)
                        .commit();
            }
        });

        ticketRepository = new TicketRepository(getActivity().getApplicationContext());
        recyclerView = mBinding.ticketRecyclerView;
        ticketList = new ArrayList<>();
        ticketAdapter = new TicketAdapter(ticketList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(ticketAdapter);
        loadTickets();
        return mBinding.getRoot();
    }

    private void loadTickets() {
        Calendar calendar = Calendar.getInstance();
        final Date currentDate = calendar.getTime(); // Create a final variable

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        ticketRepository.getTicketsByUserId(currentUser.getUid())
                .addOnSuccessListener(querySnapshot -> {
                    ticketList.clear();
                    for (Ticket ticket : querySnapshot.toObjects(Ticket.class)) {
                        try {
                            String entryDateString = ticket.getEntryDate();
                            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                            Date entryDate = format.parse(entryDateString);
                            if (entryDate != null) {
                                calendar.setTime(entryDate);
                                calendar.set(Calendar.HOUR_OF_DAY, 0);
                                calendar.set(Calendar.MINUTE, 0);
                                calendar.set(Calendar.SECOND, 0);
                                calendar.set(Calendar.MILLISECOND, 0);
                                Date entryDateStartOfDay = calendar.getTime();

                                calendar.setTime(currentDate);
                                calendar.set(Calendar.HOUR_OF_DAY, 0);
                                calendar.set(Calendar.MINUTE, 0);
                                calendar.set(Calendar.SECOND, 0);
                                calendar.set(Calendar.MILLISECOND, 0);
                                Date currentDateStartOfDay = calendar.getTime();

                                if (entryDateStartOfDay.compareTo(currentDateStartOfDay) >= 0) {
                                    ticketList.add(ticket);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    ticketAdapter.notifyDataSetChanged();
                    if (ticketList.isEmpty()) {
                        mBinding.emptyTextView.setVisibility(View.VISIBLE);
                    } else {
                        mBinding.emptyTextView.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(e -> {
                });
    }
}
