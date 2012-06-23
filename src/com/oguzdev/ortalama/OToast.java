package com.oguzdev.ortalama;

import android.content.Context;
import android.widget.Toast;

public class OToast extends Toast {

	Toast oguz;
	int duration;
	public OToast(Context context,CharSequence message) {
		super(context);
		// TODO Auto-generated constructor stub

		duration = Toast.LENGTH_SHORT;

		oguz = Toast.makeText(context, message, duration);
		oguz.show();
	}
	public OToast(Context context,CharSequence message,String how) {
		super(context);
		// TODO Auto-generated constructor stub
		if(how=="long")
			duration = Toast.LENGTH_LONG;
		else 
			duration = Toast.LENGTH_SHORT;

		oguz = Toast.makeText(context, message, duration);
		oguz.show();
	}
}

