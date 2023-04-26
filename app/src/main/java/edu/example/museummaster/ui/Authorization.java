package edu.example.museummaster.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import edu.example.museummaster.R;
import edu.example.museummaster.databinding.FragmentAuthorizationBinding;

public class Authorization extends Fragment {
    FragmentAuthorizationBinding mBinding;
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
                // Переход на второй фрагмент
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, new RegistrationEmail()).addToBackStack(null)
                        .commit();
            }
        });
        mBinding.btnaut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Переход на второй фрагмент
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, new Home()).addToBackStack(null)
                        .commit();
            }
        });
        return mBinding.getRoot();
    }

}
