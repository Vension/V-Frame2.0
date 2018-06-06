package com.vension.frame.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.vension.frame.R;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/2 11:24
 * 描  述：支持设置宽高自适应的ImageView
 * ========================================================
 */

public class VMatchImageView extends AppCompatImageView {

    private boolean isMatches = false;//是否自适应宽高

    public VMatchImageView(Context context) {
        this(context, null);
    }

    public VMatchImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VMatchImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageMatches);
        isMatches = a.getBoolean(R.styleable.ImageMatches_matches, isMatches);// 默认为自适应
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(isMatches) {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        }else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }



}