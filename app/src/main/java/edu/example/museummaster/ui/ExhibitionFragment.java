package edu.example.museummaster.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.example.museummaster.R;
import edu.example.museummaster.adapters.ExhibitionAdapter;
import edu.example.museummaster.data.data_sourses.category.models.Exhibition;

public class ExhibitionFragment extends Fragment {
    private Context context;
    private RecyclerView recyclerView;
    private ExhibitionAdapter exhibitionAdapter;
    private List<Exhibition> exhibitionList;

    private FirebaseFirestore firestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exhibition, container, false);

        recyclerView = view.findViewById(R.id.exhibitionsRecyclerView);
        exhibitionList = new ArrayList<>();
        exhibitionAdapter = new ExhibitionAdapter(exhibitionList, getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(exhibitionAdapter);

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottonNavigation);
        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    Fragment homeFragment = new HomeFragment();
                    navigateToFragment(homeFragment);
                    return true;
                case R.id.search:
                    // Already in the ExhibitionFragment
                    return true;
                case R.id.ticket:
                    Fragment ticketFragment = new TicketFragment();
                    navigateToFragment(ticketFragment);
                    return true;
                case R.id.scanner:
                    Fragment scannerFragment = new ScannerFragment();
                    navigateToFragment(scannerFragment);
                    return true;
                case R.id.profile:
                    Fragment profileFragment = new ProfileFragment();
                    navigateToFragment(profileFragment);
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

        firestore = FirebaseFirestore.getInstance();
        loadExhibitionsFromFirestore();

        // Установка слушателя кликов на адаптер
        exhibitionAdapter.setOnItemClickListener(new ExhibitionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int exhibitionId) {
                // Создание и отображение нового фрагмента ExhibitViewPagerFragment
                ExhibitViewPagerFragment fragment = ExhibitViewPagerFragment.newInstance(exhibitionId);
                navigateToFragment(fragment, exhibitionId);
            }
        });

        return view;
    }

    private void loadExhibitionsFromFirestore() {
        firestore.collection("Route").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Integer id = documentSnapshot.getLong("Id").intValue();
                        String title = documentSnapshot.getString("Title");
                        String photoName = documentSnapshot.getString("PhotoName");

                        Exhibition exhibition = new Exhibition(id, title, photoName);
                        exhibitionList.add(exhibition);
                    }
                    exhibitionAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Обработка ошибки при загрузке данных из Firestore
                });
    }

    private void navigateToFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment).addToBackStack(null);
        transaction.commit();
    }

    private void navigateToFragment(Fragment fragment, int exhibitId) {
        Bundle args = new Bundle();
        args.putInt("exhibitId", exhibitId);
        fragment.setArguments(args);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment).addToBackStack(null);
        transaction.commit();
    }

}
