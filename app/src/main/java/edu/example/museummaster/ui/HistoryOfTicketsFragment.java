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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import edu.example.museummaster.R;
import edu.example.museummaster.adapters.TicketAdapter;
import edu.example.museummaster.data.data_sourses.category.models.Ticket;
import edu.example.museummaster.data.data_sourses.category.repositories.TicketRepository;
import edu.example.museummaster.databinding.FragmentHistoryOfTicketsBinding;

public class HistoryOfTicketsFragment extends Fragment {

    private FragmentHistoryOfTicketsBinding mBinding;
    private TicketRepository mTicketRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentHistoryOfTicketsBinding.inflate(inflater, container, false);
        mTicketRepository = new TicketRepository(requireContext());

        // Устанавливаем цвет статус-бара (для Android 6.0 и выше)
        Window window = requireActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(requireContext().getColor(R.color.black_green));
        }

        // Находим кнопку "Назад" и устанавливаем обработчик события
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });

        // Отображаем список билетов
        showTickets();

        return mBinding.getRoot();
    }

    private void showTickets() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        // Получаем идентификатор пользователя (замените на ваш способ получения идентификатора)
        String userId = currentUser.getUid();

        // Получаем список всех купленных билетов пользователя из репозитория
        mTicketRepository.getTicketsByUserId(userId).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Ticket> ticketList = queryDocumentSnapshots.toObjects(Ticket.class);

                // Создаем и устанавливаем адаптер для списка билетов
                TicketAdapter ticketAdapter = new TicketAdapter(ticketList);
                mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                mBinding.recyclerView.setAdapter(ticketAdapter);
                if (ticketList.isEmpty()) {
                    mBinding.emptyTextView.setVisibility(View.VISIBLE);
                } else {
                    mBinding.emptyTextView.setVisibility(View.GONE);
                }
            }
        });
    }
}
