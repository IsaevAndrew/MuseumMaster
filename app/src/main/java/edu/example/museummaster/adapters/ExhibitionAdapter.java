package edu.example.museummaster.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.example.museummaster.R;
import edu.example.museummaster.data.data_sourses.category.models.Exhibition;

public class ExhibitionAdapter extends RecyclerView.Adapter<ExhibitionAdapter.ExhibitionViewHolder> {
    private List<Exhibition> exhibitionList;
    private Context context;

    public ExhibitionAdapter(List<Exhibition> exhibitionList, Context context) {
        this.exhibitionList = exhibitionList;
        this.context = context;
    }

    @NonNull
    @Override
    public ExhibitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exhibition_card_item, parent, false);
        return new ExhibitionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExhibitionViewHolder holder, int position) {
        Exhibition exhibition = exhibitionList.get(position);
        holder.bind(exhibition);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Обработка клика на карточку экскурсии
                // Здесь вы можете выполнить нужные действия при клике, например, открыть новый фрагмент
                // или перейти на другую активность
            }
        });
    }

    @Override
    public int getItemCount() {
        return exhibitionList.size();
    }

    public class ExhibitionViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private ImageView photoImageView;

        public ExhibitionViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            photoImageView = itemView.findViewById(R.id.photoImageView);
        }

        public void bind(Exhibition exhibition) {
            titleTextView.setText(exhibition.getTitle());
            int photoResourceId = context.getResources().getIdentifier(exhibition.getPhotoName(), "drawable", context.getPackageName());
            Glide.with(context).load(photoResourceId).into(photoImageView);
        }
    }
}
