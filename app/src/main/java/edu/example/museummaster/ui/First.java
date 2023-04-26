package edu.example.museummaster.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import edu.example.museummaster.R;
import edu.example.museummaster.databinding.FragmentFirstBinding;

public class First extends Fragment {
    FragmentFirstBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentFirstBinding.inflate(inflater, container, false);
        Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getContext().getColor(R.color.black_green));
        }
        mBinding.aut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Переход на второй фрагмент
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, new Authorization()).addToBackStack(null)
                        .commit();
            }
        });
        mBinding.reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Переход на второй фрагмент
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, new RegistrationEmail()).addToBackStack(null)
                        .commit();
            }
        });
        return mBinding.getRoot();
    }
}
