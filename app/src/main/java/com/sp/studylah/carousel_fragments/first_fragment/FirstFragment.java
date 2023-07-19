package com.sp.studylah.carousel_fragments.first_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sp.studylah.R;
import com.sp.studylah.RecyclerItemClickListener;
import com.sp.studylah.databinding.FragmentFirstBinding;
import com.sp.studylah.Pages.view1;

import java.util.Objects;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ConstraintLayout layout;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout = view.findViewById(R.id.fragment_first);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), view1.class);
                startActivity(intent);
            }
        });
        recyclerView = view.findViewById(R.id.recycler_view_carousel_first_fragment);
        CarouselDateAdapter adapter = new CarouselDateAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
                ViewGroup.LayoutParams params = ((CarouselDateAdapter.ViewHolder) holder).textView.getLayoutParams();
                params.height = adapter.height;
                params.width = adapter.width;
                ((CarouselDateAdapter.ViewHolder) holder).textView.setLayoutParams(params); //resets back to original size when recycled.
            }
        });
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int count = layoutManager.getChildCount();
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CarouselDateAdapter.ViewHolder viewholder = (CarouselDateAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
                viewholder.textView.setText("CLICKED! " + position);
                ViewGroup.LayoutParams params = ((CarouselDateAdapter.ViewHolder) Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(position))).textView.getLayoutParams();
                params.height = (int) (adapter.height * 1.2);
                params.width = (int) (adapter.width * 1.2);
                ((CarouselDateAdapter.ViewHolder) Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(position))).textView.setLayoutParams(params);
                for(int i = position - recyclerView.getChildCount(); i < position + recyclerView.getChildCount(); i++) {
                    if(recyclerView.findViewHolderForAdapterPosition(i) == null) { //avoids invalid positions
                        continue;
                    }
                    if(i != position) {
                        ViewGroup.LayoutParams params2 = ((CarouselDateAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(i)).textView.getLayoutParams();
                        params2.height = adapter.height;
                        params2.width = adapter.width;
                        ((CarouselDateAdapter.ViewHolder) Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(i))).textView.setLayoutParams(params2);
                    }
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}