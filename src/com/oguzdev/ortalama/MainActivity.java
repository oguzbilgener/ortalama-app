package com.oguzdev.ortalama;

import com.actionbarsherlock.app.*;
import com.actionbarsherlock.internal.widget.*;
import com.actionbarsherlock.internal.view.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.Intent;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.*;
import java.util.*;
import java.nio.*;
import android.util.Log;
import java.text.*;

public class MainActivity extends SherlockActivity
{
	/* test */
    private Context context;
	private SettingsSource db;
	
	// ders listesi
	private DersList derslist;
	
	// sütunlar
	private LinearLayout parent1;
	private LinearLayout parent2;
	
	private Warning warning;

	
	private static final String TAG = "Ortalama";
	
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		context = getApplicationContext();
		db = new SettingsSource(this);
		
		derslist = new DersList();
		
		parent1 = (LinearLayout) findViewById(R.id.parent1);
		parent2 = (LinearLayout) findViewById(R.id.parent2);
		
		warning = new Warning();
		
	
	}
	@Override
	public void onResume()
	{		
		super.onResume();
		db.giveperm();
		
		setupList();
    }
	
	@Override
	public void onPause()
	{
		saveList();
		
		db.close();
		super.onPause();
	}
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
    	super.onConfigurationChanged(newConfig);
    	// orientation change
    	saveList();
    	setContentView(R.layout.main);
    	parent1 = (LinearLayout) findViewById(R.id.parent1);
		parent2 = (LinearLayout) findViewById(R.id.parent2);
		
		warning = new Warning();

    	
    	setupList();    	
    }
	
	private void setupList()
	{
		// dersleri veritabanından, notları hafızadan çağır
		parent1.removeAllViews();
		parent2.removeAllViews();
		
		warning.hide();
				
		int count = db.getDersCount();
			
		if(count>0)
		{
			String[][] dersler = db.getDersler();
			int i;
			for(i=0;i<dersler.length;i++)
			{
				NotItem not = new NotItem(this);
				not.setDers(dersler[i][0]);
				
				derslist.dersEkle(dersler[i][0],Integer.parseInt(dersler[i][1]),derslist.getNot(dersler[i][0]));
				not.setNot(derslist.getNot(dersler[i][0]));
						
				if(i%2==0)
					parent1.addView(not);
				else
					parent2.addView(not);
			}
		}
		else
		{
			// hiç ders yok uyarısı
			warning.setText(getResources().getString(R.string.warning_ders_ekle));
			warning.show();
		}
	}
	
	private void saveList()
	{
		// dersleri hafızaya kaydet
		NotItem ltemp;
		for(int p=0;p<parent1.getChildCount();p++)
		{
			ltemp = (NotItem) parent1.getChildAt(p);
			derslist.NotDegistir(ltemp.getDers(),ltemp.getNot());
		}
		for(int p=0;p<parent2.getChildCount();p++)
		{
			ltemp = (NotItem) parent2.getChildAt(p);
			derslist.NotDegistir(ltemp.getDers(),ltemp.getNot());
		}
	}
	
	private Float Ortalama()
	{
		Float toplam = 0f;
		int derscount = 0;
		String ldersname;
		int lderskat;
		Float lnot;
		NotItem ltemp;
		try
		{
			for(int p=0;p<parent1.getChildCount();p++)
			{
				ltemp = (NotItem) parent1.getChildAt(p);
				ldersname = ltemp.getDers();
				lderskat = derslist.getKat(ldersname);
				derscount += lderskat;
				lnot = ltemp.getNot();
				toplam += lnot*lderskat;
			}
			for(int p=0;p<parent2.getChildCount();p++)
			{
				ltemp = (NotItem) parent2.getChildAt(p);
				ldersname = ltemp.getDers();
				lderskat = derslist.getKat(ldersname);
				derscount += lderskat;
				lnot = ltemp.getNot();
				toplam += lnot*lderskat;		
			}
			if(derscount==0)
				throw new Exception();
			return toplam/derscount;
		}
		catch(Exception e)
		{
			return 0f;
		}
	}
	
	private void Hesapla()
	{
		try
		{
			Float ortalama = Ortalama();
			if(ortalama==0f)
			{
				new OToast(context,getResources().getString(R.string.enter_value));
				return;
			}
			Dialog result = new Dialog(this);
			result.setContentView(R.layout.resultdialog);
			result.setTitle("");
			result.setCancelable(true);
			
			TextView result_text = (TextView) result.findViewById(R.id.result_val);
			result_text.setText(new DecimalFormat("#.##").format(ortalama).toString());
			
			String title = "";
			if(ortalama>=84.5)
			{
				title = getResources().getString(R.string.title_takdir);
			}
			else if(ortalama>=69.5)
			{
				title = getResources().getString(R.string.title_tesekkur);
			}
			else
			{
				title = getResources().getString(R.string.title_zayif);
			}
			
			result.setTitle(title);
			result.show();
		}
		catch(Exception e)
		{
			new OToast(context,"Could not display result\n"+e.toString());
			e.printStackTrace();
			Log.e(TAG,"result error: "+e.toString());
		}
	}
	
	private void Temizle()
	{
		// temizle butonu
		derslist.notTemizle();
		NotItem ltemp;
		for(int p=0;p<parent1.getChildCount();p++)
		{
			ltemp = (NotItem) parent1.getChildAt(p);
			ltemp.setNot(0f,true);
		}
		for(int p=0;p<parent2.getChildCount();p++)
		{
			ltemp = (NotItem) parent2.getChildAt(p);
			ltemp.setNot(0f,true);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inf = new MenuInflater(this);
		inf.inflate(R.layout.actionmenu,menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == R.id.menu_dersler) {
			Intent i = new Intent(this,Dersler.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		} else if (item.getItemId() == R.id.menu_hesapla) {
			Hesapla();
		} else if (item.getItemId() == R.id.menu_temizle) {
			Temizle();
		} else if (item.getItemId() == R.id.menu_about) {
			Intent h = new Intent(this,About.class);
			h.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(h);
		}
		return true;
	}
	
	private class DersList
	{
		private Map<String,Float> notlar;
		private Map<String,Integer> katsayilar;
		public DersList()
		{
			notlar = new HashMap<String,Float>();
			katsayilar = new HashMap<String,Integer>();
		}
		public void dersEkle(String ders)
		{
			this.dersEkle(ders,0,0);
		}
		public void dersEkle(String ders, Integer kat)
		{
			this.dersEkle(ders,0,0);
		}
		public void dersEkle(String ders, Integer kat, float not)
		{
			notlar.put(ders,not);
			katsayilar.put(ders,kat);
		}
		public void NotDegistir(String ders,Float not)
		{
			notlar.put(ders,not);
		}
		public void KatDegistir(String ders,Integer kat)
		{
			katsayilar.put(ders,kat);
		}
		public void DersSil(String ders)
		{
			notlar.remove(ders);
			katsayilar.remove(ders);
		}
		public Float getNot(String ders)
		{
			try
			{
				if(notlar.get(ders)==null)
					throw new Exception();
				return notlar.get(ders);
			}
			catch(Exception e)
			{
				return 0f;
			}
		}
		public Integer getKat(String ders)
		{
			try
			{
				if(notlar.get(ders)==null)
					throw new Exception();
				return katsayilar.get(ders);
			}
			catch(Exception e)
			{
				return 0;
			}
		}
		public void notTemizle()
		{
			Set keyset = notlar.entrySet();
			Iterator i = keyset.iterator();

			while(i.hasNext())
			{
				Map.Entry m = (Map.Entry)i.next();
				this.NotDegistir(m.getKey().toString(),0f);
			}
		}
		public int size()
		{
			return notlar.size();
		}
		public void empty()
		{
			notlar.clear();
			katsayilar.clear();
		}
		public String toString()
		{
			/*Set<String> keys = notlar.keySet();
			Collection<Float> vals = notlar.values();*/
			
			String s = "";
			Set keyset = notlar.entrySet();
			Iterator i = keyset.iterator();
			
			while(i.hasNext())
			{
				Map.Entry m = (Map.Entry)i.next();
				s += m.getKey()+" : "+m.getValue();
				if(i.hasNext())
				s+= ", ";
			}
			return s;
		}
	}
	private class Warning
	{
		private LinearLayout warnlayout;
		private TextView warntext;
		private Warning()
		{
			warnlayout = (LinearLayout)findViewById(R.id.large_warning);
			warntext = (TextView)findViewById(R.id.warning_text);
		}
		public void show()
		{
			warnlayout.setVisibility(View.VISIBLE);
		}
		public void hide()
		{
			warnlayout.setVisibility(View.GONE);
		}
		public void setText(String text)
		{
			warntext.setText(text);
		}
	}
}
