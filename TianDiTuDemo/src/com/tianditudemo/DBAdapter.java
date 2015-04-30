package com.tianditudemo;

import java.util.ArrayList;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class DBAdapter extends ContentProvider
{
	// TODO: Database name and table name.
	public static final String DATABASE_NAME = "mydb";
	public static final String DATABASE_TABLE = "maintable";
	public static ArrayList<String[]> list = new ArrayList<String[]>();
	public static final String[] ALL_TABLES = new String[]{
				"TB_GPS", 				// To record the people moving track.
				"TB_PIC", 				// To record the locale environment picture.
				"TB_VIDEO", 			// To record the locale video.
				"TB_VOICE",				// To record the locale voice.
				"TB_COMPASS",			// To record the locale compass.
				"TB_LIGHT",				// To record the locale environment light.
				"TB_PPM",				// To record the locale gas values.
				"TB_DEMAGER_EPORT",		// To record the level of locale environment. 
				"TB_MEDICAL_DEAL",		// To record the locale saving information.
				"PLACE_ACCESS",			// To record the level of working environment.
				"TB_RESCUE_INFO",		// To record the information of salvager.
				"TB_RETREAT_APPLY",		// Apply for retreat. 
				"TB_SAVE_SIT",			// To record the information of saved.
				"TB_RESCUE_SIT",		// To record the information of rescue.
				"TB_VICTIMS_DEAL",		// To record how to process the victim.
				"VICTIMS_INFO",			// To record the information of victim.
				"TB_SEARCH_SIT"			// The table for searching about the information of saved.
	}; 
	
	// Table "TB_GPS" columns.
	public static final String[] TABLE_TB_GPS_COL = new String[]{"_id","time","lng","lat"};
	public static final String[] CREATE_TB_GPS_COL = new String[]{
				TABLE_TB_GPS_COL[0]	+ " integer primary key autoincrement,", // Every table all add "_id" for "primary key".
				TABLE_TB_GPS_COL[1] + " datetime not null,",
				TABLE_TB_GPS_COL[2] + " varchar not null,",
				TABLE_TB_GPS_COL[3] + " varchar not null"
	};
	
	// Table "TB_PIC" columns.
	public static final String[] TABLE_TB_PIC_COL = new String[]{"_id","time","place","sd_path","title","des","lng","lat"};
	public static final String[] CREATE_TB_PIC_COL = new String[]{
				TABLE_TB_PIC_COL[0] + " integer primary key autoincrement,", // Every table all add "_id" for "primary key".
				TABLE_TB_PIC_COL[1] + " datetime not null,",
				TABLE_TB_PIC_COL[2] + " varchar not null,",
				TABLE_TB_PIC_COL[3] + " varchar not null,",
				TABLE_TB_PIC_COL[4] + " varchar,",
				TABLE_TB_PIC_COL[5] + " varchar,",
				// Update at 11.2 
				TABLE_TB_PIC_COL[6] + " varchar not null,",
				TABLE_TB_PIC_COL[7] + " varchar not null"
	};
	
	// Table "TB_VIDEO" columns.
	public static final String[] TABLE_TB_VIDEO_COL = new String[]{"_id","time","place","sd_path","title","des","lng","lat"};
	public static final String[] CREATE_TB_VIDEO_COL = new String[]{
				TABLE_TB_VIDEO_COL[0] + " integer primary key autoincrement,", // Every table all add "_id" for "primary key".
				TABLE_TB_VIDEO_COL[1] + " datetime not null,",
				TABLE_TB_VIDEO_COL[2] + " varchar not null,",
				TABLE_TB_VIDEO_COL[3] + " varchar not null,",
				TABLE_TB_VIDEO_COL[4] + " varchar,",
				TABLE_TB_VIDEO_COL[5] + " varchar,",
				// Update at 11.2 
				TABLE_TB_VIDEO_COL[6] + " varchar not null,",
				TABLE_TB_VIDEO_COL[7] + " varchar not null"
	};
	
	// Table "TB_VOICE" columns.
	public static final String[] TABLE_TB_VOICE_COL = new String[]{"_id","time","place","sd_path","title","des","lng","lat"};
	public static final String[] CREATE_TB_VOICE_COL = new String[]{
				TABLE_TB_VOICE_COL[0] + " integer primary key autoincrement,", // Every table all add "_id" for "primary key".
				TABLE_TB_VOICE_COL[1] + " datetime not null,",
				TABLE_TB_VOICE_COL[2] + " varchar not null,",
				TABLE_TB_VOICE_COL[3] + " varchar not null,",
				TABLE_TB_VOICE_COL[4] + " varchar,",
				TABLE_TB_VOICE_COL[5] + " varchar,",
				// Update at 11.2 
				TABLE_TB_VOICE_COL[6] + " varchar not null,",
				TABLE_TB_VOICE_COL[7] + " varchar not null"
	};
	
	// Table "TB_COMPASS" columns.
	public static final String[] TABLE_TB_COMPASS_COL = new String[]{"_id","time","place","value","title","des","lng","lat"};
	public static final String[] CREATE_TB_COMPASS_COL = new String[]{
				TABLE_TB_COMPASS_COL[0] + " integer primary key autoincrement,", // Every table all add "_id" for "primary key".
				TABLE_TB_COMPASS_COL[1] + " datetime not null,",
				TABLE_TB_COMPASS_COL[2] + " varchar not null,",
				TABLE_TB_COMPASS_COL[3] + " varchar not null,",
				TABLE_TB_COMPASS_COL[4] + " varchar,",
				TABLE_TB_COMPASS_COL[5] + " varchar,",
				// Update at 11.2 
				TABLE_TB_COMPASS_COL[6] + " varchar not null,",
				TABLE_TB_COMPASS_COL[7] + " varchar not null"
	};
	
	// Table "TB_LIGHT" columns.
	public static final String[] TABLE_TB_LIGHT_COL = new String[]{"_id","time","place","value","title","des","lng","lat"};
	public static final String[] CREATE_TB_LIGHT_COL = new String[]{
				TABLE_TB_LIGHT_COL[0] + " integer primary key autoincrement,", // Every table all add "_id" for "primary key".
				TABLE_TB_LIGHT_COL[1] + " datetime not null,",
				TABLE_TB_LIGHT_COL[2] + " varchar not null,",
				TABLE_TB_LIGHT_COL[3] + " varchar not null,",
				TABLE_TB_LIGHT_COL[4] + " varchar,",
				TABLE_TB_LIGHT_COL[5] + " varchar,",
				// Update at 11.2 
				TABLE_TB_LIGHT_COL[6] + " varchar not null,",
				TABLE_TB_LIGHT_COL[7] + " varchar not null"
	};
	
	// Table "TB_PPM" columns.
	public static final String[] TABLE_TB_PPM_COL = new String[]{
				"_id",
				"time","place",
				"co_value","co_title","co_des",
				"nh3_value","nh3_title","nh3_des",
				"fo_value","fo_title","fo_des",
				"clo2_value","clo2_title","clo2_des",
				"lng","lat"
	};
	public static final String[] CREATE_TB_PPM_COL = new String[]{
				TABLE_TB_PPM_COL[0] + " integer primary key autoincrement,", // Every table all add "_id" for "primary key".
				TABLE_TB_PPM_COL[1] + " datetime not null,",
				TABLE_TB_PPM_COL[2] + " varchar not null,",
				TABLE_TB_PPM_COL[3] + " varchar not null,",
				TABLE_TB_PPM_COL[4] + " varchar,",
				TABLE_TB_PPM_COL[5] + " varchar,",
				TABLE_TB_PPM_COL[6] + " varchar not null,",
				TABLE_TB_PPM_COL[7] + " varchar,",
				TABLE_TB_PPM_COL[8] + " varchar,",
				TABLE_TB_PPM_COL[9] + " varchar not null,",
				TABLE_TB_PPM_COL[10] + " varchar,",
				TABLE_TB_PPM_COL[11] + " varchar,",
				TABLE_TB_PPM_COL[12] + " varchar not null,",
				TABLE_TB_PPM_COL[13] + " varchar,",
				TABLE_TB_PPM_COL[14] + " varchar,",
				// Update at 11.2 
				TABLE_TB_PPM_COL[15] + " varchar not null,",
				TABLE_TB_PPM_COL[16] + " varchar not null"
	};
	
	// Table "TB_DEMAGER_EPORT" columns.
	public static final String[] TABLE_TB_DEMAGER_EPORT_COL = new String[]{
				"_id",
				"report_time","report_name","report_place",
				"people_num","death_num","hurt_num","lose_num",
				"data_source","medical_help","help_source",
				"trapped_num","trapped_reason","trapped_source",
				"house_lack_num",
				"wild_settle_num","earth_settle_num","victims_settle_num","other_settle_num",
				"weather",
				"house_sum_num","house_break_num","house_hurt_num","house_keep_num",
				"occupancy_specifica",
				"house_sit",
				"settle_source","ifsettle_source",
				"house_specifica",
				"comminute_sit","comminute_explain",
				"traffic_sit","tracffic_source","traffic_explain","traffic_influence",
				"influence_source",
				"energy_sit","energy_explain","energy_source",
				"water_explain","water_sit","water_source",
				"security_sit","security_source",
				"humen_sit","humen_source",
				"other_social","other_influence",
				"lng","lat"
	};
	public static final String[] CREATE_TB_DEMAGER_EPORT_COL = new String[]{
				TABLE_TB_DEMAGER_EPORT_COL[0] + " integer primary key autoincrement,", // Every table all add "_id" for "primary key".
				TABLE_TB_DEMAGER_EPORT_COL[1] + " datatime not null,",
				TABLE_TB_DEMAGER_EPORT_COL[2] + " varchar not null,",
				TABLE_TB_DEMAGER_EPORT_COL[3] + " varchar not null,",
				TABLE_TB_DEMAGER_EPORT_COL[4] + " integer,",
				TABLE_TB_DEMAGER_EPORT_COL[5] + " integer not null,",
				TABLE_TB_DEMAGER_EPORT_COL[6] + " integer not null,",
				TABLE_TB_DEMAGER_EPORT_COL[7] + " integer not null,",
				TABLE_TB_DEMAGER_EPORT_COL[8] + " varchar,",
				TABLE_TB_DEMAGER_EPORT_COL[9] + " varchar not null,",
				TABLE_TB_DEMAGER_EPORT_COL[10] + " varchar not null,",
				TABLE_TB_DEMAGER_EPORT_COL[11] + " integer not null,",
				TABLE_TB_DEMAGER_EPORT_COL[12] + " varchar not null,",
				TABLE_TB_DEMAGER_EPORT_COL[13] + " varchar,",
				TABLE_TB_DEMAGER_EPORT_COL[14] + " varchar not null,",
				TABLE_TB_DEMAGER_EPORT_COL[15] + " varchar not null,",
				TABLE_TB_DEMAGER_EPORT_COL[16] + " varchar,",
				TABLE_TB_DEMAGER_EPORT_COL[17] + " varchar,",
				TABLE_TB_DEMAGER_EPORT_COL[18] + " varchar,",
				TABLE_TB_DEMAGER_EPORT_COL[19] + " varchar,",
				TABLE_TB_DEMAGER_EPORT_COL[20] + " integer not null,",
				TABLE_TB_DEMAGER_EPORT_COL[21] + " integer not null,",
				TABLE_TB_DEMAGER_EPORT_COL[22] + " integer not null,",
				TABLE_TB_DEMAGER_EPORT_COL[23] + " integer,",
				TABLE_TB_DEMAGER_EPORT_COL[24] + " varchar,",
				TABLE_TB_DEMAGER_EPORT_COL[25] + " varchar,",
				TABLE_TB_DEMAGER_EPORT_COL[26] + " varchar,",
				TABLE_TB_DEMAGER_EPORT_COL[27] + " varchar not null,",
				TABLE_TB_DEMAGER_EPORT_COL[28] + " varchar not null,",
				TABLE_TB_DEMAGER_EPORT_COL[29] + " varchar not null,",
				TABLE_TB_DEMAGER_EPORT_COL[30] + " varchar,",
				TABLE_TB_DEMAGER_EPORT_COL[31] + " varchar not null,",
				TABLE_TB_DEMAGER_EPORT_COL[32] + " varchar,",
				TABLE_TB_DEMAGER_EPORT_COL[33] + " varchar,",
				TABLE_TB_DEMAGER_EPORT_COL[34] + " varchar,",
				TABLE_TB_DEMAGER_EPORT_COL[35] + " varchar,",
				TABLE_TB_DEMAGER_EPORT_COL[36] + " varchar not null,",
				TABLE_TB_DEMAGER_EPORT_COL[37] + " varchar,",
				TABLE_TB_DEMAGER_EPORT_COL[38] + " varchar,",
				TABLE_TB_DEMAGER_EPORT_COL[39] + " varchar,",
				TABLE_TB_DEMAGER_EPORT_COL[40] + " varchar not null,",
				TABLE_TB_DEMAGER_EPORT_COL[41] + " varchar,",
				TABLE_TB_DEMAGER_EPORT_COL[42] + " varchar not null,",
				TABLE_TB_DEMAGER_EPORT_COL[43] + " varchar,",
				TABLE_TB_DEMAGER_EPORT_COL[44] + " varchar not null,",
				TABLE_TB_DEMAGER_EPORT_COL[45] + " varchar,",
				TABLE_TB_DEMAGER_EPORT_COL[46] + " varchar,",
				TABLE_TB_DEMAGER_EPORT_COL[47] + " varchar,",
				// Update at 11.2 
				TABLE_TB_DEMAGER_EPORT_COL[48] + " varchar not null,",
				TABLE_TB_DEMAGER_EPORT_COL[49] + " varchar not null"
	};
	
	// Table "TB_MEDICAL_DEAL" columns.
	public static final String[] TABLE_TB_MEDICAL_DEAL_COL = new String[]{
				"_id",
				"index_mdl","team_name_mdl",
				"name_mdl","sex","age_mdl",
				"id_mdl","contact_mdl",
				"atime_mdl","stime_mdl",
				"result_mdl",
				"treatment_mdl","treat_again_mdl",
				"sign_mdl","sign_time_mdl"
	};
	public static final String[] CREATE_TB_MEDICAL_DEAL_COL = new String[]{
				TABLE_TB_MEDICAL_DEAL_COL[0] + " integer primary key autoincrement,", // Every table all add "_id" for "primary key".
				TABLE_TB_MEDICAL_DEAL_COL[1] + " integer not null,",
				TABLE_TB_MEDICAL_DEAL_COL[2] + " varchar not null,",
				TABLE_TB_MEDICAL_DEAL_COL[3] + " varchar not null,",
				TABLE_TB_MEDICAL_DEAL_COL[4] + " varchar not null,",
				TABLE_TB_MEDICAL_DEAL_COL[5] + " integer not null,",
				TABLE_TB_MEDICAL_DEAL_COL[6] + " varchar not null,",
				TABLE_TB_MEDICAL_DEAL_COL[7] + " varchar not null,",
				TABLE_TB_MEDICAL_DEAL_COL[8] + " datetime,",
				TABLE_TB_MEDICAL_DEAL_COL[9] + " datetime not null,",
				TABLE_TB_MEDICAL_DEAL_COL[10] + " varchar not null,",
				TABLE_TB_MEDICAL_DEAL_COL[11] + " varchar not null,",
				TABLE_TB_MEDICAL_DEAL_COL[12] + " varchar not null,",
				TABLE_TB_MEDICAL_DEAL_COL[13] + " varchar not null,",
				TABLE_TB_MEDICAL_DEAL_COL[14] + " datetime not null"
	};
	
	// Table "PLACE_ACCESS" columns.
	public static final String[] TABLE_PLACE_ACCESS_COL = new String[]{
				"_id",
				"sign_time_mdl","rescue_team_name","place_name",
				"gas_out","fire_out","tai_out",
				"slide_out","landslide_out","flow_out","hong_out",
				"other_out","stable_out","influence_out","shuiguan_out",
				"name_info","address_info","plan_info","a_people_info","s_people_info",
				"style_info","under_info","up_info","basic_info",
				"cheng_zhong_info","kong_jian_info",
				"dao_ta_acc","po_huai_acc","dao_ta_again_acc","shi_jiu_acc",
				"person_sug","special_sug","other_sug",
				"lng","lat"
	};
	public static final String[] CREATE_PLACE_ACCESS_COL = new String[]{
				TABLE_PLACE_ACCESS_COL[0] + " integer primary key autoincrement,", // Every table all add "_id" for "primary key".
				TABLE_PLACE_ACCESS_COL[1] + " datatime not null,",
				TABLE_PLACE_ACCESS_COL[2] + " varchar not null,",
				TABLE_PLACE_ACCESS_COL[3] + " varchar not null,",
				TABLE_PLACE_ACCESS_COL[4] + " varchar not null,",
				TABLE_PLACE_ACCESS_COL[5] + " varchar not null,",
				TABLE_PLACE_ACCESS_COL[6] + " varchar not null,",
				TABLE_PLACE_ACCESS_COL[7] + " varchar not null,",
				TABLE_PLACE_ACCESS_COL[8] + " varchar not null,",
				TABLE_PLACE_ACCESS_COL[9] + " varchar not null,",
				TABLE_PLACE_ACCESS_COL[10] + " varchar not null,",
				TABLE_PLACE_ACCESS_COL[11] + " varchar not null,",
				TABLE_PLACE_ACCESS_COL[12] + " varchar not null,",
				TABLE_PLACE_ACCESS_COL[13] + " varchar,",
				TABLE_PLACE_ACCESS_COL[14] + " varchar,",
				TABLE_PLACE_ACCESS_COL[15] + " varchar,",
				TABLE_PLACE_ACCESS_COL[16] + " varchar,",
				TABLE_PLACE_ACCESS_COL[17] + " varchar,",
				TABLE_PLACE_ACCESS_COL[18] + " varchar not null,",
				TABLE_PLACE_ACCESS_COL[19] + " varchar not null,",
				TABLE_PLACE_ACCESS_COL[20] + " varchar,",
				TABLE_PLACE_ACCESS_COL[21] + " integer,",
				TABLE_PLACE_ACCESS_COL[22] + " integer,",
				TABLE_PLACE_ACCESS_COL[23] + " varchar not null,",
				TABLE_PLACE_ACCESS_COL[24] + " varchar,",
				TABLE_PLACE_ACCESS_COL[25] + " varchar,",
				TABLE_PLACE_ACCESS_COL[26] + " varchar,",
				TABLE_PLACE_ACCESS_COL[27] + " varchar not null,",
				TABLE_PLACE_ACCESS_COL[28] + " varchar not null,",
				TABLE_PLACE_ACCESS_COL[29] + " varchar,",
				TABLE_PLACE_ACCESS_COL[30] + " varchar,",
				TABLE_PLACE_ACCESS_COL[31] + " varchar,",
				TABLE_PLACE_ACCESS_COL[32] + " varchar,",
				// Update at 11.2 
				TABLE_PLACE_ACCESS_COL[33] + " varchar not null,",
				TABLE_PLACE_ACCESS_COL[34] + " varchar not null"
	};
	
	// Table "TB_RESCUE_INFO" columns.
	public static final String[] TABLE_TB_RESCUE_INFO_COL = new String[]{
				"_id",
				"team_name","contact_name","contact_phone",
				"watch_phone","radio_fqy","sum_num",
				"manage_num","struct_num","search_num",
				"medical_num","danger_num","class_num",
				"comminute_num","equip_num","dog_num",
				"bus_num","light_num","day_num",
				"machine_num","clothing_num",
				"other","guide_num","transfer_bus_num",
				"people_num","map_num","energy_num","water_num",
				"gas_num","diesel_num","medical_o2_num",
				"crane_num","fork_lift_num","bulldozer_num",
				"command_people","rescue_people","time"
	};
	public static final String[] CREATE_TB_RESCUE_INFO_COL = new String[]{
				TABLE_TB_RESCUE_INFO_COL[0] + " integer primary key autoincrement,", // Every table all add "_id" for "primary key".
				TABLE_TB_RESCUE_INFO_COL[1] + " varchar not null,",
				TABLE_TB_RESCUE_INFO_COL[2] + " varchar not null,",
				TABLE_TB_RESCUE_INFO_COL[3] + " varchar not null,",
				TABLE_TB_RESCUE_INFO_COL[4] + " varchar not null,",
				TABLE_TB_RESCUE_INFO_COL[5] + " varchar not null,",
				TABLE_TB_RESCUE_INFO_COL[6] + " integer not null,",
				TABLE_TB_RESCUE_INFO_COL[7] + " integer not null,",
				TABLE_TB_RESCUE_INFO_COL[8] + " integer not null,",
				TABLE_TB_RESCUE_INFO_COL[9] + " integer not null,",
				TABLE_TB_RESCUE_INFO_COL[10] + " integer not null,",
				TABLE_TB_RESCUE_INFO_COL[11] + " integer not null,",
				TABLE_TB_RESCUE_INFO_COL[12] + " integer not null,",
				TABLE_TB_RESCUE_INFO_COL[13] + " integer not null,",
				TABLE_TB_RESCUE_INFO_COL[14] + " integer not null,",
				TABLE_TB_RESCUE_INFO_COL[15] + " integer not null,",
				TABLE_TB_RESCUE_INFO_COL[16] + " integer not null,",
				TABLE_TB_RESCUE_INFO_COL[17] + " integer not null,",
				TABLE_TB_RESCUE_INFO_COL[18] + " datetime not null,",
				TABLE_TB_RESCUE_INFO_COL[19] + " integer not null,",
				TABLE_TB_RESCUE_INFO_COL[20] + " integer not null,",
				TABLE_TB_RESCUE_INFO_COL[21] + " varchar,",
				TABLE_TB_RESCUE_INFO_COL[22] + " integer not null,",
				TABLE_TB_RESCUE_INFO_COL[23] + " integer,",
				TABLE_TB_RESCUE_INFO_COL[24] + " integer not null,",
				TABLE_TB_RESCUE_INFO_COL[25] + " integer not null,",
				TABLE_TB_RESCUE_INFO_COL[26] + " integer not null,",
				TABLE_TB_RESCUE_INFO_COL[27] + " integer not null,",
				TABLE_TB_RESCUE_INFO_COL[28] + " integer not null,",
				TABLE_TB_RESCUE_INFO_COL[29] + " integer not null,",
				TABLE_TB_RESCUE_INFO_COL[30] + " integer not null,",
				TABLE_TB_RESCUE_INFO_COL[31] + " integer not null,",
				TABLE_TB_RESCUE_INFO_COL[32] + " integer not null,",
				TABLE_TB_RESCUE_INFO_COL[33] + " integer not null,",
				TABLE_TB_RESCUE_INFO_COL[34] + " varchar not null,",
				TABLE_TB_RESCUE_INFO_COL[35] + " varchar not null,",
				TABLE_TB_RESCUE_INFO_COL[36] + " datetime not null"
	};
	
	// Table "TB_RETREAT_APPLY" columns.
	public static final String[] TABLE_TB_RETREAT_APPLY_COL = new String[]{
				"_id",
				"team_apply","ctime_apply","name_cont",
				"telephone_cont","phone_cont","radio_cont","atime_apply",
				"duty_apply","home_apply","reason_apply",
				"sea_place_1","sea_place_2","sea_place_3",
				"save_place_1","save_place_2","save_place_3",
				"move_place_1","move_place_2","move_place_3",
				"mdl_place_1","mdl_place_2","mdl_place_3",
				"long_apply","command_apply",
				"place_1","place_2","place_3",
				"place_lng_1","place_lng_2","place_lng_3",
				"place_lat_1","place_lat_2","place_lat_3"
	};
	public static final String[] CREATE_TB_RETREAT_APPLY_COL = new String[]{
				TABLE_TB_RETREAT_APPLY_COL[0] + " integer primary key autoincrement,", // Every table all add "_id" for "primary key".
				TABLE_TB_RETREAT_APPLY_COL[1] + " varchar not null,",
				TABLE_TB_RETREAT_APPLY_COL[2] + " datetime not null,",
				TABLE_TB_RETREAT_APPLY_COL[3] + " varchar not null,",
				TABLE_TB_RETREAT_APPLY_COL[4] + " varchar not null,",
				TABLE_TB_RETREAT_APPLY_COL[5] + " varchar not null,",
				TABLE_TB_RETREAT_APPLY_COL[6] + " varchar not null,",
				TABLE_TB_RETREAT_APPLY_COL[7] + " datetime not null,",
				TABLE_TB_RETREAT_APPLY_COL[8] + " varchar not null,",
				TABLE_TB_RETREAT_APPLY_COL[9] + " varchar not null,",
				TABLE_TB_RETREAT_APPLY_COL[10] + " varchar not null,",
				TABLE_TB_RETREAT_APPLY_COL[11] + " integer not null,",
				TABLE_TB_RETREAT_APPLY_COL[12] + " integer,",
				TABLE_TB_RETREAT_APPLY_COL[13] + " integer,",
				TABLE_TB_RETREAT_APPLY_COL[14] + " integer not null,",
				TABLE_TB_RETREAT_APPLY_COL[15] + " integer,",
				TABLE_TB_RETREAT_APPLY_COL[16] + " integer,",
				TABLE_TB_RETREAT_APPLY_COL[17] + " integer not null,",
				TABLE_TB_RETREAT_APPLY_COL[18] + " integer,",
				TABLE_TB_RETREAT_APPLY_COL[19] + " integer,",
				TABLE_TB_RETREAT_APPLY_COL[20] + " integer not null,",
				TABLE_TB_RETREAT_APPLY_COL[21] + " integer,",
				TABLE_TB_RETREAT_APPLY_COL[22] + " integer,",
				TABLE_TB_RETREAT_APPLY_COL[23] + " datetime not null,",
				TABLE_TB_RETREAT_APPLY_COL[24] + " varchar not null,",
				// Update at 11.2 
				TABLE_TB_RETREAT_APPLY_COL[25] + " varchar,",
				TABLE_TB_RETREAT_APPLY_COL[26] + " varchar,",
				TABLE_TB_RETREAT_APPLY_COL[27] + " varchar,",
				TABLE_TB_RETREAT_APPLY_COL[28] + " varchar not null,",
				TABLE_TB_RETREAT_APPLY_COL[29] + " varchar not null,",
				TABLE_TB_RETREAT_APPLY_COL[30] + " varchar not null,",
				TABLE_TB_RETREAT_APPLY_COL[31] + " varchar not null,",
				TABLE_TB_RETREAT_APPLY_COL[32] + " varchar not null,",
				TABLE_TB_RETREAT_APPLY_COL[33] + " varchar not null"
	};
	
	// Table "TB_SAVE_SI" columns.
	public static final String[] TABLE_TB_SAVE_SIT_COL = new String[]{
				"_id",
				"team_info_name","place_info_name",
				"start_time","stop_time",
				"people_sea","equip_sea","dog_sea","zong_sea","other_sea",
				"num_id","place_id",
				"place_ex_plain",
				"death_pn","money_pn",
				"sea_mark","high_mark",
				"resue_plan","equip_plan","special_plan","other_plan",
				"command_save","write_save","tb_time_save",
				"lng_id","lat_id"
	};
	public static final String[] CREATE_TB_SAVE_SIT_COL = new String[]{
				TABLE_TB_SAVE_SIT_COL[0] + " integer primary key autoincrement,", // Every table all add "_id" for "primary key".
				TABLE_TB_SAVE_SIT_COL[1] + " varchar not null,",
				TABLE_TB_SAVE_SIT_COL[2] + " varchar not null,",
				TABLE_TB_SAVE_SIT_COL[3] + " datetime not null,",
				TABLE_TB_SAVE_SIT_COL[4] + " datetime not null,",
				TABLE_TB_SAVE_SIT_COL[5] + " integer not null,",
				TABLE_TB_SAVE_SIT_COL[6] + " integer not null,",
				TABLE_TB_SAVE_SIT_COL[7] + " integer not null,",
				TABLE_TB_SAVE_SIT_COL[8] + " integer not null,",
				TABLE_TB_SAVE_SIT_COL[9] + " integer not null,",
				TABLE_TB_SAVE_SIT_COL[10] + " integer not null,",
				TABLE_TB_SAVE_SIT_COL[11] + " varchar not null,",
				TABLE_TB_SAVE_SIT_COL[12] + " varchar,",
				TABLE_TB_SAVE_SIT_COL[13] + " integer not null,",
				TABLE_TB_SAVE_SIT_COL[14] + " integer not null,",
				TABLE_TB_SAVE_SIT_COL[15] + " varchar,",
				TABLE_TB_SAVE_SIT_COL[16] + " varchar,",
				TABLE_TB_SAVE_SIT_COL[17] + " varchar,",
				TABLE_TB_SAVE_SIT_COL[18] + " varchar not null,",
				TABLE_TB_SAVE_SIT_COL[19] + " varchar,",
				TABLE_TB_SAVE_SIT_COL[20] + " varchar,",
				TABLE_TB_SAVE_SIT_COL[21] + " varchar not null,",
				TABLE_TB_SAVE_SIT_COL[22] + " varchar not null,",
				TABLE_TB_SAVE_SIT_COL[23] + " datetime not null,",
				// Update at 11.2 
				TABLE_TB_SAVE_SIT_COL[24] + " varchar not null,",
				TABLE_TB_SAVE_SIT_COL[25] + " varchar not null"
	};
	
	// Table "TB_RESCUE_SIT" columns.
	public static final String[] TABLE_TB_RESCUE_SIT_COL = new String[]{
				"_id",
				"team_name_resc","place_name_resc","start_time_resc","stop_time_resc",
				"command_pn","search_pn","zhuan_jia_pn",
				"medical_pn","protect_pn","light_pn",
				"machine_pn","break_pn","ding_pn",
				"zhi_pn","string_pn","move_pn",
				"other_pn","class_pn","team_pn",
				"security_pn",
				"plan_pro","road_pro","trapped_pro",
				"medical_pro","move_pro","special_pro",
				"action_pro","command_pn_pro",
				"write_pn_pro","time_pro",
				"place_lng","place_lat"
	};
	public static final String[] CREATE_TB_RESCUE_SIT_COL = new String[]{
				TABLE_TB_RESCUE_SIT_COL[0] + " integer primary key autoincrement,", // Every table all add "_id" for "primary key".
				TABLE_TB_RESCUE_SIT_COL[1] + " varchar not null,",
				TABLE_TB_RESCUE_SIT_COL[2] + " varchar not null,",
				TABLE_TB_RESCUE_SIT_COL[3] + " varchar not null,",
				TABLE_TB_RESCUE_SIT_COL[4] + " varchar not null,",
				TABLE_TB_RESCUE_SIT_COL[5] + " integer not null,",
				TABLE_TB_RESCUE_SIT_COL[6] + " integer not null,",
				TABLE_TB_RESCUE_SIT_COL[7] + " integer not null,",
				TABLE_TB_RESCUE_SIT_COL[8] + " integer not null,",
				TABLE_TB_RESCUE_SIT_COL[9] + " integer not null,",
				TABLE_TB_RESCUE_SIT_COL[10] + " integer not null,",
				TABLE_TB_RESCUE_SIT_COL[11] + " integer not null,",
				TABLE_TB_RESCUE_SIT_COL[12] + " integer not null,",
				TABLE_TB_RESCUE_SIT_COL[13] + " integer not null,",
				TABLE_TB_RESCUE_SIT_COL[14] + " integer not null,",
				TABLE_TB_RESCUE_SIT_COL[15] + " integer not null,",
				TABLE_TB_RESCUE_SIT_COL[16] + " integer not null,",
				TABLE_TB_RESCUE_SIT_COL[17] + " integer not null,",
				TABLE_TB_RESCUE_SIT_COL[18] + " datetime not null,",
				TABLE_TB_RESCUE_SIT_COL[19] + " datetime not null,",
				TABLE_TB_RESCUE_SIT_COL[20] + " varchar,",
				TABLE_TB_RESCUE_SIT_COL[21] + " varchar,",
				TABLE_TB_RESCUE_SIT_COL[22] + " varchar,",
				TABLE_TB_RESCUE_SIT_COL[23] + " varchar not null,",
				TABLE_TB_RESCUE_SIT_COL[24] + " varchar not null,",
				TABLE_TB_RESCUE_SIT_COL[25] + " varchar not null,",
				TABLE_TB_RESCUE_SIT_COL[26] + " varchar,",
				TABLE_TB_RESCUE_SIT_COL[27] + " varchar,",
				TABLE_TB_RESCUE_SIT_COL[28] + " varchar not null,",
				TABLE_TB_RESCUE_SIT_COL[29] + " varchar not null,",
				TABLE_TB_RESCUE_SIT_COL[30] + " datetime not null,",
				// Update at 11.2 
				TABLE_TB_RESCUE_SIT_COL[31] + " varchar not null,",
				TABLE_TB_RESCUE_SIT_COL[32] + " varchar not null"
	};
	
	// Table "TB_VICTIMS_DEAL" columns.
	public static final String[] TABLE_TB_VICTIMS_DEAL_COL = new String[]{
				"_id",
				"idcard",
				"team_name","place_name",
				"name","sex","age",
				"time","limit_time",
				"health","move",
				"receive","command","write",
				"place_lng","place_lat"
	};
	public static final String[] CREATE_TB_VICTIMS_DEAL_COL = new String[]{
				TABLE_TB_VICTIMS_DEAL_COL[0] + " integer primary key autoincrement,", // Every table all add "_id" for "primary key".
				TABLE_TB_VICTIMS_DEAL_COL[1] + " integer not null,",
				TABLE_TB_VICTIMS_DEAL_COL[2] + " varchar not null,",
				TABLE_TB_VICTIMS_DEAL_COL[3] + " varchar not null,",
				TABLE_TB_VICTIMS_DEAL_COL[4] + " varchar not null,",
				TABLE_TB_VICTIMS_DEAL_COL[5] + " varchar not null,",
				TABLE_TB_VICTIMS_DEAL_COL[6] + " varchar not null,",
				TABLE_TB_VICTIMS_DEAL_COL[7] + " datetime not null,",
				TABLE_TB_VICTIMS_DEAL_COL[8] + " varchar not null,",
				TABLE_TB_VICTIMS_DEAL_COL[9] + " varchar not null,",
				TABLE_TB_VICTIMS_DEAL_COL[10] + " varchar,",
				TABLE_TB_VICTIMS_DEAL_COL[11] + " varchar not null,",
				TABLE_TB_VICTIMS_DEAL_COL[12] + " varchar not null,",
				TABLE_TB_VICTIMS_DEAL_COL[13] + " varchar not null,",
				// Update at 11.2 
				TABLE_TB_VICTIMS_DEAL_COL[14] + " varchar not null,",
				TABLE_TB_VICTIMS_DEAL_COL[15] + " varchar not null"
	};
	
	// Table "VICTIMS_INFO" columns.
	public static final String[] TABLE_VICTIMS_INFO_COL = new String[]{
				"_id",
				"idcard",
				"team_name","place_name",
				"name","sex","age",
				"time","limit_time",
				"health","move",
				"receive","command","write",
				"tb_time",
				"place_lng","place_lat"
	};
	public static final String[] CREATE_VICTIMS_INFO_COL = new String[]{
				TABLE_VICTIMS_INFO_COL[0] + " integer primary key autoincrement,", // Every table all add "_id" for "primary key".
				TABLE_VICTIMS_INFO_COL[1] + " integer not null,",
				TABLE_VICTIMS_INFO_COL[2] + " varchar not null,",
				TABLE_VICTIMS_INFO_COL[3] + " varchar not null,",
				TABLE_VICTIMS_INFO_COL[4] + " varchar not null,",
				TABLE_VICTIMS_INFO_COL[5] + " varchar not null,",
				TABLE_VICTIMS_INFO_COL[6] + " integer not null,",
				TABLE_VICTIMS_INFO_COL[7] + " varchar not null,",
				TABLE_VICTIMS_INFO_COL[8] + " varchar,",
				TABLE_VICTIMS_INFO_COL[9] + " varchar not null,",
				TABLE_VICTIMS_INFO_COL[10] + " varchar,",
				TABLE_VICTIMS_INFO_COL[11] + " varchar not null,",
				TABLE_VICTIMS_INFO_COL[12] + " varchar not null,",
				TABLE_VICTIMS_INFO_COL[13] + " varchar not null,",
				TABLE_VICTIMS_INFO_COL[14] + " datetime not null,",
				// Update at 11.2 
				TABLE_VICTIMS_INFO_COL[15] + " varchar not null,",
				TABLE_VICTIMS_INFO_COL[16] + " varchar not null"
	};
	
	// Table "TB_SEARCH_SIT" columns.
	public static final String[] TABLE_TB_SEARCH_SIT_COL = new String[]{
				"_id",
				"team_name_sear","place_name_sear","start_time_sear",
				"stop_time_sear","people_sear","equip_sear_num","dog_sear",
				"zong_sear","other_sear",
				"trapped_num","surface_trapped_num",
				"shallow_trapped_num","deep_trapped_num",
				"trapped_des",
				"victims_num","property_num",
				"other_sear_result",
				"search_tag","obvious_markers",
				"rescue_sear","equip_sear","special_sear","other_action_sear",
				"command_pn_pro","write_pn_pro","time_pro",
				"place_lng","place_lat"
	};
	public static final String[] CREATE_TB_SEARCH_SIT_COL = new String[]{
				TABLE_TB_SEARCH_SIT_COL[0] + " integer primary key autoincrement,", // Every table all add "_id" for "primary key".
				TABLE_TB_SEARCH_SIT_COL[1] + " varchar not null,",
				TABLE_TB_SEARCH_SIT_COL[2] + " varchar not null,",
				TABLE_TB_SEARCH_SIT_COL[3] + " varchar not null,",
				TABLE_TB_SEARCH_SIT_COL[4] + " varchar not null,",
				TABLE_TB_SEARCH_SIT_COL[5] + " integer not null,",
				TABLE_TB_SEARCH_SIT_COL[6] + " integer not null,",
				TABLE_TB_SEARCH_SIT_COL[7] + " integer not null,",
				TABLE_TB_SEARCH_SIT_COL[8] + " integer not null,",
				TABLE_TB_SEARCH_SIT_COL[9] + " integer not null,",
				TABLE_TB_SEARCH_SIT_COL[10] + " integer not null,",
				TABLE_TB_SEARCH_SIT_COL[11] + " integer not null,",
				TABLE_TB_SEARCH_SIT_COL[12] + " integer not null,",
				TABLE_TB_SEARCH_SIT_COL[13] + " integer not null,",
				TABLE_TB_SEARCH_SIT_COL[14] + " varchar,",
				TABLE_TB_SEARCH_SIT_COL[15] + " integer not null,",
				TABLE_TB_SEARCH_SIT_COL[16] + " integer,",
				TABLE_TB_SEARCH_SIT_COL[17] + " varchar,",
				TABLE_TB_SEARCH_SIT_COL[18] + " varchar,",
				TABLE_TB_SEARCH_SIT_COL[19] + " varchar,",
				TABLE_TB_SEARCH_SIT_COL[20] + " varchar,",
				TABLE_TB_SEARCH_SIT_COL[21] + " varchar not null,",
				TABLE_TB_SEARCH_SIT_COL[22] + " varchar,",
				TABLE_TB_SEARCH_SIT_COL[23] + " varchar,",
				TABLE_TB_SEARCH_SIT_COL[24] + " varchar not null,",
				TABLE_TB_SEARCH_SIT_COL[25] + " varchar not null,",
				TABLE_TB_SEARCH_SIT_COL[26] + " datetime not null,",
				// Update at 11.2 
				TABLE_TB_SEARCH_SIT_COL[27] + " varchar not null,",
				TABLE_TB_SEARCH_SIT_COL[28] + " varchar not null"
	};
	// Track DB version if a new version of you app changes the format.
	public static final int DATABASE_VERSION = 2;
	
	// DB fields
	public static final String KEY_ROWID = "_id";
	public static final int COL_ROWID = 0;
	
	// TODO: Setup your fields here:
	public static final String KEY_ADVISE = "advise";
	
	// TODO: Setup your fields numbers here(0 = KEY_ADVISE, 1 = ...)
	public static final int COL_ADVISE = 1;
	
	public static final String[] ALL_KEYS = new String[]{KEY_ROWID,KEY_ADVISE};
	
	// TODO: Setup all creating SQL sentence.
	public static final String[] ALL_CREATE_TABLE_SENT = new String[]{
				join(ALL_TABLES[0],  CREATE_TB_GPS_COL),
				join(ALL_TABLES[1],  CREATE_TB_PIC_COL),
				join(ALL_TABLES[2],  CREATE_TB_VIDEO_COL),
				join(ALL_TABLES[3],  CREATE_TB_VOICE_COL),
				join(ALL_TABLES[4],  CREATE_TB_COMPASS_COL),
				join(ALL_TABLES[5],  CREATE_TB_LIGHT_COL),
				join(ALL_TABLES[6],  CREATE_TB_PPM_COL),
				join(ALL_TABLES[7],  CREATE_TB_DEMAGER_EPORT_COL),
				join(ALL_TABLES[8],  CREATE_TB_MEDICAL_DEAL_COL),
				join(ALL_TABLES[9],  CREATE_PLACE_ACCESS_COL),
				join(ALL_TABLES[10], CREATE_TB_RESCUE_INFO_COL),
				join(ALL_TABLES[11], CREATE_TB_RETREAT_APPLY_COL),
				join(ALL_TABLES[12], CREATE_TB_SAVE_SIT_COL),
				join(ALL_TABLES[13], CREATE_TB_RESCUE_SIT_COL),
				join(ALL_TABLES[14], CREATE_TB_VICTIMS_DEAL_COL),
				join(ALL_TABLES[15], CREATE_VICTIMS_INFO_COL),
				join(ALL_TABLES[16], CREATE_TB_SEARCH_SIT_COL)
	};

	// This is the sentence for create table.
	private static final String DATABASE_CREATE_SQL = 
			"create table " + DATABASE_TABLE + " ("
	
			+ KEY_ROWID + " integer primary key autoincrement, "
			// TODO: Add items to there
			+ KEY_ADVISE + " text not null"
			
			+ ");";

	// Context of application who uses us.   delete 12.7
	//private final Context context;
	
	private DatabaseHelper myDBHelper;
	
	public static final Uri CONTENT_URI = Uri.parse("content://com.tianditudemo/");
	
	private static final UriMatcher sUriMather = new UriMatcher(UriMatcher.NO_MATCH);
	static
	{
		sUriMather.addURI("com.tianditudemo", ALL_TABLES[0], 0);
		sUriMather.addURI("com.tianditudemo", ALL_TABLES[1], 1);
		sUriMather.addURI("com.tianditudemo", ALL_TABLES[2], 2);
		sUriMather.addURI("com.tianditudemo", ALL_TABLES[3], 3);
		sUriMather.addURI("com.tianditudemo", ALL_TABLES[4], 4);
		sUriMather.addURI("com.tianditudemo", ALL_TABLES[5], 5);
		sUriMather.addURI("com.tianditudemo", ALL_TABLES[6], 6);
		sUriMather.addURI("com.tianditudemo", ALL_TABLES[7], 7);
		sUriMather.addURI("com.tianditudemo", ALL_TABLES[8], 8);
		sUriMather.addURI("com.tianditudemo", ALL_TABLES[9], 9);
		sUriMather.addURI("com.tianditudemo", ALL_TABLES[10], 10);
		sUriMather.addURI("com.tianditudemo", ALL_TABLES[11], 11);
		sUriMather.addURI("com.tianditudemo", ALL_TABLES[12], 12);
		sUriMather.addURI("com.tianditudemo", ALL_TABLES[13], 13);
		sUriMather.addURI("com.tianditudemo", ALL_TABLES[14], 14);
		sUriMather.addURI("com.tianditudemo", ALL_TABLES[15], 15);
		sUriMather.addURI("com.tianditudemo", ALL_TABLES[16], 16);
	}
	
	/*
	 * There is public methods beginning.
	 */
	
	// Join the creating table string.
	private static String join(String tableName,String[] tableString)
	{
		String retString = "create table if not exists " + tableName + " (";
		int count = tableString.length;
		int i = 0;
		
		for(i = 0;i < count;++i)
		{
			retString += tableString[i];
		}
		retString += ");";
		
		return retString;
	}
	/* delete 12.7
	// Initialization
	public DBAdapter(Context ctx) 
	{
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}
	
	// Open the database connection.
	public DBAdapter open()
	{
		db = myDBHelper.getWritableDatabase();
		return this;
	} 
	
	// Close the database connection.
	public void close()
	{
		myDBHelper.close();
	}
	*/
	//==============================================
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		switch (sUriMather.match(uri)) {  
        case 0:  
            return "vnd.android.cursor.dir/" + ALL_TABLES[0];
        case 1:  
            return "vnd.android.cursor.dir/" + ALL_TABLES[1];
        case 2:  
            return "vnd.android.cursor.dir/" + ALL_TABLES[2];
        case 3:  
            return "vnd.android.cursor.dir/" + ALL_TABLES[3];
        case 4:  
            return "vnd.android.cursor.dir/" + ALL_TABLES[4];
        case 5:  
            return "vnd.android.cursor.dir/" + ALL_TABLES[5];
        case 6:  
            return "vnd.android.cursor.dir/" + ALL_TABLES[6];
        case 7:  
            return "vnd.android.cursor.dir/" + ALL_TABLES[7];
        case 8:  
            return "vnd.android.cursor.dir/" + ALL_TABLES[8];
        case 9:  
            return "vnd.android.cursor.dir/" + ALL_TABLES[9];
        case 10:  
            return "vnd.android.cursor.dir/" + ALL_TABLES[10];
        case 11:  
            return "vnd.android.cursor.dir/" + ALL_TABLES[11];
        case 12:  
            return "vnd.android.cursor.dir/" + ALL_TABLES[12];
        case 13:  
            return "vnd.android.cursor.dir/" + ALL_TABLES[13];
        case 14:  
            return "vnd.android.cursor.dir/" + ALL_TABLES[14];
        case 15:  
            return "vnd.android.cursor.dir/" + ALL_TABLES[15];
        case 16:  
            return "vnd.android.cursor.dir/" + ALL_TABLES[16];
        default:  
            throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());  
        }  
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		long rowid;
		int tableid;
		SQLiteDatabase db = myDBHelper.getReadableDatabase();
		switch ((tableid = sUriMather.match(uri))) {
		case 0:case 1:case 2:case 3:case 4:case 5:case 6:case 7:case 8:case 9:
		case 10:case 11:case 12:case 13:case 14:case 15:case 16:
			rowid = db.insert(ALL_TABLES[tableid], null, values);
			break;	
		default:
			throw new IllegalArgumentException("Unknown Uri:" + uri.toString());
		}			
		Uri insertUri = ContentUris.withAppendedId(uri, rowid);
		this.getContext().getContentResolver().notifyChange(uri, null);
		return(insertUri);
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		myDBHelper = new DatabaseHelper(this.getContext());
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
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		int tableid;
		SQLiteDatabase db = myDBHelper.getReadableDatabase();
		switch ((tableid = sUriMather.match(uri))) {
		case 0:case 1:case 2:case 3:case 4:case 5:case 6:case 7:case 8:case 9:
		case 10:case 11:case 12:case 13:case 14:case 15:case 16:
			String[] items = list.get(tableid);
			return(db.query(ALL_TABLES[tableid], items, null, null, null, null, null));
		default:
			return(null);
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//=====================================================================
	/* delete 12.7
	// Add a new set of values to the database.
	public long insertRow(String advise)
	{
		ContentValues initialValues = new ContentValues();
		// TODO: Create row's data:
		initialValues.put(KEY_ADVISE, advise);
		
		// Insert it into the database ,and return the back values.
		return db.insert(DATABASE_TABLE, null, initialValues);
	}
	// Override the function of "insertRow" in order to apply for all tables.
	public long insertRow(String tableName,ContentValues cValues)
	{
		return db.insert(tableName, null, cValues);
	}
	
	// Delete a row from the database,by rowID(primary key).
	public boolean deleteRow(long rowID)
	{
		String where = KEY_ROWID + "=" + rowID;
		return db.delete(DATABASE_TABLE, where, null) > 0;
	}
	
	// Delete the all data from the "test table".
	public void deleteAll()
	{
		Cursor c = getAllRows(DATABASE_TABLE,ALL_KEYS);
		long rowID = c.getColumnIndexOrThrow(KEY_ROWID);
		if(c.moveToFirst())
		{
			do 
			{
				deleteRow(c.getLong((int)rowID));
			} while (c.moveToNext());
		}
		c.close();
	}
	// Override the function of "deleteAll" in order to apply for all tables.
	public void deleteAll(String tableName,String[] items)
	{
		Cursor c = getAllRows(tableName,items);
		long rowID = c.getColumnIndexOrThrow(KEY_ROWID);
		if(c.moveToFirst())
		{
			do 
			{
				deleteRow(c.getLong((int)rowID));
			} while (c.moveToNext());
		}
		c.close();
	}
	
	// Return all data from the database.
	public Cursor getAllRows(String tableName,String[] items) {
		// TODO Auto-generated method stub
		String where = null;
		Cursor c = db.query(true, tableName, items, where, null, null, null, null, null);
		if(c != null)
		{
			c.moveToFirst();
		}
		return c;
	}

	// Change an existing row to be equal to new data.
	public boolean updateRow(long rowID,String advise)
	{
		String where = KEY_ROWID + "=" + rowID;
		ContentValues newValues = new ContentValues(); 
		// Create row's data.
		newValues.put(KEY_ADVISE, advise);
		
		// Insert it into the database.
		return db.update(DATABASE_TABLE, newValues, where, null) > 0;
	}
*/	

	// TODO: SQLiteOpenHelper to access the real database (Internal class).
	private static class DatabaseHelper extends SQLiteOpenHelper
	{

		DatabaseHelper(Context context) 
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase _db) 
		{
			// TODO Auto-generated method stub
			// This is test table.
			_db.execSQL(DATABASE_CREATE_SQL);
			
			// Create formal table.
			_db.execSQL(ALL_CREATE_TABLE_SENT[0]);
			_db.execSQL(ALL_CREATE_TABLE_SENT[1]);
			_db.execSQL(ALL_CREATE_TABLE_SENT[2]);
			_db.execSQL(ALL_CREATE_TABLE_SENT[3]);
			_db.execSQL(ALL_CREATE_TABLE_SENT[4]);
			_db.execSQL(ALL_CREATE_TABLE_SENT[5]);
			_db.execSQL(ALL_CREATE_TABLE_SENT[6]);
			_db.execSQL(ALL_CREATE_TABLE_SENT[7]);
			_db.execSQL(ALL_CREATE_TABLE_SENT[8]);
			_db.execSQL(ALL_CREATE_TABLE_SENT[9]);
			_db.execSQL(ALL_CREATE_TABLE_SENT[10]);
			_db.execSQL(ALL_CREATE_TABLE_SENT[11]);
			_db.execSQL(ALL_CREATE_TABLE_SENT[12]);
			_db.execSQL(ALL_CREATE_TABLE_SENT[13]);
			_db.execSQL(ALL_CREATE_TABLE_SENT[14]);
			_db.execSQL(ALL_CREATE_TABLE_SENT[15]);
			_db.execSQL(ALL_CREATE_TABLE_SENT[16]);
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) 
		{
			// TODO Auto-generated method stub
			_db.execSQL("drop table if exists " + DATABASE_TABLE);
			_db.execSQL("drop table if exists " + ALL_TABLES[0]);
			_db.execSQL("drop table if exists " + ALL_TABLES[1]);
			_db.execSQL("drop table if exists " + ALL_TABLES[2]);
			_db.execSQL("drop table if exists " + ALL_TABLES[3]);
			_db.execSQL("drop table if exists " + ALL_TABLES[4]);
			_db.execSQL("drop table if exists " + ALL_TABLES[5]);
			_db.execSQL("drop table if exists " + ALL_TABLES[6]);
			_db.execSQL("drop table if exists " + ALL_TABLES[7]);
			_db.execSQL("drop table if exists " + ALL_TABLES[8]);
			_db.execSQL("drop table if exists " + ALL_TABLES[9]);
			_db.execSQL("drop table if exists " + ALL_TABLES[10]);
			_db.execSQL("drop table if exists " + ALL_TABLES[11]);
			_db.execSQL("drop table if exists " + ALL_TABLES[12]);
			_db.execSQL("drop table if exists " + ALL_TABLES[13]);
			_db.execSQL("drop table if exists " + ALL_TABLES[14]);
			_db.execSQL("drop table if exists " + ALL_TABLES[15]);
			_db.execSQL("drop table if exists " + ALL_TABLES[16]);
			onCreate(_db);
		}
	}
}

