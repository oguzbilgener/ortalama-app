package com.oguzdev.ortalama;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.view.View.OnTouchListener;
import android.util.Log;
import com.oguzdev.ortalama.R;

public class Dersler extends Activity
{
	private Context context;
	private SettingsSource db;
	
	private LinearLayout parent;
	
	public static final String TAG = "Ortalama";
	
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dersler);
		
		context = getApplicationContext();
		db = new SettingsSource(this);
		
		ActionBar action = getActionBar();
		action.setDisplayHomeAsUpEnabled(true);
		
		parent = (LinearLayout)findViewById(R.id.parent);
		
	}
	@Override
	public void onResume()
	{
		super.onResume();
		db.giveperm();
		
		dersListele();
		
		int dcount = db.getDersCount();
		for(int d=0;d<(5-dcount);d++)
		{
			bosDersEkle();
		}
	}
	@Override
	public void onPause()
	{
		dersKaydet();
		db.close();
		super.onPause();
	}
	
	private void bosDersEkle()
	{
		DersItem di = new DersItem(this,parent,db);
		parent.addView(di);
	}
	private void dersListele()
	{
		String[][] dersler = db.getDersler();
		DersItem di;
		try
		{
			for(int i=0; i<dersler.length; i++)
			{
				di = new DersItem(this,parent,db);
				
				di.setName(dersler[i][0]);
				if(dersler[i][1]!=null && !dersler[i][1].equals(""))
					di.setKatsayi(Integer.parseInt(dersler[i][1]));
				 else
					di.setKatsayi(0);
				 
				parent.addView(di);
			}	
		}
		catch(Exception e)
		{
			Log.e(TAG,"dersler listelenemedi: "+e.toString());
		}
	}
	private void dersKaydet()
	{
		LinearLayout child;
		
		EditText derstext;
		EditText katsayitext;
		String d,ks;
		int k=0;
		int i=0;
		
		try
		{
			for(int c=0; c<parent.getChildCount(); c++)
			{
				child = (LinearLayout)parent.getChildAt(c);
				derstext = (EditText) child.findViewById(R.id.ders_name);
				d = derstext.getText().toString();
				katsayitext = (EditText) child.findViewById(R.id.ders_katsayi);
				ks = katsayitext.getText().toString();
				if(ks!=null && !ks.equals(""))
					k = Integer.parseInt(ks);
				else
					k=0;
				if(d!=null && !d.equals("") && k!=0)
				{
					if(db.dersExists(d))
					{
						db.updateDers(d,k);
					}
					else
					{
						db.insertDers(d,k);
					}
				}
				
			}
		}
		catch(Exception e)
		{
			new OToast(context,"kaydedilemedi: "+e.toString());
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case android.R.id.home:

				Intent i = new Intent(this,MainActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			break;
			case R.id.sub_menu_dersekle:
				bosDersEkle();
			break;
			case R.id.menu_about:
				Intent h = new Intent(this,About.class);
				h.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(h);
			break;
			default: 
				return super.onOptionsItemSelected(item);
		}
		return true;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inf = new MenuInflater(this);
		inf.inflate(R.layout.subactionmenu,menu);
		return true;
	}
	
}
