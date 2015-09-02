package com.duoduo.duoduocampus.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.duoduo.duoduocampus.R;
import com.duoduo.duoduocampus.utils.DToast;
import com.duoduo.duoduocampus.utils.MobileUtil;

public class AboutActivity extends BaseActivity implements OnClickListener {

	private View back;
	private View down;
	private String down_url;
	private TextView version;
	private TextView mReleaseNoteTitle;
	private ListView mReleaseNoteListView;
	private ReleaseNoteLisAdapter mReleaseNoteLisAdapter;
	private List<String> mReleaseNoteList =  new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.about_layout);
		initView(); 
		getReleaseNote();
	}

	private void initView() {
		back = findViewById(R.id.back);
		down = findViewById(R.id.downLoad);
		back.setOnClickListener(this);
		down.setOnClickListener(this);
		Intent intent = getIntent();
		down.setVisibility(View.VISIBLE);
		
		if (intent.hasExtra("url")) {
			down_url = intent.getStringExtra("url");
		}
		version = (TextView) findViewById(R.id.version);
		version.setText("当前版本  " + MobileUtil.getVersionName());
		mReleaseNoteTitle = (TextView) findViewById(R.id.about_update_title_txt);
		mReleaseNoteTitle.setText(MobileUtil.getVersionName() + "版更新动态");

		mReleaseNoteList.add("暂无更新动态");
		mReleaseNoteListView = (ListView)findViewById(R.id.about_update_content_list);
		mReleaseNoteLisAdapter = new ReleaseNoteLisAdapter();
		mReleaseNoteListView.setAdapter(mReleaseNoteLisAdapter);
		
	}

	private void getReleaseNote() {
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.downLoad:
			DToast.toastShort("待开发，敬请期待");
			break;
		}
	}

	
	class ReleaseNoteLisAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mReleaseNoteList.size();
		}

		@Override
		public Object getItem(int position) {
			return mReleaseNoteList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if(convertView == null){
				viewHolder = new ViewHolder();
				convertView = AboutActivity.this.getLayoutInflater().inflate(R.layout.about_release_note_item, null);
				viewHolder.releaseNote = (TextView)convertView.findViewById(R.id.updatepoint_display);			
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}		
			String note = mReleaseNoteList.get(position);
			viewHolder.releaseNote.setText(note);
			return convertView;
		}
		class ViewHolder {
			TextView releaseNote;
		}
		
	}
}
