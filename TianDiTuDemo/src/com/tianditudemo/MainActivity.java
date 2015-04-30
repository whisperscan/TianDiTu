package com.tianditudemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Map;

import com.tianditudemo.R;
import com.tianditudemo.PathPlanningActivity.MyOverlay;

import android.R.string;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.ItemizedOverlay;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MapController;
import com.tianditu.android.maps.Overlay;
import com.tianditu.android.maps.TOfflineMapInfo;
import com.tianditu.android.maps.TOfflineMapManager;
import com.tianditu.android.maps.TOfflineMapManager.MapAdminSet;

import com.tianditu.android.maps.MyLocationOverlay;
import com.tianditu.android.maps.OverlayItem;
import com.tianditu.android.maps.TMapLayerManager;
import com.tianditu.android.maps.MapView.LayoutParams;
import com.tianditu.android.maps.TOfflineMapManager.OnGetMapsResult;

public class MainActivity extends Activity implements OnGetMapsResult
{
	private TMapLayerManager mLayerMnger = null;
	public static View  mPopView           = null;
	public static MapView mMapView         = null;
	public static TextView  mText          = null;
	protected View   mViewLayout           = null;
	protected MapController mController    = null; 
	protected Context mCon                 = null;
	// Present Location.
	private MyLocationOverlay myLocation = null;
	private Spinner mySpinner;
	private String cityNameString_ = "Nanjing";
	//int tableNameID = 0;
	Map<String, String> ctsMap = new HashMap<String, String>();
	MyOverItemT overItemT = null;
	
	ArrayList<String[]> list = new ArrayList<String[]>();
	private ArrayList<TOfflineMapInfo> arrayList_offlineMapInfos =  new ArrayList<TOfflineMapInfo>();
	private TOfflineMapManager offlineMapMng= null;
	private TOfflineMapInfo cityInfo = null;
	
	DBAdapter myDBAdapter;
	
    private double mLon1 =  118.7625459558;
    private double mLat1 = 32.0768838682; 
    private double mLon2 = 118.7671393779;
    private double mLat2 = 32.0683125820; 
    private double mLon3 = 118.7818954346;
    private double mLat3 = 32.0729897876;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);	
		// delete 12.7 openDB();
		
		// list add.
		list.add(DBAdapter.TABLE_TB_GPS_COL);
		list.add(DBAdapter.TABLE_TB_PIC_COL);
		list.add(DBAdapter.TABLE_TB_VIDEO_COL);
		list.add(DBAdapter.TABLE_TB_VOICE_COL);
		list.add(DBAdapter.TABLE_TB_COMPASS_COL);
		list.add(DBAdapter.TABLE_TB_LIGHT_COL);
		list.add(DBAdapter.TABLE_TB_PPM_COL);
		list.add(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL);
		list.add(DBAdapter.TABLE_TB_MEDICAL_DEAL_COL);
		list.add(DBAdapter.TABLE_PLACE_ACCESS_COL);
		list.add(DBAdapter.TABLE_TB_RESCUE_INFO_COL);
		list.add(DBAdapter.TABLE_TB_RETREAT_APPLY_COL);
		list.add(DBAdapter.TABLE_TB_SAVE_SIT_COL);
		list.add(DBAdapter.TABLE_TB_RESCUE_SIT_COL);
		list.add(DBAdapter.TABLE_TB_VICTIMS_DEAL_COL);
		list.add(DBAdapter.TABLE_VICTIMS_INFO_COL);
		list.add(DBAdapter.TABLE_TB_SEARCH_SIT_COL);	
		
		/* city name (key/value) */
		ctsMap.put("ÄÏ¾©", "Nanjing");
		ctsMap.put("±±¾©", "Beijing");
		
		
		LayoutInflater inflater = LayoutInflater.from(this);
		mPopView = inflater.inflate(R.layout.main, null);
		setContentView(R.layout.main);
		mMapView = (MapView)findViewById(R.id.mapview);
		mMapView.setCachePath("/sdcard/tianditu/map/");
		
		mMapView.getOverlays().add(new MyOverlay(this));
		mCon = this;
		
		// Spinner part.
		mySpinner = (Spinner)findViewById(R.id.spn_tbn);
		// Creating data source.
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.table_name, android.R.layout.simple_spinner_item);
		// Setting drop view mode.
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Setting drop view adapter.
		mySpinner.setAdapter(adapter);
		// Setting drop view controller.
		mySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//tableNameID = (int)id;
				// Add location to the map, when table changed.
				addLocationFromTable((int)id);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				//tableNameID = 0;
			}
		});
		// Spinner end.
		
		mLayerMnger = new TMapLayerManager(mMapView);
		
		initMap();
	}
	
	
