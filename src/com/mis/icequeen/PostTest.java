package com.mis.icequeen;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PostTest extends Activity {
	private TextView tvTime;
	private TextView tvScore;
	private Uri total;
	private Cursor test;
	private Button btnReselect;
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
		
		btnReselect = (Button) findViewById(R.id.btnReselect);
		btnReselect.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent it = new Intent();
				String mode = "TEST";
				it.putExtra("MODE", mode);
				it.putExtra("BOOK", getIntent().getExtras().getInt("BOOK"));
				it.setClass(PostTest.this, ChapterSelectionActivity.class);
				startActivity(it);
				
			}
		});
		
    }

}
