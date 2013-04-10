package ru.selius.telbase;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class TelBaseService extends Service {
	
	public class TelBaseBinder extends Binder {
		TelBaseService getService() {
			return TelBaseService.this;
		}
	}
	
	private IBinder _binder = new TelBaseBinder();
	
	private TelBasePhoneStateListener _phoneStateListener;

	@Override
	public IBinder onBind(Intent intent) {
		return _binder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		_phoneStateListener = new TelBasePhoneStateListener(this);
		setListenEvents(PhoneStateListener.LISTEN_CALL_STATE);
	}

	@Override
	public void onDestroy() {
		setListenEvents(PhoneStateListener.LISTEN_NONE);
		super.onDestroy();
	}
	
	private void setListenEvents(int events) {
		TelephonyManager telephony =
			(TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		telephony.listen(_phoneStateListener, events);
	}

}
