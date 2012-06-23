package com.oguzdev.ortalama;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.content.*;
import android.view.*;
import android.text.util.*;
import android.text.method.*;

public class About extends Activity
{
	private Context context;
	private SettingsSource db;

	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

		context = getApplicationContext();
		db = new SettingsSource(this);

		ActionBar action = getActionBar();
		action.setDisplayHomeAsUpEnabled(true);
		
		TextView about_text = (TextView)findViewById(R.id.about_text);
		about_text.setMovementMethod(LinkMovementMethod.getInstance());		

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
			default: 
				return super.onOptionsItemSelected(item);
		}
		return true;
	}
}
