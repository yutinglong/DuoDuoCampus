package com.duoduo.duoduocampus.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.model.News;
import com.duoduo.duoduocampus.utils.NetImageUtil;
import com.duoduo.duoduocampus.utils.TileDrawable;

public class NewsListAdapter extends BaseAdapter {
	private LayoutInflater mInflater = null;
	private List<News> data = new ArrayList<News>();
	private Context mContext;

	public NewsListAdapter(Context context, List<News> dataList) {
		this.mInflater = LayoutInflater.from(context);
		this.data = dataList;
		
		mContext = context.getApplicationContext();
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public News getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.news_message_item, null);
			holder.title = (TextView) convertView.findViewById(R.id.new_title);
			holder.datatime = (TextView) convertView
					.findViewById(R.id.datatime);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			holder.newImg = (ImageView) convertView.findViewById(R.id.new_img);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		News mNews = data.get(position);
		
		holder.title.setText((String) mNews.newTitle);
		holder.datatime.setText((String) mNews.newDate.substring(0, 10));
		holder.content.setText((String) mNews.newContent);
		
		if (!TextUtils.isEmpty(mNews.imgUrl)) {
			NetImageUtil.with_load_info(mContext, mNews.imgUrl, holder.newImg);
			holder.newImg.setBackgroundDrawable(new TileDrawable(mContext));
			holder.newImg.setVisibility(View.VISIBLE);
		}
		else {
			holder.newImg.setVisibility(View.GONE);
		}

		return convertView;
	}

	// ViewHolder静态类
	static class ViewHolder {
		public TextView title;
		public TextView datatime;
		public TextView content;
		public ImageView newImg;
	}

}
