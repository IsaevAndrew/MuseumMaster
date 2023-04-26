package edu.example.museummaster.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.example.museummaster.R;
import edu.example.museummaster.databinding.FragmentAuthorizationBinding;
import edu.example.museummaster.databinding.FragmentProfileBinding;

public class Profile extends Fragment {
    private Context context;
    FragmentProfileBinding mBinding;
    Fragment fragment14;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottonNavigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    fragment14 = new Home();
                    FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                    transaction1.replace(R.id.container, fragment14).addToBackStack(null);
                    transaction1.commit();
                    return true;
                case R.id.search:
                    fragment14 = new Search();
                    FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                    transaction2.replace(R.id.container, fragment14).addToBackStack(null);
                    transaction2.commit();
                    return true;
                case R.id.ticket:
                    fragment14 = new Ticket();
                    FragmentTransaction transaction3 = getFragmentManager().beginTransaction();
                    transaction3.replace(R.id.container, fragment14).addToBackStack(null);
                    transaction3.commit();
                    return true;
                case R.id.scanner:
                    fragment14 = new Scanner();
                    FragmentTransaction transaction4 = getFragmentManager().beginTransaction();
                    transaction4.replace(R.id.container, fragment14).addToBackStack(null);
                    transaction4.commit();
                case R.id.profile:
                    return true;
            }
            return false;
        });
        view.findViewById(R.id.logout_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Переход на второй фрагмент
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, new First()).addToBackStack(null)
                        .commit();
            }

        });
        return view;
    }
}
