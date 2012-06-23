package com.oguzdev.ortalama;

import android.content.Context;
import android.widget.Toast;

public class OToast extends Toast {

	Toast t;
	int duration;
	public OToast(Context context,CharSequence message) {
		super(context);
		// TODO Auto-generated constructor stub

		duration = Toast.LENGTH_SHORT;

		t = Toast.makeText(context, message, duration);
		t.show();
	}
	public OToast(Context context,CharSequence message,String dur) {
		super(context);
		// TODO Auto-generated constructor stub
		if(length.equals("long"))
			duration = Toast.LENGTH_LONG;
		else 
			duration = Toast.LENGTH_SHORT;

		t = Toast.makeText(context, message, duration);
		t.show();
	}
}

