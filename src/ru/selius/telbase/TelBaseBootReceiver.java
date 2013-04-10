package ru.selius.telbase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TelBaseBootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context, TelBaseService.class));
	}

}
