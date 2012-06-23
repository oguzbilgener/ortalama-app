package com.oguzdev.ortalama;

import android.content.Context;
import android.view.View;
import android.widget.*;
import android.view.*;
import android.util.Log;

public class NotItem extends LinearLayout
{
	/* Important */
	private Context context;
	
	/* Values */
	private String dersname;
	
	/* Elements */
	private TextView ders;
	private EditText not;
	
	/* Helpers */
	private LayoutInflater inflater;
	
	public NotItem(Context con)
	{
		super(con);
		this.context = con;
		
		inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.not_layout,this);
		
		ders = (TextView) this.findViewById(R.id.not_ders);
		not = (EditText) this.findViewById(R.id.not_val);
	}
	public void setDers(String t)
	{
		this.dersname = t;
		ders.setText(t);
	}
	public void setNot(Float f)
	{
		if(f==0)
			return;
		
		try
		{
			float lf = f;
			int li = (int)lf;
			if (f == li)
				not.setText(li+"");
			else
				not.setText(f.toString());
		}
		catch (Exception e) 
		{
			try
			{
				not.setText("0");
			}
			catch(Exception es)
			{
				es.printStackTrace();
				Log.e("Ortalama","stupid error? "+es.toString());
			}
		}
	}
	public void setNot(Float f, Boolean force)
	{
		if(!force)
			return;
		if(f==0f)
			not.setText("");
		else
		{
			not.setText(f.toString());
		}
	}
	public String getDers()
	{
		return dersname;
	}
	public Float getNot()
	{
		try
		{
			String notstring = not.getText().toString();
			if (notstring.equals(""))
				return 0f;
			else
				return Float.parseFloat(notstring);
		}
		catch (NumberFormatException e)
		{
			new OToast(context,e.toString());
			Log.e("Ortalama",e.toString());
			return 0f;
		}
	}
}
