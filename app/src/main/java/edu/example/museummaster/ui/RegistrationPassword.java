package edu.example.museummaster.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.fragment.app.Fragment;

import edu.example.museummaster.R;
import edu.example.museummaster.databinding.FragmentRegistrationPasswordBinding;


public class RegistrationPassword extends Fragment {
    FragmentRegistrationPasswordBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentRegistrationPasswordBinding.inflate(inflater, container, false);
        Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getContext().getColor(R.color.black_green));
        }
        mBinding.auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, new Authorization()).addToBackStack(null)
                        .commit();
            }
        });
        mBinding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Переход на второй фрагмент
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, new CorrectRegister()).addToBackStack(null)
                        .commit();
            }
        });
        return mBinding.getRoot();
    }
}
