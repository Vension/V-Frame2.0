package com.vension.app.ui.adapters;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sunfusheng.glideimageview.GlideImageView;
import com.vension.app.R;
import com.vension.app.beans.TestBean;
import com.vension.frame.utils.DefIconFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/18 16:53
 * 描  述：
 * ========================================================
 */

public class RecyListTestAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {

	@BindView(R.id.giv_product)
	GlideImageView givProduct;
	@BindView(R.id.tv_title)
	TextView tvTitle;
	@BindView(R.id.tv_desc)
	TextView tvDesc;
	@BindView(R.id.tv_date)
	TextView tvDate;

	public RecyListTestAdapter() {
		super(R.layout.item_recy_test);
	}

	@Override
	protected void convert(BaseViewHolder helper, TestBean item) {
		ButterKnife.bind(this, helper.itemView);
		givProduct.loadLocalImage(DefIconFactory.provideIcon(),R.color.placeholder_color);
		tvTitle.setText(item.getTitle());
		tvDesc.setText(item.getDesc());
		tvDate.setText(item.getDate());
	}

}
