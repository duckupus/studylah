package com.sp.studylah.carousel_fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.sp.studylah.R;

import java.util.Arrays;
import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselItemViewHolder> {
    private final List<Integer> layout = Arrays.asList(R.layout.carousel, R.layout.fragment_first, R.layout.fragment_second);


    public static class CarouselItemViewHolder extends ViewHolder {
        public CarouselItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    @NonNull
    @Override
    public CarouselItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup viewHolder = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(layout.get(viewType), parent, false);
        return new CarouselItemViewHolder(viewHolder);
    }

    TextView textView;
    @Override
    public void onBindViewHolder(@NonNull CarouselItemViewHolder holder, int position) {
        if(position == 0){
            textView = holder.itemView.findViewById(R.id.textview);
            textView.setText("Page " + position + "\n");
            return;
        } else if(position == 1) {
            return;
        } else if(position == 2) {
            return;
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return layout.size();
    }
}
