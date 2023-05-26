package edu.example.museummaster.ui;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.example.museummaster.R;
import edu.example.museummaster.data.data_sourses.category.models.Favourite;
import edu.example.museummaster.databinding.FragmentExhibitBinding;
import edu.example.museummaster.ui.viewmodels.ExhibitViewModel;

public class Exhibit extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private ExhibitViewModel exhibitViewModel;
    private FragmentExhibitBinding mBinding;
    private Button playButton;
    private SeekBar audioSeekBar;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private boolean isPlaying = false;
    private boolean isLiked = false;
    private int currentPos = 0;
    private final int UPDATE_INTERVAL = 1000;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private String userId;

    public static Exhibit newInstance(int exhibitId) {
        Exhibit fragment = new Exhibit();
        Bundle args = new Bundle();
        args.putInt("id", exhibitId);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentExhibitBinding.inflate(inflater, container, false);
        playButton = mBinding.buttonPlay;
        audioSeekBar = mBinding.seekBar;
        playButton.setOnClickListener(this);
        audioSeekBar.setOnSeekBarChangeListener(this);
        long id = getArguments().getInt("id", 1);

        mBinding.id.setText(mBinding.id.getText() + String.valueOf(id));
        Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getContext().getColor(R.color.black_green));
        }
        System.out.println(id);

        exhibitViewModel = new ViewModelProvider(this).get(ExhibitViewModel.class);
        exhibitViewModel.getExhibitById(id).observe(getViewLifecycleOwner(), new Observer<edu.example.museummaster.data.data_sourses.category.models.Exhibit>() {
            @Override
            public void onChanged(edu.example.museummaster.data.data_sourses.category.models.Exhibit exhibit) {
                if (exhibit != null) {
                    mBinding.id.setText(exhibit.getName());
                    mBinding.textViewDescription.setText(exhibit.getDescription());
                    mBinding.imageView.setImageResource(getResources().getIdentifier(exhibit.getPhotoName(), "drawable", getContext().getPackageName()));
                    mediaPlayer = MediaPlayer.create(getContext(), getResources().getIdentifier(exhibit.getAudioName(), "raw", requireContext().getPackageName()));
                    audioSeekBar.setMax(mediaPlayer.getDuration());
                }
            }
        });

        mBinding.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLiked) {
                    removeExhibitFromFavorites();
                } else {
                    addExhibitToFavorites();
                }
            }
        });

        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        checkIfExhibitIsLiked();

        return mBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (v == playButton) {
            if (isPlaying) {
                mediaPlayer.pause();
                playButton.setText("Play");
                isPlaying = false;
                handler.removeCallbacks(updateSeekBar);
            } else {  // Если аудио не воспроизводится, начинаем воспроизведение и меняем надпись на кнопке
                mediaPlayer.start();
                playButton.setText("Pause");
                isPlaying = true;
                handler.postDelayed(updateSeekBar, UPDATE_INTERVAL);
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            mediaPlayer.seekTo(progress);
            currentPos = progress;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        handler.removeCallbacks(updateSeekBar);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        handler.postDelayed(updateSeekBar, UPDATE_INTERVAL);
    }

    private Runnable updateSeekBar = new Runnable() {
        @Override
        public void run() {
            if (isPlaying) {
                currentPos = mediaPlayer.getCurrentPosition();
                audioSeekBar.setProgress(currentPos);
            }
            handler.postDelayed(this, UPDATE_INTERVAL);
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        isPlaying = false;
        handler.removeCallbacks(updateSeekBar);
    }

    private void addExhibitToFavorites() {
        if (userId != null) {
            String exhibitId = String.valueOf(getArguments().getInt("id"));
            String exhibitTitle = mBinding.id.getText().toString();

            DocumentReference documentReference = firestore.collection("Favourite")
                    .document(userId + "_" + exhibitId);
            documentReference.set(new Favourite(userId, Integer.parseInt(exhibitId), exhibitTitle))

                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            isLiked = true;
                            mBinding.like.setImageResource(R.drawable.favorite2);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Ошибка при добавлении экспоната в избранное
                        }
                    });
        }
    }

    private void removeExhibitFromFavorites() {
        if (userId != null) {
            String exhibitId = String.valueOf(getArguments().getInt("id"));
            DocumentReference documentReference = firestore.collection("Favourite")
                    .document(userId + "_" + exhibitId);
            documentReference.delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            isLiked = false;
                            mBinding.like.setImageResource(R.drawable.favorite);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Ошибка при удалении экспоната из избранного
                        }
                    });
        }
    }

    private void checkIfExhibitIsLiked() {
        if (userId != null) {
            String exhibitId = String.valueOf(getArguments().getInt("id"));
            DocumentReference documentReference = firestore.collection("Favourite")
                    .document(userId + "_" + exhibitId);
            documentReference.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                isLiked = true;
                                mBinding.like.setImageResource(R.drawable.favorite2);
                            } else {
                                isLiked = false;
                                mBinding.like.setImageResource(R.drawable.favorite);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Ошибка при проверке наличия экспоната в избранном
                        }
                    });
        }
    }
}
