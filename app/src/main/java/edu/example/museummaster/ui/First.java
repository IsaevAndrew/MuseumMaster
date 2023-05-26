package edu.example.museummaster.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import edu.example.museummaster.R;
import edu.example.museummaster.databinding.FragmentFirstBinding;
import edu.example.museummaster.ui.viewmodels.AuthState;
import edu.example.museummaster.ui.viewmodels.AuthViewModel;

public class First extends Fragment {
    FragmentFirstBinding mBinding;
    private AuthViewModel authViewModel;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        authViewModel.getAuthStateLiveData().observe(getViewLifecycleOwner(), authState -> {
            if (authState == AuthState.AUTHENTICATED) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, new HomeFragment())
                        .commit();
            }
        });
    }
}
