package edu.example.museummaster.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import edu.example.museummaster.R;
import edu.example.museummaster.databinding.FragmentAuthorizationBinding;
import edu.example.museummaster.ui.viewmodels.AuthState;
import edu.example.museummaster.ui.viewmodels.AuthViewModel;

public class Authorization extends Fragment {
    FragmentAuthorizationBinding mBinding;
    private AuthViewModel authViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentAuthorizationBinding.inflate(inflater, container, false);
        Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getContext().getColor(R.color.black_green));
        }

        mBinding.newacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        mBinding.btnaut.setOnClickListener(v -> {
            String email = mBinding.emailEditText.getText().toString().trim();
            String password = mBinding.passwordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(requireContext(), "Введите адрес электронной почты", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(requireContext(), "Введите пароль", Toast.LENGTH_SHORT).show();
            } else {
                authViewModel.loginUser(email, password);
            }
        });
        authViewModel.getAuthStateLiveData().observe(getViewLifecycleOwner(), authState -> {
            if (authState == AuthState.AUTHENTICATED) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, new HomeFragment())
                        .commit();
            }
        });
        authViewModel.getErrorMessageLiveData().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}
