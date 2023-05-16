package edu.example.museummaster.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.example.museummaster.R;
import edu.example.museummaster.databinding.FragmentProfileBinding;
import edu.example.museummaster.ui.viewmodels.AuthState;
import edu.example.museummaster.ui.viewmodels.AuthViewModel;

public class ProfileFragment extends Fragment {
    private Context context;
    private AuthViewModel authViewModel;
    FragmentProfileBinding mBinding;
    Fragment fragment14;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottonNavigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mBinding.userEmailTextView.setText(currentUser.getEmail());
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    fragment14 = new HomeFragment();
                    FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                    transaction1.replace(R.id.container, fragment14).addToBackStack(null);
                    transaction1.commit();
                    return true;
                case R.id.search:
                    fragment14 = new ExhibitionFragment();
                    FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                    transaction2.replace(R.id.container, fragment14).addToBackStack(null);
                    transaction2.commit();
                    return true;
                case R.id.ticket:
                    fragment14 = new TicketFragment();
                    FragmentTransaction transaction3 = getFragmentManager().beginTransaction();
                    transaction3.replace(R.id.container, fragment14).addToBackStack(null);
                    transaction3.commit();
                    return true;
                case R.id.scanner:
                    fragment14 = new ScannerFragment();
                    FragmentTransaction transaction4 = getFragmentManager().beginTransaction();
                    transaction4.replace(R.id.container, fragment14).addToBackStack(null);
                    transaction4.commit();
                case R.id.profile:
                    return true;
            }
            return false;
        });
        view.findViewById(R.id.change_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, new Change_password()).addToBackStack(null)
                        .commit();
            }
        });
        view.findViewById(R.id.favourite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Переход на второй фрагмент
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, new Favourite()).addToBackStack(null)
                        .commit();
            }
        });
        view.findViewById(R.id.future_tickets).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Переход на второй фрагмент
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, new My_tickets()).addToBackStack(null)
                        .commit();
            }
        });
        view.findViewById(R.id.last_tickets).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Переход на второй фрагмент
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, new HistoryOfTicketsFragment()).addToBackStack(null)
                        .commit();
            }

        });
        view.findViewById(R.id.faq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Информация")
                        .setMessage("Данное приложение создано в рамках проекта IT Академия Samsung")
                        .setPositiveButton("Закрыть", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Обработка нажатия кнопки "Закрыть"
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().moveTaskToBack(true);
            }
        });
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        Button logoutButton = mBinding.logoutButton;
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });
        Button deleteAccountButton = mBinding.deleteAccount;
        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAccount();
            }
        });

        authViewModel.getAuthStateLiveData().observe(getViewLifecycleOwner(), authState -> {
            if (authState == AuthState.UNAUTHENTICATED) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, new First()).addToBackStack(null)
                        .commit();
            }
        });

    }
    private void deleteAccount() {
        authViewModel.deleteAccount();
    }

    private void logoutUser() {
        authViewModel.logoutUser();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

}
