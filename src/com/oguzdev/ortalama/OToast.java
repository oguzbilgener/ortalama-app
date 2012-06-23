package com.oguzdev.ortalama;

import android.content.Context;

public class OToast {

	Toast t;
	int duration;
	public OToast(Context context,CharSequence message) {


		duration = Toast.LENGTH_SHORT;

		t = Toast.makeText(context, message, duration);
		t.show();
	}
	public OToast(Context context,CharSequence message,String how) {
		if(how.equals("long"))
			duration = Toast.LENGTH_LONG;
		else 
			duration = Toast.LENGTH_SHORT;

		t = Toast.makeText(context, message, duration);
		t.show();
	}
}

