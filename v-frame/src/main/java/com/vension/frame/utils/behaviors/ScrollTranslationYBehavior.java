package com.vension.frame.utils.behaviors;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import com.vension.frame.R;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/22 10:12
 * 描  述：添加给Fab的 Behavior 实现当内容控件向下滑动向下隐藏 向上滑动进入
 * ========================================================
 */

public class ScrollTranslationYBehavior extends FloatingActionButton.Behavior {

	private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
	private boolean mIsAnimatingOut = false;//fab是否被 压出 默认false 表示显示

	public ScrollTranslationYBehavior(Context context, AttributeSet attrs) {
		super();
	}

	@Override
	public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
		// 确定是在垂直方向上滑动
		return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
				|| super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
	}

	@Override
	public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
		super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

		if (dyConsumed > 0 && !this.mIsAnimatingOut) {

			animateOut(child); // 不显示FAB
		} else if (dyConsumed < 0 && this.mIsAnimatingOut) {

			animateIn(child);// 显示FAB
		}
		if (dyConsumed > 0 && !this.mIsAnimatingOut && child.getVisibility() == View.VISIBLE) {
			// User scrolled down and the FAB is currently visible -> hide the FAB
			animateOut(child);// 不显示FAB
		}
		else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
			// User scrolled up and the FAB is currently not visible -> show the FAB
			animateIn(child);// 显示FAB
		}
	}

	// Same animation that FloatingActionButton.Behavior uses to hide the FAB when the AppBarLayout exits
	private void animateOut(final FloatingActionButton button) {
		if (Build.VERSION.SDK_INT >= 14) {
			ViewCompat.animate(button).translationY(button.getHeight() + getMarginBottom(button)).setInterpolator(INTERPOLATOR).withLayer()
					.setListener(new ViewPropertyAnimatorListener() {
						public void onAnimationStart(View view) {
							ScrollTranslationYBehavior.this.mIsAnimatingOut = true;
						}

						public void onAnimationCancel(View view) {
							ScrollTranslationYBehavior.this.mIsAnimatingOut = false;
						}

						public void onAnimationEnd(View view) {
							ScrollTranslationYBehavior.this.mIsAnimatingOut = false;
							view.setVisibility(View.INVISIBLE);
						}
					}).start();
		} else {
			Animation anim = AnimationUtils.loadAnimation(button.getContext(), R.anim.bottom_out);
			anim.setInterpolator(INTERPOLATOR);
			anim.setDuration(200L);
			anim.setAnimationListener(new Animation.AnimationListener() {
				public void onAnimationStart(Animation animation) {
					ScrollTranslationYBehavior.this.mIsAnimatingOut = true;
				}

				public void onAnimationEnd(Animation animation) {
					ScrollTranslationYBehavior.this.mIsAnimatingOut = false;
					button.setVisibility(View.INVISIBLE);
				}

				@Override
				public void onAnimationRepeat(final Animation animation) {
				}
			});
			button.startAnimation(anim);
		}
	}



	// Same animation that FloatingActionButton.Behavior uses to show the FAB when the AppBarLayout enters
	private void animateIn(FloatingActionButton button) {
		button.setVisibility(View.VISIBLE);

		if (Build.VERSION.SDK_INT >= 14) {
			ViewCompat.animate(button).translationY(0)
					.setInterpolator(INTERPOLATOR).withLayer().setListener(new ViewPropertyAnimatorListener() {
				@Override
				public void onAnimationStart(View view) {
					ScrollTranslationYBehavior.this.mIsAnimatingOut = false;
				}

				@Override
				public void onAnimationCancel(View view) {
					ScrollTranslationYBehavior.this.mIsAnimatingOut = true;
				}

				@Override
				public void onAnimationEnd(View view) {
//					ScrollTranslationYBehavior.this.mIsAnimatingOut = true;
					view.setVisibility(View.VISIBLE);
				}
			}).start();
		} else {
			Animation anim = AnimationUtils.loadAnimation(button.getContext(), R.anim.bottom_in);
			anim.setDuration(200L);
			anim.setInterpolator(INTERPOLATOR);
			button.startAnimation(anim);
		}
	}


	private int getMarginBottom(View v) {
		int marginBottom = 0;
		final ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
		if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
			marginBottom = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
		}
		return marginBottom;
	}

}