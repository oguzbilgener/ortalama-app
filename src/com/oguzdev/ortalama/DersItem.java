package com.oguzdev.ortalama;

import android.content.*;
import android.view.*;
import android.widget.*;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.animation.*;
import android.view.View.OnClickListener;
import java.util.*;

public class DersItem extends LinearLayout implements OnClickListener
{
	/* Important */
	private Context context;
	private SettingsSource db;
	private LinearLayout parent;
	
	/* Values */
	private String name;
	private int katsayi;
	private int mid;
	
	/* Elements */
	private EditText derstext;
	private EditText derskatsayi;
	private Button silbutton;
	
	/* Helpers */
	private LayoutInflater inflater;
	
	public DersItem(Context con,LinearLayout parent,SettingsSource db)
	{
		super(con);
		
		this.context = con;
		this.db = db;
		this.parent = parent;
		
		inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.ders_layout,this);
		
		derstext = (EditText) findViewById(R.id.ders_name);
		derskatsayi = (EditText) findViewById(R.id.ders_katsayi);
		silbutton = (Button) findViewById(R.id.ders_silbutton);
		
		silbutton.setOnClickListener(this);
		
		
				
	}
	public void setName(String n)
	{
		this.name = n;
		derstext.setText(n);
	}
	public void setKatsayi(int k)
	{
		if(k<=0) k=1;
		katsayi = k;
		derskatsayi.setText(k+"");
	}
	public String getName()
	{
		return name;
	}
	public int getKatsayi()
	{
		return katsayi;
	}
	
	public void onClick(View v)
	{
		if (v.getId() == R.id.ders_silbutton) {
			try
			{
				db.deleteDers(name);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		DersItem child;
		for(int p=0;p<parent.getChildCount();p++)
		{
			child = (DersItem)parent.getChildAt(p);
			if(child.getName()==this.name)
			{
				/*Animation fadeanim = (Animation) AnimationUtils.loadAnimation(context,R.anim.fade_deleted_ders);
				this.startAnimation(fadeanim);
					mid = p;
					Timer t = new Timer();
					Killme k = new Killme();
					t.schedule(k,300);
				break;*/
				parent.removeViewAt(p);
			}
		}
	}
}
