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
import com.tianditu.android.maps.TOfflineMapManager.City;
import com.tianditu.android.maps.TOfflineMapManager.MapAdminSet;

public class AllMapAdapter extends BaseExpandableListAdapter {
	private Context mContext;
	public ArrayList<MapAdminSet> mAllMaps = null;

	public City selCity;
	public int mDlgItem;

	public AllMapAdapter(Context context) {
		mContext = context;
	}

	public void loadAllMaps(ArrayList<MapAdminSet> maps) {
		mAllMaps = maps;
		notifyDataSetChanged();
	}

	@Override
	public int getGroupCount() {
		if (mAllMaps == null)
			return 0;
		return mAllMaps.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if (mAllMaps == null)
			return 0;
		MapAdminSet admin = mAllMaps.get(groupPosition);
		if (admin.getCitys() == null)
			return 0;
		return admin.getCitys().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		if (mAllMaps == null)
			return null;
		return mAllMaps.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		if (mAllMaps == null)
			return null;
		MapAdminSet admin = mAllMaps.get(groupPosition);
		if (admin.getCitys() == null)
			return null;
		return admin.getCitys().get(childPosition);
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

		MapAdminSet adminSet = (MapAdminSet) getGroup(groupPosition);
		ArrayList<City> citys = adminSet.getCitys();

		TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
		String str = String.format(Locale.getDefault(), "%d.%s",
				(groupPosition + 1), adminSet.getName());
		tv.setText(str);
		
		tv = (TextView) convertView.findViewById(android.R.id.text2);
		str = String.format(Locale.getDefault(), "(%d)", citys.size());
		tv.setText(str);
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = (View) View.inflate(mContext, android.R.layout.simple_expandable_list_item_2, null);

		City city = (City) getChild(groupPosition, childPosition);
		ArrayList<TOfflineMapInfo> mapInfos = city.getMaps();

		TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
		String str = String.format(Locale.getDefault(), "%d.%s",
				(childPosition + 1), city.getName());
		tv.setText(str);

		tv = (TextView) convertView.findViewById(android.R.id.text2);
		if (mapInfos != null && mapInfos.size() > 0) {
			str = "";
			for (TOfflineMapInfo mapInfo : mapInfos) {
				// ÀëÏß°ü
				str += String.format(Locale.getDefault(), "%s(%.1fM) ",
						OfflineMapDemo.OFFLINE_TYPENAME[mapInfo.getType()],
						(float) mapInfo.getSize() / 1024 / 1024);
			}
			tv.setText(str);
		}
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
