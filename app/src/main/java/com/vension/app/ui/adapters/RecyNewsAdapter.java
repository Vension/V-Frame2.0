package com.vension.app.ui.adapters;

import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sunfusheng.glideimageview.GlideImageView;
import com.vension.app.R;
import com.vension.app.beans.NewsItemInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author ：Created by Administrator on 2018/5/25 10:11.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
public class RecyNewsAdapter extends BaseQuickAdapter<NewsItemInfo, BaseViewHolder> {

	@BindView(R.id.giv_title)
	GlideImageView givTitle;
	@BindView(R.id.tv_title)
	TextView tvTitle;
	@BindView(R.id.tv_time)
	TextView tvTime;
	@BindView(R.id.tv_view_count)
	TextView tvViewCount;

	public RecyNewsAdapter() {
		super(R.layout.item_recy_news);
	}

	@Override
	protected void convert(BaseViewHolder helper, NewsItemInfo item) {
		ButterKnife.bind(this, helper.itemView);

		if (item.getImageUrls() != null && item.getImageUrls().size() > 0) {
			givTitle.loadImage(item.getImageUrls().get(0),R.color.placeholder_color);
		}
		tvTitle.setText(item.getTitle());
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
		try {
			tvTime.setText(sdf.format(new Date(item.getPublishDate() * 1000)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder("阅读" + item.getViewCount());
		if (!TextUtils.isEmpty(item.getCommentCount())) {
			sb.append("·").append("评论").append(item.getCommentCount());
		}
		tvViewCount.setText(sb.toString());

	}

}
