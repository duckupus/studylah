package com.sp.studylah;

import static java.lang.Math.abs;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.sp.studylah.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

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

        ViewPager2 viewPager = view.findViewById(R.id.carousel_main_view_pager);
        CarouselAdapter carouselAdapter = new CarouselAdapter();
        carouselAdapter.setItems(new String[] {"Page 1", "Page 2", "Page 3"});
        viewPager.setAdapter(carouselAdapter);

        ViewPager2.PageTransformer pageTransformer = new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float absPosition = abs(position);
                page.setScaleY(0.85f + absPosition * 0.15f);
                CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
                compositePageTransformer.addTransformer(new MarginPageTransformer((int) (40* Resources.getSystem().getDisplayMetrics().density)));
                compositePageTransformer.addTransformer((page1, position1) -> {
                    float r = (1 - abs(position1));
                    page1.setScaleY(0.85f + r * 0.2f);
                });
                viewPager.setPageTransformer(compositePageTransformer);
                viewPager.setClipChildren(false);
                viewPager.setClipToPadding(false); //show viewpager without clipping
                viewPager.setOffscreenPageLimit(3); //left and right items
                RecyclerView recyclerView = (RecyclerView) viewPager.getChildAt(0);
                recyclerView.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
            }
        };
        viewPager.setPageTransformer(pageTransformer);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}