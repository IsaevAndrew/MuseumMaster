package edu.example.museummaster.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.example.museummaster.R;
import edu.example.museummaster.adapters.ViewPagerAdapter;
import edu.example.museummaster.databinding.FragmentExhibitViewpagerBinding;

public class ExhibitViewPagerFragment extends Fragment {
    private FragmentExhibitViewpagerBinding mBinding;
    private int exhibitId;

    public static ExhibitViewPagerFragment newInstance(int exhibitId) {
        ExhibitViewPagerFragment fragment = new ExhibitViewPagerFragment();
        Bundle args = new Bundle();
        args.putInt("exhibitId", exhibitId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            exhibitId = getArguments().getInt("exhibitId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentExhibitViewpagerBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        List<Fragment> fragmentList = new ArrayList<>();

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference connectCollection = firestore.collection("Connect");
        Query query = connectCollection.whereEqualTo("IdRoute", exhibitId).orderBy("IdExhibit");

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    int idExhibit = document.getLong("IdExhibit").intValue();
                    Exhibit fragment = new Exhibit();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", idExhibit);
                    fragment.setArguments(bundle);
                    fragmentList.add(fragment);
                }
                ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentList, getChildFragmentManager(), getLifecycle());
                mBinding.viewPager.setAdapter(adapter);
            } else {
                System.out.println(task.getException());
            }
        });
        ImageButton buttonPrevious = view.findViewById(R.id.buttonPrevious);
        ImageButton buttonNext = view.findViewById(R.id.buttonNext);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);
        buttonNext.setOnClickListener(v -> {
            int currentItem = viewPager.getCurrentItem();
            if (currentItem < viewPager.getAdapter().getItemCount() - 1) {
                viewPager.setCurrentItem(currentItem + 1);
            }
        });
        buttonPrevious.setOnClickListener(v -> {
            int currentItem = viewPager.getCurrentItem();
            if (currentItem > 0) {
                viewPager.setCurrentItem(currentItem - 1);
            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                buttonPrevious.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
                buttonNext.setVisibility(position == viewPager.getAdapter().getItemCount() - 1 ? View.INVISIBLE : View.VISIBLE);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}