// Initalize mapview
	private void initMap()
	{

		
		Log.i("info", "initialize");
		
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
		if(loadOfflineMap(0, cityNameString_))
		{
			Resources res = getResources();
			Drawable marker = res.getDrawable(R.drawable.poi_xml);
			overItemT = new MyOverItemT(marker, this);
			overItemT.addPoint(mLon1, mLat1,"p1");
			overItemT.addPoint(mLon2, mLat2,"p2");
			overItemT.addPoint(mLon3, mLat3,"p3");
			overItemT.refresh();
			mMapView.getOverlays().add(overItemT);
			
			mPopView = super.getLayoutInflater().inflate(R.layout.popview, null);
			mText    = (TextView)mPopView.findViewById(R.id.text);
			mMapView.addView(mPopView,
	                new MapView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
	                		null, MapView.LayoutParams.TOP_LEFT));
			mPopView.setVisibility(View.GONE);
		}
	}
	
	// For change difference style offline map.
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
			case 0:	//Ê¸Á¿Í¼
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
			case 1: //Ó°ÏñÍ¼
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
	
	
	/* delete 12.7
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		closeDB();
	}

	private void openDB() {
		// TODO Auto-generated method stub
		myDBAdapter = new DBAdapter(this);
		myDBAdapter.open();
	}	
	
	private void closeDB() {
		// TODO Auto-generated method stub
		myDBAdapter.close();
	}
	*/
	public void addLocationFromTable(int tableID) 
	{
		String[] items = list.get(tableID); 
		
		Log.i("info", items[0]);
		String tableName = DBAdapter.ALL_TABLES[tableID];
		Log.i("info", tableName);
		
		ContentResolver cResolver = getContentResolver();
		Uri sUri = Uri.parse(DBAdapter.CONTENT_URI + tableName);
		Cursor myCursor = cResolver.query(sUri, null, null, null, null);
		
		// delete 12.7 Cursor myCursor = myDBAdapter.getAllRows(tableName,items);
		if(!myCursor.moveToFirst())	return;
		
		Log.i("info", myCursor.getString(0));
		
		int row = myCursor.getCount();
		int col = myCursor.getColumnCount();
		String[] rowStrings = new String[row];
		double[][] posinfo = new double[row][2];
		
		int pos = 0;
		MainActivity.mPopView.setVisibility(View.INVISIBLE);
		overItemT.delPoint();
		mMapView.refreshDrawableState();
		for(Boolean result = myCursor.moveToFirst();result;result = myCursor.moveToNext())
		{
			for(int i = 0;i < col;i++)
			{
				if(i == (col -1))
				{
					rowStrings[pos] += items[i] + " = " + myCursor.getString(i);
				}
				else
				{
					rowStrings[pos] += items[i] + " = " + myCursor.getString(i) + '\n';
				}
				if(items[i] == "lng")	posinfo[pos][0] = myCursor.getDouble(i);
				if(items[i] == "lat")	posinfo[pos][1] = myCursor.getDouble(i);
			}
			pos++;
		}
		
		for(int i = 0;i < row;++i)
		{
			overItemT.addPoint(posinfo[i][0], posinfo[i][1],rowStrings[i]);
			overItemT.refresh();
		}
		myCursor.close();
	}
	
	
	protected boolean isRouteDisplayed() 
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		mMapView.getController().stopAnimation(false);
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		/*
		if(keyCode == KeyEvent.KEYCODE_BACK)
			System.exit(0);
		*/
		return super.onKeyUp(keyCode, event);
	}
	
	
	// Controller.
	public void onClickVector(View v)
	{
		mMapView.setSatellite(false);
		if(loadOfflineMap(0, cityNameString_))
		{
			int zoomLevel = mMapView.getZoomLevel();
			mMapView.getController().setZoom(zoomLevel);
		}
	}

	public void onClickImage(View v) 
	{
		mMapView.setSatellite(true);
		if(loadOfflineMap(1, cityNameString_))
		{
			int zoomLevel = mMapView.getZoomLevel();
			mMapView.getController().setZoom(zoomLevel);
		}
	}
	
	public void onClickSwitchWater(View v)
	{
		if(mMapView.getMapType() == MapView.TMapType.MAP_TYPE_VEC)
			mMapView.setSatellite(true);
		if(loadOfflineMap(1, cityNameString_))
		{
			String[] layer = mLayerMnger.getLayers(mMapView.getMapType());
			if(layer.length >= 3)
			{
				Log.i("info", layer[1]);
				String []layers = new String[]{layer[1]};
				mLayerMnger.setLayersShow(layers);
			}
		}
	}
	
	public void onClickSwitchRailway(View v)
	{
		if(mMapView.getMapType() == MapView.TMapType.MAP_TYPE_VEC)
			mMapView.setSatellite(true);
		if(loadOfflineMap(1, cityNameString_))
		{
			String[] layer = mLayerMnger.getLayers(mMapView.getMapType());
			if(layer.length >= 3)
			{
				Log.i("info", layer[2]);
				String []layers = new String[]{layer[2]};
				mLayerMnger.setLayersShow(layers);
//				String[] l = mLayerMnger.getLayersShow();
//				String str = "";
//				for (int i = 0; i < l.length; i++) {
//					str += l[i];
//				}
//				Log.i("info", str);
			}
		}
	}
	
	public void onClickLocation(View v) 
	{
		
		// Setting present location.
		myLocation = new MyLocationOverlay(this, mMapView);
		// Enable and display compass.
		myLocation.enableCompass();
		// Enable present location.
		myLocation.enableMyLocation();
		mMapView.getOverlays().add(myLocation);
		/*
		GeoPoint point = myLocation.getMyLocation();
		if(point != null)
		{
			mMapView.getController().animateTo(point);
			String strLog = String.format("å½“å‰ä½ç½®:\r\n" + "ç»åº¦:%d\r\n" +"çº¬åº¦:%d",point.getLongitudeE6(), point.getLatitudeE6());
			Toast.makeText(this, strLog, Toast.LENGTH_SHORT).show();
		}*/
	}
	
	public void onClickSearch(View v)
	{
		Intent intent = null;
		intent = new Intent(MainActivity.this, PoiSearchDemo.class);
		startActivity(intent);
	}

	public void onClickStadiometry(View v)
	{
		Intent intent = null;
		Bundle bundle = new Bundle();
		bundle.putSerializable("offlineMap", arrayList_offlineMapInfos);
		intent = new Intent(MainActivity.this, StadiometryActivity.class);
		startActivity(intent);
	}
	
	
	public void onClickPathPlanning(View v)
	{
		Intent intent = null;
		intent = new Intent(MainActivity.this, PathPlanningActivity.class);
		startActivity(intent);
	}
	/*
	 * Attribute: Internal class
	 * Function:  Display database info with air bubble.
	 */
	static class MyOverItemT extends ItemizedOverlay<OverlayItem>
	{
		private static List<OverlayItem> GeoList = new ArrayList<OverlayItem>();
		private Context mContext;
//        private double mLon1 =  118.7625459558;
//        private double mLat1 = 32.0768838682; 
//        private double mLon2 = 118.7671393779;
//        private double mLat2 = 32.0683125820; 
//        private double mLon3 = 118.7818954346;
//        private double mLat3 = 32.0729897876;
		
        public void addPoint(double mLon, double mLat,String infoString) 
        {
			GeoPoint point = new GeoPoint((int)(mLat * 1E6),(int)(mLon * 1E6));
			GeoList.add(new OverlayItem(point, infoString, infoString));
		}
        
        public void delPoint()
        {
        	GeoList.clear();
        	refresh();
        }
        
        public void refresh() 
        {
        	populate();
		}
        
		public MyOverItemT(Drawable marker,Context context) 
		{
			super(boundCenter(marker));
			// TODO Auto-generated constructor stub
			this.mContext = context;
//			// Create "GeoPoint" use "mLat1,mLon1","mLat2,mLon2","mLat3,mLon3"
//			GeoPoint p1 = new GeoPoint((int)(mLat1 * 1E6),(int)(mLon1 * 1E6));
//			GeoPoint p2 = new GeoPoint((int)(mLat2 * 1E6),(int)(mLon2 * 1E6));
//			GeoPoint p3 = new GeoPoint((int)(mLat3 * 1E6),(int)(mLon3 * 1E6));
//			GeoList.add(new OverlayItem(p1, "P1P1P1P1P1P1P1P1P1P1P1P1P1P1P1P1P1P1P1P1P1P1", "point1"));
//			GeoList.add(new OverlayItem(p2, "P2P2P2P2P2P2P2P2P2P2P2P2P2P2P2P2P2P2P2P2P2P2", "point2"));
//			GeoList.add(new OverlayItem(p3, "P3P3P3P3P3P3P3P3P3P3P3P3P3P3P3P3P3P3P3P3P3P3", "point3"));
//			populate();
		}

		@Override
		protected OverlayItem createItem(int i) 
		{
			// TODO Auto-generated method stub
			// Create point items, called by parent class.
			return GeoList.get(i);
		}

		@Override
		public int size() {
			// TODO Auto-generated method stub
			return GeoList.size();
		}
		
		protected boolean onTap(int i) 
		{
			GeoPoint pt = GeoList.get(i).getPoint();
    		MainActivity.mMapView.updateViewLayout( MainActivity.mPopView,
                    new MapView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
                    		pt, MapView.LayoutParams.BOTTOM_CENTER));
    		MainActivity.mPopView.setVisibility(View.VISIBLE);
    		MainActivity.mText.setText(GeoList.get(i).getTitle());
			
			//Toast.makeText(mContext, GeoList.get(i).getSnippet(), Toast.LENGTH_SHORT).show();
			return true;
		}
		
		@Override
		public boolean onTap(GeoPoint p, MapView mapView) {
			// TODO Auto-generated method stub
			MainActivity.mPopView.setVisibility(View.GONE);
			return super.onTap(p, mapView);
		}
		
	}


	@Override
	public void onGetResult(ArrayList<MapAdminSet> arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	// add in 4.27
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
}

