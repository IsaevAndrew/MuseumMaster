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
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

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
        args.putInt("id", exhibitId);  // Use "id" as the key
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentExhibitBinding.inflate(inflater, container, false);
        // Создаем объект MediaPlayer и загружаем аудиофайл
        // Находим кнопку и SeekBar в макете и устанавливаем обработчики событий
        playButton = mBinding.buttonPlay;
        audioSeekBar = mBinding.seekBar;
        playButton.setOnClickListener(this);
        audioSeekBar.setOnSeekBarChangeListener(this);

        // Получаем ID выставки из аргументов и устанавливаем его в TextView
        long id = getArguments().getInt("id", 1);

        mBinding.id.setText(mBinding.id.getText() + String.valueOf(id));

        // Устанавливаем цвет статус-бара (для Android 6.0 и выше)
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
                    // Создание MediaPlayer
                    mediaPlayer = MediaPlayer.create(getContext(), getResources().getIdentifier(exhibit.getAudioName(), "raw", requireContext().getPackageName()));
                    // Устанавливаем максимальное значение SeekBar равным длительности аудиофайла в миллисекундах
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

        // Находим кнопку "Назад" и устанавливаем обработчик события
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        // Инициализация Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        // Проверяем, добавлен ли экспонат в избранное
        checkIfExhibitIsLiked();

        return mBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (v == playButton) {
            // Если аудио воспроизводится, останавливаем воспроизведение и меняем надпись на кнопке
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
            // Перематываем аудиофайл на выбранную позицию
            mediaPlayer.seekTo(progress);
            currentPos = progress;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // При начале перемотки SeekBar останавливаем обновление SeekBar
        handler.removeCallbacks(updateSeekBar);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // При окончании перемотки SeekBar начинаем обновление SeekBar
        handler.postDelayed(updateSeekBar, UPDATE_INTERVAL);
    }

    private Runnable updateSeekBar = new Runnable() {
        @Override
        public void run() {
            if (isPlaying) {
                // Обновляем положение SeekBar каждые 1000 миллисекунд
                currentPos = mediaPlayer.getCurrentPosition();
                audioSeekBar.setProgress(currentPos);
            }
            handler.postDelayed(this, UPDATE_INTERVAL);
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        // Останавливаем воспроизведение аудиофайла и очищаем MediaPlayer
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

            // Создаем новый документ в коллекции "Favourite" с данными пользователя и экспоната
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

            // Удаляем документ из коллекции "Favourite" с данными пользователя и экспоната
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

            // Проверяем наличие документа в коллекции "Favourite" с данными пользователя и экспоната
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
