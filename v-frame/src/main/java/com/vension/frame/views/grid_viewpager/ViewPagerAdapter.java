package com.vension.frame.views.grid_viewpager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/18 18:04
 * 描  述：
 * ========================================================
 */

public class ViewPagerAdapter<T extends View> extends PagerAdapter {
    private List<T> mViewList;

    public ViewPagerAdapter(List<T> mViewList) {
        this.mViewList = mViewList;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return (mViewList.get(position));
    }

    @Override
    public int getCount() {
        if (mViewList == null)
            return 0;
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}