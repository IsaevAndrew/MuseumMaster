package edu.example.museummaster.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.example.museummaster.R;
import edu.example.museummaster.data.data_sourses.category.models.NewsElement;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    private List<NewsElement> mDataset;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitleTextView;
        public TextView mDescriptionTextView;
        public TextView mDateTextView;

        public MyViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.title);
            mDescriptionTextView = itemView.findViewById(R.id.description);
            mDateTextView = itemView.findViewById(R.id.date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public NewsAdapter(List<NewsElement> myDataset) {
        mDataset = myDataset;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public NewsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NewsElement currentItem = mDataset.get(position);
        holder.mTitleTextView.setText(currentItem.getTitle());
        holder.mDescriptionTextView.setText(currentItem.getDescription());
        holder.mDateTextView.setText(currentItem.getDate());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
