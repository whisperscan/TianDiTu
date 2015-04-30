package com.tianditudemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tianditu.android.maps.TBusRoute;
import com.tianditu.android.maps.TBusRoute.OnTransitResultListener;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapController;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.Overlay;
import com.tianditu.android.maps.OverlayItem;
import com.tianditu.android.maps.TOfflineMapInfo;
import com.tianditu.android.maps.TOfflineMapManager;
import com.tianditu.android.maps.TOfflineMapManager.MapAdminSet;
import com.tianditu.android.maps.TOfflineMapManager.OnGetMapsResult;
import com.tianditu.android.maps.TTransitLine;
import com.tianditu.android.maps.TTransitSegmentInfo;
import com.tianditu.android.maps.TTransitResult;
import com.tianditu.android.maps.TTransitSegmentLine;
import com.tianditudemo.MainActivity.MyOverItemT;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class StadiometryActivity extends Activity implements OnTransitResultListener, OnGetMapsResult{
	private MapView mMapView = null;
	public static Context mCon   = null;
	public static TBusRoute busRoute =  null;
	public boolean  mIsStart = false;
	public GeoPoint mStart = null;
	public GeoPoint mEnd = null;
	private int     mCount = 0;
	private ListView mList = null;
	private TTransitResult mResult = null;
	private BusOverlay	  mBusOverlay = null;
	
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
		setContentView(R.layout.stadiometry);
		mList = (ListView)findViewById(R.id.list);
		mMapView = (MapView)findViewById(R.id.mapview);
		Button search = (Button)findViewById(R.id.bus_route);
		
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
//		try {
//			Bundle bundle = getIntent().getExtras();
//			arrayList_offlineMapInfos = ( ArrayList<TOfflineMapInfo>)bundle.getSerializable("offlineMap");
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
		offlineMapMng = new TOfflineMapManager(this);
		offlineMapMng.setMapPath("/sdcard/tianditu/staticmap/");
		arrayList_offlineMapInfos = offlineMapMng.searchLocalMaps();
//		 if exist. add offline map
		loadOfflineMap(0, cityNameString_);
			
			
	    mMapView.getOverlays().add(new MyOverlay(this));
		search.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				mList.setVisibility(View.GONE);
				mCount = 0;
				mIsStart = true;
				Toast.makeText(mCon,"请选择起点",Toast.LENGTH_SHORT).show();
			}
		});
		mCon = this;
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
	
	 public class MyOverlay extends Overlay{
	    	private Context mCon = null;
	    	private OverlayItem mItem = null;
	    	private Paint       mPaint= null;
	    	public MyOverlay(Context con)
	    	{
	    		mCon = con;
	    		mPaint = new Paint();
	    	}
			@Override
			public boolean onTap(GeoPoint point, MapView mapView) {
				// TODO Auto-generated method stub
				mItem = new OverlayItem(point, "Tap", point.toString());
				if(mIsStart == false){
					return false;
				}
				if(mCount == 0){
					mStart = point;
					Toast.makeText(mCon,"请选择终点",Toast.LENGTH_SHORT).show();
				}
				else if(mCount == 1){
					mEnd = point;
					mCount = 0;
					busRoute = new TBusRoute(StadiometryActivity.this);
					mIsStart = false;
					busRoute.startRoute(mStart, mEnd, TBusRoute.BUS_TYPE_FASTEST);
					
				}
				mCount ++ ;
				mapView.postInvalidate();
				return true;
			}
			
			@Override
			public void draw(Canvas canvas, MapView mapView, boolean shadow) {
				// TODO Auto-generated method stub
				super.draw(canvas, mapView, shadow);
				if(mItem == null)
					return;
				mPaint.setColor(Color.RED);
				Drawable d = mCon.getResources().getDrawable(R.drawable.tips_arrow);
				
				Point point = mapView.getProjection().toPixels(mItem.getPoint(), null);
				d.setBounds(point.x - d.getIntrinsicWidth()/2, point.y-d.getIntrinsicHeight()
						, point.x + d.getIntrinsicWidth()/2, point.y);
				d.draw(canvas);
				Rect bounds = new Rect();
				mPaint.getTextBounds(mItem.getSnippet(), 0, mItem.getSnippet().length()-1, bounds);
				canvas.drawText(mItem.getSnippet(), point.x-bounds.width()/2, point.y-d.getIntrinsicHeight(), mPaint);
			}
	    	
	    }

	@Override
	public void onTransitResult(TTransitResult result,int error) {
		// TODO Auto-generated method stub
			// TODO Auto-generated method stub
		if(result == null || result.getTransitLines().size() == 0){
			Toast.makeText(mCon, "未找到结果",Toast.LENGTH_LONG).show();
			return;
		}
		mResult = result;
		mList.setVisibility(View.VISIBLE);
		ArrayList<String> data = new ArrayList<String>();
		data.add("是否有地铁：" + result.hasSubWay());
		for(int i=0;i < result.getTransitLines().size();i++){
			TTransitLine line = result.getTransitLines().get(i);
			data.add("");
			data.add("方案" + (i+1));
			data.add("线路名称：" + line.getName());
			data.add("线路里程：" + line.getLength() + "米");
			data.add("线路用时：" + line.getCostTime() + "分");
			ArrayList<TTransitSegmentInfo> infos = line.getSegmentInfo();
			for(int j = 0;j < infos.size();j++){
				TTransitSegmentInfo info  = infos.get(j);
				data.add("");
				data.add("路段" + (j+1));
				data.add("路段起点：" + info.getStart().getName());
				data.add("路段终点：" + info.getEnd().getName());
				String str = "" + info.getType();
				int type = info.getType();
				if(type == 1){
					str += "(步行)";
				}
				else if(type == 2){
					str += "(公交)";
				}
				else if(type == 3){
					str += "(地铁)";
				}
				else{
					str += "(站内换乘)";
				}
				data.add("路段类型：" + str);
				for(int k=0;k < info.getSegmentLine().size();k++){
					TTransitSegmentLine segmentLine = info.getSegmentLine().get(k);
					data.add("路段方案" + (k + 1) + "名称：" + segmentLine.getName());
					data.add("路段方案" + (k + 1) + "方向：" + segmentLine.getDirection());
					data.add("路段方案" + (k + 1) + "id：" + segmentLine.getId());
					data.add("路段方案" + (k + 1) + "经过站数：" + segmentLine.getStationCount());
					data.add("路段方案" + (k + 1) + "里程：" + segmentLine.getLength() + "米");
					data.add("路段方案" + (k + 1) + "坐标点：" + segmentLine.getShapePoints());
				}
			}
		}
		mList.setAdapter((ListAdapter) new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,data));
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				mList.setVisibility(View.GONE);
				// TODO Auto-generated method stub
				List<Overlay> overlay = mMapView.getOverlays();
				if(mBusOverlay != null){
					overlay.remove(mBusOverlay);
					mBusOverlay = null;
				}
				mBusOverlay = new BusOverlay(mCon);
				mBusOverlay.initBus(mCon,mResult, 0);
				overlay.add(mBusOverlay);
			}
		});
	}

	@Override
	public void onGetResult(ArrayList<MapAdminSet> arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
}
