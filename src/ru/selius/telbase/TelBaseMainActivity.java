package ru.selius.telbase;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TelBaseMainActivity extends Activity {
	
	private TelBaseDBAdapter _db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        startService(new Intent(this, TelBaseService.class));

        setContentView(R.layout.main);
        
        _db = new TelBaseDBAdapter(this);
        _db.open();
        
        getLookupButton().setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				getResultView().setText(
					lookupNumber(getInputEdit().getText().toString()));
			}
		});
        
    }
    
    private Button getLookupButton() {
    	return (Button) findViewById(R.id.lookupButton);
    }
    
    private EditText getInputEdit() {
    	return (EditText) findViewById(R.id.inputEdit);
    }
    
    private TextView getResultView() {
    	return (TextView) findViewById(R.id.resultView);
    }
    
    private String lookupNumber(String number) {
    	Cursor c = _db.lookupNumber(number);
    	c.moveToFirst();
    	if (c.isAfterLast()){
    		return getResources().getString(R.string.result_view_not_found);
    	}

    	return c.getString(c.getColumnIndex("name"));
    }

}