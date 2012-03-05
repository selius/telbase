package ru.selius.telbase;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TelBaseDBAdapter {
	
	private class TelBaseOpenHelper extends SQLiteOpenHelper {

		public TelBaseOpenHelper(Context context) {
			super(context, "telbase", null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(_context.getString(R.string.db_create_script));
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion,
				int newVersion) {
			// do nothing
		}

	}
	
	private TelBaseOpenHelper _helper;
	private SQLiteDatabase _db;
	private Context _context;
	
	public TelBaseDBAdapter(Context context) {
		_context = context;
	}
	
	public TelBaseDBAdapter open() throws SQLException {
		_helper = new TelBaseOpenHelper(_context);
		_db = _helper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		_helper.close();
	}
	
	public Cursor lookupNumber(String number) {
		return _db.query("telbase",
				new String[] { "name" },
				"tel = ?",
				new String[] { number },
				null, null, null
		);
	}

}
