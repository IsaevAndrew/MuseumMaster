package edu.example.museummaster.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import edu.example.museummaster.R;
import edu.example.museummaster.adapters.NewsAdapter;
import edu.example.museummaster.ui.viewmodels.NewsViewModel;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private NewsViewModel newsViewModel;
    Fragment fragment14;

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottonNavigation);
//        bottomNavigationView.setSelectedItemId(R.id.home);
//        bottomNavigationView.setOnItemSelectedListener(item -> {
//            switch (item.getItemId()){
//                case R.id.home:
//                    return true;
//                case R.id.search:
//                    fragment14 = new Search();
//                    FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
//                    transaction2.replace(R.id.container, fragment14).addToBackStack(null);
//                    transaction2.commit();
//                    return true;
//                case R.id.ticket:
//                    fragment14 = new Ticket();
//                    FragmentTransaction transaction3 = getFragmentManager().beginTransaction();
//                    transaction3.replace(R.id.container, fragment14).addToBackStack(null);
//                    transaction3.commit();
//                    return true;
//                case R.id.scanner:
//                    fragment14 = new Scanner();
//                    FragmentTransaction transaction4 = getFragmentManager().beginTransaction();
//                    transaction4.replace(R.id.container, fragment14).addToBackStack(null);
//                    transaction4.commit();
//                    return true;
//                case R.id.profile:
//                    fragment14 = new Profile();
//                    FragmentTransaction transaction5 = getFragmentManager().beginTransaction();
//                    transaction5.replace(R.id.container, fragment14).addToBackStack(null);
//                    transaction5.commit();
//                    return true;
//            }
//            return false;
//        });
//        return view;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        adapter = new NewsAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        newsViewModel.getAllNewsLiveData().observe(getViewLifecycleOwner(), newsList -> {
            adapter.setNewsList(newsList);
        });

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottonNavigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    return true;
                case R.id.search:
                    fragment14 = new ExhibitionFragment();
                    FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                    transaction2.replace(R.id.container, fragment14).addToBackStack(null);
                    transaction2.commit();
                    return true;
                case R.id.ticket:
                    fragment14 = new TicketFragment();
                    FragmentTransaction transaction3 = getFragmentManager().beginTransaction();
                    transaction3.replace(R.id.container, fragment14).addToBackStack(null);
                    transaction3.commit();
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
