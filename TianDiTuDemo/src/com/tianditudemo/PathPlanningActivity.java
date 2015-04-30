package com.tianditudemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.tianditu.android.maps.TDrivingRoute;
import com.tianditu.android.maps.TDrivingRoute.OnDrivingResultListener;
import com.tianditu.android.maps.TDrivingRouteResult;
import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapController;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.Overlay;
import com.tianditu.android.maps.OverlayItem;
import com.tianditu.android.maps.Projection;
import com.tianditu.android.maps.TOfflineMapInfo;
import com.tianditu.android.maps.TOfflineMapManager;
import com.tianditu.android.maps.TOfflineMapManager.MapAdminSet;
import com.tianditu.android.maps.TOfflineMapManager.OnGetMapsResult;
import com.tianditudemo.MainActivity.MyOverItemT;

public class PathPlanningActivity extends Activity implements OnDrivingResultListener, OnGetMapsResult{
	private DrivingOverlay mDrivingOverlay = null;
	private MapView        mMapView        = null;
	private int			   mIndex = 0;
	private GeoPoint	   mStart = null;
	private GeoPoint	   mEnd = null;
	private GeoPoint	   p1 = null;
	private GeoPoint	   p2 = null;
	private GeoPoint	   p3 = null;
	private GeoPoint	   p4 = null;
	private ArrayList<GeoPoint> mPoints = new ArrayList<GeoPoint>();

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
		setContentView(R.layout.path_planning);
		mDrivingOverlay = new DrivingOverlay(this);
		mMapView = (MapView)findViewById(R.id.driving_mapview);
		mMapView.setDrawOverlayWhenZooming(false);
		
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
		
		
		mMapView.getOverlays().add(new MyOverlay(this));
		MapController mController = mMapView.getController();
		mController.setZoom(10);
		//mController.animateTo(new GeoPoint(39945124,116245124));
		TDrivingRoute route =  new TDrivingRoute(this);
//		GeoPoint start = new GeoPoint(39880000, 116310000);
//		GeoPoint end   = new GeoPoint(40030000, 116290000);
//		route.startRoute(start, end, null,1);
		Button btn = null;
		btn = (Button)findViewById(R.id.start);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				mIndex = 0;
				Toast.makeText(PathPlanningActivity.this, "请选择起点", Toast.LENGTH_SHORT).show();
			}
		});
		btn = (Button)findViewById(R.id.one);
		btn.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				mIndex = 1;
				Toast.makeText(PathPlanningActivity.this, "请选择途经点1", Toast.LENGTH_SHORT).show();
			}
		});
		btn = (Button)findViewById(R.id.two);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				mIndex = 2;
				Toast.makeText(PathPlanningActivity.this, "请选择途经点2", Toast.LENGTH_SHORT).show();
			}
		});
		btn = (Button)findViewById(R.id.three);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				mIndex = 3;
				Toast.makeText(PathPlanningActivity.this, "请选择途经点3", Toast.LENGTH_SHORT).show();
			}
		});
		btn = (Button)findViewById(R.id.four);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				mIndex = 4;
				Toast.makeText(PathPlanningActivity.this, "请选择途经点4", Toast.LENGTH_SHORT).show();
			}
		});
		btn = (Button)findViewById(R.id.end);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				mIndex = 5;
				Toast.makeText(PathPlanningActivity.this, "请选择终点", Toast.LENGTH_SHORT).show();
			}
		});
		
		btn = (Button)findViewById(R.id.route);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if(mStart == null){
					Toast.makeText(PathPlanningActivity.this, "请选择起点", Toast.LENGTH_SHORT).show();
					return;
				}
				if(mEnd == null){
					Toast.makeText(PathPlanningActivity.this, "请选择终点", Toast.LENGTH_SHORT).show();
					return;
				}
				mPoints.clear();
				if(p1 != null){
					mPoints.add(p1);
				}
				if(p2 != null){
					mPoints.add(p2);
				}
				if(p3 != null){
					mPoints.add(p3);
				}
				if(p4 != null){
					mPoints.add(p4);
				}
				TDrivingRoute route =  new TDrivingRoute(PathPlanningActivity.this);
				route.startRoute(mStart, mEnd, mPoints, TDrivingRoute.DRIVING_TYPE_FASTEST);
			}
		});
		
		Button btn_type = null;
		btn_type = (Button)findViewById(R.id.fastest);
		btn_type.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				TDrivingRoute route =  new TDrivingRoute(PathPlanningActivity.this);
				
				route.startRoute(mStart, mEnd, mPoints, TDrivingRoute.DRIVING_TYPE_FASTEST);
			}
		});
		btn_type = (Button)findViewById(R.id.shortest);
		btn_type.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				TDrivingRoute route =  new TDrivingRoute(PathPlanningActivity.this);
				
				route.startRoute(mStart, mEnd, mPoints,TDrivingRoute.DRIVING_TYPE_SHORTEST);
			}
		});
		btn_type = (Button)findViewById(R.id.not_highway);
		btn_type.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				TDrivingRoute route =  new TDrivingRoute(PathPlanningActivity.this);
				route.startRoute(mStart, mEnd, mPoints, TDrivingRoute.DRIVING_TYPE_NOHIGHWAY);
			}
		});
	}


	@Override
	public void onDrivingResult(TDrivingRouteResult result, int errCode) {
		// TODO Auto-generated method stub
		if(errCode != 0)
		{
			Toast.makeText(this, "规划出错！", Toast.LENGTH_SHORT).show();
			return;
		}
		mDrivingOverlay.setDrivingResult(result);
		GeoPoint point = result.getCenterPoint();
		mMapView.getController().animateTo(point);
		if(mDrivingOverlay != null){
			mMapView.getOverlays().remove(mDrivingOverlay);
		}
		mMapView.getOverlays().add(mDrivingOverlay);
		mMapView.postInvalidate();
		Toast.makeText(this, 
				"长度："+result.getLength() + ",时间：" + result.getCostTime(), 
				Toast.LENGTH_SHORT).show();
		int size = result.getSegmentCount();
		String str= "";
		for(int i = 0;i < size;i++){
			str += "描述：" + result.getSegDescription(i);
			str += "起点坐标" + result.getStartPoint(i);
			str += "街道名称" + result.getStreetName(i);
		}
		Toast.makeText(this,str, Toast.LENGTH_SHORT).show();
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
				if(mIndex == 0){
					mStart = point;
				}
				else if(mIndex == 1){
					p1 = point;
				}
				else if(mIndex == 2){
					p2 = point;
				}
				else if(mIndex == 3){
					p3 = point;
				}
				else if(mIndex == 4){
					p4 = point;
				}
				else if(mIndex == 5){
					mEnd = point;
				}
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
	
	class DrivingOverlay extends Overlay
	{
		private TDrivingRouteResult mResult = null;
		private Drawable 	  mDrawableStart= null;    //开始点图标
		private Drawable      mDrawableEnd  = null;    //终点图标
		private Drawable	  mDrawableMid  = null;  //途经点图标
		
		public DrivingOverlay(Context con){
			if(mDrawableStart == null)
				mDrawableStart = (Drawable)con.getResources().getDrawable(R.drawable.icon_route_start);
			
			if(mDrawableEnd == null)
				mDrawableEnd = (Drawable)con.getResources().getDrawable(R.drawable.icon_route_end);
			
			if(mDrawableMid == null)
				mDrawableMid = (Drawable)con.getResources().getDrawable(R.drawable.icon_route_mid);
		}
		
		void setDrivingResult(TDrivingRouteResult result)
		{
			mResult = result;
			if(result == null)
				return;
		}

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			// TODO Auto-generated method stub
			super.draw(canvas, mapView, shadow);
			if(mResult == null)
				return;
			ArrayList<GeoPoint> points = mResult.getShapePoints();
			Point[] pointScr = convertPoints(mapView.getProjection(), points);
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setStrokeWidth(5);
			paint.setStyle(Style.STROKE);
			Path path = new Path(); 
			path.moveTo(pointScr[0].x, pointScr[0].y);
			for(int i=1; i<pointScr.length; i++)
			{
				path.lineTo(pointScr[i].x, pointScr[i].y);
			}
			canvas.drawPath(path, paint);
			path.reset();
			
			for(int i=0; i<mResult.getSegmentCount(); i++)
			{
				
			}
			//绘制起终点图标
			int wid = mDrawableStart.getIntrinsicWidth();
			int height = mDrawableStart.getIntrinsicHeight();
			mDrawableStart.setBounds(pointScr[0].x - wid/2, pointScr[0].y - height, 
					pointScr[0].x + wid/2, pointScr[0].y);
			mDrawableStart.draw(canvas);
			
			Point endPoint = pointScr[pointScr.length - 1];
			wid = mDrawableEnd.getIntrinsicWidth();
			height = mDrawableEnd.getIntrinsicHeight();
			mDrawableEnd.setBounds(endPoint.x - wid/2, endPoint.y - height, 
					endPoint.x + wid/2, endPoint.y);
			mDrawableEnd.draw(canvas);
			
			for(int i = 0;i < mPoints.size();i++){
				Point midPoint = mapView.getProjection().toPixels(mPoints.get(i), null);
				wid = mDrawableMid.getIntrinsicWidth();
				height = mDrawableMid.getIntrinsicHeight();
				mDrawableMid.setBounds(midPoint.x - wid/2, midPoint.y - height, 
						midPoint.x + wid/2, midPoint.y);
				mDrawableMid.draw(canvas);
			}
			
		}
		
		private Point[] convertPoints(Projection prj, ArrayList<GeoPoint> points)
		{
			Point[] ptsRet = new Point[points.size()];
			for(int i=0; i<ptsRet.length; i++)
			{
				ptsRet[i] = prj.toPixels(points.get(i), null);
			}
			return ptsRet;
		}
		
	}


	@Override
	public void onGetResult(ArrayList<MapAdminSet> arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
}