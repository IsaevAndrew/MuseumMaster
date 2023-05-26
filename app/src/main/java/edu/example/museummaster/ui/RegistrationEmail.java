package edu.example.museummaster.ui;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import edu.example.museummaster.R;
import edu.example.museummaster.databinding.FragmentRegistrationEmailBinding;
import edu.example.museummaster.ui.viewmodels.AuthViewModel;

public class RegistrationEmail extends Fragment {
    private FragmentRegistrationEmailBinding mBinding;
    private AuthViewModel authViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentRegistrationEmailBinding.inflate(inflater, container, false);
        Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getContext().getColor(R.color.black_green));
        }

        mBinding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mBinding.emailEditText.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(requireContext(), "Введите адрес электронной почты", Toast.LENGTH_LONG).show();
                } else if (!isEmailValid(email)) {
                    Toast.makeText(requireContext(), "Введите корректный адрес электронной почты", Toast.LENGTH_LONG).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("email", email);

                    RegistrationPassword fragment = new RegistrationPassword();
                    fragment.setArguments(bundle);

                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.container, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        mBinding.auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, new Authorization()).addToBackStack(null)
                        .commit();
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
