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
import edu.example.museummaster.R;
import edu.example.museummaster.databinding.FragmentFavouriteBinding;
import edu.example.museummaster.databinding.FragmentMyTicketsBinding;

public class Favourite extends Fragment{

    private FragmentFavouriteBinding mBinding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentFavouriteBinding.inflate(inflater, container, false);

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
                        .replace(R.id.container, new Profile()).addToBackStack(null)
                        .commit();
            }
        });
        return mBinding.getRoot();
    }

}
