package edu.example.museummaster.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;

import edu.example.museummaster.R;
import edu.example.museummaster.databinding.FragmentChangePasswordBinding;
import edu.example.museummaster.ui.viewmodels.AuthViewModel;

public class Change_password extends Fragment {

    private FragmentChangePasswordBinding mBinding;
    private AuthViewModel authViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentChangePasswordBinding.inflate(inflater, container, false);

        // Устанавливаем цвет статус-бара (для Android 6.0 и выше)
        Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getContext().getColor(R.color.black_green));
        }

        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        Button changePasswordButton = mBinding.btn;
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

        // Находим кнопку "Назад" и устанавливаем обработчик события
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, new ProfileFragment()).addToBackStack(null)
                        .commit();
            }
        });

        return mBinding.getRoot();
    }

    private void changePassword() {
        String currentPassword = mBinding.currentPasswordEditText.getText().toString();
        String newPassword = mBinding.newPasswordEditText.getText().toString();
        String confirmPassword = mBinding.confirmPasswordEditText.getText().toString();

        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(requireContext(), "New password and confirm password do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);
            user.reauthenticate(credential)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            user.updatePassword(newPassword)
                                    .addOnCompleteListener(passwordUpdateTask -> {
                                        if (passwordUpdateTask.isSuccessful()) {
                                            Toast.makeText(requireContext(), "Password successfully changed", Toast.LENGTH_SHORT).show();
                                            getParentFragmentManager().beginTransaction()
                                                    .replace(R.id.container, new ProfileFragment()).addToBackStack(null)
                                                    .commit();
                                        } else {
                                            Toast.makeText(requireContext(), "Failed to change password. Please try again.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(requireContext(), "Incorrect current password", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}
