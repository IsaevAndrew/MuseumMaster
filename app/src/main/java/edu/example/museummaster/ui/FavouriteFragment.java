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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.example.museummaster.R;
import edu.example.museummaster.adapters.FavoriteAdapter;
import edu.example.museummaster.data.data_sourses.category.models.Favourite;
import edu.example.museummaster.databinding.FragmentFavouriteBinding;
import edu.example.museummaster.ui.ProfileFragment;

public class FavouriteFragment extends Fragment {

    private FragmentFavouriteBinding mBinding;
    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;
    private List<Favourite> favouriteList;
    private FirebaseFirestore firestore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentFavouriteBinding.inflate(inflater, container, false);
        View rootView = mBinding.getRoot();

        // Устанавливаем цвет статус-бара (для Android 6.0 и выше)
        Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getContext().getColor(R.color.black_green));
        }

        // Находим кнопку "Назад" и устанавливаем обработчик события
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, new ProfileFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Инициализируем Firestore
        firestore = FirebaseFirestore.getInstance();

        // Инициализируем список избранных элементов
        favouriteList = new ArrayList<>();

        // Находим RecyclerView в макете
        recyclerView = rootView.findViewById(R.id.recyclerView);

        // Устанавливаем менеджер компоновки для RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // Создаем адаптер и устанавливаем его для RecyclerView
        adapter = new FavoriteAdapter(favouriteList, getContext());
        recyclerView.setAdapter(adapter);

        // Получаем данные о любимых экспонатах из Firestore
        fetchFavouriteItems();

        return rootView;
    }

    // Метод для получения данных о любимых экспонатах из Firestore
    private void fetchFavouriteItems() {
        // Здесь вам нужно заменить "userId" на фактический идентификатор пользователя
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentUser.getUid();

        // Получаем коллекцию "Favourite" из Firestore по идентификатору пользователя
        firestore.collection("Favourite")
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                // Retrieve the exhibitId as an Integer
                                Integer exhibitId = document.getLong("exhibitId").intValue();

                                String title = document.getString("exhibitTitle");

                                // Create the Favourite object and add it to the list
                                Favourite favourite = new Favourite(userId, exhibitId, title);
                                favouriteList.add(favourite);
                            }
                            // Notify the adapter of the data change
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
