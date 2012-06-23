package com.oguzdev.ortalama;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.database.sqlite.*;

public class SettingsSource {
	
	private Context context;
	private SQLiteDatabase db;
	private SettingsDb dbh;
	private static final String TAG = "not_og";
	
	public SettingsSource(Context context) throws SQLException
	{
		this.context = context;
		dbh = new SettingsDb(this.context);
		db = dbh.getWritableDatabase();
	}
	public void close() {
		dbh.close();
	}
	public void giveperm() {
		db = dbh.getWritableDatabase();		
    	
	}

	public boolean getService() {
		int val = 0;
		Cursor get = db.query(dbh.dbtable, new String[] {"_id","name","val"}, "name = ?", new String[]{"service"}, null, null, null);
    	get.moveToFirst();
		val = Integer.parseInt(get.getString(2));
    	get.close();
		if(val==1)
		return true;
		return false;
	}
	public void insertDers(String val, int kat)
	{
		try
		{
			SQLiteStatement ss = db.compileStatement("INSERT INTO dersler (val,kat) VALUES (?,?)");
			ss.bindString(1, val);
			ss.bindLong(2,kat);
			ss.execute();
			ss.close();
		}
		catch (Exception e)
		{
			new OToast(context,e.toString());
		}
	}
	public int getDersCount()
	{
		Cursor get = db.query("dersler", new String[] {"_id","val","kat"}, null, null, null, null, null);
    	get.moveToFirst();
		return get.getCount();
	}
	public String[][] getDersler()
	{
		int count = getDersCount();
		if(count==0)
		{
			return new String[0][0];
		}
		String[][] dersler = new String[count][2];
		int i=0;
		Cursor get = db.query("dersler", new String[] {"_id","val","kat"}, null, null, null, null, null);
    	get.moveToFirst();
		while (!get.isAfterLast())
		{ 
		    dersler[i][0] = get.getString(1);
			dersler[i][1] = get.getString(2);
		   i++;
		   get.moveToNext(); 
		}
    	get.close();
		return dersler;
	}
	public boolean dersExists(String ders)
	{
		Cursor get = db.query("dersler", new String[]{"_id","val"},"val = ?", new String[]{ders}, null, null, null);
		if(get.moveToFirst())
			return true;
		return false;
	}
	public void updateDers(String ders, int kat)
	{
		try
		{
			SQLiteStatement ss = db.compileStatement("UPDATE dersler SET kat=? WHERE val=?");
			ss.bindString(2, ders);
			ss.bindString(1, kat+"");
			ss.execute();
			ss.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void deleteDers(String ders)
	{
		try
		{
			SQLiteStatement del = db.compileStatement("DELETE FROM dersler WHERE val=?");
			del.bindString(1,ders);
			del.execute();
			del.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean getNotif() {
		int val = 0;
		Cursor get = db.query(dbh.dbtable, new String[] {"_id","name","val"}, "name = ?", new String[]{"notif"}, null, null, null);
    	get.moveToFirst();
		val = Integer.parseInt(get.getString(2));
    	get.close();
		if(val==1)
		return true;
		return false;
	}

}
