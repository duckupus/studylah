package com.sp.studylah;

import android.os.Bundle;
import static java.lang.Math.abs;

import android.content.res.Resources;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.sp.studylah.carousel_fragments.CarouselAdapter;
import com.sp.studylah.carousel_fragments.FragmentCarouselAdapter;
import com.sp.studylah.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ViewPager2 viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        viewPager = findViewById(R.id.carousel_main_view_pager);
        //CarouselAdapter carouselAdapter = new CarouselAdapter();
        //viewPager.setAdapter(carouselAdapter);
        FragmentCarouselAdapter fragmentCarouselAdapter = new FragmentCarouselAdapter(this);
        viewPager.setAdapter(fragmentCarouselAdapter);

        ViewPager2.PageTransformer pageTransformer = new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float absPosition = abs(position);
                page.setScaleY(0.85f + absPosition * 0.15f);
                CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
                compositePageTransformer.addTransformer(new MarginPageTransformer((int) (40 * Resources.getSystem().getDisplayMetrics().density)));
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
}