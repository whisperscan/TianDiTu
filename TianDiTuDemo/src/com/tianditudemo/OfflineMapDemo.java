package com.tianditudemo;

import java.util.ArrayList;
import java.util.Locale;

import com.tianditudemo.offline.AllMapAdapter;
import com.tianditudemo.offline.DownLoadedAdapter;
import com.tianditudemo.offline.DownLoadingAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapController;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MapView.OnMapParamChangeListener;
import com.tianditu.android.maps.TErrorCode;
import com.tianditu.android.maps.TOfflineMapInfo;
import com.tianditu.android.maps.TOfflineMapManager;
import com.tianditu.android.maps.TOfflineMapManager.City;
import com.tianditu.android.maps.TOfflineMapManager.MapAdminSet;
import com.tianditu.android.maps.TOfflineMapManager.OnDownLoadResult;
import com.tianditu.android.maps.TOfflineMapManager.OnGetMapsResult;

public class OfflineMapDemo extends Activity implements OnClickListener, OnGetMapsResult, OnDownLoadResult, OnChildClickListener,OnMapParamChangeListener{
	public static final String OFFLINE_TYPENAME[] = new String[] { "", "Ӱ��", "ʸ��", "����" };
	
	private static final int PAGE_ALLMAPS = 0;
	private static final int PAGE_DOWNLOADING = 1;
	private static final int PAGE_DOWNLOADED = 2;
	private static final int PAGE_MAPVIEW = 3;
	
    private static final int DIALOG_PAGE_ALLMAPS = 1;
    private static final int DIALOG_PAGE_LOADING_GROUP_LOADING = 2;
    private static final int DIALOG_PAGE_LOADING_GROUP_PAUSE = 3;
    private static final int DIALOG_PAGE_LOADED_GROUP_LOADED = 4;
    private static final int DIALOG_PAGE_LOADED_GROUP_UPDATE = 5;

	private Button mBtnAllMaps;
	private Button mBtnDownLoading;
	private Button mBtnDownLoaded;
	private Button mBtnMap;
	
	// �����б�
	private AllMapAdapter mAdapterAllMap;
	private ExpandableListView mListAllMaps;
	private ProgressDialog mProgressDlg = null;

	// �������б�
	private DownLoadingAdapter mAdapterDownLoading;
	private ExpandableListView mListDownLoaded;

	// �������б�
	private EditText mEdit = null;
	private DownLoadedAdapter mAdapterDownLoaded;
	private ExpandableListView mListDownLoading;

	// ��ͼ��ͼ
	private View mViewMap;
	private EditText mEditLon = null;
	private EditText mEditLat = null;
	private EditText mEditScale = null;
	private Button mBtnMoveTo = null;
	
	private MapView mMapView;
	private MapController mController = null;

	private TOfflineMapManager offlineMapMng = null;

