package edu.example.museummaster.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;

import java.util.Calendar;

import edu.example.museummaster.R;
import edu.example.museummaster.data.data_sourses.category.models.Ticket;
import edu.example.museummaster.data.data_sourses.category.repositories.TicketRepository;

public class TicketFragment extends Fragment {
    private Context context;
    Fragment fragment14;
    private TicketRepository ticketRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        View view = inflater.inflate(R.layout.fragment_ticket, container, false);
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottonNavigation);
        bottomNavigationView.setSelectedItemId(R.id.ticket);
        Button btnBuyTicket = view.findViewById(R.id.purchaseButton);
        Spinner spinner = view.findViewById(R.id.tariffSpinner);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

// Устанавливаем время в полночь
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        DatePicker datePicker = view.findViewById(R.id.datePicker);
// Устанавливаем минимальную дату для DatePicker
        datePicker.setMinDate(calendar.getTimeInMillis());
        // Inside onCreateView() method of TicketFragment
        ticketRepository = new TicketRepository(getActivity().getApplicationContext());


        datePicker.setMinDate(Calendar.getInstance().getTimeInMillis());

        btnBuyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tariff = spinner.getSelectedItem().toString();
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();

                // Get the current date and format it
                Calendar currentDate = Calendar.getInstance();
                String purchaseDate = String.format("%d.%d.%d", currentDate.get(Calendar.DAY_OF_MONTH),
                        currentDate.get(Calendar.MONTH) + 1, currentDate.get(Calendar.YEAR));
                String entryDate = String.format("%d.%d.%d", day, month+1, year);

                // Create a new ticket with the necessary fields
                Ticket ticketData = new Ticket(currentUser.getUid(), tariff, purchaseDate, entryDate);

                // Add the ticket to the database
                ticketRepository.addTicket(ticketData.getUserId(), ticketData.getTariff(), ticketData.getPurchaseDate(), ticketData.getEntryDate())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Билет куплен")
                                        .setMessage(String.format("Тариф: %s\nДата: %d.%d.%d", tariff, day, month + 1, year))
                                        .setPositiveButton("Ок!", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                spinner.setSelection(0);
                                                datePicker.updateDate(Calendar.getInstance().get(Calendar.YEAR),
                                                        Calendar.getInstance().get(Calendar.MONTH),
                                                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                                                dialog.dismiss();
                                            }
                                        })
                                        .show();
                            } else {
                                // Handle the error
                            }
                        });
            }
        });


        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    fragment14 = new HomeFragment();
                    FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                    transaction1.replace(R.id.container, fragment14).addToBackStack(null);
                    transaction1.commit();
                    return true;
                case R.id.search:
                    fragment14 = new SearchFragment();
                    FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                    transaction2.replace(R.id.container, fragment14).addToBackStack(null);
                    transaction2.commit();
                    return true;
                case R.id.ticket:
                    return true;
                case R.id.scanner:
                    fragment14 = new ScannerFragment();
                    FragmentTransaction transaction4 = getFragmentManager().beginTransaction();
                    transaction4.replace(R.id.container, fragment14).addToBackStack(null);
                    transaction4.commit();
                    return true;
                case R.id.profile:
                    fragment14 = new ProfileFragment();
                    FragmentTransaction transaction5 = getFragmentManager().beginTransaction();
                    transaction5.replace(R.id.container, fragment14).addToBackStack(null);
                    transaction5.commit();
                    return true;
            }
            return false;
        });
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().moveTaskToBack(true);
            }
        });

        return view;
    }
}
