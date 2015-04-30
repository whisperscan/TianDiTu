package com.tianditudemo.offline;

import java.util.ArrayList;
import java.util.Locale;

import com.tianditudemo.OfflineMapDemo;

import com.tianditu.android.maps.TOfflineMapInfo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class DownLoadingAdapter  extends BaseExpandableListAdapter {
	private Context mContext;
	private ArrayList<TOfflineMapInfo> mLoadingMaps;
	private ArrayList<TOfflineMapInfo> mPausedMaps;
	public TOfflineMapInfo mSelMap;

	public DownLoadingAdapter(Context context) {
		mContext = context;
	}
	
	public void loadMapInfo(ArrayList<TOfflineMapInfo> loadingMaps, ArrayList<TOfflineMapInfo> pausedMaps) {
		mLoadingMaps = loadingMaps;
		mPausedMaps = pausedMaps;
		notifyDataSetChanged();
	}

	@Override
	public int getGroupCount() {
		return 2;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if (groupPosition == 0) {
			if (mLoadingMaps != null)
				return mLoadingMaps.size();
		} else if (groupPosition == 1) {
			if (mPausedMaps != null)
				return mPausedMaps.size();
		}
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		if (groupPosition == 0) {
			if (mLoadingMaps != null)
				return mLoadingMaps.get(childPosition);
		} else if (groupPosition == 1) {
			if (mPausedMaps != null)
				return mPausedMaps.get(childPosition);
		}
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = (View) View.inflate(mContext, android.R.layout.simple_expandable_list_item_2, null);

		TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
		if (groupPosition == 0) {
			tv.setText("下载中的城市");
		} else if (groupPosition == 1) {
			tv.setText("暂停的城市");
		}
		
		tv = (TextView) convertView.findViewById(android.R.id.text2);
		String str = String.format(Locale.getDefault(), "(%d)", getChildrenCount(groupPosition));
		tv.setText(str);
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = (View) View.inflate(mContext, android.R.layout.simple_expandable_list_item_2, null);
		}
		TOfflineMapInfo map = (TOfflineMapInfo) getChild(groupPosition, childPosition);

		TextView v1 = (TextView) convertView.findViewById(android.R.id.text1);
		v1.setText(map.getCity());

		TextView v2 = (TextView) convertView.findViewById(android.R.id.text2);
		String str = String.format(Locale.getDefault(), "%s(%.1fM/%.1fM)",
				OfflineMapDemo.OFFLINE_TYPENAME[map.getType()], 
				(float) map.getDownloadedSize() / 1024 / 1024,
				(float) map.getSize() / 1024 / 1024);
		v2.setText(str);
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