	private Toast mToast = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.offlinemapdemo);
		
		// ���ߵ�ͼ
		offlineMapMng = new TOfflineMapManager(this);
		// ���û�ȡ������Ϣ�ӿ�
		offlineMapMng.setOnDownLoadListener(this);
		// �������ߵ�ͼ�ļ���ŵĸ�Ŀ¼
		offlineMapMng.setMapPath("/sdcard/tianditu/staticmap/");
		
		// �����б�
		mBtnAllMaps = (Button) findViewById(R.id.btn_allmaps);
		mBtnAllMaps.setOnClickListener(this);
		// ������
		mBtnDownLoading = (Button) findViewById(R.id.btn_downloading);
		mBtnDownLoading.setOnClickListener(this);
		// �������б�
		mBtnDownLoaded = (Button) findViewById(R.id.btn_downloaded);
		mBtnDownLoaded.setOnClickListener(this);
		// ��ͼ��ͼ
		mBtnMap = (Button) findViewById(R.id.btn_map);
		mBtnMap.setOnClickListener(this);
		
		// ˢ�³����б�
		Button btn = (Button) findViewById(R.id.btn_refresh);
		btn.setOnClickListener(this);
		// ɨ�����߰�
		btn = (Button) findViewById(R.id.btn_scan);
		btn.setOnClickListener(this);
		// ֹͣȫ��
		btn = (Button) findViewById(R.id.btn_stopall);
		btn.setOnClickListener(this);

		// �����б�
		mAdapterAllMap = new AllMapAdapter(this);
		mListAllMaps = (ExpandableListView) findViewById(R.id.list_allmaps);
		mListAllMaps.setAdapter(mAdapterAllMap);
		mListAllMaps.setOnChildClickListener(this);

		// �������б�
		mAdapterDownLoading = new DownLoadingAdapter(this);
		mListDownLoading = (ExpandableListView)findViewById(R.id.list_downloading);
		mListDownLoading.setAdapter(mAdapterDownLoading);
		mListDownLoading.setOnChildClickListener(this);
		
		// �������б�
		mAdapterDownLoaded = new DownLoadedAdapter(this);
		mListDownLoaded = (ExpandableListView) findViewById(R.id.list_downloaded);
		mListDownLoaded.setAdapter(mAdapterDownLoaded);
		mListDownLoaded.setOnChildClickListener(this);
		// ���������б�
		mEdit = (EditText)findViewById(R.id.edit_name);
		mEdit.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				final String OFFLINE_DOWNSTATUS[] = new String[] {"", "��������", "����ͣ", "�����"};
				if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
					ArrayList<TOfflineMapInfo> arrItems = null;
					arrItems = offlineMapMng.searchLocalMaps(mEdit.getText().toString());
					String str = "";
					if (arrItems != null) {
						for (TOfflineMapInfo info : arrItems) {
							String status = OFFLINE_DOWNSTATUS[info.getState()];
							str += String.format(Locale.getDefault(), "%s%s(%.1fM ver:%s status:%s)\r\n", 
									info.getCity(),
									OFFLINE_TYPENAME[info.getType()],
									(float)info.getSize()/1024/1024,
									info.getVersion(),
									status);
						}
					}
					if (str.length() == 0)
						str = "û��������ָ�����е����ߵ�ͼ�ļ�";
					Toast.makeText(OfflineMapDemo.this, str, Toast.LENGTH_SHORT).show();;
					return true;
				}
				return false;
			}
		});

		mViewMap = (View)findViewById(R.id.layout_map);
		mEditLon = (EditText)findViewById(R.id.btn_lon);
		mEditLat = (EditText)findViewById(R.id.btn_lat);
		mEditScale = (EditText)findViewById(R.id.btn_scale);
		mBtnMoveTo = (Button)findViewById(R.id.btn_moveto);
		mBtnMoveTo.setOnClickListener(this);
		
		// ��ͼ��ͼ
		mMapView = (MapView)findViewById(R.id.layout_mapview);
		// ���õ�ͼ������·��
		mMapView.setCachePath("/sdcard/tianditu/map/");
		// �������ߵ�ͼ������Ϣ�����ڵ�ͼ��ʾ����
		mMapView.setOfflineMaps(offlineMapMng.searchLocalMaps());
		mController = mMapView.getController();
		mMapView.setParamChangeListener(this);

		mProgressDlg = new ProgressDialog(this);
		mProgressDlg.setTitle("���ͼ");
		mProgressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDlg.setMessage("��ó����б�...");
		mProgressDlg.setCanceledOnTouchOutside(false);
		mProgressDlg.setCancelable(true);
		mProgressDlg.show();
		// ��ȡ�������ߵ�ͼ�б�
		offlineMapMng.getMapList();
		
		setViewPage(PAGE_ALLMAPS);
	}

	protected void onDestroy() {
		Log.i("offline", "onDestroy pauseDownload");
		// ��ͣ���ڽ��е����ߵ�ͼ����
		offlineMapMng.pauseDownload();
		if (mToast != null)
			mToast.cancel();
		super.onDestroy();
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_allmaps:
			setViewPage(PAGE_ALLMAPS);
			break;
		case R.id.btn_downloading:
			setViewPage(PAGE_DOWNLOADING);
			break;
		case R.id.btn_downloaded:
			setViewPage(PAGE_DOWNLOADED);
			break;
		case R.id.btn_map:
			setViewPage(PAGE_MAPVIEW);
			break;
		case R.id.btn_refresh:
			mProgressDlg = new ProgressDialog(this);
			mProgressDlg.setTitle("���ͼ");
			mProgressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDlg.setMessage("ˢ�³����б�...");
			mProgressDlg.setCanceledOnTouchOutside(false);
			mProgressDlg.setCancelable(true);
			mProgressDlg.show();
			offlineMapMng.refreshMapList();
			break;
		case R.id.btn_scan: {
			int size = offlineMapMng.scan();
			mMapView.setOfflineMaps(offlineMapMng.searchLocalMaps());

			String str = String.format(Locale.getDefault(), "ɨ�����ߵ�ͼ������:%d", size);
			if (mToast == null)
				mToast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
			else
				mToast.setText(str);
			mToast.show();
			
			updateDownLoading();
			updateDownLoaded();
			break;
		}
		case R.id.btn_stopall:
			offlineMapMng.pauseDownload();
			break;
		case R.id.btn_moveto: {
			String lon = mEditLon.getText().toString();
			String lat = mEditLat.getText().toString();
			String scale = mEditScale.getText().toString();
			try {
				double dLon = Double.parseDouble(lon);
				double dLat = Double.parseDouble(lat);
				int iScale = Integer.parseInt(scale);
				GeoPoint geo = new GeoPoint((int) (dLat * 1E6), (int) (dLon * 1E6));
				mController.setZoom(iScale);
				mController.animateTo(geo);
			} catch(NumberFormatException e) {
				e.printStackTrace();
				Toast.makeText(this, "��γ����������", Toast.LENGTH_SHORT).show();
			}
			break;
		}
		}
	}

	private void setViewPage(int index)
	{
		Button btns[] = new Button[] { mBtnAllMaps, mBtnDownLoading, mBtnDownLoaded, mBtnMap };
		View views[] = { mListAllMaps, mListDownLoading, mListDownLoaded, mViewMap };
		for (int i = 0; i < btns.length; ++i) {
			if (i == index) {
				btns[i].setEnabled(false);
				views[i].setVisibility(View.VISIBLE);
			} else {
				btns[i].setEnabled(true);
				views[i].setVisibility(View.INVISIBLE);
			}
		}
		
		mEdit.setVisibility(mListDownLoaded.getVisibility());
		
		switch (index) {
		case PAGE_ALLMAPS: {
			break;
		}
		case PAGE_DOWNLOADING: {
			updateDownLoading();
			for (int i = 0; i < mAdapterDownLoading.getGroupCount(); ++i)
				mListDownLoading.expandGroup(i);
			break;
		}
		case PAGE_DOWNLOADED: {
			updateDownLoaded();
			for (int i = 0; i < mAdapterDownLoaded.getGroupCount(); ++i)
				mListDownLoaded.expandGroup(i);
			break;
		}
		case PAGE_MAPVIEW:
			break;
		}
	}
	
	private void updateDownLoading() {
		ArrayList<TOfflineMapInfo> loadingMaps = offlineMapMng.getDownLoadingMaps();
		ArrayList<TOfflineMapInfo> pausedMaps = offlineMapMng.getPausedMaps();
		mAdapterDownLoading.loadMapInfo(loadingMaps, pausedMaps);
		for (int i = 0; i < mAdapterDownLoading.getGroupCount(); ++i)
			mListDownLoaded.expandGroup(i);
	}
	
	private void updateDownLoaded() {
		ArrayList<TOfflineMapInfo> loadedMaps = offlineMapMng.searchLocalMaps();
		ArrayList<TOfflineMapInfo> updateMaps = offlineMapMng.getAllUpdateMaps();
		mAdapterDownLoaded.loadDownLoaded(loadedMaps, updateMaps);
		for (int i = 0; i < mAdapterDownLoaded.getGroupCount(); ++i)
			mListDownLoaded.expandGroup(i);
	}
	
	@Override
    protected Dialog onCreateDialog(int id) {
		final String OFFLINE_DOWNSTATUS[] = new String[] {"", "��������", "����ͣ", "�����"};
        switch (id) {
        case DIALOG_PAGE_ALLMAPS: {
			ArrayList<TOfflineMapInfo> mapInfos = mAdapterAllMap.selCity.getMaps();
			String[] itmes = new String[mapInfos.size()];
			
			int i = 0;
			for (TOfflineMapInfo mapInfo : mapInfos) {
				String status = "δ����";
				TOfflineMapInfo info = offlineMapMng.getDownloadInfo(mapInfo.getCity(), mapInfo.getType());
				if (info != null) {
					status = OFFLINE_DOWNSTATUS[info.getState()];
					if (info.getState() == TOfflineMapManager.OFFLINEMAP_DOWNLOAD_FINISHED) {
						if (info.getVersion() < mapInfo.getVersion())
							status = "����ver:" + info.getVersion();
					}
				}
				itmes[i] = String.format(Locale.getDefault(), "%s(%.1fM ver:%s status:%s)", 
						OFFLINE_TYPENAME[mapInfo.getType()],
						(float)mapInfo.getSize()/1024/1024,
						mapInfo.getVersion(),
						status);
				++ i;
			}
			DialogInterface.OnClickListener listenerList = new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int whichButton) {
                	mAdapterAllMap.mDlgItem = whichButton;
                }
			};
			DialogInterface.OnClickListener listener1 = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					// download
					ArrayList<TOfflineMapInfo> mapInfos = mAdapterAllMap.selCity.getMaps();
					if (whichButton > mapInfos.size()) {
						for (TOfflineMapInfo mapInfo : mapInfos)
							offlineMapMng.startDownload(mAdapterAllMap.selCity.getName(), mapInfo.getType());
					} else {
						offlineMapMng.startDownload(mAdapterAllMap.selCity.getName(), mapInfos.get(mAdapterAllMap.mDlgItem).getType());
					}
				}
			};
			DialogInterface.OnClickListener listener2 = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					// map
					ArrayList<TOfflineMapInfo> mapInfos = mAdapterAllMap.selCity.getMaps();
					TOfflineMapInfo mapInfo = mapInfos.get(mAdapterAllMap.mDlgItem);
					mMapView.setMapType(mapInfo.getType());
					mController.setZoom(mAdapterAllMap.selCity.getLevel());
					mController.setCenter(mAdapterAllMap.selCity.getCenter());
					setViewPage(PAGE_MAPVIEW);
				}
			};
			mAdapterAllMap.mDlgItem = 0;
			City info = mAdapterAllMap.selCity;
			String title = String.format(Locale.getDefault(), "%s level:%d center:%s",
					info.getName(),
					info.getLevel(),
					info.getCenter().toString());
			return new AlertDialog.Builder(OfflineMapDemo.this)
					.setTitle(title)
					.setSingleChoiceItems(itmes, 0, listenerList)
					.setPositiveButton("����", listener1)
					.setNeutralButton("��ͼ", listener2)
					.create();
        }
        case DIALOG_PAGE_LOADING_GROUP_LOADING:  {
			DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					// pause
					offlineMapMng.pauseDownload(mAdapterDownLoading.mSelMap.getCity(),
							mAdapterDownLoading.mSelMap.getType());
					updateDownLoading();
				}
			};
			TOfflineMapInfo info = mAdapterDownLoading.mSelMap;
			String title = info.getCity() + OFFLINE_TYPENAME[info.getType()];
			String message = String.format(Locale.getDefault(), " center:%s\n level:%d\n size:%d\n version:%s state:%s",
					info.getCenter().toString(),
					info.getLevel(),
					info.getSize(),
					info.getVersion(),
					OFFLINE_DOWNSTATUS[info.getState()]);
			return new AlertDialog.Builder(OfflineMapDemo.this)
					.setTitle(title)
					.setMessage(message)
					.setNeutralButton("��ͣ", listener)
					.create();
        }
        case DIALOG_PAGE_LOADING_GROUP_PAUSE:  {
        	// start
			DialogInterface.OnClickListener listener1 = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					// pause
					offlineMapMng.startDownload(mAdapterDownLoading.mSelMap.getCity(),
							mAdapterDownLoading.mSelMap.getType());
					updateDownLoading();
				}
			};
			DialogInterface.OnClickListener listener2 = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					// remove
					offlineMapMng.deleteMap(mAdapterDownLoading.mSelMap.getCity(),
							mAdapterDownLoading.mSelMap.getType());
					updateDownLoaded();
				}
			};
			TOfflineMapInfo info = mAdapterDownLoading.mSelMap;
			String title = info.getCity() + OFFLINE_TYPENAME[info.getType()];
			String message = String.format(Locale.getDefault(), " center:%s\n level:%d\n size:%d\n version:%s state:%s",
					info.getCenter().toString(),
					info.getLevel(),
					info.getSize(),
					info.getVersion(),
					OFFLINE_DOWNSTATUS[info.getState()]);
			return new AlertDialog.Builder(OfflineMapDemo.this)
					.setTitle(title)
					.setMessage(message)
					.setNeutralButton("��������", listener1)
					.setNegativeButton("ɾ��", listener2)
					.create();
        }
        case DIALOG_PAGE_LOADED_GROUP_LOADED: {
			DialogInterface.OnClickListener listener1 = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					// map
					mMapView.setMapType(mAdapterDownLoaded.mSelMap.getType());
					mController.setZoom(mAdapterDownLoaded.mSelMap.getLevel());
					mController.setCenter(mAdapterDownLoaded.mSelMap.getCenter());
					setViewPage(PAGE_MAPVIEW);
				}
			};
			DialogInterface.OnClickListener listener2 = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					// remove
					offlineMapMng.deleteMap(mAdapterDownLoaded.mSelMap.getCity(),
							mAdapterDownLoaded.mSelMap.getType());
					updateDownLoaded();
				}
			};
			TOfflineMapInfo info = mAdapterDownLoaded.mSelMap;
			String title = info.getCity() + OFFLINE_TYPENAME[info.getType()];
			String message = String.format(Locale.getDefault(), " center:%s\n level:%d\n size:%d\n version:%s state:%s",
					info.getCenter().toString(),
					info.getLevel(),
					info.getSize(),
					info.getVersion(),
					OFFLINE_DOWNSTATUS[info.getState()]);
			return new AlertDialog.Builder(OfflineMapDemo.this)
					.setTitle(title)
					.setMessage(message)
					.setPositiveButton("��ͼ", listener1)
					.setNegativeButton("ɾ��", listener2)
					.create();
        }
        case DIALOG_PAGE_LOADED_GROUP_UPDATE: {
			DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					// update
					offlineMapMng.startDownload(mAdapterDownLoaded.mSelMap.getCity(),
							mAdapterDownLoaded.mSelMap.getType());
					updateDownLoaded();
				}
			};
			TOfflineMapInfo info = mAdapterDownLoaded.mSelMap;
			String title = info.getCity() + OFFLINE_TYPENAME[info.getType()];
			String message = String.format(Locale.getDefault(), " center:%s\n level:%d\n size:%d\n version:%s state:%s",
					info.getCenter().toString(),
					info.getLevel(),
					info.getSize(),
					info.getVersion(),
					OFFLINE_DOWNSTATUS[info.getState()]);
			return new AlertDialog.Builder(OfflineMapDemo.this)
					.setTitle(title)
					.setMessage(message)
					.setNeutralButton("����", listener)
					.create();
        }
        }
        return null;
	}
	
	@Override
	public void onGetResult(ArrayList<MapAdminSet> maps, int error) {
		Log.i("offline", "onGetResult error = " + error);
		if (mProgressDlg != null)
			mProgressDlg.dismiss();
		mProgressDlg = null;
		mAdapterAllMap.loadAllMaps(maps);
		
		ArrayList<TOfflineMapInfo> loadingMaps = offlineMapMng.getDownLoadingMaps();
		ArrayList<TOfflineMapInfo> pausedMaps = offlineMapMng.getPausedMaps();
		mAdapterDownLoading.loadMapInfo(loadingMaps, pausedMaps);
	
		ArrayList<TOfflineMapInfo> loadedMaps = offlineMapMng.searchLocalMaps();
		ArrayList<TOfflineMapInfo> updateMaps = offlineMapMng.getAllUpdateMaps();
		mAdapterDownLoaded.loadDownLoaded(loadedMaps, updateMaps);
		
		if (error != TErrorCode.OK) {
			String str = String.format(Locale.getDefault(), "��ȡ�����б�ʧ�� onGetResult error = %d", error);
			if (mToast == null)
				mToast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
			else
				mToast.setText(str);
			mToast.show();
		}
	}
	
	@Override
	public void onDownLoadStart(String city, int type, int error) {
		Log.i("offline", "onDownLoadStart city = " + city + ", type = " + type + ", error = " + error);

		String str = String.format(Locale.getDefault(), "%s(%s),��ʼ����,error = %d", city, OFFLINE_TYPENAME[type], error);
		if (mToast == null)
			mToast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
		else
			mToast.setText(str);
		mToast.show();

		updateDownLoading();
	}
	@Override
	public void onDownLoadStop(String city, int type, int error) {
		Log.i("offline", "onDownLoadStop city = " + city + ", type = " + type + ", error = " + error);

		String str = String.format(Locale.getDefault(), "%s(%s),ֹͣ����,error = %d", city, OFFLINE_TYPENAME[type], error);
		if (mToast == null)
			mToast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
		else
			mToast.setText(str);
		mToast.show();

		updateDownLoading();
	}
	@Override
	public void onDownLoadData(String city, int type, int error) {
		Log.i("offline", "onDownLoadData city = " + city + ", type = " + type + ", error = " + error);
		if (error == TErrorCode.OK)
			updateDownLoading();
	}
	@Override
	public void onDownLoadOver(String city, int type, int error) {
		Log.i("offline", "onDownLoadOver city = " + city + ", type = " + type + ", error = " + error);

		String str = String.format(Locale.getDefault(), "%s(%s),�������,error = %d", city, OFFLINE_TYPENAME[type], error);
		if (mToast == null)
			mToast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
		else
			mToast.setText(str);
		mToast.show();

		updateDownLoading();
		updateDownLoaded();
		mMapView.setOfflineMaps(offlineMapMng.searchLocalMaps());
	}
	@Override
	public void onDownLoadDelete(String city, int type, int error) {
		Log.i("offline", "onDownLoadDelete city = " + city + ", type = " + type + ", error = " + error);
		updateDownLoading();
		updateDownLoaded();
	}
	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		if (parent == mListAllMaps) {
			MapAdminSet adminSet = (MapAdminSet)mAdapterAllMap.getGroup(groupPosition);
			mAdapterAllMap.selCity = adminSet.getCitys().get(childPosition);
			removeDialog(DIALOG_PAGE_ALLMAPS);
			showDialog(DIALOG_PAGE_ALLMAPS);
		} else if (parent == mListDownLoading) {
			mAdapterDownLoading.mSelMap = (TOfflineMapInfo)mAdapterDownLoading.getChild(groupPosition, childPosition);
			if (groupPosition == 0) {
				removeDialog(DIALOG_PAGE_LOADING_GROUP_LOADING);
				showDialog(DIALOG_PAGE_LOADING_GROUP_LOADING);
			} else {
				removeDialog(DIALOG_PAGE_LOADING_GROUP_PAUSE);
				showDialog(DIALOG_PAGE_LOADING_GROUP_PAUSE);
			}
		} else if (parent == mListDownLoaded) {
			mAdapterDownLoaded.mSelMap = (TOfflineMapInfo)mAdapterDownLoaded.getChild(groupPosition, childPosition);
			if (groupPosition == 0) {
				removeDialog(DIALOG_PAGE_LOADED_GROUP_LOADED);
				showDialog(DIALOG_PAGE_LOADED_GROUP_LOADED);
			} else {
				removeDialog(DIALOG_PAGE_LOADED_GROUP_UPDATE);
				showDialog(DIALOG_PAGE_LOADED_GROUP_UPDATE);
			}
		}
		return true;
	}

	@Override
	public void onMapParamChange(int arg0) {
		// TODO Auto-generated method stub
		if(arg0 == 0)
		{
			GeoPoint pt = mMapView.getMapCenter();
			mEditLon.setText( String.format("%4f", pt.getLongitudeE6()/1000000.0f) );
			mEditLat.setText(String.format("%4f", pt.getLatitudeE6()/1000000.0f));
		}else if(arg0 == 3)
		{
			mEditScale.setText( String.format("%d",mMapView.getZoomLevel()) );
		}
	}
}