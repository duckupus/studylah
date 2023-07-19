package com.sp.studylah.carousel_fragments.first_fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sp.studylah.R;

public class CarouselDateAdapter extends RecyclerView.Adapter<CarouselDateAdapter.ViewHolder>{

    @NonNull
    @Override
    public CarouselDateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carousel_date, parent, false);
        return new CarouselDateAdapter.ViewHolder(view);
    }

    public interface CarouselDateAdapterItemClickListener {
        void onItemClicked(int position);
    }

    public final int width = 330;
    public final int height = 83;
    @Override
    public void onBindViewHolder(@NonNull CarouselDateAdapter.ViewHolder holder, int position) {
        ViewGroup.LayoutParams params = holder.textView.getLayoutParams();
        holder.textView.setLayoutParams(params); //resets back to original size when recycled.
        holder.textView.setText("Page " + position + "\n");
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview_date_carousel);
        }
    }

}
