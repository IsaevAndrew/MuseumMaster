package edu.example.museummaster.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import edu.example.museummaster.R;
import edu.example.museummaster.adapters.NewsAdapter;
import edu.example.museummaster.data.data_sourses.category.models.NewsElement;
import edu.example.museummaster.ui.viewmodels.NewsViewModel;

public class Home extends Fragment {
//    private Context context;
//    private NewsAdapter mAdapter;
//    private List<NewsElement> mNewsList;
    Fragment fragment14;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottonNavigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    return true;
                case R.id.search:
                    fragment14 = new Search();
                    FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                    transaction2.replace(R.id.container, fragment14).addToBackStack(null);
                    transaction2.commit();
                    return true;
                case R.id.ticket:
                    fragment14 = new Ticket();
                    FragmentTransaction transaction3 = getFragmentManager().beginTransaction();
                    transaction3.replace(R.id.container, fragment14).addToBackStack(null);
                    transaction3.commit();
                    return true;
                case R.id.scanner:
                    fragment14 = new Scanner();
                    FragmentTransaction transaction4 = getFragmentManager().beginTransaction();
                    transaction4.replace(R.id.container, fragment14).addToBackStack(null);
                    transaction4.commit();
                    return true;
                case R.id.profile:
                    fragment14 = new Profile();
                    FragmentTransaction transaction5 = getFragmentManager().beginTransaction();
                    transaction5.replace(R.id.container, fragment14).addToBackStack(null);
                    transaction5.commit();
                    return true;
            }
            return false;
        });
        return view;
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        mNewsList = getNewsList();
//
//        // Создать экземпляр адаптера и назначить его RecyclerView
//        mAdapter = new NewsAdapter(mNewsList);
//        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerView);
//        mRecyclerView.setAdapter(mAdapter);
//
//        // Назначить менеджер макетов RecyclerView
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        mRecyclerView.setLayoutManager(layoutManager);
//
//        // Назначить слушатель кликов на элементы списка
//        mAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                // Обработка клика на элементе списка
//            }
//        });
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

//    private List<NewsElement> getNewsList() {
//        // Получить список данных из источника данных (например, базы данных или сети)
//        List<NewsElement> newsList = new ArrayList<>();
//        // ...
//        return newsList;
//    }
}
