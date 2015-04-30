package com.tianditudemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class LaunchActivity extends Activity{

	ListView mListView = null;
    String mStrDemos[] = {
    		"MainActivity",
    		"DBManageActivity",
    		"OfflineMapDemo"
	};
    Class<?> mActivities[] = {
    		MainActivity.class,
    		DBManageActivity.class,
    		OfflineMapDemo.class
    };
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.launch);
		mListView = (ListView)findViewById(R.id.listView);
		List<String> data = new ArrayList<String>();
		for (int i = 0; i < mStrDemos.length; i++)
		{
			data.add(mStrDemos[i]);
		}
        mListView.setAdapter((ListAdapter) new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,data));
        mListView.setOnItemClickListener(new OnItemClickListener() {  
        	@Override
        	public void onItemClick(AdapterView<?> arg0, View v, int index, long arg3) {  
        		if (index < 0 || index >= mActivities.length)
            		return;
            	
        		Intent intent = null;
        		intent = new Intent(LaunchActivity.this, mActivities[index]);
        		startActivity(intent);
            }
        });
	}

}
