package edu.example.museummaster.ui;

import android.media.MediaPlayer;
import android.net.Uri;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import edu.example.museummaster.R;
import edu.example.museummaster.databinding.FragmentExhibitBinding;
import edu.example.museummaster.ui.viewmodels.ExhibitViewModel;

public class Exhibit extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private ExhibitViewModel exhibitViewModel;
    private TextView nameTextView;
    private TextView descriptionTextView;
    private ImageView imageView;

    private FragmentExhibitBinding mBinding;
    private Button playButton;
    private SeekBar audioSeekBar;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private boolean isPlaying = false;
    private boolean like = false;
    private int currentPos = 0;
    private final int UPDATE_INTERVAL = 1000;

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
        long id = getArguments().getInt("id", 0);
        mBinding.id.setText(mBinding.id.getText() + String.valueOf(id));

        // Устанавливаем цвет статус-бара (для Android 6.0 и выше)
        Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getContext().getColor(R.color.black_green));
        }

        exhibitViewModel = new ViewModelProvider(this).get(ExhibitViewModel.class);
        exhibitViewModel.getExhibitById(id).observe(getViewLifecycleOwner(), new Observer<edu.example.museummaster.data.data_sourses.category.models.Exhibit>() {
            @Override
            public void onChanged(edu.example.museummaster.data.data_sourses.category.models.Exhibit exhibit) {
                if (exhibit != null) {
                    mBinding.id.setText(exhibit.getName());
                    mBinding.textViewDescription.setText(exhibit.getDescription());
                    System.out.println(exhibit.getPhotoName());
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
                if (like) {
                    like = false;
                    mBinding.like.setImageResource(R.drawable.favorite2);
                } else {
                    like = true;
                    mBinding.like.setImageResource(R.drawable.favorite);
                }
            }
        });
        // Находим кнопку "Назад" и устанавливаем обработчик события
        mBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, new Scanner()  ).addToBackStack(null)
                        .commit();
            }
        });
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
}
