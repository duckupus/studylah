package com.sp.studylah.carousel_fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.sp.studylah.R;
import com.sp.studylah.carousel_fragments.first_fragment.FirstFragment;
import com.sp.studylah.carousel_fragments.second_fragment.SecondFragment;
import com.sp.studylah.carousel_fragments.third_fragment.ThirdFragment;

import java.util.Arrays;
import java.util.List;

public class FragmentCarouselAdapter extends FragmentStateAdapter {
    private final List<Integer> layout = Arrays.asList(R.layout.fragment_first, R.layout.fragment_second, R.layout.fragment_third);

    public FragmentCarouselAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int layoutID = layout.get(position);
        switch(position){
            case 0:
                return new FirstFragment();
            case 1:
                return new SecondFragment();
            case 2:
                return new ThirdFragment();
            default:
                throw new IllegalArgumentException("Invalid layout ID");
        }
    }

    @Override
    public int getItemCount() {
       return layout.size();
    }
}
