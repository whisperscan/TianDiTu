package com.tianditudemo;

import com.tianditudemo.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class DBManageActivity extends Activity{

	DBAdapter myDBAdapter;
	TextView textView;
	Spinner mySpinner;
	int tableNameID = 0;
	//delete 12.7 ArrayList<String[]> list = new ArrayList<String[]>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dbmanage);
		textView = (TextView)findViewById(R.id.txt_view);
		// Open database.
		// delete 12.7 openDB();
		// Setting drop view.
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
				tableNameID = (int)id;
				
				// when change the items, we should display present column.
				displayText(getRecordSet(0));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				tableNameID = 0;
			}
		});
		// For debug.
		Log.i("info", "spinner id:"+ mySpinner.getSelectedItemId());
		/* delete 12.7
		// Save all table items.
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
		*/
	}
	/*
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
	public void onClickAddTestingDataToAllTable(View v)
	{
		ContentValues tableValues = new ContentValues();
		
		switch (tableNameID) {
			case 0:				// Table "TB_GPS"'s data values.
				tableValues.put(DBAdapter.TABLE_TB_GPS_COL[1], "2014-11-2 11:11:11");
				tableValues.put(DBAdapter.TABLE_TB_GPS_COL[2], "118.819468");
				tableValues.put(DBAdapter.TABLE_TB_GPS_COL[3], "32.007034");
				break;
			case 1:				// Table "TB_PIC"'s data values.
				tableValues.put(DBAdapter.TABLE_TB_PIC_COL[1], "2014-11-2 11:11:11");
				tableValues.put(DBAdapter.TABLE_TB_PIC_COL[2], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_PIC_COL[3], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_PIC_COL[4], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_PIC_COL[5], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_PIC_COL[6], "118.794747");
				tableValues.put(DBAdapter.TABLE_TB_PIC_COL[7], "32.103005");
				break;
			case 2:				// Table "TB_VIDEO"'s data values.
				tableValues.put(DBAdapter.TABLE_TB_VIDEO_COL[1], "2014-11-2 11:11:11");
				tableValues.put(DBAdapter.TABLE_TB_VIDEO_COL[2], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_VIDEO_COL[3], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_VIDEO_COL[4], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_VIDEO_COL[5], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_VIDEO_COL[6], "118.7791123146");
				tableValues.put(DBAdapter.TABLE_TB_VIDEO_COL[7], "32.0313775917");
				break;
			case 3:				// Table "TB_VOICE"'s data values.
				tableValues.put(DBAdapter.TABLE_TB_VOICE_COL[1], "2014-11-2 11:11:11");
				tableValues.put(DBAdapter.TABLE_TB_VOICE_COL[2], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_VOICE_COL[3], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_VOICE_COL[4], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_VOICE_COL[5], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_VOICE_COL[6], "118.7791123146");
				tableValues.put(DBAdapter.TABLE_TB_VOICE_COL[7], "32.0313775917");
				break;
			case 4:				// Table "TB_COMPASS"'s data values.
				tableValues.put(DBAdapter.TABLE_TB_COMPASS_COL[1], "2014-11-2 11:11:11");
				tableValues.put(DBAdapter.TABLE_TB_COMPASS_COL[2], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_COMPASS_COL[3], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_COMPASS_COL[4], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_COMPASS_COL[5], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_COMPASS_COL[6], "118.7791123146");
				tableValues.put(DBAdapter.TABLE_TB_COMPASS_COL[7], "32.0313775917");
				break;
			case 5:				// Table "TB_LIGHT"'s data values.
				tableValues.put(DBAdapter.TABLE_TB_LIGHT_COL[1], "2014-11-2 11:11:11");
				tableValues.put(DBAdapter.TABLE_TB_LIGHT_COL[2], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_LIGHT_COL[3], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_LIGHT_COL[4], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_LIGHT_COL[5], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_LIGHT_COL[6], "118.7791123146");
				tableValues.put(DBAdapter.TABLE_TB_LIGHT_COL[7], "32.0313775917");
				break;
			case 6:				// Table "TB_PPM"'s data values.
				tableValues.put(DBAdapter.TABLE_TB_PPM_COL[1], "2014-11-2 11:11:11");
				tableValues.put(DBAdapter.TABLE_TB_PPM_COL[2], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_PPM_COL[3], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_PPM_COL[4], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_PPM_COL[5], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_PPM_COL[6], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_PPM_COL[7], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_PPM_COL[8], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_PPM_COL[9], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_PPM_COL[10], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_PPM_COL[11], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_PPM_COL[12], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_PPM_COL[13], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_PPM_COL[14], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_PPM_COL[15], "118.7791123146");
				tableValues.put(DBAdapter.TABLE_TB_PPM_COL[16], "32.0313775917");
				break;
			case 7:				// Table "TB_DEMAGER_EPORT"'s data values.
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[1], "2014-11-2 11:11:11");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[2], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[3], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[4], 1);
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[5], 2);
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[6], 3);
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[7], 4);
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[8], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[9], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[10], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[11], 1);
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[12], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[13], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[14], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[15], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[16], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[17], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[18], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[19], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[20], 1);
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[21], 2);
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[22], 3);
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[23], 4);
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[24], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[25], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[26], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[27], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[28], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[29], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[30], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[31], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[32], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[33], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[34], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[35], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[36], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[37], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[38], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[39], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[40], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[41], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[42], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[43], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[44], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[45], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[46], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[47], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[48], "118.7791123146");
				tableValues.put(DBAdapter.TABLE_TB_DEMAGER_EPORT_COL[49], "varchar");
				break;
			case 8:				// Table "TB_MEDICAL_DEAL"'s data values.
				tableValues.put(DBAdapter.TABLE_TB_MEDICAL_DEAL_COL[1], 1);
				tableValues.put(DBAdapter.TABLE_TB_MEDICAL_DEAL_COL[2], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_MEDICAL_DEAL_COL[3], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_MEDICAL_DEAL_COL[4], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_MEDICAL_DEAL_COL[5], 1);
				tableValues.put(DBAdapter.TABLE_TB_MEDICAL_DEAL_COL[6], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_MEDICAL_DEAL_COL[7], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_MEDICAL_DEAL_COL[8], "2014-11-2 11:11:11");
				tableValues.put(DBAdapter.TABLE_TB_MEDICAL_DEAL_COL[9], "2014-11-2 11:11:11");
				tableValues.put(DBAdapter.TABLE_TB_MEDICAL_DEAL_COL[10], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_MEDICAL_DEAL_COL[11], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_MEDICAL_DEAL_COL[12], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_MEDICAL_DEAL_COL[13], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_MEDICAL_DEAL_COL[14], "2014-11-2 11:11:11");
				break;
			case 9:				// Table "PLACE_ACCESS"'s data values.
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[1], "2014-11-2 11:11:11");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[2], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[3], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[4], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[5], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[6], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[7], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[8], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[9], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[10], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[11], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[12], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[13], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[14], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[15], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[16], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[17], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[18], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[19], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[20], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[21], 1);
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[22], 2);
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[23], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[24], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[25], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[26], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[27], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[28], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[29], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[30], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[31], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[32], "varchar");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[33], "118.7791123146");
				tableValues.put(DBAdapter.TABLE_PLACE_ACCESS_COL[34], "varchar");
				break;
			case 10:			// Table "TB_RESCUE_INFO"'s data values.
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[1], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[2], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[3], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[4], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[5], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[6], 1);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[7], 2);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[8], 3);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[9], 4);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[10], 5);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[11], 6);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[12], 7);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[13], 8);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[14], 9);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[15], 10);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[16], 11);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[17], 12);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[18], "2014-11-2 11:11:11");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[19], 1);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[20], 2);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[21], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[22], 1);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[23], 2);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[24], 3);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[25], 4);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[26], 5);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[27], 6);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[28], 7);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[29], 8);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[30], 9);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[31], 10);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[32], 11);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[33], 12);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[34], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[35], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_INFO_COL[36], "2014-11-2 11:11:11");
				break;
			case 11:			// Table "TB_RETREAT_APPLY"'s data values.
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[1], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[2], "2014-11-2 11:11:11");
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[3], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[4], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[5], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[6], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[7], "2014-11-2 11:11:11");
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[8], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[9], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[10], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[11], 1);
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[12], 2);
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[13], 3);
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[14], 4);
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[15], 5);
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[16], 6);
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[17], 7);
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[18], 8);
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[19], 9);
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[20], 10);
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[21], 11);
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[22], 12);
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[23], "2014-11-2 11:11:11");
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[24], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[25], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[26], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[27], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[28], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[29], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[30], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[31], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[32], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RETREAT_APPLY_COL[33], "varchar");
				break;
			case 12:			// Table "TB_SAVE_SIT"'s data values.
				tableValues.put(DBAdapter.TABLE_TB_SAVE_SIT_COL[1], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SAVE_SIT_COL[2], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SAVE_SIT_COL[3], "2014-11-2 11:11:11");
				tableValues.put(DBAdapter.TABLE_TB_SAVE_SIT_COL[4], "2014-11-2 11:11:11");
				tableValues.put(DBAdapter.TABLE_TB_SAVE_SIT_COL[5], 1);
				tableValues.put(DBAdapter.TABLE_TB_SAVE_SIT_COL[6], 2);
				tableValues.put(DBAdapter.TABLE_TB_SAVE_SIT_COL[7], 3);
				tableValues.put(DBAdapter.TABLE_TB_SAVE_SIT_COL[8], 4);
				tableValues.put(DBAdapter.TABLE_TB_SAVE_SIT_COL[9], 5);
				tableValues.put(DBAdapter.TABLE_TB_SAVE_SIT_COL[10], 6);
				tableValues.put(DBAdapter.TABLE_TB_SAVE_SIT_COL[11], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SAVE_SIT_COL[12], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SAVE_SIT_COL[13], 1);
				tableValues.put(DBAdapter.TABLE_TB_SAVE_SIT_COL[14], 2);
				tableValues.put(DBAdapter.TABLE_TB_SAVE_SIT_COL[15], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SAVE_SIT_COL[16], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SAVE_SIT_COL[17], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SAVE_SIT_COL[18], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SAVE_SIT_COL[19], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SAVE_SIT_COL[20], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SAVE_SIT_COL[21], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SAVE_SIT_COL[22], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SAVE_SIT_COL[23], "2014-11-2 11:11:11");
				tableValues.put(DBAdapter.TABLE_TB_SAVE_SIT_COL[24], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SAVE_SIT_COL[25], "varchar");
				break;
			case 13:			// Table "TB_RESCUE_SIT"'s data values.
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[1], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[2], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[3], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[4], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[5], 1);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[6], 2);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[7], 3);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[8], 4);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[9], 5);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[10], 6);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[11], 7);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[12], 8);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[13], 9);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[14], 10);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[15], 11);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[16], 12);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[17], 13);
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[18], "2014-11-2 11:11:11");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[19], "2014-11-2 11:11:11");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[20], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[21], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[22], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[23], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[24], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[25], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[26], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[27], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[28], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[29], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[30], "2014-11-2 11:11:11");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[31], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_RESCUE_SIT_COL[32], "varchar");
				break;
			case 14:			// Table "TB_VICTIMS_DEAL"'s data values.
				tableValues.put(DBAdapter.TABLE_TB_VICTIMS_DEAL_COL[1], 1);
				tableValues.put(DBAdapter.TABLE_TB_VICTIMS_DEAL_COL[2], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_VICTIMS_DEAL_COL[3], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_VICTIMS_DEAL_COL[4], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_VICTIMS_DEAL_COL[5], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_VICTIMS_DEAL_COL[6], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_VICTIMS_DEAL_COL[7], "2014-11-2 11:11:11");
				tableValues.put(DBAdapter.TABLE_TB_VICTIMS_DEAL_COL[8], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_VICTIMS_DEAL_COL[9], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_VICTIMS_DEAL_COL[10], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_VICTIMS_DEAL_COL[11], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_VICTIMS_DEAL_COL[12], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_VICTIMS_DEAL_COL[13], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_VICTIMS_DEAL_COL[14], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_VICTIMS_DEAL_COL[15], "varchar");
				break;
			case 15:			// Table "VICTIMS_INFO"'s data values.
				tableValues.put(DBAdapter.TABLE_VICTIMS_INFO_COL[1], 1);
				tableValues.put(DBAdapter.TABLE_VICTIMS_INFO_COL[2], "varchar");
				tableValues.put(DBAdapter.TABLE_VICTIMS_INFO_COL[3], "varchar");
				tableValues.put(DBAdapter.TABLE_VICTIMS_INFO_COL[4], "varchar");
				tableValues.put(DBAdapter.TABLE_VICTIMS_INFO_COL[5], "varchar");
				tableValues.put(DBAdapter.TABLE_VICTIMS_INFO_COL[6], 1);
				tableValues.put(DBAdapter.TABLE_VICTIMS_INFO_COL[7], "varchar");
				tableValues.put(DBAdapter.TABLE_VICTIMS_INFO_COL[8], "varchar");
				tableValues.put(DBAdapter.TABLE_VICTIMS_INFO_COL[9], "varchar");
				tableValues.put(DBAdapter.TABLE_VICTIMS_INFO_COL[10], "varchar");
				tableValues.put(DBAdapter.TABLE_VICTIMS_INFO_COL[11], "varchar");
				tableValues.put(DBAdapter.TABLE_VICTIMS_INFO_COL[12], "varchar");
				tableValues.put(DBAdapter.TABLE_VICTIMS_INFO_COL[13], "varchar");
				tableValues.put(DBAdapter.TABLE_VICTIMS_INFO_COL[14], "2014-11-2 11:11:11");
				tableValues.put(DBAdapter.TABLE_VICTIMS_INFO_COL[15], "varchar");
				tableValues.put(DBAdapter.TABLE_VICTIMS_INFO_COL[16], "varchar");
				break;
			case 16:			// Table "TB_SEARCH_SIT"'s data values.
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[1], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[2], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[3], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[4], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[5], 1);
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[6], 2);
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[7], 3);
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[8], 4);
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[9], 5);
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[10], 6);
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[11], 7);
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[12], 8);
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[13], 9);
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[14], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[15], 1);
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[16], 2);
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[17], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[18], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[19], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[20], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[21], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[22], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[23], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[24], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[25], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[26], "2014-11-2 11:11:11");
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[27], "varchar");
				tableValues.put(DBAdapter.TABLE_TB_SEARCH_SIT_COL[28], "varchar");
				break;
	
			default:
				break;
		}
		
		/*
		 * Insert data values.
		 */
		ContentResolver contentResolver = getContentResolver();
		Uri sUri = Uri.parse(DBAdapter.CONTENT_URI + DBAdapter.ALL_TABLES[tableNameID]);
		Uri uri = contentResolver.insert(sUri , tableValues);
		// delete 12.7 myDBAdapter.insertRow(DBAdapter.ALL_TABLES[tableNameID], tableValues);
	}

	public void onClickDisplayFist(View v)
	{
		displayText(getRecordSet(0));
	}

	public void onClickDisplayNext(View v)
	{
		displayText(getRecordSet(1));
	}

	public void onClickDisplayLast(View v)
	{
		displayText(getRecordSet(-1));
	}
	
	public void onClickClearAll(View v)
	{

	}
	
	// Public parameter for move to next using "getRecordSet".
	public int nextCount = 0;
	/*
	 * flag = 0 -> move to first.
	 * flag < 0 -> move to last.
	 * flag > 0 -> move to next.
	 */
	public String getRecordSet(int flag) 
	{
		// TODO Auto-generated method stub
		String retString = "Nothing";
		String[] items = DBAdapter.list.get(tableNameID); 
		String tableName = DBAdapter.ALL_TABLES[tableNameID];
		
		ContentResolver cResolver = getContentResolver();
		Uri sUri = Uri.parse(DBAdapter.CONTENT_URI + DBAdapter.ALL_TABLES[tableNameID]);
		Cursor myCursor = cResolver.query(sUri, null, null, null, null);
		
		// delete 12.7 Cursor myCursor = myDBAdapter.getAllRows(tableName,items);
		int col_count = myCursor.getColumnCount();
		// For debug.
		Log.i("info","column count:" + col_count);
		
		// Select mode, and move cursor.
		if(flag == 0)		// Move to first item.
		{
			if(!myCursor.moveToFirst())	return retString;
		}
		else if(flag < 0)	// Move to last item.
		{
			if(!myCursor.moveToLast())	return retString;
		}
		else if(flag > 0)	// Move to next item from position (0,1,2,...).
		{
			if(!myCursor.moveToPosition(nextCount++))
			{
				nextCount = 0;
				if(!myCursor.moveToPosition(nextCount++)) return retString;
			}

			// For debug.
			Log.i("info","postion:" + myCursor.getPosition());
		}
		
		// Process table row.
		retString = null;
		for(int i = 0;i < col_count;++i)
		{
			if(i == (col_count -1))
			{
				retString += items[i] + " = " + myCursor.getString(i);
			}
			else
			{
				retString += items[i] + " = " + myCursor.getString(i) + " , ";
			}
		}
		
		return retString;
	}

	private void displayText(String textString) {
		// TODO Auto-generated method stub
		textView.setText(textString);
	}

}