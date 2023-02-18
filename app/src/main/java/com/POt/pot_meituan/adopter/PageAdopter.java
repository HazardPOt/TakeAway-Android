package com.POt.pot_meituan.adopter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.POt.pot_meituan.fragment.IndexFragment;

public class PageAdopter extends PagerAdapter {

    private int length = IndexFragment.Img_ad.length;
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        position %= length;
        View view = IndexFragment.mViewList.get(position);
        ViewParent parent = view.getParent();
        if(parent!=null){
            Log.i("who_are_you",parent.getClass().toString());
            androidx.viewpager.widget.ViewPager viewPager = (androidx.viewpager.widget.ViewPager)parent;
            viewPager.removeView(view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        position %= length;
        container.removeView(IndexFragment.mViewList.get(position));
    }
}
