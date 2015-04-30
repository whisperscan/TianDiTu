package com.tianditudemo.offline;

import java.util.ArrayList;
import java.util.Locale;

import com.tianditudemo.OfflineMapDemo;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.tianditu.android.maps.TOfflineMapInfo;

public class DownLoadedAdapter extends BaseExpandableListAdapter {
	private Context mContext;
	private ArrayList<TOfflineMapInfo> mLoadedMaps = null;
	private ArrayList<TOfflineMapInfo> mUpdateMaps = null;
	public TOfflineMapInfo mSelMap;

	public DownLoadedAdapter(Context context) {
		mContext = context;
	}

	public void loadDownLoaded(ArrayList<TOfflineMapInfo> maps, ArrayList<TOfflineMapInfo> updateMaps) {
		mLoadedMaps = maps;
		mUpdateMaps = updateMaps;
		notifyDataSetChanged();
	}

	@Override
	public int getGroupCount() {
		return 2;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if (groupPosition == 0) {
			if (mLoadedMaps != null)
				return mLoadedMaps.size();
		} else if (groupPosition == 1) {
			if (mUpdateMaps != null)
				return mUpdateMaps.size();
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
			if (mLoadedMaps != null)
				return mLoadedMaps.get(childPosition);
		} else if (groupPosition == 1) {
			if (mUpdateMaps != null)
				return mUpdateMaps.get(childPosition);
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
			tv.setText("已下载的城市");
		} else if (groupPosition == 1) {
			tv.setText("更新的城市");
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
		v2.setText(OfflineMapDemo.OFFLINE_TYPENAME[map.getType()] + " version:" + map.getVersion());
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
