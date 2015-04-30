package com.tianditudemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.ItemizedOverlay;
import com.tianditu.android.maps.MapController;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.OnGetPoiResultListener;
import com.tianditu.android.maps.Overlay;
import com.tianditu.android.maps.OverlayItem;
import com.tianditu.android.maps.PoiInfo;
import com.tianditu.android.maps.PoiSearch;
import com.tianditu.android.maps.TOfflineMapInfo;
import com.tianditu.android.maps.TOfflineMapManager;
import com.tianditu.android.maps.PoiSearch.CityInfo;
import com.tianditu.android.maps.PoiSearch.ProvinceInfo;
import com.tianditu.android.maps.TOfflineMapManager.MapAdminSet;
import com.tianditu.android.maps.TOfflineMapManager.OnGetMapsResult;
import com.tianditudemo.MainActivity.MyOverItemT;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/*
 * 主要用来测试SDK代码
 * */
public class PoiSearchDemo extends Activity implements OnGetPoiResultListener, OnGetMapsResult{
	private MapView mMapView = null;
	public static Context mCon   = null;
	
	
	protected MapController mController    = null; 
	ArrayList<String[]> list = new ArrayList<String[]>();
	private ArrayList<TOfflineMapInfo> arrayList_offlineMapInfos;
	private TOfflineMapManager offlineMapMng= null;
	private TOfflineMapInfo cityInfo = null;
	
	Map<String, String> ctsMap = new HashMap<String, String>();
	MyOverItemT overItemT = null;
	private String cityNameString_ = "Nanjing";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mCon = this;
		setContentView(R.layout.poisearch);
		mMapView = (MapView)findViewById(R.id.mapview);
		Button search = (Button)findViewById(R.id.search_byName);
		
		ctsMap.put("南京", "Nanjing");
		ctsMap.put("北京", "Beijing");
		
		mMapView.setCachePath("/sdcard/tianditu/map/");
		
		mMapView.setPlaceName(true);
		mMapView.setLogoPos(MapView.LOGO_RIGHT_BOTTOM);
		mMapView.setSatellite(false);
		mMapView.setDoubleTapEnable(true);
		mMapView.setBuiltInZoomControls(true);
		mController = mMapView.getController();
		mController.setZoom(10);
		
