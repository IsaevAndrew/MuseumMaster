package edu.example.museummaster.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.fragment.app.Fragment;

import edu.example.museummaster.R;
import edu.example.museummaster.databinding.FragmentExhibitBinding;
import edu.example.museummaster.databinding.FragmentFirstBinding;

public class Exhibit extends Fragment {
    FragmentExhibitBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mBinding = FragmentExhibitBinding.inflate(inflater, container, false);
        Bundle bundle = this.getArguments();
        int id = 0;
        if (bundle != null) {
            id = bundle.getInt("id", 0);
        }
        mBinding.id.setText(mBinding.id.getText()+String.valueOf(id));
        Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getContext().getColor(R.color.black_green));
        }
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();

            }
        });

        return mBinding.getRoot();
    }
}
