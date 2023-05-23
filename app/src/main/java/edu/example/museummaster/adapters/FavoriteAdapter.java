package edu.example.museummaster.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.example.museummaster.R;
import edu.example.museummaster.data.data_sourses.category.models.Favourite;
import edu.example.museummaster.ui.Exhibit;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private List<Favourite> favouriteList;
    private Context context;

    public FavoriteAdapter(List<Favourite> favouriteList, Context context) {
        this.favouriteList = favouriteList;
        this.context = context;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_exhibit, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Favourite favourite = favouriteList.get(position);
        holder.bind(favourite);
    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView exhibitTitleTextView;

        FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            exhibitTitleTextView = itemView.findViewById(R.id.textViewExhibitName);
            itemView.setOnClickListener(this);
        }

        void bind(Favourite favourite) {
            exhibitTitleTextView.setText(favourite.getExhibitTitle());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Favourite favourite = favouriteList.get(position);
                openExhibitFragment(favourite.getExhibitId());
            }
        }
    }

    private void openExhibitFragment(int exhibitId) {
        Fragment fragment = Exhibit.newInstance(exhibitId);
        AppCompatActivity activity = (AppCompatActivity) context;
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment).addToBackStack(null).commit();
    }
}
