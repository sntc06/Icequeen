package com.mis.icequeen;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class PostTest extends Activity {
	private TextView tvTime;
	private TextView tvScore;
	private Uri total;
	private Cursor test;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_test);
        Bundle extras = getIntent().getExtras();
        
        tvScore = (TextView) findViewById(R.id.tvScore);
        tvTime = (TextView) findViewById(R.id.tvTime);
        
        tvScore.setText(extras.getFloat("Grade")+"%");
        tvTime.setText(extras.getString("Time"));
        getIntent().setData(Uri.parse("content://com.mis.icequeen.testprovider/Newtest:"+extras.getString("cpt")+":"+tvScore.getText().toString()));
		total = getIntent().getData();
		test = managedQuery(total, null, null, null, null);
		test.moveToLast();
		System.out.println("insert:"+test.getString(0)+test.getString(1));
    }

}
