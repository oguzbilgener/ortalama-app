package com.oguzdev.ortalama;

import android.content.*;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SettingsDb extends SQLiteOpenHelper {

	public final static String dbname = "OrtalamaHesapla";
	public final static String dbtable = "settings";
	public final static String dbtable_s = "dersler";
	public final static int dbver = 2;
	public SettingsDb(Context context) {
		super(context,dbname,null,dbver);
	}	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE "+dbtable+"("+
				"_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
				"name TEXT NOT NULL,"+
				"val int NOT NULL"+
				")");	
		db.execSQL("CREATE TABLE "+dbtable_s+" ("+
				"_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
				"val TEXT NOT NULL,"+
				"kat int NOT NULL"+
				")");

		db.execSQL("INSERT INTO "+dbtable+" (name,val) VALUES ('service',0)");

	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP DATABASE IF EXISTS "+dbname);
		onCreate(db);		
	}
	

}
