package com.sp.studylah;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselItemViewHolder> {


    public static class CarouselItemViewHolder extends ViewHolder {
        public CarouselItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    @NonNull
    @Override
    public CarouselItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup viewHolder = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.carousel, parent, false);
        return new CarouselItemViewHolder(viewHolder);
    }

    TextView textView;
    @Override
    public void onBindViewHolder(@NonNull CarouselItemViewHolder holder, int position) {
        textView = holder.itemView.findViewById(R.id.textview);
        textView.setText("Page " + position + "\n" + strings[position]);

    }

    String[] strings;
    public void setItems(String[] string) {
        strings = string;
    }

    @Override
    public int getItemCount() {
        return strings.length;
    }
}
