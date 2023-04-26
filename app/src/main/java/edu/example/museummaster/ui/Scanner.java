package edu.example.museummaster.ui;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.Result;

import edu.example.museummaster.R;

public class Scanner extends Fragment {
    private CodeScanner mCodeScanner;
    private Context context;
    Fragment fragment14;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private static final int REQUEST_CAMERA_PERMISSION = 1;

    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(getContext(), "Camera permission is required to use the scanner", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void startCamera() {
        if (!hasCameraPermission()) {
            requestCameraPermission();
            return;
        }

        final Activity activity = getActivity();
        CodeScannerView scannerView = getView().findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(activity, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, result.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
        mCodeScanner.startPreview();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scanner, container, false);
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottonNavigation);
        bottomNavigationView.setSelectedItemId(R.id.scanner);
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
                    return true;
                case R.id.profile:
                    fragment14 = new Profile();
                    FragmentTransaction transaction5 = getFragmentManager().beginTransaction();
                    transaction5.replace(R.id.container, fragment14).addToBackStack(null);
                    transaction5.commit();
                    return true;
            }
            return false;
        });
        final Activity activity = getActivity();
        int MY_PERMISSIONS_REQUEST_CAMERA=0;
// Here, this is the current activity
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA))
            {}
            else
            {
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA );
            }
        }

        CodeScannerView scannerView = view.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(activity, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fragment14 = new Exhibit();
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", Integer.parseInt(result.getText()));
                        fragment14.setArguments(bundle);
                        FragmentTransaction transaction5 = getFragmentManager().beginTransaction();
                        transaction5.replace(R.id.container, fragment14).addToBackStack(null);
                        transaction5.commit();
                        Toast.makeText(activity, result.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}