		offlineMapMng = new TOfflineMapManager(this);
		offlineMapMng.setMapPath("/sdcard/tianditu/staticmap/");
		arrayList_offlineMapInfos = offlineMapMng.searchLocalMaps();
//		 if exist. add offline map
		loadOfflineMap(0, cityNameString_);
		
		
		//Button searchInView = (Button)findViewById(R.id.search_inView);
		//Button searchById = (Button)findViewById(R.id.search_byId);
		//Button searchByCode = (Button)findViewById(R.id.search_byGbcode);
		search.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				EditText et = (EditText)findViewById(R.id.main_search_poi_edit_name);
				if(et == null){
					return;
				}
				if(et.getText() == null ||  et.getText().toString().equals("")){
					Toast.makeText(mCon,"请输入您要查的地点",Toast.LENGTH_SHORT).show();
					return;
				}
				String searchcondition = et.getText().toString();
				PoiSearch poi = new PoiSearch(mCon,PoiSearchDemo.this,mMapView);
				poi.search(searchcondition,null,null);
			}
		});
	}
	
	private boolean loadOfflineMap(int _style, String _cityNameString)
	{
		int i;
		String cityNameString;
		
		mMapView.setPlaceName(true);
		mMapView.setLogoPos(MapView.LOGO_RIGHT_BOTTOM);
		mMapView.setDoubleTapEnable(true);
		mMapView.setBuiltInZoomControls(true);
		mController = mMapView.getController();
		mController.setZoom(10);
		Log.i("info", arrayList_offlineMapInfos.size() + "");
		if(!arrayList_offlineMapInfos.isEmpty())
		{
			switch (_style) {
			case 0:	//矢量图
				for(i = 0; i < arrayList_offlineMapInfos.size(); ++i)
				{
					cityNameString = ctsMap.get(arrayList_offlineMapInfos.get(i).getCity());
					Log.i("info", cityNameString + "case 0");
					if(cityNameString == _cityNameString)
					{
						cityInfo = arrayList_offlineMapInfos.get(i);
						mController = mMapView.getController();
						mController.setZoom(10);
						mController.animateTo(cityInfo.getCenter());
						offlineMapMng.getMapList();
						mMapView.setOfflineMaps(offlineMapMng.searchLocalMaps());
						return(true);
					}
				}
				break;
			case 1: //影像图
				for(i = 0; i < arrayList_offlineMapInfos.size(); ++i)
				{
					cityNameString = ctsMap.get(arrayList_offlineMapInfos.get(i).getCity());
					//cityNameString = arrayList_offlineMapInfos.get(i).getCity();
					Log.i("info", cityNameString + "case 1");
					if(i== 1/*(cityNameString.length() > _cityNameString.length()) && (cityNameString.substring(_cityNameString.length() + 1, _cityNameString.length() + 3) == "img")*/)
					{
						cityInfo = arrayList_offlineMapInfos.get(i);
						mController = mMapView.getController();
						mController.setZoom(10);
						mController.animateTo(cityInfo.getCenter());
						offlineMapMng.getMapList();
						mMapView.setOfflineMaps(offlineMapMng.searchLocalMaps());
						return(true);
					}
				}
				break;
			default:
				Log.i("info", "search null");
				return(false);
			}
		}
		Log.i("info", "arrayList_OfflineMapInfos is null");
		return(false);
	}
	
	/*
	 * 自定义覆盖物
	 * */
	static class OverItemT extends ItemizedOverlay<OverlayItem> implements Overlay.Snappable
    {
    	private List<OverlayItem> GeoList = new ArrayList<OverlayItem>();
        private static Drawable mMaker = null;
        public OverItemT(Drawable marker, Context context) {
	        super((mMaker = boundCenterBottom(marker)));
        }
			
        @Override
        protected OverlayItem createItem(int i) {
        	return GeoList.get(i);
        }
         
        @Override
        public int size() {
        	return GeoList.size();
        }
        public void addItem(OverlayItem item)
        {
        	item.setMarker(mMaker);
        	GeoList.add(item);
        }
        
        @Override
        public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        	super.draw(canvas, mapView, shadow);
        }
        
        @Override
        // 处理当点击事件
        protected boolean onTap(int i) {
        	Toast.makeText(mCon, GeoList.get(i).getTitle(), Toast.LENGTH_SHORT).show();
        	return super.onTap(i);
        }
        
        public void Populate()
        {
        	populate();
        }
        
    }

	@Override
	public void OnGetPoiResult(ArrayList<PoiInfo> poiInfo,
			ArrayList<ProvinceInfo> cityInfo, String keyword, int error) {
		// TODO Auto-generated method stub@Override
			// TODO Auto-generated method stub
			if(poiInfo == null || poiInfo.size() == 0){
				String str = null;
				if(cityInfo == null || cityInfo.size() == 0){
					str = "没有找到结果";
					
				}
				else{
					str = "在";
					for(int i = 0;i < cityInfo.size();i++){
						CityInfo city = cityInfo.get(i);
						str += city.mStrName + ",";
					}
					str += "找到结果";
					
				}
				Log.v("sdk",str);
				Toast.makeText(mCon,str,Toast.LENGTH_SHORT).show();
				return;
			}
			List<Overlay> list = mMapView.getOverlays();
			list.clear();
			Resources res = getResources();
			Drawable marker = res.getDrawable(R.drawable.poi_xml);
			OverItemT overlay = new OverItemT(marker, mCon);
			for(int i=0; i<poiInfo.size(); i++)
			{
				PoiInfo info = poiInfo.get(i);
				int size = info.mBusLine.size();
				String str = "";
				if(size > 0){
					str = ",经过该站的公交:";
				}
				for(int j = 0;j < size;j++){
					str += info.mBusLine.get(j).getName() + ",";
				}
				OverlayItem item = new OverlayItem(info.mPoint, info.mStrName + str, Integer.toString(i));
				overlay.addItem(item);
				item.getMarker(OverlayItem.ITEM_STATE_FOCUSED_MASK);
			}
			GeoPoint point = poiInfo.get(0).mPoint;
			mMapView.getController().animateTo(point);
			overlay.Populate();
			list.add(overlay);
			mMapView.postInvalidate();
	}

	@Override
	public void onGetResult(ArrayList<MapAdminSet> arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
}


