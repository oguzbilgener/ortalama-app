package com.oguzdev.ortalama;

import com.actionbarsherlock.app.*;
import com.actionbarsherlock.internal.widget.*;
import com.actionbarsherlock.internal.view.*;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.content.*;
import android.view.*;
import android.text.util.*;
import android.text.method.*;

public class About extends SherlockActivity
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

		ActionBar action = getSupportActionBar();
		action.setDisplayHomeAsUpEnabled(true);
		
		TextView about_text = (TextView)findViewById(R.id.about_text);
		about_text.setMovementMethod(LinkMovementMethod.getInstance());		

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == R.id.abs__home) {
			Intent i = new Intent(this,MainActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		} else {
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
}
