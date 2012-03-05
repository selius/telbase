package ru.selius.telbase;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.database.Cursor;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.widget.Toast;

public class TelBasePhoneStateListener extends PhoneStateListener {
	
	private Context _context;
	private TelBaseDBAdapter _db;
	private Toast _toast = null;
	private Timer _timer = null;
	
	private final int NUMBER_LENGTH = 10;
	
	public TelBasePhoneStateListener(Context context) {
		super();
		_context = context;
		_db = new TelBaseDBAdapter(context);
		_db.open();
	}

	@Override
	public void onCallStateChanged(int state, String incomingNumber) {
		switch (state) {
		case TelephonyManager.CALL_STATE_RINGING:
			_toast = Toast.makeText(_context,
				lookupIncomingNumber(incomingNumber), Toast.LENGTH_LONG);
			_toast.setGravity(Gravity.TOP, 0, 0);
			_toast.show();

			_timer = new Timer();
			_timer.schedule(new TimerTask() {
					@Override
					public void run() {
						_toast.show();
					}
				},
				3000, 3000
			);
			
			break;

		case TelephonyManager.CALL_STATE_IDLE:
			if (_timer != null) {
				_timer.cancel();
				_timer = null;
			}

			if (_toast != null) {
				_toast.cancel();
				_toast = null;
			}
			break;
		}
	}
	
	private String lookupIncomingNumber(String number) {
		String num = number.substring(
			Math.max(number.length() - NUMBER_LENGTH, 0));
		Cursor c = _db.lookupNumber(num);
		c.moveToFirst();
    	if (c.isAfterLast()){
    		return _context.getResources().getString(
    			R.string.call_toast_not_found);
    	}

    	return c.getString(c.getColumnIndex("name"));
	}

}
